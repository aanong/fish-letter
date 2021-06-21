package com.aihaibara.leedcode;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Array;
import java.util.*;

public class GroupAnagrams {
    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs==null){
            return null;
        }

        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {

            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String s = String.valueOf(chars);
            if(map.containsKey(s)){
                List<String> strings = map.get(s);
                strings.add(str);
            }else {
                List<String> strings = new ArrayList<>();
                strings.add(str);
                map.put(s,strings);
            }

        }

        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        String[] sd = {"eat","tea","tan","ate","nat","bat"};
        List<List<String>> lists = groupAnagrams(sd);

        System.out.println(JSON.toJSON(lists));

    }
}
