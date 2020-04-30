package frame.officer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import conectdb.DataBaseConnContract;
import conectdb.DataBaseConnSch;
import event.ScheduleEvent;
import frame.LoginFrame;
import table.TableModel;
import vo.ScheduleVO;

public class ScheduleManageFrame extends JFrame {

	// 오른쪽 검색창
	JPanel contentPane;
	public JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8;
	JButton add, modify, delete;
	private String selectedId;
	DataBaseConnSch dbc;
	// 왼쪽 테이블, eventProc
	ArrayList<ArrayList<String>> data;
	ScheduleVO vo;
	JTable table;
	TableModel model;
	JLabel jl1, jl2, jl3, jl4;
	public JComboBox<String> cbsid,cbrid,cbctel;
	public ScheduleManageFrame() {
		dbc = new DataBaseConnSch();
		setTitle("일정 관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		contentPane.setBorder(new TitledBorder("예약관리"));

		// 오른쪽
		// 오른쪽 아래 버튼
			add = new JButton("추가");
			modify = new JButton("수정");
			delete = new JButton("삭제");

		JPanel pnr_d = new JPanel();
		pnr_d.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 20));
		pnr_d.add(add);
		pnr_d.setBackground(Color.WHITE);
		pnr_d.add(modify);
		pnr_d.add(delete);
		contentPane.add(pnr_d, BorderLayout.SOUTH);

		// 텍스트필드선언
			tf1 = new JTextField();
			tf2 = new JTextField();
			tf3 = new JTextField();
			tf4 = new JTextField();
			tf5 = new JTextField();
			tf6 = new JTextField();
			tf7 = new JTextField();
		//콤보박스 선언
			DataBaseConnContract dbcc = new DataBaseConnContract();
			ArrayList<String> sarr = dbcc.getSawonId();
			ArrayList<String> rarr = dbcc.getRoomId();
			ArrayList<String> carr = dbcc.getCustomerTel();
			cbctel = new JComboBox<String>(carr.toArray(new String[carr.size()]));
			cbrid = new JComboBox<String>(rarr.toArray(new String[rarr.size()]));
			cbsid = new JComboBox<String>(sarr.toArray(new String[sarr.size()]));
			cbsid.setBackground(Color.WHITE);
			cbrid.setBackground(Color.WHITE);
			cbctel.setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		JPanel mainpn = new JPanel();
		mainpn.setLayout(new GridLayout(1, 2));
		mainpn.setBackground(Color.WHITE);
		// 왼쪽 테이블 영역
		JPanel pnl = new JPanel();
		pnl.setBorder(new TitledBorder("테이블"));
		pnl.setLayout(new BorderLayout());
		pnl.setBackground(Color.WHITE);
		model = new TableModel(); // 추상제거?
		setTable();
		table = new JTable(model);
		table.getTableHeader().setBackground(LoginFrame.mainColor);
		JScrollPane jsp = new JScrollPane(table);
		jsp.getViewport().setBackground(Color.WHITE);
		pnl.add(jsp, BorderLayout.CENTER);
		mainpn.add(pnl);
		JPanel pnr = new JPanel();
		pnr.setLayout(new GridLayout(7, 2));
		pnr.setBackground(Color.WHITE);
		
		//오른쪽 입력창
			pnr.add(new JLabel("사원번호"));
			pnr.add(cbsid);
			pnr.add(new JLabel("매물번호"));
			pnr.add(cbrid);
			pnr.add(new JLabel("고객 핸드폰"));
			pnr.add(cbctel);
			pnr.add(new JLabel("예약날짜"));
			pnr.add(tf4);
			pnr.add(new JLabel("예약시간"));//
			pnr.add(tf5);
			pnr.add(new JLabel("컨택위치"));//
			pnr.add(tf6);
			pnr.add(new JLabel("요구사항"));//
			pnr.add(tf7);
			pnr.setBorder(new TitledBorder("검색정보"));
			mainpn.add(pnr);
		contentPane.add(mainpn, BorderLayout.CENTER);
		setContentPane(contentPane);
		contentPane.setBackground(Color.WHITE);
		eventProc();
	}

	public void setTable() { //테이블설정
		String[] title = { "예약 번호", "사원 번호", "방 번호", "고객 전화번호", "미팅 날짜" };
		
		data =  dbc.getScheduleInfo();
		//ArrayList<ArrayList<String>> data = dbc.getScheduleInfo(vo);
		model.setData(data);
		model.setColumnName(title);
		model.fireTableDataChanged();

	}

	void eventProc() {
		ScheduleEvent se = new ScheduleEvent(this);
		add.addActionListener(se);
		modify.addActionListener(se);
		delete.addActionListener(se);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				selectedId = (String) table.getValueAt(row, 0);
				try {
					ScheduleVO vo = dbc.selectedSch(selectedId); //vo객체 받아오기
					//값 바꿔주기
					cbsid.setSelectedItem(vo.getSawon_id());
					cbrid.setSelectedItem(vo.getRoom_id());
					cbctel.setSelectedItem(vo.getCustomer_tel());
					tf4.setText(vo.getBooking_date());
					tf5.setText(vo.getBooking_time());
					tf6.setText(vo.getBooking_loc());
					tf7.setText(vo.getBooking_condition());
				} catch (Exception e1) {
					System.out.println("실패" + e1.getMessage());
				}
			}

		});
	}

	public String getSelectedId() {
		return selectedId;
	}
}
