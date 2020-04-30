package frame.officer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import conectdb.DataBaseConnCustomer;
import event.CustomerManageEvent;
import frame.LoginFrame;
import table.TableModel;
import vo.CustomerVO;
import vo.ScheduleVO;

public class CustomerManageFrame extends JFrame {
	JTable table;
	TableModel model ;
	JPanel contentPane;
	JPanel[] pInfo;
	JTextField[] tfInfo;
	JButton btAdd,btUpdate,btDel;
	private String selectedId;
	DataBaseConnCustomer dbcc;
	public CustomerManageFrame() {
		dbcc = new DataBaseConnCustomer();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 400, 800, 400);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(1, 2));
		
		contentPane.setBorder(new TitledBorder("고객 관리"));
		contentPane.setBackground(Color.WHITE);
		
		//왼쪽
		JPanel pLeft = new JPanel();
		pLeft.setBorder(new TitledBorder("고객 목록"));
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
		pRight.setBorder(new TitledBorder("고객 정보"));
		pRight.setLayout(new BorderLayout());
		pRight.setBackground(Color.WHITE);
		JPanel pRightCenter = new JPanel();
		pRightCenter.setLayout(new GridLayout(6,1,20,20));
		pRightCenter.setBackground(Color.WHITE);
		pInfo = new JPanel[5];
		tfInfo = new JTextField[5];
		String[] lbltitle = {"고객 전화번호","고객 이름","추가전화번호","주소","이메일"};
			for (int i = 0; i < pInfo.length; i++) {
				pInfo[i] = new JPanel();
				pInfo[i].setBackground(Color.white);
				tfInfo[i] = new JTextField();
				JLabel temp = new JLabel(lbltitle[i]);
				temp.setBackground(Color.WHITE);
				temp.setHorizontalAlignment(SwingConstants.CENTER);
				pInfo[i].add(temp);
				//tf넣고
				pRightCenter.add(pInfo[i]);
				pRightCenter.add(tfInfo[i]);
				pRightCenter.setBackground(Color.WHITE);
			}
			
			JPanel pRightSouth = new JPanel();
				pRightSouth.setBackground(Color.WHITE);
				btAdd = new JButton("추가");
				btAdd.setBackground(LoginFrame.mainColor);
				btUpdate = new JButton("수정");
				btUpdate.setBackground(LoginFrame.mainColor);
				btDel = new JButton("삭제");
				btDel.setBackground(LoginFrame.mainColor);
				pRightSouth.setLayout(new FlowLayout(FlowLayout.RIGHT));
				pRightSouth.add(btAdd);
				pRightSouth.add(btUpdate);
				pRightSouth.add(btDel);
				pRightSouth.setBackground(Color.WHITE);
				
			
			pRight.add(pRightSouth,BorderLayout.SOUTH);
			pRight.add(pRightCenter,BorderLayout.CENTER);
			pRight.setBackground(Color.WHITE);
			
		contentPane.add(pLeft);
		contentPane.add(pRight);
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		EventProc();
	
	}
		public void setTable() {
		
		String[] title = {"고객전화번호","이름","추가전화번호","주소","메일주소"};
		DataBaseConnCustomer dbcc = new DataBaseConnCustomer();
		ArrayList<ArrayList<String>> data = dbcc.selectAll();
		model.setData(data);
		model.setColumnName(title);
		model.fireTableDataChanged();
		}

	void EventProc() {
		CustomerManageEvent cme = new CustomerManageEvent(this);
		btAdd.addActionListener(cme);
		btUpdate.addActionListener(cme);
		btDel.addActionListener(cme);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				selectedId = (String) table.getValueAt(row, 0);
				try {
					CustomerVO vo = dbcc.getCustomer(selectedId);
					tfInfo[0].setText(vo.getTel());
					tfInfo[1].setText(vo.getName());
					tfInfo[2].setText(vo.getSub_tel());
					tfInfo[3].setText(vo.getAddress());
					tfInfo[4].setText(vo.getEmail());
					
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

	