package V;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HuntingDogView extends JLabel implements Runnable{
	int positionX=40*9, positionY=40*4; // ��ɰ� �ʱ� ��ġ
	int targetPointX, targetPointY; // �̵��ϰ��� �ϴ� ��ǥ ����
	boolean independentFromHunter; // true�̸� ��ɲ��� �����Ӱ� ����ȭ, false�̸� �񵿱�ȭ
	int power = 10; // ���ݷ� (Model)
	int speed  = 3; // �̵��ӵ�
	boolean activated = true; // ��ɰ��� run() ���� ������ ����. �������� true, ������������ false (������������ ��ɰ��� ���������� ������ �ʿ䰡 ���� ����)
	
	public HuntingDogView(){
		independentFromHunter = false;
		setBounds(positionX,positionY,40,40);
		this.setOpaque(true);
	}
	
	public void paintComponent(Graphics g) // �̹��� �׸���
	{
		ImageIcon Himg = new ImageIcon("HuntingDog.jpg");
		Image img = Himg.getImage();
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
	
	
	public void run() { // ��ɲ��� �����Ӱ� ������� �����̰� ��
		if(activated==false) return; // Zoo������ �۵� X
		independentFromHunter = true; // ��ɲ۰� ���������� ������ �� �ְ� ��
		int directionX, directionY; // -1, 0, 1 �� �ϳ��� ��. while�� �ȿ��� ��ġ ��ǥ�� ������ �Ͽ�, ��ġ ��ǥ ������Ű�ų�(0), �̵���ų ��(-1,1) ����
		
		if(targetPointX==positionX) directionX = 0; // ������ ��ġ�� ��ɰ��� ��ġ�� ������, 0�� �����Ͽ� ��ɰ��� �������� ������Ŵ
		else directionX = (targetPointX-positionX)/Math.abs(targetPointX-positionX); // target ������ x��ǥ��, ��ɰ��� ���� ��ġ�� x��ǥ���� �����ʿ� ������ +1, ���ʿ� ������  -1
		
		if(targetPointY==positionY) directionY = 0; // ������ ��ġ�� ��ɰ��� ��ġ�� ������, 0�� �����Ͽ� ��ɰ��� �������� ������Ŵ
		else directionY = (targetPointY-positionY)/Math.abs(targetPointY-positionY);// target ������ y��ǥ��, ��ɰ��� ���� ��ġ�� y��ǥ���� ���� ������ +1, �Ʒ��� ������  -1
		
		while(targetPointX!=positionX||positionX>=360||positionX<=0) { // ����) mapPanel�� ������ : 400x400, tile�� ������ : 40x40
			if(positionX>=0&&positionX<=360) { 
				positionX += directionX; // x��ǥ�� speed�� �ӵ��� 1�� ���� �Ǵ� ���� (�Ǵ� ����)
				setLocation(positionX,positionY);
			}
			else { // ���� ��踦 �Ѿ���� �� ��. positionX�� -1 �Ǵ� 361�� ���
				if(directionX>0) { // ���������� ���� ���� ��
					positionX-=2;
					setLocation(positionX,positionY);
					break;
				}
				else if(directionX<0) { // �������� ���� ���� ��
					positionX+=2;
					setLocation(positionX,positionY);
					break;
				}
				else {
					System.out.println("error");
				}
			}
			try {
				Thread.sleep(speed); // ������ �ӵ��� �����̰� �ϱ� ����
			} catch (InterruptedException e) {
			}
//			System.out.printf("x:%d y:%d / tx:%d, ty:%d\n",positionX, positionY,tx,ty); // ����� �뵵
		}
		
		while(targetPointY!=positionY||positionY>=360||positionY<=0) {
			if(positionY>=0&&positionY<=360) {
				positionY += directionY; // y��ǥ�� speed�� �ӵ��� 1�� ���� �Ǵ� ���� (�Ǵ� ����)
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
//			System.out.printf("x:%d y:%d / tx:%d, ty:%d\n",positionX, positionY,tx,ty); // ����� �뵵
		}
	}
}