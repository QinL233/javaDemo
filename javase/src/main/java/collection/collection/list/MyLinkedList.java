package collection.collection.list;

import java.util.Collection;

/**
 * 使用双链表实现，查询慢O(n)，增删快O(1)，有序允许重复
 * 1.linked记录着头节点和尾节点
 * 2.当元素个数为0时，头尾为null
 * 3.当元素个数为1时，头尾都指向该元素
 * 4.有元素时，【其头元素的prev永远指向null，其尾元素的next永远指向null】
 */
public class MyLinkedList {
    //节点
    private static class Node{
        private Node prev;
        private Object data;
        private Node next;

        public Node(Node prev, Object data,Node next ) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
    //记录节点个数，即元素个数
    private int size;
    //记录增删次数
    private int modCount;
    //记录头节点【必须使用transient防止序列化循环】
    transient Node head;
    //记录尾节点
    transient Node last;


    //构造方法
    public MyLinkedList() {
    }

    //通过集合构造方法
    public MyLinkedList(Collection collection){
        //将collection链到null
        linkAll(0,collection);
    }

    //添加元素，默认是添加在末尾
    public boolean add(Object object){
        linkLast(object);
        return true;
    }

    //从尾添加元素
    public boolean addLast(Object object){
        linkLast(object);
        return true;
    }

    //从头添加元素
    public boolean addHead(Object object){
        linkHead(object);
        return true;
    }

    //在指定的位置插入
    public boolean add(int index,Object object){
        //判断index合法
        checkIndex(index);
        if(index==size){
            linkLast(object);
        }else {
            linkBefore(getNode(index),object);
        }
        return true;
    }

    //将collection链到index之前
    public boolean addAll(int index,Collection collection){
        linkAll(index,collection);
        return true;
    }

