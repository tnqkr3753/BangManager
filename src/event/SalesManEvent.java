package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import frame.salesman.ChartFrame;
import frame.salesman.MarketingScFrame;
import frame.salesman.RoomShowFrame;

public class SalesManEvent implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton evt = (JButton)e.getSource();
		if(evt.getText().equals("일정 관리")) {
			MarketingScFrame msf = new MarketingScFrame();
			msf.setVisible(true);
		}else if (evt.getText().equals("통계 보기")) {
			ChartFrame cf= new ChartFrame();
			cf.setVisible(true);
		}else if (evt.getText().equals("매물 검색")) {
			RoomShowFrame rsf = new RoomShowFrame();
			rsf.setVisible(true);
		}
	}
	
}
