package cn.georgeyang.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component("redisService")  
public class RedisServiceImpl implements RedisService {
	private StringRedisTemplate redisTemplate;  
	  
	
	
    public RedisServiceImpl() {
		super();
		System.out.println("构造器");
	}

	@Autowired  
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {  
        this.redisTemplate = redisTemplate; 
        redisTemplate.getConnectionFactory().getConnection();
        System.out.println(redisTemplate);
        System.out.println(redisTemplate.getConnectionFactory().getConnection());
    }


  
    public <T> void put(String key, T obj) {
        put(key,obj,0,null);
    }  
  
    public <T> void put(String key, T obj, int timeout) {  
        put(key, obj, timeout, TimeUnit.MINUTES);  
    }  
  
    public <T> void put(String key, T obj, int timeout, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key, JSON.toJSONString(obj));
            if (timeout > 0) {
                redisTemplate.expire(key, timeout, unit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
      
    public <T> T get(String key, Class<T> cls) {
        try {
            String json = redisTemplate.boundValueOps(key).get();
            return (T) JSON.parseObject(json,cls);
        } catch (Throwable e) {
            return null;
        }
    }

    @Override
    public <T> List<T> getList(String key, Class<T> cls) {
        try {
            String json = redisTemplate.boundValueOps(key).get();
            return JSON.parseArray(json,cls);
        } catch (Throwable e) {
            return null;
        }
    }

    public boolean exists(String key) {  
        return redisTemplate.hasKey(key);  
    }  
  
    public void delete(String key) {  
        redisTemplate.delete(key);  
    }  
  
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {  
        return redisTemplate.expire(key, timeout, timeUnit);  
    }  
      
    public boolean expire(String key, long timeout) {  
        return expire(key, timeout, TimeUnit.MINUTES);  
    }  
      
    public void put(String key, String value) {  
        redisTemplate.opsForValue().set(key, value);  
    }  
  
    public void put(String key, String value, int timeout) {  
        put(key, value, timeout, TimeUnit.MINUTES);  
    }  
  
    public void put(String key, String value, int timeout, TimeUnit unit) {  
        redisTemplate.opsForValue().set(key, value, timeout, unit);  
    }  
  
      
    public String get(String key) {  
        return redisTemplate.opsForValue().get(key);  
    }

    @Override
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }
}
