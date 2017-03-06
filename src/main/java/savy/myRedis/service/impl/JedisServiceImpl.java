package savy.myRedis.service.impl;

import java.util.List;
import java.util.Set;

import savy.myRedis.dao.impl.JedisClientCluster;
import savy.myRedis.service.JedisService;
import savy.myRedis.util.RedisUtil;
import savy.myRedis.util.StaticProperty;
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
			//根据生成的表名，新增数据
			//由于未集群操作，数据会分别插入到不同的主数据库中，需在不同的数据库中进行查看
			jedisClient.hset(tableName, "name", info.getName());
			jedisClient.hset(tableName, "date", info.getBirthday().toString());
			
			//查看是否加入成功
//			String name = jedisClient.hget(tableName, "name");
//			System.out.println("信息插入的结果为："+ name);
			
			//将数据加入到索引
			jedisClient.zadd(StaticProperty.TABLEINDEXADDRESS,Double.valueOf(info.getId()), tableName);
		} catch (Exception e) {
			System.out.println("插入发生错误:"+e.toString());
			flag = false;
		}
		return flag;
	}
	
	//根据where条件，查找列表数据
	public Set<String> getInfoList(String condition){
		
		Set<String> infoSet = jedisClient.zrevrange(condition);
		
		return infoSet;
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
