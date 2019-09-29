package cn.georgeyang.executor;

import org.springframework.web.context.WebApplicationContext;

/**
 * 关联查询修改删除操作
 */
public interface SubExecIntf<P,IN,OUT> {
    //初始化
    void init(WebApplicationContext context, GExecutorService service);

    //准备开始查找数据，返回true拦截(跳过)查找
    boolean preStartSelect(ExecuteContext executeContext,ExecutableField executableField,Object tag) throws Exception;

    OUT onSubSelect(ExecuteContext executeContext,Class<OUT> returnType,IN param,Object attachParam) throws Exception;
    OUT onSubSelectMore(ExecuteContext executeContext,Class<OUT> returnType,Object[] param,Object attachParam) throws Exception;

    //一次执行完成
    void finishSelect(ExecuteContext executeContext,ExecutableField executableField,Object tag,OUT result);

    void onParentUpdate(P parent) throws Exception;
    void onParentDelete(P parent) throws Exception;
}
