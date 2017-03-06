package savy.myRedis.util;

/**
 * Redis相关工具类
 * @author xiaoyang
 * @version 创建时间：2017年3月6日  下午2:07:18
 */
public class RedisUtil {

	//根据实体类和主键编号，生成redis的表名
	public String  getTableName(Object myClass,String Id){
		//只取得类名（截断类的完整名称）
		String myClassName = myClass.getClass().getName().replaceAll("\\w+\\.+", "");
		System.out.println("取得类名称为："+myClassName);
		StringBuffer sb = new StringBuffer(myClassName);
		sb.append(":");
		sb.append(Id);
		return sb.toString(); 
	}
}
