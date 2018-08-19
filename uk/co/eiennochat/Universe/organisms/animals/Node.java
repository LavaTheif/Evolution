package uk.co.eiennochat.Universe.organisms.animals;

public class Node {
	private String id;
	private float score = 0;
	
	public Node(int layer, int number){
		id = layer+"_"+number;
	}
	
	public void reset(){
		score = 0;
	}
	public void add(float i){
		score += i;
	}
	public float getScore(){
		return (score/(1+Math.abs(score)));
	}
	public String getId() {
		return id;
	}
}
