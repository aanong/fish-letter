package com.aihaibara.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Demo {


    static Map<String,Object> cacheMap = new HashMap<>();
    static ReentrantReadWriteLock  rrw = new ReentrantReadWriteLock();
    static Lock read = rrw.readLock();
    static Lock write = rrw.writeLock();
    public static Object get(String key){
        read.lock();
        try {
            return cacheMap.get(key);
        } finally {
            read.unlock();
        }
    }
    public static  void set(String key,Object o){
        write.lock();
        try {
            cacheMap.put(key,o);
        } finally {
            write.unlock();
        }
    }
}
