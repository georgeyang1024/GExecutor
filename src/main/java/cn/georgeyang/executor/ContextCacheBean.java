package cn.georgeyang.executor;

public class ContextCacheBean<T> {
    //表示對應的mapper，如果沒有對應mapper，則是實體的class
    Class holderClazz;//如果实体注解了mapper，先找到mapper的class，如果没有，是实体的class

    ExecutableClass executableClazz;//ExecutableClass注解
    Object key;//如果不是clz不是注解的mapper，那么它是clz下的字段的字段名

    @Deprecated
    boolean isInnerValue;//实体的内部值，如果該值為false，表面他是另外一個數據庫表的數據，獨立于實體的值

    boolean cacheOnly = false;//僅供内部緩存，不做數據輸出

    T value;


    public String getDataGroupName() {
        return getDataGroupName(executableClazz,holderClazz);
    }

    protected static String getDataGroupName(ExecutableClass executableClass,Class holderClazz) {
        if (executableClass == null)
            return holderClazz.getSimpleName();
        String key = executableClass.dataKeyGroup();
        if (key == null || key.isEmpty())
            return holderClazz.getSimpleName();
        return key;
    }

    //獲取這個class，它這組數據存放的key
    protected static String getDataGroupName(GExecutorService GExecutorService, Class tagClass) {
        ExecutableClass executableClass = GExecutorService.getExecutableClassFromClazz(tagClass);
        if (executableClass != null) {
            tagClass = executableClass.mapperClazz();
        }
        return getDataGroupName(executableClass,tagClass);
    }
}
