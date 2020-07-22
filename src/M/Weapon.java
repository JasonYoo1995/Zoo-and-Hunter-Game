package M;

public abstract class Weapon extends Item {

	protected int damage;
	protected int range;
	
	public Weapon(String name, int price) {
		super(name, price);
		Merchandiser.getInstance().weaponList.add(this);
	}

	public int use() {
		System.out.println(this.name + "���!");
		return 0;
	}

	public void showInfo() {
		System.out.println("========����========");
		System.out.println("�̸�: " + this.name);
		System.out.println("����: " + this.price);
		System.out.println("Ÿ��: ����");
		System.out.println("");
	}
}
