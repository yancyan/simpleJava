package com.simple.data.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap + LinkedList.
 *
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制 。
 *
 * 实现 LRUCache 类：
 *
 *     LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
 *     int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 *     void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 *
 *
 * @author ZhuYX
 * @date 2021/05/31
 */
public class HashMapLinkedLRU {

    //
    // class DLinkedNode {
    //     int key;
    //     int value;
    //     DLinkedNode prev;
    //     DLinkedNode next;
    //     public DLinkedNode() {}
    //     public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
    // }
    //
    // private Map<Integer, DLinkedNode> cache = new HashMap<>();
    // private int size;
    // private int capacity;
    // private DLinkedNode head, tail;
    //
    // public HashMapLinkedLRU(int capacity) {
    //     this.size = 0;
    //     this.capacity = capacity;
    //
    //     head = new DLinkedNode();
    //     tail = new DLinkedNode();
    //     head.next = tail;
    //     tail.prev = head;
    // }
    //
    // public int get(int key) {
    //     DLinkedNode node = cache.get(key);
    //     if (node == null) {
    //         return -1;
    //     }
    //     moveToHead(node);
    //     return node.value;
    // }
    //
    // public void put(int key, int value) {
    //     DLinkedNode node = cache.get(key);
    //     if (node == null) {
    //         // 如果 key 不存在，创建一个新的节点
    //         DLinkedNode newNode = new DLinkedNode(key, value);
    //         // 添加进哈希表
    //         cache.put(key, newNode);
    //         // 添加至双向链表的头部
    //         addToHead(newNode);
    //         ++size;
    //         if (size > capacity) {
    //             // 如果超出容量，删除双向链表的尾部节点
    //             DLinkedNode tail = removeTail();
    //             // 删除哈希表中对应的项
    //             cache.remove(tail.key);
    //             --size;
    //         }
    //     }
    //     else {
    //         // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
    //         node.value = value;
    //         moveToHead(node);
    //     }
    // }
    //
    // private void addToHead(DLinkedNode node) {
    //     node.prev = head;
    //     node.next = head.next;
    //     head.next.prev = node;
    //     head.next = node;
    // }
    //
    //
    // private void removeNode(DLinkedNode node) {
    //     node.prev.next = node.next;
    //     node.next.prev = node.prev;
    // }
    //
    //
    // private void moveToHead(DLinkedNode node) {
    //     removeNode(node);
    //     addToHead(node);
    // }
    //
    // private DLinkedNode removeTail() {
    //     DLinkedNode res = tail.prev;
    //     removeNode(res);
    //     return res;
    // }



    // public static void main(String[] args) {
    //     var lru = new HashMapLinkedLRU(2);
    //     lru.put(1, 11);
    //     lru.put(2, 22);
    //     System.out.println(lru);
    //     lru.put(3, 33);
    //     System.out.println(lru);
    //     lru.put(4, 44);
    //     System.out.println(lru);
    //
    // }


    Map<Integer, LinkedNode> cache;
    int size;
    int capacity;

    LinkedNode head, tail;

    HashMapLinkedLRU(int capacity) {
        cache = new HashMap<>(capacity);
        this.size = 0;
        this.capacity = capacity;

        this.head = new LinkedNode();
        this.tail = new LinkedNode();

        head.next = tail;
        tail.pre = head;
    }

    public Integer get(Integer key) {
        var value = cache.get(key);
        if (value == null) {
            return -1;
        }
        moveToHead(value);
        return value.val;
    }

    public void put(Integer key, Integer value) {
        var vaNode = cache.get(key);
        if (vaNode != null) {
            moveToHead(vaNode);
        }
        else {
            var newNode = new LinkedNode();
            newNode.key = key;
            newNode.val = value;

            cache.put(key, newNode);

            addToHead(newNode);
            size++;

            // remove tail if necessary.
            if (capacity < size) {
                var tail = removeTail();
                cache.remove(tail.key);
                size--;
            }
        }
    }

    private void moveToHead(LinkedNode node) {
        remove(node);
        addToHead(node);
    }

    private LinkedNode removeTail() {
        var pre = tail.pre;
        remove(pre);
        return pre;
    }

    private void addToHead(LinkedNode node) {
        node.pre = head;
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
    }

    private void remove(LinkedNode node) {

        node.pre.next = node.next;
        node.next.pre = node.pre;

    }

    static class LinkedNode {
        LinkedNode pre;
        LinkedNode next;

        Integer key;
        Integer val;
    }
}
