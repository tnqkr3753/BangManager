package conectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import vo.LoginVO;
import vo.SawonVO;

public class DataBaseConn {
	String dburl = "jdbc:oracle:thin:@192.168.0.24:1521:orcl";
	String dbid = "bangadmin";
	String dbpassword = "6666";
	public DataBaseConn() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public vo.LoginVO Login(String id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT password,job,name FROM sawon WHERE sawon_id=? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			String pw ="";
			String name = "";
			boolean bool = false;
			if(rs.next()) { //한칸내리기;
				pw=rs.getString("PASSWORD");
				name = rs.getString("NAME");
				if(rs.getString("JOB").equals("영업")) {
					bool = true;
				}else bool = false;
			}
			vo.LoginVO vo = new LoginVO(pw, bool,name);
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
	public ArrayList<ArrayList<String>> getSawonInfo() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT * FROM sawon ";
			PreparedStatement st = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery();
//			rs.last(); //줄 마지막으로 내리기
//			int row = rs.getRow(); // 행 수 가져오기
			ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
//			String[][] info = new String[row][9];
//			rs.beforeFirst(); //첫번째로 올리기
			int i = 0;
			while(rs.next()) { //한칸내리기;
				data.add(new ArrayList<String>());
				data.get(i).add(rs.getString("SAWON_ID"));
				data.get(i).add(rs.getString("PASSWORD"));
				data.get(i).add(rs.getString("NAME"));
				data.get(i).add(rs.getString("POSITION"));
				data.get(i).add(rs.getString("JOB"));
				data.get(i).add(String.valueOf(rs.getInt("SALARY")));
				data.get(i).add(rs.getString("TEL"));
				data.get(i).add(String.valueOf(rs.getInt("CONTRACT_COUNT")));
				data.get(i).add(String.valueOf(rs.getInt("COMMISSION")));
				
//				info[i][0] = rs.getString("SAWON_ID");
//				info[i][1] = rs.getString("PASSWORD");
//				info[i][2] = rs.getString("NAME");
//				info[i][3] = rs.getString("POSITION");
//				info[i][4] = rs.getString("JOB");
//				info[i][5] = String.valueOf(rs.getInt("SALARY"));
//				info[i][6] = rs.getString("TEL");
//				info[i][7] = String.valueOf(rs.getInt("CONTRACT_COUNT"));
//				info[i][8] = String.valueOf(rs.getInt("COMMISSION"));
				i++;
			}
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
	public int addDBSawon(SawonVO vo) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			conn.setAutoCommit(false);
			String sql = "INSERT INTO sawon VALUES (seq_sawon_sawon_id.nextval,?,?,?,?,?,?,0,0)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, vo.getPassword());
			st.setString(2, vo.getName());
			st.setString(3, vo.getPosition());
			st.setString(4, vo.getJob());
			st.setString(5, vo.getSalary());
			st.setString(6, vo.getTel());
			int result = st.executeUpdate();
			st.close();
			if(result == 0) {
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
	public int updateRow(SawonVO vo) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String falseUpdate = "";
			conn.setAutoCommit(false);
			String sql = "UPDATE Sawon SET password = ? , name = ? , position = ? , "
					+ " job = ? , salary = ? , tel = ? , contract_count = ? , commission = ? " + " WHERE sawon_id = ? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, vo.getPassword());
			st.setString(2, vo.getName());
			st.setString(3, vo.getPosition());
			st.setString(4, vo.getJob());
			st.setString(5, vo.getSalary());
			st.setString(6, vo.getTel());
			st.setInt(7, vo.getContract_count());
			st.setInt(8, vo.getCommission());
			st.setString(9, vo.getSawon_id());
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
	public SawonVO getSawon(String id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			String sql = "SELECT * FROM sawon WHERE sawon_id=? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			SawonVO vo = null ;
			if(rs.next()) { //한칸내리기;
				vo = new SawonVO (rs.getString("SAWON_ID"),rs.getString("PASSWORD"),rs.getString("NAME"),rs.getString("POSITION"),
						rs.getString("JOB"),rs.getString("SALARY"),rs.getString("TEL"),rs.getInt("CONTRACT_COUNT"),rs.getInt("COMMISSION"));
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
	public int deleteSawon(String id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl,dbid,dbpassword); 
			conn.setAutoCommit(false);
			String sql = "DELETE FROM SAWON WHERE sawon_id = ? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
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
	
//	public 
}
