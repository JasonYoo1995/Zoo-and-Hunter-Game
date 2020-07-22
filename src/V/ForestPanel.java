package V;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import C.Controller;

public class ForestPanel extends JPanel {
	JFrame basicFrame; // ���� ������
	JPanel map; // ��
	JLabel[][] mapTile; // �� ���� Ÿ�� (10x10)
	JPanel quickSlot; // ţ ����
	JLabel[][] quickSlotTile; // �� ���� ���� Ÿ�� (10x2)
	JLabel timeLabel; // �ð�
	JButton help, save, exit; // ����/����/���� ��ư
	HunterView hunter;
	HuntingDogView huntingDog;
	Thread hunterMovement; // ������ �������� ����ϴ� ������. (ó������ �ʿ� �� �� start �Ǿ�, ������ ����� ������ run)
	Border border, border2; // �гΰ� ���̺� ������ �׵θ� �β� �Ӽ�
	TimeThread timeth; // �ð��� ����ϴ� ������. (ó������ �ʿ� �� �� start �Ǿ�, ������ ����� ������ run)
	JPanel skyView; // ȭ�� ��ȯ�� ���ؼ� �޾� ���� �г� (�� ���� ȭ��)
	AnimalGenerator animalGenerator;
	Controller c;
	public ForestPanel(JFrame basicFrame, HunterView hunter, HuntingDogView huntingDog, Controller c){
		this.c = c;
		this.basicFrame = basicFrame;
		this.hunter = hunter;
		this.huntingDog = huntingDog;
		this.skyView = c.skyView;
		
		border = BorderFactory.createLineBorder(Color.BLACK, 1); // �β� 1
		border2 = BorderFactory.createLineBorder(Color.BLACK, 2); // �β� 2
		
		this.setLayout(null);
		this.setBackground(new Color(83,193,75));
		this.setSize(940,600);

		timeLabel = new JLabel();
		add(timeLabel);

		help = new JButton("����");
		help.setFont(new Font("����ü", Font.BOLD, 20));
		help.setForeground(Color.white);
		help.setBounds(500,534,120,40);
		help.setBackground(new Color(70,152,64));
		add(help);
		
		save = new JButton("�����ϱ�");
		save.setFont(new Font("����ü", Font.BOLD, 20));
		save.setForeground(Color.white);
		save.setBounds(640,534,120,40);
		save.setBackground(new Color(70,152,64));
		add(save);
		
		exit = new JButton("�����ϱ�");
		exit.setFont(new Font("����ü", Font.BOLD, 20));
		exit.setForeground(Color.white);
		exit.setBounds(780,534,120,40);
		exit.setBackground(new Color(70,152,64));
		add(exit);
		exit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		       basicFrame.dispose(); // ������ �ݱ� -> ���߿� �κ��丮�� �ŷ�â���� Ȱ���� �� ���� ���� �޼ҵ�
		       System.exit(0); // ���α׷� ���� ����
		    }
		});
		
		// ��
		map = new JPanel();
		map.setBounds(500, 40, 400, 400);
		map.setBorder(border2);
		map.setLayout(null);
		map.setBackground(new Color(255,204,151));
		add(map);
		
		mapTile = new JLabel[10][10]; // 100x100 �� ���� ĭ�� ��Ÿ���� ���̺�
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				mapTile[i][j] = new JLabel();
				mapTile[i][j].setBounds(i*40,j*40,40,40);
				mapTile[i][j].setBorder(border);
				map.add(mapTile[i][j],3,0);
				mapTile[i][j].repaint();
			}
		}
		
		// ��Ż (�� �̵� ��ġ : �����)
		mapTile[9][5].setBackground(new Color(199,47,179));
		mapTile[9][5].setOpaque(true);
		
		quickSlot = new JPanel();
		quickSlot.setBounds(500, 450, 400, 60);
		quickSlot.setLayout(null);
		quickSlot.setBorder(border2);
		quickSlot.setBackground(new Color(213, 213, 213));
		add(quickSlot);

		quickSlotTile = new JLabel[10][2];
		for(int i=0; i<10; i++) {
			for(int j=0; j<2; j++) {
				quickSlotTile[i][j] = new JLabel();
				quickSlotTile[i][j].setBorder(border);
				quickSlot.add(quickSlotTile[i][j]);
			}
		}
		for(int i=0; i<10; i++) { // �� ���Կ� ��ȣ �ٿ��ֱ�
			quickSlotTile[i][0].setBounds(i*40,0,40,20);
			quickSlotTile[i][0].setText(" "+Integer.toString((i+1)%10));
		}
		for(int i=0; i<10; i++) { // �� ���Կ��� ������ ����
			quickSlotTile[i][1].setBounds(i*40,20,40,40);
		}
		animalGenerator = new AnimalGenerator(this,c); // ���� �ڵ� ������
	}
}