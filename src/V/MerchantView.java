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
	ArrayList<Animal> animalList; // ������ ���� ����Ʈ
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
	public boolean ableToInteract(HunterView hunter) { // 'X'�� ������ ��, ��ɲ��� NPC�� �����Ͽ� ������, true�� �����Ͽ� interact�� �� �� ����
		int Xdifferece = Math.abs(hunter.getPositionX() - this.positionX); // NPC�� ��ɲ� ������ x��ǥ �Ÿ�
		int Ydifferece = Math.abs(hunter.getPositionY() - this.positionY); // NPC�� ��ɲ� ������ y��ǥ �Ÿ�
		if(Xdifferece<40 && Ydifferece<40) { // �����Ͽ� ������ if�� ��� �Ͽ� true ����
			return true;
		}	
		return false;
	}
	public void buyAnimal(Hunter hunter, Animal animal) { // ���� ����
		this.animalList.add(animal); // ���Ϳ��� ���� ������ '������ ���� ����Ʈ'�� �߰�
		hunter.setMoney(hunter.getMoney()+animal.getValue()); // ���Ϳ��� �� ����
		SystemLog.getInstance().printLog(animal.getName()+"�� �Ǹ��Ͽ�, "+animal.getValue()+"��带 �������ϴ�.");
	}
}