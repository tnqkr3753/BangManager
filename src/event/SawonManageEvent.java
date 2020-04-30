package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import conectdb.DataBaseConn;
import frame.officer.SawonManageFrame;
import vo.SawonVO;

public class SawonManageEvent implements ActionListener {
	JTextField[] tf ;
	DataBaseConn dbc;
	SawonManageFrame smf;
	JComboBox<String> cbJob;
	public SawonManageEvent() {
		tf = new JTextField[8];
		dbc = new DataBaseConn();
		String[] jobtype = {"영업","사무"}; 
		cbJob = new JComboBox<String>(jobtype);
	}
	public SawonManageEvent(SawonManageFrame smf) {
		this();
		this.smf = smf;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton evt = (JButton)e.getSource();
		if(evt.getText().equals("추가")) {
			addSawon();
			smf.setTable();
		}else if(evt.getText().equals("삭제")) {
			deleteSawon(smf.getSelecedId());
			smf.setTable();
		}else if(evt.getText().equals("수정")) {
			updateSawon(smf.getSelecedId());
			smf.setTable();
		}else if(evt.getText().equals("전체 저장")) {
			saveAll(smf.getData());
			smf.setTable();
			//TODO 값을 어떻게 받을까?
		}
	}
	private void addSawon() {
		String[] title = {"사원 비밀번호","사원 이름","사원 직책","사원 업무","사원 월급","사원 전화번호"};
		JComponent[] addText;
			addText = new JComponent[12];
			for (int i = 0; i < addText.length; i++) {
				if(i%2==0) addText[i] = new JLabel(title[i/2]);
				else {
					if(i/2==3) {
						addText[i] = cbJob;
					}
					else {
						tf[i/2] = new JTextField();
						addText[i] = tf[i/2];
					}
				}
		}
		if(JOptionPane.showConfirmDialog(null, addText,
				"사원정보 입력", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			SawonVO vo = new SawonVO("", tf[0].getText(), tf[1].getText(), tf[2].getText(),
					(String)cbJob.getSelectedItem(), tf[4].getText(), tf[5].getText());
			//사원추가 db
			int result = dbc.addDBSawon(vo);
			if(result == -1) {
				JOptionPane.showMessageDialog(null, "입력에 실패했습니다.");
			}else {
				JOptionPane.showMessageDialog(null, "사원정보를 추가하였습니다.");
			}
			
		}
	}
	private void updateSawon(String selectedId) {
		String[] title = {"사원 비밀번호","사원 이름","사원 직책","사원 업무","사원 월급","사원 전화번호","실적","수당"};
		JComponent[] updateText;
		SawonVO selectedVO = dbc.getSawon(selectedId);
		updateText = new JComponent[16];
		for (int i = 0; i < updateText.length; i++) {
			if(i%2==0) updateText[i] = new JLabel(title[i/2]);
			else {
				if(i/2==3) {
					updateText[i] = cbJob;
				}
				else {
					tf[i/2] = new JTextField();
					updateText[i] = tf[i/2];
				}
			}
		}
		tf[0].setText(selectedVO.getPassword());
		tf[1].setText(selectedVO.getName());
		tf[2].setText(selectedVO.getPosition());
		cbJob.setSelectedItem(selectedVO.getJob());
		tf[4].setText(selectedVO.getSalary());
		tf[5].setText(selectedVO.getTel());
		tf[6].setText(String.valueOf(selectedVO.getContract_count()));
		tf[7].setText(String.valueOf(selectedVO.getCommission()));
		if (JOptionPane.showConfirmDialog(null, updateText, "사원정보 입력",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			SawonVO vo = new SawonVO(selectedId, tf[0].getText(), tf[1].getText(), tf[2].getText(), (String)cbJob.getSelectedItem(),
					tf[4].getText(), tf[5].getText(),Integer.parseInt(tf[6].getText()),Integer.parseInt(tf[7].getText()));
			int result = dbc.updateRow(vo);
			if(result == -1) {
				JOptionPane.showMessageDialog(null, "수정에 실패했습니다.");
			}else {
				JOptionPane.showMessageDialog(null, "사원정보를 수정하였습니다.");
			}
			
		}
	}
	private void saveAll(ArrayList<ArrayList<String>> data) {
		boolean okay = true;
		for (int i = 0; i < data.size(); i++) {
			SawonVO vo = new SawonVO();
			String[] temp = new String[data.get(i).size()];
			vo.setSawonVO(data.get(i).toArray(temp));
			if(dbc.updateRow(vo)==-1) {
				JOptionPane.showMessageDialog(null, "입력에 실패하였습니다."+i+"번째 행");
				okay=false;
				break;
			}
		}
		if(okay) JOptionPane.showMessageDialog(null, "전체 저장을 완료했습니다.");
	}
	private void deleteSawon(String selectedId) {
		if(JOptionPane.showConfirmDialog(null, selectedId +"번 사원을 정말 삭제하시겠습니까?")==JOptionPane.YES_OPTION) {
			if(dbc.deleteSawon(selectedId)==-1) {
				JOptionPane.showMessageDialog(null, "삭제를 실패했습니다.");
			}
			else {
				JOptionPane.showMessageDialog(null, "삭제 성공");
			}
		}
	}

}
