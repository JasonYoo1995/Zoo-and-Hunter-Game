package V;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import C.Controller;
import M.Forest;
import M.Inventory;
import M.Zoo;

public class AnimalView extends JLabel implements Runnable{ // 반격에 대한 스레드
	int initialHP; // 동물원 탈출시 HP 회복 용도로 사용
	String name; // 이름
	int attackingRange=40; // 반격 가능한 사거리
	int positionX=40, positionY=40; // 위치 좌표
	ForestPanel forestPanel;
	HunterView hunterView;
	Thread showCounterAttackingAreaArea; // 반격 시 동물의 사거리를 보여주는 레이블
	Random random = new Random();
	M.Animal ani;
	Controller c;
	JLabel die = new JLabel("    사 망");
	

	AnimalView(Controller c){
		this.c = c;
		this.hunterView = c.hunterView;
		this.forestPanel = c.forestPanel;
		this.positionX *= random.nextInt(9); // x좌표상 0~8번째 칸 중 하나로 랜덤 지정 
		this.positionY *= random.nextInt(10); // y좌표상 0~9번째 칸 중 하나로 랜덤 지정
		this.setOpaque(true);
		this.setBounds(positionX, positionY,40,40);
	}

	public void setLocation(int x, int y) { // 메소드 오버라이딩 : position도 함께 바꿔주기 위함
		super.setLocation(x, y);
		this.positionX = x;
		this.positionY = y;
	}

	public void HPzero() { // HP가 0이 되었을 때 실행되는 코드
		setBackground(Color.red); // 동물의 배경이 빨간색으로 바뀜
		Forest.getInstance().getAnimalHPZeroList().add(this.ani);
		SystemLog.getInstance().printLog(Forest.getInstance().getAnimalHPZeroList()
				.get(Forest.getInstance().getAnimalHPZeroList().size()-1).getName()+"의 HP : 0 (포획 가능)");
		// 리스트에 잘 추가 되었는지, 리스트에 직접 접근하여 확인한다.
	}

	public void capturedAtForest() { // HP가 0인 동물에게 사냥꾼이 다가가서, 'Z'키를 통해 동물을 포획하는 순간 실행된다.
		Inventory.getInstance().getCapturedAnimalList().add(this.ani); // 사냥꾼의 인벤토리 속 '운반 중인 동물 리스트'에 추가한다.
		Forest.getInstance().getAnimalHPZeroList().remove(this.ani); // 'Forest 내에서 HP가 0이나 아직 포획되지 않은 동물들의 목록'에서 해당 동물을 삭제한다.
		Forest.getInstance().getListAtForest().remove(this.ani); // 'Forest 내에 있는 동물 리스트'에서 삭제한다.
		this.setVisible(false); // 사냥꾼이 동물을 포획하였으므로, 동물 그림을 화면에서 제거한다.
		SystemLog.getInstance().printLog(this.ani.getName()+"을 포획하였습니다");
		// 리스트에 잘 추가 되었는지, 리스트에 직접 접근하여 확인한다.
	}

	public void capturedAtZoo() { // HP가 0인 동물에게 사냥꾼이 다가가서, 'Z'키를 통해 동물을 포획하는 순간 실행된다.
		Inventory.getInstance().getCapturedAnimalList().add(this.ani); // 사냥꾼의 인벤토리 속 '운반 중인 동물 리스트'에 추가한다.
		CageView cageView = (CageView)this.getParent().getParent();
		cageView.cage.getCageAnimalList().remove(this.ani);
		Zoo.getInstance().getListAtZoo().remove(this.ani);
		this.setVisible(false);
		this.repaint();
		cageView.alignCage();
		// 사냥꾼이 동물을 포획하였으므로, 동물 그림을 화면에서 제거한다.
		SystemLog.getInstance().printLog(this.ani.getName()+"을 포획하였습니다");
		// 리스트에 잘 추가 되었는지, 리스트에 직접 접근하여 확인한다.
	}

	public boolean rangeCheck(){ // 반격 사거리 내에 있는지 체크
		int distanceX, distanceY;
		distanceX = Math.abs(hunterView.getPositionX()-this.positionX); // 동물과 헌터 사이의 거리 측정
		distanceY = Math.abs(hunterView.getPositionY()-this.positionY); // 동물과 헌터 사이의 거리 측정
		if(attackingRange>distanceX && attackingRange>distanceY) return true; // 사거리 내에 있음
		else{
			return false; // 사거리 밖에 있음
		}

	}

