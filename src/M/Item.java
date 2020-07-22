package M;

public abstract class Item {
	
	protected String name;
	protected int price;
	protected int flag;
	
	protected abstract void showInfo();//지금은 void이지만 나중에 view와 결합할 때는 String으로!
//	protected int type; //0 - 무기, 1 - 소비류 아이템, 2 - 동물
						//이렇게 하면 퀵슬롯 할 때 0 or 1만 퀵슬롯에 넣을 수 있다고 해줄 수 있죠!
						//나중에 flag로 통합할 계획입니다!
	
	public abstract void use(Hunter h);
	
	
	public Item(String name, int price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}

}
