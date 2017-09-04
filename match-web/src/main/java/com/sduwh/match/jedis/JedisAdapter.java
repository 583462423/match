package com.sduwh.match.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

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

    /** 返回某个数据的长度*/
    public long scard(String key){
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("redis添加数据失败");
        }
        return -1;
    }

    public Set<String> sget(String key){
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.smembers(key);
        }catch (Exception e){
            logger.error("redis添加数据失败");
        }
        return null;
    }

}
