package V;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import C.Controller;
//skyView���� ��ɲ��� ���������� �̵��ϴ� �� �����ִ� Thread
public class MoveThread2 extends Thread{
	SkyViewHunter skyViewHunter;
	HunterView hunter;
	HuntingDogView huntingDog;
	JPanel forestPanel;
	JPanel map;
	TimeThread timeth;
	Controller c;
	MoveThread2(SkyViewHunter skyHunter, TimeThread thread, Controller c){
		this.c = c;
		skyViewHunter = skyHunter;
		hunter = c.hunterView;
		huntingDog = c.huntingDog;
		forestPanel = c.forestPanel;
		map = ((ForestPanel)forestPanel).map;
		timeth = thread;
	}
	public void run() {
		// ���콺�� ������ ��ư Ŭ�� ��, run() �޼ҵ尡 ����Ǿ� �������� �̵� ����
		for(int i=0; skyViewHunter.x<700; i++) {
			skyViewHunter.x++;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} // �̵� ��
		c.basicframe.setContentPane(forestPanel); // ȭ�� ��ȯ
		hunter.requestFocus(); // hunter���� focusing�� �Ǿ��, Ű���带 ������ �� �������� ��ȣ�� hunter���� ���޵�. 
		SystemLog.getInstance().printLog("�������� �̵��մϴ�."); // �ý��� �α׿� ���
		
	}
}
