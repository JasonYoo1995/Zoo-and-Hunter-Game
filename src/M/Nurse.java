package M;

public class Nurse extends NPC{
	private static Nurse instance = null;
	
	private Nurse(String name, int money){
		// TODO Auto-generated constructor stub
		super(name, money);
	}
	
	public static Nurse getInstance(){
		if(instance == null){
			instance = new Nurse("간호사", 999999999);
		}
		return instance;
	}
	
	public void heal(Hunter h){
		/**nurse에서 헌터의 돈을 확인하고 체력회복 수락/거절하게 바꿈. View에서도 그렇게 사용하기 위해**/
		if(h.getMoney()>=100 && h.getHP()<h.maxHP) { /**돈 있고 최대체력 아닐때**/
			System.out.println(h.getName()+"님, 체력회복을 위해 10골드를 지불하세요");
			h.setHP(h.maxHP);
			h.setMoney(h.getMoney()-100);
			System.out.println(h.getName()+"의 체력을 모두 회복했습니다");
		}else if(h.getMoney()<10){ /**돈 없을때**/
				System.out.println(h.getName()+"은(는) 돈이 부족하여 체력회복할 수 없습니다");
		}else if(h.getHP()>=h.maxHP) { /**최대체력일 때**/
			System.out.println(h.getName()+"은(는) 이미 최대체력이기 때문에 체력회복할 수 없습니다");
		}
		
		System.out.println(h.getName()+"의 현재 체력  : "+h.getHP());
		System.out.println(h.getName()+"의 현재 재산  : "+h.getMoney());
	}
	
	@Override
	public void interact(Hunter h) {
		// TODO Auto-generated method stub
		System.out.println("어서오세요! 어떤 일로 오셨나요~? "+h.getName()+"님!");
	}
}
