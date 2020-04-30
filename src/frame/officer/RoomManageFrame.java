package frame.officer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import conectdb.DataBaseConnRoom;
import event.RoomManageEvent;
import frame.LoginFrame;
import table.TableModel;
import vo.RoomVO;

public class RoomManageFrame extends JFrame {
	JTable table;
	private String selectedId;
	private TableModel model ;
	private JPanel contentPane;
	JPanel[] pInfo;
	public JTextField[] tfInfo;
	public JCheckBox[] ckOption ;
	DataBaseConnRoom dbcr;
	public JButton btAdd,btUpdate,btDel,btSearch;
	public JComboBox<String> cbTpye,cbStructure;
	public RoomManageFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		
		contentPane.setBorder(new TitledBorder("매물 관리"));
		contentPane.setBackground(Color.WHITE);
		//왼쪽
		JPanel pLeft = new JPanel();
		pLeft.setBorder(new TitledBorder("매물 목록"));
		pLeft.setLayout(new BorderLayout());
		pLeft.setBackground(Color.WHITE);
		model = new TableModel();
		setTable();
		table = new JTable(model);
		table.getTableHeader().setBackground(LoginFrame.mainColor);
		JScrollPane jsp = new JScrollPane(table);
		jsp.getViewport().setBackground(Color.white);
		pLeft.add(jsp,BorderLayout.CENTER);
		//오른쪽
		JPanel pRight = new JPanel();
		pRight.setBorder(new TitledBorder("매물 정보"));
		pRight.setLayout(new BorderLayout());
		pRight.setBackground(Color.WHITE);
			JPanel pRightCenter = new JPanel();
			String[] type = {"월세","전세","매매"};
			String[] structure = {"원룸","투룸","오피스텔","아파트","주택"};
			cbTpye = new JComboBox<String>(type);
			cbStructure = new JComboBox<String>(structure);
			pRightCenter.setLayout(new GridLayout(6,1,20,20));
			pRightCenter.setBackground(Color.WHITE);
			pInfo = new JPanel[6];
			tfInfo = new JTextField[6];
			String[] lbltitle = {"매물 번호","건물 명","계약 형태","구조","주소","층"};
			//오른쪽 텍스트필드부분
			for (int i = 0; i < pInfo.length; i++) {
				pInfo[i] = new JPanel();
				pInfo[i].setBackground(Color.white);
				tfInfo[i] = new JTextField();
				JLabel temp = new JLabel(lbltitle[i]);
				temp.setBackground(Color.WHITE);
				temp.setHorizontalAlignment(SwingConstants.CENTER);
				pInfo[i].add(temp);
				//콤보박스 2와 3
				pRightCenter.add(pInfo[i]);
				if(i==2) {
					pRightCenter.add(cbTpye);
				}else if (i==3) {
					pRightCenter.add(cbStructure);
				}else pRightCenter.add(tfInfo[i]);
			}
			//오른쪽 아래에 위쪽 체크박스
			JPanel pRightSouth = new JPanel();
			pRightSouth.setLayout(new BorderLayout());
			pRightSouth.setBackground(Color.WHITE);
				JPanel pRightSouthUp = new JPanel();
				pRightSouthUp.setBackground(Color.WHITE);
				pRightSouthUp.setLayout(new GridLayout(2,4));
				ckOption = new JCheckBox[7];
				String[] ckOptionTitle = {"에어컨","세탁기","건조기","화장실","엘레베이터","주차","CCTV"};
				for (int i = 0; i < ckOption.length; i++) {
					ckOption[i]= new JCheckBox(ckOptionTitle[i]);
					ckOption[i].setBackground(Color.WHITE);
					pRightSouthUp.add(ckOption[i]);
				}
				//오른쪽 아래 아래쪽 버튼
				JPanel pRightSouthDown = new JPanel();
				pRightSouthDown.setBackground(Color.WHITE);
				btAdd = new JButton("추가");
				btAdd.setBackground(LoginFrame.mainColor);
				btUpdate = new JButton("수정");
				btUpdate.setBackground(LoginFrame.mainColor);
				btDel = new JButton("삭제");
				btDel.setBackground(LoginFrame.mainColor);
				btSearch = new JButton("검색");
				btSearch.setBackground(LoginFrame.mainColor);
				pRightSouthDown.setLayout(new FlowLayout(FlowLayout.RIGHT));
				pRightSouthDown.add(btSearch);
				pRightSouthDown.add(btAdd);
				pRightSouthDown.add(btUpdate);
				pRightSouthDown.add(btDel);
			pRightSouth.add(pRightSouthUp,BorderLayout.CENTER);
			pRightSouth.add(pRightSouthDown,BorderLayout.SOUTH);
			pRight.add(pRightSouth,BorderLayout.SOUTH);
			pRight.add(pRightCenter,BorderLayout.CENTER);
		contentPane.add(pLeft,BorderLayout.CENTER);
		contentPane.add(pRight,BorderLayout.EAST);
		setContentPane(contentPane);
		eventProc();
	}
	public void setTable() {
		String[] title = {"방 번호","건물 이름","계약 형태","구조","위치","가격","면적","층","월세","거래여부"};
		dbcr = new DataBaseConnRoom();
//		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
		ArrayList data = dbcr.selectAll();
		model.setColumnName(title);
		model.setData(data);
		model.fireTableDataChanged();
	}
	void eventProc() {
		RoomManageEvent rme = new RoomManageEvent(this);
		btAdd.addActionListener(rme);
		btUpdate.addActionListener(rme);
		btDel.addActionListener(rme);
		btSearch.addActionListener(rme);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable evt = (JTable)e.getComponent();
				int row = evt.getSelectedRow();
				selectedId = (String) table.getValueAt(row, 0);
				RoomVO vo = dbcr.getRoom(selectedId);
				tfInfo[0].setText(vo.getRoom_id());
				tfInfo[1].setText(vo.getRoom_name());
				cbTpye.setSelectedItem(vo.getType());
				cbStructure.setSelectedItem(vo.getStructure());
				tfInfo[4].setText(vo.getLocation());
				tfInfo[5].setText(vo.getFloor());
				ckOption[0].setSelected(vo.isAircon());
				ckOption[1].setSelected(vo.isLaundry());
				ckOption[2].setSelected(vo.isDry());
				ckOption[3].setSelected(vo.isBath());
				ckOption[4].setSelected(vo.isEvevator());
				ckOption[5].setSelected(vo.isParking());
				ckOption[6].setSelected(vo.isCctv());
				System.out.println(selectedId);
			}
		});
	}
	public String getSelectedId() {
		return selectedId;
	}
	public void setModel(ArrayList<ArrayList<String>> data) {
		this.model.setData(data);
		model.fireTableDataChanged();
	}

}
