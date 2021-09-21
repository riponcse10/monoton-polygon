
package offline3;

/**
 *
 * @author Ripon
 */
public class BinarySearchTree {

    private BSTNode root;
    //private long size;

    public BinarySearchTree() {
        root = null;
        //size = 0;
    }

    public void insert(Edge edg) {

        BSTNode newNode = new BSTNode();
        newNode.edge = edg;

        if (root == null) {
            root = newNode;
            //size++;
        } else {
            Comparable keys = edg.keyValue();
            while (true) { 	    		
                root = findNode(keys, root);
                Comparable cmp = root.keyValue();
                if (keys.compareTo(cmp) < 0) {
                    newNode.leftnode = root.leftnode;
                    newNode.rightnode = root;
                    root.leftnode = null;
                    root = newNode;
                    //size++;
                    return;
                } else if (keys.compareTo(cmp) > 0) {
                    newNode.rightnode = root.rightnode;
                    newNode.leftnode = root;
                    root.rightnode = null;
                    root = newNode;
                    //size++;
                    return;
                } else {
                    keys = edg.keyValue();
                }
            }
        }
    }

    
    public BSTNode delete(Comparable keys) {

        BSTNode newTree;

        root = findNode(keys, root);

        if (!(root.keyValue()).equals(keys)) {
            return null;
        } 
        BSTNode result = root;

        if (root.leftnode == null) {
            newTree = root.rightnode;
        } else {
            
            newTree = root.leftnode;
            newTree = findNode(keys, newTree);
            newTree.rightnode = root.rightnode;
        }

        root = newTree;
        //size--;
        return result;
    }

    
    
    public BSTNode findMaxSmallerThan(Comparable keys) {
        
        root = findNode(keys, root);

        if (root.data().keyValue().compareTo(keys) < 0) {
            return root;
        } else if (root.leftnode != null) {
            BSTNode result = root.leftnode;
            while (result.rightnode != null) {
                result = result.rightnode;
            }
            return result;
        } else {
            
            return null;
        }
    }

    
    
    
    private BSTNode rotateWithLeftChild(BSTNode k2) {
        BSTNode k1 = k2.leftnode;
        k2.leftnode = k1.rightnode;
        k1.rightnode = k2;
        return k1;
    }

    
    private BSTNode rotateWithRightChild(BSTNode k1) {
        BSTNode k2 = k1.rightnode;
        k1.rightnode = k2.leftnode;
        k2.leftnode = k1;
        return k2;
    }

    private static BSTNode header = new BSTNode();

    
    private BSTNode findNode(Comparable keys, BSTNode t) {
        BSTNode _leftTreeMax, _rightTreeMin;
            
        header.leftnode = header.rightnode = null;
        _leftTreeMax = _rightTreeMin = header;

        while (true) {
            Comparable rKey = t.keyValue();
            if (keys.compareTo(rKey) < 0) {
                if (t.leftnode == null) {
                    break;
                }
                if (keys.compareTo(t.leftnode.keyValue()) < 0) {
                    t = rotateWithLeftChild(t);
                }
                if (t.leftnode == null) {
                    break;
                }

                
                _rightTreeMin.leftnode = t;
                _rightTreeMin = t;
                t = t.leftnode;
            } else if (keys.compareTo(rKey) > 0) {
                if (t.rightnode == null) {
                    break;
                }
                if (keys.compareTo(t.rightnode.keyValue()) > 0) {
                    t = rotateWithRightChild(t);
                }
                if (t.rightnode == null) {
                    break;
                }

                
                _leftTreeMax.rightnode = t;
                _leftTreeMax = t;
                t = t.rightnode;
            } else {
                break;
            }
        }

        _leftTreeMax.rightnode = t.leftnode;
        _rightTreeMin.leftnode = t.rightnode;
        t.leftnode = header.rightnode;
        t.rightnode = header.leftnode;
        //t.leftnode = null;
        //t.rightnode = null;
        return t;
    }

}