	public void run() { // level2 이상의 동물들은 공격을 당할 시, 사거리 내에서 반격을 한다.
		if(this.ani.getLevel()<2) return;
		boolean inRange; // 반격 사거리 내에 있으면 true
		if(c.hunter.activated == false) return;
		while(this.ani.getHP()>0) { // 살아 있을 때만 반격 / 헌터가 동물원에 있을 때만 반격
			inRange = this.rangeCheck(); // 사냥꾼이 사거리 안으로 들어와 true가 리턴되면 while문 들어가 반격 시작
			while(inRange) { // 사거리 내에 있는 동안만 반격
				boolean InterruptedwhileSleeping = false;
				try {
					Thread.sleep(3000);// 3초마다 반격
				} catch (InterruptedException e) {
					//					System.out.println("interrupted during sleep"); // sleep 도중 공격 대상이 바뀐 경우
					return;
				}
				if(this.ani.getHP()<=0) return; // 동물의 HP가 0이 되면 반격 종료
				if(this.rangeCheck()==false) return; // 사냥꾼이 사거리 밖으로 피하면 반격 끝
				if(InterruptedwhileSleeping==true) return; // sleep 도중에 공격 대상이 바뀌어 interrup가 호출된 경우
				if(c.hunter.getHP()>this.ani.getPower()) {
					CounterAttackingArea counterAttackingArea = new CounterAttackingArea(forestPanel);
					counterAttackingArea.setBounds(this.positionX-this.attackingRange+40, this.positionY-this.attackingRange+40,
							this.attackingRange*2-40, this.attackingRange*2-40);
					showCounterAttackingAreaArea = new Thread((Runnable) counterAttackingArea);
					showCounterAttackingAreaArea.start();
					c.hunter.setHP(c.hunter.getHP()-this.ani.getPower()); // 반격
					c.hunterStatusArea.update();
					SystemLog.getInstance().printLog(this.toString()+"가 반격하여 "+this.ani.getPower()+"의 데미지를 입었습니다.");
				}
				else { // 헌터의 HP가 0이 되면 반격 종료
					int lastHP = c.hunter.getHP();
					c.hunter.setHP(0); // 헌터의 HP가 마이너스가 되지 않게 방지
					c.hunterStatusArea.update();
					SystemLog.getInstance().printLog(this.toString()+"가 반격하여 "+lastHP+"의 데미지를 입었습니다.");
					SystemLog.getInstance().printLog("["+c.hunter.getName()+"]가 사망했습니다!!!!!!!");
					
					try {
						die.setVisible(true);
						die.setBackground(Color.GRAY);
						die.setOpaque(true);
						die.setBounds(270, 200, 400, 200);
						die.setFont(new Font("Monospaced", Font.BOLD, 60));
						c.basicframe.getContentPane().add(die,0);
						die.repaint();
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					die.setVisible(false);
					c.basicframe.setContentPane(c.zooPanel); // 화면 전환
					
					c.zooPanel.map.add(c.hunterView,0); 
					c.zooPanel.map.add(c.huntingDog,0);
					c.hunterView.setLocation(360,200); // hunter가 포탈 위에 위치하도록
					c.huntingDog.setLocation(360,160); // 사냥개가 포탈 옆에 위치하도록
					c.hunterView.setPositionX(360);
					c.hunterView.setPositionY(200);
					c.huntingDog.positionX = 360;
					c.huntingDog.positionY = 160;
					c.huntingDog.activated=false; // Zoo에서 사냥개는 사냥꾼을 따라다니는 기능 외에는 아무 것도 못하게 만듦
					c.hunter.setActivated(false); // Zoo에서 사냥꾼은 공격을 할 수 없게 만듦
					
					c.skyView.skyViewHunter.setLocation(200, 450);
					c.skyView.skyViewHunter.repaint();
					c.zooPanel.add(c.zooStatusArea); // Zoo와 Forest 간에 동기화된 상태창들을 Zoo에 부착1
		            c.zooPanel.add(c.hunterStatusArea); // Zoo와 Forest 간에 동기화된 상태창들을 Zoo에 부착2 
		            c.zooPanel.add(SystemLog.getInstance()); // Zoo와 Forest 간에 동기화된 상태창들을 Zoo에 부착3
					
		            c.zooPanel.add(c.skyView.timeLabel);
					
					
					c.hunterView.requestFocus(); // hunter에게 focusing이 되어야, 키보드를 눌렀을 때 움직임의 신호가 hunter에게 전달됨. 
					SystemLog.getInstance().printLog("동물원으로 이동합니다."); // 시스템 로그에 띄움
					
					
					c.zooStatusArea.repaint();
					c.hunterStatusArea.repaint();
					return; 
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class RabbitView extends AnimalView {
	static int num = 1; // 생성되는 토끼의 수 = 각 토끼의 고유한 이름의 역할을 하기도 함 (토끼1, 토끼2, ...)
	RabbitView(Controller c){
		super(c);
		ani = new M.Rabbit();
		ani.backLink = this;
		this.name = "토끼"+num++;
		System.out.println("123123123"+this.getName());
		this.initialHP = 40;
		System.out.println(this.toString()+"이 생성되었습니다");
	}

	public String toString() { // 시스템 로그에 띄울 때, '토끼1'이 아닌 '토끼'로 뜨게 하려고 만듦
		return "토끼";
	}

	public void paintComponent(Graphics g) // 동물 이미지를 그리는 메소드
	{
		ImageIcon img = new ImageIcon("Rabbit.png");
		Image Img = img.getImage();
		super.paintComponent(g);
		g.drawImage(Img, 0, 0, getWidth(), getHeight(), this);
	}
}


class DeerView extends AnimalView {
	static int num = 1; 
	DeerView(Controller c){
		super(c);
		ani = new M.Deer();
		ani.backLink = this;
		this.name = "사슴"+num++;
		this.initialHP = 500;
		System.out.println(this.toString()+"이 생성되었습니다");
	}

	public String toString() {
		return "사슴";
	}

	public void paintComponent(Graphics g) // 동물 이미지를 그리는 메소드
	{
		ImageIcon img = new ImageIcon("Deer.png");
		Image Img = img.getImage();
		super.paintComponent(g);
		g.drawImage(Img, 0, 0, getWidth(), getHeight(), this);
	}
}

class FoxView extends AnimalView {
	static int num = 1; 
	FoxView(Controller c){
		super(c);
		ani = new M.Fox();
		ani.backLink = this;
		this.name = "여우"+num++;
		this.initialHP = 2000;
		System.out.println(this.toString()+"이 생성되었습니다");
		this.attackingRange *= 2;
	}

	public String toString() {
		return "여우";
	}

	public void paintComponent(Graphics g) // 동물 이미지를 그리는 메소드
	{
		ImageIcon img = new ImageIcon("Fox.png");
		Image Img = img.getImage();
		super.paintComponent(g);
		g.drawImage(Img, 0, 0, getWidth(), getHeight(), this);
	}
}

class WolfView extends AnimalView {
	static int num = 1; 
	WolfView(Controller c){
		super(c);
		ani = new M.Wolf();
		ani.backLink = this;
		this.name = "늑대"+num++;
		this.initialHP = 5000;
		System.out.println(this.toString()+"이 생성되었습니다");
		this.attackingRange *= 3;
	}

	public String toString() {
		return "늑대";
	}

	public void paintComponent(Graphics g) // 동물 이미지를 그리는 메소드
	{
		ImageIcon img = new ImageIcon("Wolf.png");
		Image Img = img.getImage();
		super.paintComponent(g);
		g.drawImage(Img, 0, 0, getWidth(), getHeight(), this);
	}
}

class LionView extends AnimalView {
	static int num = 1; 
	LionView(Controller c){
		super(c);
		ani = new M.Lion();
		ani.backLink = this;
		this.name = "사자"+num++;
		this.initialHP = 10000;
		System.out.println(this.toString()+"이 생성되었습니다");
		this.attackingRange *= 4;
	}

	public String toString() {
		return "사자";
	}

	public void paintComponent(Graphics g) // 동물 이미지를 그리는 메소드
	{
		ImageIcon img = new ImageIcon("Lion.png");
		Image Img = img.getImage();
		super.paintComponent(g);
		g.drawImage(Img, 0, 0, getWidth(), getHeight(), this);
	}
}