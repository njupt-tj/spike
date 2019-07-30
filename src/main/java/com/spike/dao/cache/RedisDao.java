package com.spike.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.spike.dao.SpikeMapper;
import com.spike.entity.Spike;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;

/**
 * Author: tangji
 * Date: 2019 07 18 15:40
 * Description: <...>
 */
public class RedisDao {
    private final JedisPool jedisPool;
    private Logger logger = Logger.getLogger(RedisDao.class);
    private RuntimeSchema<Spike> schema = RuntimeSchema.createFrom(Spike.class);

    public RedisDao(String ip, int port) {
        this.jedisPool = new JedisPool(ip, port);
    }

    public Spike getSpikeByRedis(Long spikeId) {
        try {
            //从连接池中获取连接
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "spike" + spikeId;
                //没有实现序列化操作
                //采用自定义系列化,get byte[] -->反序列化-->object
                //protostuff:pojo
                byte[] bytes = jedis.get(key.getBytes());
                //缓存获取到
                if (bytes != null) {
                    //空对象
                    Spike spike = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, spike, schema);
                    //spike被反序列化
                    return spike;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putSpikeIntoRedis(Spike spike) {
        //set object to redis object-->byte[]--->redis
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "spike" + spike.getSpikeId();
                //序列化
                byte[] bytes = ProtostuffIOUtil.toByteArray(spike, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //设置超时时间
                long milliseconds = 3600 * 1000L;
                String result = jedis.psetex(key.getBytes(), milliseconds, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean reduceStock(Long spikeId) {
        Jedis jedis=null;
        try {
            //获取连接
            jedis = jedisPool.getResource();
            String key="spikeId"+spikeId+"stock";
            if (jedis.get(key)==null){
                Spike spike=getSpikeByRedis(spikeId);
                jedis.set(key, String.valueOf(spike.getNumber()));
                jedis.expire(key, 1800);
            }
            final Long decr = jedis.decr(key);
            if (decr>=0){
                return true;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }finally {
            jedis.close();
        }
        return false;
    }

    public boolean markUserAccessGood(Long spikeId,Long userPhone){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            String key="spikeId"+spikeId+"userPhone"+userPhone;
            final String s = jedis.get(key);
            if (s==null){
                String value="已经访问";
                jedis.set(key, value);
                jedis.expire(key, 1800);
                return true;
            }
        }catch (Exception  e){
            logger.error(e.getMessage(),e);
        }finally {
            jedis.close();
        }
        return false;
    }
}
