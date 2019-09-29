package cn.georgeyang.executor;

import java.util.concurrent.Callable;

public class ThreadBeanCallable implements Callable<ExecutorBean> {
    private GExecutorService GExecutorService;
    private ExecutorBean bean;

    public ThreadBeanCallable(GExecutorService GExecutorService, ExecutorBean bean) {
        this.GExecutorService = GExecutorService;
        this.bean = bean;
    }
    @Override
    public ExecutorBean call() throws Exception {
        try {
            bean.result = this.GExecutorService.selectFieldValue(bean.context,bean.tag,bean.field,bean.attachParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
