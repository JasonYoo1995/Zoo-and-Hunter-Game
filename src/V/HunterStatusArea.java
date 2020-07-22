package V;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

import C.Controller;
import M.Hunter;

public class HunterStatusArea extends JTextArea{ // singleton  (헌터의 상태를 나타내는 상태창)
	Controller c;
	public HunterStatusArea(Controller c){
		this.c = c;
		this.setBounds(40,220,420,60);
		this.setEditable(false);
		this.setFocusable(false);
		this.setLineWrap(true);
		this.setFont(new Font("KoPub돋움체 Medium", Font.PLAIN, 25));
		this.setBackground(new Color(206,251,201));
		this.setText(" * 캐릭터 이름 : "+c.hunter.getName()+"\n"+" * "+"소유자산 : "+c.hunter.getMoney()+"골드   * HP : "+c.hunter.getHP());
	}
	
	public void update() {
		this.setText(" * 캐릭터 이름 : "+c.hunter.getName()+"\n"+" * "+"소유자산 : "+c.hunter.getMoney()+"골드   * HP : "+c.hunter.getHP());
	}
}
