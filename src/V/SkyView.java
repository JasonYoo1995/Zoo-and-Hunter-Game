package V;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import C.Controller;

public class SkyView extends JPanel {
	SkyViewHunter skyViewHunter;
    JButton forestButton;
    JButton zooButton;
    JLabel guide;
    MoveThread moveThread;
    MoveThread2 moveThread2;
    JFrame basicFrame;
    ZooPanel zooPanel;
    ForestPanel forestPanel;
    TimeThread timeThread;
    JLabel timeLabel;
    HunterView hunter;
    HuntingDogView huntingDog;
    Controller c;
    public SkyView(Controller c){
    	this.c = c;
    	basicFrame = c.basicframe;
    	zooPanel = (ZooPanel) c.zooPanel;
    	forestPanel = (ForestPanel) c.forestPanel;
    	hunter = c.hunterView;
        huntingDog = c.huntingDog;
        
    	this.setLayout(null);
		this.setBackground(new Color(83,193,75));
  	    this.setSize(940,600);

		skyViewHunter = new SkyViewHunter();
        add(skyViewHunter);
        
        // timeLabel�� ���� �Ӽ��� ���صΰ� add�� ���� �� ����. �ð��� Zoo�� Forest������ ������ ��
        // �������� ���� �� �� �Ȱ��� timeLabel�� ������ ����
        timeLabel = new JLabel();
        timeLabel.setBounds(40,40,420,50);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setFont(new Font("KoPub����ü Medium", Font.PLAIN, 34));
        timeLabel.setBackground(new Color(206,251,201));
        timeLabel.setOpaque(true);
		timeThread = new TimeThread(timeLabel); // �ð��� ��� ������
		zooPanel.timeLabel = timeLabel; // �������� ���� ���� timeLabel�� ����
		forestPanel.timeLabel = timeLabel; // �������� ���� ���� timeLabel�� ����

  	    ImageIcon Zimg = new ImageIcon("Zoo.png");
		Image ZooImg = Zimg.getImage();
		zooButton = new FitImage2(ZooImg); // FitImage2�� �Ű������� ���� �̹����� �����ϴ� JButton�� ������ִ� ����� ���� Ŭ�����̴�.
        zooButton.setBounds(100, 170, 250, 250);
        zooButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { // Zoo�� ����
        		// �̵� ��ư�� 2�� �̻� �������� ������, thread ���� �߻��Ͽ�, ��ư ��Ȱ��ȭ
        		zooButton.setEnabled(false);
        		forestButton.setEnabled(false);
        	
        		// <���������� ȭ���� ��ȯ�Ǹ鼭 �̷������ �� ������>
        		hunter.setLocation(360,200); // hunter�� ��Ż ���� ��ġ�ϵ���
        		huntingDog.setLocation(360,160); // ��ɰ��� ��Ż ���� ��ġ�ϵ���
        		zooPanel.map.add(hunter,0); 
        		zooPanel.map.add(huntingDog,0);
        		huntingDog.activated=false; // Zoo���� ��ɰ��� ��ɲ��� ����ٴϴ� ��� �ܿ��� �ƹ� �͵� ���ϰ� ����
        		c.hunter.setActivated(false); // Zoo���� ��ɲ��� ������ �� �� ���� ����
        		System.out.println(c.hunter.activated);
        		
        		if(timeThread.secend==0&&timeThread.minute==0) {// ���� ���� ��, ó������ ��ư�� ������ ��, time count ����
					timeThread.start();
				}
        		
        		zooPanel.add(c.zooStatusArea); // Zoo�� Forest ���� ����ȭ�� ����â���� Zoo�� ����1
				zooPanel.add(c.hunterStatusArea); // Zoo�� Forest ���� ����ȭ�� ����â���� Zoo�� ����2 
				zooPanel.add(SystemLog.getInstance()); // Zoo�� Forest ���� ����ȭ�� ����â���� Zoo�� ����3
				zooPanel.add(timeLabel); // �� �� ���� ����ȭ�� �ð� ���̺��� Zoo�� ����
				      		
        		// SkyView���� ��ġ ū ��ɲ��� �̵��� ��, ȭ���� ��ȯ�Ǵ� ������
        		moveThread = new MoveThread(skyViewHunter,timeThread,c);
        		moveThread.start();
        	}
        });
        add(zooButton);
	    
	    ImageIcon Fimg = new ImageIcon("Forest.png"); 
		Image ForestImg = Fimg.getImage(); 
		forestButton = new FitImage2(ForestImg);
  	    forestButton.setBounds(590, 170, 250, 250);
        add(forestButton);
        forestButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { // Forest�� ����
        		// �̵� ��ư�� 2�� �̻� �������� ������, thread ���� �߻��Ͽ�, ��ư ��Ȱ��ȭ
        		zooButton.setEnabled(false);
        		forestButton.setEnabled(false);
    
        		// <���������� ȭ���� ��ȯ�Ǹ鼭 �̷������ �� ������>
        		hunter.setLocation(360,200); // hunter�� ��Ż ���� ��ġ�ϵ���
        		huntingDog.setLocation(360,160); // ��ɰ��� ��Ż ���� ��ġ�ϵ���
        		forestPanel.map.add(hunter,0);
        		forestPanel.map.add(huntingDog,0);
        		huntingDog.activated=true; // Forest���� ��ɰ��� �����ϰ� ���������� ������ �� �ִ� ��� Ȱ��ȭ
        		c.hunter.setActivated(true);
        		System.out.println(c.hunter.activated);
    
        		if(timeThread.secend==0&&timeThread.minute==0) {// ���� ���� ��, ó������ ��ư�� ������ ��, time count ����
					timeThread.start();
				}
        		
        		forestPanel.add(c.zooStatusArea); // Zoo�� Forest ���� ����ȭ�� ����â���� Forest ȭ�� ���� ����1
        		forestPanel.add(c.hunterStatusArea); // Zoo�� Forest ���� ����ȭ�� ����â���� Forest ȭ�� ���� ����2 
        		forestPanel.add(SystemLog.getInstance()); // Zoo�� Forest ���� ����ȭ�� ����â���� Forest ȭ�� ���� ����3
        		forestPanel.add(timeLabel); // �� �� ���� ����ȭ�� �ð� ���̺��� Forest�� ����
        		
        		// SkyView���� ��ġ ū ��ɲ��� �̵��� ��, ȭ���� ��ȯ�Ǵ� ������
    		    moveThread2 = new MoveThread2(skyViewHunter,timeThread,c);
           		moveThread2.start();
        	}
        });
      
        guide = new JLabel("�̵��ϰ� ���� ���� �����ϼ���!");
        guide.setFont(new Font("KoPub����ü Medium", Font.PLAIN, 35));
        guide.setBounds(250, 50, 500, 90);
        add(guide);   
    }
}