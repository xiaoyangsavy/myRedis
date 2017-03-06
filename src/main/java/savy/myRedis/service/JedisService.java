package savy.myRedis.service;

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
}
