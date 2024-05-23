//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.PrintWriter;


public class BookScrabbleHandler implements ClientHandler {
    Scanner scanner;
    PrintWriter out;
    String line;
    List<String> words;

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        Scanner scanner = new Scanner(inFromClient);
        PrintWriter out = new PrintWriter(outToClient);
        try {
            if (scanner.hasNextLine()) {
                line = scanner.nextLine();
                words = splitLine(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DictionaryManager dictionaryManager = new DictionaryManager();

        String[] words2 = new String[words.toArray().length - 1];
        words.subList(1, words.size()).toArray(words2);

        boolean flag;
        if (words.get(0).equals("C")) {
            flag = dictionaryManager.challenge(words2);
        } else {
            flag = dictionaryManager.query(words2);
        }

        if (flag) {
            out.println("true");
            out.flush();
        } else {
            out.println("false");
            out.flush();
        }
        out.close();
    }

    @Override
    public void close() {
        scanner.close();
        out.close();
    }

    private List<String> splitLine(String line) {
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("([^,]+|\".*?\")");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            words.add(matcher.group(1).replace("\"", ""));  // Remove quotes if present
        }
        return words;
    }
}
