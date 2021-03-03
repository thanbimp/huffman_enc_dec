package hua.org.datastructuresassignment;
import java.io.IOException;
import java.io.Serializable;

public class HuffmanTree  implements Serializable{

    HuffmanNode root;

    public HuffmanTree  (int[] freqArray,int charCount) throws IOException{
        this.root = null;
        ArrayMinHeap<HuffmanNode> heap = new ArrayMinHeap<>();
        for (int i = 0; i < freqArray.length;i++){

            HuffmanNode hn = new HuffmanNode();
            hn.data = freqArray[i];
            hn.c = (char)i;
            hn.left = null;
            hn.right = null;
            heap.insert(hn);
        }
        
        while (heap.size() > 1) {
        HuffmanNode a = heap.findMin();
        heap.deleteMin();
        HuffmanNode b = heap.findMin();
        heap.deleteMin();
        HuffmanNode c = new HuffmanNode();
        c.data = a.data + b.data;
        c.c = 129; //out of ASCII range,used to determine if leaf
        c.left = a;
        c.right = b;
        root = c;
        heap.insert(c);
        }
    }
}
 