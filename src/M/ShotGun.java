package M;

public class ShotGun extends Weapon {

	public ShotGun(String name, int price) {
		super(name, price);
		this.flag = 4;
		this.damage = 30;
		this.range = 0;
	}

	@Override
	public void use(Hunter h) { 
		// TODO Auto-generated method stub
		h.setPower(h.getPower() + this.damage);
//		h.setDistance(h.getDistance() + this.range);
	}
	
	
}
