package savy.myRedis.vo;

import java.util.Date;

//物料信息
public class MaterialInfo {

	private String id;
	private String name;
	private String sex;
	private String address;
	private String birthday;
	
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@Override
	public String toString() {
		return "MaterialInfo [id=" + id + ", name=" + name + ", sex=" + sex + ", address=" + address + ", birthday="
				+ birthday + "]";
	}
	
	
}
