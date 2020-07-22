package V;

import java.util.Random;

import C.Controller;
import M.Animal;
import M.Forest;

public class AnimalGenerator { 
	//	게임 종료 조건은 25마리이지만, 동물 생성은 끝없이 된다.
	//	게임 시작 초기에는 토끼 4마리, 사슴 1마리로 시작하여, 2마리가 숲에서 포획될 때마다, 2마리가 새로 생겨나게 한다.
	//	동물원 레벨이 0일 때는, 토끼:사슴 = 2:1의 랜덤비율로 나타난다
	//	동물원 레벨이 1일 때는, 토끼:사슴:여우 = 2:2:1의 랜덤비율로 나타난다
	//	동물원 레벨이 2일 때는, 사슴:여우:늑대 = 1:2:1의 랜덤비율로 나타난다 
	//	동물원 레벨이 3일 때는, 여우:늑대:사자 = 2:1:2의 랜덤비율로 나타난다
	// 우리가 꽉 차서 가두지 못하는 동물은 상인에게 판매하여 돈을 벌 수 있다.
	
	ForestPanel forestPanel;
	Controller c;
	AnimalGenerator(ForestPanel forestPanel, Controller c){ // forestPanel에 객체를 생성한다.
		this.c = c;
		this.forestPanel = forestPanel;
		for(int i=0; i<4; i++) {
			this.setPositionOfAnimal(putIntoForest(new RabbitView(c).ani).backLink); // 토끼 4마리);
		}
		this.setPositionOfAnimal(putIntoForest(new DeerView(c).ani).backLink); // 사슴 1마리
	}
	
	public void checkAnimalNumberAtForest(int zooLevel) { // 사냥꾼이 동물을 포획할 때마다 호출되어, 숲속의 동물 마리수를 모니터링한다.
		if(Forest.getInstance().getListAtForest().size()<=3) { // 숲속에는 항상 동물이 5마리 또는 4마리로 유지된다. 즉, 숲속에 동물이 3마리가 되면, 새로 2마리를 생성해준다.
			putIntoForest(animalAccordingToZooLevel(zooLevel));
			putIntoForest(animalAccordingToZooLevel(zooLevel));
			// 생성된 동물 2마리를 숲속에 넣어준다.
		}
	}
	
	public Animal animalAccordingToZooLevel(int zooLevel) { // 동물원 레벨에 따라 정해진 비율의 랜덤 확률로 생성한 동물을 리턴
		Random random = new Random();
		if(zooLevel==0) { // 토끼:사슴 = 2:1
			if(random.nextInt(3)<2) return new RabbitView(c).ani;
			else return new DeerView(c).ani;
		}
		else if(zooLevel==1) { // 토끼:사슴:여우 = 2:2:1
			if(random.nextInt(5)<2) return new RabbitView(c).ani;
			else if(random.nextInt(5)<4) return new DeerView(c).ani;
			else return new FoxView(c).ani;
		}
		else if(zooLevel==2) { // 사슴:여우:늑대 = 1:2:1
			if(random.nextInt(4)<1) return new DeerView(c).ani;
			else if(random.nextInt(4)<3) return new FoxView(c).ani;
			else return new WolfView(c).ani;
		}
		else { // 토끼:사슴:여우:늑대:사자 = 1:2:1:1:3
			if(random.nextInt(8)<1) return new RabbitView(c).ani;
			if(random.nextInt(8)<3) return new DeerView(c).ani;
			else if(random.nextInt(8)<4) return new FoxView(c).ani;
			else if(random.nextInt(8)<5) return new WolfView(c).ani;
			else return new LionView(c).ani;
		}
	}
	
	public Animal putIntoForest(Animal animal) { // 생성한 동물을 숲에 내보냄
		setPositionOfAnimal(animal.backLink); // 위치 중복 방지
		forestPanel.map.add(animal.backLink); // 화면 상에 표시
		animal.backLink.repaint();
		SystemLog.getInstance().printLog(animal.getName()+"가 숲속에 나타났습니다.");
		return animal;
	}
	
	public void setPositionOfAnimal(AnimalView animal) { // 동물의 위치가 겹쳐서 생성되면, 안 겹칠때까지 위치를 바꿔주는 알고리즘
		Random random = new Random();
		for(int i=0; i<Forest.getInstance().getListAtForest().size(); i++) { // 생성된 모든 동물을 순서대로 처리할 건데
			AnimalView animalToAdd = ((Animal)Forest.getInstance().getListAtForest().get(i)).backLink; // i번째 동물에 대하여
			for(int j=0; j<i; j++) { // i번째 동물과 그 이전에 생성된 모든 동물들을 비교. 0번째부터~j(=i-1)번째 동물까지 순서대로 살핌
				while(animalToAdd.positionX== ((Animal)Forest.getInstance().getListAtForest().get(j)).backLink.positionX
						&& animalToAdd.positionY== ((Animal)Forest.getInstance().getListAtForest().get(j)).backLink.positionY){ // 동물의 위치가 안 겹칠때까지 반복
					animalToAdd.setLocation(40*random.nextInt(9), 40*random.nextInt(10));
					j=0; // 위치를 바꾸고 0번째 동물부터 재비교
				}
			}
		}
	}
}
