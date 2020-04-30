package frame.salesman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class ChartFrame extends JFrame {
	public JRadioButton cbS,cbC,cbR;
	JPanel pMainCenter;
	public ChartFrame() {
		ButtonGroup group = new ButtonGroup();
		//라디오버튼 생성
		setTitle("차트");
		cbS = new JRadioButton("직급별 급여",true);
		cbC = new JRadioButton("사원별 실적");
		cbR = new JRadioButton("건물별 예약 수");
		cbS.setBackground(Color.WHITE);
		cbC.setBackground(Color.WHITE);
		cbR.setBackground(Color.WHITE);
		group.add(cbS);
		group.add(cbC);
		group.add(cbR);
		setBackground(Color.WHITE);
		setBounds(0, 100, 1960, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel pMain = new JPanel();
		pMain.setLayout(new BorderLayout());
		pMain.setBackground(Color.WHITE);
			JPanel pMainTop = new JPanel();
			pMainTop.setBackground(Color.WHITE);
			pMainTop.setBorder(new TitledBorder("차트 선택"));
			pMainTop.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
			pMainTop.add(cbS);
			pMainTop.add(cbC);
			pMainTop.add(cbR);
			//차트부분
			pMainCenter = new JPanel();
			pMainCenter.setBackground(Color.WHITE);
			pMainCenter.setLayout(new GridLayout(1,1));
			pMainCenter.removeAll();
			MakeSalChart mc = new MakeSalChart();
			JFreeChart jfc = mc.getChart();
			jfc.getPlot().setBackgroundPaint(Color.WHITE);
			jfc.setBackgroundPaint(Color.WHITE);
			ChartPanel chartPanel=new ChartPanel(jfc); 
			chartPanel.setBackground(Color.WHITE);
			pMainCenter.add(chartPanel,BorderLayout.CENTER);
			pMainCenter.revalidate();
			pMainCenter.repaint();
		pMain.add(pMainCenter,BorderLayout.CENTER);
		pMain.add(pMainTop,BorderLayout.NORTH);
		add(pMain);
		eventProc();
	}
	void eventProc() {
		ChartEvent ce = new ChartEvent();
		cbS.addItemListener(ce);
		cbC.addItemListener(ce);
		cbR.addItemListener(ce);
	}
	class ChartEvent implements ItemListener{
		public ChartEvent() {
			
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(cbS.isSelected()) {
				pMainCenter.removeAll();
				MakeSalChart mc = new MakeSalChart();
				JFreeChart jfc = mc.getChart();
				jfc.getPlot().setBackgroundPaint(Color.WHITE);
				jfc.setBackgroundPaint(Color.WHITE);
				ChartPanel chartPanel=new ChartPanel(jfc); 
				chartPanel.setBackground(Color.WHITE);
				pMainCenter.add(chartPanel,BorderLayout.CENTER);
				pMainCenter.revalidate();
				pMainCenter.repaint();
			}else if(cbC.isSelected()) {
				pMainCenter.removeAll();
				MakeContractChart mc = new MakeContractChart();
				JFreeChart jfc = mc.getChart();
				jfc.getPlot().setBackgroundPaint(Color.WHITE);
				jfc.setBackgroundPaint(Color.WHITE);
				ChartPanel chartPanel=new ChartPanel(jfc); 
				chartPanel.setBackground(Color.WHITE);
				pMainCenter.add(chartPanel,BorderLayout.CENTER);
				pMainCenter.revalidate();
				pMainCenter.repaint();
			}else if(cbR.isSelected()) {
				pMainCenter.removeAll();
				MakeRoomChart mc = new MakeRoomChart();
				JFreeChart jfc = mc.getChart();
				jfc.getPlot().setBackgroundPaint(Color.WHITE);
				jfc.setBackgroundPaint(Color.WHITE);
				ChartPanel chartPanel=new ChartPanel(jfc); 
				chartPanel.setBackground(Color.WHITE);
				pMainCenter.add(chartPanel,BorderLayout.CENTER);
				pMainCenter.revalidate();
				pMainCenter.repaint();
			}
		}
	}
}
