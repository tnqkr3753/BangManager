package frame.salesman;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import event.OfficerEvent;
import event.SalesManEvent;

public class SalesMainFrame extends JFrame {
	String id ="";
	String password = ""; //다른프레임에서도 쓰기위해?
	static Color mainColor = new Color(226, 240, 217);
	JButton[] btn = new JButton[3];
	JPanel mainPnl;
	String name;
	
	public SalesMainFrame(String id, String password,String name) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.id= id;
		this.password = password;
		this.name = name;
		mainPnl = new JPanel();
		setTitle("방매니저 (영업사원용) " + this.name);
		setBounds(100,100,650,650);
		mainPnl.setBackground(Color.WHITE);
		mainPnl.setLayout(null);
		JLabel lbl = new JLabel("영업 사원 업무 매니저"); 
		lbl.setForeground(new Color(156,170,147));
		lbl.setFont(new Font("Serif", Font.BOLD, 20));
		lbl.getFont().deriveFont(64f);
		lbl.setBounds(50, 25, 250, 50);
		mainPnl.add(lbl);
		btnSet(mainPnl);
		add(mainPnl);
		eventProc();
	}
	//버튼 위치 조정 메소드
	private void btnSet(JPanel pnl) {
		String[] job = {"일정 관리","통계 보기","매물 검색"};
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(job[i]);
			btn[i].setBackground(mainColor);
			pnl.add(btn[i]);
		}
		btn[0].setBounds(100, 100, 200, 200);
		btn[1].setBounds(350, 225, 200, 200);
		btn[2].setBounds(100, 350, 200, 200);
	}
	void eventProc() {
		event.SalesManEvent sme = new SalesManEvent();
		for (int i = 0; i < btn.length; i++) {
			btn[i].addActionListener(sme);
		}
	}
}
