
public class Node {

    private String binary;
    private char sympol;
    private int nodeNum;
    private int counter;

    private Node leftNode;
    private Node rightNode;
    private Node parent;

    private Node swapNode;

    public Node() {

        binary = "";
        nodeNum = 0;
        counter = 0;
        sympol = '\u0000';

        leftNode = null;
        rightNode = null;
        parent = null;

    }

    public void addLeft(int num, String b) {

        Node node = new Node();

        node.nodeNum = num;
        node.binary = b;
        node.counter = 0;

        this.leftNode = node;
        node.parent = this;
    }

    public void addRight(char s, int num, String b) {

        Node node = new Node();

        node.sympol = s;
        node.nodeNum = num;
        node.binary = b;
        node.counter = 1;

        node.parent = this;
        this.rightNode = node;

    }

    public Node get(char sympol, Node root) {
        Node node = new Node();
        node.setSympol(sympol);
        Node foundNode = search(node, root);
        return foundNode;
    }

    public Node search(Node searchNode, Node node) {
        if(node != null) {
            if(searchNode.getSympol() == node.getSympol() && node.getLeftNode() == null
                    && node.getRightNode() == null) {
                return node;
            } else {
                Node foundNode = search(searchNode, node.getLeftNode());
                if(foundNode == null)
                    foundNode = search(searchNode, node.getRightNode());
                return foundNode;
            }
        } else
            return null;
    }

    public Node find(String code, Node root) {
        Node node = new Node();
        node.setBinary(code);
        Node foundNode = searchBinary(node, root);
        return foundNode;
    }

    public Node searchBinary(Node searchNode, Node node) {
        if(node != null) {
            if(searchNode.getBinary().equals(node.getBinary())
                    && node.getLeftNode() == null
                    && node.getRightNode() == null) {
                return node;
            } else {
                Node foundNode = searchBinary(searchNode, node.getLeftNode());
                if(foundNode == null)
                    foundNode = searchBinary(searchNode, node.getRightNode());
                return foundNode;
            }
        } else
            return null;
    }

    public void traverse (Node root){

        if (root.getLeftNode() != null){
            traverse (root.getLeftNode());
        }
        System.out.println(root.getSympol() + " num: " + root.getNodeNum() + " counter: "  + root.getCounter() + " binary: " + root.getBinary());
        if (root.getRightNode() != null){
            traverse (root.getRightNode());
        }
    }

    public boolean checkForSwap(Node node, Node root) {
        Node searchNode = root;
        swapNode = node;
        swapNode = findSwapNode(node, searchNode, swapNode);
        if (swapNode == node)
            return false;
        else {
            //	System.out.println("swap node num: " + node.getNodeNum());
            //	System.out.println("with node num: " + swapNode.getNodeNum());
            Node tmpNode = node;
            String constBinary = swapNode.getBinary();
            int constNodeNum = swapNode.getNodeNum();
            Boolean swapLeft = false;
            if (swapNode.getParent().getLeftNode() == swapNode)
                swapLeft = true;
            Node swapParent = swapNode.getParent();
            if (node.getParent().getLeftNode() == node) {
                node.getParent().setLeftNode(swapNode);
                swapNode.setParent(node.getParent());
            } else {
                node.getParent().setRightNode(swapNode);
                swapNode.setParent(node.getParent());
            }
            if (swapLeft) {
                swapParent.setLeftNode(tmpNode);
                tmpNode.setParent(swapParent);
            } else {
                swapParent.setRightNode(tmpNode);
                tmpNode.setParent(swapParent);
            }
            swapNode.setNodeNum(tmpNode.getNodeNum());// a right then left
            swapNode.setBinary(tmpNode.getBinary());
            node.setNodeNum(constNodeNum);
            node.setBinary(constBinary);
            changeBinaryCode(root);
            return true;
        }
    }

    private void changeBinaryCode(Node root) {
        if (root.getLeftNode() != null) {
            root.getLeftNode().setBinary(root.getBinary() + "0");
            changeBinaryCode(root.getLeftNode());
        }
        if (root.getRightNode() != null) {
            root.getRightNode().setBinary(root.getBinary() + "1");
            changeBinaryCode(root.getRightNode());
        }
    }

    private Node findSwapNode(Node node, Node searchNode, Node swapNode) {

        if(searchNode.getCounter() <= swapNode.getCounter() &&
                searchNode.getNodeNum() > swapNode.getNodeNum() &&
                searchNode != node.getParent()) {
            swapNode = searchNode;
        }
        if (searchNode.getLeftNode() != null) {
            swapNode = findSwapNode (node, searchNode.getLeftNode(), swapNode);
        }
        if (searchNode.getRightNode() != null) {
            swapNode = findSwapNode (node, searchNode.getRightNode(), swapNode);
        }
         return swapNode;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }

    public char getSympol() {
        return sympol;
    }

    public void setSympol(char sympol) {
        this.sympol = sympol;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }



}
