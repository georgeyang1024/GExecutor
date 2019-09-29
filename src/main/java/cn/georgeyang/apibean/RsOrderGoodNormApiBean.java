package cn.georgeyang.apibean;

import cn.georgeyang.exec.Exec_GetOrderNormInfoByGoodIdAndNormId;
import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.RsGoodNormEntity;

/**
 * 多层嵌套、多字段查询
 */
public class RsOrderGoodNormApiBean extends RsOrderGoodApiBean {

    @ExecutableField(bindFieldName = "rsNormId")
    public RsGoodNormBean rsGoodNormBean;
}
