package collection.map;

import org.junit.Test;

public class MyHashMapTest {

    @Test
    public void map(){
        MyHashMap myHashMap = new MyHashMap();
        for (int i=0;i<20;i++) {
            boolean put = myHashMap.put(i, String.valueOf(i) + "+" + String.valueOf(i));
            System.out.println(put);
        }
        for (int i=0;i<20;i++) {
            boolean put = myHashMap.put(i, String.valueOf(i) + "+" + String.valueOf(i));
            System.out.println(put);
        }
        System.out.println("size:"+myHashMap.getSize());
        for (int i=0;i<20;i++) {
            System.out.println(myHashMap.get(i));
        }
    }
}
