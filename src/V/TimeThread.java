package V;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import C.Controller;

public class TimeThread extends Thread{ // 시간을 측정하는 스레드
	int minute,secend;
	JLabel timeLabel;
	TimeThread(JLabel timeLabel){
		//시간을 보여주는 레이블인 timeLabel을 컨트롤 하기 위해 timeLabel을 매개변수로 받음
		this.minute = 0;
		this.secend = 0;
		this.timeLabel = timeLabel;
	}
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000); // 1초
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			secend++;
			if(secend==60) {
				minute++;
				secend=0;
			}
			this.timeLabel.setText("Time "+minute+":"+secend);
		}
	}
}	
