package M;
import java.util.ArrayList;

import V.AnimalView;

public class Forest extends Map {
    private static Forest instance = null;
    private ArrayList<Animal> listAtForest; // HP�� 0�� �ƴ� ����Ʈ
    private ArrayList<Animal> animalHPZeroList; 
    
	
    private Forest(){
    	this.name="Forest";
    	this.listAtForest=new ArrayList<>();
    	this.animalHPZeroList = new ArrayList<>();
    }

    //����Ÿ�� void->Forest�� ����
    public static Forest getInstance(){
    	if(instance==null) {
    		instance=new Forest();
    	}
    	return instance;
    }
   
    //override
    public void showList() {
    	System.out.print("���� Forest�� �ִ� ���� : ");
    	System.out.println();
	}

    
    public String getMame() {
    	return this.name;
    }
    public ArrayList getListAtForest() {
    	return this.listAtForest;
    }

	public ArrayList<Animal> getAnimalHPZeroList() {
		return animalHPZeroList;
	}

	public void setAnimalHPZeroList(ArrayList<Animal> animalHPZeroList) {
		this.animalHPZeroList = animalHPZeroList;
	}

	public void setListAtForest(ArrayList<Animal> listAtForest) {
		this.listAtForest = listAtForest;
	}
    
    
}
