package savy.myRedis.controller;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Tuple;
import savy.myRedis.service.JedisService;
import savy.myRedis.service.impl.JedisServiceImpl;
import savy.myRedis.util.StaticProperty;
import savy.myRedis.vo.MaterialInfo;

public class RedisController {

	private JedisService jedisService = new JedisServiceImpl();

	// @Test
	// 直接查找数据
	public void redisControllertest() {
		String result = jedisService.getContentList(1);
		System.out.println("返回的结果为：" + result);
	}

	@Test
	// 批量添加数据
	public void insertInfos() {
		
		for(int i=0;i<5;i++){
			this.addInfo();
		}
	}
	
	@Test
	//清空数据库
	public void clear(){
		  jedisService.clear(StaticProperty.TABLEINDEX);
	}
	
	// @Test
	// 先同步数据，再查找数据
	public void syncCounttest() {
		boolean flag = jedisService.syncCount(1);
		if (flag) {
			String result = jedisService.getContentList(2);
			System.out.println("返回的结果为：" + result);
		}

	}

	// 录入数据
	@Test
	public void addInfo() {
		// 生成主键，每次自增1
		Long userId = jedisService.getId(StaticProperty.TABLEIDUSER);
		System.out.println("编号为1：" + userId);
		MaterialInfo materialInfo = new MaterialInfo();
		materialInfo.setId(String.valueOf(userId));
		materialInfo.setName("test");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new java.util.Date();
		String myDate = sdf.format(date);
		materialInfo.setBirthday(myDate);
		materialInfo.setAddress("北京");

		boolean flag = jedisService.addInfo(materialInfo);
		if (flag) {
			System.out.println("插入数据成功！");
		} else {
			System.out.println("插入数据失败！");
		}

	}

	@Test
	// 取得list列表中的数据
	public void getInfoList() {
		Set<String> infoList = jedisService.getInfoList(StaticProperty.TABLEINDEX);
//		Set<String> infoList = jedisService.getInfoList(StaticProperty.TABLEINDEXADDRESS,false);
		Iterator<String> iterator = infoList.iterator();
		while (iterator.hasNext()) {
			String tableId = iterator.next();
			System.out.println("列表项的编号为：" + tableId);
			MaterialInfo materialInfo = jedisService.getInfo(tableId);
			System.out.println("列表项的数据为：" + materialInfo.toString());
		}
	}
	
	@Test
	// 取得list列表中交集的数据，集群不可使用！
	public void getInfoListByWheres() {
		Set<String> infoList = jedisService.getInfoListByWheres(StaticProperty.TABLEINDEXAGE,StaticProperty.TABLEINDEXSEX);
//		Set<String> infoList = jedisService.getInfoList(StaticProperty.TABLEINDEXADDRESS,false);
		Iterator<String> iterator = infoList.iterator();
		while (iterator.hasNext()) {
			String tableId = iterator.next();
			System.out.println("列表项的编号为：" + tableId);
			MaterialInfo materialInfo = jedisService.getInfo(tableId);
			System.out.println("列表项的数据为：" + materialInfo.toString());
		}
	}
	
	
	@Test
	//分页查询列表记录
	public void getListByPage(){
		
		Set<Tuple> infoList = jedisService.getInfoListByPage("baseInfoBackIndex",4,3);
		
		for (Tuple tuple : infoList) {  
			System.out.println(tuple.getScore() + " : " + tuple.getElement());  
        }  
	}
	

}
