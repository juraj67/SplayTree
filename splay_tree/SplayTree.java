package splay_tree;

import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author juraj67
 * @param <T>
 */
public class SplayTree<T extends Comparable<T>> {
    private Node<T> root;
    
    /**
     * Constructor creates an empty Splay_Tree
     */
    public SplayTree() {
    }
    
    /**
     * Constructor creates new Splay_Tree with root
     * @param root 
     */
    public SplayTree(Node<T> root) {
        this.root = root;
    }
        
    /**
     * Copy constructor makes a copy of the Splay_Tree
     * @param tree_toCopy 
     */
    public SplayTree(SplayTree<T> tree_toCopy) {
        this.root = tree_toCopy.getRoot();
    }

    /**
     * Method returns the root of the Splay_Tree
     * @return 
     */
    public Node<T> getRoot() {
        return this.root;
    }
    
    /**
     * Method inserts data to the splay tree
     * @param paData
     * @return 
     */
    public boolean insert(T paData) {
        Node<T> node_toInsert = new Node<T>(paData);
        if(root == null) {
            root = node_toInsert;
        } else {
            Node<T> actual_node = this.root;
            while (actual_node != null) {     
                if (actual_node.getData().compareTo(paData) > 0) {      //go left - s1 > s2, positive number  
                    if (actual_node.hasLeftChild()) {
                        actual_node = actual_node.getLeftChild();
                    } else {
                        actual_node.setLeftChild(node_toInsert);
                        actual_node.getLeftChild().setParent(actual_node);
                        
                        splay(actual_node.getLeftChild());              //splay operation
                        return true;
                    }
                } else if(actual_node.getData().compareTo(paData) < 0){ //go right - s1 < s2, negative number  
                    if (actual_node.hasRightChild()) {
                        actual_node = actual_node.getRightChild();
                    } else {
                        actual_node.setRightChild(node_toInsert);
                        actual_node.getRightChild().setParent(actual_node);
                        splay(actual_node.getRightChild());             //splay operation
                        return true;
                    }
                } else {                                                //if the tree contains the node
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Method finds and returns data
     * @param paCriterion
     * @return 
     */
    public T find(T paCriterion) {
        Node<T> located = this.locateNode(paCriterion);
        if(located == null) {                                       //splay tree is empty
            return located.getData();
        } else if(located.getData().compareTo(paCriterion) == 0) {  //found
            splay(located);          //call splay operation on the found node
            return located.getData();
        } else {                                                    //not found
            splay(located);         //call splay operation on the last node
            return null;
        }
    }
    
    /**
     * Method finds and returns requested node, if node wasn't found, it returns the last visited 
     * @param paCriterion
     * @return 
     */
    private Node<T> locateNode(T paCriterion) {
        Node<T> actual_node = null;
        if(root == null) {
            return actual_node;
        } else {
            actual_node = this.root;
            if(actual_node.getData().compareTo(paCriterion) == 0) {     //requested node is a root
                return actual_node;
            }
            while (actual_node != null) {     
                if (actual_node.getData().compareTo(paCriterion) > 0) {     //go left - s1 > s2, positive number  
                    if (actual_node.hasLeftChild()) {
                        actual_node = actual_node.getLeftChild();
                    } else {                                            //not found
                        return actual_node;
                    }
                } else if(actual_node.getData().compareTo(paCriterion) < 0){ //go right - s1 < s2, negative number  
                    if (actual_node.hasRightChild()) {
                        actual_node = actual_node.getRightChild();
                    } else {                                            //not found
                        return actual_node;
                    }
                } else {                                                //found
                    return actual_node;
                }
            }
        }
        return actual_node;
    }
    
    /**
     * Method deletes requested node 
     * @param paCriterion
     * @return 
     */
    public boolean delete(T paCriterion) {
        boolean success = false;
        Node<T> to_delete = this.locateNode(paCriterion);
        if (this.root == null || (to_delete.getData().compareTo(paCriterion) != 0)) {
            return success;
        } else {
            if (to_delete.isLeaf() && (this.root.getData().compareTo(paCriterion) == 0)) {  //requested node is a root and leaf
                this.root = null;
                success = true;
            } else {
                Node<T> parent_toDelete = to_delete.getParent();          //parent of requested node
                if (to_delete.isLeaf()) {                                       ////requested node is a leaf
                    parent_toDelete.removeChild(paCriterion);
                    splay(parent_toDelete);             //splay nad rodicom
                    success = true;
                } else if (to_delete.hasOnlyOneChild()) {                       //has one son
                    //childOf_toDelete - which son is it
                    Node<T> childOf_toDelete = (to_delete.hasLeftChild() ? to_delete.getLeftChild() : to_delete.getRightChild());
                    if(parent_toDelete == null) {                               //requested node is a root with one son
                        childOf_toDelete.setParent(null);
                        this.root = childOf_toDelete;
                    } else {                                                    //requested has one son
                        childOf_toDelete.setParent(parent_toDelete);
                        parent_toDelete.replaceChild(paCriterion, childOf_toDelete);
                        splay(parent_toDelete);
                    }
                    success = true;
                } else {                                                        //has more sons
                    Node<T> ino_successor = inorder_successor(to_delete); //replace with inorder successor
                    Node<T> parentOf_in_succ = ino_successor.getParent(); //parent of the inorder successor
                    Node<T> in_succ_Right_Son = ino_successor.getRightChild(); //right son of the inorder successor
                    if(parent_toDelete == null) {                               //it is root with two sons
                        ino_successor.setLeftChild(to_delete.getLeftChild());
                        ino_successor.setParent(null);
                        this.root = ino_successor;
                        this.root.getLeftChild().setParent(this.root);
                        if(paCriterion.compareTo(parentOf_in_succ.getData()) != 0) { //root has in right subtree more successors
                            this.root.setRightChild(to_delete.getRightChild());
                            parentOf_in_succ.setLeftChild(in_succ_Right_Son);
                            this.root.getRightChild().setParent(this.root);
                            if(in_succ_Right_Son != null) {
                                in_succ_Right_Son.setParent(parentOf_in_succ);
                            }
                        }
                        success = true;
                    } else {                                                    //it isn't root, but has 2 sons
                        ino_successor.setParent(parent_toDelete);
                        parent_toDelete.replaceChild(paCriterion, ino_successor);
                        ino_successor.setLeftChild(to_delete.getLeftChild());
                        ino_successor.getLeftChild().setParent(ino_successor);
                        if(paCriterion.compareTo(parentOf_in_succ.getData()) != 0) { 
                            ino_successor.setRightChild(to_delete.getRightChild());
                            parentOf_in_succ.setLeftChild(in_succ_Right_Son);
                            ino_successor.getRightChild().setParent(ino_successor);
                            ino_successor.setRightChild(to_delete.getRightChild());
                            if(in_succ_Right_Son != null) {
                                in_succ_Right_Son.setParent(parentOf_in_succ);
                            }
                        }
                        splay(parent_toDelete);
                        success = true;
                    }
                }  
            }
        }
        return success;
    }
    
    /**
     * Method returns the inorder successor of the node
     * @param paNode
     * @return 
     */
    private Node<T> inorder_successor(Node<T> paNode) {
        paNode = paNode.getRightChild();
        while(paNode != null) {
            if(paNode.hasLeftChild()) {
                paNode = paNode.getLeftChild();
            } else {
                return paNode;
            }
        }
        return paNode;
    }
    
    /**
     * InOrder traversal
     * @return 
     */
    public LinkedList inOrder() {
        LinkedList<T> linked_list = new LinkedList<>();
        
        if(this.root != null) {
            Stack<Node<T>> stack = new Stack<>();
            Node<T> actual_node = this.root;
            
            while(actual_node != null || !stack.empty()) {
                if (actual_node != null) {      //idem do lava
                    stack.push(actual_node);
                    actual_node = actual_node.getLeftChild();
                } else {                        //idem do prava
                    actual_node = stack.pop();
                    linked_list.add(actual_node.getData());
                    actual_node = actual_node.getRightChild();
                }
            }
        }
        return linked_list;
    }
    
    /**
     * Splay operation
     * @param paNode 
     */
    private void splay(Node<T> paNode) {
        int type_of_child1 = -1;
        int type_of_child2 = -1;
        boolean node_isRoot = false;
        
        while(!node_isRoot) {           //while node isn't the root of the tree
            type_of_child1 = this.type_of_child(paNode);
            switch(type_of_child1) {
                case 0:                 //node hasn't got the parent
                    node_isRoot = true;
                    break;
                case 1:                 //node is left son
                    type_of_child2 = this.type_of_child(paNode.getParent());
                    switch(type_of_child2) {
                        case 0:                 //node has only 1 parent -> do 1 right rotation
                            paNode = this.right_rotation(paNode.getParent());
                            node_isRoot = true;
                            break;
                        case 1:                 //node is 2 times left son -> do 2 right rotations
                            paNode = this.right_rotation(paNode.getParent().getParent());
                            paNode = this.right_rotation(paNode);
                            break;
                        case 2:                 //node is left son and his parent is right son -> do 1 right and 1 left rotation
                            paNode = this.right_rotation(paNode.getParent());     
                            paNode = this.left_rotation(paNode.getParent());        
                            break;
                    }
                    break; 
                case 2:                //node is right son
                    type_of_child2 = this.type_of_child(paNode.getParent());
                    switch(type_of_child2) {
                        case 0:                 //node has only 1 parent -> do 1 left rotation
                            paNode = this.left_rotation(paNode.getParent());
                            node_isRoot = true;
                            break;
                        case 1:                 //node is right son and his parent is left son -> do 1 left and 1 right rotation
                            paNode = this.left_rotation(paNode.getParent());       
                            paNode = this.right_rotation(paNode.getParent());      
                            break;

                        case 2:                 //node is 2 times right son -> do 2 left rotations
                            paNode = this.left_rotation(paNode.getParent().getParent());
                            paNode = this.left_rotation(paNode);
                            break;
                    }
                    break; 
                default:
                    node_isRoot = true;
                    break;
            }
        }
        this.root = paNode;
    }
    
    /**
     * Left rotation of the tree
     * @param paNode
     * @return 
     */
    private Node<T> left_rotation(Node<T> paNode) {
        Node<T> parent_of_node = paNode.getParent();      //parent of requested node
        Node<T> new_node = paNode.getRightChild();        //new root of this subtree
        
        new_node.setParent(parent_of_node);
        if(parent_of_node != null) {
            parent_of_node.replaceChild(paNode.getData(), new_node);
        }
        
        paNode.setParent(new_node);  
        paNode.setRightChild(new_node.getLeftChild()); 
        if(new_node.hasLeftChild()) {
            paNode.getRightChild().setParent(paNode);
        }
        new_node.setLeftChild(paNode);  
        
        return new_node;  
    }
    
    /**
     * Right rotation of the tree
     * @param paNode
     * @return 
     */
    private Node<T> right_rotation(Node<T> paNode) {
        Node<T> parent_of_node = paNode.getParent();      //parent of requested node
        Node<T> new_node = paNode.getLeftChild();         //new root of this subtree
        
        new_node.setParent(parent_of_node);
        if(parent_of_node != null) {
            parent_of_node.replaceChild(paNode.getData(), new_node);
        }
        
        paNode.setParent(new_node);  
        paNode.setLeftChild(new_node.getRightChild());
        if(new_node.hasRightChild()) {
            paNode.getLeftChild().setParent(paNode);
        }
        new_node.setRightChild(paNode);
        
        return new_node;  
    }
    
    /**
     * Method returns the type of child
     * 0 - node hasn't got parent, 1 - left son, 2 - right son
     * @param paNode
     * @return 
     */
    private int type_of_child(Node<T> paNode) {
        if(!paNode.hasParent()) {
            return 0;                   //node hasn't gor parent   
        } else {
            Node<T> parent = paNode.getParent();
            if (parent.hasLeftChild() && parent.getLeftChild().getData().compareTo(paNode.getData()) == 0) {
                return 1;               //node is left son
            } else {
                return 2;               //node is right son
            }
        }
    }
}
