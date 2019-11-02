package cn.georgeyang.executor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 一个字段id对应到另外一个字段的详细属性
 * 一对一关系
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface ExecutableField {
    String bindFieldName() default  "id";//和那个当前类的字段关联对应？（用它的id查询详细内容，用于管理查询）,如果空字符串，表示整个实体传进去
    String[] bindFieldList() default {};//多个关联字段查询时，用到
    boolean isIdField() default false;//指定这个字段是id(主键索引字段)，过滤查询该字段的内容
    Class<? extends SubExecIntf> executeImpl() default DefSubExecIntf.class;//自定义的执行方法实现
    boolean forceDataInner() default false;//强制字段数据查询结果为实体内置数据，如果设置true，该字段的值，就会赋值到字段，而不是用context的自动放到缓存数据
}


