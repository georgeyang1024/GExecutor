package cn.georgeyang.controller;

import cn.georgeyang.apibean.OrderGoodApiBean;
import cn.georgeyang.apibean.RsOrderGoodApiBean;
import cn.georgeyang.bean.OutBean;
import cn.georgeyang.executor.GExecutorService;
import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.mapper.GoodEntityMapper;
import cn.georgeyang.mapper.OrderEntityMapper;
import cn.georgeyang.mapper.RsOrderGoodEntityMapper;
import cn.georgeyang.pojo.*;
import cn.georgeyang.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取订单关联的商品
 * 对比测试
 */
@Controller
@ResponseBody
@RequestMapping("/test5")
public class Test5Controller {
    @Resource
    OrderEntityMapper orderEntityMapper;
    @Autowired
    GExecutorService GExecutorService;
    @Autowired
    GoodEntityMapper goodEntityMapper;
    @Autowired
    RsOrderGoodEntityMapper rsOrderGoodEntityMapper;

    @ResponseBody
    @RequestMapping("/m1")
    public List<OrderGoodApiBean> m1(HttpServletRequest request) throws Exception{
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderGoodApiBean> retList = new ArrayList<>(list.size());
        for (OrderEntity orderEntity : list) {
            OrderGoodApiBean apiBean = new OrderGoodApiBean();
            BeanUtils.copyProperties(orderEntity,apiBean);

            //依赖关系
            RsOrderGoodEntityExample rsOrderGoodEntityExample = new RsOrderGoodEntityExample();
            rsOrderGoodEntityExample.createCriteria().andOrderIdEqualTo(orderEntity.getId());
            List<RsOrderGoodEntity> rsOrderGoodEntities = rsOrderGoodEntityMapper.selectByExample(rsOrderGoodEntityExample);
            if (Utils.isNotEmpty(rsOrderGoodEntities)) {
                List<RsOrderGoodApiBean> rsOrderGoodApiBeanList = new ArrayList<>(rsOrderGoodEntities.size());

                for (RsOrderGoodEntity rsOrderGoodEntity : rsOrderGoodEntities) {
                    RsOrderGoodApiBean rsOrderGoodApiBean = new RsOrderGoodApiBean();
                    BeanUtils.copyProperties(rsOrderGoodEntity,rsOrderGoodApiBean);
                    //商品信息
                    GoodEntityWithBLOBs goodInfo = goodEntityMapper.selectByPrimaryKey(rsOrderGoodApiBean.getGoodId());
                    rsOrderGoodApiBean.goodInfo = goodInfo;
                    rsOrderGoodApiBeanList.add(rsOrderGoodApiBean);
                }

                apiBean.relationShipList = rsOrderGoodApiBeanList;
            }

            retList.add(apiBean);
        }
        return retList;
    }

    @ResponseBody
    @RequestMapping("/d1")
    public List<OrderGoodApiBean> d1(HttpServletRequest request) throws Exception{
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        return GExecutorService.tranToList(OrderGoodApiBean.class,list);
    }

    @ResponseBody
    @RequestMapping("/d2")
    public OutBean d2(HttpServletRequest request) throws Exception{
        ExecuteContext executeContext = new ExecuteContext();
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderGoodApiBean> retList = GExecutorService.tranToList(executeContext,OrderGoodApiBean.class,list);
        return new OutBean(retList,executeContext.getDataMap());
    }


}
