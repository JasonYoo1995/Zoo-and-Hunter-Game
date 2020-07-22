package M;

import C.Controller;
import V.AnimalView;

public abstract class Animal implements Attackable {
    protected String name;
    private int HP; 
    private int level;
    private int power;
    private int value;
    private int x;
    private int y;
    private int range;
    private Map where;
    private int type; // 인벤토리에 넣을 때 구분하기 위해서
    protected int initialHP;
    public AnimalView backLink; // back link
     public Animal() {
     	this.type=2;
     	this.where=Forest.getInstance();
     	this.range = 1;
     	this.power = 0;
     	Forest.getInstance().getListAtForest().add(this);
     }

	public Map getWhere() {
		return where;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getInitialHP() {
		return initialHP;
	}

	public void setInitialHP(int initialHP) {
		this.initialHP = initialHP;
	}
    
	
	
}
