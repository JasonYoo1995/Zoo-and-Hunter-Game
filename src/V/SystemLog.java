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
	static JTextArea area; // ��ũ�� ���� �ȿ� �ִ� TextArea
	private SystemLog(){
		this.setBounds(40,300,420,270);
		area = new JTextArea();
		area.setEditable(false);
		area.setFocusable(false);
		area.setLineWrap(true);
		area.setFont(new Font("������� ExtraBold", Font.PLAIN, 13));
		area.setText(" - ȯ���մϴ�. ������ �������� ��� �������� ��Ȱ�����ּ���!\n - Z: ���� ��ȹ /  X: ��Ż �̵�, ���� ���� / A: ���� ����");
		area.setBackground(new Color(206,251,201));
		this.setViewportView(area);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // '����'�δ� ��ũ�� �ٰ� ������ �ʰ� ��
	}
	public static SystemLog getInstance() {
		if(instance==null) instance = new SystemLog();
		return instance;
	}
	public static void printLog(String log) { // �ý��� �α� â�� �Ű������� ���� String�� �����
		area.setText(area.getText()+"\n - "+log);
	}
}



