package M;

import java.util.ArrayList;

import V.CageView;
import C.Controller;

public class Cage {
	private int level;
	private ArrayList<Animal> cageAnimalList;
	
	public int num; // �츮 ��ȣ
	public int price; // �츮 ����
	public String name; // �츮 �̸�
	public CageView backlink=null;
	
	public Cage(int count){
		this.num = count;
		level = 0; /**�ʱ�ȭ�� �����ڿ��� ���ִ� �� ���ٰ� �������� �׷��� �� ���Ƽ� ����� �ű�**/
		cageAnimalList = new ArrayList<Animal>();
		price=50;
		name="�μ��� �����츮";
	}

	
	public boolean setStatus(){
		switch(Carpenter.getInstance().getUpgradeTime()/2){
		case 0:
			this.level = 1;
			this.name = "�����츮";
			this.price = 50;
			return true;
		case 1:
			this.level = 2;
			this.name = "öâ�츮";
			this.price = 500;
			return true;
		case 2:
			this.level = 3;
			this.name = "����츮";
			this.price = 2000;
			return true;
		default:
			return false;
		}
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	public ArrayList<Animal> getCageAnimalList() {
		return cageAnimalList;
	}


	public void setCageAnimalList(ArrayList<Animal> cageAnimalList) {
		this.cageAnimalList = cageAnimalList;
	}
}
