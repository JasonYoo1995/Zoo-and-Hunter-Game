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
			instance = new Carpenter("���", 999999999);
		}
		return instance;
	}
	
	/**View�� Carpenter�� �ִ� �޼ҵ� �����ͼ� ���... View�� �˰����� ���߱�� �����߱� ����**/
	public int upgradeCage(Hunter h) { // �츮�� �� �� ���׷��̵带 �߾������� ����, �ڵ����� ������ ���׷��̵带 ����
		if(this.upgradeTime==0) {
			if(h.getMoney()<50) SystemLog.getInstance().printLog("���� �����Ͽ� �츮�� ��ü�� �� �����ϴ�.");
			else {
				h.setMoney(h.getMoney()-50); // 50���
				c.cageView[0].cage.setStatus();
				upgradeTime++;
				return 1;
			}
		}
		else if(this.upgradeTime==1) {
			if(h.getMoney()<50) SystemLog.getInstance().printLog("���� �����Ͽ� �츮�� ��ü�� �� �����ϴ�.");
			else {
				h.setMoney(h.getMoney()-50); // 50���
				c.cageView[1].cage.setStatus();
				upgradeTime++;
				Zoo.getInstance().setLevel(1);
				return 1;
			}
		}
		// 0, 1�� �츮�� �����츮(lv1)�� �Ǹ�, ������ ������ 1�� �ȴ�.

		else if(this.upgradeTime==2) {
			if(h.getMoney()<500) SystemLog.getInstance().printLog("���� �����Ͽ� �츮�� ��ü�� �� �����ϴ�.");
			else {
				h.setMoney(h.getMoney()-500); // 500���
				c.cageView[2].cage.setStatus();
				upgradeTime++;
				return 2;
			}
		}
		else if(this.upgradeTime==3) {
			if(h.getMoney()<500) SystemLog.getInstance().printLog("���� �����Ͽ� �츮�� ��ü�� �� �����ϴ�.");
			else {
				h.setMoney(h.getMoney()-500); // 500���
				c.cageView[3].cage.setStatus();
				upgradeTime++;
				Zoo.getInstance().setLevel(2);
				return 2;
			}
		}
		// 2, 3�� �츮�� öâ�츮(lv2)�� �Ǹ�, ������ ������ 2�� �ȴ�.

		else if(this.upgradeTime==4) {
			if(h.getMoney()<2000) SystemLog.getInstance().printLog("���� �����Ͽ� �츮�� ��ü�� �� �����ϴ�.");
			else {
			h.setMoney(h.getMoney()-2000); // 2000���
			c.cageView[4].cage.setStatus();
			upgradeTime++;
			Zoo.getInstance().setLevel(3);
			return 3;
			}
		}
		// 4�� �츮�� ����츮(lv3)�� �Ǹ�, ������ ������ 3�� �ȴ�.
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
