//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.util.HashSet;

public class CacheManager {
    private int capacity;
    private CacheReplacementPolicy crp;
    HashSet<String> set;

    public CacheManager(int capacity, CacheReplacementPolicy crp){
        this.capacity = capacity;
        this.crp = crp;
        set = new HashSet<String>();
    }


    public boolean query(String word){
        if(set.contains(word) )
            return true;
        return false;

    }


    public void add(String a) {
        if(set.size() >= capacity) {
            set.remove(crp.remove());
            set.add(a);
        } else {
            crp.add(a);
            set.add(a);
        }
    }
}
