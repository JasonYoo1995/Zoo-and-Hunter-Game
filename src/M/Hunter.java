package M;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import C.Controller;
import V.AnimalView;
import V.BasicFrame;
import V.ForestPanel;
import V.HunterView;
import V.HuntingDogView;
import V.ShootingPath;
import V.ZooPanel;

public class Hunter implements Attackable {
	private String name;
	private int money;
	private int power;
	private int HP;
	private int shootingDistance; // 사거리
	public boolean activated; // Zoo에서 'A'를 눌렀을 때, 공격하지 못하게 만듦
	
	public  static final int maxHP=100;
	
	
	public Hunter() {
		this.name = "";
		this.money = 1500;
		this.HP = 50;
		this.power = 80;
		this.shootingDistance = 80;
	}
	

	public String getName() {
		return name;
	}

	public int getMoney() {
		return money;
	}


	public int getPower() {
		return power;
	}

	public int getHP() {
		return HP;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setHP(int hP) {
		HP = hP;
	}


	public int getShootingDistance() {
		return shootingDistance;
	}



	public boolean isActivated() {
		return activated;
	}


	public void setShootingDistance(int shootingDistance) {
		this.shootingDistance = shootingDistance;
	}



	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}
	
	public void requestHeal(Hunter h) {
		Nurse.getInstance().heal(this);
	}
}
