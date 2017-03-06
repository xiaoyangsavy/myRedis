package savy.myRedis.dao.impl;


import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import savy.myRedis.dao.JedisClient;
import savy.myRedis.util.StaticProperty;

 
/**
 * 集群Dao实现类
 * @author xiaoyang
 * @version 创建时间：2017年3月6日  下午3:10:49
 */
public class JedisClientCluster implements JedisClient {

	private JedisCluster jedisCluster;//集群
	String IP = "192.168.100.99";//服务器地址
	
	//设置集群
	public JedisClientCluster(){
		// 创建jedisCluster
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort(IP, 7001));
		nodes.add(new HostAndPort(IP, 7002));
		nodes.add(new HostAndPort(IP, 7003));
		nodes.add(new HostAndPort(IP, 7004));
		nodes.add(new HostAndPort(IP, 7005));
		nodes.add(new HostAndPort(IP, 7006));
	
		jedisCluster = new JedisCluster(nodes);
	}
	
	//取得键值
	public String get(String key) {
		String string = jedisCluster.get(key);
		return string;
	}

	//设置键值
	public String set(String key, String value) {
		String string = jedisCluster.set(key, value);
		return string;
	}

	//返回自增主键
	public long incr(String key) {
		Long result = jedisCluster.incr(key);
		return result;
	}

	//设置hash
	public Long hset(String hkey, String key, String value) {
		Long result = jedisCluster.hset(hkey, key, value);
		return result;
	}

	//取得hash
	public String hget(String hkey, String key) {
		String string = jedisCluster.hget(hkey, key);
		return string;
	}

	//删除键
	public Long del(String key) {
		Long result = jedisCluster.del(key);
		return result;
	}

	//删除hash
	public Long hdel(String hkey, String key) {
		Long result = jedisCluster.hdel(hkey, key);
		return result;
	}

	//设置key的过期时间
	public Long expire(String key, int second) {
		Long result = jedisCluster.expire(key, second);
		return result;
	}
	
	
	//新增list
   public Long zadd(String indexName,double score,String value){  
	   Long result = jedisCluster.zadd(indexName,score,value); 
	   return result;
  } 

	//查找list数据(反序)
   public Set<String> zrevrange(String indexName){  
	   Set<String> result = jedisCluster.zrevrange(indexName,0,-1); 
	   return result;
  }

 
}
