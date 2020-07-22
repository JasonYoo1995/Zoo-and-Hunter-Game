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
//    //리턴타입 void->Forest로 변경
//    public static Mountain getInstance(){
//    	if(instance==null) {
//    		instance=new Mountain();
//    	}
//    	return instance;
//    }
//   
//    //override
//    public void showList() {
//    	System.out.print("현재 Mountain에 있는 동물 : ");
//    	super.showList();
//    	System.out.println();
//	}
//    
//    public String getMame() {
//    	return this.name;
//    }
//}
