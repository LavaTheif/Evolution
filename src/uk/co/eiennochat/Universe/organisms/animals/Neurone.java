package uk.co.eiennochat.Universe.organisms.animals;

public class Neurone {
	private float weight;
	private Node from;
	private Node to;
	
	public Neurone(float weight, Node from, Node to){
		this.weight = weight;
		this.from = from;
		this.to = to;
	}
	public void stimulate(){
		to.add(from.getScore()*weight);
	}
	public float getWeight() {
		return weight;
	}
	public Node getFrom() {
		return from;
	}
	public Node getTo() {
		return to;
	}
}
