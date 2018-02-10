package textCompression;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assets.Probability;

public class Arithmetic {

    String text, decomprssedText = "";
    Double compressedValue;

    private List<Probability> probsList = new ArrayList<Probability>();
    private Map<Character, Range> basicRanges = new HashMap<Character, Range>();
    FileOperations fileOPs = new FileOperations();

    public void compress(String fileName) throws FileNotFoundException, IOException {
        text = fileOPs.readFile(fileName);
        calcProbabilty();
        calcBasicRanges();
        Double lower = 0.0;
        Double higher = 1.0;
        for (int i = 0; i < text.length(); i++) {
            Double range = higher - lower;
            Character letter = text.charAt(i);
            higher = lower + (range * basicRanges.get(letter).getHigher());
            lower = lower + (range * basicRanges.get(letter).getLower());
            System.out.println(lower + "  " + higher);
            compressedValue = (lower + higher) / 2;
        }
        String compFileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compressedArth.txt";
        fileOPs.writeCompValue(compressedValue, compFileName);
        fileOPs.writeFileLength(text.length(), compFileName);
        fileOPs.writeProbListArth(probsList, compFileName);

    }

    public void decompress() throws IOException {
        String fileName = "E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compressedArth.txt";
        compressedValue = 0.0;
        probsList.clear();
        compressedValue = fileOPs.readCompValue(fileName);
        int length = fileOPs.readFileLength(fileName);
        probsList = fileOPs.readProbsListArth(fileName);
        calcBasicRanges();
        Double code = compressedValue;
        Double lower = 0.0;
        Double higher = 1.0;
        for (int i = 0; i < length; i++) {
            code = ((code - lower) / (higher - lower));
            System.out.println("code: " + code);
            for (Map.Entry<Character, Range> entry : basicRanges.entrySet()) {
                Double l = entry.getValue().getLower();
                Double h = entry.getValue().getHigher();
                if (code > l && code < h) {
                    lower = l;
                    higher = h;
                    decomprssedText += entry.getKey();
                    break;
                }
            }
        }
        fileOPs.writeDataToFile("E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\decompArth.txt", decomprssedText);
    }

    public void calcBasicRanges() {
        Double lr = 0.0;
        for (Probability p : probsList) {
            Range r = new Range();
            r.setLower(lr);
            r.setHigher(lr + p.getProb());
            basicRanges.put(p.getLetter(), r);
            lr += p.getProb();
        }
    }


    public void calcProbabilty() {
        Map<Character, Integer> freqOfLetter = new HashMap<Character, Integer>();
        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            Integer freq = freqOfLetter.get(letter);
            if (freq != null)
                freqOfLetter.put(letter, freq + 1);
            else
                freqOfLetter.put(letter, 1);
        }
        for (Map.Entry<Character, Integer> entry : freqOfLetter.entrySet()) {
            Probability prob = new Probability(entry.getKey(), new Double(entry.getValue()) / text.length());
            probsList.add(prob);
        }

    }

}
