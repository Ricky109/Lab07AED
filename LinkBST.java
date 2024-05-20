package arbolesPractica01;

public interface LinkBST<E> {
	public void insert(E x) throws ItemDuplicated;
	public void remove(E x)throws ItemNoFound;
	public E search(E x) throws ItemNoFound;
	public boolean isEmpty();
}
