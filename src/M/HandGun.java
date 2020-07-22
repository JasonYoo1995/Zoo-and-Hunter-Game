package M;

public class HandGun extends Weapon {
	public HandGun(String name, int price) {
		super(name, price);
		this.flag = 3;
		this.damage = 10;
		this.range = 1;
	}

	@Override
	public void use(Hunter h) {
		// TODO Auto-generated method stub
		h.setPower(h.getPower() + this.damage);
//		h.setDistance(h.getDistance() + this.range);
	}
	
	
}
