package V;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import C.Controller;

public class CageTileView extends JPanel {
	AnimalView animal; // 어떤 동물이 들어가 있는지 -->쓸모없는 것 같은디
	int positionX, positionY; //화면상에서 해당 타일의 위치
	
	public Controller c;
	
	public CageTileView(){
		/**CageTileView 객체 하나가 결국 모델의 cage를 의미...하는 건 아님. cage의 ArrayList의 한 인덱스라고 보면 될듯**/
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
	
	public void setAnimal(AnimalView animal){ // tile에 동물도 저장하고 동물 이미지도 화면에 띄운다.
		animal.setVisible(true);
		animal.setLocation(0,0);
		this.animal = animal;
		this.add(animal);
	}
}
