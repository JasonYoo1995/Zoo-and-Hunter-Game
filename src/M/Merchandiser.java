package M;

import java.util.ArrayList;

public class Merchandiser extends NPC{
	private static Merchandiser instance = null;
	Item i;
	ArrayList<Item> weaponList;
	ArrayList<Item> expendableList;

	private Merchandiser(String name, int money) {
		// TODO Auto-generated constructor stub
		super(name, money);
		weaponList = new ArrayList<Item>();
		expendableList = new ArrayList<Item>();
	}

	public static Merchandiser getInstance(){
		if(instance == null){
			instance = new Merchandiser("�����",99999999);
		}
		return instance;
	}

	public void buy(Item i, Hunter h) {
		int temp = h.getMoney();

		if(Inventory.getInstance().search(i) != null) {         
			Inventory.getInstance().removeThing(i);
			System.out.println("========�ŷ� �Ϸ�!========");
			System.out.println(i.getName() + " �Ǹ� �Ϸ�");
			System.out.println("���� ������: " + temp + " -> " + (temp + i.getPrice()));
//			if(i.getType() == 1) {
//				int tempNum = ((Expendable) i).getNum();
//				System.out.println("���� ������: " + tempNum + "��");
//			}
//			System.out.println("");
//			temp += i.getPrice();
//			h.setMoney(temp);
//		} else {
//			System.out.println("========�ŷ� ����!========");
//			System.err.println("�������� �������� �ʽ��ϴ�!");
		}
	}

	public void buy(Animal ani, Hunter h) {
		int temp = h.getMoney();

//		Inventory.getInstance().removeThing(ani);
		System.out.println("========�ŷ� �Ϸ�!========");
		System.out.println(ani.getName() + " �Ǹ� �Ϸ�");
		System.out.println("���� ������: " + temp + " -> " + (temp + ani.getValue()));
		temp += ani.getValue();
		h.setMoney(temp);
		System.out.println("");
	}

	public void sell(Item i, Hunter h) {
		int temp = h.getMoney();
		if(temp >= i.getPrice()) {
			Inventory.getInstance().addThing(i);
			System.out.println("========���� �Ϸ�!========");
			System.out.println(i.getName() + " ����!");
			System.out.println("���� ������: " + temp + " -> " + (temp - i.getPrice()));
			temp -= i.getPrice();
			h.setMoney(temp);
			Merchandiser.getInstance().expendableList.remove(i);
			Merchandiser.getInstance().weaponList.remove(i);
		} else {
			System.out.println("========���� ����!========");
			System.out.println("���� �����մϴ�!");
			System.out.println("");
		}
	}

	public void sell(Animal a, Hunter h){ // pet�� Hunter���� �˴ϴ�.

	}

	public void addExpendable(Expendable exp){
		this.expendableList.add(exp);
	}

	public void showWeaponList(){
		System.out.print("������ �Ĵ� ���� : ");
		for (int i = 0; i < Merchandiser.getInstance().weaponList.size(); i++) {
			System.out.print(Merchandiser.getInstance().weaponList.get(i).getName()+" ");
		}
		System.out.println();
	}

	public void showExpendableList(){
		System.out.print("������ �Ĵ� �Ҹ�ǰ : ");
		for (int i = 0; i < Merchandiser.getInstance().expendableList.size(); i++) {
			System.out.print(Merchandiser.getInstance().expendableList.get(i).getName()+" ");
		}
		System.out.println();
	}

	public void showAllList(){
		System.out.print("������ �Ĵ� ���� : ");
		for (int i = 0; i < Merchandiser.getInstance().weaponList.size(); i++) {
			System.out.print(Merchandiser.getInstance().weaponList.get(i).getName()+" ");
		}
		for (int i = 0; i < Merchandiser.getInstance().expendableList.size(); i++) {
			System.out.print(Merchandiser.getInstance().expendableList.get(i).getName()+" ");
		}
		System.out.println();
	}

	@Override
	public void interact(Hunter h) {
		// TODO Auto-generated method stub
		System.out.println("����ʼ�~ ��� ��������~? "+h.getName()+"!");
	}
}
