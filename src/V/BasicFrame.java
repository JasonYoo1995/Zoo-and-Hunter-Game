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

public class BasicFrame extends JFrame { // ���� ������ �Ǵ� Frame���μ�, �� ������ Panel�� �ٲ�鼭 ��� �̵��� �̷������ 
	public BasicFrame(){
		this.setUndecorated(true); // ��� �ٸ� ����
		this.setResizable(false); // ������ ���� �Ұ�
		this.setVisible(true); 
		this.setBounds(500,200,940,600);
		this.setLayout(null);
		this.setBackground(new Color(83,193,75));
	}
}