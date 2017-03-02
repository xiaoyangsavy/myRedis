package savy.myRedis.service;

public interface JedisService {

	public String getContentList(int myId);
	
	public	boolean syncCount(int myId);
	
}
