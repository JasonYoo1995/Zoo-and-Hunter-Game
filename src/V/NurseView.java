package V;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

import C.Controller;
import M.Hunter;

public class NurseView extends FitImage{
	int positionX;
	int positionY;
	
	public C.Controller c; /**C��Ű���� Controller Ŭ���� ��ü �޾ƿ�**/
	
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
		int Xdifferece = Math.abs(hunter.getPositionX() - this.positionX); // NPC�� ��ɲ� ������ x��ǥ �Ÿ�
		int Ydifferece = Math.abs(hunter.getPositionY() - this.positionY); // NPC�� ��ɲ� ������ y��ǥ �Ÿ�
		if(Xdifferece<40 && Ydifferece<40) {
			c.nurse.interact(c.hunter);
			return true;
		}	
		return false;
	}
	public void heal(Hunter hunter) {
		c.nurse.heal(hunter); 
		/**HunterView���� Controller���� �� Hunter ��ü�� �޾Ƽ� �Ѱ���� ������,**/
		/**�׷��� NurseView������ Model nurse�� heal�� ȣ���ϸ� ��**/
	}
}