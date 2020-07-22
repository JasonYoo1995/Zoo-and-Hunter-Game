package M;

import java.util.ArrayList;

class Map {
	private Tile[][] tile;
	public String name;
	

	Map() {
		tile = new Tile[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				tile[i][j] = new Tile();
			}
		}
	}

	public void showMap() {
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				System.out.print(tile[i][j].showTile() + " ");
			}
			System.out.println();
		}
	}

	public void setTile(Object o, int x, int y) {
		if (o instanceof Hunter)
			tile[x][y].setHunter((Hunter) o);
		if (o instanceof Animal)
			tile[x][y].setAnimal((Animal) o);
	}

	public Tile getTile(int x, int y) {
		return tile[x][y];
	}

	public void blankTile(Object o, int x, int y) {
		if (o instanceof Hunter)
			tile[x][y].setHunter(null);
		if (o instanceof Animal)
			tile[x][y].setAnimal(null);
	}
	

}
