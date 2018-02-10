package imageCompression;

import java.lang.reflect.Array;
import java.util.ArrayList;

import assets.Pair;
import assets.Quantizer;

public class VectorQuantizer {

    ArrayList<Quantizer> quantizer = new ArrayList<>();
    int[][] imgMatrix = {{1, 2, 7, 9, 4, 11}, {3, 4, 6, 6, 12, 12}, {4, 9, 15, 14, 9, 9}, {10, 10, 20, 18, 8, 8}, {4, 3, 17, 16, 1, 4}, {4, 5, 18, 18, 5, 6}};
    int imgHieght = 6, imgWidth = 6, codeBookWidth = 2, codeBookHeight = 2;
    int /* numOfLevels,*/ notEqualAssociates = 0;

    ArrayList<ArrayList<ArrayList<Integer>>> split = new ArrayList<>();
    ArrayList<ArrayList<Pair>> nearestVector = new ArrayList<>();
    ArrayList<Pair> lastNearestVector = new ArrayList<>();
    FileOperations fileOps = new FileOperations();
    ArrayList<ArrayList<Integer>> avg = new ArrayList<>();


    public void compress() {
        initializeNearestVector();
        int numOfLevels = 1;
        while (true) {
            split = constructSplit(numOfLevels);





            numOfLevels++;
        }
    }

    private ArrayList<ArrayList<ArrayList<Integer>>> constructSplit(int numOfLevels) {

        int level;
        if (split.size() == 0)
            level  = -1 ;
        else
            level = 0;
        for (int l = 0; l < numOfLevels; l++) {
            ArrayList<ArrayList<Integer>> split1 = new ArrayList<>();
            ArrayList<ArrayList<Integer>> split2 = new ArrayList<>();
            avg = calcAvg(level);
            for (int i = 0; i < codeBookHeight; i++) {
                ArrayList<Integer> row1 = new ArrayList<>();
                ArrayList<Integer> row2 = new ArrayList<>();
                for (int j = 0; j < codeBookWidth; j++) {
                    row1.add(avg.get(i).get(j) - 1);
                    row2.add(avg.get(i).get(j) + 1);
                }
                split1.add(row1);
                split2.add(row2);
            }
            if(split.size() != 0)
                split.remove(0);
            split.add(split1);
            split.add(split2);
            level++;
        }
        return split;
    }


    private ArrayList<ArrayList<Integer>> calcAvg(int level) {

        for (int codeBookRow = 0; codeBookRow < codeBookHeight; codeBookRow++) {
            ArrayList<Integer> avgRow = new ArrayList<>();
            for (int codeBookCol = 0; codeBookCol < codeBookWidth; codeBookCol++) {
                long sum = 0;
                for (int i = codeBookRow; i < imgHieght; i += codeBookHeight) {
                    for (int j = codeBookCol; j < imgWidth; j += codeBookWidth) {
                        if (nearestVector.get(i).get(j).getLevel() == level)
                            sum += nearestVector.get(i).get(j).getVal();
                        //System.out.print(nearestVector.get(i).get(j).getVal() + " ");
                    }
                }
                int avg = (int) (sum / ((imgWidth * imgHieght) / (codeBookWidth * codeBookHeight)));
                System.out.print(level + " " +avg + " ");
                avgRow.add(avg);
            }
            avg.add(avgRow);
        }
        return avg;
    }

    private void initializeNearestVector() {
        for (int i = 0; i < imgHieght; i++) {
            ArrayList<Pair> row = new ArrayList<>();
            for (int j = 0; j < imgWidth; j++) {
                Pair p = new Pair(imgMatrix[i][j], -1);
                row.add(p);
            }
            nearestVector.add(row);
        }
    }

    public static void main(String[] args) {
        VectorQuantizer vq = new VectorQuantizer();
        vq.compress();
    }
}

