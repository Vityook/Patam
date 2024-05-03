package test;


import java.util.Arrays;
import java.util.Objects;

public class Word {
	public Tile[] tiles;
	public int row, col;
	public boolean vertical;

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

	@Override
	public int hashCode() {
		return Objects.hash(Arrays.hashCode(tiles), row, col, vertical);
	}
}
