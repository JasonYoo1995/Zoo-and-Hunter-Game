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
			return '��';
		else if (ani != null){
			if(ani.getHP()<=0){
				return '��';
			}
			else
			return '��';
		}
		else if (h != null)
			return '��';
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