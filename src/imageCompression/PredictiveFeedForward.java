package imageCompression;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import assets.ImageClass;
import assets.Quantizer;

public class PredictiveFeedForward {

    ArrayList<Quantizer> quantizer = new ArrayList<Quantizer>();
    int[][] imgMatrix;
    int[][] decompImgMatrix;

    ArrayList<ArrayList<Integer>> compressedImg = new ArrayList<ArrayList<Integer>>();
    int imgHieght, imgWidth, numOfLevels;

    FileOperations fileOps = new FileOperations();

    public void compress() throws IOException {
        imgHieght = imgMatrix.length;
        imgWidth = imgMatrix[0].length;
        constructQuantizer(numOfLevels);
        for (int i = 0; i < imgHieght; i++) {
            ArrayList<Integer> compressedRow = new ArrayList<Integer>();
            for (int j = 0; j < imgWidth; j++) {
                if (j == 0) {
                    compressedRow.add(imgMatrix[i][j]);
                } else {
                    int diff = imgMatrix[i][j] - imgMatrix[i][j - 1]; // 155 155 156 15
                    int q = findQ(diff);
                    compressedRow.add(q);
                }
            }
            compressedImg.add(compressedRow);
        }
        fileOps.writeAssetsFF(numOfLevels, imgWidth, imgHieght);
        fileOps.writeCompImgFF(compressedImg);
        decompress();
    }

    public void decompress() throws IOException {
        numOfLevels = fileOps.readNumOfLevelsFF();
        compressedImg = fileOps.readCompImgFF();
        quantizer.clear();
        constructQuantizer(numOfLevels);
        imgHieght = compressedImg.size();
        imgWidth = compressedImg.get(0).size();
        decompImgMatrix = new int [imgWidth][imgHieght];
        System.out.println(imgWidth + "             " + imgHieght);
        for (int i = 0; i < imgHieght; i++) {
            for (int j = 0; j < imgWidth; j++) {
                if (j == 0) {
                    System.out.println("here: ");
                    decompImgMatrix[i][j] = compressedImg.get(i).get(j);
                } else {
                    int q = compressedImg.get(i).get(j);
                    int q_1 = findQ_1(q);
                    decompImgMatrix[i][j] = decompImgMatrix[i][j - 1] + q_1;
                    if (decompImgMatrix[i][j]  > 255)
                    	decompImgMatrix[i][j]  = 255;
                }

                if (decompImgMatrix[i][j] > 255)
                    decompImgMatrix[i][j] = 255;
                if (decompImgMatrix[i][j] < 0)
                    decompImgMatrix[i][j] = 0;
                System.out.println(imgMatrix[i][j]  + " -> " + decompImgMatrix[i][j]);
            }
        }
        ImageClass img = new ImageClass();
        img.writeImage(decompImgMatrix);
        System.out.println("deCompression done");
    }

    private int findQ_1(int val) {
        for (int i = 0; i < quantizer.size(); i++) {
            Quantizer q = quantizer.get(i);
            if (q.getQ() == val)
                return q.getQ_1();
        }
        return 0;
    }

    private int findQ(int diff) {
        for (int i = 0; i < quantizer.size(); i++) {
            Range r = quantizer.get(i).getRange();
            if (diff < 0) {
                if (diff > r.getLower() && diff <= r.getUpper())
                    return i;
            } else {
                if (diff >= r.getLower() && diff < r.getUpper())
                    return i;
            }
        }
        return 0;
    }

    private void constructQuantizer(int numOfLevels) {
        int extremeLr = 256, extremeUpper = 0;
        for (int i = 0; i < imgHieght; i++) {
            for (int j = 1; j < imgWidth; j++) {
                int val = imgMatrix[i][j] - imgMatrix[i][j - 1];
                if (val < extremeLr)
                    extremeLr = val;
                if (val > extremeUpper)
                    extremeUpper = val;
            }
        }
        int stepSize = (extremeUpper - extremeLr) / numOfLevels;
        if (stepSize <= 1)
            stepSize = 2;
        int lr = extremeLr, upper = extremeUpper;
        for (int i = 0; i < numOfLevels; i++) {
            Range r = new Range();
            if (i == 0) {
                lr = extremeLr;
                upper = lr + stepSize;
            } else if (i == numOfLevels - 1) {
                lr = upper;
                upper = extremeUpper;
            } else {
                lr = upper;
                upper = lr + stepSize;
            }
            r.setUpper(upper);
            r.setLower(lr);
            int q_1 = (lr + upper) / 2;
            Quantizer q = new Quantizer(i, r, q_1);
            quantizer.add(q);
            //System.out.println(i + " " + lr + " " + upper + " " + q_1);
        }
    }

    public int[][] getImgMatrix() {
        return imgMatrix;
    }

    public void setImgMatrix(int[][] imgMatrix) {
        this.imgMatrix = imgMatrix;
    }

    public int getNumOfLevels() {
        return numOfLevels;
    }

    public void setNumOfLevels(int numOfLevels) {
        this.numOfLevels = numOfLevels;
    }


}
