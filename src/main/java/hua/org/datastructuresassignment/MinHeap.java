package hua.org.datastructuresassignment;

public interface MinHeap<E extends Comparable<E>> {

	
	void insert(E elem);

	
	E findMin();

	
	E deleteMin();

	
	boolean isEmpty();

	
	int size();

	
	void clear();

}
