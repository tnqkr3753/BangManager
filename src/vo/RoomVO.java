package vo;

import java.util.ArrayList;

public class RoomVO {
	private String room_id;
	private String room_name;
	private String type;
	private String structure;
	private String location;
	private int room_price;
	private String area;
	private String floor;
	private int monthly_rent;
	private boolean deal_status;
	private boolean aircon,laundry,dry,bath,evevator,parking,cctv;
	public RoomVO() {
		
	}
	public RoomVO(String room_id, String room_name, String type, String structure, String location, int room_price,
			String area, String floor, int monthly_rent, boolean deal_status, boolean aircon, boolean laundry,
			boolean dry, boolean bath, boolean evevator, boolean parking, boolean cctv) {
		this.room_id = room_id;
		this.room_name = room_name;
		this.type = type;
		this.structure = structure;
		this.location = location;
		this.room_price = room_price;
		this.area = area;
		this.floor = floor;
		this.monthly_rent = monthly_rent;
		this.deal_status = deal_status;
		this.aircon = aircon;
		this.laundry = laundry;
		this.dry = dry;
		this.bath = bath;
		this.evevator = evevator;
		this.parking = parking;
		this.cctv = cctv;
	}
	public void setDeal_status(boolean deal_status) {
		this.deal_status = deal_status;
	}
	public boolean isAircon() {
		return aircon;
	}
	public boolean isLaundry() {
		return laundry;
	}
	public boolean isDry() {
		return dry;
	}
	public boolean isBath() {
		return bath;
	}
	public boolean isEvevator() {
		return evevator;
	}
	public boolean isParking() {
		return parking;
	}
	public boolean isCctv() {
		return cctv;
	}
	public String getRoom_id() {
		return room_id;
	}
	public String getRoom_name() {
		return room_name;
	}
	public String getType() {
		return type;
	}
	public String getStructure() {
		return structure;
	}
	public String getLocation() {
		return location;
	}
	public int getRoom_price() {
		return room_price;
	}
	public String getArea() {
		return area;
	}
	public String getFloor() {
		return floor;
	}
	public int getMonthly_rent() {
		return monthly_rent;
	}
	public boolean isDeal_status() {
		return deal_status;
	}
	public void setAsArrayList(ArrayList data) {
		if(data.size()==16) {
			this.room_id = "";
			this.room_name = (String) data.get(0);
			this.type =(String) data.get(1);
			this.structure = (String) data.get(2);
			this.location = (String) data.get(3);
			this.room_price = Integer.parseInt((String)data.get(4));
			this.area = (String) data.get(5);
			this.floor = (String) data.get(6);
			this.monthly_rent = Integer.parseInt((String) data.get(7));
			this.deal_status = (Boolean) data.get(8);
			this.aircon = (Boolean) data.get(9);
			this.laundry = (Boolean) data.get(10);
			this.dry = (Boolean) data.get(11);
			this.bath = (Boolean) data.get(12);
			this.evevator = (Boolean) data.get(13);
			this.parking = (Boolean) data.get(14);
			this.cctv = (Boolean) data.get(15);
		}else if(data.size() == 17) {
			this.room_id = (String) data.get(0);
			this.room_name = (String) data.get(1);
			this.type =(String) data.get(2);
			this.structure = (String) data.get(3);
			this.location = (String) data.get(4);
			this.room_price = Integer.parseInt((String) data.get(5));
			this.area = (String) data.get(6);
			this.floor = (String) data.get(7);
			this.monthly_rent = Integer.parseInt((String) data.get(8));
			this.deal_status = (Boolean) data.get(9);
			this.aircon = (Boolean) data.get(12);
			this.laundry = (Boolean) data.get(11);
			this.dry = (Boolean) data.get(12);
			this.bath = (Boolean) data.get(13);
			this.evevator = (Boolean) data.get(14);
			this.parking = (Boolean) data.get(15);
			this.cctv = (Boolean) data.get(16);
		}
	}
	
}
