package frame.salesman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import conectdb.DataBaseConn;
import conectdb.DataBaseConnContract;
import conectdb.DataBaseConnRoom;
import frame.LoginFrame;
import vo.DealVO;
import vo.RoomVO;
import vo.SawonVO;
public class ContractFrame extends JFrame {
	JPanel contentPane;
	String rid;
	XSSFSheet sheet;
	DataBaseConnContract dbcc;
	JButton btFinish,btCancle ;
	JTextField[] tf,tfDate;
	JComboBox<String> cbsid,cbctel;
	public ContractFrame() {
		dbcc = new DataBaseConnContract();
		tf = new JTextField[12];
		btFinish = new JButton("입력완료");
		btCancle = new JButton("취소");
		//콤보박스 선언
		ArrayList<String> sarr = dbcc.getSawonId();
		ArrayList<String> carr = dbcc.getCustomerTel();
		cbsid = new JComboBox<String>(sarr.toArray(new String[sarr.size()]));
		cbctel = new JComboBox<String>(carr.toArray(new String[carr.size()]));
		cbsid.setBackground(Color.WHITE);
		cbctel.setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 400, 700);
		//센터패널
		JPanel pCenter = new JPanel();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(Color.WHITE);
		
		setContentPane(contentPane);
		pCenter.setBackground(Color.WHITE);
		pCenter.setLayout(new BorderLayout());
			//입력부 중앙
			JPanel pCenterMid = new JPanel();
			pCenterMid.setLayout(new GridLayout(13,1));
			JPanel[] ptxlb = new JPanel[12];
			String[] lbTitle = {"대리인 사원번호","임차인전화번호","임차인 주민등록번호","임대인주소","임대인 이름","임대인 전화번호","임대인 주민등록번호","사원 주소",
					"월세 지불일","기타 약속사항","계약금","중도금"};
			for (int i = 0; i < ptxlb.length; i++) {
				ptxlb[i] = new JPanel();
				ptxlb[i].setBackground(Color.WHITE);
				ptxlb[i].setLayout(new BorderLayout());
				ptxlb[i].add(new JLabel(lbTitle[i]),BorderLayout.NORTH);
				if(i==0) {
					ptxlb[i].add(cbsid);
				}else if(i==1) {
					ptxlb[i].add(cbctel);
				}
				else{
					tf[i] = new JTextField(40);
					ptxlb[i].add(tf[i],BorderLayout.CENTER);
				}
				
				pCenterMid.add(ptxlb[i]);
			}
			
