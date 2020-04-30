package frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import frame.officer.OfficerFrame;
import frame.salesman.SalesMainFrame;
import conectdb.DataBaseConn;
import event.SalesManEvent;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class LoginFrame extends JFrame {
	private JPanel contentPane;
	static public Color mainColor = new Color(226, 240, 217);
	private JTextField tfLoginId;
	private JTextField tfLoginPass;
	JButton btnLogin;
	/**
	 * Launch the application.
	 */
	public LoginFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("방매니저 로그인");
		setBounds(100, 100, 500, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new TitledBorder("로그인"));
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		contentPane.add(panel1, BorderLayout.CENTER);
		panel1.setLayout(new GridLayout(0, 2, 50, 0));
		
		
		JLabel lblLoginId = new JLabel("아이디 ");
		lblLoginId.setPreferredSize(new Dimension(100,200));
		lblLoginId.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.add(lblLoginId);
		
		tfLoginId = new JTextField();
		tfLoginId.setColumns(16);
		panel1.add(tfLoginId);
		
		JLabel lbLoginPass = new JLabel("비밀번호");
		lbLoginPass.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.add(lbLoginPass);
		
		tfLoginPass = new JTextField();
		tfLoginPass.setColumns(16);
		panel1.add(tfLoginPass);
		btnLogin = new JButton("로그인");
		btnLogin.setBackground(mainColor);
		contentPane.add(btnLogin, BorderLayout.EAST);
	}
	public void eventProc() {
		LoginEvent evt = new LoginEvent();
		btnLogin.addActionListener(evt);
		tfLoginPass.addActionListener(evt);
	}
	public class LoginEvent implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			login();
		}
		void login() {
			String loginId = tfLoginId.getText();
			String loginPass = tfLoginPass.getText();
			//데이터베이스 연결 
			conectdb.DataBaseConn dc = new DataBaseConn();
			vo.LoginVO result = dc.Login(loginId);
			//아이디와 비밀번호 확인
			if(result.getPassword().equals(loginPass)) {
				JOptionPane.showMessageDialog(null, "로그인 성공");
				if(result.isBoolSales()) {
					SalesMainFrame smf = new SalesMainFrame(loginId, loginPass,result.getName());
					smf.setVisible(true);
					setVisible(false);
				}else {
					//사무직일때 프레임 열기
					OfficerFrame off = new OfficerFrame(loginId, loginPass,result.getName());
					off.setVisible(true);
					setVisible(false);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.");
			}
		}

	}

}
