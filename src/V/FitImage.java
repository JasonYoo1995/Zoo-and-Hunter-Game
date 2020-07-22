package V;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

public class FitImage extends JLabel // �̹����� �� ���̺�. ���̺��� ����� ���� �̹��� ũ�⵵ ������. �������� ���ڷ� Image Ÿ���� �̹����� ����.
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image img;
	FitImage(){
		
	}
	FitImage(Image i){
		img = i;
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		getParent().repaint();
	}
}