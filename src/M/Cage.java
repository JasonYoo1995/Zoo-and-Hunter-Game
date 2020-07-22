package M;

import java.util.ArrayList;

import V.CageView;
import C.Controller;

public class Cage {
	private int level;
	private ArrayList<Animal> cageAnimalList;
	
	public int num; // 우리 번호
	public int price; // 우리 가격
	public String name; // 우리 이름
	public CageView backlink=null;
	
	public Cage(int count){
		this.num = count;
		level = 0; /**초기화는 생성자에서 해주는 게 좋다고 교수님이 그랬던 것 같아서 여기로 옮김**/
		cageAnimalList = new ArrayList<Animal>();
		price=50;
		name="부서진 나무우리";
	}

	
	public boolean setStatus(){
		switch(Carpenter.getInstance().getUpgradeTime()/2){
		case 0:
			this.level = 1;
			this.name = "나무우리";
			this.price = 50;
			return true;
		case 1:
			this.level = 2;
			this.name = "철창우리";
			this.price = 500;
			return true;
		case 2:
			this.level = 3;
			this.name = "전기우리";
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
