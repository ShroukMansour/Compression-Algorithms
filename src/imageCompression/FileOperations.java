package imageCompression;

import java.io.*;
import java.util.ArrayList;

public class FileOperations {


    public void writeWidthHieghtNQ(int imgWidth, int imgHieght) throws IOException {
        String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compImgNQ.txt";
        File file = new File(fileName);
        file.createNewFile();
        DataOutputStream os;
        os = new DataOutputStream(new FileOutputStream(file, true));
        os.writeInt(imgWidth);
        os.writeInt(imgHieght);
    }

    public void writeCompImgNQ(ArrayList<Integer> compressedImg) throws IOException {
        String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compImgNQ.txt";
        File file = new File(fileName);
        file.createNewFile();
        DataOutputStream os;
        os = new DataOutputStream(new FileOutputStream(file, true));
        try {
            for (int i = 0; i < compressedImg.size(); i++) {
                os.writeInt(compressedImg.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        os.close();
    }

    public int[][] readCompImgNQ(int numOfLevels, int width, int hieght) throws IOException {
        String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compImgNQ.txt";
        int[][] compImg = new int[width][hieght];
        int compVal;
        DataInputStream is = new DataInputStream(new FileInputStream(fileName));
        is.skipBytes( numOfLevels * Integer.BYTES + 3 * Integer.BYTES);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < hieght; j++) {
                compVal = is.readInt();
                compImg[i][j] = compVal;
            }
        }
        is.close();
        return compImg;
    }


    public int readImgWidthNQ(int numOfLevels) throws IOException {
        String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compImgNQ.txt";
        int width;
        DataInputStream is = new DataInputStream(new FileInputStream(fileName));
        is.skipBytes(Integer.BYTES + numOfLevels * Integer.BYTES);
        width = is.readInt();
        is.close();
        return width;
    }

    public int readImgHieghtNQ(int numOfLevels) throws IOException {
        String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compImgNQ.txt";
        int hight;
        DataInputStream is = new DataInputStream(new FileInputStream(fileName));
        is.skipBytes( numOfLevels * Integer.BYTES + 2 * Integer.BYTES);
        hight = is.readInt();
        is.close();
        return hight;
    }

    public void writeSplitNQ(ArrayList<Integer> split) throws IOException {
        // TODO Auto-generated method stub
        String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compImgNQ.txt";
        File file = new File(fileName);
        file.createNewFile();
        DataOutputStream os;
        os = new DataOutputStream(new FileOutputStream(file));
        os.writeInt(split.size());
        try {
            for (int i = 0; i < split.size(); i++) {
                os.writeInt(split.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        os.close();
    }

    public ArrayList<Integer> readSplitNQ() throws IOException {
        // TODO Auto-generated method stub
        ArrayList<Integer> split = new ArrayList<Integer>();
        Integer splitVal;
        String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compImgNQ.txt";
        DataInputStream is = new DataInputStream(new FileInputStream(fileName));
        int numOfLevels = is.readInt();
        for (int j = 0; j < numOfLevels; j++) {
            splitVal = is.readInt();
            System.out.println(splitVal + "num: " + numOfLevels);
            split.add(splitVal);
        }
        is.close();
        return split;
    }

    public void writeNumOfLevelsNQ(int numOfLevels) throws IOException {
        // TODO Auto-generated method stub
        String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compImgNQ.txt";
        File file = new File(fileName);
        file.createNewFile();
        DataOutputStream os;
        os = new DataOutputStream(new FileOutputStream(file));
        try {
            os.writeInt(numOfLevels);

        } catch (IOException e) {
            e.printStackTrace();
        }
        os.close();
    }

    public int readNumOfLevelsNQ() throws IOException {
        String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compImgNQ.txt";
        int numOfLevels;
        DataInputStream is = new DataInputStream(new FileInputStream(fileName));
        numOfLevels = is.readInt();
        is.close();
        return numOfLevels;
    }

	public void writeAssetsFF(int numOfLevels, int imgWidth, int imgHieght) throws IOException {
		String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compressedImgFF.txt";	
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		file.writeInt(numOfLevels);
		file.writeInt(imgWidth);
		file.writeInt(imgHieght);
		file.close();
	}

	public void writeCompImgFF(ArrayList<ArrayList<Integer>> compressedImg) throws IOException {
		String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compressedImgFF.txt";	
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		file.seek(file.length());
		for (int i = 0; i < compressedImg.size(); i++) {
			for (int j = 0; j < compressedImg.get(0).size(); j++) {
				file.writeInt(compressedImg.get(i).get(j));
			}
		}
		file.close();
	}

	public int readNumOfLevelsFF() throws IOException {
		String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compressedImgFF.txt";	
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		int num = file.readInt();
		return num;
	}

	public ArrayList<ArrayList<Integer>> readCompImgFF() throws IOException {
		String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compressedImgFF.txt";	
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		file.seek(Integer.BYTES);
		int width = file.readInt();
		int hight = file.readInt();
		ArrayList<ArrayList<Integer>> compresedImg = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < hight; i++) {
			ArrayList<Integer> compressedRow = new ArrayList<Integer>();
			for (int j = 0; j < width; j++) {
				compressedRow.add(file.readInt());
			}
			compresedImg.add(compressedRow);
		}
		return compresedImg;
	}
}








