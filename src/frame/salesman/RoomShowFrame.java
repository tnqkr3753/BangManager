package frame.salesman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import conectdb.DataBaseConnRoom;
import event.RoomShowEvent;
import frame.LoginFrame;
import table.TableModel;
import table.TableModelNotEdit;
import vo.RoomVO;

public class RoomShowFrame extends JFrame {
	JTable table;
	private String selectedId;
	private TableModel model ;
	private JPanel contentPane;
	public JComboBox<String> cbtype,cbstructure;
	DataBaseConnRoom dbcr;
	JButton btContract;
	public RoomShowFrame() {
		dbcr = new DataBaseConnRoom();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		
		contentPane.setBorder(new TitledBorder("매물 정보"));
		contentPane.setBackground(Color.WHITE);
		//왼쪽
		JPanel pLeft = new JPanel();
			pLeft.setBorder(new TitledBorder("매물 목록"));
			pLeft.setLayout(new BorderLayout());
			pLeft.setBackground(Color.WHITE);
			model = new TableModelNotEdit();
			setTable();
			table = new JTable(model);
			table.getTableHeader().setBackground(LoginFrame.mainColor);
			JScrollPane jsp = new JScrollPane(table);
			jsp.getViewport().setBackground(Color.white);
			pLeft.add(jsp,BorderLayout.CENTER);
		//하단
		JPanel pSouth = new JPanel();
			btContract = new JButton("계약서 작성");
			btContract.setBackground(LoginFrame.mainColor);
			pSouth.setBackground(Color.WHITE);
			pSouth.setLayout(new FlowLayout(FlowLayout.RIGHT,10,20));
			pSouth.add(btContract);
			contentPane.add(pSouth,BorderLayout.SOUTH);
		
		contentPane.add(pLeft,BorderLayout.CENTER);
		setContentPane(contentPane);
		eventProc();
	}
	public void setTable() { //테이블구성
		String[] title = {"방 번호","건물 이름","계약 형태","구조","위치","가격","면적","층","월세","거래여부","에어컨","세탁기","건조기","화장실","엘레베이터","주차","CCTV"};
//		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
		ArrayList data = dbcr.selectAll();
		model.setColumnName(title);
		model.setData(data);
		model.fireTableDataChanged();
	}
	void eventProc() {
		RoomShowEvent rme = new RoomShowEvent(this);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable evt = (JTable)e.getComponent();
				int row = evt.getSelectedRow();
				selectedId = (String) table.getValueAt(row, 0);
				RoomVO vo = dbcr.getRoom(selectedId);
				System.out.println(selectedId);
			}
		});
		btContract.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(selectedId !=null) {
					ContractFrame ctf = new ContractFrame(selectedId);
					ctf.setVisible(true);
				}
			}
		});
	}
	//선택한 값 내보내기
	public String getSelectedId() {
		return selectedId;
	}
	//모델 설정
	public void setModel(ArrayList<ArrayList<String>> data) {
		this.model.setData(data);
		model.fireTableDataChanged();
	}

}
