package M;

import java.util.ArrayList;

public class Zoo extends Map {
    private static Zoo instance = null;
    private int level;
    private String name;
    private ArrayList listAtZoo;
    private Zoo(){
    	this.name="폐장직전의 동물원";
    	this.listAtZoo=new ArrayList<Animal>();
    	this.level = 0;
    }

    //리턴타입 void->Zoo로 변경
    public static Zoo getInstance(){
    	if(instance==null) {
    		instance=new Zoo();
    	}
    	
    	return instance;
    }
    
    
    public boolean update() {
    	switch(this.level) {
    	case 1 :
    		this.name = "허름한 동물원";
    		return true;
    	case 2:
    		this.name = "볼만한 동물원";
    		return true;
    	case 3:
    		this.name = "화려한 동물원";
    		return true;
    	default:
    		return false;
    	}
    }
    
    public String getName(){
    	return this.name;
    }

	public ArrayList<Animal> getListAtZoo() {
		return listAtZoo;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

}