    //根据索引删除，index使用遍历查找node，因此效率很低
    public boolean remove(int index){
        checkIndex(index);
        unlink(getNode(index));
        return true;
    }
    //默认删除末尾项，时间为o(1),最快
    public boolean remove(){
        unlinkLast();
        return true;
    }
    public boolean removeLast(){
        unlinkLast();
        return true;
    }
    public boolean removeHead(){
        unlinkHead();
        return true;
    }
    public Object get(int index){
        checkIndex(index);
        return getNode(index).data;
    }
    public Object getHead(){
        return head.data;
    }
    public Object getLast(){
        return last.data;
    }
    //根据数据找到索引位置
    public int indexOf(Object object){
        //记录索引位置，从0开始算
        int index = 0;
        if(object==null) {
            //使用for循环加链指针遍历双链表
            for (Node node=head;node!=null;node=node.next) {
                if (node.data==null) {
                    return index;
                }
                index++;
            }
        }else {
            for (Node node=head;node!=null;node=node.next) {
                if (object.equals(node.data)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }
    //根据数据找到索引位置(从后)
    public int lastIndexOf(Object object){
        //记录索引位置(从size-1开始算开始)
        int index = size-1;
        if(object==null) {
            //使用for循环加链指针遍历双链表
            for (Node node=last;node!=null;node=node.prev) {
                if (node.data==null) {
                    return index;
                }
                index--;
            }
        }else {
            for (Node node=last;node!=null;node=node.prev) {
                if (object.equals(node.data)) {
                    return index;
                }
                index--;
            }
        }
        return -1;
    }

    //判断索引合法
    private void checkIndex(int index){
        //因为size位置是当前用于存储的位置，一定为空【真正的数组大小是size-1】
        if(index<0 || index > size){
            throw new IllegalArgumentException("error size"+size);
        }
    }
    //根据index获取node
    private Node getNode(int index){
        Node rs;
        //使用二分法加快遍历
        if(index < (index>>1)){
            //从头开始遍历直到index
            rs= head;
            for(int i=0;i<index;i++){
                //循环将node的下指标指向
                rs=rs.next;
            }
        }else {
            //从尾巴开始遍历
            rs=last;
            for (int i=size-1;i>index;i--){
                rs=rs.prev;
            }
        }
        return rs;
    }

    //链尾
    private void linkLast(Object object){
        /*
            1.创建一个[list]新节点
            2.[node]新节点指向旧的[list]尾节点
            3.[list]尾节点指向[node]新节点
            4.[node]旧的尾节点也指向[node]新节点
            5.元素个数,操作次数++
            eg:[]为集合所指
                1.[head]=null,[last]=null
                2.[head]=[new node][last]=[new node]
                3.[head]=[new node],new_node,[last]=[new2 node]
                4.3.[head]=[new node],new_node,new_node2,[last]=[new3 node]
         */
        //旧的last
        Node old_last = last;
        //新的节点指向旧的last节点【如果last为null，则该节点的prev指向null】
        Node new_node = new Node(old_last, object, null);
        //新的last将指向new_node
        last =new_node;
        if(old_last==null){
            //如果旧的last为空，证明是第一个元素则head也得指向该节点
            head=new_node;
        }else {
            //如果不为空，则旧的节点的next为新节点
            old_last.next=new_node;
        }
        size++;
        modCount++;
    }

    //链头【与链尾相反】
    private void linkHead(Object object){
        Node old_head=head;
        Node new_node = new Node(null, object, old_head);
        head=new_node;
        if(old_head==null){
            last=new_node;
        }else {
            old_head.prev=new_node;
        }
        size++;
        modCount++;
    }

    //链到指定的node之前
    private void linkBefore(Node node,Object object){
        //获取原node的prev
        Node pre_node = node.prev;
        //新的node前驱指向原来的prev，后继指向该node
        Node new_node = new Node(pre_node, object, node);
        //原来的prev指向新的node
        node.prev=new_node;
        //如果原来的前驱为空【证明他是第一个节点】
        if(pre_node==null){
            //则该新节点为首节点
            head=new_node;
        }else {
            //旧的前驱指向新的节点
            pre_node.next=new_node;
        }
        size++;
        modCount++;
    }
    //将collection链到Node[index]之前
    private void linkAll(int index,Collection collection){
        //检测index
        checkIndex(index);
        //检测collection
        Object[] objects = collection.toArray();
        if(objects.length ==0){
            throw new IllegalArgumentException("collection is empty");
        }
        //copy集合操作----------
        //prev是新节点的前驱，next是新节点的后继，即链到next之前
        Node old_prev;
        Node old_next;
        //判断index是否边界以提高计算性能
        if(index==size){
            //证明index是末尾
            old_prev=last;
            old_next=null;
        }else {
            old_next = getNode(index);
            old_prev = old_next.prev;
        }
        for (Object object:objects){
            //新节点链到old_node上
            Node new_node = new Node(old_prev, object, null);
            if(old_prev==null){
                //证明index位置的node是头节点，该新node在前边，因此新node是新的head
                head=new_node;
            }else {
                //否则old_node也要指向new_node
                old_prev.next=new_node;
            }
            //这样new_node就和old_node双向链起来的，新node变成了（旧的prev节点）
            old_prev=new_node;
        }
        //最终collection以及链到了原来next的前边，但是还没有与next链起来，其old_prev就是old_next的前驱
        if(old_next==null){
            //如果node[index]就是null，证明是接入末尾，其last就是prev
            last=old_prev;
        }else{
            //否则将末尾两边链起来
            old_prev.next=old_next;
            old_prev.prev=old_prev;
        }
        //集合数量+长度
        size+=objects.length;
        //操作符+1
        modCount++;
    }

    //删除指定节点
    private Object unlink(Node node){
        Node old_prev = node.prev;
        Object old_data = node.data;
        Node old_next = node.next;
        //判断是否为头尾节点
        if(old_prev==null){
            //如果该节点的前驱为null，证明该节点是head，则头指针指向next【后移一位】
            head=old_next;
        }else {
            //删除该节点的前驱
            node.prev=null;
            //将该节点的前驱指向后继
            old_prev.next=old_next;
        }
        if(old_next==null){
            last=old_prev;
        }else {
            node.next=null;
            old_next.prev=old_prev;
        }
        //删除该节点数据
        node.data=null;
        //元素个数--
        size--;
        //操作次数++
        modCount++;
        return old_data;
    }

    //删除第一个节点
    private Object unlinkHead(){
        Object old_data = head.data;
        //获取下个node
        Node old_next = head.next;
        //清空旧的head释放内存
        head.data=null;
        head.next=null;
        //将head指向下一个
        head=old_next;
        if(old_next==null){
            //证明只有一个节点，则last也指向null
            last=null;
        }else {
            //斩断两个节点的链
            old_next.prev=null;
        }
        //元素个数--
        size--;
        //操作次数++
        modCount++;
        return old_data;
    }
    //删除最后一个节点
    private Object unlinkLast(){
        Object old_data = last.data;
        Node old_prev = last.prev;
        last.data=null;
        last.prev=null;
        last=old_prev;
        if(old_prev==null){
            head=null;
        }else {
            old_prev.next=null;
        }
        size--;
        modCount++;
        return old_data;
    }
}
