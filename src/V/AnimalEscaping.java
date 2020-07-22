package V;

import java.util.Random;

import C.Controller;
import M.Animal;
import M.Zoo;

public class AnimalEscaping extends Thread {
	ZooPanel zooPanel;
	ForestPanel forestPanel;
	int mininumTime; // Ż���ϴ� �ֱ��� �ּҰ� 
	int deviation; // Ż���ϴ� �ֱ��� ����
	Random random;
	AnimalEscaping(ZooPanel zooPanel, Controller c){
		random = new Random();
		this.zooPanel = zooPanel;
		this.mininumTime = 15;
		this.deviation = 2;
		this.forestPanel = c.forestPanel;
	}
	
	public int randomTimeGenerator() { 
		return mininumTime+random.nextInt(deviation); // ex) ����� 30, ������ 5�̸� -> 30~35�ʸ��� Ż��
	}
	
	public void run() {
		Animal animalToEscape;
		while(true) {
			if(Zoo.getInstance().getListAtZoo().size() >= 3) { // �������� ������ 3���� �̻��� ���� Ż��
				animalToEscape = Zoo.getInstance().getListAtZoo()
						.remove(random.nextInt(Zoo.getInstance().getListAtZoo().size()));
				// �츮�� �ִ� ��� ���� �� 1������ �������� ��
				((CageView)animalToEscape.backLink.getParent().getParent()).animalEscaped(animalToEscape); // ���� Ż��
			}
			int randomTime = randomTimeGenerator();
			try {
				Thread.sleep(randomTime*1000); // �����ڰ� ������ �ֱ⸶�� ���� 1������ �������� Ż��
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
