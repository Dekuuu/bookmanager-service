package com.biyesheji.bookmanagerservice.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LIstToMap {
    /**
    * Description: 将List<Map>转为一个Map</Map><br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/18 21:38<br/>
    * @param:<br/>
    * @return:
    */

    public static HashMap<String,String>  listToMap(List<Map<String,String>> list){
        HashMap<String,String> map=new HashMap<>();
        list.stream().forEach(l ->{
            map.put(l.get("CATEGORYNO"),l.get("CATEGORYNAME"));
        });
        return map;
    }

    public static HashMap  listToMapInteger(List<Map<String,Integer>> list){
        HashMap<Object,Object> map=new HashMap<>();
        list.stream().forEach(l ->{
            map.put(l.get("KEY"),l.get("VALUE"));
        });
        return map;
    }
}
