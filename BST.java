package com.baijia;

import java.util.NoSuchElementException;

/**
 * 
 * @title BST
 * @desc binary search tree
 * @author guojin
 * @date 2017年4月6日
 * @version version
 */
public class BST<Key extends Comparable<Key>, Value> {

    private Node root;// root of BST

    private class Node {
        private Key key;// sorted by key
        private Value val;// associated data
        private Node left, right;// left and right subtrees
        private int size;// number of nodes in subtrees

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        } else {
            return get(key) != null;
        }
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        } else {
            return get(root, key);
        }
    }

    private Value get(Node x, Key key) {
        if (x == null) {
            return null;
        } else {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                return get(x.left, key);
            } else if (cmp > 0) {
                return get(x.right, key);
            } else {
                return x.val;
            }
        }
    }

    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {
            return new Node(key, val, 1);
        } else {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x.left = put(x.left, key, val);
            } else if (cmp > 0) {
                x.right = put(x.right, key, val);
            } else {
                x.val = val;
            }
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }
    }

    /**
     * 返回第k大的数
     * 
     * @param k
     * @return
     */
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        } else {
            Node x = select(root, k);
            return x.key;
        }
    }

    public Node select(Node x, int k) {
        if (x == null) {
            return null;
        } else {
            int t = size(x.left);
            if (t > k) {
                return select(x.left, k);
            } else if (t < k) {
                return select(x.right, k);
            } else {
                return x;
            }
        }
    }

    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        } else {
            return rank(key, root);
        }
    }

    public int rank(Key key, Node x) {
        if (x == null) {
            return 0;
        } else {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                return rank(key, x.left);
            } else if (cmp > 0) {
                return 1 + size(x.left) + rank(key, x.right);
            } else {
                return size(x.left);
            }
        }
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        } else {
            root = deleteMin(root);
        }
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        } else {
            x.left = deleteMin(x.left);
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }
    }

    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        } else {
            root = deleteMax(root);
        }
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        } else {
            x.right = deleteMax(x.right);
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        } else {
            root = delete(root, key);
        }
    }

    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        } else {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x.left = delete(x.left, key);
            } else if (cmp > 0) {
                x.right = delete(x.right, key);
            } else {
                if (x.right == null) {
                    return x.left;
                } else if (x.left == null) {
                    return x.right;
                } else {
                    Node t = x;
                    x = min(t.right);
                    x.right = deleteMin(t.right);
                    x.left = t.left;
                }
            }
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }
    }

    public Node min() {
        return min(root);
    }

    /**
     * 获取x节点下（包括x节点）key最小的节点
     * 
     * @param x
     * @return
     */
    private Node min(Node x) {
        if (x == null) {
            throw new IllegalArgumentException("argument to min() is null");
        } else if (x.left != null) {
            return min(x.left);
        } else {
            return x;
        }
    }

    public static void main(String[] args) {
        BST<Integer, String> bst = new BST<Integer, String>();
        bst.put(3, "guo");
        bst.put(1, "jin");
        bst.put(4, "bjhl");
        bst.delete(1);
        System.out.println(bst.min().key);
    }

}
