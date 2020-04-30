package vo;

public class ScheduleVO {
	String booking_id;
	String sawon_id;
	String room_id;
	String customer_tel;
	String booking_date;
	String booking_time;
	String booking_loc;
	String booking_condition;
	
	public ScheduleVO(){
		
	}
	public ScheduleVO(String booking_id, String sawon_id, String room_id, String customer_tel, String booking_date,
			String booking_time, String booking_loc, String booking_condition) {
		this.booking_id = booking_id;
		this.sawon_id = sawon_id;
		this.room_id = room_id;
		this.customer_tel = customer_tel;
		this.booking_date = booking_date;
		this.booking_time = booking_time;
		this.booking_loc = booking_loc;
		this.booking_condition = booking_condition;
	}
	public String getBooking_id() {
		return booking_id;
	}
	public String getSawon_id() {
		return sawon_id;
	}


	public void setSawon_id(String sawon_id) {
		this.sawon_id = sawon_id;
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


	public String getBooking_date() {
		return booking_date;
	}


	public void setBooking_date(String booking_date) {
		this.booking_date = booking_date;
	}


	public String getBooking_time() {
		return booking_time;
	}


	public void setBooking_time(String booking_time) {
		this.booking_time = booking_time;
	}


	public String getBooking_loc() {
		return booking_loc;
	}


	public void setBooking_loc(String booking_loc) {
		this.booking_loc = booking_loc;
	}


	public String getBooking_condition() {
		return booking_condition;
	}


	public void setBooking_condition(String booking_condition) {
		this.booking_condition = booking_condition;
	}

	
	
	

}
