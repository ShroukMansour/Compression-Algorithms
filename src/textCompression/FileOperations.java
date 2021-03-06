package textCompression;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import assets.Probability;

public class FileOperations {

    String binaryTextStdHuff;


    public void writeDataToFile(String fileName, String data) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        DataOutputStream os = new DataOutputStream(new FileOutputStream(file));
        try {
            os.writeBytes(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        os.close();
    }


    public void writeProbListToFile(List<Probability> probsList, String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        DataOutputStream os = new DataOutputStream(new FileOutputStream(file));
        os.write(probsList.size());
        for (Probability p : probsList) {
            os.writeChar(p.getLetter());
            os.writeDouble(p.getProb());
        }
        os.close();
    }


    public void writeStdCompressedData(Map<String, String> codes, String text) throws IOException {

        File file = new File("E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compressStdHuff.txt");
        file.createNewFile();
        DataOutputStream os = new DataOutputStream(new FileOutputStream(file, true));
        char letter;
        String binary;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            binary = codes.get(Character.toString(letter));
            os.writeByte(Integer.parseInt(binary));
        }
        os.close();
    }


    public String readFile(String fileName) throws FileNotFoundException, IOException {

        File file = new File(fileName);
        byte[] data;
        try (FileInputStream fis = new FileInputStream(file)) {
            data = new byte[(int) file.length()];
            fis.read(data);
        }
        String text = new String(data, "UTF-8");
        return text;

    }


    public List<Probability> readProbsListFromFile(String fileName) throws IOException {

        // TODO Auto-generated method stub
        List<Probability> probList = new ArrayList<Probability>();
        DataInputStream is = new DataInputStream(new FileInputStream(fileName));
        int sz = is.read();
        try {
            while (true) {
                Character letter = is.readChar();
                double prob = is.readDouble();
                Probability p = new Probability(letter, prob);
                probList.add(p);
                sz--;
                if (sz == 0)
                    break;
            }
        } catch (EOFException e) {
            e.printStackTrace();
        }
        for (Probability p : probList) {
            System.out.println(p.getLetter() + " " + p.getProb());
        }
        int avail = is.available();
        byte[] data = new byte[avail];
        is.read(data);
        binaryTextStdHuff = new String(data);
        is.close();
        return probList;
    }


    public String readBinaryDataStdHuff() {
        return binaryTextStdHuff;
    }


    public void writeCompValue(Double compressedValue, String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        DataOutputStream os = new DataOutputStream(new FileOutputStream(file));
        os.writeDouble(compressedValue);
        os.close();
    }


    public Double readCompValue(String fileName) throws IOException {
        DataInputStream is = new DataInputStream(new FileInputStream(fileName));
        Double compVal = is.readDouble();
        is.close();
        return compVal;
    }


    public int readFileLength(String fileName) throws IOException {
        DataInputStream is = new DataInputStream(new FileInputStream(fileName));
        is.skipBytes(Double.BYTES);
        int fileLength = is.readInt();
        is.close();
        return fileLength;
    }


    public void writeFileLength(int length, String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        DataOutputStream os = new DataOutputStream(new FileOutputStream(file, true));
        os.writeInt(length);
        os.close();
    }


    public List<Probability> readProbsListArth(String fileName) throws IOException {
        DataInputStream is = new DataInputStream(new FileInputStream(fileName));
        is.skipBytes(Double.BYTES + Integer.BYTES);
        List<Probability> probList = new ArrayList<Probability>();
        int sz = is.read();
        try {
            while (true) {
                Character letter = is.readChar();
                double prob = is.readDouble();
                Probability p = new Probability(letter, prob);
                probList.add(p);
                sz--;
                if (sz == 0)
                    break;
            }
        } catch (EOFException e) {
            e.printStackTrace();
        }
        int avail = is.available();
        byte[] data = new byte[avail];
        is.read(data);
        is.close();
        return probList;
    }


    public void writeProbListArth(List<Probability> probsList, String fileName) throws IOException {
        // TODO Auto-generated method stub
        File file = new File(fileName);
        file.createNewFile();
        DataOutputStream os = new DataOutputStream(new FileOutputStream(file, true));
        os.write(probsList.size());
        for (Probability p : probsList) {
            os.writeChar(p.getLetter());
            os.writeDouble(p.getProb());
        }
        os.close();
    }


}
