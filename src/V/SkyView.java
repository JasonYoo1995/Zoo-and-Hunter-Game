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
        
        // timeLabel에 대한 속성만 정해두고 add는 아직 안 했음. 시간은 Zoo나 Forest에서만 보여야 함
        // 동물원과 숲속 둘 다 똑같은 timeLabel을 공유할 예정
        timeLabel = new JLabel();
        timeLabel.setBounds(40,40,420,50);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setFont(new Font("KoPub돋움체 Medium", Font.PLAIN, 34));
        timeLabel.setBackground(new Color(206,251,201));
        timeLabel.setOpaque(true);
		timeThread = new TimeThread(timeLabel); // 시간을 재는 스레드
		zooPanel.timeLabel = timeLabel; // 동물원과 숲에 같은 timeLabel를 설정
		forestPanel.timeLabel = timeLabel; // 동물원과 숲에 같은 timeLabel를 설정

  	    ImageIcon Zimg = new ImageIcon("Zoo.png");
		Image ZooImg = Zimg.getImage();
		zooButton = new FitImage2(ZooImg); // FitImage2는 매개변수로 받은 이미지를 포함하는 JButton을 만들어주는 사용자 지정 클래스이다.
        zooButton.setBounds(100, 170, 250, 250);
        zooButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { // Zoo로 가기
        		// 이동 버튼을 2번 이상 연속으로 누르면, thread 오류 발생하여, 버튼 비활성화
        		zooButton.setEnabled(false);
        		forestButton.setEnabled(false);
        	
        		// <동물원으로 화면이 전환되면서 이루어져야 할 설정들>
        		hunter.setLocation(360,200); // hunter가 포탈 위에 위치하도록
        		huntingDog.setLocation(360,160); // 사냥개가 포탈 옆에 위치하도록
        		zooPanel.map.add(hunter,0); 
        		zooPanel.map.add(huntingDog,0);
        		huntingDog.activated=false; // Zoo에서 사냥개는 사냥꾼을 따라다니는 기능 외에는 아무 것도 못하게 만듦
        		c.hunter.setActivated(false); // Zoo에서 사냥꾼은 공격을 할 수 없게 만듦
        		System.out.println(c.hunter.activated);
        		
        		if(timeThread.secend==0&&timeThread.minute==0) {// 게임 시작 시, 처음으로 버튼을 눌렀을 때, time count 시작
					timeThread.start();
				}
        		
        		zooPanel.add(c.zooStatusArea); // Zoo와 Forest 간에 동기화된 상태창들을 Zoo에 부착1
				zooPanel.add(c.hunterStatusArea); // Zoo와 Forest 간에 동기화된 상태창들을 Zoo에 부착2 
				zooPanel.add(SystemLog.getInstance()); // Zoo와 Forest 간에 동기화된 상태창들을 Zoo에 부착3
				zooPanel.add(timeLabel); // 두 맵 간에 동기화된 시간 레이블을 Zoo에 부착
				      		
        		// SkyView에서 덩치 큰 사냥꾼이 이동한 뒤, 화면이 전환되는 스레드
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
        	public void actionPerformed(ActionEvent e) { // Forest로 가기
        		// 이동 버튼을 2번 이상 연속으로 누르면, thread 오류 발생하여, 버튼 비활성화
        		zooButton.setEnabled(false);
        		forestButton.setEnabled(false);
    
        		// <동물원으로 화면이 전환되면서 이루어져야 할 설정들>
        		hunter.setLocation(360,200); // hunter가 포탈 위에 위치하도록
        		huntingDog.setLocation(360,160); // 사냥개가 포탈 옆에 위치하도록
        		forestPanel.map.add(hunter,0);
        		forestPanel.map.add(huntingDog,0);
        		huntingDog.activated=true; // Forest에서 사냥개가 공격하고 독립적으로 움직일 수 있는 기능 활성화
        		c.hunter.setActivated(true);
        		System.out.println(c.hunter.activated);
    
        		if(timeThread.secend==0&&timeThread.minute==0) {// 게임 시작 시, 처음으로 버튼을 눌렀을 때, time count 시작
					timeThread.start();
				}
        		
        		forestPanel.add(c.zooStatusArea); // Zoo와 Forest 간에 동기화된 상태창들을 Forest 화면 위에 부착1
        		forestPanel.add(c.hunterStatusArea); // Zoo와 Forest 간에 동기화된 상태창들을 Forest 화면 위에 부착2 
        		forestPanel.add(SystemLog.getInstance()); // Zoo와 Forest 간에 동기화된 상태창들을 Forest 화면 위에 부착3
        		forestPanel.add(timeLabel); // 두 맵 간에 동기화된 시간 레이블을 Forest에 부착
        		
        		// SkyView에서 덩치 큰 사냥꾼이 이동한 뒤, 화면이 전환되는 스레드
    		    moveThread2 = new MoveThread2(skyViewHunter,timeThread,c);
           		moveThread2.start();
        	}
        });
      
        guide = new JLabel("이동하고 싶은 곳을 선택하세요!");
        guide.setFont(new Font("KoPub돋움체 Medium", Font.PLAIN, 35));
        guide.setBounds(250, 50, 500, 90);
        add(guide);   
    }
}