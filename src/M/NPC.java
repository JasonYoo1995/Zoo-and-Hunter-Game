package M;

public abstract class NPC {
	private String name;
	private int money;
	private Map where;
	int x, y;
	
	
	NPC(String name, int money){
		this.where = Zoo.getInstance();
		this.name = name;
		this.money = money;
	}
	
	public abstract void interact(Hunter h);
	
	public String getName(){
		return this.name;
	}

}

