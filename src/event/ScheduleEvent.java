package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import conectdb.DataBaseConnSch;
import frame.officer.ScheduleManageFrame;
import vo.ScheduleVO;

public class ScheduleEvent implements ActionListener {

	// 검색 추가 수정(창띄우기) 삭제

	ScheduleVO vo;
	TableModel model;
	DataBaseConnSch dbc;

	private ScheduleManageFrame scmf;

	public ScheduleEvent() {
		dbc = new DataBaseConnSch();
	}

	public ScheduleEvent(ScheduleManageFrame scmf) {
		this();
		this.scmf = scmf;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton evt = (JButton) e.getSource();
		if (evt.getText().equals("추가")) {
			add();
			scmf.setTable();
		} else if (evt.getText().equals("수정")) {
			modify(scmf.getSelectedId());
			scmf.setTable();
		} else if (evt.getText().equals("삭제")) {
			delete(scmf.getSelectedId());
			scmf.setTable();
		}
	}

	void add() {
		String sawon_id = (String)scmf.cbsid.getSelectedItem();
		String room_id = (String)scmf.cbrid.getSelectedItem();
		String customer_tel = (String)scmf.cbctel.getSelectedItem();
		String booking_date = scmf.tf4.getText();
		String booking_time = scmf.tf5.getText();
		String booking_loc = scmf.tf6.getText();
		String booking_condition = scmf.tf7.getText();

		ScheduleVO vo = new ScheduleVO("", sawon_id, room_id, customer_tel, booking_date, booking_time, booking_loc,
				booking_condition);
		// vo를 인자로 가진 database에 보내서 인설트를 해주고
		int result = dbc.insertSchedule(vo);
		// 인설트가 됬다면 성공메세지, 실패하면 실패메세지
		if (result == -1) {
			JOptionPane.showMessageDialog(null, "추가에 실패했습니다");
		} else {
			JOptionPane.showMessageDialog(null, "추가에 성공했습니다");
		}
	}

	void modify(String selectedId) {
		String sawon_id = (String)scmf.cbsid.getSelectedItem();
		String room_id = (String)scmf.cbrid.getSelectedItem();
		String customer_tel = (String)scmf.cbctel.getSelectedItem();
		String booking_date = scmf.tf4.getText();
		String booking_time = scmf.tf5.getText();
		String booking_loc = scmf.tf6.getText();
		String booking_condition = scmf.tf7.getText();
		ScheduleVO vo = new ScheduleVO(selectedId, sawon_id, room_id, customer_tel, booking_date, booking_time, booking_loc,
				booking_condition);
		// dbc업데이문을 만들어서 vo를 넘겨주고
		// 결과값을 받고
		int result = dbc.updateSchedule(vo);

		if (result == -1) {
			JOptionPane.showMessageDialog(null, "추가에 실패했습니다.");
		} else {
			JOptionPane.showMessageDialog(null, "추가에 성공했습니다");
		}
	}

	void delete(String selectedId) {
		if (JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?") == JOptionPane.OK_OPTION) {
			dbc.deleteSchedule(selectedId);
		}
	}
	
	

}
