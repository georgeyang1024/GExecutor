package cn.georgeyang.pojo;

import cn.georgeyang.executor.ExecutableClass;
import cn.georgeyang.mapper.GoodEntityMapper;

@ExecutableClass(mapperClazz = GoodEntityMapper.class,redisCacheSaveMinute = 60,dataKeyGroup = "goodInfoTest")
public class GoodEntityWithBLOBs extends GoodEntity {
    private String images;

    private String content;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}