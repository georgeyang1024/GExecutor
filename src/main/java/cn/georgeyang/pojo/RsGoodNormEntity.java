package cn.georgeyang.pojo;

import cn.georgeyang.executor.ExecutableClass;
import cn.georgeyang.mapper.RsGoodNormEntityMapper;

import java.util.Date;

@ExecutableClass(mapperClazz = RsGoodNormEntityMapper.class,redisCacheSaveMinute = 60)
public class RsGoodNormEntity {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Boolean isDel;

    private Integer goodId;

    private Integer normId;

    private Integer stock;

    private Double sellPrice;

    private Double costPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getNormId() {
        return normId;
    }

    public void setNormId(Integer normId) {
        this.normId = normId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }
}