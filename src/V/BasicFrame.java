package V;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BasicFrame extends JFrame { // 가장 메인이 되는 Frame으로서, 이 위에서 Panel이 바뀌면서 장소 이동이 이루어진다 
	public BasicFrame(){
		this.setUndecorated(true); // 상단 바를 없앰
		this.setResizable(false); // 사이즈 변경 불가
		this.setVisible(true); 
		this.setBounds(500,200,940,600);
		this.setLayout(null);
		this.setBackground(new Color(83,193,75));
	}
}