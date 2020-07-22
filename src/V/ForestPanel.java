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
	JFrame basicFrame; // 메인 프레임
	JPanel map; // 맵
	JLabel[][] mapTile; // 맵 안의 타일 (10x10)
	JPanel quickSlot; // 큇 슬롯
	JLabel[][] quickSlotTile; // 퀵 슬롯 안의 타일 (10x2)
	JLabel timeLabel; // 시간
	JButton help, save, exit; // 도움말/저장/종료 버튼
	HunterView hunter;
	HuntingDogView huntingDog;
	Thread hunterMovement; // 헌터의 움직임을 담당하는 스레드. (처음으로 맵에 들어갈 때 start 되어, 게임이 종료될 때까지 run)
	Border border, border2; // 패널과 레이블에 적용할 테두리 두께 속성
	TimeThread timeth; // 시간을 담당하는 스레드. (처음으로 맵에 들어갈 때 start 되어, 게임이 종료될 때까지 run)
	JPanel skyView; // 화면 전환을 위해서 받아 놓은 패널 (맵 선택 화면)
	AnimalGenerator animalGenerator;
	Controller c;
	public ForestPanel(JFrame basicFrame, HunterView hunter, HuntingDogView huntingDog, Controller c){
		this.c = c;
		this.basicFrame = basicFrame;
		this.hunter = hunter;
		this.huntingDog = huntingDog;
		this.skyView = c.skyView;
		
		border = BorderFactory.createLineBorder(Color.BLACK, 1); // 두께 1
		border2 = BorderFactory.createLineBorder(Color.BLACK, 2); // 두께 2
		
		this.setLayout(null);
		this.setBackground(new Color(83,193,75));
		this.setSize(940,600);

		timeLabel = new JLabel();
		add(timeLabel);

		help = new JButton("도움말");
		help.setFont(new Font("돋움체", Font.BOLD, 20));
		help.setForeground(Color.white);
		help.setBounds(500,534,120,40);
		help.setBackground(new Color(70,152,64));
		add(help);
		
		save = new JButton("저장하기");
		save.setFont(new Font("돋움체", Font.BOLD, 20));
		save.setForeground(Color.white);
		save.setBounds(640,534,120,40);
		save.setBackground(new Color(70,152,64));
		add(save);
		
		exit = new JButton("종료하기");
		exit.setFont(new Font("돋움체", Font.BOLD, 20));
		exit.setForeground(Color.white);
		exit.setBounds(780,534,120,40);
		exit.setBackground(new Color(70,152,64));
		add(exit);
		exit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		       basicFrame.dispose(); // 프레임 닫기 -> 나중에 인벤토리나 거래창에서 활용할 수 있을 법한 메소드
		       System.exit(0); // 프로그램 강제 종료
		    }
		});
		
		// 맵
		map = new JPanel();
		map.setBounds(500, 40, 400, 400);
		map.setBorder(border2);
		map.setLayout(null);
		map.setBackground(new Color(255,204,151));
		add(map);
		
		mapTile = new JLabel[10][10]; // 100x100 맵 위의 칸을 나타내는 레이블
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				mapTile[i][j] = new JLabel();
				mapTile[i][j].setBounds(i*40,j*40,40,40);
				mapTile[i][j].setBorder(border);
				map.add(mapTile[i][j],3,0);
				mapTile[i][j].repaint();
			}
		}
		
		// 포탈 (맵 이동 위치 : 보라색)
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
		for(int i=0; i<10; i++) { // 퀵 슬롯에 번호 붙여주기
			quickSlotTile[i][0].setBounds(i*40,0,40,20);
			quickSlotTile[i][0].setText(" "+Integer.toString((i+1)%10));
		}
		for(int i=0; i<10; i++) { // 퀵 슬롯에서 아이템 영역
			quickSlotTile[i][1].setBounds(i*40,20,40,40);
		}
		animalGenerator = new AnimalGenerator(this,c); // 동물 자동 생성기
	}
}