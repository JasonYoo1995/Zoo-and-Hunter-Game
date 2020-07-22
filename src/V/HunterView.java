package V;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import C.Controller;
import M.Animal;
import M.Forest;
import M.Inventory;
import M.Zoo;

public class HunterView extends JLabel implements Runnable{
	private BasicFrame basicFrame; // 메인 프레임
	ZooPanel zooPanel;
	private ForestPanel forestPanel;
	private HuntingDogView huntingDog;
	private boolean up = false; // 방향키를 눌러서 true가 되는 순간, 항시 돌아가고 있는 hunterMovement Thread에 의해 사냥꾼이 움직인다.
	private boolean down = false; 
	private boolean left = false;
	private boolean right = false;
	private int direction; // 사냥꾼이 바라보는 방향
	private int positionX; //이 레이블의 위치
	private int positionY;	
	private AnimalView animalTargeted; // 사냥꾼이 가장 최근에 공격한 동물로서, 체력은 0보다 큼
	private Thread dogMovement; // 사냥개의 움직임에 대한 thread
	private ShootingPath shootingPath; // 총알의 경로를 나타내는 얇은 빨간색 레이블. (Runnable 구현 : 50ms 동안 보였다가 사라짐)
	private Thread shootingThread; // shootingPath의 run메소드를 실행할 때 필요
	private HunterView hunterView; // Listener에서는 this를 쓸 수 없어서 따로 자기 자신을 참조하는 변수를 만들어 놓음
	private int speed;
	private Iterator animalHPZeroIterator;
	private Iterator animalIterator;
	private Controller c;
	M.Hunter hunter;
	JInternalFrame inventoryFrame = new JInternalFrame();
	JButton exitButton = new JButton("X");
	int num=0;
	Thread counterattackingThread;
	AnimalView recentlyTargeted = null; // 사냥꾼이 최근에 공격하고 있던 동물 : <반격 알고리즘>에서 씀.
	int frameFlag = 0;

