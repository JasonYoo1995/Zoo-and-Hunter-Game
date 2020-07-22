package V;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HuntingDogView extends JLabel implements Runnable{
	int positionX=40*9, positionY=40*4; // 사냥개 초기 위치
	int targetPointX, targetPointY; // 이동하고자 하는 목표 지점
	boolean independentFromHunter; // true이면 사냥꾼의 움직임과 동기화, false이면 비동기화
	int power = 10; // 공격력 (Model)
	int speed  = 3; // 이동속도
	boolean activated = true; // 사냥개의 run() 실행 유무를 제어. 숲에서는 true, 동물원에서는 false (동물원에서는 사냥개가 독립적으로 움직일 필요가 없기 때문)
	
	public HuntingDogView(){
		independentFromHunter = false;
		setBounds(positionX,positionY,40,40);
		this.setOpaque(true);
	}
	
	public void paintComponent(Graphics g) // 이미지 그리기
	{
		ImageIcon Himg = new ImageIcon("HuntingDog.jpg");
		Image img = Himg.getImage();
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
	
	
	public void run() { // 사냥꾼의 움직임과 상관없이 움직이게 함
		if(activated==false) return; // Zoo에서는 작동 X
		independentFromHunter = true; // 사냥꾼과 독립적으로 움직일 수 있게 함
		int directionX, directionY; // -1, 0, 1 중 하나가 들어감. while문 안에서 위치 좌표에 덧셈을 하여, 위치 좌표 유지시키거나(0), 이동시킬 수(-1,1) 있음
		
		if(targetPointX==positionX) directionX = 0; // 동물의 위치와 사냥개의 위치가 같으면, 0을 대입하여 사냥개의 움직임을 정지시킴
		else directionX = (targetPointX-positionX)/Math.abs(targetPointX-positionX); // target 동물의 x좌표가, 사냥개의 현재 위치의 x좌표보다 오른쪽에 있으면 +1, 왼쪽에 있으면  -1
		
		if(targetPointY==positionY) directionY = 0; // 동물의 위치와 사냥개의 위치가 같으면, 0을 대입하여 사냥개의 움직임을 정지시킴
		else directionY = (targetPointY-positionY)/Math.abs(targetPointY-positionY);// target 동물의 y좌표가, 사냥개의 현재 위치의 y좌표보다 위에 있으면 +1, 아래에 있으면  -1
		
		while(targetPointX!=positionX||positionX>=360||positionX<=0) { // 참고) mapPanel의 사이즈 : 400x400, tile의 사이즈 : 40x40
			if(positionX>=0&&positionX<=360) { 
				positionX += directionX; // x좌표가 speed의 속도로 1씩 증가 또는 감소 (또는 정지)
				setLocation(positionX,positionY);
			}
			else { // 맵의 경계를 넘어가려고 할 때. positionX가 -1 또는 361인 경우
				if(directionX>0) { // 오른쪽으로 가고 있을 떄
					positionX-=2;
					setLocation(positionX,positionY);
					break;
				}
				else if(directionX<0) { // 왼쪽으로 가고 있을 떄
					positionX+=2;
					setLocation(positionX,positionY);
					break;
				}
				else {
					System.out.println("error");
				}
			}
			try {
				Thread.sleep(speed); // 적당한 속도로 움직이게 하기 위함
			} catch (InterruptedException e) {
			}
//			System.out.printf("x:%d y:%d / tx:%d, ty:%d\n",positionX, positionY,tx,ty); // 디버깅 용도
		}
		
		while(targetPointY!=positionY||positionY>=360||positionY<=0) {
			if(positionY>=0&&positionY<=360) {
				positionY += directionY; // y좌표가 speed의 속도로 1씩 증가 또는 감소 (또는 정지)
				setLocation(positionX,positionY);
			}
			else {
				if(directionY>0) {
					positionY-=2;
					setLocation(positionX,positionY);
					break;
				}
				else if(directionY<0) {
					positionY+=2;
					setLocation(positionX,positionY);
					break;
				}
			}
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
			}
//			System.out.printf("x:%d y:%d / tx:%d, ty:%d\n",positionX, positionY,tx,ty); // 디버깅 용도
		}
	}
}