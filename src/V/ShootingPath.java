package V;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShootingPath extends JLabel implements Runnable{ // 빨간색 총알의 경로 : 얇은 레이블로 표현 (0.05초 나타났다가 사라지는 스레드)
	ForestPanel forestPanel;
	JPanel mapPanel;
	ShootingPath(ForestPanel forestPanel){ // 'A'키로 사거리 내에서 공격시 생성 및 실행됨
		this.forestPanel =  forestPanel;
		this.mapPanel = this.forestPanel.map;
		this.mapPanel.add(this); // 레이블을 맵 위에 올림
		this.setBackground(Color.red);
		this.setOpaque(true);
	}
	public void run() {
		try {
			Thread.sleep(50); // 0.05초간 올려져 있다가
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mapPanel.remove(this); // 맵에서 없애준다.
		mapPanel.repaint();
		this.repaint();
	}
}
