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
//    //리턴타입 void->Forest로 변경
//    public static Cave getInstance(){
//    	if(instance==null) {
//    		instance=new Cave();
//    	}
//    	return instance;
//    }
//   
//    //override
//    public void showList() {
//    	System.out.print("현재 Cave에 있는 동물 : ");
//    	super.showList();
//    	System.out.println();
//	}
//    
//    public String getMame() {
//    	return this.name;
//    }
//}
