//package M;
//import java.util.ArrayList;
//
//public class Mountain extends Map {
//    private static Mountain instance = null;
//    private Mountain(){
//    	this.name="Mountain";
//    	this.animals=new ArrayList<>();
//    }
//
//    //����Ÿ�� void->Forest�� ����
//    public static Mountain getInstance(){
//    	if(instance==null) {
//    		instance=new Mountain();
//    	}
//    	return instance;
//    }
//   
//    //override
//    public void showList() {
//    	System.out.print("���� Mountain�� �ִ� ���� : ");
//    	super.showList();
//    	System.out.println();
//	}
//    
//    public String getMame() {
//    	return this.name;
//    }
//}
