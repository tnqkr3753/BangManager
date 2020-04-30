package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import conectdb.DataBaseConnCustomer;
import frame.officer.CustomerManageFrame;
import vo.CustomerVO;
import vo.RoomVO;
import vo.SawonVO;

public class CustomerManageEvent implements ActionListener {

	JTextField[] tf;
	DataBaseConnCustomer dbcc;
	CustomerManageFrame cmf;
	
	public CustomerManageEvent() {
		tf = new JTextField[5];
		dbcc = new DataBaseConnCustomer();
	}
	public CustomerManageEvent(CustomerManageFrame cmf) {
		this();
		this.cmf = cmf;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		JButton evt = (JButton)e.getSource();
		if(evt.getText().equals("추가")) {
			addCustomer();
			this.cmf.setTable();
		}else if(evt.getText().equals("수정")) {
			updateCustomer(this.cmf.getSelectedId());
			this.cmf.setTable();
		}else if(evt.getText().equals("삭제")) {
			deleteCustomer(this.cmf.getSelectedId());
			this.cmf.setTable();
		}
		}
	private void deleteCustomer(String selectedId) {
		
		if(JOptionPane.showConfirmDialog(null, selectedId +"번 고객을 정말 삭제하시겠습니까?")==JOptionPane.YES_OPTION) {
			if(dbcc.deleteCustomer(selectedId)==-1) {
				JOptionPane.showMessageDialog(null, "삭제를 실패했습니다.");
			}
			else {
				JOptionPane.showMessageDialog(null, "삭제 성공");
			}
		}
	}	
	private void updateCustomer(String selectedId) {
		String[] title = {"고객전화번호","이름","추가전화번호","주소","이메일"};
		JComponent[] updateText;
		CustomerVO selectedVO = dbcc.getCustomer(selectedId);
		updateText = new JComponent[10];
		for (int i = 0; i < updateText.length; i++) {
			if (i % 2 == 0)
				updateText[i] = new JLabel(title[i / 2]);
			else {
				tf[i / 2] = new JTextField();
				updateText[i] = tf[i / 2];
			}
		}
		tf[0].setEditable(false);
		tf[0].setText(selectedVO.getTel());
		tf[1].setText(selectedVO.getName());
		tf[2].setText(selectedVO.getSub_tel());
		tf[3].setText(selectedVO.getAddress());
		tf[4].setText(selectedVO.getEmail());
		
		if (JOptionPane.showConfirmDialog(null, updateText, "고객 정보 입력",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			ArrayList data = new ArrayList();
			for (int i = 0; i < 5; i++) {
				data.add(tf[i].getText());
			}
			
			CustomerVO updateVO = new CustomerVO();
			updateVO.setAsArrayList(data);
			int result = dbcc.updateCustomer(updateVO);
			//TODO insert가 아니라 update문
			if(result == -1) {
				JOptionPane.showMessageDialog(null, "수정에 실패했습니다." );
			}else {
				JOptionPane.showMessageDialog(null, "고객 정보를 수정하였습니다.");
			}
		}
	}
	private void addCustomer() {
		String[] title = {"고객전화번호","이름","추가전화번호","주소","이메일"};
		JComponent[] addText;
			addText = new JComponent[10];
			for (int i = 0; i < title.length*2; i++) {
				if (i % 2 == 0)
					addText[i] = new JLabel(title[i / 2]);
				else {
					tf[i / 2] = new JTextField();
					addText[i] = tf[i / 2];
				}
			}
			if(JOptionPane.showConfirmDialog(null, addText,
					"고객 정보 입력", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {	
				CustomerVO vo = new CustomerVO(tf[0].getText(), tf[1].getText(), tf[2].getText(),
						tf[3].getText(), tf[4].getText());
				
				//고객 추가 DB
				int result = dbcc.addDBCustomer(vo);
				if(result == -1) {
					JOptionPane.showMessageDialog(null, "입력에 실패했습니다.");
				}else {
					JOptionPane.showMessageDialog(null, "고객 정보를 추가하였습니다.");
				}
			}
	}
	
}


