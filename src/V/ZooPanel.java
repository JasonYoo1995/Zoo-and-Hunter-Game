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
	BasicFrame basicFrame; // ���� ������
	public JPanel map; // ��
	JLabel[][] mapTile; // �� ���� Ÿ�� (10x10)
	JPanel quickSlot; // ţ ����
	JLabel[][] quickSlotTile; // �� ���� ���� Ÿ�� (10x2)
	JLabel timeLabel; // �ð�
	JButton help, exit; // ����/����/���� ��ư
	HunterView hunter;
	Thread hunterMovement; //�� �ʿ��� ������ ������ ���� ��
	Border border, border2; //���̺귯�� �Լ�. JLabel�� ���⸦ �������ֱ� ���� ��.
	TimeThread timeThread;
	JPanel skyView;
	CageView cage[]; // ���� �츮
	MerchantView merchantView; // ����
	NurseView nurseView; // ��ȣ��
	CarpenterView carpenterView; // ���
	AnimalEscaping animalEscaping; // �츮�� �ִ� ������ ������ �ð� �������� Ż���ϰ� �ϴ� ��ġ
	Controller c;
	HelpLabel helpLabel;
	//������
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
		help.setFocusable(false);
		add(help);
		
		help.addActionListener(new buttonListner());


		exit = new JButton("�����ϱ�");
		exit.setFont(new Font("����ü", Font.BOLD, 20));
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

		// NPC 3�� �߰�
		this.merchantView = new MerchantView(c);
		this.map.add(merchantView);
		this.nurseView = new NurseView(c); /**NurseView�� ������ �Ű������� Controller�� �߰��߱� ������ ���⼭�� ����-����-**/
		this.map.add(nurseView);
		this.carpenterView = new CarpenterView(c);
		this.map.add(carpenterView);

		hunterMovement = new Thread(hunter); 
		hunterMovement.start(); // ������ �������� ����ϴ� ������ : ���α׷� ����ñ��� �۵�

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

		// ��Ż
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

