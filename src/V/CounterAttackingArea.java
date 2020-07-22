package V;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CounterAttackingArea extends JLabel implements Runnable{
	ForestPanel forestPanel;
	JPanel mapPanel;
	CounterAttackingArea(JPanel forestPanel){ // 'A'Ű�� ��Ÿ� ������ ���ݽ� ���� �� �����
		this.forestPanel = (ForestPanel) forestPanel;
		this.mapPanel = this.forestPanel.map;
		this.mapPanel.add(this); // ���̺��� �� ���� �ø�
		this.setOpaque(false);
		Border border = BorderFactory.createLineBorder(Color.GREEN, 5);
		this.setBorder(border);
	}
	public void run() {
		try {
			Thread.sleep(200); // 0.2�ʰ� �÷��� �ִٰ�
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Ȯ��");
		mapPanel.remove(this); // �ʿ��� �����ش�.
		mapPanel.repaint();
		this.repaint();
	}
}
