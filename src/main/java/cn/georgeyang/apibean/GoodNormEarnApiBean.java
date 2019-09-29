package cn.georgeyang.apibean;

import cn.georgeyang.exec.Exec_GetGoodNormOrderCount;
import cn.georgeyang.exec.Exec_GetGoodNormTotalCostPrice;
import cn.georgeyang.exec.Exec_GetGoodNormTotalEarnPrice;
import cn.georgeyang.exec.Exec_GetGoodNormTotalPayPrice;
import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.GoodEntityWithBLOBs;
import cn.georgeyang.pojo.NormEntity;
import cn.georgeyang.pojo.RsGoodNormEntity;

public class GoodNormEarnApiBean extends RsGoodNormEntity {
    @ExecutableField(executeImpl = Exec_GetGoodNormOrderCount.class)
    public Integer orderCount;//订单数量

    @ExecutableField(executeImpl = Exec_GetGoodNormTotalCostPrice.class)
    public Double totalCostPrice = 0D;

    @ExecutableField(executeImpl = Exec_GetGoodNormTotalPayPrice.class)
    public Double totalPayPrice = 0D;

    @ExecutableField(executeImpl = Exec_GetGoodNormTotalEarnPrice.class)
    public Double totalEarnPrice = 0D;

    @ExecutableField(bindFieldName = "goodId")
    public GoodEntityWithBLOBs goodInfo;

    @ExecutableField(bindFieldName = "normId")
    public NormEntity normInfo;
}
