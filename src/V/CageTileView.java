package V;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import C.Controller;

public class CageTileView extends JPanel {
	AnimalView animal; // � ������ �� �ִ��� -->������� �� ������
	int positionX, positionY; //ȭ��󿡼� �ش� Ÿ���� ��ġ
	
	public Controller c;
	
	public CageTileView(){
		/**CageTileView ��ü �ϳ��� �ᱹ ���� cage�� �ǹ�...�ϴ� �� �ƴ�. cage�� ArrayList�� �� �ε������ ���� �ɵ�**/
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		this.setBorder(border);
		this.setLayout(null);
		this.setSize(40, 40);
		this.setVisible(true);
		this.setOpaque(false);
	}
	
	public void setPosition(int x, int y) {
		this.setLocation(x, y);
		this.positionX = x;
		this.positionY = y;
	}
	
	public void setAnimal(AnimalView animal){ // tile�� ������ �����ϰ� ���� �̹����� ȭ�鿡 ����.
		animal.setVisible(true);
		animal.setLocation(0,0);
		this.animal = animal;
		this.add(animal);
	}
}
