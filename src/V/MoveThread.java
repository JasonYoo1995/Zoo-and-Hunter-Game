package V;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import C.Controller;
//skyView���� ��ɲ��� ����(=������)���� �̵��ϴ� �� �����ִ� Thread
public class MoveThread extends Thread{
	SkyViewHunter skyViewHunter; // �� ���ý� ��ġ�� Ŀ�� ��ɲ� ���̺�
	HunterView hunter;
	JPanel zooPanel;
	TimeThread timeThread;
	JFrame basicFrame;
	Controller c;
	MoveThread(SkyViewHunter skyViewHunter, TimeThread timeThread, Controller c){
		this.c = c;
		this.skyViewHunter = skyViewHunter;
		this.timeThread = timeThread;
		this.hunter = c.hunterView;
		this.zooPanel = c.zooPanel;
		this.basicFrame = c.basicframe;
	}
	public void run() {
		// ���콺�� ������ ��ư Ŭ�� ��, run() �޼ҵ尡 ����Ǿ� �������� �̵� ����
		for(int i=0; skyViewHunter.x>200; i++) {
			skyViewHunter.x--;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // �̵� ��
		basicFrame.setContentPane(zooPanel); // ȭ�� ��ȯ
		hunter.requestFocus(); // hunter���� focusing�� �Ǿ��, Ű���带 ������ �� �������� ��ȣ�� hunter���� ���޵�. 
		SystemLog.getInstance().printLog("���������� �̵��մϴ�."); // �ý��� �α׿� ���
	}
}
