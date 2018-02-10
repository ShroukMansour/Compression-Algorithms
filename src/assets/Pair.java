package assets;

public class Pair {
    int val;
    int level;

    public Pair(int val, int level) {
        this.val = val;
        this.level = level;
    }


    public void setLevel(int level) {
        this.level = level;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getLevel() {
        return level;
    }

    public int getVal() {
        return val;
    }

}

