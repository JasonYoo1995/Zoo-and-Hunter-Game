package V;
import java.awt.Image;

import javax.swing.ImageIcon;

import C.Controller;
import M.Hunter;
import M.Zoo;


public class CarpenterView extends FitImage{
	int upgradeTime; // 우리를 몇번 업그레이드 했었는지
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
		this.upgradeTime = 0; /**플레이타임 중 우리를 업그레이드한 횟수**/
		this.money = 1000;
		this.setBounds(this.positionX, this.positionY,40,40);
		this.c=c;
	}
	
	public boolean ableToInteract(HunterView hunterView) { // 사냥꾼이 NPC에게 접근하였을 때 true 리턴 ('X'키 누를 때 호출되는 메소드)
		int Xdifferece = Math.abs(hunterView.getPositionX() - this.positionX); // NPC와 사냥꾼 사이의 x좌표 거리
		int Ydifferece = Math.abs(hunterView.getPositionY() - this.positionY); // NPC와 사냥꾼 사이의 y좌표 거리
		if(Xdifferece<40 && Ydifferece<40) {
			c.carpenter.interact(c.hunter); /**모델의 Hunter를 컨트롤러로부터 받아서 상호작용**/
			return true;
		}	
		return false;
	}
}