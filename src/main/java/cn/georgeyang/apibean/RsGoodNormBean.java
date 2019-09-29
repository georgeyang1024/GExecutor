package cn.georgeyang.apibean;

import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.NormEntity;
import cn.georgeyang.pojo.RsGoodNormEntity;

public class RsGoodNormBean extends RsGoodNormEntity {
    @ExecutableField(bindFieldName = "normId")
    public NormEntity normEntity;
}
