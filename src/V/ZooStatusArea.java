package V;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

import C.Controller;
import M.Zoo;

public class ZooStatusArea extends JTextArea{ // singleton (������ ���¸� ��Ÿ���� ����â)
	Controller c;
	public ZooStatusArea(Controller c){
		this.c = c;
		this.setBounds(40,110,420,90);
		this.setEditable(false);
		this.setFocusable(false);
		this.setLineWrap(true);
		this.setFont(new Font("KoPub����ü Medium", Font.PLAIN, 25));
		this.setBackground(new Color(206,251,201));
		this.setText(" * "+Zoo.getInstance().getName()+"\n"+" * "+"������ level : "
		+Zoo.getInstance().getLevel()+"\n"+" * ���� ���� ���� : "+Zoo.getInstance().getListAtZoo().size()+"����");
	}
	public void update() {
		this.setText(" * "+Zoo.getInstance().getName()+"\n"+" * "+"������ level : "
	+Zoo.getInstance().getLevel()+"\n"+" * ���� ���� ���� : "+Zoo.getInstance().getListAtZoo().size()+"����");
	}
}