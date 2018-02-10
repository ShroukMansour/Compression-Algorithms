package imageCompression;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import assets.ImageClass;
import assets.Pair;
import assets.Quantizer;

import static java.lang.StrictMath.abs;


public class NonuniformQuantizer {

    ArrayList<Quantizer> quantizer = new ArrayList<>();
    int[][] imgMatrix;
    int imgHieght, imgWidth;
    int numOfLevels, avg, notEqualAssociates = 0;
    long sum;

    ArrayList<Integer> split = new ArrayList<>();
    ArrayList<Pair> associate = new ArrayList<>();
    ArrayList<Pair> lastAssociate = new ArrayList<>();
    FileOperations fileOps = new FileOperations();

    public void compress() throws IOException {
        for (int row = 0; row < imgHieght; row++) {
            for (int col = 0; col < imgWidth; col++) {
                sum += imgMatrix[row][col];
            }
        }
        avg = (int) (sum / (imgHieght * imgWidth));
        split.add(avg - 1);
        split.add(avg + 1);
        for (int i = 0; i < imgHieght; i++) {
            for (int j = 0; j < imgWidth; j++) {
                int level = findSplit(imgMatrix[i][j]);
                Pair p = new Pair(imgMatrix[i][j], level);
                associate.add(p);
            }
        }
        while (true) {
            lastAssociate.clear();
            sortAssociate(associate);
            constructSplit();
            for (int i = 0; i < associate.size(); i++) {
                Pair lastPair = associate.get(i);
                lastAssociate.add(lastPair);
                int level = findSplit(associate.get(i).getVal());
                associate.get(i).setLevel(level);
            }
            if(split.size() >= numOfLevels) {
                if (equalAccosiates()) {
                    break;
                }
            }

        }
        constructQuantizer();
        int comp;
        ArrayList<Integer> compressedImg = new ArrayList<Integer>();
        for (int i = 0; i <  imgHieght; i++) {
            for (int j = 0; j < imgWidth; j++) {
                comp = findRange(imgMatrix[i][j]);
                compressedImg.add(comp);
            }
        }
        fileOps.writeSplitNQ(split);
        fileOps.writeWidthHieghtNQ(imgWidth, imgHieght);
        fileOps.writeCompImgNQ(compressedImg);
        System.out.println("Compression done");
}

    public void decompress() throws IOException {
        split = fileOps.readSplitNQ();
        System.out.println("Split from decomp");
        for (int i = 0; i < split.size(); i++) {
            System.out.println(split.get(i));
        }
        numOfLevels = split.size();
        constructQuantizer();
        imgWidth = fileOps.readImgWidthNQ(split.size());
        imgHieght = fileOps.readImgHieghtNQ(split.size());
        int[][] compImage = new int[imgWidth][imgHieght];
        compImage = fileOps.readCompImgNQ(split.size(), imgWidth, imgHieght);
        int compVal;
        for (int i = 0; i < imgWidth ; i++) {
            for (int j = 0; j < imgHieght; j++) {
                compVal = compImage[i][j];
                int q_1 = findQ_1(compVal);
                compImage[i][j] = q_1;
            }
        }
        ImageClass img = new ImageClass();
        img.writeImage(compImage);
    }

    public int findQ_1(int compVal) {
        for (int i = 0; i < quantizer.size(); i++) {
            if (compVal == quantizer.get(i).getQ())
                return quantizer.get(i).getQ_1();
        }
        return 0;
    }


    public int findRange(Integer val) {
        for (int i = 0; i < quantizer.size(); i++) {
            float lr = quantizer.get(i).getRange().getLower();
            float up = quantizer.get(i).getRange().getUpper();
            if (val >= lr && val <= up) {
                return quantizer.get(i).getQ();
            }
        }
        return 0;
    }


    private void    constructQuantizer() {
        float upperRange = 255, lowerRange = 0;
        Range r;
        for (int i = 0; i < numOfLevels; i++) {
            if (i == 0) {
                upperRange = ((float) (split.get(i) + split.get(i + 1))) / 2;
                r = new Range(0, upperRange);
            } else if (i == numOfLevels - 1) {
                r = new Range(upperRange, 255);
            } else {
                lowerRange = upperRange;
                upperRange = ((float) (split.get(i) + split.get(i + 1))) / 2;
                r = new Range(lowerRange, upperRange);
            }
            Quantizer q = new Quantizer(i, r, split.get(i));
            System.out.println("q: " + q.getQ() + " range: " + q.getRange().getLower() + "-" + q.getRange().getLower()
                                    + " q_1: " + q.getQ_1());
            quantizer.add(q);
        }


    }


    public boolean equalAccosiates() {
        sortAssociate(associate);
        sortAssociate(lastAssociate);
        for (int i = 0; i < associate.size(); i++) {
            if (associate.get(i) != lastAssociate.get(i)) {
                if (split.size() >= numOfLevels)
                    notEqualAssociates++;
                if(notEqualAssociates > 10)
                    return true;
                return false;
            }
        }
        return true;
    }

    private void constructSplit() {
        avg = 0;
        sum = 0;
        int numOfElem = 0;
        int level = 0;
        for (int i = 0; i < associate.size(); i++) {
            if (associate.get(i).getLevel() == level && i < associate.size() - 1) {
                sum += associate.get(i).getVal();
                numOfElem++;
            } else {
                if (i == associate.size() - 1) {
                    sum += associate.get(i).getVal();
                    numOfElem++;
                }
                avg = (int) (sum / numOfElem);
                split.remove(0);
                if (split.size() >= numOfLevels) {
                    split.add(avg);
                } else {
                    split.add(avg - 1);
                    split.add(avg + 1);
                }
                sum = associate.get(i).getVal();
                numOfElem = 1;
                if(level < split.size())
                    level++;
            }
        }
    }

    private void sortAssociate(ArrayList<Pair> a) {
        Collections.sort(a, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.getLevel(), o2.getLevel());
            }
        });
    }
    private void sortArrivalR(ArrayList<Pair> a) {
        Collections.sort(a, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return - Integer.compare(o1.getLevel(), o2.getLevel());
            }
        });
    }

    public int findSplit(Integer val) {
        int minDiff = 1000000, minIndex = 0;
        for (int i = 0; i < split.size(); i++) {
            int diff = abs(val - split.get(i));
            if (diff < minDiff) {
                minDiff = diff; //(67-9)0 (67-11)1 (67-76)2
                minIndex = i;
            }
        }
        return minIndex;
    }


    public void setNumOfLevels(int i) {
        // TODO Auto-generated method stub
        numOfLevels = i;
    }

    public void setImgMatrix(int[][] img) {
        // TODO Auto-generated method stub
        imgMatrix = img;
    }

    public void setImgHieght(int hieght) {
        // TODO Auto-generated method stub
        imgHieght = hieght;
    }

    public void setImgWidth(int width) {
        // TODO Auto-generated method stub
        imgWidth = width;
    }


}
