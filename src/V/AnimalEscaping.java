package V;

import java.util.Random;

import C.Controller;
import M.Animal;
import M.Zoo;

public class AnimalEscaping extends Thread {
	ZooPanel zooPanel;
	ForestPanel forestPanel;
	int mininumTime; // 탈출하는 주기의 최소값 
	int deviation; // 탈출하는 주기의 편차
	Random random;
	AnimalEscaping(ZooPanel zooPanel, Controller c){
		random = new Random();
		this.zooPanel = zooPanel;
		this.mininumTime = 15;
		this.deviation = 2;
		this.forestPanel = c.forestPanel;
	}
	
	public int randomTimeGenerator() { 
		return mininumTime+random.nextInt(deviation); // ex) 평균이 30, 편차가 5이면 -> 30~35초마다 탈출
	}
	
	public void run() {
		Animal animalToEscape;
		while(true) {
			if(Zoo.getInstance().getListAtZoo().size() >= 3) { // 동물원에 동물이 3마리 이상일 때만 탈출
				animalToEscape = Zoo.getInstance().getListAtZoo()
						.remove(random.nextInt(Zoo.getInstance().getListAtZoo().size()));
				// 우리에 있는 모든 동물 중 1마리를 랜덤으로 고름
				((CageView)animalToEscape.backLink.getParent().getParent()).animalEscaped(animalToEscape); // 동물 탈출
			}
			int randomTime = randomTimeGenerator();
			try {
				Thread.sleep(randomTime*1000); // 개발자가 지정한 주기마다 동물 1마리가 랜덤으로 탈출
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
