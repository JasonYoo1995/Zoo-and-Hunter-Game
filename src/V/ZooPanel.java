package V;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import C.Controller;

public class ZooPanel extends JPanel {
	BasicFrame basicFrame; // 메인 프레임
	public JPanel map; // 맵
	JLabel[][] mapTile; // 맵 안의 타일 (10x10)
	JPanel quickSlot; // 큇 슬롯
	JLabel[][] quickSlotTile; // 퀵 슬롯 안의 타일 (10x2)
	JLabel timeLabel; // 시간
	JButton help, exit; // 도움말/저장/종료 버튼
	HunterView hunter;
	Thread hunterMovement; //숲 맵에서 움직임 구현을 위한 것
	Border border, border2; //라이브러리 함수. JLabel의 굵기를 굵게해주기 위한 것.
	TimeThread timeThread;
	JPanel skyView;
	CageView cage[]; // 동물 우리
	MerchantView merchantView; // 상인
	NurseView nurseView; // 간호사
	CarpenterView carpenterView; // 목수
	AnimalEscaping animalEscaping; // 우리에 있는 동물이 랜덤한 시간 간격으로 탈출하게 하는 장치
	Controller c;
	HelpLabel helpLabel;
	//생성자
	public ZooPanel(BasicFrame basicFrame, HunterView hunter, Controller c){
		this.c = c;
		this.basicFrame = basicFrame;
		this.hunter = hunter;
		this.hunter.zooPanel = this;
		this.skyView = c.skyView;
		this.animalEscaping = new AnimalEscaping(this,c);
		this.animalEscaping.start();


		this.helpLabel = new HelpLabel(c);
		helpLabel.setVisible(false);
		this.add(helpLabel, 0);

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
		help.setFocusable(false);
		add(help);
		
		help.addActionListener(new buttonListner());


		exit = new JButton("종료하기");
		exit.setFont(new Font("돋움체", Font.BOLD, 20));
		exit.setForeground(Color.white);
		exit.setBounds(780,534,120,40);
		exit.setBackground(new Color(70,152,64));
		add(exit);
		exit.addActionListener(new buttonListner());


		map = new JPanel();
		map.setBounds(500, 40, 400, 400);
		map.setBorder(border2);
		map.setLayout(null);
		map.setBackground(new Color(255,204,151));
		add(map);

		// NPC 3명 추가
		this.merchantView = new MerchantView(c);
		this.map.add(merchantView);
		this.nurseView = new NurseView(c); /**NurseView의 생성자 매개변수에 Controller를 추가했기 때문에 여기서도 변경-보라-**/
		this.map.add(nurseView);
		this.carpenterView = new CarpenterView(c);
		this.map.add(carpenterView);

		hunterMovement = new Thread(hunter); 
		hunterMovement.start(); // 헌터의 움직임을 담당하는 스레드 : 프로그램 종료시까지 작동

		mapTile = new JLabel[10][10];
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				mapTile[i][j] = new JLabel();
				mapTile[i][j].setBounds(i*40,j*40,40,40);
				mapTile[i][j].setBorder(border);
				map.add(mapTile[i][j]);
				mapTile[i][j].repaint();
			}
		}

		// 포탈
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

		for(int i=0; i<10; i++) {
			quickSlotTile[i][0].setBounds(i*40,0,40,20);
			quickSlotTile[i][0].setText(" "+Integer.toString((i+1)%10));
		}
		for(int i=0; i<10; i++) {
			quickSlotTile[i][1].setBounds(i*40,20,40,40);
		}
	}
	class buttonListner implements ActionListener{
		int flag=0;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == help){
				if(flag ==0){
					helpLabel.setVisible(true);
					flag=1;
				}
				else if(flag == 1){
					helpLabel.setVisible(false);
					flag=0;
				}
			}
			else if(e.getSource() == exit){
				basicFrame.dispose();
				System.exit(0);
			}
		}

	}
}

