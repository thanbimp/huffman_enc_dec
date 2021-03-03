/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hua.org.datastructuresassignment;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;


public class Decoder {

    public static int decode(HuffmanNode root, int index, StringBuilder sb,PrintWriter outFile) {
        if (root == null) {
            return index;
        }

        // Found a leaf node
        if (root.left == null && root.right == null && root.c != 129) {
            outFile.append(root.c);
            return index;
        }

        index++;


        if (sb.charAt(index) == '0') {
            root = root.left;
        } else {
            root = root.right;
        }

        index = decode(root, index, sb,outFile);
        return index;
    }


    public static void writeDecFile(Path inputFile, HuffmanTree tree, Path outputFile) throws FileNotFoundException, IOException {

        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile.toString()));

        FileInputStream fin = new FileInputStream(inputFile.toString());
        DataInputStream din = new DataInputStream(fin);
        int loadedCounter = din.readInt();
        byte[] loadedFile = new byte[(int) inputFile.toFile().length()];
        loadedFile = din.readAllBytes();

        BitSet set = BitSet.valueOf(loadedFile);
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < loadedCounter; i++) {
            if (set.get(i)) {
                binaryString.append("1");
            } else {
                binaryString.append("0");
            }
        }
        int index = -1;
        while (index < binaryString.length() - 1) {
            index = decode(tree.root, index, binaryString,outFile);
        }
        outFile.close();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Path inputFileName = Paths.get(args[0]);
        Path outputFileName = Paths.get(args[1]);
        System.out.println("Starting decoding proccess...");
        ObjectInputStream OIStream = new ObjectInputStream(new FileInputStream("tree.dat"));
        HuffmanTree loadedTree;
        loadedTree = (HuffmanTree) OIStream.readObject();
        writeDecFile(inputFileName, loadedTree, outputFileName);
        System.out.println("File: "+outputFileName+" has been written successfully!");
    }

}
