package frame.officer;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import frame.salesman.SalesMainFrame;
import event.OfficerEvent;
import event.SalesManEvent;

public class OfficerFrame extends JFrame {
	String id ="";
	String password = ""; //다른프레임에서도 쓰기위해?
	static Color mainColor = new Color(226, 240, 217);
	JButton[] btn = new JButton[4];
	JPanel mainPnl;
	String name;
	public OfficerFrame(String id, String password,String name) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.id= id;
		this.password = password;
		this.name = name;
		mainPnl = new JPanel();
		setTitle("방매니저 (사무직 사원 용) " + this.name);
		setBounds(100,100,650,650);
		mainPnl.setBorder(new TitledBorder("사무직 사원 업무 매니저"));
		mainPnl.setBackground(Color.WHITE);
		mainPnl.setLayout(null);
		btnSet(mainPnl);
		add(mainPnl);
		officerEventProc();
	}
	private void btnSet(JPanel pnl) {
		String[] job = {"사원 관리","예약 관리","매물 현황","고객 등록"};
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(job[i]);
			btn[i].setBackground(mainColor);
			pnl.add(btn[i]);
		}
		btn[0].setBounds(100, 100, 200, 200);
		btn[1].setBounds(350, 100, 200, 200);
		btn[2].setBounds(100, 350, 200, 200);
		btn[3].setBounds(350, 350, 200, 200);
	}
	void officerEventProc() {
		event.OfficerEvent ofe = new OfficerEvent();
		for (int i = 0; i < btn.length; i++) {
			btn[i].addActionListener(ofe);
		}
	}
}
