//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class LRU implements CacheReplacementPolicy {
    LinkedList<String> list;


    public LRU() {
        this.list = new LinkedList<String>();
    }

    @Override
    public void add(String word) {
        if (list.contains(word)) {
            list.remove(word); // Remove to update access order
            list.add(word);
        }
        else list.add(word);
    }

    @Override
    public String remove() {
        return list.removeFirst();
    }
}
