package test;

import java.util.ArrayList;

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
        if(!check) return false;

        if(board[w.row][w.col] == null) {
            return directions(w);
        }
        if(board[w.row][w.col] != null && w.tiles[0] == null) {
            return directions(w);
        }
        if(board[w.row][w.col].letter == w.tiles[0].letter) {
            return directions(w);
        }
        return false;
    }

    static boolean outsideBoard(Word w) {
        return !(w.getRow() < 0 || w.getRow() >= 15 || w.getCol() < 0 || w.getCol() >= 15);
    }

    static boolean tooLong(Word w) {
        if(w.vertical)
            if(w.tiles.length > 15 - (w.row))
                return false;
        if(!w.vertical)
            if(w.tiles.length > 15 - (w.col))
                return false;
        return true;
    }

    int firstToStart(Word w) {
        if(board[7][7] == null) {
            for (int i = 0; i < w.tiles.length; i++) {
                if (w.tiles[i] == null)
                    return -1;
            }
            if (w.col == 7 && w.row == 7)
                if (w.row + w.tiles.length >= 7 && w.row + w.tiles.length < 15)
                    return 1;


            if (w.col == 7) {
                if (w.vertical)
                    if (w.row <= 7 && w.row + w.tiles.length - 1 >= 7 && w.row + w.tiles.length - 1 < 15)
                        return 1;
                    else return -1;
            }

            if (w.row == 7) {
                if (!w.vertical)
                    if (w.col <= 7 && w.col + w.tiles.length - 1 >= 7 && w.col + w.tiles.length - 1 < 15)
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

    public boolean directions(Word w) {
        if (w.vertical) {
            for (int i = 0; i < w.tiles.length; i++) {
                if (board[w.row + i][w.col] == null)
                    continue;
                if (board[w.row + i][w.col] != null && w.tiles[i] != null)
                    if (board[w.row + i][w.col].letter != w.tiles[i].letter)
                        return false;
            }
        } else {
            for (int i = 0; i < w.tiles.length; i++) {
                if (board[w.row][w.col + i] == null)
                    continue;
                if (board[w.row][w.col + i] != null && w.tiles[i] != null)
                    if (board[w.row][w.col + i].letter != w.tiles[i].letter)
                        return false;
            }
        }

        int flag = 0;
        if(w.vertical){
            if(w.col -1 > 0)
                for (int i = 0; i < w.tiles.length; i++)
                    if(board[w.row + i][w.col - 1] != null)
                        flag = 1;

            if(w.col + 1 < 14)
                for (int i = 0; i < w.tiles.length; i++)
                    if(board[w.row + i][w.col + 1] != null)
                        flag = 1;

            if(w.row - 1 > 0)
                if(board[w.row - 1][w.col] != null)
                    flag = 1;

            if(w.row + w.tiles.length + 1 < 14)
                if(board[w.row + w.tiles.length + 1][w.col] != null)
                    flag = 1;
        }
        else {
            if(w.row -1 > 0)
                for (int i = 0; i < w.tiles.length; i++)
                    if(board[w.row - 1][w.col + i] != null)
                        flag = 1;

            if(w.row + 1 < 14)
                for (int i = 0; i < w.tiles.length; i++)
                    if(board[w.row + 1][w.col + i] != null)
                        flag = 1;

            if(w.col - 1 > 0)
                if(board[w.row][w.col - 1] != null)
                    flag = 1;

            if(w.col + w.tiles.length + 1 < 14)
                if(board[w.row][w.col + w.tiles.length + 1] != null)
                    flag = 1;
        }
        if(flag == 1) return true;
        else return false;
    }

    boolean dictionaryLegal(Word w){ return true; }

    public ArrayList<Word> getWords(Word w){
        boolean check = false;

        ArrayList<Word> array = new ArrayList();
        array.set(0, w);
        Tile[] tiles = new Tile[w.tiles.length];
        if(!w.vertical){
            for(int i = 0 ; i < w.tiles.length ; i++){
                if(board[w.row - 1][w.col] != null){
                    int j = 0;
                    while(board[w.row - j][w.col] != null){
                        j++;
                    }
                    int k = 0;
                    assert tiles != null;
                    tiles[k].score = board[w.row - j][w.col].score;
                }

            }
        }
        return array;
    }

    public int tryPlaceWord(Word w){
        return 1;
    }
}
