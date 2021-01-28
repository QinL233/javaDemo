package collection.map;

import java.util.Objects;

public class MyHashMap<k,v> {
    //默认数组容量
    static final int default_capacity=1<<4;
    //最大数组容量
    static final int max_capacity=1<<30;
    //装置因子：判断map的拥挤度，容量*转载因子 ->16*0.75=12 即为转载边界
    static final float default_load=0.75f;
    //转载因子
    final float load;
    //链长大于该值则转成红黑树
    static final int tree_flag=1<<3;
    //链长小于该值则转换成单链表
    static final int link_flag=(1<<3)-2;
    //最小转换树容量的标识，至少是默认容量的4倍才开始转换
    static final int min_tree_capacity=default_capacity<<2;
    //该容器的node数组
    transient Node<k,v>[] tables;
    //node数，即元素个数
    transient int size;
    //修改次数
    transient int modCount;
    //即用于决定是否扩容的边界值，可以是load*capacity，也可以给定
    int capacity_flag;


    //链表：存储一个数据的节点，包括key和value，其hash为key的桶号，next则是单链表的指向
    static class Node<k,v>{
        private int hash;
        private k key;
        private v value;
        private Node<k,v> next;

        public Node(int hash, k key, v value, Node<k,v> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public k getKey() {
            return key;
        }

        public v getValue() {
            return value;
        }

        public void setValue(v value) {
            this.value = value;
        }

        public int hashCode(){
            /*
                key，value的哈希码(二进制)做异或运算：相同返回0，相反返回1 --》 同类抵消
                eg: 1^2=3  --》 001 ^ 010 = 011 = 3
             */
            return Objects.hashCode(key)^Objects.hashCode(value);
        }

        //判断该节点是否已存在,是否为新节点
        public boolean isExist(Node<k,v> new_node){
            if(new_node == this){
                return true;
            }else {
                //比较key和value是否相同
                if(Objects.equals(key,new_node.key) && Objects.equals(value,new_node.value)){
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    //树结构节点，扩展node链式结构
    static class TreeNode<k,v> extends Node<k,v>{
        //父节点
        private TreeNode<k,v> parent;
        //左节点
        private TreeNode<k,v> left;
        //右节点
        private TreeNode<k,v> right;
        //是否为红
        private boolean red;
        //前一个节点
        private TreeNode<k,v> prev;

        public TreeNode(int hash,k key,v value,Node<k,v> next){
            super(hash, key, value, next);
        }

        //返回根节点
        public  TreeNode root(){
            //循环树：因为必定存在root节点，因此写死循环
            for(TreeNode root=this,flag;true;){
                //若树的父节点为空，则返回该节点
                if((flag=root.parent)==null){
                    return root;
                }
                //否则将指针上移到父节点
                root=flag;
            }
        }
    }

    //构造：主要是决定装载因子和初始化capacity容量，并不会构建数组
    public MyHashMap(){
        this.load=default_load;
    }

    public MyHashMap(int capacity){this(capacity,default_load); }

    public MyHashMap(int capacity,float load){
        if(capacity <0){
            throw new IllegalArgumentException("error capacity:"+capacity);
        }else if(capacity > max_capacity){
            capacity=max_capacity;
        }
        if(Float.isNaN(load) || load<0 ){
            throw new IllegalArgumentException("error load"+load);
        }
        this.load=load;
        //确保返回一个二次幂数字的容量，扩容标识符为【大于等于capacity的最近二次幂】
        this.capacity_flag=getCapacityWithPower(capacity);
    }
    //返回key的hash码
    private int getHash(k key){
        int flag;
        return key==null?0:(flag=key.hashCode())^(flag>>>16);
    }

    //返回一个【大于等于capacity的最近二次幂】数字：10->16，17->32
    private static int getCapacityWithPower(int capacity){
        /*
            |=：或运算，位数“或”存在1则1
            &=：与运算，位数“与”存在1则1
            ^=：异或运算，位数相同则1
         */
        //保证当给定数就是【最近的二次幂】时可以获得
        capacity--;
        //思路：将数字右移一位取或得到前两位为1，再右移两位取或得前4位为1，同理
        capacity |=capacity>>>1;
        capacity |=capacity>>>2;
        capacity |=capacity>>>4;
        capacity |=capacity>>>8;
        capacity |=capacity>>>16;
        //得到前32位都为1，即为二次幂数字
        return capacity<0?1:capacity>=max_capacity?max_capacity:capacity+1;
    }

    //扩容数组长度，默认大小为16，若元素个数比例达到upload则扩展为2倍
    private Node[] extendCapacity(){
        //旧元素
        Node<k,v>[] old_tables=tables;
        int old_capacity=old_tables==null?0:old_tables.length;
        int old_capacity_flag=capacity_flag;
        //新元素
        int new_capacity;
        int new_capacity_flag=0;

        //如果旧数组有值
        if(old_capacity > 0){
            //如果数组已经达到上限
            if(old_capacity >= max_capacity){
                //修改阈值为int的最大值(2^31-1)，这样以后就不会扩容了
                capacity_flag=Integer.MAX_VALUE;
                //返回旧数组
                return old_tables;
                //【此处扩大数组一倍】，如果新数组小于max，并且旧数组长度大于默认值
            }else if((new_capacity=old_capacity<<1) < max_capacity && old_capacity >= default_capacity){
                //将新表的【容量标记】扩容两倍
                new_capacity_flag=old_capacity_flag<<1;
            }
            //或者如果旧容量标记已经大于0，即已经给定容量大小
        }else if(old_capacity_flag > 0){
            //则直接使用该容量标记
            new_capacity=old_capacity_flag;
        }else {
            //否则给定默认容量标记，16*0.75=12
            new_capacity_flag=(int)(default_load*default_capacity);
            //给定默认容量为，16
            new_capacity=default_capacity;
        }
        //如果容量标记还是等于0， 即old_capacity ∈ (0, default)且flag未自定义
        if(new_capacity_flag == 0){
            //则该flag为当前容量*load
            float ft=(float)new_capacity*load;
            new_capacity_flag= new_capacity < max_capacity && ft < (float)max_capacity?(int)ft:Integer.MAX_VALUE;
        }
        //刷新容量标记
        capacity_flag=new_capacity_flag;
        //开始构造新数组，使用新的容量大小
        Node<k,v>[] new_tables=new Node[new_capacity];
        //将容器绑定在新数组中
        tables=new_tables;
        //开始将旧值移动到新表中
        if(old_tables != null){
            //遍历旧表
            for (int i=0;i<old_capacity;i++){
                Node<k,v> node=old_tables[i];
                //如果该位置有值
                if(node != null) {
                    //清空位置释放内存
                    old_tables[i]=null;
                    //判断该节点是否有后继节点
                    if (node.next == null) {
                        //若无后继节点即非链表，直接将该复制到新表的位置中【数组位置：hash & (len-1)】
                        new_tables[node.hash & (new_capacity - 1)] = node;
                        //否则该位置的是链表
                    } else {
                        //新表大小为2倍，因此将单链表分成【0/1】两个单链表，并分别存将对应的head放在新的数组对应的两个位置中
                        Node<k,v> head0=null, last0=null;//0链表：头尾指针
                        Node<k,v> head1=null, last1=null;//1链表：头尾指针
                        //遍历链表
                        Node next;
                        do{
                            next=node.next;
                            /*
                                将一个单链表分成【0/1】两个单链表，比较方法:
                                【eg1】
                                    node.hash       =10     0000 1010
                                    old_capacity    =16     0001 0000
                                    &               =0      0000 0000
                                 结论扩容后无需改变位置
                                 【eg2】
                                    node.hash       =17     0001 0001
                                    old_capacity    =16     0001 0000
                                    &               =1      0001 0000
                                 结论扩容后，位置发生了变化，新下标为【原下标+原长度】
                             */
                            if((node.hash & old_capacity)==0){
                                /*
                                    单链操作
                                    eg: now为当前的节点
                                    1：head=now，last=now
                                    2：last.next=now【这里将上一个节点链到当前节点】，last=now继续下移
                                    3：last.next=now【继续将上一个节点链到当前节点】，last=now继续下移
                                 */
                                if (last0 == null){
                                    //说明该链还没有元素，将当前节点复制到头指针
                                    head0=node;
                                }else {
                                    //将上一个节点链到当前节点
                                    last0.next=node;
                                }
                                //尾指针下移
                                last0=node;
                            }else {
                                //同理
                                if(last1 == null){
                                    head1=node;
                                }else {
                                    last1.next=node;
                                }
                                last1=node;
                            }
                            //指针下移
                            node=next;
                        }while (node !=null);
                        //复制0链表
                        if(last0 != null){
                            //末尾指向null
                            last0.next=null;
                            //将头存放在数组【原位置】
                            new_tables[i]=head0;
                        }
                        //复制1链表
                        if(last1 != null){
                            last1.next=null;
                            //将头存放在数组【原来的位置+扩容大小（原容量大小）的位置】
                            new_tables[i+old_capacity]=head1;
                        }
                    }
                }
            }
        }
        return new_tables;
    }

    //添加元素
    public boolean put(k key,v value){
        //默认为允许覆盖old_value
        return putVal(getHash(key),key,value,false);
    }

    /**
     * 插入一个元素对象
     * @param hash
     * @param key
     * @param value
     * @param notUpdate 不能改变标识，若为false则相同key能覆盖
     * @return  true则表示key完全相同直接覆盖，false表示添加了一个新元素
     */
    private boolean putVal(int hash,k key,v value,boolean notUpdate){
        boolean rs=false;
        /*
           1.数据以node[]类型存放，其下标为hash(key)
           2.若nodes[key]不存在，则直接在该位置【添加】
           3.当key已存在，则【替换】old_node
           4.当hash(key)已存在，则遍历链表，若已存在则【替换】，直到【添加】new_node到末尾
         */
        Node<k,v>[] tables;    //元素数组
        int len;
        //如果tables是空数组，或者数组长度为0
        if((tables=this.tables)==null || (len=tables.length)==0){
            //则扩容数组
            len=(tables=extendCapacity()).length;
        }
        int i;      //数组下标
        //如果数组当前位置为null，则直接插入新节点
        if(tables[i=(len-1)&hash] == null){
            //插入新节点
            tables[i]=new Node(hash,key,value,null);
            //元素个数++
            size++;
            //冲突处理
            rs=false;
        }else {
            //获取该位置的旧元素，该位置也是链表的首位置
            Node<k,v> root_node = tables[i];
            k old_key = root_node.key;
            //如果数组两个key的hash相同且值相同的则直接覆盖
            if(root_node.hash == hash && (old_key == key || (old_key != null && old_key .equals(key)))){
                if(!notUpdate || root_node.value == null ) {
                    //如果能修改或者oldValue本就为null
                    root_node.value = value;
                }
                //替换完毕直接返回
                rs= true;
            }else{
                //否则查看链节点是否存在该key，若最终不存在则添加new_node到末尾
                Node<k,v> node;
                //count是当前链表的个数，因为root存在所以count至少为1，每次遍历完count+1
                for (int count=1;true;count++){
                    //如果根节点next为空
                    if((node=root_node.next) == null){
                        //直接在next位置添加新元素
                        root_node.next=new Node(hash,key,value,null);
                        //添加完毕判断count大小是否达到tree_flag进行是否转换数据格式
                        if(count >= tree_flag-1){
                            //因为count还未+1，因此条件-1
                        }
                        //直接返回
                        rs= false;
                        //元素个数++
                        size++;
                        //跳出循环
                        break;
                    }
                    //如果key和next相同
                    if(node.hash == hash && ((old_key=node.key) == key || (key != null && key.equals(old_key)))){
                        //则替换元素
                        if(!notUpdate || root_node.value == null ) {
                            //如果能修改或者oldValue本就为null
                            node.value = value;
                        }
                        //替换count无需增加，无需判断是否需要变更结构
                        //替换完毕直接返回
                        rs= true;
                    }
                }
            }
        }
        //如果元素个数达到容量标识，则继续扩容tables
        if(size > capacity_flag){
            extendCapacity();
        }
        //操作数++
        modCount++;
        return rs;
    }

    //删除
    public boolean remove(k key){
        return true;
    }

    //查询
    public Object get(k key){
        //根据key获取node
        Node<k,v> node=getNode(getHash(key),key);
        //返回value
        return node==null?null:node.value;
    }

    private Node<k,v> getNode(int hash,k key){
        Node<k,v>[] tab=tables;
        int len=tab.length;
        //tab数组中经过散列的第一个位置
        Node<k,v> head_node=tab[(len-1)&hash];

        k k;
        Node<k,v> node;
        //数组长度不为0
        if (tab != null && len>0 && head_node!=null ){
            k=head_node.key;
            //判断该head_node是否就是取值：判断key
            if(head_node.hash == hash && (k==key || (key != null && key.equals(k)))){
                return head_node;
            }
            //指针指向head的后继
            node=head_node.next;
            //如果为空则不存在该key，直接跳过
            if(node !=null){
                //存在链，遍历链表
                do{
                    //继续判断key是否一致
                    k=node.key;
                    if(node.hash == hash && (k==key || (key != null && key.equals(k)))){
                        return node;
                    }
                    node=node.next;
                }while (node != null);
            }
        }
        return null;
    }

    public int getSize(){
        return size;
    }

}
