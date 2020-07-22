package M;

public abstract class Drink extends Expendable {
	
	protected int effect;
	
	public Drink(String name, int price) {
		super(name, price);
	}
	
	public void showInfo() {
		System.out.println("========정보========");
		System.out.println("이름: " + this.name);
		System.out.println("가격: " + this.price);
		System.out.println("현재 보유 개수: " + this.num);
		System.out.println("타입: 소비 - 포션");
		System.out.println("");
	}
	
	public void showLeftPotion(){
		int num = this.getNum();
		System.out.println(this.name + " 사용!");
		this.setNum(--num);
		System.out.println(this.name + "남은 개수: " + num);
	}
}
