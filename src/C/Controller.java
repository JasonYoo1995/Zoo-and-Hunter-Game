package C;

import javax.swing.JFrame;

import V.*;
import M.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Controller {
	public BasicFrame basicframe; // 기본 틀
	public M.Hunter hunter;
	public BeginningView beginning; // 첫 화면(사냥꾼 이름 설정 화면)
	public SkyView skyView; // 맵 고르는 화면
	public ForestPanel forestPanel; // 숲
	public ZooPanel zooPanel; // 동물원
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
		basicframe.setContentPane(beginning); // 프로그램 시작 시, init 화면부터 보이기 시작
		nurse = M.Nurse.getInstance(); /**모델의 nurse 클래스에서 인스턴스를 받아옴-보라-**/
		nurseView=new NurseView(this); /**그리고 Controller 줘서 이 클래스 안에 있는 것들 받아서 사용하게 함-보라-**/
		carpenter=M.Carpenter.getInstance();
		carpenter.setController(this);
		carpenterView=new CarpenterView(this);
		merchandiser = Merchandiser.getInstance();
		merchantView = new MerchantView(this);
		
	}
}