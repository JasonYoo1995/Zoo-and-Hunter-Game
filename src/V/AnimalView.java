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

public class AnimalView extends JLabel implements Runnable{ // �ݰݿ� ���� ������
	int initialHP; // ������ Ż��� HP ȸ�� �뵵�� ���
	String name; // �̸�
	int attackingRange=40; // �ݰ� ������ ��Ÿ�
	int positionX=40, positionY=40; // ��ġ ��ǥ
	ForestPanel forestPanel;
	HunterView hunterView;
	Thread showCounterAttackingAreaArea; // �ݰ� �� ������ ��Ÿ��� �����ִ� ���̺�
	Random random = new Random();
	M.Animal ani;
	Controller c;
	JLabel die = new JLabel("    �� ��");
	

	AnimalView(Controller c){
		this.c = c;
		this.hunterView = c.hunterView;
		this.forestPanel = c.forestPanel;
		this.positionX *= random.nextInt(9); // x��ǥ�� 0~8��° ĭ �� �ϳ��� ���� ���� 
		this.positionY *= random.nextInt(10); // y��ǥ�� 0~9��° ĭ �� �ϳ��� ���� ����
		this.setOpaque(true);
		this.setBounds(positionX, positionY,40,40);
	}

	public void setLocation(int x, int y) { // �޼ҵ� �������̵� : position�� �Բ� �ٲ��ֱ� ����
		super.setLocation(x, y);
		this.positionX = x;
		this.positionY = y;
	}

	public void HPzero() { // HP�� 0�� �Ǿ��� �� ����Ǵ� �ڵ�
		setBackground(Color.red); // ������ ����� ���������� �ٲ�
		Forest.getInstance().getAnimalHPZeroList().add(this.ani);
		SystemLog.getInstance().printLog(Forest.getInstance().getAnimalHPZeroList()
				.get(Forest.getInstance().getAnimalHPZeroList().size()-1).getName()+"�� HP : 0 (��ȹ ����)");
		// ����Ʈ�� �� �߰� �Ǿ�����, ����Ʈ�� ���� �����Ͽ� Ȯ���Ѵ�.
	}

	public void capturedAtForest() { // HP�� 0�� �������� ��ɲ��� �ٰ�����, 'Z'Ű�� ���� ������ ��ȹ�ϴ� ���� ����ȴ�.
		Inventory.getInstance().getCapturedAnimalList().add(this.ani); // ��ɲ��� �κ��丮 �� '��� ���� ���� ����Ʈ'�� �߰��Ѵ�.
		Forest.getInstance().getAnimalHPZeroList().remove(this.ani); // 'Forest ������ HP�� 0�̳� ���� ��ȹ���� ���� �������� ���'���� �ش� ������ �����Ѵ�.
		Forest.getInstance().getListAtForest().remove(this.ani); // 'Forest ���� �ִ� ���� ����Ʈ'���� �����Ѵ�.
		this.setVisible(false); // ��ɲ��� ������ ��ȹ�Ͽ����Ƿ�, ���� �׸��� ȭ�鿡�� �����Ѵ�.
		SystemLog.getInstance().printLog(this.ani.getName()+"�� ��ȹ�Ͽ����ϴ�");
		// ����Ʈ�� �� �߰� �Ǿ�����, ����Ʈ�� ���� �����Ͽ� Ȯ���Ѵ�.
	}

	public void capturedAtZoo() { // HP�� 0�� �������� ��ɲ��� �ٰ�����, 'Z'Ű�� ���� ������ ��ȹ�ϴ� ���� ����ȴ�.
		Inventory.getInstance().getCapturedAnimalList().add(this.ani); // ��ɲ��� �κ��丮 �� '��� ���� ���� ����Ʈ'�� �߰��Ѵ�.
		CageView cageView = (CageView)this.getParent().getParent();
		cageView.cage.getCageAnimalList().remove(this.ani);
		Zoo.getInstance().getListAtZoo().remove(this.ani);
		this.setVisible(false);
		this.repaint();
		cageView.alignCage();
		// ��ɲ��� ������ ��ȹ�Ͽ����Ƿ�, ���� �׸��� ȭ�鿡�� �����Ѵ�.
		SystemLog.getInstance().printLog(this.ani.getName()+"�� ��ȹ�Ͽ����ϴ�");
		// ����Ʈ�� �� �߰� �Ǿ�����, ����Ʈ�� ���� �����Ͽ� Ȯ���Ѵ�.
	}

