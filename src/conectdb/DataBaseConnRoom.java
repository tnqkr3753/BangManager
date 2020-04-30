package conectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.RoomVO;
import vo.SawonVO;

public class DataBaseConnRoom {
	String dburl = "jdbc:oracle:thin:@192.168.0.24:1521:orcl";
	String dbid = "bangadmin";
	String dbpassword = "6666";
	public DataBaseConnRoom() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public int insertOne(RoomVO vo){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "INSERT INTO room values (seq_room_room_id.nextval "
					+ " ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ArrayList data = new ArrayList();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, vo.getRoom_name());
			st.setString(2, vo.getType());
			st.setString(3, vo.getStructure());
			st.setString(4, vo.getLocation());
			st.setInt(5, vo.getRoom_price());
			st.setString(6, vo.getArea());
			st.setString(7, vo.getFloor());
			st.setInt(8, vo.getMonthly_rent());
			st.setString(9, String.valueOf(vo.isDeal_status()));
			st.setString(10, isBoolean(vo.isAircon()));
			st.setString(11, isBoolean(vo.isLaundry()));
			st.setString(12, isBoolean(vo.isDry()));
			st.setString(13, isBoolean(vo.isBath()));
			st.setString(14, isBoolean(vo.isEvevator()));
			st.setString(15, isBoolean(vo.isParking()));
			st.setString(16, isBoolean(vo.isCctv()));
			int result = st.executeUpdate();
			if(result == 0) {
				System.out.println("입력실패");
				return -1;
			}
			//7. 닫기
			st.close();
			
			return 0;
		}catch (Exception e) {
			System.out.println("실패"+e.getMessage());
			return -1;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	String isBoolean(boolean a) {
		if(a) {
			return "유";
		}
		else {
			return "무";
		}
	}
	boolean getBoolean(String a) {
		if(a.equals("유")) {
			return true;
		}
		else {
			return false;
		}
	}
	public ArrayList selectAll() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT * FROM  room";
			ArrayList data = new ArrayList();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) { //한칸내리기;
				ArrayList temp = new ArrayList();
				temp.add(rs.getString("ROOM_ID"));
				temp.add(rs.getString("ROOM_NAME"));
				temp.add(rs.getString("TYPE"));
				temp.add(rs.getString("STRUCTURE"));
				temp.add(rs.getString("LOCATION"));
				temp.add(rs.getInt("ROOM_PRICE"));
				temp.add(rs.getString("AREA"));
				temp.add(rs.getString("FLOOR"));
				temp.add(rs.getInt("MONTHLY_RENT"));
				temp.add(rs.getString("DEAL_STATUS"));
				temp.add(rs.getString("AIRCON"));
				temp.add(rs.getString("LAUNDRY"));
				temp.add(rs.getString("DRY"));
				temp.add(rs.getString("BATH"));
				temp.add(rs.getString("ELEVATOR"));
				temp.add(rs.getString("PARKING"));
				temp.add(rs.getString("CCTV"));
				data.add(temp);
			}
			//7. 닫기
			rs.close();
			st.close();
			
			return data;
		}catch (Exception e) {
			System.out.println("실패"+e.getMessage());
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public RoomVO getRoom(String id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT * FROM room WHERE room_id=? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			RoomVO vo = null ;
			if(rs.next()) { //한칸내리기;
				vo = new RoomVO (rs.getString("ROOM_ID"),rs.getString("ROOM_NAME"),rs.getString("TYPE"),rs.getString("STRUCTURE"),
						rs.getString("LOCATION"),rs.getInt("ROOM_PRICE"),rs.getString("AREA"),rs.getString("FLOOR"),rs.getInt("MONTHLY_RENT"),
						Boolean.getBoolean(rs.getString("DEAL_STATUS")),getBoolean(rs.getString("AIRCON")),getBoolean(rs.getString("LAUNDRY"))
						,getBoolean(rs.getString("DRY")),getBoolean(rs.getString("BATH")),getBoolean(rs.getString("ELEVATOR")),getBoolean(rs.getString("PARKING")),
						getBoolean(rs.getString("CCTV")));
			}
			//7. 닫기
			rs.close();
			st.close();
			
			return vo;
		}catch (Exception e) {
			System.out.println("실패"+e.getMessage());
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public int deleteRoom(String room_id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			conn.setAutoCommit(false);
			String sql = "DELETE FROM room WHERE room_id = ? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, room_id);
			int rs = st.executeUpdate();
			if (rs != 1) {
				conn.rollback();
				return -1;
			}
			st.close();
			conn.commit();
			return 0;
		}catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("실패"+e.getMessage());
				return -1;
			} catch (SQLException e1) {
				e1.printStackTrace();
				return -1;
			}
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public int updateRoom(RoomVO vo){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "UPDATE room set room_name = ?,type = ?,structure = ?,location = ?,room_price=?,area = ?  "
					+ " ,floor = ?, monthly_rent = ?, deal_status=?, aircon = ?,laundry = ?, dry = ?, bath = ?, elevator = ?,  "
					+ "  parking = ?, cctv = ? WHERE room_id = ?";
			ArrayList data = new ArrayList();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, vo.getRoom_name());
			st.setString(2, vo.getType());
			st.setString(3, vo.getStructure());
			st.setString(4, vo.getLocation());
			st.setInt(5, vo.getRoom_price());
			st.setString(6, vo.getArea());
			st.setString(7, vo.getFloor());
			st.setInt(8, vo.getMonthly_rent());
			st.setString(9, String.valueOf(vo.isDeal_status()));
			st.setString(10, isBoolean(vo.isAircon()));
			st.setString(11, isBoolean(vo.isLaundry()));
			st.setString(12, isBoolean(vo.isDry()));
			st.setString(13, isBoolean(vo.isBath()));
			st.setString(14, isBoolean(vo.isEvevator()));
			st.setString(15, isBoolean(vo.isParking()));
			st.setString(16, isBoolean(vo.isCctv()));
			st.setString(17, vo.getRoom_id());
			int result = st.executeUpdate();
			if(result == 0) {
				System.out.println("수정 실패");
				return -1;
			}
			//7. 닫기
			st.close();
			
			return 0;
		}catch (Exception e) {
			System.out.println("실패"+e.getMessage());
			return -1;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public ArrayList<ArrayList<String>> searchRoom(String[] optionStr) {
		Connection conn = null;
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT * FROM room WHERE room_id like '%"+optionStr[0]+"%'AND room_name like '%"+optionStr[1]+"%' "
					+ " AND type like '%"+optionStr[2]+"%' AND structure like '%"+optionStr[3]+"%' AND location like '%"+optionStr[4]+"%' "
							+ " AND floor like '%"+optionStr[5]+"%' ";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if (rs.next()) { // 한칸내리기;
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(rs.getString("ROOM_ID"));
				temp.add(rs.getString("ROOM_NAME"));
				temp.add(rs.getString("TYPE"));
				temp.add(rs.getString("STRUCTURE"));
				temp.add(rs.getString("LOCATION"));
				temp.add(String.valueOf(rs.getInt("ROOM_PRICE")));
				temp.add(rs.getString("AREA"));
				temp.add(rs.getString("FLOOR"));
				temp.add(String.valueOf(rs.getInt("MONTHLY_RENT")));
				temp.add(String.valueOf(Boolean.getBoolean(rs.getString("DEAL_STATUS"))));
				data.add(temp);
			}
			
		
			//7. 닫기
			rs.close();
			st.close();
			
			return data;
		}catch (Exception e) {
			System.out.println("실패"+e.getMessage());
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
