package savy.myRedis.dao.impl;


import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import savy.myRedis.dao.JedisClient;
import savy.myRedis.util.StaticProperty;

/**
 * 数据库连接池Dao实现类，未使用!
 * @author xiaoyang
 * @version 创建时间：2017年3月6日  下午3:11:17
 */
public class JedisClientPool implements JedisClient{
	
	private JedisPool jedisPool = new JedisPool(StaticProperty.REDISIP, 6379);

	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	public Long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long hset = jedis.hset(hkey, key, value);
		jedis.close();
		return hset;
	}

	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hget(hkey, key);
		jedis.close();
		return result;
	}

	public Long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	public Long hdel(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(hkey, key);
		jedis.close();
		return result;
	}

	public Long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		return result;
	}

	public Long rpush(String indexName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long zadd(String indexName, double score, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> zrevrange(String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> zrange(String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> sinter(String... indexName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long srem(String indexName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Tuple> zrangeWithScores(String condition, long pageNumber, long LineSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
