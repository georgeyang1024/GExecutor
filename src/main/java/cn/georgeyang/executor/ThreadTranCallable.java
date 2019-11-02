package cn.georgeyang.executor;

import java.util.concurrent.Callable;

public class ThreadTranCallable<T> implements Callable<T> {
    private GExecutorService executorService;
    private Class<T> outClazz;
    private Object sourceData;
    private ExecuteContext context;
    private Object attachParam;

    public ThreadTranCallable(GExecutorService executorService, ExecuteContext context, Class<T> clazz, Object sourceData,Object attachParam) {
        this.executorService = executorService;
        this.context = context;
        this.outClazz = clazz;
        this.sourceData = sourceData;
        this.attachParam = attachParam;
    }

    @Override
    public T call() throws Exception {
        return executorService.tranTo(context,sourceData,outClazz,attachParam);
    }
}
