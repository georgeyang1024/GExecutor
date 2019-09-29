package cn.georgeyang.apibean;

import cn.georgeyang.exec.Exec_GetOrderGoodNameListByOrderId;
import cn.georgeyang.exec.Exec_GetRsOrderGoodListByOrderId;
import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.OrderEntity;

import java.util.List;

public class OrderGoodNameListApiBean extends OrderEntity {
    @ExecutableField(bindFieldName = "id",executeImpl = Exec_GetOrderGoodNameListByOrderId.class,forceDataInner = true)
    public List<String> goodNameList;
}
