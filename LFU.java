//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.util.*;

public class LFU implements CacheReplacementPolicy {
    private LinkedHashMap<String, Integer> map;

    public LFU(){
        map = new LinkedHashMap<String , Integer>();
    }

    @Override
    public void add(String word) {
        int count = 0 ;
        if(map.containsKey(word)){
            count = map.get(word);
            map.replace(word, count+1);
        }
        else
            map.put(word, 1);
    }

    @Override
    public String remove() {
        int index = 0;
        String removeString = null;
        int lowest = Integer.MAX_VALUE;
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        Iterator<Map.Entry<String,Integer>> it = entrySet.iterator();

        for(Map.Entry m : map.entrySet()){
            if((int)m.getValue() < lowest)
                lowest = (int)m.getValue();
        }
        while(it.hasNext()){
            Map.Entry<String,Integer> anotherit = it.next();
            if(anotherit.getValue() == lowest)
                removeString = anotherit.getKey();
        }
        return removeString;
    }
}
