package V;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CounterAttackingArea extends JLabel implements Runnable{
	ForestPanel forestPanel;
	JPanel mapPanel;
	CounterAttackingArea(JPanel forestPanel){ // 'A'키로 사거리 내에서 공격시 생성 및 실행됨
		this.forestPanel = (ForestPanel) forestPanel;
		this.mapPanel = this.forestPanel.map;
		this.mapPanel.add(this); // 레이블을 맵 위에 올림
		this.setOpaque(false);
		Border border = BorderFactory.createLineBorder(Color.GREEN, 5);
		this.setBorder(border);
	}
	public void run() {
		try {
			Thread.sleep(200); // 0.2초간 올려져 있다가
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("확인");
		mapPanel.remove(this); // 맵에서 없애준다.
		mapPanel.repaint();
		this.repaint();
	}
}
