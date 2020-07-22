package M;

public abstract class Drink extends Expendable {
	
	protected int effect;
	
	public Drink(String name, int price) {
		super(name, price);
	}
	
	public void showInfo() {
		System.out.println("========����========");
		System.out.println("�̸�: " + this.name);
		System.out.println("����: " + this.price);
		System.out.println("���� ���� ����: " + this.num);
		System.out.println("Ÿ��: �Һ� - ����");
		System.out.println("");
	}
	
	public void showLeftPotion(){
		int num = this.getNum();
		System.out.println(this.name + " ���!");
		this.setNum(--num);
		System.out.println(this.name + "���� ����: " + num);
	}
}
