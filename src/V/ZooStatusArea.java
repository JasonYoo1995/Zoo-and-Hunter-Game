package V;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

import C.Controller;
import M.Zoo;

public class ZooStatusArea extends JTextArea{ // singleton (동물원 상태를 나타내는 상태창)
	Controller c;
	public ZooStatusArea(Controller c){
		this.c = c;
		this.setBounds(40,110,420,90);
		this.setEditable(false);
		this.setFocusable(false);
		this.setLineWrap(true);
		this.setFont(new Font("KoPub돋움체 Medium", Font.PLAIN, 25));
		this.setBackground(new Color(206,251,201));
		this.setText(" * "+Zoo.getInstance().getName()+"\n"+" * "+"동물원 level : "
		+Zoo.getInstance().getLevel()+"\n"+" * 전시 중인 동물 : "+Zoo.getInstance().getListAtZoo().size()+"마리");
	}
	public void update() {
		this.setText(" * "+Zoo.getInstance().getName()+"\n"+" * "+"동물원 level : "
	+Zoo.getInstance().getLevel()+"\n"+" * 전시 중인 동물 : "+Zoo.getInstance().getListAtZoo().size()+"마리");
	}
}