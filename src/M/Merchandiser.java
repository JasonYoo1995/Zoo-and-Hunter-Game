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
			instance = new Merchandiser("잡상인",99999999);
		}
		return instance;
	}

	public void buy(Item i, Hunter h) {
		int temp = h.getMoney();

		if(Inventory.getInstance().search(i) != null) {         
			Inventory.getInstance().removeThing(i);
			System.out.println("========거래 완료!========");
			System.out.println(i.getName() + " 판매 완료");
			System.out.println("현재 소지금: " + temp + " -> " + (temp + i.getPrice()));
//			if(i.getType() == 1) {
//				int tempNum = ((Expendable) i).getNum();
//				System.out.println("현재 보유량: " + tempNum + "개");
//			}
//			System.out.println("");
//			temp += i.getPrice();
//			h.setMoney(temp);
//		} else {
//			System.out.println("========거래 실패!========");
//			System.err.println("아이템이 존재하지 않습니다!");
		}
	}

	public void buy(Animal ani, Hunter h) {
		int temp = h.getMoney();

//		Inventory.getInstance().removeThing(ani);
		System.out.println("========거래 완료!========");
		System.out.println(ani.getName() + " 판매 완료");
		System.out.println("현재 소지금: " + temp + " -> " + (temp + ani.getValue()));
		temp += ani.getValue();
		h.setMoney(temp);
		System.out.println("");
	}

	public void sell(Item i, Hunter h) {
		int temp = h.getMoney();
		if(temp >= i.getPrice()) {
			Inventory.getInstance().addThing(i);
			System.out.println("========구매 완료!========");
			System.out.println(i.getName() + " 구매!");
			System.out.println("현재 소지금: " + temp + " -> " + (temp - i.getPrice()));
			temp -= i.getPrice();
			h.setMoney(temp);
			Merchandiser.getInstance().expendableList.remove(i);
			Merchandiser.getInstance().weaponList.remove(i);
		} else {
			System.out.println("========구매 실패!========");
			System.out.println("돈이 부족합니다!");
			System.out.println("");
		}
	}

	public void sell(Animal a, Hunter h){ // pet을 Hunter에게 팝니다.

	}

	public void addExpendable(Expendable exp){
		this.expendableList.add(exp);
	}

	public void showWeaponList(){
		System.out.print("상인이 파는 무기 : ");
		for (int i = 0; i < Merchandiser.getInstance().weaponList.size(); i++) {
			System.out.print(Merchandiser.getInstance().weaponList.get(i).getName()+" ");
		}
		System.out.println();
	}

	public void showExpendableList(){
		System.out.print("상인이 파는 소모품 : ");
		for (int i = 0; i < Merchandiser.getInstance().expendableList.size(); i++) {
			System.out.print(Merchandiser.getInstance().expendableList.get(i).getName()+" ");
		}
		System.out.println();
	}

	public void showAllList(){
		System.out.print("상인이 파는 물건 : ");
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
		System.out.println("어서오십쇼~ 어떻게 오셨을까~? "+h.getName()+"!");
	}
}
