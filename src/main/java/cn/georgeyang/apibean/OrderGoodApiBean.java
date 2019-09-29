package cn.georgeyang.apibean;

import cn.georgeyang.exec.Exec_GetRsOrderGoodListByOrderId;
import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.OrderEntity;

import java.util.List;

public class OrderGoodApiBean extends OrderEntity {
    @ExecutableField(bindFieldName = "id",executeImpl = Exec_GetRsOrderGoodListByOrderId.class,forceDataInner = true)
    public List<RsOrderGoodApiBean> relationShipList;
}
