package collection.map;

public class MyHashTable<k,v> {

    //用于真实存储数据的节点
    private transient Node<?,?>[] tables;
    //元素个数
    private transient int size;
    //操作次数
    private transient int modCount=0;
    //转载因子
    private float load;
    //即用于决定是否扩容的边界值，可以是load*capacity，也可以给定
    int capacity_flag;
    //数组容量最大值
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    static class Node<k,v> {
        private int hash;
        private k key;
        private v value;
        private Node<k, v> next;

        public Node(int hash, k key, v value, Node<k, v> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    //定义初始容器大小，以及转载因子（用于判断容器溶度以扩大数组容量）
    public MyHashTable(int initialCapacity,float load){
        if(initialCapacity <0){
            throw new IllegalArgumentException("capacity is :" + initialCapacity);
        }
        if(load < 0 || Float.isNaN(load)){
            throw new IllegalArgumentException("load id :" + load);
        }
        if(initialCapacity == 0 ){
            initialCapacity=1;
        }
        this.load=load;
        tables = new Node<?,?>[initialCapacity];
        //两边取小
        capacity_flag= (int) Math.min(initialCapacity*load,MAX_ARRAY_SIZE+1);
    }

}
