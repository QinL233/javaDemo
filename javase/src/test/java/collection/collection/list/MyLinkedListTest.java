package collection.collection.list;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MyLinkedListTest {

    @Test
    public void link() {
        int size = 10;
        List list = new ArrayList();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        MyLinkedList linkedList = new MyLinkedList(list);
        linkedList.add(10);
        linkedList.add(0, 11);
        linkedList.remove(1);
        System.out.println(linkedList.indexOf(5));
    }
}
