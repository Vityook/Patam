package test;


import java.io.ObjectInputStream;
import java.util.Objects;
import java.util.Random;

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
        public int[] lettersInBag = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        public int[] lettersInBagCopy = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        public int amountTiles = 98;

        Tile[] tiles = {new Tile(1,'A'), new Tile(3,'B'), new Tile(3,'C'),
                new Tile(2,'D'), new Tile(1,'E'), new Tile(4,'F'), new Tile(2,'G'),
                new Tile(4,'H'), new Tile(1,'I'), new Tile(8,'J'), new Tile(5,'K'),
                new Tile(1,'L'), new Tile(3,'M'), new Tile(1,'N'), new Tile(1,'O'),
                new Tile(3,'P'), new Tile(10,'Q'), new Tile(1,'R'), new Tile(1,'S'),
                new Tile(1,'T'), new Tile(1,'U'), new Tile(4,'V'), new Tile(4,'W'),
                new Tile(8,'X'), new Tile(4,'Y'), new Tile(10,'Z')};

        public Tile getRand() {
            if (amountTiles == 0)
                return null;
            Random random = new Random();
            int rand = random.nextInt(26);
            while (lettersInBag[rand] == 0)
                rand = (rand + 1) % 26;
            amountTiles--;
            lettersInBag[rand]--;
            return tiles[rand];
        }

        public Tile getTile(char c) {
            if ( amountTiles == 0 )
                return null;
            if(Character.isUpperCase(c)){
                int index = c - 'A';
                if(lettersInBag[index] == 0)
                    return null;
                else{
                    lettersInBag[index]--;
                    amountTiles--;
                    return tiles[index];
                }
            }
            return null;
        }

        public void put(Tile tile){
            int index = tile.letter - 'A';
            if(lettersInBag[index] < lettersInBagCopy[index]){
                lettersInBag[index]++;
                amountTiles++;
            }
        }

        public int size(){
            return amountTiles;
        }

        public int[] getQuantities(){
            return lettersInBag.clone();
        }

        private Bag() {}

        private static Bag instance = null;
        public static Bag getBag() {
            if(instance == null){
                instance = new Bag();
            }
            return instance;
        }
    }


}
