package M;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import V.AnimalView;

public class Inventory {
	private String name;
	private ArrayList<AnimalView> animals;
	private ArrayList<Weapon> weapons;
	private ArrayList<Expendable> expendables;
	private Stack<Item> tmpStack;
	private ArrayList<Animal> capturedAnimalList; // 들고 있는 동물 리스트 (=동물 주머니)
	private static Inventory i; // 인벤토리도 싱글톤으로 구현

	private Inventory() {
		name = "인벤토리";
		animals = new ArrayList<>();
		weapons = new ArrayList<>();
		expendables = new ArrayList<>();
		tmpStack = new Stack<>();
		this.capturedAnimalList = new ArrayList();
		
	}

	public ArrayList<AnimalView> list() {
		return animals;
	}

	public static Inventory getInstance() {
		if (i == null) {
			i = new Inventory();
		}
		return i;
	}

	// recieveItem과 recieveAnimal을 제네릭 사용해서 하나의 메소드로 합쳐줄 수 있을듯
	// public void recieveItem(Item i) {
	// //Item마다 name있으면 좋을듯
	// int checkNum=i.getType(); //아이템의 타입을 가져옴
	// switch(checkNum) {
	// case 0: //0은 무기
	// this.weapons.add(i);
	// System.out.println(i.getName()+"무기가 인벤토리 안에 들어갑니다");
	// break;
	// case 1: //1은 소비템
	// this.expendables.add(i);
	// System.out.println(i.getName()+"소비템이 인벤토리 안에 들어갑니다");
	// break;
	// }


	public void showAnimalList() {
		System.out.print("현재 Inventory에 있는 동물 : ");
		for (int i = 0; i < this.animals.size(); i++) {
			System.out.print(Inventory.getInstance().animals.get(i).getName() + " ");
		}
		System.out.println();
	}

	public void showExpendableList() {
		System.out.println("현재 Inventory에 있는 소모품");
		for (int i = 0; i < this.expendables.size(); i++) {
			System.out.print(Inventory.getInstance().getExpendables().get(i).getName()+ " ");
		}
		System.out.println();
	}

	public void showWeaponList() {
		System.out.println("현재 Inventory에 있는 무기");
		for (int i = 0; i < this.weapons.size(); i++) {
			System.out.print(Inventory.getInstance().getWeapons().get(i).getName()+ " ");
		}
		System.out.println();
	}

/*	public String checkType(Item i) {
		if (i.getType() == 0) {
			return "Weapon";
		}

		if (i.getType() == 1) {
			return "Expendable";
		}

		return null;
	}*/

	public void addThing(Item i) {
		if(i.flag>0 && i.flag<3){
			this.expendables.remove(i);
		}
		else if(i.flag>=3 && i.flag< 6){
			this.weapons.remove(i);
		}
	}

	public void removeThing(Item i) {
		if(i.flag>0 && i.flag<3){
			this.expendables.remove(i);
		}
		else if(i.flag>=3 && i.flag< 6){
			this.weapons.remove(i);
		}
	}

	public void addThing(AnimalView ani) {
		this.animals.add(ani);
	}

	public void removeThing(AnimalView ani) {
		this.animals.remove(ani);
	}

	public Item search(Item i) {
		int size;
		if (i.flag>=3 && i.flag< 6) {
			size = weapons.size();
			for (int n = 0; n < size; n++) {
				if (i.equals(weapons.get(n))) {
					return weapons.get(n);
				}
			}
		} else if (i.flag>0 && i.flag<3) {
			size = expendables.size();
			for (int n = 0; n < size; n++) {
				if (i.equals(expendables.get(n))) {
					return expendables.get(n);
				}
			}
		}
		return null;
	}

	public void search(AnimalView ani) {
		int size = animals.size();
		for (int i = 0; i < size; i++) {
			if (ani.getName().equals(animals.get(i).getName())) {
				System.out.print(animals.get(i).getName()+" "); // GUI에서는 객체자체를 보여주면 될 것 같습니다.
				//				ArrayList ret = new ArrayList();
			}
			else {
				System.out.println("찾는 동물이 없습니다!!!");
				break;
			}
		}
	}

	public ArrayList<AnimalView> getAnimals() {
		return animals;
	}

	public void setAnimals(ArrayList<AnimalView> animals) {
		this.animals = animals;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}

	public ArrayList<Expendable> getExpendables() {
		return expendables;
	}

	public void setExpendables(ArrayList<Expendable> expendables) {
		this.expendables = expendables;
	}

	public Stack<Item> getTmpStack() {
		return tmpStack;
	}

	public void setTmpStack(Stack<Item> tmpStack) {
		this.tmpStack = tmpStack;
	}

	public String getName() {
		return name;
	}



	public ArrayList<Animal> getCapturedAnimalList() {
		return capturedAnimalList;
	}

	public void setCapturedAnimalList(ArrayList<Animal> capturedAnimalList) {
		this.capturedAnimalList = capturedAnimalList;
	}

	public void setName(String name) {
		this.name = name;
	}


}
