package com.fh.util;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    private static JedisPool pool;
      private static void  initPool(){
          JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
          jedisPoolConfig.setMaxTotal(1000);
          jedisPoolConfig.setMinIdle(100);
          jedisPoolConfig.setMaxIdle(100);
          jedisPoolConfig.setTestOnBorrow(true);
          jedisPoolConfig.setTestOnReturn(true);

          pool = new JedisPool(jedisPoolConfig,"192.168.174.129",6379);
      }
    static {
        initPool();
    }
    private RedisPool(){}

    public static Jedis getResource(){
          return pool.getResource();
    }
}
