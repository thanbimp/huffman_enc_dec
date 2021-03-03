package hua.org.datastructuresassignment;

import java.util.NoSuchElementException;

public class ArrayMinHeap<E extends Comparable<E>> implements MinHeap<E> {

    public static final int DEFAULT_CAPACITY = 64;

    private E[] array;
    private int size;

    public ArrayMinHeap() {
        this.size = 0;
        this.array = (E[]) new Comparable[DEFAULT_CAPACITY + 1];
    }

    public ArrayMinHeap(E[] array) {
        int n = array.length;
        this.array = (E[]) new Comparable[n + 1];
        this.size = n;
        
        System.arraycopy(array, 0, this.array, 1, n);
        for(int i = this.array.length/2;i>0;i--){
        fixdown(i);
    }
    }

    public void insert(E elem) {
        if (size + 1 >= array.length) {
            doubleCapacity();
        }
        array[++size] = elem;
        fixup(size);
    }

    public E findMin() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        return array[1];
    }


    public E deleteMin() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        E result = array[1];
        array[1] =array[size];
        array[size] = null;
        size--;
        fixdown(1);

        return result;
    }


    public boolean isEmpty() {
        return size == 0;
    }



    public int size() {

        return size;
    }



    public void clear() {
        for(int i= 1;i<=size;i++){
            array[i]= null;
        }
        size = 0;

    }

    private void swap(int i, int j) {
        E tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private void fixup(int k) {
        assert k >= 1 && k <= size;
        while (k > 1 && array[k].compareTo(array[k / 2]) < 0) {
            swap(k, k / 2);
            k = k / 2;
        }

    }
    
    private void fixdown(int k){
        while(2*k <=size){
            int j= 2*k;
            if(j+1<=size && array[j+1].compareTo(array[j])<0){
                j++;
            }
            if(array[k].compareTo(array[j])<=0){
                break;
            }
            swap(k,j);
            k = j;
        }
           
    }

    private void doubleCapacity() {
        int newCapacity = (array.length - 1) * 2;
        E[] newArray = (E[]) new Comparable[newCapacity + 1];
        for (int i = 1; i <= size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

}
