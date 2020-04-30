package conectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.SawonVO;

public class DataBaseConnChart {
	String dburl = "jdbc:oracle:thin:@192.168.0.24:1521:orcl";
	String dbid = "bangadmin";
	String dbpassword = "6666";
	public DataBaseConnChart() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	//방 별 예약 수 
	public ArrayList<ArrayList> getRoomCount() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT slt.cnt cont, r.room_name rname FROM room r, "
					+ " (SELECT count(*) cnt , b.room_id rid FROM booking b GROUP BY b.room_id) slt  "+
					" WHERE r.room_id=slt.rid ";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			ArrayList data = new ArrayList();
			while(rs.next()) {
				ArrayList temp = new ArrayList();
				temp.add(rs.getInt("CONT"));
				temp.add(rs.getString("RNAME"));
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
	//사원별 실적
	public ArrayList<ArrayList> getSawonCountContract() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT contract_count ccount, name FROM sawon";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			ArrayList data = new ArrayList();
			while(rs.next()) {
				ArrayList temp = new ArrayList();
				temp.add(rs.getInt("CCOUNT"));
				temp.add(rs.getString("NAME"));
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
	//포지션별 월급 평균
	public ArrayList<ArrayList> getSawonPositionSal() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT avg(salary) savg,position FROM sawon GROUP BY position";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			ArrayList data = new ArrayList();
			while(rs.next()) {
				ArrayList temp = new ArrayList();
				temp.add(rs.getInt("SAVG"));
				temp.add(rs.getString("POSITION"));
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
