package cn.georgeyang.executor;

import org.springframework.web.context.WebApplicationContext;

/**
 * 根據id，获取数据库实体，通用
 */
public class DefSubExecIntf implements SubExecIntf<Object,Object,Object> {
    protected WebApplicationContext webApplicationContext;
    protected GExecutorService GExecutorService;

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
        return GExecutorService.fetch(executeContext,returnType,param);
    }

    @Override
    public Object onSubSelectMore(ExecuteContext executeContext, Class<Object> returnType, Object[] param, Object attachParam) throws Exception {
        return null;
    }

    @Override
    public void finishSelect(ExecuteContext executeContext, ExecutableField executableField, Object tag, Object result) {

    }

    @Override
    public void onParentUpdate(Object parent) throws Exception {

    }

    @Override
    public void onParentDelete(Object parent) throws Exception {

    }
}
