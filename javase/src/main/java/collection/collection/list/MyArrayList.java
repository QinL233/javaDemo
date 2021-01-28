package collection.collection.list;

import java.util.Arrays;
import java.util.Collection;

/**
 * 使用数组实现，查询快O(1)，增删慢O(n)，有序允许重复
 */
public class MyArrayList {

    //实际存储数据【使用数组实现】
    private Object[] data;
    //记录元素个数【并非数组长度，数组长度在创建时就固定了】
    private int size;
    //默认数组容量
    private int default_capacity;
    //空数组
    private Object[] empty_data={};
    //共享空数组
    private Object[] auto_empty_data={};
    //对象操作数组次数，用于多线程检查对元素个数是否发生改变【每次增删数组modCount+1】，实现fail-fast
    protected int modCount;

    /**
     * 无参构造：生成一个空数组
     */
    public MyArrayList(){
        data=auto_empty_data;
        System.out.println("create auto_empty_data");
    }

    /**
     * 指定长度构造容器
     * @param size 容器长度
     */
    public MyArrayList(int size){
        //根据size分情况构造数组
        if(size>0){
            data=new Object[size];
            System.out.println("create Object["+size+"]");
        }else if(size==0){
            data=empty_data;
            System.out.println("create empty_data");
        }else {
            throw new IllegalArgumentException("error size:"+size);
        }
    }

    /**
     * 通过传入Collection集合构建容器，容器的初始值为该collection
     * @param collection
     */
    public MyArrayList(Collection collection){
        //使用collection自带的toArray()返回数组
        data=collection.toArray();
        //更新元素个数
        size=data.length;
        /*
            判断toArray是否返回成功
            若Collection对象为固定类型数组，有可能返回的不是Object[]
            因此存入String，Integer等其它类型时会出错
         */
        if(size !=0){
            //如果返回的不是Object[]数组
            if(data.getClass() != Object[].class){
                //使用Arrays.copyOf确保返回Object[]
                data=Arrays.copyOf(data,size,Object[].class);
                System.out.println("create Object["+size+"]"+data.getClass());
            }
        }else {
            data=empty_data;
            System.out.println("create empty_data");
        }
    }

    public boolean add(Object object){
        //判断数组大小，是否需要扩容
        //add后的元素个数，即需要的数组长度
        int request_size = size+1;
        //如果时自动扩展的空数组
        if (data==auto_empty_data){
            /*
                获取新的数组长度【这里size需要+1判断，因为是add】
                eg:Math.max(int,int):比较两个数大小，返回大的数
                小于10则取10，大于10则取size+1【add后的元素个数，即需要的数组长度】
                1.这里表面auto_empty_data在做add时会自动创建大小为10的数组
                2.而empty_data则不会进行这一逻辑判断，只会判断下边+1后是否大于数组长度
             */
            request_size=Math.max(size+1,default_capacity);
        }
        //数组操作符+1
        modCount++;
        //如果新的数组长度【add后的元素个数，即需要的数组长度】确实大于当前数组长度
        if(request_size - data.length >0){
            //扩展数组
            int old_size=data.length;
            //新数组大小为扩展一半
            int new_size=old_size+(old_size>>1);
            /*
                1.先判断扩张后的数组长度是否满足需要
                2.后判断扩张后的数组长度是否过长
             */
            if(new_size < request_size){
                //如果扩展后还是不能满足需求则扩展到需求的大小
                new_size=request_size;
            }
            if(new_size > Integer.MAX_VALUE-8){
                //如果扩容后的值已经接近max，则判断【需求】的大小是否大于max-8，如果是就直接上max，如果不是就接近max以免jvm内存溢出
                new_size=request_size>Integer.MAX_VALUE-8?Integer.MAX_VALUE:Integer.MAX_VALUE-8;
            }
            //真正扩展数组大小的方法：核心为创建新的数组并复制
            data=Arrays.copyOf(data,new_size);
            System.out.println("extend Object["+size+"]");
        }
        //在当前元素位置插入改数据
        data[size]=object;
        System.out.println("add Object["+size+"]"+"-->"+object+"-->size:"+(size+1));
        //元素个数+1
        size++;
        return true;
    }

    //判断索引合法
    private void checkIndex(int index){
        //因为size位置是当前用于存储的位置，一定为空【真正的数组大小是size-1】
        if(index<0 || index > size){
            throw new IllegalArgumentException("error size"+size);
        }
    }

    //获取数组元素
    public Object get(int index){
        checkIndex(index);
        return data[index];
    }

    //更新数组元素
    public boolean set(int index,Object object){
        checkIndex(index);
        modCount++;
        data[index]=object;
        System.out.println("set Object["+index+"]"+"-->"+get(index));
        return true;
    }

    /**
     * 根据index删除数组元素并返回旧值
     * @param index
     * @return
     */
    public Object remove(int index){
        //判断数组大小
        checkIndex(index);
        //数组操作符+1
        modCount++;
        //获取旧值
        Object old_value=get(index);
        System.out.println("remove Object["+index+"]"+"-->"+old_value);
        /*
            删除数组元素:使用System.copyOf(src,src_len,des,des_len,copy_size
            复制src从src_len开始的元素到des的des_len，复制的元素个数为copy_size
                eg: 0,1,2,[3],4,5,6,7
                1.src_len为源数组起始位置：index+1
                2.des_len为目标数组起始位置：index
                3.copy_size为复制元素的长度：size-(index+1)
         */
        int copy_size=size-(index+1);
        if(copy_size>0){
            //如果需要复制的长度大于0
            System.arraycopy(data,index+1,data,index,copy_size);
        }
        //最终删除末尾项
        data[size-1]=null;
        //删除成功元素个数-1
        size--;
        return old_value;
    }

    //根据value删除元素，从头遍历
    public boolean remove(Object object){
        if(object==null){
            for (int i=0;i<size;i++){
                if(data[i]==null) {
                    fastRemove(i);
                }
            }
        }else {
            for (int i=0;i<size;i++){
                if(object.equals(data[i])) {
                    fastRemove(i);
                }
            }
        }
        return true;
    }

    //无需判断index边界的remove方法，仅供remove(object)使用
    private void fastRemove(int index){
        //数组操作符+1
        modCount++;
        //获取旧值
        Object old_value=get(index);
        System.out.println("remove Object["+index+"]"+"-->"+old_value);
        int copy_size=size-(index+1);
        if(copy_size>0){
            //如果需要复制的长度大于0
            System.arraycopy(data,index+1,data,index,copy_size);
        }
        //最终删除末尾项
        data[size-1]=null;
        //删除成功元素个数-1
        size--;
    }

}
