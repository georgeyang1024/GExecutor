package cn.georgeyang.apibean;

import cn.georgeyang.exec.Exec_IsUserEntitySelf;
import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.UserEntity;

public class UserEntityApiBean extends UserEntity {
    @ExecutableField(executeImpl = Exec_IsUserEntitySelf.class)
    public Boolean isSelf;
}
