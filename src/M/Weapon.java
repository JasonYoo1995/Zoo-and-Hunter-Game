package M;

public abstract class Weapon extends Item {

	protected int damage;
	protected int range;
	
	public Weapon(String name, int price) {
		super(name, price);
		Merchandiser.getInstance().weaponList.add(this);
	}

	public int use() {
		System.out.println(this.name + "사용!");
		return 0;
	}

	public void showInfo() {
		System.out.println("========정보========");
		System.out.println("이름: " + this.name);
		System.out.println("가격: " + this.price);
		System.out.println("타입: 무기");
		System.out.println("");
	}
}
