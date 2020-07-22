package V;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import C.Controller;

public class TimeThread extends Thread{ // �ð��� �����ϴ� ������
	int minute,secend;
	JLabel timeLabel;
	TimeThread(JLabel timeLabel){
		//�ð��� �����ִ� ���̺��� timeLabel�� ��Ʈ�� �ϱ� ���� timeLabel�� �Ű������� ����
		this.minute = 0;
		this.secend = 0;
		this.timeLabel = timeLabel;
	}
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000); // 1��
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
