//#####################################################################
//#   Name: Victor Poliakov                                           #
//#   ID: 206707259                                                   #
//#####################################################################

package test;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
    private final Map<String , Dictionary> dMap;
    private static DictionaryManager singleDm = null;

    DictionaryManager(){
        dMap = new HashMap<>();
    }


    public boolean query(String ... args){
        boolean flag = false;
        for(int i=0; i<args.length-1; i++){
            if(!dMap.containsKey(args[i]))
                dMap.put(args[i], new Dictionary(args[i]));
            if(dMap.get(args[i]).query(args[args.length - 1]))
                flag = true;
            dMap.get(args[i]).query(args[i]);
        }
        return flag;
    }


    public boolean challenge(String ... args){
        boolean flag = false;
        for(int i=0; i<args.length-1; i++){
            if(!dMap.containsKey(args[i]))
                dMap.put(args[i], new Dictionary(args[i]));
            if(dMap.get(args[i]).challenge(args[args.length - 1]))
                flag = true;
            dMap.get(args[i]).challenge(args[i]);
        }
        return flag;
    }


    public int getSize() {
        return dMap.size();
    }


    public static DictionaryManager get(){
        if(singleDm == null){
            singleDm = new DictionaryManager();
        }
        return singleDm;
    }

}
