package conectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.MarketingScVO;
import vo.SawonVO;
import vo.ScheduleVO;

public class DataBaseConnMkt {
	String dburl = "jdbc:oracle:thin:@192.168.0.24:1521:orcl";
	String dbid = "bangadmin";
	String dbpassword = "6666";

	public DataBaseConnMkt() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("실패" + e.getMessage());
		}
	}

	// 예약번호, 사원번호, 매물번호, 고객명, 고객핸드폰, 예약날짜, 예약시간, 만날위치

	// 수정, 삭제시 예약번호 가져오기 selectedId, bookingid
	public MarketingScVO getBookingId(String id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl, dbid, dbpassword);
			String sql = "SELECT s.name sname, r.room_id rid, c.costomer_name cname,c.costomer_tel ctel, b.booking_date bdate, b.booking_time btime, b.booking_loc bloc " + 
					"  FROM booking b, sawon s, room r, costomer c " + 
					"  WHERE b.sawon_id = s.sawon_id AND b.costomer_tel = c.costomer_tel AND b.room_id = r.room_id AND b.booking_id = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			MarketingScVO vo = null;
			if (rs.next()) {
				vo = new MarketingScVO(id, rs.getString("SNAME"), rs.getString("RID"),
						rs.getString("CNAME"), rs.getString("CTEL"), rs.getString("BDATE"),
						rs.getString("BTIME"), rs.getString("BLOC"));
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
				e.printStackTrace();
			}
		}

	}

	// 수정
//	public int updateRow(MarketingScVO vo) {
//		Connection conn = null;
//		try {
//			conn = DriverManager.getConnection(dburl, dbid, dbpassword);
//			conn.setAutoCommit(false);
//			String sql = "UPDATE booking SET booking_id = ? , sawon_id = ? , room_id = ? ,  "
//					+ " costomer_name = ? , costomer_tel = ? , booking_date = ? , booking_time = ? , booking_loc = ? ";
//			PreparedStatement st = conn.prepareStatement(sql);
//			st.setString(1, vo.getBooking_id());
//			st.setString(2, vo.getName());
//			st.setString(3, vo.getRoom_id());
//			st.setString(4, vo.getCustomer_name());
//			st.setString(5, vo.getCustomer_tel());
//			st.setString(6, vo.getBooking_date());
//			st.setString(7, vo.getBooking_time());
//			st.setString(8, vo.getBooking_loc());
//			int rs = st.executeUpdate();
//			if (rs != 1) {
//				conn.rollback();
//				return -1;
//			}
//			st.close();
//			conn.commit();
//			return 0;
//		} catch (Exception e) {
//			try {
//				conn.rollback();
//				System.out.println("실패" + e.getMessage());
//				return -1;
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//				return -1;
//			}
//		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				System.out.println("실패야" + e.getMessage());
//			}
//		}
//
//	}

	// 삭제
	public int deleteBooking(String id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl, dbid, dbpassword);
			conn.setAutoCommit(false);
			String sql = "DELETE FROM booking WHERE booking_id = ? ";
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
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("실패" + e.getMessage());
				return -1;
			} catch (SQLException e1) {
				e1.printStackTrace();
				return -1;
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 테이블데이터
	// 예약번호, 사원번호, 매물번호, 고객명, 고객핸드폰, 예약날짜, 예약시간, 만날위치
	public ArrayList<ArrayList<String>> getBookingInfo() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dburl, dbid, dbpassword);
			String sql = "SELECT b.booking_id, s.sawon_id, r.room_id, c.costomer_name,c.costomer_tel,  "
					+ "  b.booking_date, b.booking_time, b.booking_loc"
					+ "  FROM booking b, sawon s, room r, costomer c "
					+ "  WHERE b.sawon_id = s.sawon_id AND b.costomer_tel=c.costomer_tel AND b.room_id=r.room_id ";
			PreparedStatement st = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery();
			ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
			int i = 0;
			while (rs.next()) {
				data.add(new ArrayList<String>());
				data.get(i).add(rs.getString("BOOKING_ID"));
				data.get(i).add(rs.getString("SAWON_ID"));
				data.get(i).add(rs.getString("ROOM_ID"));
				data.get(i).add(rs.getString("COSTOMER_NAME"));
				data.get(i).add(rs.getString("COSTOMER_TEL"));
				data.get(i).add(rs.getString("BOOKING_DATE"));
				data.get(i).add(rs.getString("BOOKING_TIME"));
				data.get(i).add(rs.getString("BOOKING_LOC"));
				i++;
			}
			st.close();
			rs.close();
			return data;

		} catch (Exception e) {
			System.out.println("실패" + e.getMessage());
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println("테이블 불러오기 실패" + e.getMessage());
			}
		}
	}

	//마우스이벤트
//	public MarketingScVO selectedmc(String id) {
//		Connection conn = null;
//		MarketingScVO vo = null;
//		try {
//			conn = DriverManager.getConnection(dburl, dbid, dbpassword);
//			String sql = "SELECT * FROM booking WHERE booking_id=?";
//			PreparedStatement st = conn.prepareStatement(sql);
//			st.setString(1, id);
//			ResultSet rs = st.executeQuery();
//			vo = new MarketingScVO();
//			if (rs.next()) {
//				vo.setBooking_id(rs.getString("BOOKING_ID"));
//				vo.setSawon_id(rs.getString("SAWON_ID"));
//				vo.setRoom_id(rs.getString("ROOM_ID"));
//				vo.setCustomer_name(rs.getString("COSTOMER_NAME"));
//				vo.setCustomer_tel(rs.getString("COSTOMER_TEL"));
//				vo.setBooking_date(rs.getString("BOOKING_DATE"));
//				vo.setBooking_time(rs.getString("BOOKING_TIME"));
//				vo.setBooking_loc(rs.getString("BOOKING_LOC"));
//			}
//			rs.close();
//			st.close();
//			return vo;
//
//		} catch (Exception e) {
//			System.out.println("실패" + e.getMessage());
//			return null;
//		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				System.out.println("실패" + e.getMessage());
//			}
//
//		}
//		
//	}
}
