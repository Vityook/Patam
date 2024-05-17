//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.io.*;

public class IOSearcher {

    public static boolean search(String word, String... fileNames)  {
        for(int i = 0 ; i < fileNames.length ; i++){
            FileReader fr = null;
            BufferedReader br = null;
            String s;
            try {
                fr = new FileReader(fileNames[i]);
                br = new BufferedReader(fr);
                while ((s=br.readLine()) != null){
                    if(s.contains(word))
                        return true;
                }
            } catch (FileNotFoundException e) {
                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                    }
                }
                if (fr != null) {
                    try {
                        fr.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return false;
    }
}
