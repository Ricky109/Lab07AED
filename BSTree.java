package arbolesPractica01;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BSTree<E extends Comparable<E>> implements LinkBST<E> {
    protected class Node<E> {
    	public E data;
    	public Node<E> left, right;
        
        public Node(E data) {
            this(data, null, null);
        }
        
        public Node(E data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
    
    protected Node<E> root;
    
    public BSTree() {
        this.root = null;
    }
    
    public boolean isEmpty() {
        return this.root == null;
    }
    
    @Override
    public void insert(E x) throws ItemDuplicated {
    	
        Node<E> nodito = new Node<>(x);
        if (isEmpty()) {
            this.root = nodito;
        }else  {
            Node<E> current = root;
            Node<E> ant = null;
            while (current != null) {
                ant = current;
                // nos movemos asia los hijos
                if(x.compareTo(current.data)==0) {
                	 throw new ItemDuplicated();
                }else if (x.compareTo(current.data) > 0) {
                    current = current.right;
                }else {
                    current = current.left;
                }
            }
            //insertamos los nodos hijos
            if (x.compareTo(ant.data) > 0) {
                ant.right = nodito;
            } else {
                ant.left = nodito;
            }
        }
    }
    
    @Override
    public void remove(E x) throws ItemNoFound{
    	boolean isLeftChild =true;
		Node<E> P = null;
		Node<E> current = root;
    	if(isEmpty()) {
    		throw new ItemNoFound();
    	}else {
    		while(current!=null && !current.data.equals(x)) {
    			P = current;
    			if(x.compareTo(current.data)<0) {
    				isLeftChild = true;
    				current = current.left;
    			}else {
    				isLeftChild = false;
    				current = current.right;
    			}
    		}if(current == null)
    			throw new ItemNoFound();
    	}
    	//Caso 1
    	if(current.left==null && current.right==null) {
    		if (current == root) {
                root = null;
            } else if (isLeftChild) {
                P.left = null;
            } else {
                P.right = null;
            }
    	}
    	
    	//Caso 2
    	else if(current.left==null) {
    		if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                P.left = current.left;
            } else {
                P.right = current.left;
            }
    	}else if (current.left == null) { // Only right child
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                P.left = current.right;
            } else {
                P.right = current.right;
            }
    	}
    	//Caso 3
    	else {
            Node<E> successor = subRemove(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                P.left = successor;
            } else {
                P.right = successor;
            }
            successor.left = current.left;
        }
    }

    private Node<E> subRemove(Node<E> node) {
        Node<E> padre = node;
        Node<E> sucesor = node.right;
        while (sucesor.left != null) {
        	padre = sucesor;
        	sucesor = sucesor.left;
        }
        if (sucesor != node.right) {
        	padre.left = sucesor.right;
        	sucesor.right = node.right;
        }
        return sucesor;
    }
    
    @Override
    public E search(E x) throws ItemNoFound{
    	if (isEmpty())
            throw new ItemNoFound();
    	Node<E> current = root;
    	while(current != null) {
    		if(x.compareTo(current.data)>0) {
    			current = current.right;
    		}else if(x.compareTo(current.data)<0) {
    			current = current.left;
    		}else {
    			return current.data;
    		}
    	}throw new ItemNoFound();
    	
    }
    
    public E minRemove() {
    	Node<E> prov = null;
		try {
			prov = minRemove(root);
		} catch (ItemNoFound e) {
			System.out.println(e.getMessage());
		}
    	return prov.data;
    }
    
    public Node<E> minRemove(Node<E> n)throws ItemNoFound{
    	if(isEmpty())
    		throw new ItemNoFound();
    	Node<E> current = n;
    	while(current.left!=null) {
    		current = current.left;
    	}return current;
    }
    
    //Ejercicio 01
    public int countsNodes() throws ItemNoFound{
    	if(isEmpty())
    		throw new ItemNoFound();
    	return countsNodes(root);
    	
    }
    
    private int countsNodes(Node<E> nod) {
    	if(nod==null)
    		return 0;
    	
    	if (nod.left == null && nod.right == null) {
            return 0;
        }
    	int cont = 1;
    	cont+=countsNodes(nod.left);
    	cont+=countsNodes(nod.right);
    	return cont;
    }
    
    public int searchHeight(E x) throws ItemNoFound{
    	if (isEmpty())
            throw new ItemNoFound();
    	Node<E> current = root;
    	while(current != null) {
    		if(x.compareTo(current.data)>0) {
    			current = current.right;
    		}else if(x.compareTo(current.data)<0) {
    			current = current.left;
    		}else {
    			return height(current);
    		}
    	}throw new ItemNoFound();
    }
       
    private int height(Node n)throws ItemNoFound {
    	int de=0;
    	int iz=0;
   	 	if(n.left==null && n.right==null) {
   	 		return 0;
   	 	}else if(n.left==null) {
   	 		de+=height(n.right);
   	 	}else {
   	 		iz+=height(n.left);
   	 	}
   	 	return 1+Math.max(de,iz);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        inOrderTraversal(root, sb);
        return sb.toString();
    }

    private void inOrderTraversal(Node<E> node, StringBuilder sb) {
        if (node != null) {
            inOrderTraversal(node.left, sb);
            sb.append(node.data).append(" ");
            inOrderTraversal(node.right, sb);
        }
    }
    
    //Ejercicio 1
    public int countNodes(int level) throws ItemNoFound{
        if (isEmpty()) {
            throw new ItemNoFound();
        }
        if (level < 0) {
            return 0; // Nivel inválido
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int currentLevel = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            if (currentLevel == level) {
                return size; // El número de nodos en el nivel actual
            }
            // Procesar todos los nodos en el nivel actual
            for (int i = 0; i < size; i++) {
                Node<E> node = queue.poll();
                // Agregar los hijos del nodo a la cola
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            currentLevel++;
        }
        return 0;
    }
    
    //Ejercicio 2.1
    public int areaBST() {
    	int treeHeight = 0;
        int leafCount = countLeaves(root);
        try {
        	treeHeight = height(root);
        }catch (ItemNoFound e) {
        	System.out.println(e.getMessage());
        }
        
        return leafCount * treeHeight;
    }
    
    private int countLeaves(Node<E> node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return countLeaves(node.left) + countLeaves(node.right);
    }
    
    //Ejercicio 2.2
    public static <E extends Comparable<E>> boolean sameArea(BSTree<E> tree1, BSTree<E> tree2) {
        return tree1.areaBST() == tree2.areaBST();
    }
    
    //Ejercicio 3 
    public void iterativePreOrden() {

            if (isEmpty()) {
                System.out.println("El árbol está vacío");
                return;
            }

            Stack<Node<E>> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                Node<E> current = stack.pop();
                System.out.print(current.data + " ");
                if (current.right != null) {
                    stack.push(current.right);
                }
                if (current.left != null) {
                    stack.push(current.left);
                }
            }
        }

        //Ejercicio 4

        public int countNodes() {
            return countNodes(root);
        }

        private int countNodes(Node<E> node) {
            if (node == null) {
                return 0;
            } else {
                return 1 + countNodes(node.left) + countNodes(node.right);

            }
        }
        
    
}
