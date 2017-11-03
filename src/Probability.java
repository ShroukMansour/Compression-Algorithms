
public class Probability implements Comparable<Probability>{
	private Character letter;
	private double prob;
	
	
	Probability(Character l, double p){
		letter = l;
		prob = p;
		//System.out.println(letter + " " + prob);
	}
	
	public double getProb() {
		return prob;
	}
	
	public Character getLetter() {
		return letter;
	}
	

	@Override
	public int compareTo(Probability p) {
		// TODO Auto-generated method stub
		int combareProb = Double.compare(this.prob, p.getProb());
		return -combareProb;
	}
	
}
