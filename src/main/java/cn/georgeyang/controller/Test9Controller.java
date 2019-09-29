package cn.georgeyang.controller;

import cn.georgeyang.apibean.*;
import cn.georgeyang.bean.OutBean;
import cn.georgeyang.executor.GExecutorService;
import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.mapper.*;
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
 * 详情的订单信息，规格
 * 对比测试
 */
@Controller
@ResponseBody
@RequestMapping("/test9")
public class Test9Controller {
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
    @Autowired
    RsGoodNormEntityMapper rsGoodNormEntityMapper;
    @Resource
    NormEntityMapper normEntityMapper;

    @ResponseBody
    @RequestMapping("/m1")
    public List<OrderFullInfoApiBean> m1(HttpServletRequest request) throws Exception{
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderFullInfoApiBean> retList = new ArrayList<>(list.size());
        for (OrderEntity orderEntity : list) {
            OrderFullInfoApiBean apiBean = new OrderFullInfoApiBean();
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
                List<RsOrderGoodNormApiBean> rsOrderGoodApiBeanList = new ArrayList<>();

                for (RsOrderGoodEntity rsOrderGoodEntity : rsOrderGoodEntities) {
                    //价格
                    payPrice += rsOrderGoodEntity.getPayPrice();

                    RsOrderGoodNormApiBean rsOrderGoodApiBean = new RsOrderGoodNormApiBean();
                    BeanUtils.copyProperties(rsOrderGoodEntity,rsOrderGoodApiBean);
                    //商品信息
                    GoodEntityWithBLOBs goodInfo = goodEntityMapper.selectByPrimaryKey(rsOrderGoodEntity.getGoodId());
                    rsOrderGoodApiBean.goodInfo = goodInfo;
                    //规格信息
                    RsGoodNormEntity rsGoodNormEntity = rsGoodNormEntityMapper.selectByPrimaryKey(rsOrderGoodEntity.getRsNormId());
                    RsGoodNormBean rsGoodNormBean = new RsGoodNormBean();
                    BeanUtils.copyProperties(rsGoodNormEntity,rsGoodNormBean);
                    rsGoodNormBean.normEntity = normEntityMapper.selectByPrimaryKey(rsGoodNormEntity.getNormId());
                    rsOrderGoodApiBean.rsGoodNormBean = rsGoodNormBean;

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
    public List<OrderFullInfoApiBean> d1(HttpServletRequest request) throws Exception{
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderFullInfoApiBean> retList = GExecutorService.tranToList(OrderFullInfoApiBean.class,list);
        return retList;
    }

    @ResponseBody
    @RequestMapping("/d2")
    public OutBean d2(HttpServletRequest request) throws Exception{
        ExecuteContext executeContext = new ExecuteContext();
        OrderEntityExample example = new OrderEntityExample();
        List<OrderEntity> list = orderEntityMapper.selectByExample(example);
        List<OrderFullInfoApiBean> retList = GExecutorService.tranToList(executeContext, OrderFullInfoApiBean.class,list);
        return new OutBean(retList,executeContext.getDataMap());
    }


}
