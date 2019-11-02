package cn.georgeyang.executor;

import org.springframework.web.context.WebApplicationContext;


public abstract class NormSubExecImpl<P,IN,OUT> implements SubExecIntf<P,IN,OUT> {
    protected WebApplicationContext webApplicationContext;
    protected GExecutorService GExecutorService;
    private boolean isLifeCycleDoing;

    @Override
    public void init(WebApplicationContext context, GExecutorService service) {
        this.webApplicationContext = context;
        this.GExecutorService = service;
        isLifeCycleDoing = false;
    }

    @Override
    public boolean preStartSelect(ExecuteContext executeContext, ExecutableField executableField, Object tag) throws Exception {
        isLifeCycleDoing = true;
        return false;
    }

    @Override
    public void finishSelect(ExecuteContext executeContext, ExecutableField executableField, Object tag, OUT result) {
        isLifeCycleDoing = false;
    }

    @Override
    public void onParentUpdate(P parent) throws Exception {

    }

    @Override
    public void onParentDelete(P parent) throws Exception {

    }

    @Override
    public boolean isLifeCycleFinish() {
        return !isLifeCycleDoing;
    }
}
