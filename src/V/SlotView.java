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
	
	public SlotView(T item, String name, String info, int num) {//20��° �ٿ� item.getName()�� ����� �� ��� ���ڷ�
		this.item = item;                 //�׳� �޾ҽ��ϴ�.
		setSize(80, 80);
		this.itemLabel = new JButton(); // �̹��� ���� ��������.
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
