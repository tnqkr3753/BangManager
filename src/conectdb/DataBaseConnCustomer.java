package conectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.CustomerVO;
import vo.SawonVO;
import vo.ScheduleVO;

public class DataBaseConnCustomer {

	String dburl = "jdbc:oracle:thin:@192.168.0.24:1521:orcl";
	String dbid = "bangadmin";
	String dbpassword = "6666";
	public DataBaseConnCustomer() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int insertOne(CustomerVO vo){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "INSERT INTO costomer values (seq_costomer_costomer_tel.nextval "
					+ " ,?,?,?,?,?)";
			ArrayList data = new ArrayList();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, vo.getTel());
			st.setString(2, vo.getName());
			st.setString(3, vo.getSub_tel());
			st.setString(4, vo.getAddress());
			st.setString(5, vo.getEmail());
			
			
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

	public ArrayList<ArrayList<String>> selectAll() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT * FROM costomer";
			ArrayList<ArrayList<String>> data = new ArrayList();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) { //한칸내리기;
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(rs.getString("COSTOMER_TEL"));
				temp.add(rs.getString("COSTOMER_NAME"));
				temp.add(rs.getString("COSTOMER_SUB_TEL"));
				temp.add(rs.getString("COSTOMER_ADDRESS"));
				temp.add(rs.getString("COSTOMER_EMAIL"));
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
	public int addDBCustomer(CustomerVO vo) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "INSERT INTO costomer values (? "
					+ " ,?,?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, vo.getTel());
			st.setString(2, vo.getName());
			st.setString(3, vo.getSub_tel());
			st.setString(4, vo.getAddress());
			st.setString(5, vo.getEmail());
			
			int result = st.executeUpdate();
			st.close();
			
			if(result == 0) {
				System.out.println("입력실패");
				conn.rollback();
				return -1;
			}else {
				conn.commit();
				return 0;
			}
		}catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	public int deleteCustomer(String Costomer_tel) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			conn.setAutoCommit(false);
			String sql = "DELETE FROM COSTOMER WHERE COSTOMER_TEL = ? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,  Costomer_tel);
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


	public CustomerVO getCustomer(String Id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT * FROM COSTOMER WHERE COSTOMER_TEL =? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, Id);
			ResultSet rs = st.executeQuery();
			CustomerVO vo = null ;
			if(rs.next()) { //한칸내리기;
				vo = new CustomerVO (rs.getString("COSTOMER_TEL"),rs.getString("COSTOMER_NAME"),
						rs.getString("COSTOMER_SUB_TEL"),rs.getString("COSTOMER_ADDRESS"),rs.getString("COSTOMER_EMAIL"));
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
		

	public int updateRow(CustomerVO vo) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			
			String sql = "UPDATE COSTOMER SET COSTOMER_tel = ? , COSTOMER_NAME = ?  "
					+ " COSTOMER_SUB_TEL = ? , COSTOMER_ADDRESS = ?, COSTOMER_EMAIL  " + " WHERE COSTOMER_TEL = ? ";
			PreparedStatement st = conn.prepareStatement(sql);
				
			st.setString(1, vo.getTel());
			st.setString(2, vo.getName());
			st.setString(3, vo.getSub_tel());
			st.setString(4, vo.getAddress());
			st.setString(5, vo.getEmail());
			
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
	public int updateCustomer(CustomerVO updateVO) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			
			String sql = "UPDATE COSTOMER SET COSTOMER_NAME = ?,  "
					+ " COSTOMER_SUB_TEL = ? , COSTOMER_ADDRESS = ?, COSTOMER_EMAIL = ?  " + " WHERE COSTOMER_TEL = ? ";
			PreparedStatement st = conn.prepareStatement(sql);
				
			st.setString(1, updateVO.getName());
			st.setString(2, updateVO.getSub_tel());
			st.setString(3, updateVO.getAddress());
			st.setString(4, updateVO.getEmail());
			st.setString(5, updateVO.getTel());
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
	}