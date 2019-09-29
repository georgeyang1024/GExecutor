package cn.georgeyang.exec;

import cn.georgeyang.executor.ExecuteContext;
import cn.georgeyang.executor.NormSubExecImpl;
import cn.georgeyang.mapper.RsOrderGoodEntityMapper;
import cn.georgeyang.pojo.RsOrderGoodEntity;
import cn.georgeyang.pojo.RsOrderGoodEntityExample;
import cn.georgeyang.utils.Utils;

import java.util.List;


/**
 * 重用
 *
 * @param <P>
 * @param <IN>
 * @param <OUT>
 */
public abstract class ExecBaseGoodNormEarn<P, IN, OUT> extends NormSubExecImpl<P,IN,OUT> {
    public List<RsOrderGoodEntity> getRsOrderGoodListCache( ExecuteContext executeContext, Integer rsGoodNormId) {
        if (executeContext == null)
            return null;
        List<RsOrderGoodEntity> list = (List<RsOrderGoodEntity>) executeContext.getContextCache(GExecutorService, List.class, rsGoodNormId);
        if (Utils.isNotEmpty(list))
            return list;
        RsOrderGoodEntityMapper rsOrderGoodEntityMapper = webApplicationContext.getBean(RsOrderGoodEntityMapper.class);
        RsOrderGoodEntityExample rsOrderGoodEntityExample = new RsOrderGoodEntityExample();
        rsOrderGoodEntityExample.createCriteria().andRsNormIdEqualTo(rsGoodNormId);
        List<RsOrderGoodEntity> rsOrderGoodList = rsOrderGoodEntityMapper.selectByExample(rsOrderGoodEntityExample);
        if (Utils.isNotEmpty(rsOrderGoodList)) {
            executeContext.putContextCache(GExecutorService,List.class,true,List.class,rsGoodNormId);
        }
        return rsOrderGoodList;
    }
}
