package arbolesPractica01;

public class Test {
    public static void main(String[] args) {
        BSTree<Integer> tree = new BSTree<>();
        BSTree<Integer> tree2 = new BSTree<>();
        try {
        	tree.insert(10);
        	tree.insert(5);
        	tree.insert(15);
        	tree.insert(3);
        	tree.insert(7);
            
            
            tree2.insert(8);
            tree2.insert(3);
            tree2.insert(10);
            tree2.insert(1);
            tree2.insert(6);
            
        } catch (ItemDuplicated e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Elementos del árbol en orden: ");
        System.out.println(tree.toString());
        try {
        	tree.remove(5);
        	System.out.println(tree.search(20));
        	System.out.println(tree.minRemove());
        	System.out.println(tree.countsNodes());
        	System.out.println(tree.searchHeight(3));
        	System.out.println(tree.countNodes(0));
        	System.out.println(tree.areaBST());
        }
        catch (ItemNoFound e) {
        	System.out.println(e.getMessage());
        }
        
        System.out.print("Elementos del árbol en orden: ");
        System.out.println(tree.toString());
        System.out.println(BSTree.sameArea(tree, tree2));
    }
}
