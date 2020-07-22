package M;

public class Rifle extends Weapon {

	public Rifle(String name, int price) {
		super(name, price);
		this.flag = 5;
		this.damage = 20;
		this.range = 2;
	}

	@Override
	public void use(Hunter h) {
		// TODO Auto-generated method stub
		h.setPower(h.getPower() + this.damage);
//		h.setDistance(h.getDistance() + this.range);
	}
	

	
}
