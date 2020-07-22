package V;

import java.awt.Color;
import java.util.Random;

import javax.swing.JPanel;

import C.Controller;
import M.Animal;
import M.Cage;
import M.Forest;
import M.Zoo;

public class CageView extends JPanel{ // ���� �츮
	public static int count=0; //cageView�� ������ ���ڸ� ���
	public final int sizeX = 80; // �г�ũ��
	public final int sizeY = 120; 
	public Color color; // ����
	public CageTileView[] cageTile; // ������ �� �ڸ� (JPanel Ÿ��)
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
		for(int i=0; i<6; i++) { //ȭ��󿡼� 6ĭ(�����δ� 5������ �ִ�ġ)
			cageTile[i] = new CageTileView();
			cageTile[i].setVisible(true);
			this.add(cageTile[i]); //������ CageView �гο� add�ϴ� ��
		}
		
		//�г� �󿡼� ���̴� ��ġ ����
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

	boolean hunterOnCage() { // ���Ͱ� �츮 ���� �ö�� ���� ��, true ���� (���Ͱ� 'X'Ű�� ���� �츮�� ������ �����Ϸ���  �� �� ����)
		if(this.positionX<c.hunterView.getPositionX() && this.positionX+80>c.hunterView.getPositionX()
			&& this.positionY<c.hunterView.getPositionY() && this.positionY+120>c.hunterView.getPositionY()) {
			return true;
		}
		else return false;
	}
	
	public boolean putAnimal(Animal animal) { // ������ �츮�� ���� �� �ִ� ������ �Ǹ�, true ����
		if(this.cage.getLevel()==0) { /**�����ڿ��� �̹� count�� ++���������Ƿ� ���� cage�� �ε��� �ѹ��� count-1�� �����ؾ���**/
			SystemLog.getInstance().printLog("�μ��� �����츮���� ������ ���� �� �����ϴ�.");
			return false; // �μ��� �����츮���� ���� ���� �Ұ�
		}
		if(this.cage.getLevel()<animal.getLevel()) {
			SystemLog.getInstance().printLog(animal.getName()+"�� lv."+animal.getLevel()+" �̻��� �츮���� ���� �� �ֽ��ϴ�.");
			return false; // �츮�� �������� ���� ������ ������ ���� �Ұ�
		}
		if(this.cage.getCageAnimalList().size()>=5) {
			SystemLog.getInstance().printLog("�츮�� �� ���� ���� �� �����ϴ�.");
			return false; // �� ���� ���� ���� �Ұ�
		}
		
		// ������ �츮�� �ִ� �ڵ�
		this.cage.getCageAnimalList().add(animal); // '�츮 �ȿ� �ִ� �������� ����Ʈ'�� �߰�
		animal.backLink.setBackground(Color.green);
		alignCage(); // �츮�� ȭ�� ���� �迭�� ����
		SystemLog.getInstance().printLog(animal.getName()+"�� "+"�츮�� �־����ϴ�.");
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
			SystemLog.getInstance().printLog("�� �̻� ���׷��̵� �� �� �����ϴ�!");
			return;
		}
	}
	
	public void alignCage() { // ArrayListó�� remove�� ȭ��󿡼� �ڵ�����
		for(int i=0; i<this.cage.getCageAnimalList().size(); i++) {
			cageTile[i].setAnimal(this.cage.getCageAnimalList().get(i).backLink);
			cageTile[i].getParent().repaint();
		}
	}
	
	public void animalEscaped(Animal animal) {
		this.cage.getCageAnimalList().remove(animal); // '�츮 �ȿ� �ִ� ���� ����Ʈ'���� ����
		animal.backLink.setBackground(Color.blue);
		c.zooStatusArea.update(); // ������ ����â ������Ʈ 
		alignCage(); // �츮�� ȭ�� ���� �迭�� ����
		Forest.getInstance().getListAtForest().add(animal);
		SystemLog.getInstance().printLog((this.cage.num+1)+"�� �츮�� �ִ� "+animal.getName()+"�� �������� �����ƽ��ϴ�.");
		animal.backLink.setLocation(random.nextInt(9)*40, random.nextInt(10)*40);
		c.forestPanel.animalGenerator.putIntoForest(animal);
		// animalGenerator Ŭ������ putIntoForest�� Ȱ���Ͽ�, ȭ����� �߰��� ����Ʈ���� �߰�, ��ġ�� �ʴ� ��ġ �������� �� ���� ����
		animal.setHP(animal.getInitialHP()); // ü�� ȸ��
	}
	
	public void setColor(Color color) {
		this.setBackground(color);
	}
}
