package M;

import java.util.ArrayList;

public class Zoo extends Map {
    private static Zoo instance = null;
    private int level;
    private String name;
    private ArrayList listAtZoo;
    private Zoo(){
    	this.name="���������� ������";
    	this.listAtZoo=new ArrayList<Animal>();
    	this.level = 0;
    }

    //����Ÿ�� void->Zoo�� ����
    public static Zoo getInstance(){
    	if(instance==null) {
    		instance=new Zoo();
    	}
    	
    	return instance;
    }
    
    
    public boolean update() {
    	switch(this.level) {
    	case 1 :
    		this.name = "�㸧�� ������";
    		return true;
    	case 2:
    		this.name = "������ ������";
    		return true;
    	case 3:
    		this.name = "ȭ���� ������";
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
