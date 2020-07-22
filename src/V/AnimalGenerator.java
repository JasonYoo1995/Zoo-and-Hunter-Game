package V;

import java.util.Random;

import C.Controller;
import M.Animal;
import M.Forest;

public class AnimalGenerator { 
	//	���� ���� ������ 25����������, ���� ������ ������ �ȴ�.
	//	���� ���� �ʱ⿡�� �䳢 4����, �罿 1������ �����Ͽ�, 2������ ������ ��ȹ�� ������, 2������ ���� ���ܳ��� �Ѵ�.
	//	������ ������ 0�� ����, �䳢:�罿 = 2:1�� ���������� ��Ÿ����
	//	������ ������ 1�� ����, �䳢:�罿:���� = 2:2:1�� ���������� ��Ÿ����
	//	������ ������ 2�� ����, �罿:����:���� = 1:2:1�� ���������� ��Ÿ���� 
	//	������ ������ 3�� ����, ����:����:���� = 2:1:2�� ���������� ��Ÿ����
	// �츮�� �� ���� ������ ���ϴ� ������ ���ο��� �Ǹ��Ͽ� ���� �� �� �ִ�.
	
	ForestPanel forestPanel;
	Controller c;
	AnimalGenerator(ForestPanel forestPanel, Controller c){ // forestPanel�� ��ü�� �����Ѵ�.
		this.c = c;
		this.forestPanel = forestPanel;
		for(int i=0; i<4; i++) {
			this.setPositionOfAnimal(putIntoForest(new RabbitView(c).ani).backLink); // �䳢 4����);
		}
		this.setPositionOfAnimal(putIntoForest(new DeerView(c).ani).backLink); // �罿 1����
	}
	
	public void checkAnimalNumberAtForest(int zooLevel) { // ��ɲ��� ������ ��ȹ�� ������ ȣ��Ǿ�, ������ ���� �������� ����͸��Ѵ�.
		if(Forest.getInstance().getListAtForest().size()<=3) { // ���ӿ��� �׻� ������ 5���� �Ǵ� 4������ �����ȴ�. ��, ���ӿ� ������ 3������ �Ǹ�, ���� 2������ �������ش�.
			putIntoForest(animalAccordingToZooLevel(zooLevel));
			putIntoForest(animalAccordingToZooLevel(zooLevel));
			// ������ ���� 2������ ���ӿ� �־��ش�.
		}
	}
	
	public Animal animalAccordingToZooLevel(int zooLevel) { // ������ ������ ���� ������ ������ ���� Ȯ���� ������ ������ ����
		Random random = new Random();
		if(zooLevel==0) { // �䳢:�罿 = 2:1
			if(random.nextInt(3)<2) return new RabbitView(c).ani;
			else return new DeerView(c).ani;
		}
		else if(zooLevel==1) { // �䳢:�罿:���� = 2:2:1
			if(random.nextInt(5)<2) return new RabbitView(c).ani;
			else if(random.nextInt(5)<4) return new DeerView(c).ani;
			else return new FoxView(c).ani;
		}
		else if(zooLevel==2) { // �罿:����:���� = 1:2:1
			if(random.nextInt(4)<1) return new DeerView(c).ani;
			else if(random.nextInt(4)<3) return new FoxView(c).ani;
			else return new WolfView(c).ani;
		}
		else { // �䳢:�罿:����:����:���� = 1:2:1:1:3
			if(random.nextInt(8)<1) return new RabbitView(c).ani;
			if(random.nextInt(8)<3) return new DeerView(c).ani;
			else if(random.nextInt(8)<4) return new FoxView(c).ani;
			else if(random.nextInt(8)<5) return new WolfView(c).ani;
			else return new LionView(c).ani;
		}
	}
	
	public Animal putIntoForest(Animal animal) { // ������ ������ ���� ������
		setPositionOfAnimal(animal.backLink); // ��ġ �ߺ� ����
		forestPanel.map.add(animal.backLink); // ȭ�� �� ǥ��
		animal.backLink.repaint();
		SystemLog.getInstance().printLog(animal.getName()+"�� ���ӿ� ��Ÿ�����ϴ�.");
		return animal;
	}
	
	public void setPositionOfAnimal(AnimalView animal) { // ������ ��ġ�� ���ļ� �����Ǹ�, �� ��ĥ������ ��ġ�� �ٲ��ִ� �˰���
		Random random = new Random();
		for(int i=0; i<Forest.getInstance().getListAtForest().size(); i++) { // ������ ��� ������ ������� ó���� �ǵ�
			AnimalView animalToAdd = ((Animal)Forest.getInstance().getListAtForest().get(i)).backLink; // i��° ������ ���Ͽ�
			for(int j=0; j<i; j++) { // i��° ������ �� ������ ������ ��� �������� ��. 0��°����~j(=i-1)��° �������� ������� ����
				while(animalToAdd.positionX== ((Animal)Forest.getInstance().getListAtForest().get(j)).backLink.positionX
						&& animalToAdd.positionY== ((Animal)Forest.getInstance().getListAtForest().get(j)).backLink.positionY){ // ������ ��ġ�� �� ��ĥ������ �ݺ�
					animalToAdd.setLocation(40*random.nextInt(9), 40*random.nextInt(10));
					j=0; // ��ġ�� �ٲٰ� 0��° �������� ���
				}
			}
		}
	}
}
