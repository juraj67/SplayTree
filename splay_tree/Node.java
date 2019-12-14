package splay_tree;

/**
 *
 * @author juraj67
 * @param <T>
 */
public class Node<T extends Comparable<T>> {
    private Node parent;
    private Node leftChild;
    private Node rightChild;
    private T data;
    
    /**
     * Constructor creates an empty Node
     */
    public Node() {
    }
    
    /**
     * Constructor creates a new Node
     * @param data 
     */
    public Node(T data) {
        this.data = data;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }
    
    /**
     * Copy constructor makes a copy of Node
     * @param nodeToCopy 
     */
    public Node(Node<T> nodeToCopy) {
        this.data = nodeToCopy.getData();
        this.parent = nodeToCopy.getParent();
        this.leftChild = nodeToCopy.getLeftChild();
        this.rightChild = nodeToCopy.getRightChild();
    }
    
    /**
     * Returns the parent of the node
     * @return 
     */
    public Node getParent() {
        return this.parent;
    }
    
    /**
     * Returns the left child of the node
     * @return 
     */
    public Node getLeftChild() {
        return this.leftChild;
    }
    
    /**
     * Returns the right child of the node
     * @return 
     */
    public Node getRightChild() {
        return this.rightChild;
    }
    
    /**
     * Method returns the splay node data
     * @return 
     */
    public T getData() {
        return this.data;
    }
    
    /**
     * Method sets parent of the tree 
     * @param parent 
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    /**
     * Method sets left child of the tree 
     * @param leftChild 
     */
    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }
    
    /**
     * Method sets right child of the tree 
     * @param rightChild 
     */
    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
    
    /**
     * Method returns true if the node has a left child
     * @return 
     */
    public boolean hasLeftChild() {
        return (this.leftChild == null ? false : true);
    }
    
    /**
     * Method returns true if the node has a right child
     * @return 
     */
    public boolean hasRightChild() {
        return (this.rightChild == null ? false : true);
    }
    
    /**
     * Method returns true if the node is a leaf
     * @return 
     */
    public boolean isLeaf() { 
        return (!this.hasLeftChild() && !this.hasRightChild());
    }
    
    /**
     * Method removes a node that matches the criteria
     * @param paCriterion 
     */
    public void removeChild(T paCriterion) {
        if (this.getData().compareTo(paCriterion) > 0) {
            this.leftChild = null;
        } else if(this.getData().compareTo(paCriterion) < 0) {
            this.rightChild = null;
        } 
    }
    
    /**
     * Method replaces a node that matches the criteria
     * @param paCriterion
     * @param paReplaceNode 
     */
    public void replaceChild(T paCriterion, Node paReplaceNode) {
        if (this.getData().compareTo(paCriterion) > 0) {
            this.leftChild = paReplaceNode;
        } else if(this.getData().compareTo(paCriterion) < 0) {
            this.rightChild = paReplaceNode;
        } 
    }
    
    /**
     * Method returns true, if the node has only one child
     * @return 
     */
    public boolean hasOnlyOneChild() {
        if(this.leftChild == null && this.rightChild != null) {
            return true;
        } else if(this.leftChild != null && this.rightChild == null){
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Method returns true, if the node has got a parent
     * @return 
     */
    public boolean hasParent() {
        return (this.parent != null);
    }
}
