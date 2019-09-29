package cn.georgeyang.exec;

import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.executor.NormSubExecImpl;
import cn.georgeyang.pojo.UserEntity;


public class Exec_IsUserEntitySelf  extends NormSubExecImpl<Object, Integer, Boolean> {
    @Override
    public boolean preStartSelect(ExecuteContext executeContext, ExecutableField executableField, Object tag) throws Exception {
        if (tag == null)
            return true;
        if (!(tag instanceof UserEntity))
            return false;
        return super.preStartSelect(executeContext, executableField, tag);
    }

    @Override
    public Boolean onSubSelect(ExecuteContext executeContext, Class<Boolean> returnType, Integer param, Object attachParam) throws Exception {
        if (!(attachParam instanceof Integer))
            return null;
        if (!(param instanceof Integer))
            return null;
        return param == attachParam;
    }

    @Override
    public Boolean onSubSelectMore(ExecuteContext executeContext, Class<Boolean> returnType, Object[] param, Object attachParam) throws Exception {
        return null;
    }
}
