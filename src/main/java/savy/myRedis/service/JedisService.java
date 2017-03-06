package savy.myRedis.service;

import java.util.List;
import java.util.Set;

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
	
	//返回列表数据,根据where条件
	public Set<String> getInfoList(String condition);
}
