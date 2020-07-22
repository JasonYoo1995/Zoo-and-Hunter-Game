package M;

public class PowerPotion extends Drink{
	
	public PowerPotion(String name, int price) {
		super(name, price);
		effect = 1;
		flag = 2;
	}

	@Override
	public void use(Hunter h) {
		// TODO Auto-generated method stub
		h.setPower(h.getPower() + effect); 
		
	}

}
