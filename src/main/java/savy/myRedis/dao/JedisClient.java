package savy.myRedis.dao;

public interface JedisClient {

	String get(String key);
	String set(String key, String value);
	Long del(String key);
	Long expire(String key, int second);
	
	
	//目前使用到的方法
	long incr(String key);
	Long hset(String hkey, String key, String value);
	String hget(String hkey, String key);
	Long hdel(String hkey, String key);
}
