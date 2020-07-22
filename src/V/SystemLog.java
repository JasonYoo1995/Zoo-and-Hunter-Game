package V;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import C.Controller;
import M.Hunter;

public class SystemLog extends JScrollPane{ // singleton
	private static SystemLog instance;
	static JTextArea area; // 스크롤 페인 안에 있는 TextArea
	private SystemLog(){
		this.setBounds(40,300,420,270);
		area = new JTextArea();
		area.setEditable(false);
		area.setFocusable(false);
		area.setLineWrap(true);
		area.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 13));
		area.setText(" - 환영합니다. 도망간 동물들을 잡아 동물원을 부활시켜주세요!\n - Z: 동물 포획 /  X: 포탈 이동, 동물 전시 / A: 동물 공격");
		area.setBackground(new Color(206,251,201));
		this.setViewportView(area);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // '가로'로는 스크롤 바가 생기지 않게 함
	}
	public static SystemLog getInstance() {
		if(instance==null) instance = new SystemLog();
		return instance;
	}
	public static void printLog(String log) { // 시스템 로그 창에 매개변수로 받은 String을 띄워줌
		area.setText(area.getText()+"\n - "+log);
	}
}



