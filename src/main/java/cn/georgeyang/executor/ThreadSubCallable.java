package cn.georgeyang.executor;

import java.util.concurrent.Callable;

public class ThreadSubCallable<T> implements Callable<T> {
    private GExecutorService GExecutorService;
    private Class<T> clazz;
    private Object dataId;
    private ExecuteContext context;

    public ThreadSubCallable(GExecutorService GExecutorService, ExecuteContext context, Class<T> clazz, Object dataId) {
        this.GExecutorService = GExecutorService;
        this.context = context;
        this.clazz = clazz;
        this.dataId = dataId;
    }

    public ThreadSubCallable(GExecutorService GExecutorService, Class<T> clazz, Object dataId) {
        this.GExecutorService = GExecutorService;
        this.clazz = clazz;
        this.dataId = dataId;
    }

    @Override
    public T call() throws Exception {
        return GExecutorService.fetch(context,clazz,dataId);
    }
}
