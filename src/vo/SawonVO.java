package vo;

import java.util.ArrayList;

public class SawonVO {
	String sawon_id;
	String password;
	String name;
	String position;
	String job;
	String salary;
	String tel;
	int contract_count;
	int commission;
	
	public SawonVO(String sawon_id, String password, String name, String position, String job, String salary,
			String tel, int contract_count, int commission) {
		this.sawon_id = sawon_id;
		this.password = password;
		this.name = name;
		this.position = position;
		this.job = job;
		this.salary = salary;
		this.tel = tel;
		this.contract_count = contract_count;
		this.commission = commission;
	}
	
	public void setContract_count(int contract_count) {
		this.contract_count = contract_count;
	}

	public void setCommission(int commission) {
		this.commission = commission;
	}

	public SawonVO(String sawon_id, String password, String name, String position, String job, String salary,
			String tel) {
		this.sawon_id = sawon_id;
		this.password = password;
		this.name = name;
		this.position = position;
		this.job = job;
		this.salary = salary;
		this.tel = tel;
	}

	public SawonVO() {
	}

	public String getSawon_id() {
		return sawon_id;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public String getPosition() {
		return position;
	}
	public String getJob() {
		return job;
	}
	public String getSalary() {
		return salary;
	}
	public String getTel() {
		return tel;
	}
	public int getContract_count() {
		return contract_count;
	}
	public int getCommission() {
		return commission;
	}
	public void setSawonVO(String[] arr) {
		this.sawon_id = arr[0];
		this.password = arr[1];
		this.name = arr[2];
		this.position = arr[3];
		this.job = arr[4];
		this.salary = arr[5];
		this.tel = arr[6];
		this.contract_count = Integer.parseInt(arr[7]);
		this.commission = Integer.parseInt(arr[8]);
	}
}
