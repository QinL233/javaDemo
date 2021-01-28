package collection.collection.list;

import org.junit.Test;

public class MyArrayListTest {

    @Test
    public void array() {
        int size=10;
        int len=5;
        MyArrayList arrayList=new MyArrayList(len);
        for(int i=0;i<size;i++){
            arrayList.add(i);
        }
        for(int i=0;i<size;i++){
            System.out.print(arrayList.get(i)+"\t");
        }
    }
}
