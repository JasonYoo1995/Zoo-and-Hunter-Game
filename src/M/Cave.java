//package M;
//import java.util.ArrayList;
//
//public class Cave extends Map {
//    private static Cave instance = null;
//    private Cave(){
//    	this.name="Cave";
//    	this.animals=new ArrayList<>();
//    }
//
//    //����Ÿ�� void->Forest�� ����
//    public static Cave getInstance(){
//    	if(instance==null) {
//    		instance=new Cave();
//    	}
//    	return instance;
//    }
//   
//    //override
//    public void showList() {
//    	System.out.print("���� Cave�� �ִ� ���� : ");
//    	super.showList();
//    	System.out.println();
//	}
//    
//    public String getMame() {
//    	return this.name;
//    }
//}
