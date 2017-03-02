package savy.myRedis.controller;

import org.junit.Test;

import savy.myRedis.service.JedisService;
import savy.myRedis.service.impl.JedisServiceImpl;

public class RedisController {
	
	private JedisService jedisService;

//	@Test
	//直接查找数据
	public void redisControllertest() {
		jedisService = new JedisServiceImpl();
		String result = jedisService.getContentList(1);
		System.out.println("返回的结果为：" +result);
	}
	
	@Test
	//先同步数据，再查找数据
	public void syncCounttest() {
		jedisService = new JedisServiceImpl();
		
		boolean flag = jedisService.syncCount(1);
		if(flag){
			String result = jedisService.getContentList(2);
			System.out.println("返回的结果为：" +result);
		}
		
	}
}
