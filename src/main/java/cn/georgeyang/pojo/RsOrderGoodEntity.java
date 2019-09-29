package cn.georgeyang.pojo;

import java.util.Date;

public class RsOrderGoodEntity {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Long orderId;

    private Integer goodId;

    private Integer rsNormId;

    private Integer buySum;

    private Double payPrice;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getRsNormId() {
        return rsNormId;
    }

    public void setRsNormId(Integer rsNormId) {
        this.rsNormId = rsNormId;
    }

    public Integer getBuySum() {
        return buySum;
    }

    public void setBuySum(Integer buySum) {
        this.buySum = buySum;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }
}