package savy.myRedis.service.impl;

import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Tuple;
import savy.myRedis.dao.impl.JedisClientCluster;
import savy.myRedis.service.JedisService;
import savy.myRedis.util.RedisUtil;
import savy.myRedis.util.StaticProperty;
import savy.myRedis.vo.MaterialInfo;

public class JedisServiceImpl implements JedisService {

	private JedisClientCluster jedisClient = new JedisClientCluster();
	RedisUtil redisUtil = new RedisUtil();
	String tableName = "myTest";

	// 返回表的主键
	public long getId(String key) {
		Long result = jedisClient.incr(key);
		return result;
	}

	// 新增数据
	public boolean addInfo(MaterialInfo info) {
		boolean flag = true;

		String tableName = redisUtil.getTableName(info, info.getId(), false);
		System.out.println("生成的表名为：" + tableName);
		try {
			// 根据生成的表名，新增数据
			// 由于未集群操作，数据会分别插入到不同的主数据库中，需在不同的数据库中进行查看
			jedisClient.hset(tableName, "name", info.getName());
			jedisClient.hset(tableName, "date", info.getBirthday().toString());

			// 将数据加入到全表索引
			jedisClient.zadd(StaticProperty.TABLEINDEX, Double.valueOf(info.getId()), tableName);
			if (Integer.valueOf(info.getId()) % 3 == 0) {
				// 将数据加入到地址索引
				jedisClient.zadd(StaticProperty.TABLEINDEXADDRESS, Double.valueOf(info.getId()), tableName);
			}
			if (Integer.valueOf(info.getId()) % 2 == 0) {
				// 将数据加入到年龄索引
				jedisClient.zadd(StaticProperty.TABLEINDEXAGE, Double.valueOf(info.getId()), tableName);
			}
			if (Integer.valueOf(info.getId()) % 5 == 0) {
				// 将数据加入到性别索引
				jedisClient.zadd(StaticProperty.TABLEINDEXSEX, Double.valueOf(info.getId()), tableName);
			}
		} catch (Exception e) {
			System.out.println("插入发生错误:" + e.toString());
			flag = false;
		}
		return flag;
	}

	// 根据表名取得vo对象
	public MaterialInfo getInfo(String tableName) {
		MaterialInfo materialInfo = new MaterialInfo();
		materialInfo.setBirthday(jedisClient.hget(tableName, "date"));
		materialInfo.setName(jedisClient.hget(tableName, "name"));
		return materialInfo;
	}

	// 清除指定表的数据
	public boolean clear(String tableName) {
		boolean flag = true;
		Set<String> infoList = this.getInfoList(tableName);
		Iterator<String> iterator = infoList.iterator();
		while (iterator.hasNext()) {
			String tableId = iterator.next();
			System.out.println("列表项的编号为：" + tableId);
			jedisClient.srem(tableName, tableId);
		}
		return flag;
	}

	// 清除指定表的数据
	public boolean deleteById(String tableId) {
		boolean flag = true;
		jedisClient.del(tableId);
		return flag;
	}

	// 根据where条件，查找列表数据
	public Set<String> getInfoList(String condition) {

		Set<String> infoSet = jedisClient.zrevrange(condition);

		return infoSet;
	}

	// 根据where条件，查找列表数据
	public Set<String> getInfoList(String condition, boolean isDesc) {
		Set<String> infoSet = null;
		if (isDesc) {
			infoSet = jedisClient.zrevrange(condition);
		} else {
			infoSet = jedisClient.zrange(condition);
		}
		return infoSet;
	}

	// 根据多个where条件，查找列表数据
	public Set<String> getInfoListByWheres(String... condition) {
		Set<String> infoSet = null;
		infoSet = jedisClient.sinter(condition);
		return infoSet;
	}

	// 通过缓存，获取数据
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

	// 同步数据，删除缓存
	public boolean syncCount(int myId) {
		boolean flag = false;
		Long result = jedisClient.hdel(tableName, String.valueOf(myId));
		System.out.println("删除缓存的结果为：" + result);
		flag = true;
		return flag;

	}

	// //分页查询列表记录
	public Set<Tuple> getInfoListByPage(String condition, long pageNumber, long lineSize) {
		Set<Tuple> infoSet = null;
		long start = (pageNumber - 1) * lineSize;
		long end = (pageNumber) * lineSize - 1;// 由于redis的范围包括边界值，需要-1
		infoSet = jedisClient.zrangeWithScores(condition, start, end);
		return infoSet;
	}

}
