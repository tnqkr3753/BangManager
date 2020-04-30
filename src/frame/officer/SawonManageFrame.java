package frame.officer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import conectdb.DataBaseConn;
import event.SawonManageEvent;
import frame.LoginFrame;
import table.TableModel;

public class SawonManageFrame extends JFrame {
	private String selectedId;
	private JPanel contentPane;
	private JTable tbl;
	private ArrayList<ArrayList<String>> data;
	private TableModel model;//
	private JButton btAdd,btUpdate,btDel,btRefresh,btSaveAll;
	public SawonManageFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		setTitle("사원 관리");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		//테이블 내용 삽입
		model = new TableModel();
		setTable();
		tbl = new JTable(model);
		tbl.setBackground(Color.WHITE);
		tbl.getTableHeader().setBackground(LoginFrame.mainColor);
		//버튼 생성
			btAdd = new JButton("추가");
			
			btUpdate = new JButton("수정");
			btDel = new JButton("삭제");
			btRefresh = new JButton("새로고침");
			btSaveAll = new JButton("전체 저장");
			btAdd.setBackground(LoginFrame.mainColor);
			btUpdate.setBackground(LoginFrame.mainColor);
			btDel.setBackground(LoginFrame.mainColor);
			btRefresh.setBackground(LoginFrame.mainColor);
			btSaveAll.setBackground(LoginFrame.mainColor);
		contentPane.setBorder(new TitledBorder("사원 목록"));
		contentPane.setBackground(Color.WHITE);
		//테이블
		JScrollPane jsp = new JScrollPane(tbl);
		jsp.getViewport().setBackground(Color.WHITE);
		
		contentPane.add(jsp,BorderLayout.CENTER);
			//버튼 패널
			JPanel pnlBtn = new JPanel();
			pnlBtn.setBackground(Color.WHITE);
			pnlBtn.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 20));
			pnlBtn.add(btRefresh);
			pnlBtn.add(btAdd);
			pnlBtn.add(btUpdate);
			pnlBtn.add(btSaveAll);
			pnlBtn.add(btDel);
		contentPane.add(pnlBtn,BorderLayout.SOUTH);
		eventProc();
		}
	public void setTable() { //테이블수정
		conectdb.DataBaseConn dbc = new DataBaseConn();
		data = dbc.getSawonInfo();
		String[] title = {"사원번호","비밀번호","이름","직급","업무","월급(만)","전화번호","실적","수당"};
		model.setColumnName(title);
		model.setData(data);
	}
	void eventProc() {
		SawonManageEvent sme = new SawonManageEvent(this);
		btAdd.addActionListener(sme);
		btUpdate.addActionListener(sme);
		btDel.addActionListener(sme);
		btRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTable();
				tbl.repaint();
			}
		});
		btSaveAll.addActionListener(sme);
		tbl.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable evt = (JTable)e.getComponent();
				int row = evt.getSelectedRow();
				selectedId = (String) tbl.getValueAt(row, 0);
			}
		});
	}
	public String getSelecedId() {
		return this.selectedId;
	}
	public ArrayList<ArrayList<String>> getData(){
		return this.data;
	}
}
