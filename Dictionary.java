//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Dictionary  {
    CacheReplacementPolicy LRU;
    CacheReplacementPolicy LFU;
    BloomFilter bf;
    CacheManager cacheExist;
    CacheManager cacheNotExist;
    String[] copyFileNames;

    public Dictionary(String... fileNames){
        cacheExist = new CacheManager(400, new LRU());
        cacheNotExist  = new CacheManager(100, new LFU());
        bf = new BloomFilter(256, "MD5", "SHA1");
        copyFileNames = fileNames.clone();
        for(int i = 0 ; i < fileNames.length ; i++){
            try {
                Scanner scan = new Scanner(new File(Paths.get(fileNames[i]).toUri()));
                while(scan.hasNext())
                    bf.add(scan.next());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean query(String word) {
        if(cacheExist.query(word))
            return true;
        if(cacheNotExist.query(word))
            return false;
        if(bf.contains(word)){
            cacheExist.add(word);
            return true;
        }
        else{
            cacheNotExist.add(word);
            return false;
        }
    }

    public boolean challenge(String word) {
        IOSearcher io = new IOSearcher();
        if (IOSearcher.search(word, copyFileNames)) {
            cacheExist.add(word);
            return true;
        } else {
            cacheNotExist.add(word);
            return false;
        }
    }

}
