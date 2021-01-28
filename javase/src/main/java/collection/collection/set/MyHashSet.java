package collection.collection.set;

import collection.map.MyHashMap;

import java.util.Collection;

/**
 * 使用hashMap中key不可能重复的特性实现
 */
public class MyHashSet<k> {
    //使用map
    private transient MyHashMap map;
    //value永远为null
    private static final Object value =new Object();

    //构造初始化一个map
    public MyHashSet(){
        map= new MyHashMap();
    }

    public MyHashSet(int capacity){
        map = new MyHashMap(capacity);
    }

    public MyHashSet(int capacity,float load){
        map = new MyHashMap(capacity,load);
    }
    //复制collection到新集合
    public MyHashSet(Collection collection){

    }

    public boolean add(k key){
        //true则表示key完全相同直接覆盖，false表示添加了一个新元素，而value全都是null，因此会得到一个不重复的map“数组”
        return !map.put(key, value);
    }

    public boolean remove(k key){
        return  map.remove(key);
    }

    public int size(){
        return map.getSize();
    }

    public boolean isEmpty(){
        return map.getSize()==0;
    }

}
