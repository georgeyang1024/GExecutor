package cn.georgeyang.pojo;

import cn.georgeyang.executor.ExecutableClass;
import cn.georgeyang.mapper.NormEntityMapper;
import cn.georgeyang.mapper.OrderEntityMapper;

import java.util.Date;

@ExecutableClass(mapperClazz = NormEntityMapper.class,redisCacheSaveMinute = 60)
public class NormEntity {
    private Integer id;

    private Date craeteTime;

    private Date updateTime;

    private Boolean isDel;

    private String name;

    private String unit;

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

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }
}