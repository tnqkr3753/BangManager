package frame.salesman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import conectdb.DataBaseConnMkt;
import event.MarketingScEvent;
import frame.LoginFrame;
import table.TableModel;
import vo.MarketingScVO;

public class MarketingScFrame extends JFrame {

	protected static final String id = null;
	private String selectedId;
	private ArrayList<ArrayList<String>> data;
	private JPanel contentPane;
	private JTable table;
	private TableModel model;
	private JButton modify, delete;
	public DataBaseConnMkt dbcm;

	public MarketingScFrame() {
		dbcm = new DataBaseConnMkt();
		setTitle("영업사원 일정");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// 테이블 내용 삽입
		model = new TableModel();
		setTable();
		table = new JTable(model);
		table.setBackground(Color.WHITE);
		table.getTableHeader().setBackground(LoginFrame.mainColor);

		// 버튼

		//modify = new JButton("수정");
		delete = new JButton("삭제");
		JScrollPane jsp = new JScrollPane(table);
		jsp.getViewport().setBackground(Color.WHITE);
		contentPane.add(jsp, BorderLayout.CENTER);

		JPanel bp = new JPanel();
		bp.setBackground(Color.WHITE);
		bp.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 20));

		//bp.add(modify);
		delete.setBackground(LoginFrame.mainColor);
		bp.add(delete);
		contentPane.add(bp, BorderLayout.SOUTH);
		eventProc();
	}

	public void setTable() {
		// db연결
		dbcm = new DataBaseConnMkt();
		data = dbcm.getBookingInfo();
		String[] title = { "예약번호", "사원번호", "매물 번호", "고객명", "고객 연락처", "예약날짜", "예약시간", "만날 위치" };
		model.setData(data);
		model.setColumnName(title);
		model.fireTableDataChanged();
	}

	void eventProc() {
		// 이벤트 클래스 연결
		// 버튼 이벤트
		MarketingScEvent mse = new MarketingScEvent(this);// this
		//modify.addActionListener(mse);
		delete.addActionListener(mse);

		// 마우스 클릭 이벤트
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				selectedId = (String) table.getValueAt(row, 0);
				// con에 받을거
				System.out.println(selectedId);
			
			}

		});
	}
	public String getselectedId() {
		return selectedId;
	}

}
