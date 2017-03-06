package savy.myRedis.dao;

import java.util.Set;

public interface JedisClient {

	public String get(String key);
	public String set(String key, String value);
	public Long del(String key);
	public Long expire(String key, int second);
	
	
	//目前使用到的方法
	public long incr(String key);
	public Long hset(String hkey, String key, String value);
	public String hget(String hkey, String key);
	public Long hdel(String hkey, String key);
	
	public Long zadd(String indexName,double score,String value);
	
	public Set<String> zrevrange(String indexName);
	
}
