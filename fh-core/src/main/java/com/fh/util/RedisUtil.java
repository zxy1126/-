package com.fh.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static void set(String key,String value){
        Jedis jedis=null;
        try {
            jedis = RedisPool.getResource();
            jedis.set(key,value);
        } finally {
            if(null != jedis){
                jedis.close();
                jedis=null;
            }
        }
    }

    public static Long incrExpire(String key,int second){
        Jedis jedis=null;
        try {
            jedis = RedisPool.getResource();
            Long incr = jedis.incr(key);
            if(incr == 1){
                jedis.expire(key,second);
            }
                return incr;
        } finally {
            if(null != jedis){
                jedis.close();
                jedis=null;
            }
        }
    }


    public static boolean setNxExpire(String key,String value,int second){
        Jedis jedis=null;
        try {
            jedis = RedisPool.getResource();
            Long setnx = jedis.setnx(key, value);
            if(setnx == 1){
                jedis.expire(key,second);
                return true;
            }else{
                return false;
            }
        } finally {
            if(null != jedis){
                jedis.close();
                jedis=null;
            }
        }
    }


    public static void expire(String key,int second){
        Jedis jedis=null;
        try {
            jedis = RedisPool.getResource();
            jedis.expire(key,second);
        } finally {
            if(null != jedis){
                jedis.close();
                jedis=null;
            }
        }
    }


    public static String get(String key){
        Jedis jedis=null;
        String result ="";
        try {
            jedis = RedisPool.getResource();
            result = jedis.get(key);
        } finally {
            if(null != jedis){
                jedis.close();
                jedis=null;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        //set("stuName","shazi");
        String stuName = get("stuName");
        System.out.println(stuName);

    }
}
