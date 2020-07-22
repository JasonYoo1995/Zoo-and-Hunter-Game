package V;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

import C.Controller;
import M.Hunter;

public class NurseView extends FitImage{
	int positionX;
	int positionY;
	
	public C.Controller c; /**C패키지의 Controller 클래스 객체 받아옴**/
	
	public NurseView(Controller c){
		ImageIcon i = new ImageIcon("Nurse.png"); 
		Image img = i.getImage(); 
		super.img = img;
		this.positionX = 0;
		this.positionY = 200;
		this.setBounds(this.positionX, this.positionY,40,40);
		this.c=c;
	}
	boolean ableToInteract(HunterView hunter) {
		int Xdifferece = Math.abs(hunter.getPositionX() - this.positionX); // NPC와 사냥꾼 사이의 x좌표 거리
		int Ydifferece = Math.abs(hunter.getPositionY() - this.positionY); // NPC와 사냥꾼 사이의 y좌표 거리
		if(Xdifferece<40 && Ydifferece<40) {
			c.nurse.interact(c.hunter);
			return true;
		}	
		return false;
	}
	public void heal(Hunter hunter) {
		c.nurse.heal(hunter); 
		/**HunterView에서 Controller에서 모델 Hunter 객체를 받아서 넘겨줬기 때문에,**/
		/**그래서 NurseView에서는 Model nurse의 heal만 호출하면 됨**/
	}
}