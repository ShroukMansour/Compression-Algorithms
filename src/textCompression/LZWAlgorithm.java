package textCompression;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class LZWAlgorithm {
    private Vector<Integer> tags = new Vector<Integer>();
    private Map<String, Integer> compressDict = new HashMap<String, Integer>();
    private Map<Integer, String> decompressDict = new HashMap<Integer, String>();


    public LZWAlgorithm() {
        for (int i = 32; i < 128; i++) {
            compressDict.put(Character.toString((char) i), i);
            decompressDict.put(i, Character.toString((char) i));
        }
    }

    public void compress(String text) {

        int subStrSz, idx = 65, compressionKey = 128;
        String subStr;
        boolean compressionFinished = false;

        for (int i = 0; i < text.length() && !compressionFinished; ) {
            subStrSz = i + 1;
            while (true) {
                if (subStrSz > text.length()) {
                    compressionFinished = true;
                    tags.add(idx);
                    break;
                }
                subStr = text.substring(i, subStrSz);
                if (compressDict.containsKey(subStr)) {
                    idx = compressDict.get(subStr);
                    subStrSz++;
                } else if (!compressDict.containsKey(subStr)) {
                    compressDict.put(subStr, compressionKey);
                    tags.add(idx);
                    i = subStrSz - 1;
                    compressionKey++;
                    break;
                }
            }
        }
        writeTagsToFile();
    }

    public void decompress() throws FileNotFoundException, IOException {

        int decompressionKey = 128;
        String text = "";
        String lastDecompressed = "", decompressed;
        for (Integer tag : tags) {
            if (lastDecompressed.isEmpty()) {
                lastDecompressed = decompressDict.get(tag);
                text = lastDecompressed;
            } else {
                if (decompressDict.get(tag) == null) {
                    decompressDict.put(tag, lastDecompressed + lastDecompressed.charAt(0));
                }
                decompressed = decompressDict.get(tag);
                decompressDict.put(decompressionKey, lastDecompressed + decompressed.charAt(0));
                lastDecompressed = decompressed;
                text += decompressed;
                decompressionKey++;
            }
        }

        DataOutputStream os = new DataOutputStream(
                new FileOutputStream("E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\decompressedLZW.txt"));
        os.writeBytes(text);
        os.close();

        System.out.println(text);

    }


    public void readFile(String fileName) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        System.out.println(fileName);
        byte[] data;
        try (FileInputStream fis = new FileInputStream(file)) {
            data = new byte[(int) file.length()];
            fis.read(data);
        }
        String text = new String(data, "UTF-8");
        compress(text);
    }


    public void writeTagsToFile() {

        try {
            DataOutputStream os = new DataOutputStream(new
                    FileOutputStream("E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compressedTagsLZW.txt"));
            for (Integer tag : tags) {
                os.write(tag);
                System.out.println(tag);
            }
            System.out.println("Done");
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readTagsFromFile() throws FileNotFoundException, EOFException, IOException {
        tags.clear();
        DataInputStream is = new DataInputStream(
                new FileInputStream("E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compressedTagsLZW.txt"));

        try {
            while (true) {
                Integer tag = is.read();
                if (tag == -1)
                    break;
                tags.add(tag);
                System.out.println(tag);
            }
        } catch (EOFException e) {
            e.printStackTrace();
        }
        decompress();
    }

}
