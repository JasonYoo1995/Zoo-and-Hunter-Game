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
	JLabel title; // ���� �̸�
	JLabel label_picture; // ���� ���� ���� ����
	JLabel label_name; // 'ĳ���� �̸�'�̶�� �ؽ�Ʈ�� ���
	JTextField name; // ĳ���� �̸��� �Է��ϴ� �ؽ�Ʈ �ʵ�
	JButton save; // �̸� ���� ��ư

	BasicFrame basicFrame; // ���� ������
	JPanel skyView; // save ��ư Ŭ���� ��ü�Ǵ� ���� ȭ��
	Hunter hunter;
	Controller c;
	
	public BeginningView(BasicFrame basicFrame, JPanel skyView, Hunter hunter, Controller c){
		this.basicFrame = basicFrame;
		this.skyView = skyView; 
		this.hunter = hunter;
		this.c = c; 
		
		// �г� ����
		this.setLayout(null);
		this.setBackground(new Color(83,193,75));
		this.setSize(940,600);
		
		// ���� ���� ���� ����
		ImageIcon i = new ImageIcon("BeginningPicture.jpg"); 
		Image img = i.getImage(); 
		label_picture = new FitImage(img);
		label_picture.setBounds(70, 60, 800, 400); // �̹����� �� ���̺��� setBounds�� ���� ũ��/��ġ ���� ����
		this.add(label_picture);

		// 'ĳ���� �̸�'�̶�� �ؽ�Ʈ�� ���
		label_name = new JLabel("ĳ���� �̸�");
		label_name.setBounds(120, 500, 300, 50);
		label_name.setFont(new Font("Monospaced", Font.BOLD, 30));
		this.add(label_name);
		
		// �ؽ�Ʈ �ʵ� 
		name = new JTextField();
		name.setBounds(320, 500, 300, 50);
		name.setFont(new Font("Monospaced", Font.BOLD, 30));
		this.add(name);
		name.addKeyListener(new KeyAdapter() {
			//�ؽ�Ʈ�ʵ忡 �� ���ڶ� ���� ��ư�� Ȱ��ȭ �ǰ� ��(���� ��ư�� Ȱ��ȭ��)
			public void keyReleased(KeyEvent e) {
				if(name.getText().length()==0)
					save.setEnabled(false);
				else
					save.setEnabled(true);
			}
			
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					basicFrame.setContentPane(skyView); // ȭ�� ��ȯ
					hunter.setName(name.getText());
					c.hunterStatusArea.update();
				}
			}
		});
		
		// ���� ���� ��ư
		save = new JButton("���� ����");
		save.setBounds(650, 500, 160, 50);
		save.setFont(new Font("Monospaced", Font.BOLD, 20));
		save.setEnabled(false);
		this.add(save);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basicFrame.setContentPane(skyView); // ȭ�� ��ȯ
				hunter.setName(name.getText());
				c.hunterStatusArea.update();
			}
		});
	}
}