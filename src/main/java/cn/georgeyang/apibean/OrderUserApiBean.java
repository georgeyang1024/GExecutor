package cn.georgeyang.apibean;

import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.OrderEntity;
import cn.georgeyang.pojo.UserEntity;

public class OrderUserApiBean extends OrderEntity {
    @ExecutableField(bindFieldName = "userId")
    public UserEntity userEntity;
}
