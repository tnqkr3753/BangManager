package conectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import frame.officer.ScheduleManageFrame;
import vo.ScheduleVO;

public class DataBaseConnSch {
	String dburl = "jdbc:oracle:thin:@192.168.0.24:1521:orcl";
	String dbid = "bangadmin";
	String dbpassword = "6666";
	
	

	public DataBaseConnSch() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {

		}
	}
	//마우스
	public ScheduleVO selectedSch(String id) {
		Connection conn = null;
		ScheduleVO vo = null;
		try {
			conn = DriverManager.getConnection(dburl, dbid, dbpassword);
			String sql = "SELECT * FROM booking WHERE booking_id=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			vo = new ScheduleVO();
			if (rs.next()) {
				vo.setSawon_id(rs.getString("SAWON_ID"));
				vo.setRoom_id(rs.getString("ROOM_ID"));
				vo.setCustomer_tel(rs.getString("COSTOMER_TEL"));
				vo.setBooking_date(rs.getString("BOOKING_DATE"));
				vo.setBooking_time(rs.getString("BOOKING_TIME"));
				vo.setBooking_loc(rs.getString("BOOKING_LOC"));
				vo.setBooking_condition(rs.getString("BOOKING_CONDITION"));
			}
			rs.close();
			st.close();
			return vo;

		} catch (Exception e) {
			System.out.println("실패" + e.getMessage());
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("실패" + e.getMessage());
			}
		}
	}
	
	// 추가
	public int insertSchedule(ScheduleVO vo) {
		Connection conn = null;
		try {
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(dburl, dbid, dbpassword);
			// 3. sql문 작성
			String sql = "INSERT INTO booking VALUES (seq_booking_booking_id.nextval,?,?,?,?,?,?,?) ";
			// 4. 전송객체 얻어오기
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, vo.getSawon_id());
			st.setString(2, vo.getRoom_id());
			st.setString(3, vo.getCustomer_tel());
			st.setString(4, vo.getBooking_date());
			st.setString(5, vo.getBooking_time());
			st.setString(6, vo.getBooking_loc());
			st.setString(7, vo.getBooking_condition());
			int result = st.executeUpdate();
			if (result == 0) {
				return -1;
			}
			// 5. 전송
			st.close();
			System.out.println("등록되었습니다.");
			return 0;
		} catch (SQLException e) {
			System.out.println("등록실패" + e.getMessage());
			return -1;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}

	// 수정


	public ArrayList<ArrayList<String>> getScheduleInfo() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl, dbid, dbpassword);

			// 3. sql문 작성
			String sql = "SELECT * FROM booking ";
			// 4. 전송객체 얻어오기
			ArrayList<ArrayList<String>> data = null;
			PreparedStatement st = conn.prepareStatement(sql);
			data=new ArrayList<ArrayList<String>> ();

			ResultSet rs = st.executeQuery();
			while(rs.next()){
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(rs.getString("BOOKING_ID"));
				temp.add(rs.getString("SAWON_ID"));
				temp.add(rs.getString("ROOM_ID"));
				temp.add(rs.getString("COSTOMER_TEL"));
				temp.add(rs.getString("BOOKING_DATE"));
				data.add(temp);
			}
			rs.close();
			st.close();
			return data;
		}catch(Exception e) {
			System.out.println("실패"+e.getMessage());
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
//			
//	
//	// 업데이트
	public int updateSchedule(ScheduleVO vo) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl, dbid, dbpassword);
			String sql = "UPDATE booking SET sawon_id=?, room_id=?,costomer_tel=?, booking_date=?, booking_time=?,  "
					+ "  booking_loc=?, booking_condition=? WHERE booking_id=? ";
			PreparedStatement st = conn.prepareStatement(sql);
			
			
			st.setString(1, vo.getSawon_id());
			st.setString(2, vo.getRoom_id());
			st.setString(3, vo.getCustomer_tel());
			st.setString(4, vo.getBooking_date());
			st.setString(5, vo.getBooking_time());
			st.setString(6, vo.getBooking_loc());
			st.setString(7, vo.getBooking_condition());
			st.setString(8, vo.getBooking_id());
			int result = st.executeUpdate();
			if (result != 1) {
				conn.rollback();
				return -1;
			}
			st.close();
			conn.commit();
			return 0;
		} catch (SQLException e) {
			try {
				conn.rollback();
				System.out.println("실패" + e.getMessage());
				return -1;
			} catch (SQLException e1) {
				return -1;
			}
		}

	}

	// 삭제
	public int deleteSchedule(String id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl, dbid, dbpassword);
			String sql = "DELETE FROM booking WHERE booking_id = ? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);

			int result = st.executeUpdate();
			st.close();

			System.out.println("삭제되었습니다.");
			return 0;
		} catch (SQLException e) {
			System.out.println("다시 입력해주세요." + e.getMessage());
			return -1;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}

}
