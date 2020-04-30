package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import conectdb.DataBaseConnMkt;
import frame.salesman.MarketingScFrame;
import vo.MarketingScVO;
import vo.SawonVO;

public class MarketingScEvent implements ActionListener{

	JTextField[] tf;
	MarketingScVO vo;
	TableModel model;
	DataBaseConnMkt dbcm;
	MarketingScFrame mksf;
	
	public MarketingScEvent() {
		tf = new JTextField[8];
		for (int i = 0; i < tf.length; i++) {
			tf[i] = new JTextField();
		}
		dbcm = new DataBaseConnMkt();
	}
	
	public MarketingScEvent(MarketingScFrame mksf) {
		this();
		this.mksf = mksf;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton evt = (JButton)e.getSource();
		if(evt.getText().equals("삭제")) {
			deleteMarketing(mksf.getselectedId());
			mksf.setTable();
//			updateMarketing(mksf.getselectedId());
//			mksf.setTable();
//		}else if(evt.getText().equals("삭제")) {
//			
		}
	}


	//수정
//	private void updateMarketing(String selectedId) {
//		String[] title = {"예약번호", "사원번호", "매물번호", "고객명", "고객핸드폰", "예약날짜", "예약시간", "만날위치"};
//		JComponent[] updateText;
//		//사원아이디			//dbcm.db연결
//		MarketingScVO selectedvo = dbcm.getBookingId(selectedId);
//		updateText = new JComponent[16];
//		for (int i = 0; i < updateText.length; i++) {
//			if (i % 2 == 0)
//				updateText[i] = new JLabel(title[i / 2]);
//			else {
//				tf[i / 2] = new JTextField();
//				updateText[i] = tf[i / 2];
//			}
//		}
//		tf[0].setText(selectedvo.getBooking_id());
//		tf[1].setText(selectedvo.getName());
//		tf[2].setText(selectedvo.getRoom_id());
//		tf[3].setText(selectedvo.getCustomer_name());
//		tf[4].setText(selectedvo.getCustomer_tel());
//		tf[5].setText(selectedvo.getBooking_date());
//		tf[6].setText(selectedvo.getBooking_time());
//		tf[7].setText(selectedvo.getBooking_loc());
//		
//		if (JOptionPane.showConfirmDialog(null, updateText, "예약정보 입력",
//				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//			MarketingScVO vo = new MarketingScVO(selectedId, tf[1].getText(),tf[2].getText(),tf[3].getText()
//					,tf[4].getText(),tf[5].getText(),tf[6].getText(),tf[7].getText());
//			int result = dbcm.updateRow(vo);
//			if(result == -1) {
//				JOptionPane.showMessageDialog(null, "수정에 실패했습니다.");
//			}else {
//				JOptionPane.showMessageDialog(null, "예약정보를 수정하였습니다.");
//			}
//		}	
//	}
	
	//삭제
	private void deleteMarketing(String selectedId) {
		if(JOptionPane.showConfirmDialog(null, selectedId+"번 예약정보를 정말 삭제하시겠습니까?")==JOptionPane.YES_NO_OPTION) {
		 if(dbcm.deleteBooking(selectedId)==-1) {
			 JOptionPane.showMessageDialog(null, "삭제를 실패했습니다.");
			}else {
				JOptionPane.showMessageDialog(null, "예약정보를 삭제하였습니다.");
			}
		}
	}
}

	
	


