package test;


import java.io.ObjectInputStream;
import java.util.Objects;

public class Tile {
    public final char letter;
    public final int score;

    private Tile(int score, char letter) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return score == tile.score && letter == tile.letter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, letter);
    }

    public static class Bag{

    }


}
