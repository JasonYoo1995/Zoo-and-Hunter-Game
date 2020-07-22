package V;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import C.Controller;
//skyView에서 사냥꾼이 왼쪽(=동물원)으로 이동하는 걸 보여주는 Thread
public class MoveThread extends Thread{
	SkyViewHunter skyViewHunter; // 맵 선택시 덩치가 커진 사냥꾼 레이블
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
		// 마우스로 동물원 버튼 클릭 시, run() 메소드가 실행되어 왼쪽으로 이동 시작
		for(int i=0; skyViewHunter.x>200; i++) {
			skyViewHunter.x--;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // 이동 끝
		basicFrame.setContentPane(zooPanel); // 화면 전환
		hunter.requestFocus(); // hunter에게 focusing이 되어야, 키보드를 눌렀을 때 움직임의 신호가 hunter에게 전달됨. 
		SystemLog.getInstance().printLog("동물원으로 이동합니다."); // 시스템 로그에 띄움
	}
}
