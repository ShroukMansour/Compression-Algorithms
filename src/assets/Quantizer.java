package assets;

import imageCompression.Range;

public class Quantizer {
    int q;
    Range range;
    int q_1;

    public Quantizer(int i, Range r, Integer q1) {
        q = i;
        range = r;
        q_1 = q1;
    }

    public int getQ_1() {
        return q_1;
    }

    public void setQ_1(int q_1) {
        this.q_1 = q_1;
    }

    public int getQ() {
        return q;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public void setQ(int q) {
        this.q = q;
    }
}
