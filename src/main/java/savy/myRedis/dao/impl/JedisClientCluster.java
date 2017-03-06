package savy.myRedis.dao.impl;


import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import savy.myRedis.dao.JedisClient;

/**
 * redis集群版客户端
 * <p>Title: JedisClientCluster</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月21日下午3:06:25
 * @version 1.0
 */
public class JedisClientCluster implements JedisClient {

	private JedisCluster jedisCluster;
	String IP = "192.168.100.99";
	
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
	
	
	public String get(String key) {
		String string = jedisCluster.get(key);
		return string;
	}

	public String set(String key, String value) {
		String string = jedisCluster.set(key, value);
		return string;
	}

	public long incr(String key) {
		Long result = jedisCluster.incr(key);
		return result;
	}

	public Long hset(String hkey, String key, String value) {
		Long result = jedisCluster.hset(hkey, key, value);
		return result;
	}

	public String hget(String hkey, String key) {
		String string = jedisCluster.hget(hkey, key);
		return string;
	}

	public Long del(String key) {
		Long result = jedisCluster.del(key);
		return result;
	}

	public Long hdel(String hkey, String key) {
		Long result = jedisCluster.hdel(hkey, key);
		return result;
	}

	public Long expire(String key, int second) {
		Long result = jedisCluster.expire(key, second);
		return result;
	}

}
