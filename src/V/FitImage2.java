package V;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JLabel;

public class FitImage2 extends JButton // �̹����� �� ��ư
{	
	Image img;
	FitImage2(Image i){
		img = i;
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		getParent().repaint();
	}
}