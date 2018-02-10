package assets;

public class BinaryTree {

    private String binary;
    private String letters;
    private double prob;

    private BinaryTree leftNode;
    private BinaryTree rightNode;

    public BinaryTree() {
        // TODO Auto-generated constructor stub

        binary = "";
        letters = "";
        prob = 0;
    }


    public void addRightNode() {
        this.rightNode = new BinaryTree();
    }

    public void addLeftNode() {
        this.leftNode = new BinaryTree();
    }


    public String getBinary() {
        return binary;
    }

    public String getLetters() {
        return letters;
    }

    public double getProb() {
        return prob;
    }


    public void setBinary(String binary) {
        this.binary = binary;
    }


    public void setLetters(String letters) {
        this.letters = letters;
    }


    public void setProb(double prob) {
        this.prob = prob;
    }


    public BinaryTree getLeftNode() {
        return leftNode;
    }


    public void setLeftNode(BinaryTree leftNode) {
        this.leftNode = leftNode;
    }


    public BinaryTree getRightNode() {
        return rightNode;
    }


    public void setRightNode(BinaryTree rightNode) {
        this.rightNode = rightNode;
    }

}
