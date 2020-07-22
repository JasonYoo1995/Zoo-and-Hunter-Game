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
	private BasicFrame basicFrame; // ���� ������
	ZooPanel zooPanel;
	private ForestPanel forestPanel;
	private HuntingDogView huntingDog;
	private boolean up = false; // ����Ű�� ������ true�� �Ǵ� ����, �׽� ���ư��� �ִ� hunterMovement Thread�� ���� ��ɲ��� �����δ�.
	private boolean down = false; 
	private boolean left = false;
	private boolean right = false;
	private int direction; // ��ɲ��� �ٶ󺸴� ����
	private int positionX; //�� ���̺��� ��ġ
	private int positionY;	
	private AnimalView animalTargeted; // ��ɲ��� ���� �ֱٿ� ������ �����μ�, ü���� 0���� ŭ
	private Thread dogMovement; // ��ɰ��� �����ӿ� ���� thread
	private ShootingPath shootingPath; // �Ѿ��� ��θ� ��Ÿ���� ���� ������ ���̺�. (Runnable ���� : 50ms ���� �����ٰ� �����)
	private Thread shootingThread; // shootingPath�� run�޼ҵ带 ������ �� �ʿ�
	private HunterView hunterView; // Listener������ this�� �� �� ��� ���� �ڱ� �ڽ��� �����ϴ� ������ ����� ����
	private int speed;
	private Iterator animalHPZeroIterator;
	private Iterator animalIterator;
	private Controller c;
	M.Hunter hunter;
	JInternalFrame inventoryFrame = new JInternalFrame();
	JButton exitButton = new JButton("X");
	int num=0;
	Thread counterattackingThread;
	AnimalView recentlyTargeted = null; // ��ɲ��� �ֱٿ� �����ϰ� �ִ� ���� : <�ݰ� �˰���>���� ��.
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


	public void paintComponent(Graphics g) // ��ɲ� �̹���
	{
		ImageIcon Himg = new ImageIcon("Hunter.jpg");
		Image HunterImg = Himg.getImage();
		super.paintComponent(g);
		g.drawImage(HunterImg, 0, 0, getWidth(), getHeight(), this);
	}

	public void searchAnimal() { // HP�� 0�� �ƴ� �����鿡 ���ؼ��� shot �޼ҵ� ���� (shot �޼ҵ嵵 ���� ���� �����ϴ� �޼ҵ尡 �ƴ�)
		if(Forest.getInstance().getListAtForest()==null) return; // ����Ʈ�� ����ִ� ��쿡 ���� ���� ó��
		animalIterator = Forest.getInstance().getListAtForest().iterator(); // ���� �ִ� ������ �߿� ��Ÿ� �ȿ� �ִ� ������ ã�� ���� iterator�� ����
		while(animalIterator.hasNext()) {
			Animal animalInForest = (Animal)this.animalIterator.next(); // ���� �ִ� ������ �߿���
			if(animalInForest.getHP()>0&&animalInForest.backLink!=null) { // ü���� 0�� �ƴ� ��������
				if(shot(animalInForest.backLink)==true) { // ��Ÿ� ���� ���� ���, shot�� �Ѵ�. (��Ÿ� ���� �ִ��� ���δ� shot �޼ҵ� ���ο��� ó��)
					break; // ��Ÿ� ���� ���� ��� s	hot�޼ҵ��� return ���� true�� ���ͼ�, break�� ���� ��Ÿ� �� ���� Ž���� �����Ѵ�. (1������ shot�ϱ� ���� �ʿ��� ����)
					// ��Ÿ� ���� ���� ������ ���� ���, 1������ ����������, ��ɲۿ��� ������ �ִ� ������ �켱������ �����ϴ� �˰����� ���� �������� �ʾ���
				};
			}
		}
	}

	public boolean shot(AnimalView animalSearched) { // ��Ÿ� ���� �ִ� ������ ���� attack�޼ҵ带 ����
		boolean ableToAttack = false; // ��Ÿ� ���� �ְ�, HP�� 0�� �ƴ� ��� true
		forestPanel = c.forestPanel;
		shootingPath = new ShootingPath(forestPanel); // �Ѿ� ��θ� ��Ÿ���� ���̺�. ���̽��� ���� ���̺��� ��ġ�� ũ�⸦ ������ ����
		shootingThread = new Thread((Runnable) shootingPath); // shootingPath ���̺��� run() �޼ҵ� ������ ���� �ʿ�
		int distanceX = Math.abs(this.getPositionX()-animalSearched.positionX); // ��ɲ۰� ���� ������ x��ǥ �Ÿ� 
		int distanceY = Math.abs(this.getPositionY()-animalSearched.positionY); // ��ɲ۰� ���� ������ y��ǥ �Ÿ�

		if(this.direction==0) { // ���� ���� �߿� ��� ���
			if(this.getPositionY()-animalSearched.positionY<this.c.hunter.getShootingDistance()+40
					&& this.getPositionY()-animalSearched.positionY>0
					&& Math.abs(animalSearched.positionX-this.getPositionX())<20) { // ��Ÿ� �˻�
				shootingPath.setBounds(this.getPositionX()+20, animalSearched.positionY+40, 2, distanceY-40); // ������ �Ѿ��� ��θ� ������ (�����ٶ� Label)
				ableToAttack = true; // ���� ����
			}
		}
		else if(this.direction==1) { // �Ʒ��� ���� �߿� ��� ���
			if(animalSearched.positionY-this.getPositionY()<this.c.hunter.getShootingDistance()+40
					&& animalSearched.positionY-this.getPositionY()>0
					&& Math.abs(animalSearched.positionX-this.getPositionX())<20) { // ��Ÿ� �˻�
				shootingPath.setBounds(this.getPositionX()+20, this.getPositionY()+40, 2, distanceY-40);
				ableToAttack = true;
			}
		}
		else if(this.direction==2) { // ���������� ���� �߿� ��� ���
			if(animalSearched.positionX-this.getPositionX()<this.c.hunter.getShootingDistance()+40
					&& animalSearched.positionX-this.getPositionX()>0
					&& Math.abs(animalSearched.positionY-this.getPositionY())<20) { // ��Ÿ� �˻�
				shootingPath.setBounds(this.getPositionX()+40, this.getPositionY()+20, distanceX-40, 2);
				ableToAttack = true;
			}
		}
		else if(this.direction==3) { // �������� ���� �߿� ��� ���
			if(this.getPositionX()-animalSearched.positionX<this.c.hunter.getShootingDistance()+40
					&& this.getPositionX()-animalSearched.positionX>0
					&& Math.abs(animalSearched.positionY-this.getPositionY())<20) { // ��Ÿ� �˻�
				shootingPath.setBounds(animalSearched.positionX+40, this.getPositionY()+20, distanceX-40, 2);
				ableToAttack = true;
			}
		}

		if(ableToAttack) { // ��Ÿ� ���� �ִٸ�
			shootingThread.start(); // �� ���ư��� ����� �����ִ� ���̺��� start.
			this.animalTargeted = animalSearched; // attack�� ���� Ÿ������ ����
			attack(this.animalTargeted); // Ÿ�� ����
		}
		return ableToAttack; // ��Ÿ� ���� ������ true �����Ͽ� ����, ��Ÿ� �ۿ� ������ false �����Ͽ� �ٸ� ���� Ž��
	}
	
	
	public void attack(AnimalView animalTargeted) { // Ÿ�� ����
		if(this.c.hunter.getHP()<=0) { // ��ɲ��� HP�� 0�̸� ���� �Ұ�
			SystemLog.getInstance().printLog("HP�� 0�̹Ƿ�, ������ �� �����ϴ�.");
			return;
		}
		if(animalTargeted!=null) { // ���� ó�� : ������ Ÿ���� �־�߸� ����. (������ HP�� 0�� �Ǹ�, Ÿ�Ͽ��� ��ҵǾ� animalTargeted�� null�� ��)
			animalTargeted.ani.setHP(animalTargeted.ani.getHP()-(this.c.hunter.getPower()+huntingDog.power)); // ������ HP�� '��ɲ۰� ��ɰ��� power�� ��' ��ŭ�� ����
	
				// <�ݰ� �˰���>
				if(recentlyTargeted==null) recentlyTargeted = animalTargeted;
				if(counterattackingThread==null) { // ���� ���� �̷��� ó������ �ݰ��� �� (�����尡 ��������� isAlive() �޼ҵ带 ����� �� ��� ���� ó��)
					counterattackingThread = new Thread(recentlyTargeted); // ������ ����
					counterattackingThread.start(); // ������ ��ɲۿ��� �ݰ��ϱ� ������
				}
				else if(recentlyTargeted != animalTargeted) { // ��� ����� ���߿� �ٲ� ���
					counterattackingThread.interrupt(); // ������ �ݰ��ϴ� ������ �ݰ��� ����
					recentlyTargeted = animalTargeted; // ���� ����� ������Ʈ
					counterattackingThread = new Thread(recentlyTargeted); // �ٲ� ����� �����带 �־���
					counterattackingThread.start(); // �ٲ� ����� ������ ��ɲۿ��� �ݰ��ϱ� ������
				}
				else if(recentlyTargeted == animalTargeted && !counterattackingThread.isAlive()) { // ���� ����� ������, �ݰ��� ���� ���
					counterattackingThread = new Thread(recentlyTargeted);
					counterattackingThread.start(); // ���� ������ �ݰ� �����
				}
	 		    //<�ݰ� �˰��� ��>

		}
		if(animalTargeted.ani.getHP()>0)
			SystemLog.getInstance().printLog(animalTargeted.toString()+"�� HP : "+animalTargeted.ani.getHP());
	}

	public AnimalView capture() { // HP�� 0�� ������ ��ȹ
		if(this.basicFrame.getContentPane()==forestPanel) {
			// forest������ capture
			if(Forest.getInstance().getAnimalHPZeroList().size()==0) return null; // ���� ó�� : ��ȹ�� �� �ִ� ������ ���� ���
			this.animalHPZeroIterator = Forest.getInstance().getAnimalHPZeroList().iterator();
			while(animalHPZeroIterator.hasNext()) {
				Animal animalHPZero = (Animal)this.animalHPZeroIterator.next(); // �˻��� ����
				int Xdifferece = Math.abs(animalHPZero.backLink.positionX - this.getPositionX()); // ��ȹ�� ������ ��ɲ� ������ x��ǥ �Ÿ�
				int Ydifferece = Math.abs(animalHPZero.backLink.positionY - this.getPositionY()); // ��ȹ�� ������ ��ɲ� ������ y��ǥ �Ÿ�
				if(Xdifferece<40 && Ydifferece<40) { // ��ɲ��� ������ ��ȹ�� �� �ִ� ��ġ�� ���� ���
					animalHPZero.backLink.capturedAtForest(); // ���� ��ȹ
					forestPanel.animalGenerator.checkAnimalNumberAtForest(Zoo.getInstance().getLevel());
					return animalHPZero.backLink; // ��ȹ�� ���� ����
				}
			}
		}
		else { // zoo������ capture
			if(Zoo.getInstance().getListAtZoo().isEmpty()) 
				return null;	// ���� ó�� : ��ȹ�� �� �ִ� ������ ���� ���
			Iterator zooAnimalIterator = Zoo.getInstance().getListAtZoo().iterator();
			while(zooAnimalIterator.hasNext()) {
				Animal zooAnimal = (Animal)zooAnimalIterator.next(); // �˻��� ����
				int Xdifferece = Math.abs(((CageTileView)zooAnimal.backLink.getParent()).positionX
						+((CageView)zooAnimal.backLink.getParent().getParent()).positionX - this.getPositionX()); // ��ȹ�� ������ ��ɲ� ������ x��ǥ �Ÿ�
				int Ydifferece = Math.abs(((CageTileView)zooAnimal.backLink.getParent()).positionY
						+((CageView)zooAnimal.backLink.getParent().getParent()).positionY - this.getPositionY()); // ��ȹ�� ������ ��ɲ� ������ y��ǥ �Ÿ�
				if(Xdifferece<40 && Ydifferece<40) { // ��ɲ��� ������ ��ȹ�� �� �ִ� ��ġ�� ���� ���
					zooAnimal.backLink.capturedAtZoo(); // ���� ��ȹ
					c.zooStatusArea.update(); // ������ ����â ������Ʈ
					return zooAnimal.backLink; // ��ȹ�� ���� ����
				}
			}
		}
		return null;
	}

	public void sellAnimal(MerchantView merchant, Animal animal) {
		Inventory.getInstance().getCapturedAnimalList().remove(animal); // ������ ���� �ָӴϿ��� ����
		merchant.buyAnimal(c.hunter, animal); // ���ο��� ���� �ŷ� ��û
	}

	void moveUp()
	{
		while(true) {
			try
			{
				Thread.sleep(speed); // speed�� �ӵ��� ������. (���ڰ� �������� ����)
				if(this.getPositionY()>0) { // �� ������ ����
					this.setPositionY(this.getPositionY() - 1); // ���� �����δ�
					this.setLocation(this.getPositionX(),this.getPositionY()); // ��ɲ��� ��ġ�� ȭ�� �� �ݿ�
					if(huntingDog.independentFromHunter==false) { // ��ɰ��� ������ ���� �����̰� ���� ���� ��
						huntingDog.positionX = this.getPositionX(); // ��ɰ��� ��ɲ��� ���� �����δ� (x��ǥ)
						if(this.getPositionY()<=320) // (�� �ȿ���) 
							huntingDog.positionY = this.getPositionY()+40; // ��ɰ��� ��ɲ��� ���� �����δ�(y��ǥ)
						else if(this.getPositionX()<=320){ // (��ɰ��� y��ǥ�� �� ������ �������� �� �� 1) 
							huntingDog.positionX = this.getPositionX()+40; // ��ɰ��� ��ɲ��� ���� �����δ� (x��ǥ)
							huntingDog.positionY = 360; // ��ɰ��� �� ������ ���� �ʰ� y��ǥ�� 360���� �����Ѵ�.
						}
						else { // (��ɰ��� �� ������ �������� �� �� 2)
							huntingDog.positionX = this.getPositionX()-40;
							huntingDog.positionY = 360; // ��ɰ��� �� ������ ���� �ʰ� y��ǥ�� 360���� �����Ѵ�.
						}
						huntingDog.setLocation(huntingDog.positionX,huntingDog.positionY);  // ��ɰ��� ��ġ�� ȭ�� �� �ݿ�
					}
				}
			}
			catch(InterruptedException e)
			{
				return;
			}
			if(up==false) // Ű���� ����
			{
				break; // �������� �ʴ´�
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
				Thread.sleep(1); // while���� �ʹ� ������ ���ۿ� ������ ����. �ణ�� ������ �ʿ�
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if(Zoo.getInstance().getLevel()>=3 && Zoo.getInstance().getListAtZoo().size()>20){
				c.basicframe.setContentPane(c.ending);
				break;
			}
			
			else if(up==true) moveUp(); // ����Ű�� ������ up�� true�� �ٲ��, ���� �����δ�.
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
		public void keyReleased(KeyEvent e) { // ����Ű�� ���� ����, ��ɲ��� �������� ����
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
			if(e.getKeyCode()!=KeyEvent.VK_A) huntingDog.independentFromHunter = false; // false�̸� ��ɰ�+��ɲ� ������ ����ȭ
			// 'A'�� ������ Ű(=����Ű)�� ���� ��, �⺻������ ��ɰ��� ��ɲ��� �������� ����ȭ�ȴ�. ('A'�� �����ϴ� �߿���, ����ȭ���� �ʾƾ� ��)

			if(e.getKeyCode()==KeyEvent.VK_UP){
				if(up==false) // Ű���带 ��� ������ ��, �̹� true�� ��Ȳ���� ���� �� �Է� �� ���� �Ǵ� ���� ����
				{
					direction = 0; // �ٶ󺸴� ���� ����
					up=true; // ������ �� �ְ� ����
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
				if(c.hunter.isActivated() == false) return; // ��ɲ��� �������� �ִ� ��� ��� �Ұ�
				// <���� �����ϴ� �˰��� + ���� ���� �Ϸ� ��, ��ɰ��� ��ɲۿ��� ���ƿ��� �˰���)
				if(animalTargeted!=null) { // ������ HP�� 0�� �Ǹ�, animalTargeted�� ������Ƿ�, ������ HP�� 0�� �Ǿ��� �� if�� ���
					if(animalTargeted.ani.getHP()>0) searchAnimal(); // ������ HP�� 0�� �ƴϸ�, searchAnimal�� ���� ������ ����
					else { // ������ HP�� 0�� �Ǿ� ��ȹ ������ ���°� �Ǿ��� ��, ��ɰ��� ��ɲ� ������ ���ƿ;� �ϹǷ�, ��ɰ��� ��ǥ ������ ��ɲ� ������ �ٲ���
						animalTargeted.HPzero(); // ������ HP�� 0�� �Ǿ��� ��, �Ͼ�� �ϵ� ����
						if(animalTargeted.positionX<=40 && animalTargeted.positionY>=360) {  // �� �ȿ��� �������� �ϹǷ� ���̽��� 3������ ������
							huntingDog.targetPointX = getPositionX(); // ��ɲ� ��
							huntingDog.targetPointY = getPositionY()-40; // ��ɲ� ��
						}
						else if(animalTargeted.positionX<=40) {
							huntingDog.targetPointX = getPositionX(); // ��ɲ� ��
							huntingDog.targetPointY = getPositionY()+40; // ��ɲ� ��
						}
						else {
							huntingDog.targetPointX = getPositionX()-40; // ��ɲ� ��
							huntingDog.targetPointY = getPositionY(); // ��ɲ� ��
						}
						animalTargeted = null; // ������ HP�� 0�� �Ǹ�, animalTargeted�� ���
						dogMovement = new Thread(huntingDog); // ��ɰ��� ������ �� �ֵ��� Thread ����
						dogMovement.start(); // ��ɰ��� ��ɲ��� ���� ���ƿ�
						huntingDog.independentFromHunter = false; // ��ɲ۰� ��ɰ��� �������� ����ȭ
					}
				}
				else searchAnimal(); // animalTargeted�� ä���� �����Ƿ�, �ֱٿ� �����ߴ� ������ ����

				// <������ �����ϸ�, ��ɰ��� ������ ���� �����̴� �˰���>
				if(dogMovement==null) { // ���� ó��
					dogMovement = new Thread(huntingDog);
				}
				else { 
					if(!dogMovement.isAlive() && animalTargeted!=null) { // isAlive() : �ش� �����尡 ������� ���� �����̸� true ����. ��, dogMovement�� ����Ǿ�� if�� ���
						dogMovement = new Thread(huntingDog);
						huntingDog.targetPointX = animalTargeted.positionX; // ��ɰ��� ��ǥ ������ ������ ��ġ�� ����
						huntingDog.targetPointY = animalTargeted.positionY; // ��ɰ��� ��ǥ ������ ������ ��ġ�� ����
						dogMovement.start(); // ��ɰ� �̵� ����
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
				/**ableToInteract(c.hunterView)�� �ٲ���� nullpointer�ȳ�**/
				if(basicFrame.getContentPane()==zooPanel) { //Merchant, Nurse, Carpenter���� Interact
					if(zooPanel.merchantView.ableToInteract(c.hunterView)==true) { // ���ΰ��� interact
						SystemLog.getInstance().printLog(c.merchandiser.getName()+" : ����ð�! � �Ϸ� �Դ°�? "
								+c.hunter.getName()+"!");
						if(c.inventory.getCapturedAnimalList().size()==0) { // ���� �ָӴϰ� ����ִ� ���
							SystemLog.getInstance().printLog("��� �ִ� ������ �����ϴ�.");
							return;
						}
						hunterView.sellAnimal(zooPanel.merchantView, c.inventory.getCapturedAnimalList().
								get(c.inventory.getCapturedAnimalList().size()-1)); // ���ο��� ���� �ָӴϿ� �ִ� ���� �Ǹ�
						c.hunterStatusArea.update(); // ����â ����
					}
					if(zooPanel.nurseView.ableToInteract(c.hunterView)==true) { // ��ȣ����� interact
						SystemLog.getInstance().printLog(c.nurse.getName()+" : �������! � �Ϸ� ���̳���~? "+c.hunter.getName()+"��!"); 

						/**�ý��۷α׿� �޽����� ����� �ؼ� Model�� NurseŬ������ �ִ� �� ������ ��**/
						if(c.hunter.getHP()>=c.hunter.maxHP) { /**�ִ�ü���� ��**/
							SystemLog.getInstance().printLog(c.hunter.getName()+"��(��) �̹� �ִ�ü���̱� ������ ü��ȸ���� �� �����ϴ�");
						}
						else if(c.hunter.getMoney()>=100 && c.hunter.getHP()<c.hunter.maxHP) { /**�� �ְ� �ִ�ü�� �ƴҶ�**/
							SystemLog.getInstance().printLog(c.hunter.getName()+"��, ü��ȸ���� ���� 10��带 �����ϼ���");
							SystemLog.getInstance().printLog(c.hunter.getName()+"�� ü���� ��� ȸ���߽��ϴ�");
						}
						else if(c.hunter.getMoney()<100){ /**�� ������**/
							SystemLog.getInstance().printLog(c.hunter.getName()+"��(��) ���� �����Ͽ� ü��ȸ���� �� �����ϴ�");
						}
						zooPanel.nurseView.heal(c.hunter);
						c.hunterStatusArea.update(); // ����â ����
					}
					else if(zooPanel.carpenterView.ableToInteract(c.hunterView)==true) { // ������� interact
						SystemLog.getInstance().printLog(c.carpenter.getName()+" : �� �Գ�! ���� ���ΰ�? "
								+c.hunter.getName()+"!!!!");
						int temp = c.carpenter.upgradeCage(c.hunter);
						if(temp == 1) {
							SystemLog.getInstance().printLog("�����츮�� ��ü����!");
							c.cageView[num++].levelUp();
						}
						else if(temp == 2){
							SystemLog.getInstance().printLog("öâ�츮�� ��ü����!");
							c.cageView[num++].levelUp();
						}
						else if(temp == 3){
							SystemLog.getInstance().printLog("����츮�� ��ü����!");
							c.cageView[num++].levelUp();
						}
						else {
							System.out.println("Error occurs");
						}
						c.hunterStatusArea.update(); // ����â ����
						c.zooStatusArea.update();
					}
				}
				if(getX()>40*8 && getY()>=40*4 && getY()<=40*6) { // ��Ż�� ���� ��� �̵�
					basicFrame.setContentPane(c.skyView);
					c.skyView.forestButton.setEnabled(true);
					c.skyView.zooButton.setEnabled(true);
				}
				else if(basicFrame.getContentPane()==zooPanel) { // ���� ����
					for(int i=0; i<5; i++) { // 0~4�� �츮�� ��� Ž��
						if(c.cageView[i].hunterOnCage()==true) {// ��ɲ��� �츮 ���� �ö� �ִٸ�, �ش��ϴ� i�� �츮�� ���Ͽ� Ž��
							System.out.println(i+"�� �츮");
							if(c.inventory.getCapturedAnimalList().size()==0) { // ��ɲ��� ��� �ִ� ������ ������, ���� ���� �Ұ�
								SystemLog.getInstance().printLog("��� �ִ� ������ �����ϴ�.");
								return;
							}
							int index = c.inventory.getCapturedAnimalList().size()-1; // ��� �ִ� ������ ������, ����Ʈ�� ������ ��°���� �������� Ž��
							while(index>=0) { // ����Ʈ�� ������ ��°���� 0��°���� Ž��
								Animal animalToPut = c.inventory.getCapturedAnimalList().get(index); // index��° ������ ���Ͽ� Ž��
								if(c.cageView[i].putAnimal(animalToPut)==true) { // ���� ���ð� ������ �����̸� putAnimal�� true�� �����Ͽ� if�� ���
									Zoo.getInstance().getListAtZoo().add(animalToPut); // '�������� �ִ� ���� ����Ʈ'�� �߰�
									c.inventory.getCapturedAnimalList().remove(index); // '��� �ִ� ���� ����Ʈ'���� ����
									Zoo.getInstance().update();
									c.zooStatusArea.update();
									break; // ������ ���� �Ϸ��Ͽ����Ƿ�, Ž�� ����
								}
								else index--; // ���� �Ұ����� �����̸�, ��� Ž���� ����� ���ʿ� �ִ� ��ҷ� �ٽ� Ž�� (����Ʈ�� ����)
							}
						}
					}
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_Z) hunterView.capture(); // HP�� 0�� ������ �����Ͽ� ��ȹ
		}
	}
}
//'X'Ű : ��Ż�̵� & NPC�� Interact
//'Z'Ű : ���� �ݱ�
//'A'Ű : ���� ����