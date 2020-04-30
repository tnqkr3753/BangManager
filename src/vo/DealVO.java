package vo;

public class DealVO {
	String deal_id;
	String room_id;
	String customer_tel;
	String sawon_id;
	String deal_date;
	int down_payment;
	public DealVO(String deal_id, String room_id, String customer_tel, String sawon_id, String deal_date,
			int down_payment) {
		super();
		this.deal_id = deal_id;
		this.room_id = room_id;
		this.customer_tel = customer_tel;
		this.sawon_id = sawon_id;
		this.deal_date = deal_date;
		this.down_payment = down_payment;
	}
	public String getDeal_id() {
		return deal_id;
	}
	public void setDeal_id(String deal_id) {
		this.deal_id = deal_id;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getCustomer_tel() {
		return customer_tel;
	}
	public void setCustomer_tel(String customer_tel) {
		this.customer_tel = customer_tel;
	}
	public String getSawon_id() {
		return sawon_id;
	}
	public void setSawon_id(String sawon_id) {
		this.sawon_id = sawon_id;
	}
	public String getDeal_date() {
		return deal_date;
	}
	public void setDeal_date(String deal_date) {
		this.deal_date = deal_date;
	}
	public int getDown_payment() {
		return down_payment;
	}
	public void setDown_payment(int down_payment) {
		this.down_payment = down_payment;
	}
}
