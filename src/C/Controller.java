package C;

import javax.swing.JFrame;

import V.*;
import M.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Controller {
	public BasicFrame basicframe; // �⺻ Ʋ
	public M.Hunter hunter;
	public BeginningView beginning; // ù ȭ��(��ɲ� �̸� ���� ȭ��)
	public SkyView skyView; // �� ���� ȭ��
	public ForestPanel forestPanel; // ��
	public ZooPanel zooPanel; // ������
	public HunterView hunterView;
	public HuntingDogView huntingDog;
	public AnimalView animalView;
	public AnimalEscaping animalEscaping;
	public ZooStatusArea zooStatusArea;
	public M.Nurse nurse;
	public NurseView nurseView;
	public M.Carpenter carpenter; 
	public CarpenterView carpenterView;
	public CageView[] cageView = new CageView[5];
	public Inventory inventory;
	public HunterStatusArea hunterStatusArea;
	public M.Merchandiser merchandiser;
	public MerchantView merchantView;
	public EndingView ending;
	
	public Controller() {
		initialize();
	}
	
	public void initialize() {
		inventory = Inventory.getInstance();
		hunter = new Hunter();
		basicframe = new BasicFrame(); 
		hunterStatusArea = new HunterStatusArea(this);
		zooStatusArea = new ZooStatusArea(this);
		huntingDog = new HuntingDogView(); 
		hunterView = new HunterView(basicframe, huntingDog, this); 
		zooPanel = new ZooPanel(basicframe, hunterView, this);
		forestPanel = new ForestPanel(basicframe, hunterView, huntingDog, this);
		
		for (int i = 0; i < cageView.length; i++) {
			cageView[i] = new CageView(this);
			zooPanel.map.add(cageView[i]);
		}
		
		cageView[0].setPosition(40, 0);
		cageView[1].setPosition(40*5, 0);
		cageView[2].setPosition(0, 40*7);
		cageView[3].setPosition(40*4, 40*7);
		cageView[4].setPosition(40*8, 40*7);
		
		ending = new EndingView(this);
		skyView = new SkyView(this);
		beginning = new BeginningView(basicframe, skyView, hunter, this); 
		basicframe.setContentPane(beginning); // ���α׷� ���� ��, init ȭ����� ���̱� ����
		nurse = M.Nurse.getInstance(); /**���� nurse Ŭ�������� �ν��Ͻ��� �޾ƿ�-����-**/
		nurseView=new NurseView(this); /**�׸��� Controller �༭ �� Ŭ���� �ȿ� �ִ� �͵� �޾Ƽ� ����ϰ� ��-����-**/
		carpenter=M.Carpenter.getInstance();
		carpenter.setController(this);
		carpenterView=new CarpenterView(this);
		merchandiser = Merchandiser.getInstance();
		merchantView = new MerchantView(this);
		
	}
}