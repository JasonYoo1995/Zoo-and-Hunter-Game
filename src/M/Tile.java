package M;

public class Tile {
	private Animal ani;
	private Hunter h;

	Tile() {
		ani = null;
		h = null;
	}

	public char showTile() {
		if (ani == null && h == null)
			return 'бр';
		else if (ani != null){
			if(ani.getHP()<=0){
				return 'бу';
			}
			else
			return 'бс';
		}
		else if (h != null)
			return 'б▄';
		else
			return ' ';
	}

	public void setAnimal(Animal ani) {
		this.ani = ani;
	}

	public Animal getAnimal() {
		return ani;
	}

	public void setHunter(Hunter h) {
		this.h = h;
	}
}