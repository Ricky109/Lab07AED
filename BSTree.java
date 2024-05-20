package arbolesPractica01;

public class BSTree<E extends Comparable<E>> implements LinkBST<E> {
    class Node<E> {
        protected E data;
        protected Node left, right;
        
        public Node(E data) {
            this(data, null, null);
        }
        
        public Node(E data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
    
    private Node<E> root;
    
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
    
    private Node<E> minRemove(Node<E> n)throws ItemNoFound{
    	if(isEmpty())
    		throw new ItemNoFound();
    	Node<E> current = n;
    	while(current.left!=null) {
    		current = current.left;
    	}return current;
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
    
}
