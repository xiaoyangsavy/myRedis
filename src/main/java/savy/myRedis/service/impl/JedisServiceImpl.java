package savy.myRedis.service.impl;

import savy.myRedis.dao.impl.JedisClientCluster;
import savy.myRedis.service.JedisService;
import savy.myRedis.util.RedisUtil;
import savy.myRedis.vo.MaterialInfo;

public class JedisServiceImpl implements JedisService {

	private JedisClientCluster jedisClient = new JedisClientCluster();
	RedisUtil RedisUtil = new RedisUtil();
	String tableName = "myTest";

	//返回表的主键
	public long getId(String key) {
		Long result = jedisClient.incr(key);
		return result;
	}
	
	//新增数据
	public boolean addInfo(MaterialInfo info){
		boolean flag = true;
		
		String tableName =  RedisUtil.getTableName(info, info.getId());
		System.out.println("生成的表名为："+tableName);
		try {
			jedisClient.hset(tableName, "name", info.getName());
			jedisClient.hset(tableName, "date", info.getBirthday().toString());
		} catch (Exception e) {
			flag = false;
		}
		
		
		
		return flag;
	}
	
	
	//通过缓存，获取数据
	public String getContentList(int myId) {

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

	//同步数据，删除缓存
	public boolean syncCount(int myId) {
		boolean flag = false;
		Long result = jedisClient.hdel(tableName, String.valueOf(myId));
		System.out.println("删除缓存的结果为：" + result);
		flag = true;
		return flag;

	}

}