	public HunterView(BasicFrame basicFrame, HuntingDogView huntingDog, Controller c){
		this.hunterView = this;
		this.basicFrame = basicFrame;
		this.huntingDog = huntingDog;
		this.positionX = 40*9;
		this.positionY = 40*5;
		this.speed = 2;
		this.c = c;
		this.forestPanel = c.forestPanel;

		this.setBounds(this.getPositionX(), this.getPositionY(),40,40);
		this.setOpaque(true);
		this.addKeyListener(new HunterListener());

		inventoryFrame.setVisible(true);
		inventoryFrame.setBounds(200, 100, 300, 300);
		inventoryFrame.setLayout(null);
		inventoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inventoryFrame.setFocusable(false);
		inventoryFrame.add(exitButton);
		exitButton.setMargin(new Insets(0,0,0,0));
		exitButton.setBounds(inventoryFrame.getWidth()-60, 10, 40, 40);
		exitButton.setFont(new Font("", Font.PLAIN, 20));
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				inventoryFrame.setVisible(false);
				c.hunterView.requestFocus();
			}
		});
	}


	public void paintComponent(Graphics g) // 사냥꾼 이미지
	{
		ImageIcon Himg = new ImageIcon("Hunter.jpg");
		Image HunterImg = Himg.getImage();
		super.paintComponent(g);
		g.drawImage(HunterImg, 0, 0, getWidth(), getHeight(), this);
	}

	public void searchAnimal() { // HP가 0이 아닌 동물들에 대해서만 shot 메소드 실행 (shot 메소드도 아직 실제 공격하는 메소드가 아님)
		if(Forest.getInstance().getListAtForest()==null) return; // 리스트가 비어있는 경우에 대한 예외 처리
		animalIterator = Forest.getInstance().getListAtForest().iterator(); // 숲에 있는 동물들 중에 사거리 안에 있는 동물을 찾기 위해 iterator를 받음
		while(animalIterator.hasNext()) {
			Animal animalInForest = (Animal)this.animalIterator.next(); // 숲에 있는 동물들 중에서
			if(animalInForest.getHP()>0&&animalInForest.backLink!=null) { // 체력이 0이 아닌 동물들은
				if(shot(animalInForest.backLink)==true) { // 사거리 내에 있을 경우, shot을 한다. (사거리 내에 있는지 여부는 shot 메소드 내부에서 처리)
					break; // 사거리 내에 있을 경우 s	hot메소드의 return 값이 true로 나와서, break를 통해 사거리 내 동물 탐색을 중지한다. (1마리만 shot하기 위해 필요한 구문)
					// 사거리 내에 여러 마리가 있을 경우, 1마리만 공격하지만, 사냥꾼에게 가까이 있는 동물을 우선적으로 공격하는 알고리즘은 아직 구현하지 않았음
				};
			}
		}
	}

	public boolean shot(AnimalView animalSearched) { // 사거리 내에 있는 동물에 한해 attack메소드를 실행
		boolean ableToAttack = false; // 사거리 내에 있고, HP가 0이 아닌 경우 true
		forestPanel = c.forestPanel;
		shootingPath = new ShootingPath(forestPanel); // 총알 경로를 나타내는 레이블. 케이스에 따라 레이블의 위치와 크기를 조절할 예정
		shootingThread = new Thread((Runnable) shootingPath); // shootingPath 레이블의 run() 메소드 실행을 위해 필요
		int distanceX = Math.abs(this.getPositionX()-animalSearched.positionX); // 사냥꾼과 동물 사이의 x좌표 거리 
		int distanceY = Math.abs(this.getPositionY()-animalSearched.positionY); // 사냥꾼과 동물 사이의 y좌표 거리

		if(this.direction==0) { // 위로 가던 중에 쏘는 경우
			if(this.getPositionY()-animalSearched.positionY<this.c.hunter.getShootingDistance()+40
					&& this.getPositionY()-animalSearched.positionY>0
					&& Math.abs(animalSearched.positionX-this.getPositionX())<20) { // 사거리 검사
				shootingPath.setBounds(this.getPositionX()+20, animalSearched.positionY+40, 2, distanceY-40); // 빨간색 총알의 경로를 보여줌 (가느다란 Label)
				ableToAttack = true; // 공격 가능
			}
		}
		else if(this.direction==1) { // 아래로 가던 중에 쏘는 경우
			if(animalSearched.positionY-this.getPositionY()<this.c.hunter.getShootingDistance()+40
					&& animalSearched.positionY-this.getPositionY()>0
					&& Math.abs(animalSearched.positionX-this.getPositionX())<20) { // 사거리 검사
				shootingPath.setBounds(this.getPositionX()+20, this.getPositionY()+40, 2, distanceY-40);
				ableToAttack = true;
			}
		}
		else if(this.direction==2) { // 오른쪽으로 가던 중에 쏘는 경우
			if(animalSearched.positionX-this.getPositionX()<this.c.hunter.getShootingDistance()+40
					&& animalSearched.positionX-this.getPositionX()>0
					&& Math.abs(animalSearched.positionY-this.getPositionY())<20) { // 사거리 검사
				shootingPath.setBounds(this.getPositionX()+40, this.getPositionY()+20, distanceX-40, 2);
				ableToAttack = true;
			}
		}
		else if(this.direction==3) { // 왼쪽으로 가던 중에 쏘는 경우
			if(this.getPositionX()-animalSearched.positionX<this.c.hunter.getShootingDistance()+40
					&& this.getPositionX()-animalSearched.positionX>0
					&& Math.abs(animalSearched.positionY-this.getPositionY())<20) { // 사거리 검사
				shootingPath.setBounds(animalSearched.positionX+40, this.getPositionY()+20, distanceX-40, 2);
				ableToAttack = true;
			}
		}

		if(ableToAttack) { // 사거리 내에 있다면
			shootingThread.start(); // 총 날아가는 모습을 보여주는 레이블을 start.
			this.animalTargeted = animalSearched; // attack에 대한 타켓으로 설정
			attack(this.animalTargeted); // 타켓 공격
		}
		return ableToAttack; // 사거리 내에 있으면 true 리턴하여 공격, 사거리 밖에 있으면 false 리턴하여 다른 동물 탐색
	}
	
	
	public void attack(AnimalView animalTargeted) { // 타켓 공격
		if(this.c.hunter.getHP()<=0) { // 사냥꾼의 HP가 0이면 공격 불가
			SystemLog.getInstance().printLog("HP가 0이므로, 공격할 수 없습니다.");
			return;
		}
		if(animalTargeted!=null) { // 예외 처리 : 설정된 타켓이 있어야만 공격. (동물의 HP가 0이 되면, 타켓에서 취소되어 animalTargeted가 null이 됨)
			animalTargeted.ani.setHP(animalTargeted.ani.getHP()-(this.c.hunter.getPower()+huntingDog.power)); // 동물의 HP가 '사냥꾼과 사냥개의 power의 합' 만큼씩 감소
	
				// <반격 알고리즘>
				if(recentlyTargeted==null) recentlyTargeted = animalTargeted;
				if(counterattackingThread==null) { // 게임 시작 이래로 처음으로 반격할 때 (스레드가 비어있으면 isAlive() 메소드를 사용할 수 없어서 예외 처리)
					counterattackingThread = new Thread(recentlyTargeted); // 스레드 장착
					counterattackingThread.start(); // 동물이 사냥꾼에게 반격하기 시작함
				}
				else if(recentlyTargeted != animalTargeted) { // 사냥 대상이 도중에 바뀐 경우
					counterattackingThread.interrupt(); // 기존에 반격하던 동물의 반격을 중지
					recentlyTargeted = animalTargeted; // 공격 대상을 업데이트
					counterattackingThread = new Thread(recentlyTargeted); // 바뀐 대상의 스레드를 넣어줌
					counterattackingThread.start(); // 바뀐 대상의 동물이 사냥꾼에게 반격하기 시작함
				}
				else if(recentlyTargeted == animalTargeted && !counterattackingThread.isAlive()) { // 공격 대상은 같은데, 반격이 끝난 경우
					counterattackingThread = new Thread(recentlyTargeted);
					counterattackingThread.start(); // 같은 동물이 반격 재시작
				}
	 		    //<반격 알고리즘 끝>

		}
		if(animalTargeted.ani.getHP()>0)
			SystemLog.getInstance().printLog(animalTargeted.toString()+"의 HP : "+animalTargeted.ani.getHP());
	}

	public AnimalView capture() { // HP가 0인 동물을 포획
		if(this.basicFrame.getContentPane()==forestPanel) {
			// forest에서의 capture
			if(Forest.getInstance().getAnimalHPZeroList().size()==0) return null; // 예외 처리 : 포획할 수 있는 동물이 없는 경우
			this.animalHPZeroIterator = Forest.getInstance().getAnimalHPZeroList().iterator();
			while(animalHPZeroIterator.hasNext()) {
				Animal animalHPZero = (Animal)this.animalHPZeroIterator.next(); // 검사할 동물
				int Xdifferece = Math.abs(animalHPZero.backLink.positionX - this.getPositionX()); // 포획할 동물과 사냥꾼 사이의 x좌표 거리
				int Ydifferece = Math.abs(animalHPZero.backLink.positionY - this.getPositionY()); // 포획할 동물과 사냥꾼 사이의 y좌표 거리
				if(Xdifferece<40 && Ydifferece<40) { // 사냥꾼이 동물을 포획할 수 있는 위치에 있을 경우
					animalHPZero.backLink.capturedAtForest(); // 동물 포획
					forestPanel.animalGenerator.checkAnimalNumberAtForest(Zoo.getInstance().getLevel());
					return animalHPZero.backLink; // 포획된 동물 리턴
				}
			}
		}
		else { // zoo에서의 capture
			if(Zoo.getInstance().getListAtZoo().isEmpty()) 
				return null;	// 예외 처리 : 포획할 수 있는 동물이 없는 경우
			Iterator zooAnimalIterator = Zoo.getInstance().getListAtZoo().iterator();
			while(zooAnimalIterator.hasNext()) {
				Animal zooAnimal = (Animal)zooAnimalIterator.next(); // 검사할 동물
				int Xdifferece = Math.abs(((CageTileView)zooAnimal.backLink.getParent()).positionX
						+((CageView)zooAnimal.backLink.getParent().getParent()).positionX - this.getPositionX()); // 포획할 동물과 사냥꾼 사이의 x좌표 거리
				int Ydifferece = Math.abs(((CageTileView)zooAnimal.backLink.getParent()).positionY
						+((CageView)zooAnimal.backLink.getParent().getParent()).positionY - this.getPositionY()); // 포획할 동물과 사냥꾼 사이의 y좌표 거리
				if(Xdifferece<40 && Ydifferece<40) { // 사냥꾼이 동물을 포획할 수 있는 위치에 있을 경우
					zooAnimal.backLink.capturedAtZoo(); // 동물 포획
					c.zooStatusArea.update(); // 동물원 상태창 업데이트
					return zooAnimal.backLink; // 포획된 동물 리턴
				}
			}
		}
		return null;
	}

	public void sellAnimal(MerchantView merchant, Animal animal) {
		Inventory.getInstance().getCapturedAnimalList().remove(animal); // 동물을 동물 주머니에서 꺼냄
		merchant.buyAnimal(c.hunter, animal); // 상인에게 동물 거래 요청
	}

	void moveUp()
	{
		while(true) {
			try
			{
				Thread.sleep(speed); // speed의 속도로 움직임. (숫자가 작을수록 빠름)
				if(this.getPositionY()>0) { // 맵 안으로 제한
					this.setPositionY(this.getPositionY() - 1); // 위로 움직인다
					this.setLocation(this.getPositionX(),this.getPositionY()); // 사냥꾼의 위치를 화면 상에 반영
					if(huntingDog.independentFromHunter==false) { // 사냥개가 동물을 향해 움직이고 있지 않을 때
						huntingDog.positionX = this.getPositionX(); // 사냥개도 사냥꾼을 따라 움직인다 (x좌표)
						if(this.getPositionY()<=320) // (맵 안에서) 
							huntingDog.positionY = this.getPositionY()+40; // 사냥개도 사냥꾼을 따라 움직인다(y좌표)
						else if(this.getPositionX()<=320){ // (사냥개의 y좌표가 맵 밖으로 나가려고 할 때 1) 
							huntingDog.positionX = this.getPositionX()+40; // 사냥개도 사냥꾼을 따라 움직인다 (x좌표)
							huntingDog.positionY = 360; // 사냥개가 맵 밖으로 가지 않게 y좌표를 360으로 고정한다.
						}
						else { // (사냥개가 맵 밖으로 나가려고 할 때 2)
							huntingDog.positionX = this.getPositionX()-40;
							huntingDog.positionY = 360; // 사냥개가 맵 밖으로 가지 않게 y좌표를 360으로 고정한다.
						}
						huntingDog.setLocation(huntingDog.positionX,huntingDog.positionY);  // 사냥개의 위치를 화면 상에 반영
					}
				}
			}
			catch(InterruptedException e)
			{
				return;
			}
			if(up==false) // 키보드 때면
			{
				break; // 움직이지 않는다
			}
		}
	}

	void moveDown()
	{
		while(true) {
			try
			{
				Thread.sleep(speed);
				if(getPositionY()<360) {
					setPositionY(getPositionY() + 1);
					setLocation(getPositionX(),getPositionY());
					if(huntingDog.independentFromHunter==false) {
						huntingDog.positionX = this.getPositionX();
						if(getPositionY()>=40)
							huntingDog.positionY = this.getPositionY()-40;
						else if(getPositionX()>=320){
							huntingDog.positionX = getPositionX()-40;
							huntingDog.positionY = 0;
						}
						else {
							huntingDog.positionX = getPositionX()+40;
							huntingDog.positionY = 0;
						}
						huntingDog.setLocation(huntingDog.positionX,huntingDog.positionY);
					}
				}
			}
			catch(InterruptedException e)
			{
				return;
			}
			if(down==false)
			{
				break;
			}
		}
	}
	void moveRight()
	{
		while(true) {
			try
			{
				Thread.sleep(speed);
				if(getPositionX()<360) {
					setPositionX(getPositionX() + 1);
					setLocation(getPositionX(),getPositionY());
					if(huntingDog.independentFromHunter==false) {
						huntingDog.positionY = this.getPositionY();
						if(getPositionX()>=40)
							huntingDog.positionX = this.getPositionX()-40;
						else if(getPositionY()>=320){
							huntingDog.positionY = getPositionY()-40;
							huntingDog.positionX = 0;
						}
						else {
							huntingDog.positionY = getPositionY()+40;
							huntingDog.positionX = 0;
						}
						huntingDog.setLocation(huntingDog.positionX,huntingDog.positionY);
					}
				}
			}
			catch(InterruptedException e)
			{
				return;
			}
			if(right==false)
			{
				break;
			}	
		}
	}
	void moveLeft()
	{
		while(true) {
			try
			{
				Thread.sleep(speed);
				if(getPositionX()>0) {
					setPositionX(getPositionX() - 1);
					setLocation(getPositionX(),getPositionY());
					if(huntingDog.independentFromHunter==false) {
						huntingDog.positionY = this.getPositionY();
						if(getPositionX()<=320)
							huntingDog.positionX = this.getPositionX()+40;
						else if(getPositionY()<=40){
							huntingDog.positionY = getPositionY()+40;
							huntingDog.positionX = 360;
						}
						else {
							huntingDog.positionY = getPositionY()-40;
							huntingDog.positionX = 360;
						}
						huntingDog.setLocation(huntingDog.positionX,huntingDog.positionY);
					}
				}
			}
			catch(InterruptedException e)
			{
				return;
			}
			if(left==false)
			{
				break;
			}
		}
	}

	public void run() {
		while(true)
		{
			try {
				Thread.sleep(1); // while문이 너무 빠르면 동작에 에러가 생김. 약간의 지연이 필요
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if(Zoo.getInstance().getLevel()>=3 && Zoo.getInstance().getListAtZoo().size()>20){
				c.basicframe.setContentPane(c.ending);
				break;
			}
			
			else if(up==true) moveUp(); // 방향키를 누르면 up이 true로 바뀌어, 위로 움직인다.
			else if(down==true) moveDown();
			else if(right==true) moveRight();
			else if(left==true) moveLeft();
			else;
			//			
			//			System.out.println("x,y : "+positionX+" "+positionY);
		}
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	class HunterListener extends KeyAdapter{
		public void keyReleased(KeyEvent e) { // 방향키를 떼는 순간, 사냥꾼의 움직임이 멈춤
			if(e.getKeyCode()==KeyEvent.VK_UP)
			{
				up=false;
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN)
			{
				down=false;
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				right=false;
			}
			else if(e.getKeyCode()==KeyEvent.VK_LEFT)
			{
				left=false;
			}
		}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()!=KeyEvent.VK_A) huntingDog.independentFromHunter = false; // false이면 사냥개+사냥꾼 움직임 동기화
			// 'A'를 제외한 키(=방향키)를 누를 때, 기본적으로 사냥개과 사냥꾼의 움직임은 동기화된다. ('A'로 공격하는 중에는, 동기화되지 않아야 함)

			if(e.getKeyCode()==KeyEvent.VK_UP){
				if(up==false) // 키보드를 길게 눌렀을 때, 이미 true인 상황에서 여러 번 입력 및 실행 되는 것을 방지
				{
					direction = 0; // 바라보는 방향 설정
					up=true; // 움직일 수 있게 만듦
				}
				else
					return;
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				if(down==false)
				{
					direction = 1;
					down=true;
				}
				else
					return;
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				if(right==false)
				{
					direction = 2;
					right=true;
				}
				else
					return;
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT){
				if(left==false)
				{
					direction = 3;
					left=true;
				}
				else
					return;
			}
			if(e.getKeyCode()==KeyEvent.VK_A){
				if(c.hunter.isActivated() == false) return; // 사냥꾼이 동물원에 있는 경우 사냥 불가
				// <동물 공격하는 알고리즘 + 동물 공격 완료 시, 사냥개가 사냥꾼에게 돌아오는 알고리즘)
				if(animalTargeted!=null) { // 동물의 HP가 0이 되면, animalTargeted가 비워지므로, 동물의 HP가 0이 되었을 때 if문 통과
					if(animalTargeted.ani.getHP()>0) searchAnimal(); // 동물의 HP가 0이 아니면, searchAnimal을 통해 동물을 공격
					else { // 동물의 HP가 0이 되어 포획 가능한 상태가 되었을 때, 사냥개는 사냥꾼 옆으로 돌아와야 하므로, 사냥개의 목표 지점을 사냥꾼 옆으로 바꿔줌
						animalTargeted.HPzero(); // 동물의 HP가 0이 되었을 때, 일어나는 일들 실행
						if(animalTargeted.positionX<=40 && animalTargeted.positionY>=360) {  // 맵 안에서 움직여야 하므로 케이스를 3가지로 나눠줌
							huntingDog.targetPointX = getPositionX(); // 사냥꾼 옆
							huntingDog.targetPointY = getPositionY()-40; // 사냥꾼 옆
						}
						else if(animalTargeted.positionX<=40) {
							huntingDog.targetPointX = getPositionX(); // 사냥꾼 옆
							huntingDog.targetPointY = getPositionY()+40; // 사냥꾼 옆
						}
						else {
							huntingDog.targetPointX = getPositionX()-40; // 사냥꾼 옆
							huntingDog.targetPointY = getPositionY(); // 사냥꾼 옆
						}
						animalTargeted = null; // 동물의 HP를 0이 되면, animalTargeted을 비움
						dogMovement = new Thread(huntingDog); // 사냥개가 움직일 수 있도록 Thread 생성
						dogMovement.start(); // 사냥개가 사냥꾼을 향해 돌아옴
						huntingDog.independentFromHunter = false; // 사냥꾼과 사냥개의 움직임을 동기화
					}
				}
				else searchAnimal(); // animalTargeted이 채워져 있으므로, 최근에 공격했던 동물을 공격

				// <동물을 공격하면, 사냥개가 동물을 향해 움직이는 알고리즘>
				if(dogMovement==null) { // 예외 처리
					dogMovement = new Thread(huntingDog);
				}
				else { 
					if(!dogMovement.isAlive() && animalTargeted!=null) { // isAlive() : 해당 스레드가 종료되지 않은 상태이면 true 리턴. 즉, dogMovement가 종료되어야 if문 통과
						dogMovement = new Thread(huntingDog);
						huntingDog.targetPointX = animalTargeted.positionX; // 사냥개의 목표 지점을 동물의 위치로 지정
						huntingDog.targetPointY = animalTargeted.positionY; // 사냥개의 목표 지점을 동물의 위치로 지정
						dogMovement.start(); // 사냥개 이동 시작
					}
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_I){
				switch(frameFlag){
				case 0:
					if(inventoryFrame.getParent() != c.basicframe){
						c.basicframe.add(inventoryFrame, 0);
					}
					inventoryFrame.setVisible(true);
					frameFlag = 1;
					c.hunterView.requestFocus();
					break;
				
				case 1:
					inventoryFrame.setVisible(false);
					c.basicframe.repaint();
					frameFlag = 0;
					break;
				}
			}
			
			
			if(e.getKeyCode()==KeyEvent.VK_X){
				/**ableToInteract(c.hunterView)로 바꿔줘야 nullpointer안남**/
				if(basicFrame.getContentPane()==zooPanel) { //Merchant, Nurse, Carpenter에게 Interact
					if(zooPanel.merchantView.ableToInteract(c.hunterView)==true) { // 상인과의 interact
						SystemLog.getInstance().printLog(c.merchandiser.getName()+" : 어서오시게! 어떤 일로 왔는가? "
								+c.hunter.getName()+"!");
						if(c.inventory.getCapturedAnimalList().size()==0) { // 동물 주머니가 비어있는 경우
							SystemLog.getInstance().printLog("들고 있는 동물이 없습니다.");
							return;
						}
						hunterView.sellAnimal(zooPanel.merchantView, c.inventory.getCapturedAnimalList().
								get(c.inventory.getCapturedAnimalList().size()-1)); // 상인에게 동물 주머니에 있는 동물 판매
						c.hunterStatusArea.update(); // 상태창 업뎃
					}
					if(zooPanel.nurseView.ableToInteract(c.hunterView)==true) { // 간호사와의 interact
						SystemLog.getInstance().printLog(c.nurse.getName()+" : 어서오세요! 어떤 일로 오셨나요~? "+c.hunter.getName()+"님!"); 

						/**시스템로그에 메시지를 띄워야 해서 Model의 Nurse클래스에 있는 거 가져다 씀**/
						if(c.hunter.getHP()>=c.hunter.maxHP) { /**최대체력일 때**/
							SystemLog.getInstance().printLog(c.hunter.getName()+"은(는) 이미 최대체력이기 때문에 체력회복할 수 없습니다");
						}
						else if(c.hunter.getMoney()>=100 && c.hunter.getHP()<c.hunter.maxHP) { /**돈 있고 최대체력 아닐때**/
							SystemLog.getInstance().printLog(c.hunter.getName()+"님, 체력회복을 위해 10골드를 지불하세요");
							SystemLog.getInstance().printLog(c.hunter.getName()+"의 체력을 모두 회복했습니다");
						}
						else if(c.hunter.getMoney()<100){ /**돈 없을때**/
							SystemLog.getInstance().printLog(c.hunter.getName()+"은(는) 돈이 부족하여 체력회복할 수 없습니다");
						}
						zooPanel.nurseView.heal(c.hunter);
						c.hunterStatusArea.update(); // 상태창 업뎃
					}
					else if(zooPanel.carpenterView.ableToInteract(c.hunterView)==true) { // 목수와의 interact
						SystemLog.getInstance().printLog(c.carpenter.getName()+" : 잘 왔네! 무슨 일인가? "
								+c.hunter.getName()+"!!!!");
						int temp = c.carpenter.upgradeCage(c.hunter);
						if(temp == 1) {
							SystemLog.getInstance().printLog("나무우리로 교체성공!");
							c.cageView[num++].levelUp();
						}
						else if(temp == 2){
							SystemLog.getInstance().printLog("철창우리로 교체성공!");
							c.cageView[num++].levelUp();
						}
						else if(temp == 3){
							SystemLog.getInstance().printLog("전기우리로 교체성공!");
							c.cageView[num++].levelUp();
						}
						else {
							System.out.println("Error occurs");
						}
						c.hunterStatusArea.update(); // 상태창 업뎃
						c.zooStatusArea.update();
					}
				}
				if(getX()>40*8 && getY()>=40*4 && getY()<=40*6) { // 포탈을 통해 장소 이동
					basicFrame.setContentPane(c.skyView);
					c.skyView.forestButton.setEnabled(true);
					c.skyView.zooButton.setEnabled(true);
				}
				else if(basicFrame.getContentPane()==zooPanel) { // 동물 전시
					for(int i=0; i<5; i++) { // 0~4번 우리를 모두 탐색
						if(c.cageView[i].hunterOnCage()==true) {// 사냥꾼이 우리 위에 올라가 있다면, 해당하는 i번 우리에 대하여 탐색
							System.out.println(i+"번 우리");
							if(c.inventory.getCapturedAnimalList().size()==0) { // 사냥꾼이 들고 있는 동물이 없으면, 동물 전시 불가
								SystemLog.getInstance().printLog("들고 있는 동물이 없습니다.");
								return;
							}
							int index = c.inventory.getCapturedAnimalList().size()-1; // 들고 있는 동물이 있으면, 리스트의 마지막 번째부터 역순으로 탐색
							while(index>=0) { // 리스트의 마지막 번째부터 0번째까지 탐색
								Animal animalToPut = c.inventory.getCapturedAnimalList().get(index); // index번째 동물에 대하여 탐색
								if(c.cageView[i].putAnimal(animalToPut)==true) { // 동물 전시가 가능한 조건이면 putAnimal은 true를 리턴하여 if문 통과
									Zoo.getInstance().getListAtZoo().add(animalToPut); // '동물원에 있는 동물 리스트'에 추가
									c.inventory.getCapturedAnimalList().remove(index); // '들고 있는 동물 리스트'에서 삭제
									Zoo.getInstance().update();
									c.zooStatusArea.update();
									break; // 동물을 전시 완료하였으므로, 탐색 종료
								}
								else index--; // 전시 불가능한 조건이면, 방금 탐색한 요소의 왼쪽에 있는 요소로 다시 탐색 (리스트의 역순)
							}
						}
					}
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_Z) hunterView.capture(); // HP가 0인 동물에 접근하여 포획
		}
	}
}
//'X'키 : 포탈이동 & NPC와 Interact
//'Z'키 : 동물 줍기
//'A'키 : 동물 공격