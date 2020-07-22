package V;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

import C.Controller;
import M.Hunter;

public class HunterStatusArea extends JTextArea{ // singleton  (������ ���¸� ��Ÿ���� ����â)
	Controller c;
	public HunterStatusArea(Controller c){
		this.c = c;
		this.setBounds(40,220,420,60);
		this.setEditable(false);
		this.setFocusable(false);
		this.setLineWrap(true);
		this.setFont(new Font("KoPub����ü Medium", Font.PLAIN, 25));
		this.setBackground(new Color(206,251,201));
		this.setText(" * ĳ���� �̸� : "+c.hunter.getName()+"\n"+" * "+"�����ڻ� : "+c.hunter.getMoney()+"���   * HP : "+c.hunter.getHP());
	}
	
	public void update() {
		this.setText(" * ĳ���� �̸� : "+c.hunter.getName()+"\n"+" * "+"�����ڻ� : "+c.hunter.getMoney()+"���   * HP : "+c.hunter.getHP());
	}
}
