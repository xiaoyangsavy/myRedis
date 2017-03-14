package savy.myRedis.service;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Tuple;
import savy.myRedis.vo.MaterialInfo;

public interface JedisService {

	//通过缓存，获得数据
	public String getContentList(int myId);
	
	//同步数据，删除缓存
	public	boolean syncCount(int myId);
	
	//获得主键编号
	public long getId(String key);
	
	//新增信息
	public boolean addInfo(MaterialInfo info);
	
	//获取信息
	public MaterialInfo getInfo(String tableName);
	
	//返回列表数据,根据where条件
	public Set<String> getInfoList(String condition);
	
	//返回列表数据,根据where条件,可以正序或者倒叙
	public Set<String> getInfoList(String condition,boolean isDesc);
	
	// 根据多个where条件，查找列表数据
	public Set<String> getInfoListByWheres(String... condition);
	
	// 清除指定表的数据
	public boolean clear(String tableName);
	
	// 根据页数，查找列表数据
	public Set<Tuple> getInfoListByPage(String condition, long pageNumber, long LineSize);
}
