package V;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import C.Controller;
import M.Hunter;

public class BeginningView extends JPanel {
	JLabel title; // 게임 이름
	JLabel label_picture; // 게임 시작 메인 사진
	JLabel label_name; // '캐릭터 이름'이라는 텍스트를 띄움
	JTextField name; // 캐릭터 이름을 입력하는 텍스트 필드
	JButton save; // 이름 저장 버튼

	BasicFrame basicFrame; // 메인 프레임
	JPanel skyView; // save 버튼 클릭시 교체되는 다음 화면
	Hunter hunter;
	Controller c;
	
	public BeginningView(BasicFrame basicFrame, JPanel skyView, Hunter hunter, Controller c){
		this.basicFrame = basicFrame;
		this.skyView = skyView; 
		this.hunter = hunter;
		this.c = c; 
		
		// 패널 설정
		this.setLayout(null);
		this.setBackground(new Color(83,193,75));
		this.setSize(940,600);
		
		// 게임 시작 메인 사진
		ImageIcon i = new ImageIcon("BeginningPicture.jpg"); 
		Image img = i.getImage(); 
		label_picture = new FitImage(img);
		label_picture.setBounds(70, 60, 800, 400); // 이미지가 들어간 레이블을 setBounds를 통해 크기/위치 조절 가능
		this.add(label_picture);

		// '캐릭터 이름'이라는 텍스트를 띄움
		label_name = new JLabel("캐릭터 이름");
		label_name.setBounds(120, 500, 300, 50);
		label_name.setFont(new Font("Monospaced", Font.BOLD, 30));
		this.add(label_name);
		
		// 텍스트 필드 
		name = new JTextField();
		name.setBounds(320, 500, 300, 50);
		name.setFont(new Font("Monospaced", Font.BOLD, 30));
		this.add(name);
		name.addKeyListener(new KeyAdapter() {
			//텍스트필드에 한 글자라도 들어가면 버튼이 활성화 되게 함(시작 버튼이 활성화됨)
			public void keyReleased(KeyEvent e) {
				if(name.getText().length()==0)
					save.setEnabled(false);
				else
					save.setEnabled(true);
			}
			
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					basicFrame.setContentPane(skyView); // 화면 전환
					hunter.setName(name.getText());
					c.hunterStatusArea.update();
				}
			}
		});
		
		// 게임 시작 버튼
		save = new JButton("게임 시작");
		save.setBounds(650, 500, 160, 50);
		save.setFont(new Font("Monospaced", Font.BOLD, 20));
		save.setEnabled(false);
		this.add(save);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basicFrame.setContentPane(skyView); // 화면 전환
				hunter.setName(name.getText());
				c.hunterStatusArea.update();
			}
		});
	}
}