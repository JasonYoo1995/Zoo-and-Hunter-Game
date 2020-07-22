package M;

import V.SystemLog;
import C.Controller;


public class Carpenter extends NPC{
	private static Carpenter instance = null;
	private int upgradeTime;
	Controller c;
	
	private Carpenter(String name, int money){
		super(name, money);
		upgradeTime=0;
	}
	
	public void setController(Controller c){
		this.c = c;
	}
	
	public static Carpenter getInstance(){
		if(instance == null){
			instance = new Carpenter("목수", 999999999);
		}
		return instance;
	}
	
	/**View의 Carpenter에 있던 메소드 가져와서 사용... View에 알고리즘을 맞추기로 논의했기 때문**/
	public int upgradeCage(Hunter h) { // 우리를 몇 번 업그레이드를 했었는지에 따라, 자동으로 적절한 업그레이드를 해줌
		if(this.upgradeTime==0) {
			if(h.getMoney()<50) SystemLog.getInstance().printLog("돈이 부족하여 우리를 교체할 수 없습니다.");
			else {
				h.setMoney(h.getMoney()-50); // 50골드
				c.cageView[0].cage.setStatus();
				upgradeTime++;
				return 1;
			}
		}
		else if(this.upgradeTime==1) {
			if(h.getMoney()<50) SystemLog.getInstance().printLog("돈이 부족하여 우리를 교체할 수 없습니다.");
			else {
				h.setMoney(h.getMoney()-50); // 50골드
				c.cageView[1].cage.setStatus();
				upgradeTime++;
				Zoo.getInstance().setLevel(1);
				return 1;
			}
		}
		// 0, 1번 우리가 나무우리(lv1)로 되면, 동물원 레벨은 1이 된다.

		else if(this.upgradeTime==2) {
			if(h.getMoney()<500) SystemLog.getInstance().printLog("돈이 부족하여 우리를 교체할 수 없습니다.");
			else {
				h.setMoney(h.getMoney()-500); // 500골드
				c.cageView[2].cage.setStatus();
				upgradeTime++;
				return 2;
			}
		}
		else if(this.upgradeTime==3) {
			if(h.getMoney()<500) SystemLog.getInstance().printLog("돈이 부족하여 우리를 교체할 수 없습니다.");
			else {
				h.setMoney(h.getMoney()-500); // 500골드
				c.cageView[3].cage.setStatus();
				upgradeTime++;
				Zoo.getInstance().setLevel(2);
				return 2;
			}
		}
		// 2, 3번 우리가 철창우리(lv2)로 되면, 동물원 레벨은 2가 된다.

		else if(this.upgradeTime==4) {
			if(h.getMoney()<2000) SystemLog.getInstance().printLog("돈이 부족하여 우리를 교체할 수 없습니다.");
			else {
			h.setMoney(h.getMoney()-2000); // 2000골드
			c.cageView[4].cage.setStatus();
			upgradeTime++;
			Zoo.getInstance().setLevel(3);
			return 3;
			}
		}
		// 4번 우리가 전기우리(lv3)로 되면, 동물원 레벨은 3이 된다.
		return -1;
	}
	
	public int getUpgradeTime() {
		return upgradeTime;
	}

	public void setUpgradeTime(int upgradeTime) {
		this.upgradeTime = upgradeTime;
	}

	@Override
	public void interact(Hunter h) {
		// TODO Auto-generated method stub
		
	}
}
