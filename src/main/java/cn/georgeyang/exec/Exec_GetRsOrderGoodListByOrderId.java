package cn.georgeyang.exec;

import cn.georgeyang.apibean.RsOrderGoodApiBean;
import cn.georgeyang.executor.*;
import cn.georgeyang.mapper.RsOrderGoodEntityMapper;
import cn.georgeyang.pojo.RsOrderGoodEntity;
import cn.georgeyang.pojo.RsOrderGoodEntityExample;

import java.util.List;

/**
 * 根据订单id，获取订单的商品依赖数据
 */
public class Exec_GetRsOrderGoodListByOrderId extends NormSubExecImpl<Object, Long, List<RsOrderGoodApiBean>> {

    @Override
    public List<RsOrderGoodApiBean> onSubSelect(ExecuteContext executeContext, Class<List<RsOrderGoodApiBean>> returnType, Long orderId, Object attachParam) throws Exception {
        RsOrderGoodEntityMapper rsOrderGoodEntityMapper = webApplicationContext.getBean(RsOrderGoodEntityMapper.class);
        RsOrderGoodEntityExample rsOrderGoodEntityExample = new RsOrderGoodEntityExample();
        rsOrderGoodEntityExample.createCriteria().andOrderIdEqualTo(orderId);
        List<RsOrderGoodEntity> rsOrderGoodEntities = rsOrderGoodEntityMapper.selectByExample(rsOrderGoodEntityExample);
        return GExecutorService.tranToList(executeContext,RsOrderGoodApiBean.class,rsOrderGoodEntities);
    }

    @Override
    public List<RsOrderGoodApiBean> onSubSelectMore(ExecuteContext executeContext, Class<List<RsOrderGoodApiBean>> returnType, Object[] param, Object attachParam) throws Exception {
        return null;
    }
}
