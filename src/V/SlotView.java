package V;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class SlotView<T> extends JPanel implements ActionListener {
	private T item;
	private String itemInfo;
	private JButton itemLabel;
	
	public SlotView(T item, String name, String info, int num) {//20번째 줄에 item.getName()을 사용할 수 없어서 인자로
		this.item = item;                 //그냥 받았습니다.
		setSize(80, 80);
		this.itemLabel = new JButton(); // 이미지 파일 가져오기.
		itemLabel.setText(name + "\n" + num);
		itemLabel.setToolTipText(info);
		itemLabel.addActionListener(this);
		this.itemInfo = info;
		add(itemLabel);
	}
	
	public void setItemLabel(ImageIcon img) {
		itemLabel = new JButton(img);
	}
	
	public JButton getItemLabel() {
		return this.itemLabel;
	}
	
	public void setItem(T item) {
		this.item = item;
	}
	
	public T getItem() {
		return this.item;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == itemLabel) {
			
		}
	}
}
