package org.apache.hadoop.mapreduce;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class keyStatistic {
    //key counters
    private static Map<String, AtomicInteger> keyMap = new ConcurrentHashMap<String , AtomicInteger>();
    //define function to return key hash map
    public static Map<String,AtomicInteger> getKeyMap (){
        return keyMap;
    }

    public  static void keyMapUpdate(String key){
        //key counters: 11/28
//        if (keyMap.containsKey(key)){
//            keyMap.put(key,(int)keyMap.get(key)+1);
//            System.out.println("key: " + key + " key value added:" + keyMap.get(key));
//        } else {
//            keyMap.put(key, 1);
//            System.out.println("key: " + key + " key added:" + keyMap.get(key));
//        }
        if( keyMap.putIfAbsent(key,new AtomicInteger(1)) != null ) {
            keyMap.get(key).addAndGet(1);
        }

    }

    public static void outputKeyMap(){
        System.out.println("key, value: " + java.util.Arrays.asList(keyMap));
    }
    public static int get(String key){return keyMap.get(key).intValue();}
}
