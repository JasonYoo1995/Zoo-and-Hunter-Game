package V;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class SkyViewHunter extends JLabel{
	// SkyView에서 Hunter의 이미지를 담고있는 레이블
	int x, y;
	public void paintComponent(Graphics g)
	{
		ImageIcon Himg = new ImageIcon("Hunter.jpg");
		Image HunterImg = Himg.getImage(); 
		setBounds(x, y, 60, 90);
		super.paintComponent(g);
		g.drawImage(HunterImg, 0, 0, getWidth(), getHeight(), this);
		getParent().repaint();
	}
	SkyViewHunter(){
		x=440;
		y=450;
		setBounds(x, y, 60, 90);
	}
}