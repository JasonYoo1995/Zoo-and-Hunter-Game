package V;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShootingPath extends JLabel implements Runnable{ // ������ �Ѿ��� ��� : ���� ���̺�� ǥ�� (0.05�� ��Ÿ���ٰ� ������� ������)
	ForestPanel forestPanel;
	JPanel mapPanel;
	ShootingPath(ForestPanel forestPanel){ // 'A'Ű�� ��Ÿ� ������ ���ݽ� ���� �� �����
		this.forestPanel =  forestPanel;
		this.mapPanel = this.forestPanel.map;
		this.mapPanel.add(this); // ���̺��� �� ���� �ø�
		this.setBackground(Color.red);
		this.setOpaque(true);
	}
	public void run() {
		try {
			Thread.sleep(50); // 0.05�ʰ� �÷��� �ִٰ�
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mapPanel.remove(this); // �ʿ��� �����ش�.
		mapPanel.repaint();
		this.repaint();
	}
}
