package arbolesPractica01;

public class Test {
    public static void main(String[] args) {
        BSTree<Integer> tree = new BSTree<>();

        try {
            tree.insert(10);
            tree.insert(5);
            tree.insert(20);
            tree.insert(3);
            tree.insert(7);
            tree.insert(15);
            tree.insert(25);
            tree.insert(1);
            tree.insert(9);
            
            // Intentamos insertar un duplicado
            tree.insert(10);
            
        } catch (ItemDuplicated e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Elementos del árbol en orden: ");
        System.out.println(tree.toString());
        try {
        	tree.remove(5);
        	System.out.println(tree.search(20));
        	System.out.println(tree.minRemove());
        }
        catch (ItemNoFound e) {
        	System.out.println(e.getMessage());
        }
        
        System.out.print("Elementos del árbol en orden: ");
        System.out.println(tree.toString());
    }
}
