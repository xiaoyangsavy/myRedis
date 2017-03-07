package savy.myRedis.dao;

import java.util.Set;

public interface JedisClient {

	public String get(String key);
	public String set(String key, String value);
	public Long del(String key);
	public Long expire(String key, int second);
	
	
	//目前使用到的方法
	public long incr(String key);
	//设置hash值
	public Long hset(String hkey, String key, String value);
	//取得hash值
	public String hget(String hkey, String key);
	//删除hash值
	public Long hdel(String hkey, String key);
	
	//向列表添加数据
	public Long zadd(String indexName,double score,String value);
	//正序返回列表
	public Set<String> zrange(String indexName);
	//倒叙返回列表
	public Set<String> zrevrange(String indexName);
	//查找多个条件的list数据
	public Set<String> sinter(String... indexName);
	 //删除list指定值的数据
	   public Long srem(String indexName,String value);
}
