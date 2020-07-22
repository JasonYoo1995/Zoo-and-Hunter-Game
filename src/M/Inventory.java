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
	private ArrayList<Animal> capturedAnimalList; // ��� �ִ� ���� ����Ʈ (=���� �ָӴ�)
	private static Inventory i; // �κ��丮�� �̱������� ����

	private Inventory() {
		name = "�κ��丮";
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

	// recieveItem�� recieveAnimal�� ���׸� ����ؼ� �ϳ��� �޼ҵ�� ������ �� ������
	// public void recieveItem(Item i) {
	// //Item���� name������ ������
	// int checkNum=i.getType(); //�������� Ÿ���� ������
	// switch(checkNum) {
	// case 0: //0�� ����
	// this.weapons.add(i);
	// System.out.println(i.getName()+"���Ⱑ �κ��丮 �ȿ� ���ϴ�");
	// break;
	// case 1: //1�� �Һ���
	// this.expendables.add(i);
	// System.out.println(i.getName()+"�Һ����� �κ��丮 �ȿ� ���ϴ�");
	// break;
	// }


	public void showAnimalList() {
		System.out.print("���� Inventory�� �ִ� ���� : ");
		for (int i = 0; i < this.animals.size(); i++) {
			System.out.print(Inventory.getInstance().animals.get(i).getName() + " ");
		}
		System.out.println();
	}

	public void showExpendableList() {
		System.out.println("���� Inventory�� �ִ� �Ҹ�ǰ");
		for (int i = 0; i < this.expendables.size(); i++) {
			System.out.print(Inventory.getInstance().getExpendables().get(i).getName()+ " ");
		}
		System.out.println();
	}

	public void showWeaponList() {
		System.out.println("���� Inventory�� �ִ� ����");
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
				System.out.print(animals.get(i).getName()+" "); // GUI������ ��ü��ü�� �����ָ� �� �� �����ϴ�.
				//				ArrayList ret = new ArrayList();
			}
			else {
				System.out.println("ã�� ������ �����ϴ�!!!");
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
