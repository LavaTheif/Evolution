package uk.co.eiennochat.Universe.organisms.animals;

import java.util.ArrayList;
import java.util.Random;

import uk.co.eiennochat.Universe.MainLoop;
import uk.co.eiennochat.Universe.object.land.Ground;

public class Brain {
	Random random = new Random();
	private Node[] inputLayer = new Node[10];
	private Node[] layer1 = new Node[10];
	private Node[] layer2 = new Node[10];
	private Node[] outputLayer = new Node[8];
	
	private ArrayList<Neurone> neurones = new ArrayList<Neurone>();
	
	private float inherit = 0;
	private Animal body;
	
	public Brain(Animal[] parents, Animal body){
		this.body = body;
		if(parents.length==0){
			newBrain();
		}else{
			childBrain(parents);
		}
	}
	
	public void think(){
		//TODO set input Nodes
		if(body.getX()>=MainLoop.UNIVERSE_WIDTH)body.setX(MainLoop.UNIVERSE_WIDTH-1);
		if(body.getZ()>=MainLoop.UNIVERSE_HEIGHT)body.setZ(MainLoop.UNIVERSE_HEIGHT-1);
		Ground land = MainLoop.land[(int)(body.getX())/Ground.HEIGHT][(int)(body.getZ())/Ground.WIDTH];
		inputLayer[0].add(land.getCurrentTemp());
		inputLayer[1].add(land.getWaterLevel());
		inputLayer[2].add(0);
		inputLayer[3].add(0);
		inputLayer[4].add(0);
		inputLayer[5].add(MainLoop.ticks+0.0f%500-250);
		inputLayer[6].add(body.getHunger());
		inputLayer[7].add(body.getThirsty());
		inputLayer[8].add(0);
		inputLayer[9].add(0);
		for(Neurone neurone : neurones){
			neurone.stimulate();
		}
		//TODO read output Nodes
		
		body.turn(outputLayer[5].getScore());
		body.walk(outputLayer[4].getScore());
		if(outputLayer[6].getScore()>0.75)
			body.attemptToMate();
		
		resetNodeValues();
	}
	
	private void resetNodeValues(){
			for(Node node : inputLayer){
				node.reset();
			}
			for(Node node : layer1){
				node.reset();
			}
			for(Node node : layer2){
				node.reset();
			}
			for(Node node : outputLayer){
				node.reset();
			}
	}
	
	private void newBrain(){
		setupNodes();
		setupNeurones(inputLayer, layer1);
		setupNeurones(layer1, layer2);
		setupNeurones(layer2, outputLayer);
	}
	private void childBrain(Animal[] parents){
		setupNodes();
		inheritNeurones(parents);
	}
	
	private void setupNeurones(Node[] from, Node[] to){
		for(int i = 0; i < from.length; i++){
			for(int ii = 0; ii < to.length; ii++){
				neurones.add(new Neurone((2*random.nextFloat())-1, from[i], to[ii]));
			}
		}
		inherit = (2*random.nextFloat())-1;
	}
	private void inheritNeurones(Animal[] parents){
		int totalNeurones = parents[0].getBrain().getNeurones().size();
		for(Animal parent : parents){
			inherit += parent.getBrain().getInherit();
		}
		inherit /= parents.length;
		
		for(int i = 0; i < totalNeurones; i++){
			float weight = 0;
			for(Animal parent : parents){
				weight += parent.getBrain().getNeurones().get(i).getWeight();
			}
			weight /= parents.length;
			weight += inherit;
			if(weight > 1)weight = 1;
			if(weight < -1)weight = -1;
			neurones.add(new Neurone(weight, getNode(parents[0].getBrain().getNeurones().get(i).getFrom().getId()), getNode(parents[0].getBrain().getNeurones().get(i).getTo().getId())));
		}
	}
	
	private void setupNodes(){
		for(int i = 0; i < inputLayer.length; i++){
			inputLayer[i] = new Node(0, i);
		}
		for(int i = 0; i < layer1.length; i++){
			layer1[i] = new Node(1, i);
		}
		for(int i = 0; i < layer2.length; i++){
			layer2[i] = new Node(2, i);
		}
		for(int i = 0; i < outputLayer.length; i++){
			outputLayer[i] = new Node(3, i);
		}
	}
	
	private Node getNode(String id){
		int layer = Integer.parseInt(id.split("_")[0]);
		int number = Integer.parseInt(id.split("_")[1]);
		if(layer == 0){
			return inputLayer[number];
		}else if(layer == 1){
			return layer1[number];
		}else if(layer == 2){
			return layer2[number];
		}else if(layer == 3){
			return outputLayer[number];
		}else{
			return null;
		}
	}
	
	private ArrayList<Neurone> getNeurones() {
		return neurones;
	}

	private float getInherit() {
		return inherit;
	}
}
