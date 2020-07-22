package V;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

public class ItemPanelView<T> extends JPanel {
	private ArrayList<SlotView<T>> itemViewList;
	private int invNum = 0;
	
	public ItemPanelView(ArrayList<SlotView<T>> itemViewList) {
		setLayout(new GridLayout());
		setSize(300, 500);
//		setBackground(Color.white);
		
		this.itemViewList = itemViewList;
		
		int size = itemViewList.size();
	}
	
	public void setItemViewList(ArrayList<SlotView<T>> itemViewList) {
		this.itemViewList = itemViewList;
	}
	
	public ArrayList<SlotView<T>> getItemViewList(){
		return this.itemViewList;
	}
	
	
}
