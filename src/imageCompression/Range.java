package imageCompression;

public class Range {

    float upper;
    float lower;

    public Range(float l, float u) {
        upper = u;
        lower = l;
    }


    public Range() {
		// TODO Auto-generated constructor stub
	}


	public float getUpper() {
        return upper;
    }

    public void setUpper(float upper) {
        this.upper = upper;
    }

    public float getLower() {
        return lower;
    }

    public void setLower(float lower) {
        this.lower = lower;
    }
}
