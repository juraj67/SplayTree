# SplayTree
Splay tree is a binary search tree with a Splay operation, that uses rotations. 
The Splay operation moves the element to the root of the tree after each search, insert and delete. 

# How to use it
Copy the package "splay_tree" into your project, import it and create new SplayTree object:
```java
import splay_tree.*;

SplayTree<Integer> splayTree = new SplayTree<>();
```
**Example**
```java
import splay_tree.*;
import java.util.LinkedList;

public class TestClass {
  public static void main(String[] args) {
    SplayTree<Integer> splayTree = new SplayTree<>();
    
    //insert 
    splayTree.insert(2);
    splayTree.insert(1);
    splayTree.insert(3);
    
    //find 
    System.out.println(splayTree.find(2)); //prints 2
    
    //delete 
    splayTree.delete(2); //removes 2 

    //inorder traversal
    LinkedList<Integer> linked = splayTree.inOrder();
    for(Integer i: linked) {
      System.out.println(i); //prints 1 and 3
    }
  }
}
```

# Author
**Juraj Pavlech, 2019**
