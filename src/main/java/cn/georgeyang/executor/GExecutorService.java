package cn.georgeyang.executor;

import cn.georgeyang.redis.RedisService;
import cn.georgeyang.utils.Utils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.concurrent.*;

/**
 * 线程池批量查询
 * 实体类注解mapper名
 * id字段注解 getInfoTo xx字段（该字段可能是一个，也可能是多个) xx字段，又注解mapper名
 * 给定class和id，自动解析，支持多个输入，返回一个实体
 * 执行过程是查找redis，如果没有再找mapper缓存，最后查库，后续存入缓存
 * 如果找不到mapper了，直接抛出奔溃
 */
public class GExecutorService {
    @Autowired
    private RedisService redisService;

    private RedisTemplate<String, String> getRedisTemplate() {
        return redisService.getRedisTemplate();
    }

    private void getAllClassField(List<Field> fieldList, Class clz, Class... withAnnotationFiled) {
        if (clz == null || clz == Object.class)
            return;
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if (withAnnotationFiled.length == 0) {
                field.setAccessible(true);
                fieldList.add(field);
            } else {
                for (Class annonClz : withAnnotationFiled) {
                    if (field.getAnnotation(annonClz) != null) {
                        field.setAccessible(true);
                        fieldList.add(field);
                    }
                }
            }
        }
        Class supClz = clz.getSuperclass();
        getAllClassField(fieldList, supClz, withAnnotationFiled);
    }

    private Field getFieldByClass(Class clz, String fieldName) {
        if (clz == null)
            return null;
        if (clz == Object.class)
            return null;
        try {
            Field field = clz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (java.lang.Exception e) {

        }
        return getFieldByClass(clz.getSuperclass(), fieldName);
    }

    //键，类名
    //值，select可查询方法
    //mapper查询方法缓存
    private Map<Class, Method> selectMethodCacheMap = new WeakHashMap<>();
    private Map<String, Method> updateMethodCacheMap = new WeakHashMap<>();
    private Map<Class, Method> deleteMethodCacheMap = new WeakHashMap<>();
    private Map<Class, Method> insertMethodCacheMap = new WeakHashMap<>();
    private Map<Class, Method> setIdMethodCacheMap = new WeakHashMap<>();
    private Map<Class, CoustmerMapperExec> coustmerMapperExecMap = new WeakHashMap<>();

    //实体的子查询字段数组缓存
    private Map<Class, List<Field>> subSelectMethodMap = new HashMap<>();

    //自定义查询器类名-实例缓存
    private WeakHashMap<Class, SubExecIntf> executerCacheMap = new WeakHashMap<>();

    public <T, O> List<T> tranToList(Class<T> clz, List<O> dataList) throws Exception {
        return tranToList(null, clz, dataList);
    }

    public <T, O> List<T> tranToList(ExecuteContext executeContext, Class<T> clz, List<O> dataList) throws Exception {
        if (Utils.isEmpty(dataList))
            return Collections.emptyList();
        List<T> retList = new ArrayList<>(dataList.size());

        //TODO 換成綫程池
//        ExecutorService executorService = getExecutorService(dataList.size());
//        List<Future<T>> futureList = new LinkedList<>();
        for (O t : dataList) {
            retList.add(fetchFrom(executeContext, t, clz));
        }
        return retList;
    }


    private java.util.concurrent.ExecutorService getExecutorService(Integer dataSize) {
        int threadSize = dataSize;
        threadSize = threadSize >= 8 ? 8 : threadSize;
        java.util.concurrent.ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
        return executorService;
    }

    public <T> List<T> selectList(Class<T> clz, List<Integer> ids) throws Exception {
        return this.selectList(null, clz, ids);
    }

    public <T> List<T> selectList(ExecuteContext executeContext, Class<T> clz, List<Integer> ids) throws Exception {
        if (Utils.isEmpty(ids))
            return Collections.emptyList();
        List<T> retList = new ArrayList<>(ids.size());
        if (ids.size() == 1) {
            T ret = this.fetch(executeContext, clz, ids.get(0));
            retList.add(ret);
            return retList;
        }

        java.util.concurrent.ExecutorService executorService = getExecutorService(ids.size());
        List<Future<T>> futureList = new LinkedList<>();
        for (Integer id : ids) {
            ThreadSubCallable callable = new ThreadSubCallable(this, executeContext, clz, id);
            Future<T> ret = executorService.submit(callable);
            futureList.add(ret);
        }
        for (Future<T> future : futureList) {
            retList.add(future.get());
        }
        executorService.shutdown();
        return retList;
    }

    /**
     * 转化
     *
     * @param clz
     * @param <T>
     * @return
     * @throws Exception
     */
    //嵌套会导致出现重复数据，不要使用转化，使用select（传入contextCacheList防止重复），
    public <T> T tranTo(Object value, Class<T> clz) throws Exception {
        return fetchFrom(null, value, clz);
    }

    @Deprecated
    public <T> T fetchFrom(ExecuteContext executeContext, Object value, Class<T> clz) throws Exception {
        return this.tranTo(executeContext, value, clz);
    }

    public <T> T tranTo(ExecuteContext executeContext, Object value, Class<T> clz) throws Exception {
        return this.tranTo(executeContext, value, clz,null);
    }

    public <T> T tranTo(ExecuteContext executeContext, Object value, Class<T> clz,Object attachParam) throws Exception {
        if (value == null)
            return null;
        //返回类型不一致(可能是高级的多字段的实体)，用json转一下
        String retJson = JSON.toJSONString(value);
        Object object = JSON.parseObject(retJson, clz);

        //获取子字段，关联的，可被赋值的
        List<Field> subSelectFieldList = getSubSelectFieldList(clz);

        //子字段还需要在找数据库！！
        if (Utils.isEmpty(subSelectFieldList))
            return (T) object;

        List<Object> fieldValues = new ArrayList<>();
        if (subSelectFieldList.size() >= 2) {
            int threadSize = subSelectFieldList.size();
            threadSize = threadSize >= 8 ? 8 : threadSize;
            java.util.concurrent.ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
            List<Future<ExecutorBean>> futureList = new LinkedList<>();
            for (int i = 0; i < subSelectFieldList.size(); i++) {
                ExecutorBean executorBean = new ExecutorBean();
                executorBean.index = i;
                executorBean.context = executeContext;
                executorBean.tag = object;
                executorBean.field = subSelectFieldList.get(i);
                executorBean.attachParam = attachParam;
                ThreadBeanCallable callable = new ThreadBeanCallable(this, executorBean);
                Future<ExecutorBean> ret = executorService.submit(callable);
                futureList.add(ret);
            }
            for (Future<ExecutorBean> future : futureList) {
                try {
                    ExecutorBean ret = future.get();
                    fieldValues.add(ret.result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            executorService.shutdown();
        } else {
            Field field = subSelectFieldList.get(0);
            Object fieldValue = selectFieldValue(executeContext, object, field,attachParam);
            fieldValues.add(fieldValue);
        }

        //所有结果，设置到字段里
        for (int i = 0; i < subSelectFieldList.size(); i++) {
            Field setField = subSelectFieldList.get(i);
            Object fieldValue = fieldValues.get(i);
            if (fieldValue == null || object == null)
                continue;
            //如果放到公共data緩存成功，那麽不用設置值
            boolean filterSetValue = false;
            if (executeContext != null) {
                filterSetValue = executeContext.putToCache(this, value, object.getClass(), setField, fieldValue);
            }
            if (filterSetValue)
                continue;
            if (setField.getType() != fieldValue.getClass()) {
                fieldValue = tranTo(executeContext, fieldValue, setField.getType());
            }
            setField.set(object, fieldValue);
        }
        return (T) object;
    }

    public <T> T select(Class<T> clz, Object id) throws Exception {
        if (id == null) {
            throw new NullPointerException("select id must not be null");
        }
        if (id instanceof String) {
            return selectByKey(clz, (String) id);
        }
        if (id instanceof Integer) {
            return selectByKey(clz, (Integer) id);
        }
        if (id instanceof Long) {
            return selectByKey(clz, (Long) id);
        }
        throw new IllegalArgumentException("type for id is not support");
    }

    public <T> T select(Class<T> clz, Integer id) throws Exception {
        return selectByKey(clz, id);
    }

    public <T> T select(Class<T> clz, String id) throws Exception {
        return selectByKey(clz, id);
    }

    public <T> T select(Class<T> clz, Long lId) throws Exception {
        return selectByKey(clz, lId);
    }

    private <T> CoustmerMapperExec getMapperExecerFromClass(Class<T> clazz) throws Exception {
        ExecutableClass executableClass = checkAndGetExecutableClassFromClazz(clazz);
        CoustmerMapperExec execCache = coustmerMapperExecMap.get(clazz);
        if (execCache == null) {
            Class<? extends CoustmerMapperExec> mapperExer = executableClass.mapperExecer();
            if (mapperExer == CoustmerMapperExec.class)
                return null;
            execCache = mapperExer.newInstance();
            coustmerMapperExecMap.put(clazz, execCache);
        }
        return execCache;
    }

    /**
     * 递归找到ExecutableClass
     *
     * @param clz
     * @return
     */
    protected ExecutableClass getExecutableClassFromClazz(Class clz) {
        ExecutableClass executableClass = (ExecutableClass) clz.getAnnotation(ExecutableClass.class);
        if (executableClass != null)
            return executableClass;
        Class superClz = clz.getSuperclass();
        if (superClz == Object.class)
            return null;
        return getExecutableClassFromClazz(superClz);
    }

    /**
     * 检查类是否包含ExecutableClass，要么返回值，要么抛异常
     *
     * @param clz
     * @return
     * @throws Exception
     */
    private ExecutableClass checkAndGetExecutableClassFromClazz(Class clz) throws Exception {
        if (clz == null)
            throw new IllegalArgumentException("clz can't be null");
        ExecutableClass executableClass = getExecutableClassFromClazz(clz);
        if (executableClass == null)
            throw new IllegalArgumentException("clz can't Executable:" + clz.getName());
        return executableClass;
    }

    private <T> T selectByKey(Class<T> clz, Object key) throws Exception {
        return fetch(null, clz, key);
    }

    public <T> T fetch(ExecuteContext executeContext, Class<T> clz, Object key) throws Exception {
        return fetch(executeContext,clz,key,null);
    }

    public <T> T fetch(ExecuteContext executeContext, Class<T> clz, Object key,Object attachParam) throws Exception {
        Object object = null;
        ExecutableClass executableClass = checkAndGetExecutableClassFromClazz(clz);
        Class mapperClz = executableClass.mapperClazz();
        String redisKey = null;

        if (executeContext != null)
            object = executeContext.getContextCache(this, clz, key);
        if (object == null) {
            redisKey = String.format("%s-%s", mapperClz.getName(), String.valueOf(key));
            if (executableClass.redisCacheSaveMinute() > 0) {
                object = getObject(redisKey, clz);
            }
        } else {
            System.out.println("重复的查询，已被忽略:" + clz + ">>" + key);
        }


        Method selectMethod = null;
        if (object == null) {
            //org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.lizetang.api.mapper.db.CoGoodInfoEntityMapper' available: expected single matching bean but found 2: coGoodInfoMapper,coGoodInfoEntityMapper
            Object mapper = getWebApplicationContext().getBean(mapperClz);

            CoustmerMapperExec mapperExec = getMapperExecerFromClass(clz);
            if (mapperExec == null) {
                selectMethod = getSelectMethod(mapperClz, executableClass);
                if (selectMethod == null)
                    throw new IllegalArgumentException("can't find method (" + executableClass.selectFieldName() + ") in mapper:" + clz.getName());
                try {
                    object = selectMethod.invoke(mapper, key);
                } catch (Throwable t) {
                    if (t instanceof IllegalArgumentException) {
                        throw new IllegalArgumentException("selectMethod argument type mismatch:" + selectMethod + " argument:" + key);
                    } else {
                        throw t;
                    }
                }
            } else {
                object = mapperExec.onMapperSelect(getWebApplicationContext(), mapper, key);
            }

            int redisCacheSaveMinute = executableClass.redisCacheSaveMinute();
            //对数据库原始数据缓存(须在获取关联数据之前)
            if (redisCacheSaveMinute >= 1) {
                this.setObject(redisKey, object, redisCacheSaveMinute);
            }
        }

        //获取子字段，关联的，可被赋值的
        List<Field> subSelectFieldList = getSubSelectFieldList(clz);
        boolean hasOtherExecField = false;
        if (subSelectFieldList == null || subSelectFieldList.size() == 0) {
            hasOtherExecField = false;
        } else if (subSelectFieldList.size() == 1) {
            Field execField = subSelectFieldList.get(0);
            if (execField != null) {
                ExecutableField executableField = execField.getAnnotation(ExecutableField.class);
                if (!executableField.isIdField()) {
                    hasOtherExecField = true;
                }
            }
        } else {
            hasOtherExecField = true;
        }

        System.out.println("fetch start:" + clz + ">>" + key);
        System.out.println("fetch info:" + object);

        //返回类型一致，是数据库原始数据，不用后面的子关联查询了
        if (!hasOtherExecField && (selectMethod == null || selectMethod.getReturnType() == clz)) {
            return (T) object;
        }

        return tranTo(executeContext, object, clz, attachParam);
    }

    public Integer insert(Object object) throws Exception {
        Class objectClz = object.getClass();
        ExecutableClass executableClass = checkAndGetExecutableClassFromClazz(objectClz);

        Class mapperClz = executableClass.mapperClazz();
        Object mapper = getWebApplicationContext().getBean(mapperClz);

        Object retObj = null;
        CoustmerMapperExec mapperExec = getMapperExecerFromClass(objectClz);
        if (mapperExec == null) {
            Method insertMethod = getInsertMethod(executableClass.insertFieldName(), mapperClz);
            retObj = insertMethod.invoke(mapper, object);
        } else {
            retObj = mapperExec.onMapperInsert(getWebApplicationContext(), mapper, object);
        }

        if (retObj == null) {
            return null;
        }
        if (retObj.getClass() == int.class) {
            return (int) retObj;
        }
        if (retObj instanceof Integer) {
            return (Integer) retObj;
        }
        throw new ClassCastException(retObj.toString() + " can Not Cast To Integer");
    }


    public Integer delete(Class executableBeanClass, Integer id) throws Exception {
        Integer ret = null;

        Object bean = executableBeanClass.newInstance();
        Method setIdMethod = getSetIdMethod(executableBeanClass);
        setIdMethod.invoke(bean, (int) id);
        ret = delete(bean);

        return ret;
    }

    public Integer delete(Object object) throws Exception {
        Class objectClz = object.getClass();
        ExecutableClass executableClass = checkAndGetExecutableClassFromClazz(objectClz);

        Class mapperClz = executableClass.mapperClazz();
        Object mapper = getWebApplicationContext().getBean(mapperClz);

        CoustmerMapperExec mapperExec = getMapperExecerFromClass(objectClz);
        boolean isSoftDelete = true;
        Integer ret = 0;
        if (mapperExec == null) {
            Method deleteMethod = getDelectMethod(mapperClz, object, executableClass);
            //判断软删除
            Class delMthClz = deleteMethod.getDeclaringClass();
            isSoftDelete = delMthClz == objectClz;

            if (isSoftDelete) {
                deleteMethod.invoke(object, true);
                ret = this.update(object);
            } else {
                ret = (int) deleteMethod.invoke(mapper, object);
            }
        } else {
            ret = mapperExec.onMapperDelete(getWebApplicationContext(), mapper, object);
        }

        //这里不处理自定义删除的缓存
        if (!isSoftDelete) {
            //最后删除redis缓存，返回值
            Object id = tryGetBeanId(object);
            if (id == null)
                return -1;
            String redisKey = String.format("%s-%s", mapperClz.getName(), String.valueOf(id));
            removeCacheObject(redisKey);
        }

        //获取子字段，关联的，可被赋值的
        List<Field> subSelectFieldList = getSubSelectFieldList(objectClz);
        for (Field execField : subSelectFieldList) {
            ExecutableField executableField = execField.getAnnotation(ExecutableField.class);
            if (executableField != null) {
                SubExecIntf subExecIntf = this.getSubExecInstance(executableField);
                subExecIntf.onParentDelete(object);
            }
        }


        return ret;

    }

    /**
     * 更新或者插入一个实体
     *
     * @param formObj 原来的实体(表数据)
     * @param toObj   之后的实体(表数据)
     * @return
     * @throws Exception
     */
    public Integer updateOrInsertTo( Object formObj,  Object toObj,  IEquality iequality) throws Exception {
        if (toObj == null)
            throw new InvalidParameterException("toObj can't be null");
        if (formObj != null && toObj != null) {
            boolean eq;
            if (iequality != null) {
                eq = iequality.equals(formObj, toObj);
            } else {
                eq = formObj.equals(toObj);
            }

            if (eq)
                return 1;//相等，当作是更新成功了，实际上没有更新数据库
            else
                return this.update(toObj);
        }
        if (formObj == null && toObj != null) {
            return this.insert(toObj);
        }
        return 0;
    }

    public Integer updateOrInsertTo( Object formObj,  Object toObj) throws Exception {
        return updateOrInsertTo(formObj, toObj, null);
    }

    public Integer update(Object object) throws Exception {
        Class objectClz = object.getClass();
        ExecutableClass executableClass = checkAndGetExecutableClassFromClazz(objectClz);

        Class mapperClz = executableClass.mapperClazz();
        Object mapper = getWebApplicationContext().getBean(mapperClz);

        CoustmerMapperExec mapperExec = getMapperExecerFromClass(objectClz);
        int ret = 0;
        if (mapperExec == null) {
            String methodCacheKey = mapperClz.getName();
            Method updateMethod = updateMethodCacheMap.get(methodCacheKey);
            if (updateMethod == null) {
                updateMethod = getClassMethod(mapperClz, true, executableClass.updateFieldName());
                updateMethod.setAccessible(true);
                updateMethodCacheMap.put(methodCacheKey, updateMethod);
            }

            ret = (int) updateMethod.invoke(mapper, object);
        } else {
            ret = mapperExec.onMapperUpdate(getWebApplicationContext(), mapper, object);
        }

        if (ret <= 0)
            return ret;


        Object id = tryGetBeanId(object);
        if (id == null)
            return -1;
        String redisKey = String.format("%s-%s", mapperClz.getName(), String.valueOf(id));
        if (executableClass.flushCache()) {//删除缓存
            removeCacheObject(redisKey);
        } else {
            //更新后的缓存，过期时间减半
            this.setObject(redisKey, object, executableClass.redisCacheSaveMinute() / 2);
        }


        //获取子字段，关联的，可被赋值的
        List<Field> subSelectFieldList = getSubSelectFieldList(objectClz);
        for (Field execField : subSelectFieldList) {
            ExecutableField executableField = execField.getAnnotation(ExecutableField.class);
            if (executableField != null) {
                SubExecIntf subExecIntf = this.getSubExecInstance(executableField);
                subExecIntf.onParentUpdate(object);
            }
        }

        return ret;
    }


    protected Object tryGetBeanId(Object bean) {
        if (bean == null)
            return null;
        Class clz = bean.getClass();
        try {
            Field idField = getFieldByClass(clz, "id");
            return idField.get(bean);
        } catch (Exception e) {
        }

        try {
            List<Field> fields = this.getSubSelectFieldList(clz);
            for (Field execField : fields) {
                ExecutableField executableField = execField.getAnnotation(ExecutableField.class);
                if (executableField.isIdField()) {
                    return execField.get(bean);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }


    protected Method tryGetSetDelMethod(Object bean) {
        Class clz = bean.getClass();
        return getClassMethod(clz, true, "setIsdel");
    }

    private SubExecIntf getSubExecInstance(ExecutableField executableField) throws Exception {
        if (executableField == null)
            return null;
        Class excutoeClazz = executableField.executeImpl();
        return this.getSubExecInstance(excutoeClazz);
    }

    public <T extends SubExecIntf> T getSubExecInstance(Class<T> subExecClazz) throws Exception {
        if (subExecClazz == null)
            return null;
        SubExecIntf subExecIntf = executerCacheMap.get(subExecClazz);
        if (subExecIntf == null) {
            subExecIntf = subExecClazz.newInstance();
            subExecIntf.init(getWebApplicationContext(),this);
            executerCacheMap.put(subExecClazz, subExecIntf);
        }
        return (T) subExecIntf;
    }

    protected Object selectFieldValue(ExecuteContext context, Object bean, Field execField, Object attachParam) throws Exception {
        if (bean == null)
            return null;
        Class beanClazz = bean.getClass();
        Object fieldValue = null;
        Object paramter = null;
        ExecutableField executableField = execField.getAnnotation(ExecutableField.class);
        if (executableField != null) {
            if (executableField.isIdField()) {//id字段，直接返回
                return execField.get(bean);
            }
            SubExecIntf subExecIntf = this.getSubExecInstance(executableField);
            if (subExecIntf == null)
                return null;

            synchronized (subExecIntf) {//一次查询的生命周期
                if (subExecIntf.preStartSelect(context, executableField, bean))
                    return null;

                String[] bindFiles = executableField.bindFieldList();
                if (bindFiles.length > 0) {
                    Object[] params = new Object[bindFiles.length];
                    for (int i = 0; i < bindFiles.length; i++) {
                        Field bindIdField = getFieldByClass(beanClazz, bindFiles[i]);
                        if (bindIdField == null)
                            throw new NullPointerException("can't find idField (" + bindFiles[i] + ") in  class : " + beanClazz.getName());
                        paramter = bindIdField.get(bean);
                        params[i] = paramter;
                    }
                    fieldValue = subExecIntf.onSubSelectMore(context, execField.getType(), params, attachParam);
                } else {
                    Field bindIdField = getFieldByClass(beanClazz, executableField.bindFieldName());
                    if (bindIdField == null)
                        throw new NullPointerException("can't find idField (" + executableField.bindFieldName() + ") in  class : " + beanClazz.getName());
                    paramter = bindIdField.get(bean);

                    fieldValue = subExecIntf.onSubSelect(context, execField.getType(), paramter, attachParam);
                }

                subExecIntf.finishSelect(context,executableField,bean,fieldValue);
            }//end synchronized

        }

        System.out.println("selectFieldValue start:" + bean + ">>" + execField);
        System.out.println("selectFieldValue info:" + paramter + ">>" + fieldValue);
        return fieldValue;
    }


    public void init() {
        System.out.println("DBExecutorService init!!!");
    }

    public void destory() {
        System.out.println("DBExecutorService destory!!!");
    }

    private WebApplicationContext mWebApplicationContext;

    private WebApplicationContext getWebApplicationContext() {
        if (mWebApplicationContext == null) {
            mWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        }
        return mWebApplicationContext;
    }

    protected <T> List<T> getObjectList(final String key, Class<T> clazz) {
        String json = this.getRedisTemplate().boundValueOps(key).get();
        return JSON.parseArray(json, clazz);
    }

    protected <T> T getObject(final String key, Class<T> clazz) {
        String json = this.getRedisTemplate().boundValueOps(key).get();
        return JSON.parseObject(json, clazz);
    }

    protected boolean setObject(final String key, Object value, int expireMinutes) {
        if (key == null || value == null)
            return false;
        String json = JSON.toJSONString(value);
        this.getRedisTemplate().opsForValue().set(key, json);
        if (expireMinutes > 0) {
            this.getRedisTemplate().expire(key, expireMinutes, TimeUnit.MINUTES);
        }
        return true;
    }

    public boolean removeCache(Class beanClazz, Object id) {
        ExecutableClass executableClass = null;
        try {
            executableClass = checkAndGetExecutableClassFromClazz(beanClazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (executableClass == null)
            return false;
        Class mapperClz = executableClass.mapperClazz();
        String redisKey = String.format("%s-%s", mapperClz.getName(), String.valueOf(id));
        return this.removeCacheObject(redisKey);
    }

    protected boolean removeCacheObject(final String key) {
        this.getRedisTemplate().delete(key);
        return true;
    }

    protected boolean setObjectList(final String key, List<Object> value, int expireMinutes) {
        String json = JSON.toJSONString(value);
        this.getRedisTemplate().opsForValue().set(key, json);
        if (expireMinutes > 0) {
            this.getRedisTemplate().expire(key, expireMinutes, TimeUnit.MINUTES);
        }
        return true;
    }


    protected Method getDelectMethod(Class mapperClz, Object target, ExecutableClass executableClass) {
        Method deleteMethod = deleteMethodCacheMap.get(mapperClz);
        try {

            if (deleteMethod == null) {
                //优先bean的软删除,isDel字段
                deleteMethod = tryGetSetDelMethod(target);

                //如果没有软删除，那就只好来硬删除了
                if (deleteMethod == null) {
                    deleteMethod = getClassMethod(mapperClz, false, executableClass.deleteFieldName(), Integer.class);
                }

                deleteMethod.setAccessible(true);

                deleteMethodCacheMap.put(mapperClz, deleteMethod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteMethod;
    }

    protected Method getSelectMethod(Class mapperClz, ExecutableClass executableClass) {
        Method selectMethod = selectMethodCacheMap.get(mapperClz);
        if (selectMethod != null) {
            return selectMethod;
        }
        //ID主键
        try {
            if (selectMethod == null) {
                selectMethod = getClassMethod(mapperClz, false, executableClass.selectFieldName(), Integer.class);
            }
        } catch (Exception e) {
        }
        //Long为主键
        try {
            if (selectMethod == null) {
                selectMethod = getClassMethod(mapperClz, false, executableClass.selectFieldName(), Long.class);
            }
        } catch (Exception e) {
        }
        //字符串主键
        try {
            if (selectMethod == null) {
                selectMethod = getClassMethod(mapperClz, false, executableClass.selectFieldName(), String.class);
            }
        } catch (Exception e) {
        }
        //最终拿到了主键的查询方法
        if (selectMethod != null) {
            selectMethod.setAccessible(true);
            selectMethodCacheMap.put(mapperClz, selectMethod);
        }
        return selectMethod;
    }

    protected Method getClassMethod(Class clz, boolean ignoreCase, String methodName, Class<?>... pars) {
        if (clz == null)
            return null;
        try {
            if (ignoreCase) {
                Method[] methods = clz.getMethods();
                for (Method method : methods) {
                    if (method.getName().equalsIgnoreCase(methodName)) {
                        return method;
                    }
                }
            } else {
                Method method = clz.getMethod(methodName, pars);
                return method;
            }
        } catch (Exception e) {
        }

        try {
            if (ignoreCase) {
                Method[] methods = clz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().equalsIgnoreCase(methodName)) {
                        return method;
                    }
                }
            } else {
                Method method = clz.getDeclaredMethod(methodName, pars);
                return method;
            }
        } catch (Exception e) {
        }
        Class superClz = clz.getSuperclass();
        return getClassMethod(superClz, ignoreCase, methodName, pars);
    }

    private List<Field> getSubSelectFieldList(Class executableBeanClass) {
        List<Field> subSelectFieldList = subSelectMethodMap.get(executableBeanClass);
        if (subSelectFieldList == null) {
            subSelectFieldList = new ArrayList<>();
            getAllClassField(subSelectFieldList, executableBeanClass, ExecutableField.class);
            subSelectMethodMap.put(executableBeanClass, subSelectFieldList);
        }
        return subSelectFieldList;
    }


    protected Method getInsertMethod(String coumserMethodName, Class mapperClz) {
        Method setIdMethod = insertMethodCacheMap.get(mapperClz);
        if (setIdMethod != null)
            return setIdMethod;

        Method inserMethod = null;
        if (coumserMethodName != null && coumserMethodName.length() > 0) {
            inserMethod = getClassMethod(mapperClz, true, coumserMethodName);
        }
        if (inserMethod == null) {
            inserMethod = getClassMethod(mapperClz, true, "insertRetId");
        }
        if (inserMethod == null) {
            inserMethod = getClassMethod(mapperClz, true, "insertSelective");
        }
        if (inserMethod == null) {
            inserMethod = getClassMethod(mapperClz, true, "insert");
        }

        if (inserMethod != null) {
            insertMethodCacheMap.put(mapperClz, inserMethod);
        }
        return inserMethod;
    }

    protected Method getSetIdMethod(Class beanClz) {
        Method setIdMethod = setIdMethodCacheMap.get(beanClz);
        if (setIdMethod != null)
            return setIdMethod;

        setIdMethod = getClassMethod(beanClz, true, "setId");

        if (setIdMethod != null) {
            setIdMethodCacheMap.put(beanClz, setIdMethod);
        }

        return setIdMethod;
    }


    public <T extends SubExecIntf, OUT> OUT execSubSelect(Class<T> subExecClazz, ExecuteContext executeContext, Class<OUT> returnTyp, Object param, Object attachParam) throws Exception {
        T subExecInstance = (T) this.getSubExecInstance(subExecClazz);
        return (OUT) subExecInstance.onSubSelect(executeContext, returnTyp, param, attachParam);
    }

    public <T extends SubExecIntf, OUT> OUT execSubSelectMore(Class<T> subExecClazz, ExecuteContext executeContext, Class<OUT> returnTyp, Object[] param, Object attachParam) throws Exception {
        T subExecInstance = (T) this.getSubExecInstance(subExecClazz);
        return (OUT) subExecInstance.onSubSelectMore(executeContext, returnTyp, param, attachParam);
    }
}
