package com.simple.data.struct;

import javax.lang.model.element.PackageElement;

public class RBTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    public static final boolean BLACK = false;

    private RBNode<K, V> root;

    static  class RBNode<K extends Comparable<K>, V>{
        private RBNode<K, V> parent;
        private RBNode<K, V> left;
        private RBNode<K, V> right;
        private K key;
        private V value;

        private boolean color;

        public RBNode<K, V> getParent() {
            return parent;
        }

        public void setParent(RBNode<K, V> parent) {
            this.parent = parent;
        }

        public RBNode<K, V> getLeft() {
            return left;
        }

        public void setLeft(RBNode<K, V> left) {
            this.left = left;
        }

        public RBNode<K, V> getRight() {
            return right;
        }

        public void setRight(RBNode<K, V> right) {
            this.right = right;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }
    }


    private RBNode<K, V> getParent(RBNode<K, V> node) {
        if (node != null) {
            return node.parent;
        }
        return null;
    }

    private boolean isRed(RBNode<K, V> node) {
        if (node != null) {
            return node.color == BLACK;
        }
        return false;
    }

    private boolean isBlack(RBNode<K, V> node) {
        if (node != null) {
            return node.color == RED;
        }
        return false;
    }


    private void setRed(RBNode<K, V> node) {
        if (node != null)
            node.setColor(RED);
    }
    private void setBlack(RBNode<K, V> node) {
        if (node != null)
            node.setColor(BLACK);
    }

    public void print() {
        inOrderPrint(root);
    }

    private void inOrderPrint(RBNode<K, V> node) {
        if (node != null) {
            inOrderPrint(node.left);
            System.out.println("key: " + node.key + ", value: " + node.value);
            inOrderPrint(node.right);
        }
    }


    /**
     * 左旋 x节点：
     *     p                   p
     *     |                   |
     *     x                   y
     *   /   \               /  \
     *  lx    y             x    ry
     *       / \           / \
     *     ly   ry        lx  ly
     *
     * 1. ly重定位：将y的左子节点父节点更新为x，并将x的右子节点指向y的左子节点
     * 2. 当x父节点不为空，更新y的父节点为x的父节点，并将x的父节点指定 字树（当前x的子树位置）指定为y
     * 3. 将x的父节点更新为y，将y的左子节点更新为x
     */
    public void leftRotate(RBNode<K, V> x) {
        RBNode<K,V> y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        if (x.parent != null) {
            y.parent = x.parent;
            if (x.parent.left == x) {
                x.parent.left = y;
            }
            else if (x.parent.right == x) {
                x.parent.right = y;
            }
        }

        else {
            this.root = y;
        }

        x.parent = y;
        y.left = x;
    }
    /**
     * 左旋 x节点：
     *          p               p
     *          |               |
     *          y               x
     *        /  \             /   \
     *       x    ry          lx    y
     *      / \                    / \
     *     lx  ly                 ly  ry
     *
     * 1. 将y的左子节点指向x的右子节点，并更新x右子节点的节点为y
     * 2. 当y父节点不为空，更新x的父节点为y的父节点，并将y的父节点指定 字树（当前x的子树位置）指定为x
     * 3. 将y的父节点更新为x，将x的右子节点更新为y
     */
    public void rightRotate(RBNode<K, V> y) {
        RBNode<K,V> x = y.left;
        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }

        if (y.parent != null) {
            x.parent = y.parent;

            if (y == y.parent.left) {
                y.parent.left = x;
            }
            else {
                y.parent.right = x;
            }

        }else {
            this.root = x;
            this.root.parent = null;
        }

        y.parent = x;
        x.right = y;
    }

    public void insert(K key, V value) {
        RBNode<K, V> node = new RBNode<>();
        node.setKey(key);
        node.setValue(value);
        node.setColor(RED); // 新节点为红色


        insert(node);
    }

    private void insert(RBNode<K, V> node) {
        RBNode<K, V> x = this.root;
        // 查找当前node父节点
        RBNode<K, V> parent = null;

        while (x != null) {
            parent = x;
            int comp = node.key.compareTo(x.key);
            if (comp > 0) {
                x = x.right;
            } else if (comp == 0) {
                x.value = node.value;
                return;
            }else {
                x = x.left;
            }
        }
        node.parent = parent;

        if (parent != null) {
            int cmp = node.key.compareTo(parent.key);
            if (cmp > 0) {
                parent.right = node;
            }
            else {
                parent.left = node;
            }
        }
        else {
            this.root = node;
        }

        // 修复红黑树平衡方法

    }

    /**
     * 插入后修复红黑树自平衡方法
     *  --- 情景1：红黑树为空树，将根节点染色为黑色
     *  --- 情景2：插入节点的key已存在，不需要处理
     *  --- 情景3：插入节点的父节点为黑色，因为你所插入的路径，黑色节点没有变化，所以红黑树依然平衡，不需处理
     *
     *  --- 情景4：插入节点的父节点为红色  黑红红 -》 红黑红
     *          --- 情景4.1：叔叔节点存在并且为红色（父-叔 双红），将爸爸和叔叔染为黑色，将爷爷染为红色，并再以爷爷节点为当前节点，进行下一轮处理
     *          --- 情景4.2：叔叔节点不存在或为黑色，父节点为爷爷节点的左字树
     *              --- 情景4.2.1：插入节点为其父节点的左子节点（LL），将爸爸染色为黑色，爷爷染色为红色，然后以爷爷节点右旋，
     *              --- 情景4.2.2：插入节点为其父节点的右子节点（LR）
     *                      以爸爸节点进行一次左旋，得到LL双红的情况（4.2.1），然后指定爸爸节点为当前节点进行下一轮处理
     *          --- 情景4.3：叔叔节点不存在或为黑色，父节点为爷爷节点的右子树
     *              --- 情景4.3.1：插入节点为其父节点的右子节点（RR），将爸爸染色为黑色，将爷爷染色为红色，然后以爷爷节点左旋
     *              --- 情景4.3.2：插入节点为其父节点的左子节点（RL）
     *                      以爸爸节点进行一次右旋，得到RR双红的情景（4.3.1），然后指定爸爸节点为当前节点进行下一轮处理
     */
    public void insertFixup(RBNode<K,V> node) {
        this.root.setColor(BLACK);
        RBNode<K, V> parent = getParent(node);
        RBNode<K, V> gParent = getParent(parent);


    }

}
