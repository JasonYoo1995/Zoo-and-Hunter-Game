package V;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

import M.Animal;
import M.Expendable;
import M.Hunter;
import M.Merchandiser;
import M.Weapon;

public class InventoryView extends JFrame {
	private JPanel weaponTab, expTab, aniTab;
	private JTabbedPane invenTab;
	
	/**
	 * ���ο� InventoryView
	 * @param merchant
	 */
		public InventoryView(Merchandiser merchant) {
			setTitle("Inventory");
			setBounds(500,200,500, 700);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(false);
			
			invenTab = new JTabbedPane();
			invenTab.addTab("Weapon", weaponTab);
			invenTab.addTab("Expendable", expTab);
			invenTab.addTab("Animal", aniTab);
			add(invenTab);
		
			setVisible(true);
		}
	
	/**
	 * ���Ϳ� InventoryView ������	
	 * @param hunter
	 */
		
	public InventoryView(Hunter hunter) {
		setTitle("Inventory");
		setBounds(500,200,500, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		invenTab = new JTabbedPane();
		invenTab.addTab("Weapon", weaponTab);
		invenTab.addTab("Expendable", expTab);
		invenTab.addTab("Animal", aniTab);
		add(invenTab);
	
		setVisible(true);
	}
	
	//���Ϳ�. Animal ���� �ִ�. ��ȹ�� �������� ���̴� ��!
	public InventoryView(ItemPanelView<Weapon> wpTabView, 
			ItemPanelView<Expendable> expTabView, ItemPanelView<Animal> aniTapView) {
		setTitle("Inventory");
		setBounds(500,200,500, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		expTab = expTabView;
		
		invenTab = new JTabbedPane();
		invenTab.addTab("Weapon", weaponTab);
		invenTab.addTab("Expendable", expTab);
		invenTab.addTab("Animal", aniTab);
		add(invenTab);
	
		setVisible(true);
	}
}
