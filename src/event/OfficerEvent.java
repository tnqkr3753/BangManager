package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import frame.officer.SawonManageFrame;
import frame.officer.ScheduleManageFrame;
import frame.officer.CustomerManageFrame;
import frame.officer.RoomManageFrame;

public class OfficerEvent implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton evt = (JButton)e.getSource();
		if(evt.getText().equals("사원 관리")) {
			System.out.println("사원관리 창 띄우기");
			SawonManageFrame sawonframe = new SawonManageFrame();
			sawonframe.setVisible(true);


		}else if (evt.getText().equals("예약 관리")) {
			//예약관리 창 띄우기
			ScheduleManageFrame frame = new ScheduleManageFrame();
			frame.setVisible(true);
			System.out.println("예약 관리 창 띄우기");
		}else if (evt.getText().equals("매물 현황")) {
			//방관리 창띄우기
			RoomManageFrame roomFrame = new RoomManageFrame();
			roomFrame.setVisible(true);
			System.out.println("매물 현황 창 띄우기");
		}else if (evt.getText().equals("고객 등록")) {
			//고객등록 창 띄우기
			CustomerManageFrame cusFrame = new CustomerManageFrame();
			cusFrame.setVisible(true);
		}
	}
	
}
