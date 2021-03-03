package hua.org.datastructuresassignment;

import java.io.*;  // Import the File class
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.BitSet;

public class Encoder {

    static int totalChars;

    private static void calculateASCIIChars(int[] frequencyCounter, String data) {

        int x;

        for (int i = 0; i < data.length(); i++) {
            x = data.charAt(i);
            if (x >= 0 && x <= 128) {
                frequencyCounter[x]++;
                totalChars = totalChars + 1;
            }
        }
    }

    public static void printCode(HuffmanNode root, String s, OutputStreamWriter outFile) throws IOException {
        if (root.left == null && root.right == null && root.c != 129) {
            outFile.append(root.c + "\u0091" + s + "\u0091"); //\u0091 is used as a seperator since it is reserved for private use
            return;
        }
        printCode(root.left, s + "0", outFile);
        printCode(root.right, s + "1", outFile);
    }

    public static String[][] readCodes() throws IOException {

        Path codeFileName = Paths.get("codes.dat");
        String codesFile = Files.readString(codeFileName, StandardCharsets.UTF_8);
        String[] codes = new String[256];
        if (codesFile.contains("\u0091")) {
            codes = codesFile.split("\u0091");
        }
        String[][] byteCodes = new String[codes.length / 2][2];
        int j = 0;
        for (int i = 0; i < codes.length; i = i + 2) {
            byteCodes[j][0] = codes[i];
            byteCodes[j][1] = codes[i + 1];
            j++;
        }
        return byteCodes;
    }

    public static void writeEncFile(Path inputFile, Path outputFile) throws IOException {
        String inputFileString = Files.readString(inputFile, StandardCharsets.UTF_8);
        String[][] codesArr = readCodes();
        StringBuilder encoded = new StringBuilder();
        System.out.println("Encoding,this may appear stuck,please wait");
        for (int i = 0; i < inputFileString.length(); i++) {
            for (String[] codesArr1 : codesArr) {
                if (inputFileString.charAt(i) == codesArr1[0].toCharArray()[0]) {
                    encoded.append(codesArr1[1]);
                }
            }
        }

        BitSet bitSet = new BitSet(encoded.length());
        Integer bitcounter = 0;
        for (Character c : encoded.toString().toCharArray()) {
            if (c.equals('1')) {
                bitSet.set(bitcounter);
            }
            bitcounter++;
        }

        System.out.println("Writing Encoded File");
        FileOutputStream fos = new FileOutputStream(outputFile.toString());
        DataOutputStream dos = new DataOutputStream(fos);
        dos.writeInt(bitSet.length());
        dos.write(bitSet.toByteArray());
        System.out.println("File: " + outputFile + " has been written successfully!");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Path inputFileName = Paths.get(args[0]);
        Path outputFileName = Paths.get(args[1]);


            System.out.println("txt file given as input,starting encoding process...");
            totalChars = 0;
            int[] frequencyCounter = new int[128];
            String data = Files.readString(inputFileName);
            calculateASCIIChars(frequencyCounter, data);
            FileOutputStream datFile = new FileOutputStream("frequencies.dat");
            ObjectOutputStream OOStream = new ObjectOutputStream(datFile);
            OOStream.writeObject(frequencyCounter);
            OOStream.writeObject(totalChars);
            OOStream.close();
            datFile.close();
            System.out.println("frequencies.dat written successfully");
            ObjectInputStream OIStream = new ObjectInputStream(new FileInputStream("frequencies.dat"));


            int[] loadedArray;
            int loadedCounter;
            loadedArray = (int[]) OIStream.readObject();
            loadedCounter = (int) OIStream.readObject();
            OIStream.close();
            HuffmanTree tree = new HuffmanTree(loadedArray, loadedCounter);
            datFile = new FileOutputStream("tree.dat");
            OOStream = new ObjectOutputStream(datFile);
            OOStream.writeObject(tree);
            OOStream.close();
            datFile.close();
            System.out.println("tree.dat written successfully");


            OIStream = new ObjectInputStream(new FileInputStream("tree.dat"));
            HuffmanTree loadedTree;
            loadedTree = (HuffmanTree) OIStream.readObject();
            OutputStreamWriter codesFile = new OutputStreamWriter(new FileOutputStream("codes.dat"), StandardCharsets.UTF_8);
            printCode(loadedTree.root, "", codesFile);
            codesFile.close();
            System.out.println("codes.dat written successfully");


            writeEncFile(inputFileName, outputFileName);
        }
    }
