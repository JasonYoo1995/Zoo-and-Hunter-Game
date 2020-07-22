package M;

public class Nurse extends NPC{
	private static Nurse instance = null;
	
	private Nurse(String name, int money){
		// TODO Auto-generated constructor stub
		super(name, money);
	}
	
	public static Nurse getInstance(){
		if(instance == null){
			instance = new Nurse("��ȣ��", 999999999);
		}
		return instance;
	}
	
	public void heal(Hunter h){
		/**nurse���� ������ ���� Ȯ���ϰ� ü��ȸ�� ����/�����ϰ� �ٲ�. View������ �׷��� ����ϱ� ����**/
		if(h.getMoney()>=100 && h.getHP()<h.maxHP) { /**�� �ְ� �ִ�ü�� �ƴҶ�**/
			System.out.println(h.getName()+"��, ü��ȸ���� ���� 10��带 �����ϼ���");
			h.setHP(h.maxHP);
			h.setMoney(h.getMoney()-100);
			System.out.println(h.getName()+"�� ü���� ��� ȸ���߽��ϴ�");
		}else if(h.getMoney()<10){ /**�� ������**/
				System.out.println(h.getName()+"��(��) ���� �����Ͽ� ü��ȸ���� �� �����ϴ�");
		}else if(h.getHP()>=h.maxHP) { /**�ִ�ü���� ��**/
			System.out.println(h.getName()+"��(��) �̹� �ִ�ü���̱� ������ ü��ȸ���� �� �����ϴ�");
		}
		
		System.out.println(h.getName()+"�� ���� ü��  : "+h.getHP());
		System.out.println(h.getName()+"�� ���� ���  : "+h.getMoney());
	}
	
	@Override
	public void interact(Hunter h) {
		// TODO Auto-generated method stub
		System.out.println("�������! � �Ϸ� ���̳���~? "+h.getName()+"��!");
	}
}
