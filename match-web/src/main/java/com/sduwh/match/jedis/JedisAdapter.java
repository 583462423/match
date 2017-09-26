package com.sduwh.match.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-8-28.
 * 内存数据库
 */
@Service
public class JedisAdapter implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    JedisPool jedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool("redis://localhost:6379");
    }

    /** 向set中添加数据*/
    public long sadd(String key,String ...value){
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("redis添加数据失败");
        }
        return -1;
    }

    /** 返回集合人数*/
    public long scard(String key){
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.scard(key);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return 0;
    }

    public Set<String> sget(String key){
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.smembers(key);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }


    public void sadd(String key, String value){
        try(Jedis jedis = jedisPool.getResource()){
            jedis.sadd(key,value);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /** 集合remove*/
    public void srem(String key,String value){
        try(Jedis jedis = jedisPool.getResource()){
            jedis.srem(key,value);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }



    /** 查看是否包含在该集合中*/
    public boolean sismember(String key, String value){
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }


    /** 添加事件到阻塞队列中去*/
    public void addEvent(String event){
        String key = RedisKeyGenerator.getEventKey();
        try(Jedis jedis = jedisPool.getResource()){
            jedis.lpush(key,event);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    public List<String> getEvent(){
        String key = RedisKeyGenerator.getEventKey();
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.brpop(0,key)
                    .stream()
                    .filter(e->!e.equals(key))
                    .collect(Collectors.toList());
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    /** 有序集合添加*/
    public long zadd(String key,double score,String member){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.zadd(key,score,member);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return 0;
    }

    /** 有序集合获取*/
    public Set<String> zgetAll(String key){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.zrange(key,0,-1);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    /** 给某个hkey对应的hash中设置key-value*/
    public long hset(String hkey,String key,String value){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.hset(hkey,key,value);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return 0;
    }

    /** 获取某个hash表*/
    public Map<String,String> hmget(String key){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.hgetAll(key);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    /** 获取某个hash表中某个key对应的值*/
    public String hget(String hkey,String key){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.hget(hkey,key);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    /** 查看某个hkey对应的hash是否包含某个key值*/
    public boolean hexists(String hkey,String key){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.hexists(hkey,key);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    /** 删除hash中某个key*/
    public long hrem(String hkey,String key){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.hdel(hkey,key);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return 0;
    }


}
