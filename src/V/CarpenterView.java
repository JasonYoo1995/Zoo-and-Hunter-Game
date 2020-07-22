package V;
import java.awt.Image;

import javax.swing.ImageIcon;

import C.Controller;
import M.Hunter;
import M.Zoo;


public class CarpenterView extends FitImage{
	int upgradeTime; // �츮�� ��� ���׷��̵� �߾�����
	int positionX;
	int positionY;
	int money;
	Controller c;
	
	public CarpenterView(Controller c){
		ImageIcon i = new ImageIcon("Carpenter.png"); 
		Image img = i.getImage(); 
		super.img = img;
		this.positionX = 360;
		this.positionY = 40;
		this.upgradeTime = 0; /**�÷���Ÿ�� �� �츮�� ���׷��̵��� Ƚ��**/
		this.money = 1000;
		this.setBounds(this.positionX, this.positionY,40,40);
		this.c=c;
	}
	
	public boolean ableToInteract(HunterView hunterView) { // ��ɲ��� NPC���� �����Ͽ��� �� true ���� ('X'Ű ���� �� ȣ��Ǵ� �޼ҵ�)
		int Xdifferece = Math.abs(hunterView.getPositionX() - this.positionX); // NPC�� ��ɲ� ������ x��ǥ �Ÿ�
		int Ydifferece = Math.abs(hunterView.getPositionY() - this.positionY); // NPC�� ��ɲ� ������ y��ǥ �Ÿ�
		if(Xdifferece<40 && Ydifferece<40) {
			c.carpenter.interact(c.hunter); /**���� Hunter�� ��Ʈ�ѷ��κ��� �޾Ƽ� ��ȣ�ۿ�**/
			return true;
		}	
		return false;
	}
}