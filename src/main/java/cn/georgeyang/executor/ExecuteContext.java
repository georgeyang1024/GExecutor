package cn.georgeyang.executor;

import java.lang.reflect.Field;
import java.util.*;

public class ExecuteContext {
    private List<ContextCacheBean> contextCacheList = new LinkedList<>();

    public Object getContextCache(GExecutorService GExecutorService, Class entityClz, Object key) {
        if (entityClz == null || key == null || contextCacheList == null || contextCacheList.size() == 0)
            return null;
        synchronized (this) {
            ExecutableClass resultExtClazz = GExecutorService.getExecutableClassFromClazz(entityClz);
            Class findClz = resultExtClazz == null ? entityClz : resultExtClazz.mapperClazz();
            for (ContextCacheBean cache : contextCacheList) {
                if (cache.isInnerValue)
                    continue;
                if (cache.holderClazz == findClz) {
                    boolean isRight = false;
                    if (key instanceof String) {//字符串鍵，的匹配
                        isRight = key.equals(cache.key);
                    } else if (cache.key == key) {
                        isRight = true;
                    }
                    if (isRight) {
                        return cache.value;
                    }
                }
            }
        }
        return null;
    }

    //自定義緩存數據時，設置push緩存數據
    public void putContextCache(GExecutorService GExecutorService, Class entityClz, boolean cacheOnly, Object key, Object value) {
        ExecutableClass resultExtClazz = GExecutorService.getExecutableClassFromClazz(entityClz);
        Class holderClazz = resultExtClazz == null ? entityClz : resultExtClazz.mapperClazz();
        putToCacheReal(holderClazz, resultExtClazz, key, false, cacheOnly, value);
    }

    //這個數據是不是内部嵌套的數據(非表字段外的數據)
    private boolean isInnerObj(Object bean) {
        if (bean instanceof List)
            return true;
        if (bean instanceof Map)
            return true;
        Class clz = bean.getClass();
        if (clz == Integer.class)
            return true;
        if (clz == Boolean.class)
            return true;
        if (clz == Byte.class)
            return true;
        if (clz == Character.class)
            return true;
        if (clz == Short.class)
            return true;
        if (clz == Long.class)
            return true;
        if (clz == Float.class)
            return true;
        if (clz == Double.class)
            return true;
        if (clz == String.class)
            return true;
        return clz.isPrimitive();
    }

    protected boolean putToCache(GExecutorService GExecutorService, ExecutorBean resultBean) {
        if (resultBean == null)
            return false;
        if (resultBean.result == null)
            return false;
        return putToCache(GExecutorService, null, resultBean.tag.getClass(), resultBean.field, resultBean.result);
    }

    /**
     * 寫入緩存
     *
     * @param GExecutorService
     * @param parentClz         目標class
     * @param executeField      目標字段
     * @param result            執行結果(包含id索引,用id作爲key)
     * @return 返回true表示result数据放入context成功，不再往实体的对应字段赋值了
     */
    protected boolean putToCache(GExecutorService GExecutorService, Object parentValue, Class parentClz, Field executeField, Object result) {
        if (result == null)
            return false;
        if (result instanceof List) {
            List<Object> newObj = new ArrayList<>();
            List list = (List) result;
            for (Object object : list) {
                Object objKey = GExecutorService.tryGetBeanId(object);
                if (objKey == null) {
                    newObj.add(object);
                    continue;
                }
                newObj.add(objKey);

                this.putToCache(GExecutorService, parentValue, parentClz, executeField, object);
            }

            result = newObj;
        } else if (result instanceof Map) {
            Map<Object, Object> newObj = new HashMap<>();
            Map map = (Map) result;
            for (Object mKey : map.keySet()) {
                Object mValue = map.get(mKey);
                Object objKey = GExecutorService.tryGetBeanId(mValue);
                //不能转成新的结果，抽不出来的数据
                boolean canTranNewData = mKey == objKey || (mKey instanceof String && mKey.equals(String.valueOf(objKey)));
                if (!canTranNewData) {
                    newObj.put(mKey, mValue);
                    continue;
                }

                String cKey = ContextCacheBean.getDataGroupName(GExecutorService, mValue.getClass());
                newObj.put(mKey, cKey);

                this.putToCache(GExecutorService, parentValue, parentClz, executeField, mValue);
            }
            result = newObj;
        }

        ExecutableClass resultExtClazz = null;
        if (isInnerObj(result)) {
//            resultExtClazz = dbExecutorService.getExecutableClassFromClazz(parentClz);
//            Class clz = resultExtClazz == null ? parentClz : resultExtClazz.mapperClazz();
//            Object key = executeField == null ? result.getClass() : executeField.getName();
//            putToCacheReal(clz,resultExtClazz,key,true,result);
            return false;
        } else {
            ExecutableField executableField = executeField.getAnnotation(ExecutableField.class);
            if (executableField != null) {
                if (executableField.forceDataInner()) {
                    return false;
                }
            }
        }

        //放入缓存，返回true告诉调用者，放入缓存成功，result不要再赋值到bean的字段上了
        resultExtClazz = GExecutorService.getExecutableClassFromClazz(result.getClass());
        Class clz = resultExtClazz == null ? result.getClass() : resultExtClazz.mapperClazz();
        Object key = GExecutorService.tryGetBeanId(result);
        if (key == null && parentValue != null) {
            key = GExecutorService.tryGetBeanId(parentValue);
        }
        putToCacheReal(clz, resultExtClazz, key, false, result);
        return true;
    }

    protected void putToCacheReal(Class holderClazz, ExecutableClass executableClass, Object key, boolean isInner, Object value) {
        this.putToCacheReal(holderClazz, executableClass, key, isInner, false, value);
    }

    protected void putToCacheReal(Class holderClazz, ExecutableClass executableClass, Object key, boolean isInner, boolean cacheOnly, Object value) {
        synchronized (this) {
            ContextCacheBean cacheBean = new ContextCacheBean();
            cacheBean.holderClazz = holderClazz;
            cacheBean.executableClazz = executableClass;
            cacheBean.key = key;
            cacheBean.isInnerValue = isInner;
            cacheBean.cacheOnly = cacheOnly;
            cacheBean.value = value;
            contextCacheList.add(cacheBean);
        }
    }


    public Map<String, Map<String, Object>> getOutMap(boolean retData) {
        Map<String, Map<String, Object>> resultMap = new HashMap<>();
        synchronized (this) {
            for (ContextCacheBean cache : contextCacheList) {
                if (cache.cacheOnly)
                    continue;//忽略僅作換成的數據的輸出
                if (cache.isInnerValue == retData)
                    continue;
                String clzz = cache.getDataGroupName();
                Map<String, Object> dataMap = resultMap.get(clzz);
                if (dataMap == null) {
                    dataMap = new HashMap<>();
                    resultMap.put(clzz, dataMap);
                }
                String key = String.valueOf(cache.key);
                dataMap.put(key, cache.value);
            }
        }
        return resultMap;
    }

    /**
     * 這個方法，用於輸出json數據，注意不要改名
     * 获取完数据后，获取非实体嵌套的数据
     *
     * @return
     */
    public Map<String, Map<String, Object>> getDataMap() {
        return getOutMap(true);
    }

}
