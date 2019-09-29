package cn.georgeyang.pojo;

import cn.georgeyang.executor.ExecutableClass;
import cn.georgeyang.mapper.GoodEntityMapper;
import cn.georgeyang.mapper.OrderEntityMapper;

import java.util.Date;

@ExecutableClass(mapperClazz = GoodEntityMapper.class,redisCacheSaveMinute = 60)
public class GoodEntity {
    private Integer id;

    private Date craeteTime;

    private Date updateTime;

    private String goodName;

    private Short stat;

    private Integer publicUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCraeteTime() {
        return craeteTime;
    }

    public void setCraeteTime(Date craeteTime) {
        this.craeteTime = craeteTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }

    public Short getStat() {
        return stat;
    }

    public void setStat(Short stat) {
        this.stat = stat;
    }

    public Integer getPublicUserId() {
        return publicUserId;
    }

    public void setPublicUserId(Integer publicUserId) {
        this.publicUserId = publicUserId;
    }
}