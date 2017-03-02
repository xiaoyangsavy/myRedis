package savy.myRedis.service.impl;

import savy.myRedis.dao.impl.JedisClientCluster;
import savy.myRedis.service.JedisService;

public class JedisServiceImpl implements JedisService {

	private JedisClientCluster jedisClient;
	String tableName = "myTest";

	public String getContentList(int myId) {

		jedisClient = new JedisClientCluster();
		String result = null;
		// 添加取缓存的逻辑
		try {
			result = jedisClient.hget(tableName, String.valueOf(myId));
			System.out.println("缓存的内容为 ：" + result);
			// 判断结果是否为空
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 缓存逻辑结束
		if (result == null) {
			result = "{id:01,name:savy}";
		}

		// 把内容添加到缓存中
		try {
			jedisClient.hset(tableName, String.valueOf(myId), result);
		} catch (Exception e) {
		}
		return result;
	}

	//删除缓存
	public boolean syncCount(int myId) {
		boolean flag = false;
		jedisClient = new JedisClientCluster();
		Long result = jedisClient.hdel(tableName, String.valueOf(myId));
		System.out.println("删除缓存的结果为：" + result);
		flag = true;
		return flag;

	}

}
