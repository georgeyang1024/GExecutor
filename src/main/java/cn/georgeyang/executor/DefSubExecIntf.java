package cn.georgeyang.executor;

import org.springframework.web.context.WebApplicationContext;

/**
 * 根據id，获取数据库实体，通用
 */
public class DefSubExecIntf implements SubExecIntf<Object,Object,Object> {
    protected WebApplicationContext webApplicationContext;
    protected GExecutorService GExecutorService;
    private boolean isLifeCycleDoing;

    @Override
    public void init(WebApplicationContext context, GExecutorService service) {
        this.webApplicationContext = context;
        this.GExecutorService = service;
    }

    @Override
    public boolean preStartSelect(ExecuteContext executeContext, ExecutableField executableField, Object tag) throws Exception {
        return false;
    }

    @Override
    public Object onSubSelect(ExecuteContext executeContext, Class<Object> returnType, Object param, Object attachParam) throws Exception {
        isLifeCycleDoing = true;
        return GExecutorService.fetch(executeContext,returnType,param);
    }

    @Override
    public Object onSubSelectMore(ExecuteContext executeContext, Class<Object> returnType, Object[] param, Object attachParam) throws Exception {
        isLifeCycleDoing = true;
        return null;
    }

    @Override
    public void finishSelect(ExecuteContext executeContext, ExecutableField executableField, Object tag, Object result) {
        isLifeCycleDoing = false;
    }

    @Override
    public void onParentUpdate(Object parent) throws Exception {

    }

    @Override
    public void onParentDelete(Object parent) throws Exception {

    }

    @Override
    public boolean isLifeCycleFinish() {
        return !isLifeCycleDoing;
    }
}