			//날짜 입력 패널
			tfDate = new JTextField[3];
			JPanel pDate = new JPanel();
			JPanel pDateCenter = new JPanel();
			pDateCenter.setBackground(Color.WHITE);
			pDate.setBackground(Color.WHITE);
			String[] dateTitle = {"년","월","일"};
			for (int i = 0; i < dateTitle.length; i++) {
				tfDate[i] = new JTextField(5);
				pDateCenter.add(tfDate[i]);
				pDateCenter.add(new JLabel(dateTitle[i]));
			}
			pDate.setLayout(new BorderLayout());
			pDate.add(new JLabel("계약일"),BorderLayout.NORTH);
			pDate.add(pDateCenter,BorderLayout.CENTER);
			pCenterMid.add(pDate);
			pCenterMid.setBackground(Color.WHITE);
			pCenter.setBackground(Color.WHITE);
			pCenter.add(pCenterMid,BorderLayout.CENTER);
		//하단 버튼
			JPanel pSouth = new JPanel();
			pSouth.setLayout(new FlowLayout(FlowLayout.RIGHT,10,20));
			btFinish.setBackground(LoginFrame.mainColor);
			btCancle.setBackground(LoginFrame.mainColor);
			pSouth.add(btFinish);
			pSouth.add(btCancle);
			pSouth.setBackground(Color.WHITE);
			contentPane.add(pSouth,BorderLayout.SOUTH);
		contentPane.add(pCenter,BorderLayout.CENTER);
		EventProc();
	}
	public ContractFrame(String id) {
		this();
		this.rid = id;
		contentPane.setBorder(new TitledBorder(this.rid+"번 방 계약서 작성"));
//		setExcel();
	}
	void EventProc() {
		ContractEvent ce = new ContractEvent();
		btFinish.addActionListener(ce);
		btCancle.addActionListener(ce);
	}
	//엑셀수정 메소드
	void setExcel(){
		FileInputStream file;
		try {
			String[] info = {(String)cbsid.getSelectedItem(),rid,(String)cbctel.getSelectedItem()};
			ArrayList data = dbcc.getInfo(info);  //조인
			file = new FileInputStream("xlsx/contract.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file); //수정객체 가져오기
			sheet = workbook.getSheetAt(0); //시트생성
			//소재지 2,3 Z가 25 N이13
			setExcelCell(2, 3, (String)data.get(2));
			//면적 4,25 5,25 1평 = 3.3m2
			setExcelCell(3, 25, String.valueOf(Math.round(Double.parseDouble(((String)data.get(4)).substring(0, ((String)data.get(4)).length()-1))*3.305)));
			setExcelCell(4, 25, String.valueOf(Math.round(Double.parseDouble(((String)data.get(4)).substring(0, ((String)data.get(4)).length()-1))*3.305)));
			setExcelCell(5, 25, String.valueOf(Math.round(Double.parseDouble(((String)data.get(4)).substring(0, ((String)data.get(4)).length()-1))*3.305)));
			//28,1 기타 임차인과 임대인의 약속사항
			setExcelCell(28, 1, tf[9].getText());
			// 31,1 연도  31, 13 월 31,17 일
			Calendar c = Calendar.getInstance();
			//오늘 연 월 일 구하기
			setExcelCell(31,1,String.valueOf(c.get(Calendar.YEAR)));
			setExcelCell(31,13,String.valueOf(c.get(Calendar.MONTH)+1));
			setExcelCell(31,17,String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
			//32,5 임대인 주소 
			setExcelCell(32, 5, tf[3].getText());
			//33,5 임대인 주민
			setExcelCell(33, 5, tf[6].getText()); 
			//33,15 임대인 전화33,25 임대인 이름
			setExcelCell(33, 15, tf[5].getText());
			setExcelCell(33, 25, tf[4].getText());
			//34,5대리인 주소 34,25 성명
			setExcelCell(34, 5, tf[7].getText());
			setExcelCell(34, 25, (String)data.get(0));
			//35,5 임차인 주소 
			setExcelCell(35, 5, (String)data.get(8) );
			//36,5 임차인 주민
			setExcelCell(36, 5, tf[2].getText());
			//36,15 임차인 전화36,25 임차인 이름
			setExcelCell(36, 15, (String)data.get(6));
			setExcelCell(36, 25, (String)data.get(7));
			//37,5대리인 주소 37,25 성명
			setExcelCell(37, 5, tf[7].getText());
			setExcelCell(37, 25, (String)data.get(0));
			//영수자 성명 9,27
			setExcelCell(9, 27, (String)data.get(7));
			//콤마표기
			DecimalFormat formatter = new DecimalFormat("###,###");
			//8,5보증금 9,5계약금 10,5 중도금 11,5 잔금 12,5 월세
			setExcelCell(10, 5, tf[11].getText());
			//잔금은 보증금 - 계약
			//중도금 잔금 지불일시 할지말지
			//월세 지불일시 할지말지 12,18
			setExcelCell(12, 18, tf[8].getText());
			//8,16 보증금
			String price = formatter.format(data.get(3));
			setExcelCell(8, 5, price);
			setExcelCell(8, 16, price);
			int contractMoney = 0; //계약금
			contractMoney = Integer.parseInt(tf[10].getText());
			setExcelCell(9, 5, contractMoney);
			setExcelCell(11, 5, formatter.format(((Integer)data.get(3)-contractMoney)));
			setExcelCell(12, 5, formatter.format(data.get(5)));//월세
			//38부터 공인중개사
			//11,16 연도 11,20월 11,22 일
			String y = tfDate[0].getText();
			String m = tfDate[1].getText();
			String d = tfDate[2].getText();
			//중도금
			setExcelCell(10, 16, y);//연도
			setExcelCell(10, 20, m);//월
			setExcelCell(10, 22, d);//일
			//잔금
			setExcelCell(11, 16, y);//연도
			setExcelCell(11, 20, m);//월
			setExcelCell(11, 22, d);//일
			//2조 ~~ 13줄 18,21, 23
			setExcelCell(13, 18, y);//연도
			setExcelCell(13, 21, m);//월
			setExcelCell(13, 23, d);//일
			//2조 14줄 7,10 ,12
			String[] d_day = getDate(y, m, d, 730);
			setExcelCell(14, 7, d_day[0]);//연도
			setExcelCell(14, 10, d_day[1]);//월
			setExcelCell(14, 12, d_day[2]);//일
			//파일 내보내기
			FileOutputStream fos = new FileOutputStream("contract/"+(String)data.get(7)+"님_"+rid+"방 계약서.xlsx");
			workbook.write(fos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("파일이 없음"+e.getMessage());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("IO오류"+e1.getMessage());
		} 
	}
	void setExcelCell(int row, int col, String a) { //셀채우기 문자열
		XSSFRow rows=sheet.getRow(row);
		Cell cell=rows.getCell(col);
		cell.setCellValue(a);
	}
	void setExcelCell(int row, int col, int a) { //셀채우기 인트형
		XSSFRow rows=sheet.getRow(row);
		Cell cell=rows.getCell(col);
		cell.setCellValue(a);
	}
	class ContractEvent implements ActionListener {
		public ContractEvent() {
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Object evt = e.getSource();
			if(evt==btFinish) {
				boolean haveNull = false;
				for (int i = 2; i < tf.length; i++) {
					if(tf[i].getText().equals("")) {
						haveNull = true;
						JOptionPane.showMessageDialog(null, "값을 전부 채워주세요");
						break;
					}
				} 
				if(!haveNull) {
					setExcel();
					
					DataBaseConnRoom dbcm = new DataBaseConnRoom();
					//거래여부 true로 바꾸기
					RoomVO rvo = dbcm.getRoom(rid);
					rvo.setDeal_status(true);
					dbcm.updateRoom(rvo);
					DataBaseConn dbc = new DataBaseConn();
					//사원실적 증가 및 커미션 증가
					SawonVO svo = dbc.getSawon((String)cbsid.getSelectedItem());
					svo.setContract_count(svo.getContract_count()+1);
					svo.setCommission(svo.getCommission()+(int)Math.round((rvo.getRoom_price()*0.003)));
					dbc.updateRow(svo);
					dbcc.addDeal(new DealVO("", rid, (String)cbctel.getSelectedItem(), (String)cbsid.getSelectedItem(),
							tfDate[0].getText()+tfDate[1].getText()+tfDate[2].getText(), dbcm.getRoom(rid).getRoom_price()));
					dispose();
				}
			}else if(evt ==btCancle) {
				dispose();
			}
		}
	}
	// 일수 계산 y 연도 m 월 d 일 a 계산수
	String[] getDate(String y,String m,String d, int a) {
		Calendar cal = new GregorianCalendar(Integer.parseInt(y),Integer.parseInt(m),Integer.parseInt(d));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		cal.add(Calendar.DAY_OF_MONTH, a);
		StringTokenizer st = new StringTokenizer(sdf.format(cal.getTime()),"/");
		String[] date = new String[3];
		for (int i = 0; st.hasMoreTokens(); i++) {
			date[i] = st.nextToken();
		}
		return date;
	}
}
