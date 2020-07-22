package M;

public abstract class Item {
	
	protected String name;
	protected int price;
	protected int flag;
	
	protected abstract void showInfo();//������ void������ ���߿� view�� ������ ���� String����!
//	protected int type; //0 - ����, 1 - �Һ�� ������, 2 - ����
						//�̷��� �ϸ� ������ �� �� 0 or 1�� �����Կ� ���� �� �ִٰ� ���� �� ����!
						//���߿� flag�� ������ ��ȹ�Դϴ�!
	
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
