package com.spike.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.spike.entity.Spike;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
            Jedis jedis=jedisPool.getResource();
            try {
                String key="spike"+spike.getSpikeId();
                byte[] bytes=ProtostuffIOUtil.toByteArray(spike, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //设置超时时间
                long milliseconds=3600*1000L;
                String result = jedis.psetex(key.getBytes(), milliseconds, bytes);
                return result;
            }
            finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
       return  null;
    }
}
