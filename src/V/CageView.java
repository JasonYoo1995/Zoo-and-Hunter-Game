package V;

import java.awt.Color;
import java.util.Random;

import javax.swing.JPanel;

import C.Controller;
import M.Animal;
import M.Cage;
import M.Forest;
import M.Zoo;

public class CageView extends JPanel{ // 동물 우리
	public static int count=0; //cageView가 생성된 숫자를 기록
	public final int sizeX = 80; // 패널크기
	public final int sizeY = 120; 
	public Color color; // 색깔
	public CageTileView[] cageTile; // 동물이 들어갈 자리 (JPanel 타입)
	public int positionX, positionY;
	public Cage cage;
	Random random;
	public Controller c;
	
	public CageView(Controller c){
		this.c = c;
		cage = new Cage(count++);
		cage.backlink = this;
		this.setBackground(Color.white);
		this.setSize(sizeX,sizeY);
		this.setOpaque(true);
		this.setLayout(null);
		this.repaint();
		this.random = new Random();
		cageTile = new CageTileView[6];
		for(int i=0; i<6; i++) { //화면상에서 6칸(실제로는 5마리가 최대치)
			cageTile[i] = new CageTileView();
			cageTile[i].setVisible(true);
			this.add(cageTile[i]); //현재의 CageView 패널에 add하는 것
		}
		
		//패널 상에서 보이는 위치 지정
		cageTile[0].setPosition(0,0);
		cageTile[1].setPosition(40,0);
		cageTile[2].setPosition(0,40);
		cageTile[3].setPosition(40,40);
		cageTile[4].setPosition(0,80);
		cageTile[5].setPosition(40,80);
	}

	public void setPosition(int x, int y) {
		this.setLocation(x, y);
		this.positionX = x;
		this.positionY = y;
	}

	boolean hunterOnCage() { // 헌터가 우리 위에 올라와 있을 때, true 리턴 (헌터가 'X'키를 눌러 우리에 동물을 전시하려고  할 때 쓰임)
		if(this.positionX<c.hunterView.getPositionX() && this.positionX+80>c.hunterView.getPositionX()
			&& this.positionY<c.hunterView.getPositionY() && this.positionY+120>c.hunterView.getPositionY()) {
			return true;
		}
		else return false;
	}
	
	public boolean putAnimal(Animal animal) { // 동물을 우리에 넣을 수 있는 조건이 되면, true 리턴
		if(this.cage.getLevel()==0) { /**생성자에서 이미 count를 ++시켜줬으므로 현재 cage의 인덱스 넘버는 count-1로 접근해야함**/
			SystemLog.getInstance().printLog("부서진 나무우리에는 동물을 넣을 수 없습니다.");
			return false; // 부서진 나무우리에는 동물 전시 불가
		}
		if(this.cage.getLevel()<animal.getLevel()) {
			SystemLog.getInstance().printLog(animal.getName()+"는 lv."+animal.getLevel()+" 이상의 우리에만 넣을 수 있습니다.");
			return false; // 우리의 레벨보다 높은 레벨의 동물은 전시 불가
		}
		if(this.cage.getCageAnimalList().size()>=5) {
			SystemLog.getInstance().printLog("우리가 꽉 차서 넣을 수 없습니다.");
			return false; // 꽉 차면 동물 전시 불가
		}
		
		// 동물을 우리에 넣는 코드
		this.cage.getCageAnimalList().add(animal); // '우리 안에 있는 동물들의 리스트'에 추가
		animal.backLink.setBackground(Color.green);
		alignCage(); // 우리의 화면 상의 배열을 정렬
		SystemLog.getInstance().printLog(animal.getName()+"을 "+"우리에 넣었습니다.");
		return true;
	}
	
	public void levelUp() {
		if(this.cage.getLevel()==1) {
			this.setBackground(Color.yellow);
		}
		else if(cage.getLevel()==2) {
			this.setColor(Color.blue);
		}
		else if(cage.getLevel()==3) {
			this.setColor(Color.red);
		}
		else{
			SystemLog.getInstance().printLog("더 이상 업그레이드 할 수 없습니다!");
			return;
		}
	}
	
	public void alignCage() { // ArrayList처럼 remove시 화면상에서 자동정렬
		for(int i=0; i<this.cage.getCageAnimalList().size(); i++) {
			cageTile[i].setAnimal(this.cage.getCageAnimalList().get(i).backLink);
			cageTile[i].getParent().repaint();
		}
	}
	
	public void animalEscaped(Animal animal) {
		this.cage.getCageAnimalList().remove(animal); // '우리 안에 있는 동물 리스트'에서 제거
		animal.backLink.setBackground(Color.blue);
		c.zooStatusArea.update(); // 동물원 상태창 업데이트 
		alignCage(); // 우리의 화면 상의 배열을 정렬
		Forest.getInstance().getListAtForest().add(animal);
		SystemLog.getInstance().printLog((this.cage.num+1)+"번 우리에 있는 "+animal.getName()+"가 숲속으로 도망쳤습니다.");
		animal.backLink.setLocation(random.nextInt(9)*40, random.nextInt(10)*40);
		c.forestPanel.animalGenerator.putIntoForest(animal);
		// animalGenerator 클래스의 putIntoForest를 활용하여, 화면상의 추가와 리스트에의 추가, 겹치지 않는 위치 설정까지 한 번에 실행
		animal.setHP(animal.getInitialHP()); // 체력 회복
	}
	
	public void setColor(Color color) {
		this.setBackground(color);
	}
}
