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

    
}
