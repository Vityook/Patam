package test;


public class Board {
    Tile[][] board;

    static boolean middleCheck ;
    int [] letterScore = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10}; //ascii start at 65=A -> 90=Z

    public static char [][] boardScore =
            {
                    {'R','N','N','T','N','N','N','R','N','N','N','T','N','N','R'},
                    {'N','Y','N','N','N','B','N','N','N','B','N','N','N','Y','N'},
                    {'N','N','Y','N','N','N','T','N','T','N','N','N','Y','N','N'},
                    {'T','N','N','Y','N','N','N','T','N','N','N','Y','N','N','T'},
                    {'N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N'},
                    {'N','B','N','N','N','B','N','N','N','B','N','N','N','B','N'},
                    {'N','N','T','N','N','N','T','N','T','N','N','N','T','N','N'},
                    {'R','N','N','T','N','N','N','C','N','N','N','T','N','N','R'},//8
                    {'N','N','T','N','N','N','T','N','T','N','N','N','T','N','N'},
                    {'N','B','N','N','N','B','N','N','N','B','N','N','N','B','N'},
                    {'N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N'},
                    {'T','N','N','Y','N','N','N','T','N','N','N','Y','N','N','T'},
                    {'N','N','Y','N','N','N','T','N','T','N','N','N','Y','N','N'},
                    {'N','Y','N','N','N','B','N','N','N','B','N','N','N','Y','N'},
                    {'R','N','N','T','N','N','N','R','N','N','N','T','N','N','R'},
            };
    private Board() {
        board = new Tile[15][15];
        middleCheck = false;
    }

    private static Board instance = null;
    public static Board getBoard(){
        if(instance == null)
            instance = new Board();
        return instance;
    }

    public Tile[][] getTiles(){
        return board.clone();
    }

    public boolean boardLegal(Word w){
        boolean check;
        int x;
        check = outsideBoard(w);
        if (!check)
            return false;
        check = tooLong(w);
        if (!check)
            return false;
        x = firstToStart(w);
        if (x == -1 )
            return false;
        if (x == 1)
            return true;

        check =  legalLetters(w);
        if(!check) return false;

        check = overlappingWords(w);
    }

    static boolean outsideBoard(Word w) {
        return !(w.getRow() < 0 || w.getRow() > 15 || w.getCol() < 0 || w.getCol() > 15);
    }

    static boolean tooLong(Word w) {
        return !(w.vertical && (w.tiles.length + w.getRow()) > 15) || (!(w.vertical) && (w.tiles.length + w.getCol() > 15));
    }

    int firstToStart(Word w) {
        if(board[7][7] == null) {
            for (int i = 0; i < w.tiles.length; i++) {
                if (w.tiles[i] == null)
                    return -1;
            }
            if (w.getCol() == 7 && w.getRow() == 7)
                if (w.getRow() + w.tiles.length >= 7 && w.getRow() + w.tiles.length < 15 && w.getCol() <= 7 && w.getCol() + w.tiles.length >= 7 && w.getCol() + w.tiles.length < 15)
                    return 1;
                else return -1;

            if (w.getCol() == 7) {
                if (w.vertical)
                    if (w.getRow() <= 7 && w.getRow() + w.tiles.length - 1 >= 7 && w.getRow() + w.tiles.length - 1 < 15)
                        return 1;
                    else return -1;
            }

            if (w.getRow() == 7) {
                if (w.vertical)
                    if (w.getCol() <= 7 && w.getCol() + w.tiles.length - 1 >= 7 && w.getCol() + w.tiles.length - 1 < 15)
                        return 1;
                    else return -1;
            }
        }

        return 0;
    }

    static boolean legalLetters(Word w){
        for(int i = 0 ; i < w.tiles.length; i++)
            if(w.tiles[i].letter < 'A' || w.tiles[i].letter > 'Z' || w.tiles[i] == null)
                return false;
        return true;
    }

    static boolean overlappingWords(Word w){
        for(int i = 0 ; i < w.tiles.length ; i++){
            if(w.tiles[i] != null)
                break;
            if(i+1 == 15)
                return false;
        }
        return true;
    }

    public int tryPlaceWord(Word w){
        return 1;
    }
}
