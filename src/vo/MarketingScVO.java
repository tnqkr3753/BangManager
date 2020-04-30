package vo;

public class MarketingScVO {
//예약번호, 사원번호, 매물번호, 고객명, 고객핸드폰, 예약날짜, 예약시간, 만날위치
	String booking_id;
	String name;
	String room_id;
	String customer_name;
	String customer_tel;
	String booking_date;
	String booking_time;
	String booking_loc;
	
	public MarketingScVO() {
		
	}

	public MarketingScVO(String booking_id, String name, String room_id, 
			String customer_name, String customer_tel, String booking_date, 
			String booking_time, String booking_loc) {
		this.booking_id= booking_id;
		this.name= name;
		this.room_id= room_id;
		this.customer_name= customer_name;
		this.customer_tel= customer_tel;
		this.booking_date= booking_date;
		this.booking_time= booking_date;
		this.booking_loc= booking_loc;
	}

	public String getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(String booking_id) {
		this.booking_id = booking_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoom_id() {
		return room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
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
	
}
