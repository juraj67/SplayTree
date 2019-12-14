import splay_tree.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;


/**
 *
 * @author juraj67
 */
public class SplayTree_Tests {
    
    private SplayTree<Integer> tree;
    private int countDelete;
    private int CountInsert;
    private int CountWrongDelete;
    private int actualCount;
    private int countFind;
    private int countWrongFind;
    
    private LinkedList<Integer> linked_list;
    private ArrayList<Integer> array_list;

    private Random rand;
    
    public SplayTree_Tests() {
        this.array_list = new ArrayList<>();
        this.linked_list = new LinkedList<>();
        this.tree = new SplayTree<>();
        
        this.countFind = 0;
        this.countWrongFind = 0;
        this.actualCount = 0;
        this.countDelete = 0;
        this.CountInsert = 0;
        this.CountWrongDelete = 0;
        
        this.rand = new Random();
    }
    
    public void testing(int myCount) {
        for (int i = 0; i < myCount; i++) {
            
            double d = rand.nextDouble();
            
            if (d > 0.6) {
                insrt();
            } else if(d > 0.2){
                dlt();
            } else {
                fnd();
            }
            
            if((i+1) == myCount) {
                System.out.println("\nNumber of operations: " + (i+1));
                this.compare();
            }
        }
        
    }
    
    public boolean insrt() {
        int numb = rand.nextInt(100000);
        
        if (tree.insert(numb)) {
            array_list.add(numb);
            CountInsert++;
            actualCount++;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean dlt() {
        if(actualCount > 0) {
            int x = actualCount - 1;
            int numb = 0;
            if(x > 0) {
                numb = rand.nextInt(x);
            }

            int to_delete = array_list.get(numb);

            if (tree.delete(to_delete)) {
                actualCount--;
                array_list.remove((Object)to_delete);
                countDelete++;
                return true;
            } else {
                CountWrongDelete++;
                return false;
            }
        }
        return false;
    }
    
    public void fnd() {
        if(actualCount > 0) {
            int x = actualCount - 1;
            int numb = 0;
            if(x > 0) {
                numb = rand.nextInt(x);
            }

            int to_find = array_list.get(numb);
            Comparable node = tree.find(to_find);
            if(node.compareTo(to_find) == 0 && array_list.contains(to_find)) {
                countFind++;
            } else {
                countWrongFind++;
            }
        }
    }

    public int getCountFind() {
        return countFind;
    }

    public int getCountWrongFind() {
        return countWrongFind;
    }
    
    public int countArray() {
        return array_list.size();
    }

    public int getCountDelete() {
        return countDelete;
    }

    public int getCountInsert() {
        return CountInsert;
    }

    public int getCountWrongDelete() {
        return CountWrongDelete;
    }
    
    public int getCountTree() {
        linked_list = tree.inOrder();
        return linked_list.size();
    }
    
    public void compare() {
        linked_list = tree.inOrder();    
        Collections.sort(array_list);   
        
        System.out.println("\nSplay tree items:");
        for (Integer integer : linked_list) {
            System.out.print(integer + ", ");
        }
        System.out.println("\n\nArrayList items:");
        for (Integer integer : array_list) {
            System.out.print(integer + ", ");
        }
        System.out.println("\n\nNumber of items in tree: " + this.getCountTree());
        System.out.println("Number of items in ArrayList: " + this.countArray());
        System.out.println("Items in the splay tree and in the ArrayList are equal: " + linked_list.equals(array_list));
    }
    
    public static void main(String[] args) {
        
        SplayTree_Tests test = new SplayTree_Tests();
        
        test.testing(1000000);
        
        System.out.println("\nNumber of successful insertion: " + test.getCountInsert());
        System.out.println("Number of successful deletions: " + test.getCountDelete());
        System.out.println("Number of unsuccessful deletions: "+ test.getCountWrongDelete());
        System.out.println("Number of successful searching: " + test.getCountFind());
        System.out.println("Number of unsuccessful searching: " + test.getCountWrongFind());
        

    }
    
}
