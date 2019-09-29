package cn.georgeyang.pojo;

import cn.georgeyang.executor.ExecutableClass;
import cn.georgeyang.mapper.NormEntityMapper;
import cn.georgeyang.mapper.UserEntityMapper;

import java.util.Date;

@ExecutableClass(mapperClazz = UserEntityMapper.class,redisCacheSaveMinute = 1,dataKeyGroup = "userInfo")
public class UserEntity {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Boolean isDel;

    private String nickName;

    private String headImage;

    private String pwd;

    private Short stat;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage == null ? null : headImage.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Short getStat() {
        return stat;
    }

    public void setStat(Short stat) {
        this.stat = stat;
    }
}