package M;

public abstract class Expendable extends Item {
	protected int num;
	
	public Expendable(String name, int price) {
		super(name, price);
		this.num = 1;
		Merchandiser.getInstance().expendableList.add(this);
	}
	
	public int getNum() {
		return this.num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	


}
