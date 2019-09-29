package cn.georgeyang.apibean;

import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.GoodEntityWithBLOBs;
import cn.georgeyang.pojo.RsOrderGoodEntity;


public class RsOrderGoodApiBean extends RsOrderGoodEntity {
    @ExecutableField(bindFieldName = "goodId")
    public GoodEntityWithBLOBs goodInfo;

}
