package V;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

public class FitImage extends JLabel // 이미지가 들어간 레이블. 레이블의 사이즈에 맞춰 이미지 크기도 조절됨. 생성자의 인자로 Image 타입의 이미지를 받음.
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