package com.ce;

import java.util.Comparator;

/**
 * ClassName:    SkipList
 * Package:    com.ce
 * Description: 跳表的实现
 * Datetime:    2021/4/14   20:14
 * Author:   XXXXX@XX.com
 */
public class SkipList<K,V> {
    private static final int MAX_LEVEL = 32; //跳表最高32层
    private static final double P = 0.25;

    private int size;
    private Comparator<K> comparator;
    /**
     * 有效层数
     */
    private int level;
    /**
     * 首节点不存放任何key-value对
     */
    private Node<K,V> first;

    public SkipList(Comparator<K> comparator) {
        this.comparator = comparator;
        first = new Node<>(null,null,MAX_LEVEL);
        first.nexts = new Node[MAX_LEVEL];
    }
    public SkipList() {
        this(null);
    }

    public int size(){
        return size;
    }

    /**
     * 判断跳表是否为空
     * @return
     */
    public boolean isEmpty(){
        return size==0;
    }

    /**
     *
     * @param key
     * @param value
     * @return 返回以前该key 对应的value值
     */
    public V put(K key, V value){
        keyCheck(key);
        Node<K,V> node = first;
        //用来存储插入的某个节点的之前的每一层的前驱
        Node<K,V>[] prevs = new Node[level];//这里的level指的是有效层数，之前有定义过

        for (int i = level-1; i >=0 ; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(key,node.nexts[i].key)) > 0){
                node = node.nexts[i];
            }
            if (cmp == 0){//节点是存在的，只需要改变value
                V oldV  = node.nexts[i].value;
                node.nexts[i].value = value;
                return oldV;
            }
            prevs[i] = node;
        }
        //能来到这意味着i是-1，这里相当于已经确定前驱node 要添加新节点
        //新节点的层数
        int newLevel = randomLevel();
        //添加新节点
        Node<K,V> newNode = new Node<>(key, value,newLevel);
        //设置前驱和后继节点
        for (int i = 0; i < newLevel ; i++) {
            if (i>=level){
                first.nexts[i] = newNode;
            }else {
                newNode.nexts[i] = prevs[i].nexts[i];
                prevs[i].nexts[i] = newNode;
            }

        }
        size++;
        //计算跳表的最终层数
        level = Math.max(level,newLevel);
        return null;
    }

    /**
     *
     * @param key
     * @return 返回 key 对应的value
     */
    public V get(K key){
        keyCheck(key);
        Node<K,V> node = first;
        for (int i = level-1; i >=0 ; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(key,node.nexts[i].key)) > 0){
                node = node.nexts[i];
            }
            // node.next[i].key >= key
            if (cmp == 0){
                return node.nexts[i].value;
            }
        }
        return null;
    }

    /**
     *
     * @param key
     * @return 删除某个k-value ，并返回删掉的value值
     */
    public V remove(K key){
        keyCheck(key);
        Node<K,V> node = first;
        //用来存储插入的某个节点的之前的每一层的前驱
        Node<K,V>[] prevs = new Node[level];//这里的level指的是有效层数，之前有定义过
        boolean exist = false;

        for (int i = level-1; i >=0 ; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(key,node.nexts[i].key)) > 0){
                node = node.nexts[i];
            }

            prevs[i] = node;
            if (cmp==0){
                exist = true;
            }
        }
        if (!exist) return null;
        // 存在这个数，把他删掉
        // 需要被删除的节点
        Node<K,V> removeNode = node.nexts[0];
        // 数量减少
        size--;
        //设置后继
        for (int i = 0; i < removeNode.nexts.length; i++) {
            prevs[i] = removeNode.nexts[i];
        }
        // 更新跳表的层数
        int newLevel = level;
        while (--newLevel>=0 && first.nexts[newLevel]==null){
            level = newLevel;
        }

        return removeNode.value;
    }

    private int compare(K k1, K k2){
        return comparator != null ? comparator.compare(k1,k2) : ((Comparable<K>)k1).compareTo(k2);
    }


    private void  keyCheck(K key){
        if(key==null){
            throw new IllegalArgumentException("key must not be null");
        }
    }

    private int randomLevel(){
        int level = 1;
        while (Math.random()<P && level < MAX_LEVEL){
            level++;
        }
        return level;
    }

    private static class Node<K,V> {
        K key;
        V value;
        Node<K,V>[] nexts;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            nexts = new Node[level];
        }
        @Override
        public String toString() {
            return key + ":" + value + "_" + nexts.length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("一共" + level + "层").append("\n");
        for (int i = level - 1; i >= 0; i--) {
            Node<K, V> node = first;
            while (node.nexts[i] != null) {
                sb.append(node.nexts[i]);
                sb.append("\t");
                node = node.nexts[i];
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
