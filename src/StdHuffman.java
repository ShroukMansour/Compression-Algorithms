import java.util.List;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class StdHuffman {
	
	private String text;

	private List<Probability> probsList = new ArrayList<Probability>();

	private FileOperations fileOps = new FileOperations();
	
	private Map<String, String> compressionCodes = new HashMap<String, String>();
	
	private Map<String, String> decompressionCodes = new HashMap<String, String>();
	
	
	public void compress(String fileName) throws FileNotFoundException, IOException {
		
		text = fileOps.readFile(fileName);
		calcProbabilty();
		constructTree();
		fileOps.writeProbListToFile(probsList);
		fileOps.writeStdCompressedData(compressionCodes, text);
	}
	
	
	public void decompress() throws IOException {
			
		probsList.clear();
		compressionCodes.clear();
		probsList = fileOps.readProbsListFromFile(
				"E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\compressStdHuff.txt");
		String compressedText = fileOps.readBinaryDataStdHuff();
		String text = "";
		constructTree();
		for (Entry<String, String> entry : compressionCodes.entrySet()) {
			decompressionCodes.put(entry.getValue(), entry.getKey());
		}
		for(int i = 0; i < compressedText.length();) {
			int subStrSz = 1;
			while(true) {
				String subStr = compressedText.substring(i, i + subStrSz);
				if(decompressionCodes.containsKey(subStr)) {
					text += decompressionCodes.get(subStr);
					i += subStrSz;
					break;
					
				} else {
					subStrSz++;
				}		
			}
		}
		fileOps.writeDataToFile("E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files\\deCompressStdHuff.txt", text);
	}
	
	
	public void constructTree() throws IOException {
		BinaryTree root = new BinaryTree();
		BinaryTree node = root;
		int sz = probsList.size(); 
		int probSz = probsList.size() -  1;
		
		while(true) {
			
			node.addRightNode();
			node.addLeftNode();
			
			for(int i = sz - probSz; i < sz; i++) {
				node.getRightNode().setProb(node.getRightNode().getProb() + probsList.get(i).getProb());
				node.getRightNode().setLetters(node.getRightNode().getLetters() + probsList.get(i).getLetter());
			}
			
			node.getRightNode().setBinary(node.getBinary() + "1");
			node.getLeftNode().setBinary(node.getBinary() + "0");
			node.getLeftNode().setLetters(Character.toString(probsList.get(sz - probSz - 1).getLetter()));
			node.getLeftNode().setProb(probsList.get(sz - probSz - 1).getProb());
			compressionCodes.put(node.getLeftNode().getLetters(), node.getLeftNode().getBinary());

			probSz--;
			
			if(node.getRightNode().getLetters().length() > 1)
				node = node.getRightNode();
			else if (node.getLeftNode().getLetters().length() > 1)
				node = node.getLeftNode();
			else {
				compressionCodes.put(node.getRightNode().getLetters(), node.getRightNode().getBinary());
				break;
			}
		}
		for (Entry<String, String> entry : compressionCodes.entrySet()) {
		    System.out.println(entry.getKey() + "/" + entry.getValue());
		}
	}
	
	
	public void calcProbabilty() {
		
		Map<Character, Integer> freqOfLetter = new HashMap<Character, Integer>();
		for(int i = 0; i < text.length(); i++) {
			char letter = text.charAt(i);
			Integer freq = freqOfLetter.get(letter);
			if(freq != null)
				freqOfLetter.put(letter, freq + 1);
			else
				freqOfLetter.put(letter, 1);
		}
		for (Entry<Character, Integer> entry : freqOfLetter.entrySet()) {
			Probability prob = new Probability(entry.getKey(), new Double(entry.getValue()) / text.length());
			probsList.add(prob);
			Collections.sort(probsList);
		}
	}
}
