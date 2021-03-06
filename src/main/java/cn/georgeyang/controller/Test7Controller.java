package cn.georgeyang.controller;

import cn.georgeyang.apibean.OrderInfoApiBean;
import cn.georgeyang.apibean.RsOrderGoodApiBean;
import cn.georgeyang.bean.OutBean;
import cn.georgeyang.executor.GExecutorService;
import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.mapper.GoodEntityMapper;
import cn.georgeyang.mapper.OrderEntityMapper;
import cn.georgeyang.mapper.RsOrderGoodEntityMapper;
import cn.georgeyang.mapper.UserEntityMapper;
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
 * 获取订单全部信息接口
 * 对比测试
 */
@Controller
@ResponseBody
@RequestMapping("/test7")
public class Test7Controller {
    @Resource
    UserEntityMapper userEntityMapper;
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
    public List<OrderInfoApiBean> m1(HttpServletRequest request) throws Exception{
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderInfoApiBean> retList = new ArrayList<>(list.size());
        for (OrderEntity orderEntity : list) {
            OrderInfoApiBean apiBean = new OrderInfoApiBean();
            BeanUtils.copyProperties(orderEntity,apiBean);

            //用户资料
            UserEntity userEntity = userEntityMapper.selectByPrimaryKey(orderEntity.getUserId());
            apiBean.userEntity = userEntity;

            //依赖关系
            RsOrderGoodEntityExample rsOrderGoodEntityExample = new RsOrderGoodEntityExample();
            rsOrderGoodEntityExample.createCriteria().andOrderIdEqualTo(orderEntity.getId());
            List<RsOrderGoodEntity> rsOrderGoodEntities = rsOrderGoodEntityMapper.selectByExample(rsOrderGoodEntityExample);

            if (Utils.isNotEmpty(rsOrderGoodEntities)) {
                Double payPrice = 0D;
                List<RsOrderGoodApiBean> rsOrderGoodApiBeanList = new ArrayList<>();

                for (RsOrderGoodEntity rsOrderGoodEntity : rsOrderGoodEntities) {
                    //价格
                    payPrice += rsOrderGoodEntity.getPayPrice();

                    RsOrderGoodApiBean rsOrderGoodApiBean = new RsOrderGoodApiBean();
                    BeanUtils.copyProperties(rsOrderGoodEntity,rsOrderGoodApiBean);
                    //商品信息
                    GoodEntityWithBLOBs goodInfo = goodEntityMapper.selectByPrimaryKey(rsOrderGoodEntity.getGoodId());
                    rsOrderGoodApiBean.goodInfo = goodInfo;
                    rsOrderGoodApiBeanList.add(rsOrderGoodApiBean);
                }

                apiBean.relationShipList = rsOrderGoodApiBeanList;
                apiBean.userTotalPayPrice = payPrice;
            }

            retList.add(apiBean);
        }
        return retList;
    }

    @ResponseBody
    @RequestMapping("/d1")
    public List<OrderInfoApiBean> d1(HttpServletRequest request) throws Exception{
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderInfoApiBean> retList = GExecutorService.tranToList(OrderInfoApiBean.class,list);
        return retList;
    }

    @ResponseBody
    @RequestMapping("/d2")
    public OutBean d2(HttpServletRequest request) throws Exception{
        ExecuteContext executeContext = new ExecuteContext();
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderInfoApiBean> retList = GExecutorService.tranToList(executeContext, OrderInfoApiBean.class,list);
        return new OutBean(retList,executeContext.getDataMap());
    }


}
