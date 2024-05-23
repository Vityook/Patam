//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.io.*;

public class IOSearcher {

    public static boolean search(String word, String... fileNames)  {
        for (String fileName : fileNames) {
            String s;
            try (FileReader fr = new FileReader(fileName); BufferedReader br = new BufferedReader(fr)) {
                while ((s = br.readLine()) != null) {
                    if (s.contains(word))
                        return true;
                }
            } catch (FileNotFoundException e) {
                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
