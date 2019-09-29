package cn.georgeyang.executor;

import org.springframework.web.context.WebApplicationContext;

/**
 * 自定义（重写）mapper的默认执行方法
 * @param <IdType>
 * @param <DataType>
 */
public interface CoustmerMapperExec<MapperType,IdType,DataType> {
    DataType onMapperSelect(WebApplicationContext context, MapperType mapper, IdType param) throws Exception;

    Integer onMapperInsert(WebApplicationContext context, MapperType mapper, DataType param) throws Exception;

    Integer onMapperUpdate(WebApplicationContext context, MapperType mapper, DataType param) throws Exception;

    Integer onMapperDelete(WebApplicationContext context, MapperType mapper, DataType param) throws Exception;
}
