package cn.georgeyang.apibean;

import cn.georgeyang.exec.Exec_GetOrderTotalPayPriceByOrderId;
import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.OrderEntity;
import cn.georgeyang.pojo.UserEntity;

public class OrderPayPriceApiBean extends OrderEntity {
    @ExecutableField(bindFieldName = "id",executeImpl = Exec_GetOrderTotalPayPriceByOrderId.class)
    public Double userTotalPayPrice;
}
