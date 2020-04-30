package conectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.DealVO;

public class DataBaseConnContract {
	String dburl = "jdbc:oracle:thin:@192.168.0.24:1521:orcl";
	String dbid = "bangadmin";
	String dbpassword = "6666";
	public DataBaseConnContract() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public ArrayList getInfo(String[] info) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT s.name sname,s.tel stel,r.location rloc, r.room_price rprice,"
					+ " r.area rarea, r.monthly_rent rmon, c.costomer_tel ctel, c.costomer_name cname, c.costomer_address "
					+ " cadd FROM sawon s,room r, costomer c WHERE s.sawon_id=? AND r.room_id=? AND c.costomer_tel=? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, info[0]);
			st.setString(2, info[1]);
			st.setString(3, info[2]);
			ResultSet rs = st.executeQuery();
			ArrayList data = new ArrayList();
			boolean bool = false;
			if(rs.next()) { //한칸내리기;
				data.add(rs.getString("SNAME"));//0
				data.add(rs.getString("STEL"));//1
				data.add(rs.getString("RLOC"));//2
				data.add(rs.getInt("RPRICE"));//3
				data.add(rs.getString("RAREA"));//4
				data.add(rs.getInt("RMON"));//5
				data.add(rs.getString("CTEL"));//6
				data.add(rs.getString("CNAME"));//7
				data.add(rs.getString("CADD"));//8
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
	public ArrayList<String> getSawonId() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT sawon_id FROM sawon WHERE job='영업'  ";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			ArrayList<String> id = new ArrayList<String>();
			while(rs.next()) {
				id.add(rs.getString("SAWON_ID"));
			}
			//7. 닫기
			rs.close();
			st.close();
			
			return id;
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
	public ArrayList<String> getRoomId() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT room_id FROM room  ";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			ArrayList<String> id = new ArrayList<String>();
			while(rs.next()) {
				id.add(rs.getString("ROOM_ID"));
			}
			//7. 닫기
			rs.close();
			st.close();
			
			return id;
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
	public ArrayList<String> getCustomerTel() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT COSTOMER_TEL FROM costomer  ";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			ArrayList<String> id = new ArrayList<String>();
			while(rs.next()) {
				id.add(rs.getString("COSTOMER_TEL"));
			}
			//7. 닫기
			rs.close();
			st.close();
			
			return id;
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
	public int addDeal(DealVO vo) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "INSERT INTO deal VALUES (seq_deal_deal_id.nextval,?,?,?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, vo.getRoom_id());
			st.setString(2, vo.getCustomer_tel());
			st.setString(3, vo.getSawon_id());
			st.setString(4, vo.getDeal_date());
			st.setInt(5, vo.getDown_payment());
			int rs = st.executeUpdate();
			if(rs!=1) {
				System.out.println("실패");
				return -1;
			}
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
}
