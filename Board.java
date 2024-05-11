package test;

import java.util.ArrayList;

public class Board {
    Tile[][] board;

    static boolean middleCheck ;
    int [] letterScore = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10}; //ascii start at 65=A -> 90=Z

    final int noBonus = 0, doubleLetterBonus = 1, tripleLetterBonus = 2, doubleWordBonus = 3, tripleWordBonus = 4,
    centerPoint = 5;

    public static char [][] boardScore =
            {
                    {4, 0, 0, 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4},
                    {0, 3, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 3, 0},
                    {0, 0, 3, 0, 0, 0, 1, 0, 1, 0, 0, 0, 3, 0, 0},
                    {1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 1},
                    {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0},
                    {0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0},
                    {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0},
                    {4, 0, 0, 1, 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 4},
                    {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0},
                    {0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0},
                    {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0},
                    {1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 1},
                    {0, 0, 3, 0, 0, 0, 1, 0, 1, 0, 0, 0, 3, 0, 0},
                    {0, 3, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 3, 0},
                    {4, 0, 0, 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4},
            };
    private Board() {
        board = new Tile[15][15];
        middleCheck = false;
    }
    static int numOfTilesPlaced = 0;

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
            if(w.getTiles()[i] != null)
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

    ArrayList<Word> getWords(Word w) {

        ArrayList<Word> al = new ArrayList<Word>();
        al.add(w);//add the first new word to the list.

        if (this.board[7][7] == null && isFirstWordIsLegal(w)) {
            return al; //if the first word.
        }

        int row   = w.getRow();
        int col   = w.getCol();
        int start = 0;
        int end   = 0;

        for (int i = 0; i < w.getTiles().length; i++) {

            if (w.isVertical()) {
                if (w.getTiles()[i] == null && this.board[row][col] != null) {
                    row++;
                    continue;
                }
                if (col != 14) {
                    if (this.board[row][col + 1] != null) {
                        col++;
                        while (this.board[row][col] != null) {
                            end++;
                            col++;
                        }
                    }
                    col = w.getCol();
                }
                if (col != 0) {
                    if (this.board[row][col - 1] != null) {
                        col--;
                        while (this.board[row][col] != null) {
                            start++;
                            col--;
                        }
                    }
                    col = w.getCol();
                }
                if (start != 0 && end != 0) {
                    al.add(makeWord(row, w.getCol() - start, start + end + 1, false, w.getTiles()[i]));
                }
                if (start == 0 && end != 0) {
                    al.add(makeWord(row, w.getCol(), end + 1, false, w.getTiles()[i]));
                }
                if (start != 0 && end == 0) {
                    al.add(makeWord(row, w.getCol() - start, start + 1, false, w.getTiles()[i]));
                }
                row++;
                col = w.getCol();

            } else {//not vertical
                if (w.getTiles()[i] == null && this.board[row][col] != null) {
                    col++;
                    continue;
                }

                if (row != 14) {
                    if (this.board[row + 1][col] != null) {
                        row++;
                        while (this.board[row][col] != null) {
                            row++;
                            end++;
                        }
                    }
                    row = w.getRow();
                }
                if (col != 0) {
                    if (this.board[row - 1][col] != null) {
                        row--;
                        while (this.board[row][col] != null) {
                            row--;
                            start++;
                        }
                    }
                    row = w.getRow();
                }
                if (start != 0 && end != 0) {
                    al.add(makeWord(w.getRow() - start, col, start + end + 1, true, w.getTiles()[i]));
                }
                if (start == 0 && end != 0) {
                    al.add(makeWord(w.getRow(), col, end + 1, true, w.getTiles()[i]));
                }
                if (start != 0 && end == 0) {
                    al.add(makeWord(w.getRow() - start, col, start + 1, true, w.getTiles()[i]));
                }
                col++;
                row = w.getRow();
            }
            start = 0;
            end   = 0;
        }

        return al;
    }

    public boolean isFirstWordIsLegal(Word w) {
        if (this.board[7][7] == null)//the star is empty and check if the word has the 7,7 index.
        {
            int row = w.getRow();
            int col = w.getCol();
            for (int i = 0; i < w.getTiles().length; i++) {
                if (col == 7 && row == 7) {
                    return true;
                }
                if (w.isVertical())//אנכית
                {
                    row++;
                } else //אופקית
                {
                    col++;
                }
            }
            return false; //the star is empty and the word doesn't have the 7,7 index.
        }
        return true; //the star isn't empty.
    }

    public Word makeWord(int row, int col, int length, boolean vertical, Tile t) {
        int    r    = row;
        int    c    = col;
        Tile[] temp = new Tile[length];
        for (int i = 0; i < length; i++) {
            if (this.board[r][c] == null) {
                temp[i] = t;
            } else {
                temp[i] = this.board[r][c];
            }
            if (vertical) {
                r++;
            } else {
                c++;
            }
        }
        return new Word(temp, row, col, vertical);
    }


    /**
     * helping method for tryPlaceWord, this method places a word on a given board
     */
    private void placeWord(Tile[][] boardTiles, Word w) {
        for (int row = w.getRow(), col = w.getCol(), count = 0; count < w.getTiles().length; count++) {
            if (w.getTiles()[count] != null) {
                boardTiles[row][col] = w.getTiles()[count];
            }

            if (w.isVertical()) {
                row++;
            } else {
                col++;
            }
        }

        if (boardTiles == board) //same address -> word is placed on board
        {
            numOfTilesPlaced += w.getTiles().length;
        }
    }

    /**
     * places a word on the board if it follows all the rules
     */
    int tryPlaceWord(Word w) {
        int score = 0, i = 0;
        if (boardLegal(w) && dictionaryLegal(w)) {
            ArrayList<Word> newWords = getWords(w); //check all new words
            for (Word word : newWords) {
                if (!dictionaryLegal(word)) {
                    return 0;
                }
            }
            if (numOfTilesPlaced == 0) //first word
            {
                placeWord(this.board, w);  //place word permanently
                score += 2 * getScore(newWords.get(0));
                i++;
            } else {
                placeWord(this.board, w);  //place word permanently
            }
            for (; i < newWords.size(); i++) //calc score for all the new words created
            {
                score += getScore(newWords.get(i));
            }
            return score;
        } else {
            return 0;
        }
    }

    /**
     * returns the score of a given word
     */
    int getScore(Word w) {
        int score     = 0;
        int wordMult  = 1;
        int tileScore = 0;
        for (int i = w.getRow(), j = w.getCol(), count = 0; count < w.getTiles().length; count++) {
            if (w.getTiles()[count] == null) {
                tileScore = this.board[i][j] != null ? this.board[i][j].score : 0;
            } else {
                tileScore = w.getTiles()[count].score;
            }
            switch (boardScore[i][j]) {
                case noBonus:
                case centerPoint:
                    score += tileScore;
                    break;
                case doubleLetterBonus:
                    score += 2 * tileScore;
                    break;
                case tripleLetterBonus:
                    score += 3 * tileScore;
                    break;
                case doubleWordBonus:
                    score += tileScore;
                    wordMult *= 2;
                    break;
                case tripleWordBonus:
                    score += tileScore;
                    wordMult *= 3;
                    break;
                default:
                    break;
            }

            if (w.isVertical()) {
                i++;
            } else {
                j++;
            }
        }

        return score * wordMult;
    }
}
