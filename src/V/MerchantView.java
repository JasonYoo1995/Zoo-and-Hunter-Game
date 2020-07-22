package V;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import C.Controller;
import M.Animal;
import M.Hunter;

public class MerchantView extends FitImage{
	int positionX;
	int positionY;
	int money;
	ArrayList<Animal> animalList; // 구매한 동물 리스트
	Controller c;
	public MerchantView(Controller c){
		this.c = c;
		ImageIcon i = new ImageIcon("Merchant.png"); 
		Image img = i.getImage(); 
		super.img = img;
		this.positionX = this.positionY = 160;
		this.money = 1000;
		this.animalList = new ArrayList();
		this.setBounds(this.positionX, this.positionY,40,40);
	}
	public boolean ableToInteract(HunterView hunter) { // 'X'를 눌렀을 때, 사냥꾼이 NPC와 근접하여 있으면, true를 리턴하여 interact를 할 수 있음
		int Xdifferece = Math.abs(hunter.getPositionX() - this.positionX); // NPC와 사냥꾼 사이의 x좌표 거리
		int Ydifferece = Math.abs(hunter.getPositionY() - this.positionY); // NPC와 사냥꾼 사이의 y좌표 거리
		if(Xdifferece<40 && Ydifferece<40) { // 근접하여 있으면 if문 통과 하여 true 리턴
			return true;
		}	
		return false;
	}
	public void buyAnimal(Hunter hunter, Animal animal) { // 동물 구매
		this.animalList.add(animal); // 헌터에게 받은 동물을 '구매한 동물 리스트'에 추가
		hunter.setMoney(hunter.getMoney()+animal.getValue()); // 헌터에게 돈 지급
		SystemLog.getInstance().printLog(animal.getName()+"을 판매하여, "+animal.getValue()+"골드를 벌었습니다.");
	}
}