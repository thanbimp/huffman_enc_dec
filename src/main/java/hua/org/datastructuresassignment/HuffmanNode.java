package hua.org.datastructuresassignment;
import java.io.Serializable;


public  class HuffmanNode implements Comparable<HuffmanNode> ,Serializable{
    public char c;
    public  int data;

    public HuffmanNode left;
    public HuffmanNode right;

    public int compareTo(HuffmanNode O) {
        return this.data - O.data;
    }

 
}
