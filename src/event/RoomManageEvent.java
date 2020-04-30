package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import conectdb.DataBaseConnRoom;
import frame.officer.RoomManageFrame;
import vo.RoomVO;
import vo.SawonVO;

public class RoomManageEvent implements ActionListener {
	private JTextField[] tf;
	private DataBaseConnRoom dbcm;
	private RoomManageFrame rmf;
	private JCheckBox[] ck;
	private JComboBox<String> cbType,cbStructure;
	public RoomManageEvent() {
		tf = new JTextField[8];
		ck = new JCheckBox[8];
		dbcm = new DataBaseConnRoom();
	}
	public RoomManageEvent(RoomManageFrame rmf) {
		this();
		this.rmf = rmf;
		cbType = rmf.cbTpye;
		cbStructure = rmf.cbStructure;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object evt = e.getSource();
		if(evt == rmf.btAdd) {
			addRoom();
			rmf.setTable();
		}else if(evt == rmf.btUpdate) {
			updateRoom(this.rmf.getSelectedId());
			rmf.setTable();
		}else if(evt == rmf.btDel) {
			deleteRoom(this.rmf.getSelectedId());
			rmf.setTable();
		}else if(evt == rmf.btSearch) {
			searchRoom();
		}
	}
	private void searchRoom() {
		String[] optionStr = new String[6];
		for (int i = 0; i < optionStr.length; i++) {
			if(i==2) {
				optionStr[i] = (String)rmf.cbTpye.getSelectedItem();
			}else if(i==3) {
				optionStr[i] = (String)rmf.cbStructure.getSelectedItem();
			}else optionStr[i] = rmf.tfInfo[i].getText();
		}
		ArrayList<ArrayList<String>> data =dbcm.searchRoom(optionStr);
		rmf.setModel(data);
	}
	private void deleteRoom(String selectedId) {
		
		if(JOptionPane.showConfirmDialog(null, selectedId +"번 방을 정말 삭제하시겠습니까?")==JOptionPane.YES_OPTION) {
			if(dbcm.deleteRoom(selectedId)==-1) {
				JOptionPane.showMessageDialog(null, "삭제를 실패했습니다.");
			}
			else {
				JOptionPane.showMessageDialog(null, "삭제 성공");
			}
		}
	}

	private void updateRoom(String selectedId) {
		String[] title = {"방 이름","계약 형태","구조","지역","가격","평수","층","월세"};
		JComponent[] updateText;
		RoomVO selectedVO = dbcm.getRoom(selectedId);
		updateText = new JComponent[18];
		for (int i = 0; i < updateText.length-2; i++) {
			if (i % 2 == 0)
				updateText[i] = new JLabel(title[i / 2]);
			else {
				if(i/2==1) {
					updateText[i] = cbType;
				}else if (i/2==2) {
					updateText[i] = cbStructure;
				}else {
					tf[i / 2] = new JTextField();
					updateText[i] = tf[i / 2];
				}
			}
		}
		JPanel pn1 = new JPanel();
		JPanel pn2 = new JPanel();
		String[] ckTitle = {"거래여부","에어컨","세탁기","건조기","화장실","엘레베이터","주차","CCTV"};
		for (int j = 0; j < ck.length; j++) {
				ck[j] = new JCheckBox(ckTitle[j]);
				if(j<4) {
					pn1.add(ck[j]);
				}else {
					pn2.add(ck[j]);
				}
		}
		updateText[16] = pn1;
		updateText[17] = pn2;
		tf[0].setText(selectedVO.getRoom_name());
		cbType.setSelectedItem(selectedVO.getType());
		cbStructure.setSelectedItem(selectedVO.getStructure());
		tf[3].setText(selectedVO.getLocation());
		tf[4].setText(String.valueOf(selectedVO.getRoom_price()));
		tf[5].setText(selectedVO.getArea());
		tf[6].setText(selectedVO.getFloor());
		tf[7].setText(String.valueOf(selectedVO.getMonthly_rent()));
		ck[0].setSelected(selectedVO.isDeal_status());
		ck[1].setSelected(selectedVO.isAircon());
		ck[2].setSelected(selectedVO.isLaundry());
		ck[3].setSelected(selectedVO.isDry());
		ck[4].setSelected(selectedVO.isBath());
		ck[5].setSelected(selectedVO.isEvevator());
		ck[6].setSelected(selectedVO.isParking());
		ck[7].setSelected(selectedVO.isCctv());
		if (JOptionPane.showConfirmDialog(null, updateText, "방 정보 입력",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			ArrayList data = new ArrayList();
			data.add(selectedId);
			for (int i = 0; i < tf.length; i++) {
				if(i == 7) data.add(isNull(tf[i].getText()));
				else if(i==1) data.add((String)cbType.getSelectedItem());
				else if(i==2) data.add((String)cbStructure.getSelectedItem());
				else data.add(tf[i].getText());
			}
			for (int i = 0; i < ck.length; i++) {
				data.add(ck[i].isSelected());
			}
			RoomVO updateVO = new RoomVO();
			updateVO.setAsArrayList(data);
			int result = dbcm.updateRoom(updateVO);
			//TODO insert가 아니라 update문
			if(result == -1) {
				JOptionPane.showMessageDialog(null, "수정에 실패했습니다.");
			}else {
				JOptionPane.showMessageDialog(null, "방정보를 수정하였습니다.");
			}
		}
	}

	private void addRoom() {
		//room시퀀스 사용, 월세 계약형태에 따라 
		String[] title = {"건물 이름","계약 형태","구조","위치","가격","면적","층","월세"};
		JComponent[] addText;
			addText = new JComponent[18];
			for (int i = 0; i < title.length*2; i++) {
				if (i % 2 == 0)
					addText[i] = new JLabel(title[i / 2]);
				else {
					if(i/2==1) {
						addText[i] = cbType;
					}else if (i/2==2) {
						addText[i] = cbStructure;
					}else {
						tf[i / 2] = new JTextField();
						addText[i] = tf[i / 2];
					}
				}
			}
		JPanel pn1 = new JPanel();
		JPanel pn2 = new JPanel();
		String[] ckTitle = {"거래여부","에어컨","세탁기","건조기","화장실","엘레베이터","주차","CCTV"};
		for (int j = 0; j < ck.length; j++) {
				ck[j] = new JCheckBox(ckTitle[j]);
				if(j<4) {
					pn1.add(ck[j]);
				}else {
					pn2.add(ck[j]);
				}
		}
		addText[16] = pn1;
		addText[17] = pn2;
		if(JOptionPane.showConfirmDialog(null, addText,
				"방 정보 입력", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			ArrayList data = new ArrayList();
			for (int i = 0; i < tf.length; i++) {
				if(i == 7) data.add(isNull(tf[i].getText()));
				else if(i==1) data.add((String)cbType.getSelectedItem());
				else if(i==2) data.add((String)cbStructure.getSelectedItem());
				else data.add(tf[i].getText());
			}
			for (int i = 0; i < ck.length; i++) {
				data.add(ck[i].isSelected());
			}
			RoomVO vo = new RoomVO();
			vo.setAsArrayList(data);
			int result = dbcm.insertOne(vo);
			if(result == -1) {
				JOptionPane.showMessageDialog(null, "입력에 실패했습니다.");
			}else {
				JOptionPane.showMessageDialog(null, "방정보를 추가하였습니다.");
			}
			
		}
	}
	String isNull(String a) {
		if(a.equals("")) {
			return "0";
		}
		else return a;
	}


}
