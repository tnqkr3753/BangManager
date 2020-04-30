package vo;

import java.util.ArrayList;

public class CustomerVO {
	 String tel;
	 String name;
	 String sub_tel;
	 String address;
	 String email;
	
	public CustomerVO(String tel,  String name, String sub_tel, String address, String email) {
		this.tel = tel;
		this.name = name;
		this.sub_tel = sub_tel;
		this.address = address;
		this.email = email;
	}
	public CustomerVO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getSub_tel() {
		return sub_tel;
	}

	public void setSub_tel(String sub_tel) {
		this.sub_tel = sub_tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setCustomerVO(String[] arr) {
		this.tel = arr[0];
		this.name = arr[1];
		this.sub_tel = arr[2];
		this.address = arr[3];
		this.email = arr[4];
	
	}

	public void setAsArrayList(ArrayList data) {
			this.tel=(String)data.get(0);
			this.name=(String)data.get(1);
			this.sub_tel=(String)data.get(2);
			this.address=(String)data.get(3);
			this.email=(String)data.get(4);
	}


}
