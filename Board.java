//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.util.ArrayList;

public class Board {
    Tile[][] board;

    static boolean middleCheck ;

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

        if(board[w.getRow()][w.getCol()] == null) {
            return directions(w);
        }
        if(board[w.getRow()][w.getCol()] != null && w.getTiles()[0] == null) {
            return directions(w);
        }
        if(board[w.getRow()][w.getCol()].letter == w.getTiles()[0].letter) {
            return directions(w);
        }
        return false;
    }

    static boolean outsideBoard(Word w) {
        return !(w.getRow() < 0 || w.getRow() >= 15 || w.getCol() < 0 || w.getCol() >= 15);
    }

    static boolean tooLong(Word w) {
        if(w.isVertical())
            if(w.getTiles().length > 15 - (w.getRow()))
                return false;
        if(!w.isVertical())
            return w.getTiles().length <= 15 - (w.getCol());
        return true;
    }

    int firstToStart(Word w) {
        if(board[7][7] == null) {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (w.getTiles()[i] == null)
                    return -1;
            }
            if (w.getCol() == 7 && w.getRow() == 7)
                if (w.getRow() + w.getTiles().length >= 7 && w.getRow() + w.getTiles().length < 15)
                    return 1;


            if (w.getCol() == 7) {
                if (w.isVertical())
                    if (w.getRow() <= 7 && w.getRow() + w.getTiles().length - 1 >= 7 && w.getRow() + w.getTiles().length - 1 < 15)
                        return 1;
                    else return -1;
            }

            if (w.getRow() == 7) {
                if (!w.isVertical())
                    if (w.getCol() <= 7 && w.getCol() + w.getTiles().length - 1 >= 7 && w.getCol() + w.getTiles().length - 1 < 15)
                        return 1;
                    else return -1;
            }
        }

        return 0;
    }

    static boolean legalLetters(Word w){
        for(int i = 0 ; i < w.getTiles().length; i++)
            if(w.getTiles()[i] != null)
                if(w.getTiles()[i].letter < 'A' || w.getTiles()[i].letter > 'Z' || w.getTiles()[i] == null)
                    return false;
        return true;
    }

    static boolean overlappingWords(Word w){
        for(int i = 0 ; i < w.getTiles().length ; i++){
            if(w.getTiles()[i] != null)
                break;
            if(i+1 == 15)
                return false;
        }
        return true;
    }

    public boolean directions(Word w) {
        if (w.isVertical()) {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (board[w.getRow() + i][w.getCol()] == null)
                    continue;
                if (board[w.getRow() + i][w.getCol()] != null && w.getTiles()[i] != null)
                    if (board[w.getRow() + i][w.getCol()].letter != w.getTiles()[i].letter)
                        return false;
            }
        } else {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (board[w.getRow()][w.getCol() + i] == null)
                    continue;
                if (board[w.getRow()][w.getCol() + i] != null && w.getTiles()[i] != null)
                    if (board[w.getRow()][w.getCol() + i].letter != w.getTiles()[i].letter)
                        return false;
            }
        }

        int flag = 0;
        if(w.isVertical()){
            if(w.getCol() -1 > 0)
                for (int i = 0; i < w.getTiles().length; i++)
                    if(board[w.getRow() + i][w.getCol() - 1] != null)
                        flag = 1;

            if(w.getCol() + 1 < 14)
                for (int i = 0; i < w.getTiles().length; i++)
                    if(board[w.getRow() + i][w.getCol() + 1] != null)
                        flag = 1;

            if(w.getRow() - 1 > 0)
                if(board[w.getRow() - 1][w.getCol()] != null)
                    flag = 1;

            if(w.getRow() + w.getTiles().length + 1 < 14)
                if(board[w.getRow() + w.getTiles().length + 1][w.getCol()] != null)
                    flag = 1;
        }
        else {
            if(w.getRow() -1 > 0)
                for (int i = 0; i < w.getTiles().length; i++)
                    if(board[w.getRow() - 1][w.getCol() + i] != null)
                        flag = 1;

            if(w.getRow() + 1 < 14)
                for (int i = 0; i < w.getTiles().length; i++)
                    if(board[w.getRow() + 1][w.getCol() + i] != null)
                        flag = 1;

            if(w.getCol() - 1 > 0)
                if(board[w.getRow()][w.getCol() - 1] != null)
                    flag = 1;

            if(w.getCol() + w.getTiles().length + 1 < 14)
                if(board[w.getRow()][w.getCol() + w.getTiles().length + 1] != null)
                    flag = 1;
        }
        return flag == 1;
    }

    boolean dictionaryLegal(Word w){ return true; }

    ArrayList<Word> getWords(Word w) {

        ArrayList<Word> array = new ArrayList<Word>();
        array.add(w);

        if(board[7][7] == null && isFirstWordIsLegal(w))
            return array;

        int tempRow = w.getRow();
        int tempCol = w.getCol();
        int start =0;
        int end = 0;

        for(int i = 0 ; i < w.getTiles().length; i++){
            if(w.isVertical()){
                if(w.getTiles()[i] == null && board[tempRow][tempCol] != null){
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
                    tempCol = w.getCol();
                }


                if (tempCol > 0) {
                    if (this.board[tempRow][tempCol - 1] != null) {
                        tempCol--;
                        while (board[tempRow][tempCol] != null) {
                            tempCol--;
                            start++;
                        }
                    }
                    tempCol = w.getCol();
                }


                if(start != 0 && end != 0)
                    array.add(createWord(tempRow, w.getCol() - start, start + end + 1, false, w.getTiles()[i]));
                else if (start == 0 && end != 0)
                    array.add(createWord(tempRow, w.getCol(), end + 1, false, w.getTiles()[i]));
                else if (start != 0 )
                    array.add(createWord(tempRow, w.getCol() - start, start + 1, false, w.getTiles()[i]));

                tempCol = w.getCol();
                tempRow++;
            }
            else {
                if(w.getTiles()[i] == null && board[tempRow][tempCol] != null){
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
                    tempRow = w.getRow();
                }


                if (tempRow > 0) {
                    if (this.board[tempRow - 1][tempCol] != null) {
                        tempRow--;
                        while (board[tempRow][tempCol] != null) {
                            tempRow--;
                            start++;
                        }
                    }
                    tempRow = w.getRow();
                }


                if(start != 0 && end != 0)
                    array.add(createWord(w.getRow() - start, tempCol, start + end + 1, true, w.getTiles()[i]));
                else if (start == 0 && end != 0)
                    array.add(createWord(w.getRow(), tempCol, end + 1, true, w.getTiles()[i]));
                else if (start != 0)
                    array.add(createWord(w.getRow() - start, tempCol, start + 1, true, w.getTiles()[i]));

                tempCol++;
                tempRow = w.getRow();
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

            for(int i = 0 ; i < w.getTiles().length ; i++){
                if(tempCol == 7 && tempRow == 7)
                    return true;
                if(w.isVertical()) tempRow++;
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




    private void placeWord(Tile[][] boardTiles, Word w) {
        for (int i = 0; i < w.getTiles().length; i++)
            if (w.getTiles()[i] != null)
                board[w.getRow() + (w.isVertical() ? i : 0)][w.getCol() + (w.isVertical() ? 0 : i)] = w.getTiles()[i];

        if (boardTiles == board)
            numOfTilesPlaced += w.getTiles().length;

    }


    public int tryPlaceWord(Word w) {
        int i = 0;
        if (boardLegal(w) && dictionaryLegal(w)) {
            ArrayList<Word> newArray = getWords(w);

            for (Word word : newArray)
                if (!dictionaryLegal(word))
                    return 0;

            int score = 0;
            if (numOfTilesPlaced == 0) { // First word
                placeWord(board, w);
                score += 2 * getScore(newArray.get(0)); // Double score for first word
                i++;
            } else
                placeWord(board, w);

            for (; i < newArray.size() ; i++)
                score += getScore(newArray.get(i));

            return score;
        }
        else return 0;
    }


    public int getScore(Word w) {
        int score = 0;
        int wordMultiplier = 1;

        for (int i = 0; i < w.getTiles().length; i++) {
            Tile tile = w.getTiles()[i] != null ? w.getTiles()[i] : board[w.getRow() + (w.isVertical() ? i : 0)][w.getCol() + (w.isVertical() ? 0 : i)];
            int tileScore = tile.score;

            score += applyBonus(boardScore[w.getRow() + (w.isVertical() ? i : 0)][w.getCol() + (w.isVertical() ? 0 : i)], tileScore);
            wordMultiplier *= getWordMultiplier(boardScore[w.getRow() + (w.isVertical() ? i : 0)][w.getCol() + (w.isVertical() ? 0 : i)]);
        }

        return score * wordMultiplier;
    }

    private int applyBonus(int bonus, int tileScore) {
        switch (bonus) {
            case noBonus:
            case centerPoint:
                return tileScore;
            case doubleLetterBonus:
                return tileScore * 2;
            case tripleLetterBonus:
                return tileScore * 3;
            default:
                return tileScore;
        }
    }

    private int getWordMultiplier(int bonus) {
        switch (bonus) {
            case doubleWordBonus:
                return 2;
            case tripleWordBonus:
                return 3;
            default:
                return 1;
        }
    }
}
