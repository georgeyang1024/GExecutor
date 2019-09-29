package cn.georgeyang.apibean;

import cn.georgeyang.exec.Exec_GetUserBuyGoodIdListByUId;
import cn.georgeyang.executor.ExecutableField;
import cn.georgeyang.pojo.GoodEntityWithBLOBs;
import cn.georgeyang.pojo.UserEntity;

import java.util.List;

public class UserBuyGoodListApiBean extends UserEntity {

    public List<GoodEntityWithBLOBs> buyGoodList;

    @ExecutableField(executeImpl = Exec_GetUserBuyGoodIdListByUId.class)
    public List<Integer> buyGoodIdList;

}
