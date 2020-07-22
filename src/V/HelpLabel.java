package V;

import java.awt.Image;

import javax.swing.ImageIcon;

import C.Controller;

public class HelpLabel extends FitImage{
	
	Controller c;
	HelpLabel(Controller c){
		this.c = c;
		ImageIcon i = new ImageIcon("help.jpg");
		Image img = i.getImage();
		super.img = img;
		this.setBounds(40, 40, 860, 520);
		this.setVisible(true);
	}
}
