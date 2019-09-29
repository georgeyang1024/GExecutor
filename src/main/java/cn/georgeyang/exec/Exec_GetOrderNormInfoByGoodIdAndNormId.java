package cn.georgeyang.exec;

import cn.georgeyang.apibean.GoodNormEarnApiBean;
import cn.georgeyang.executor.*;
import cn.georgeyang.mapper.RsGoodNormEntityMapper;
import cn.georgeyang.pojo.RsGoodNormEntity;
import cn.georgeyang.pojo.RsGoodNormEntityExample;
import cn.georgeyang.utils.Utils;

import java.util.List;

public class Exec_GetOrderNormInfoByGoodIdAndNormId extends NormSubExecImpl<Object, Integer, List<GoodNormEarnApiBean>> {

    @Override
    public List<GoodNormEarnApiBean> onSubSelect(ExecuteContext executeContext, Class<List<GoodNormEarnApiBean>> returnType, Integer param, Object attachParam) throws Exception {
        return null;
    }

    @Override
    public List<GoodNormEarnApiBean> onSubSelectMore(ExecuteContext executeContext, Class<List<GoodNormEarnApiBean>> returnType, Object[] param, Object attachParam) throws Exception {
        //多个参数查询
        Integer goodId = (Integer) param[0];
        Integer normId = (Integer) param[1];
        RsGoodNormEntityMapper rsGoodNormEntityMapper = webApplicationContext.getBean(RsGoodNormEntityMapper.class);
        RsGoodNormEntityExample example = new RsGoodNormEntityExample();
        example.createCriteria().andGoodIdEqualTo(goodId).andNormIdEqualTo(normId);
        List<RsGoodNormEntity> rsGoodNormList = rsGoodNormEntityMapper.selectByExample(example);
        if (Utils.isEmpty(rsGoodNormList))
            return null;
        return GExecutorService.tranToList(executeContext,GoodNormEarnApiBean.class,rsGoodNormList);
    }
}
