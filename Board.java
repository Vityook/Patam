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

        ArrayList<Word> array = new ArrayList<Word>();
        array.add(w);

        if(board[7][7] == null && isFirstWordIsLegal(w))
            return array;

        int tempRow = w.row;
        int tempCol = w.col;
        int start =0;
        int end = 0;

        for(int i = 0 ; i < w.tiles.length; i++){
            if(w.vertical){
                if(w.tiles[i] == null && board[tempRow][tempCol] != null){
                    tempRow++;
                    continue;
                }

                if (tempCol < 14) {
                    if (this.board[tempRow][tempCol + 1] != null) {
                        tempCol++;
                        while (board[tempRow][tempCol] != null) {
                            tempCol++;
                            end++;
                        }
                    }
                    tempCol = w.col;
                }


                if (tempCol > 0) {
                    if (this.board[tempRow][tempCol - 1] != null) {
                        tempCol--;
                        while (board[tempRow][tempCol] != null) {
                            tempCol--;
                            start++;
                        }
                    }
                    tempCol = w.col;
                }


                if(start != 0 && end != 0)
                    array.add(createWord(tempRow, w.col - start, start + end + 1, false, w.getTiles()[i]));
                else if (start == 0 && end != 0)
                    array.add(createWord(tempRow, w.col, end + 1, false, w.getTiles()[i]));
                else if (start != 0 )
                    array.add(createWord(tempRow, w.col - start, start + 1, false, w.getTiles()[i]));

                tempCol = w.col;
                tempRow++;
            }
            else {
                if(w.tiles[i] == null && board[tempRow][tempCol] != null){
                    tempCol++;
                    continue;
                }
                if (tempRow < 14) {
                    if (this.board[tempRow + 1][tempCol] != null) {
                        tempRow++;
                        while (board[tempRow][tempCol] != null) {
                            tempRow++;
                            end++;
                        }
                    }
                    tempRow = w.row;
                }


                if (tempRow > 0) {
                    if (this.board[tempRow - 1][tempCol] != null) {
                        tempRow--;
                        while (board[tempRow][tempCol] != null) {
                            tempRow--;
                            start++;
                        }
                    }
                    tempRow = w.row;
                }


                if(start != 0 && end != 0)
                    array.add(createWord(w.row - start, tempCol, start + end + 1, true, w.getTiles()[i]));
                else if (start == 0 && end != 0)
                    array.add(createWord(w.row, tempCol, end + 1, true, w.getTiles()[i]));
                else if (start != 0)
                    array.add(createWord(w.row - start, tempCol, start + 1, true, w.getTiles()[i]));

                tempCol++;
                tempRow = w.row;
            }
            start = 0;
            end  = 0;
        }

        return array;
    }

    public boolean isFirstWordIsLegal(Word w) {
        if(board[7][7] == null){
            int tempRow = w.getRow();
            int tempCol = w.getCol();

            for(int i = 0 ; i < w.tiles.length ; i++){
                if(tempCol == 7 && tempRow == 7)
                    return true;
                if(w.vertical) tempRow++;
                else tempCol++;
            }
            return false;
        }
        return true;
    }

    public Word createWord(int row, int col, int length, boolean vertical, Tile t) {
        int tempRow = row;
        int TempCol = col;
        Tile[] temp = new Tile[length];
        for(int i = 0 ; i < length ; i++){
            if(board[tempRow][TempCol] == null)
                temp[i] = t;
            else temp[i] = board[tempRow][TempCol];
            if(vertical) tempRow++;
            else TempCol++;
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
        return 1;
}
