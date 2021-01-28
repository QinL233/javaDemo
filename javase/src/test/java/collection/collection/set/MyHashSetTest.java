package collection.collection.set;

import org.junit.Test;

public class MyHashSetTest {

    @Test
    public void set(){
        MyHashSet<Object> myHashSet = new MyHashSet<>();
        for (int i=0;i<20;i++) {
            boolean put = myHashSet.add(i);
            System.out.println(put);
        }
        for (int i=0;i<20;i++) {
            boolean put = myHashSet.add(i);
            System.out.println(put);
        }
        System.out.println("size:"+myHashSet.size());

    }
}
