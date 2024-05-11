//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.util.Objects;

public class Word {
	private Tile[] tiles;
	private int row, col;
	private boolean vertical;

	public Word(Tile[] tiles, int row, int col, boolean vertical){
		this.tiles = tiles;
		this.row = row;
		this.col = col;
		this.vertical = vertical;
	}

	public int getCol() {
		return col;
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public int getRow() {
		return row;
	}

	public boolean isVertical() {
		return vertical;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Word word = (Word) o;
		return row == word.row && col == word.col && vertical == word.vertical && Objects.deepEquals(tiles, word.tiles);
	}
}