	public boolean rangeCheck(){ // �ݰ� ��Ÿ� ���� �ִ��� üũ
		int distanceX, distanceY;
		distanceX = Math.abs(hunterView.getPositionX()-this.positionX); // ������ ���� ������ �Ÿ� ����
		distanceY = Math.abs(hunterView.getPositionY()-this.positionY); // ������ ���� ������ �Ÿ� ����
		if(attackingRange>distanceX && attackingRange>distanceY) return true; // ��Ÿ� ���� ����
		else{
			return false; // ��Ÿ� �ۿ� ����
		}

	}

	public void run() { // level2 �̻��� �������� ������ ���� ��, ��Ÿ� ������ �ݰ��� �Ѵ�.
		if(this.ani.getLevel()<2) return;
		boolean inRange; // �ݰ� ��Ÿ� ���� ������ true
		if(c.hunter.activated == false) return;
		while(this.ani.getHP()>0) { // ��� ���� ���� �ݰ� / ���Ͱ� �������� ���� ���� �ݰ�
			inRange = this.rangeCheck(); // ��ɲ��� ��Ÿ� ������ ���� true�� ���ϵǸ� while�� �� �ݰ� ����
			while(inRange) { // ��Ÿ� ���� �ִ� ���ȸ� �ݰ�
				boolean InterruptedwhileSleeping = false;
				try {
					Thread.sleep(3000);// 3�ʸ��� �ݰ�
				} catch (InterruptedException e) {
					//					System.out.println("interrupted during sleep"); // sleep ���� ���� ����� �ٲ� ���
					return;
				}
				if(this.ani.getHP()<=0) return; // ������ HP�� 0�� �Ǹ� �ݰ� ����
				if(this.rangeCheck()==false) return; // ��ɲ��� ��Ÿ� ������ ���ϸ� �ݰ� ��
				if(InterruptedwhileSleeping==true) return; // sleep ���߿� ���� ����� �ٲ�� interrup�� ȣ��� ���
				if(c.hunter.getHP()>this.ani.getPower()) {
					CounterAttackingArea counterAttackingArea = new CounterAttackingArea(forestPanel);
					counterAttackingArea.setBounds(this.positionX-this.attackingRange+40, this.positionY-this.attackingRange+40,
							this.attackingRange*2-40, this.attackingRange*2-40);
					showCounterAttackingAreaArea = new Thread((Runnable) counterAttackingArea);
					showCounterAttackingAreaArea.start();
					c.hunter.setHP(c.hunter.getHP()-this.ani.getPower()); // �ݰ�
					c.hunterStatusArea.update();
					SystemLog.getInstance().printLog(this.toString()+"�� �ݰ��Ͽ� "+this.ani.getPower()+"�� �������� �Ծ����ϴ�.");
				}
				else { // ������ HP�� 0�� �Ǹ� �ݰ� ����
					int lastHP = c.hunter.getHP();
					c.hunter.setHP(0); // ������ HP�� ���̳ʽ��� ���� �ʰ� ����
					c.hunterStatusArea.update();
					SystemLog.getInstance().printLog(this.toString()+"�� �ݰ��Ͽ� "+lastHP+"�� �������� �Ծ����ϴ�.");
					SystemLog.getInstance().printLog("["+c.hunter.getName()+"]�� ����߽��ϴ�!!!!!!!");
					
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
					c.basicframe.setContentPane(c.zooPanel); // ȭ�� ��ȯ
					
					c.zooPanel.map.add(c.hunterView,0); 
					c.zooPanel.map.add(c.huntingDog,0);
					c.hunterView.setLocation(360,200); // hunter�� ��Ż ���� ��ġ�ϵ���
					c.huntingDog.setLocation(360,160); // ��ɰ��� ��Ż ���� ��ġ�ϵ���
					c.hunterView.setPositionX(360);
					c.hunterView.setPositionY(200);
					c.huntingDog.positionX = 360;
					c.huntingDog.positionY = 160;
					c.huntingDog.activated=false; // Zoo���� ��ɰ��� ��ɲ��� ����ٴϴ� ��� �ܿ��� �ƹ� �͵� ���ϰ� ����
					c.hunter.setActivated(false); // Zoo���� ��ɲ��� ������ �� �� ���� ����
					
					c.skyView.skyViewHunter.setLocation(200, 450);
					c.skyView.skyViewHunter.repaint();
					c.zooPanel.add(c.zooStatusArea); // Zoo�� Forest ���� ����ȭ�� ����â���� Zoo�� ����1
		            c.zooPanel.add(c.hunterStatusArea); // Zoo�� Forest ���� ����ȭ�� ����â���� Zoo�� ����2 
		            c.zooPanel.add(SystemLog.getInstance()); // Zoo�� Forest ���� ����ȭ�� ����â���� Zoo�� ����3
					
		            c.zooPanel.add(c.skyView.timeLabel);
					
					
					c.hunterView.requestFocus(); // hunter���� focusing�� �Ǿ��, Ű���带 ������ �� �������� ��ȣ�� hunter���� ���޵�. 
					SystemLog.getInstance().printLog("���������� �̵��մϴ�."); // �ý��� �α׿� ���
					
					
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
	static int num = 1; // �����Ǵ� �䳢�� �� = �� �䳢�� ������ �̸��� ������ �ϱ⵵ �� (�䳢1, �䳢2, ...)
	RabbitView(Controller c){
		super(c);
		ani = new M.Rabbit();
		ani.backLink = this;
		this.name = "�䳢"+num++;
		System.out.println("123123123"+this.getName());
		this.initialHP = 40;
		System.out.println(this.toString()+"�� �����Ǿ����ϴ�");
	}

	public String toString() { // �ý��� �α׿� ��� ��, '�䳢1'�� �ƴ� '�䳢'�� �߰� �Ϸ��� ����
		return "�䳢";
	}

	public void paintComponent(Graphics g) // ���� �̹����� �׸��� �޼ҵ�
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
		this.name = "�罿"+num++;
		this.initialHP = 500;
		System.out.println(this.toString()+"�� �����Ǿ����ϴ�");
	}

	public String toString() {
		return "�罿";
	}

	public void paintComponent(Graphics g) // ���� �̹����� �׸��� �޼ҵ�
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
		this.name = "����"+num++;
		this.initialHP = 2000;
		System.out.println(this.toString()+"�� �����Ǿ����ϴ�");
		this.attackingRange *= 2;
	}

	public String toString() {
		return "����";
	}

	public void paintComponent(Graphics g) // ���� �̹����� �׸��� �޼ҵ�
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
		this.name = "����"+num++;
		this.initialHP = 5000;
		System.out.println(this.toString()+"�� �����Ǿ����ϴ�");
		this.attackingRange *= 3;
	}

	public String toString() {
		return "����";
	}

	public void paintComponent(Graphics g) // ���� �̹����� �׸��� �޼ҵ�
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
		this.name = "����"+num++;
		this.initialHP = 10000;
		System.out.println(this.toString()+"�� �����Ǿ����ϴ�");
		this.attackingRange *= 4;
	}

	public String toString() {
		return "����";
	}

	public void paintComponent(Graphics g) // ���� �̹����� �׸��� �޼ҵ�
	{
		ImageIcon img = new ImageIcon("Lion.png");
		Image Img = img.getImage();
		super.paintComponent(g);
		g.drawImage(Img, 0, 0, getWidth(), getHeight(), this);
	}
}