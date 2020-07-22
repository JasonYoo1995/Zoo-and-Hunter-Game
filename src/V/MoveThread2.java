package V;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import C.Controller;
//skyView에서 사냥꾼이 오른쪽으로 이동하는 걸 보여주는 Thread
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
		// 마우스로 동물원 버튼 클릭 시, run() 메소드가 실행되어 왼쪽으로 이동 시작
		for(int i=0; skyViewHunter.x<700; i++) {
			skyViewHunter.x++;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} // 이동 끝
		c.basicframe.setContentPane(forestPanel); // 화면 전환
		hunter.requestFocus(); // hunter에게 focusing이 되어야, 키보드를 눌렀을 때 움직임의 신호가 hunter에게 전달됨. 
		SystemLog.getInstance().printLog("숲속으로 이동합니다."); // 시스템 로그에 띄움
		
	}
}
