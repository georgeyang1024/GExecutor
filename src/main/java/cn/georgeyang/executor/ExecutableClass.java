package cn.georgeyang.executor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
public @interface ExecutableClass {
    Class mapperClazz();//项目bean的class，一般是数据库的mapper，也可以是service(搭配selectFieldName，自定义搜索数据)
    String dataKeyGroup() default "";//一批数据组成一个列表时，对应的key名(默认是Entity的全名，去除entity字眼)
    String selectFieldName() default "selectByPrimaryKey";//默认是mapper的根据id查询的方法名
    String insertFieldName() default "insertSelective";//默认是mapper的插入方法名
    String updateFieldName() default "updateByPrimaryKeySelective";//默认的mapper的更新方法名
    String deleteFieldName() default "deleteByPrimaryKey";//默认的mapper的更新方法名，
    int redisCacheSaveMinute() default 10;//在redis服务器上保留多少分钟
    boolean flushCache() default true;//更新数据时，删除redis缓存，否则只更新redis的缓存
    Class<? extends CoustmerMapperExec> mapperExecer() default CoustmerMapperExec.class;
}


