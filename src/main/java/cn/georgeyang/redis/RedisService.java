package cn.georgeyang.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
/** 
 * redis缓存接口。 
 * 
 * 缓存时间单位默认分钟， 
 * 默认timeout值为5， 
 * 没有timeout和timeunit的方法默认不设置有效时间，永久有效。 
 */  
public interface RedisService {
  
    <T> void put(String key, T obj);  
    <T> void put(String key, T obj, int timeout);  
    <T> void put(String key, T obj, int timeout, TimeUnit unit);  
  
    <T> T get(String key, Class<T> cls);  
  
    <T> List<T> getList(String key, Class<T> cls);
  
    boolean exists(String key);  
  
    void delete(String key);  
  
    boolean expire(String key, long timeout, TimeUnit timeUnit);  
    boolean expire(String key, long timeout);  
      
    void put(String key, String value);  
    void put(String key, String value, int timeout);  
    void put(String key, String value, int timeout, TimeUnit unit);  
  
    String get(String key);

    RedisTemplate getRedisTemplate();
}
