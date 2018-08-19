package uk.co.eiennochat.Universe.object.land;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import uk.co.eiennochat.Universe.MainLoop;
import uk.co.eiennochat.Universe.object.Chemical;

public class Ground {
	public static byte WIDTH = 10;
	public static byte HEIGHT = 10;
	public static Random random = new Random();
/**
 * Land:  Changes over time, and allows an area for things to stand on
 * Each section has an average temperature (it fluctuates around this temp dayly and depending
 * on the seasons).  It can be sandy, muddy, watery, wet, dry, rocky etc.  Can change overtime
 * depending on plants, weather and animals.  Has 'fertility' for how well plants grow
 */
	//How well plants can grow
	protected byte fertility;
	//Whats the average temp in this section?
	protected byte averageTemp;
	//What is the current temp?  Usually fluctuates by 5-10 from avg depending on time etc
	protected byte currentTemp;
	//Is this land contaminated?  If so plants will absorb it and dead animals on here will
	//also get contaminated.  Will be eaten by anyone eating things from here.  Could be good
	//or bad eg like potassium for plants, or some kind of poison
	protected Chemical[] contaminants;
	//What properties does the land have?  How strong are these properties.  eg: 10% rocky, 
	//50% sandy, 65% watery would be a beach.  Note theses % are independent of each other.
	protected ObjectProps properties;
	protected ArrayList<ObjectProps> allGroundProps = new ArrayList<>();
	//How much friction is on this land?  How well can animals etc grip it?
	protected byte friction;
	//How much water is in this bit of land?
	protected byte waterLevel;
	//Where is this bit of land located? (multiply by width & height to calculate world pos)
	protected int x;
	protected int y;
//	protected Color test;
	
	public Ground(int x, int y){
		//Set x && y
		this.x = x;
		this.y = y;
		//What do we divide by to get the average?
		byte div = 0;
		
		//What the average properties of land near here are
		byte avg_friction=0;
		ObjectProps avg_props;// = ObjectProps.generateMuddy();

		Chemical[] avg_contaminants;
		byte avg_temp=0;
		byte avg_fertility=0;
		
		if(x!=0){//see what left section is like
			Ground g = MainLoop.land[x-1][y];
			avg_friction += g.getFriction();
			avg_temp += g.getAverageTemp();
			avg_fertility += g.getFertility();
			allGroundProps.addAll(g.getAllGroundProps());
			div++;
		}
		if(y!=0){//see what section above is like
			Ground g = MainLoop.land[x][y-1];
			avg_friction += g.getFriction();
			avg_temp += g.getAverageTemp();
			avg_fertility += g.getFertility();
			allGroundProps.addAll(g.getAllGroundProps());
			div++;
		}
		if(y!=0 && x!=0){//see what above left is like
			Ground g = MainLoop.land[x-1][y-1];
			avg_friction += g.getFriction();
			avg_temp += g.getAverageTemp();
			avg_fertility += g.getFertility();
			allGroundProps.addAll(g.getAllGroundProps());
			div++;
		}
		if(y!=0 && x<MainLoop.UNIVERSE_WIDTH/WIDTH-1){//see what above right is like
			Ground g = MainLoop.land[x+1][y-1];
			avg_friction += g.getFriction();
			avg_temp += g.getAverageTemp();
			avg_fertility += g.getFertility();
			allGroundProps.addAll(g.getAllGroundProps());
			div++;
		}
		if(x==0 && y==0){//first tile, make it up
			avg_friction += random.nextInt(5);
			avg_temp += random.nextInt(5);
			avg_fertility += random.nextInt(5);
			avg_props = getRandomObjectPropsType();
			allGroundProps.add(avg_props);
			div++;
		}
		//Calc average;
		avg_fertility/=div;
		avg_friction/=div;
		avg_temp/=div;
		
		while(allGroundProps.size()>4){
			allGroundProps.remove(random.nextInt(allGroundProps.size()));
		}
		
		if(random.nextInt(10)==0 && allGroundProps.size()!=1){
			ObjectProps props = allGroundProps.get(random.nextInt(allGroundProps.size()));
			props.setColour(new Color((int)((props.getColour().getRGB()+0x050505)*0.99999)));
		}
		if(random.nextInt(100)==0 && allGroundProps.size()!=1){allGroundProps.remove(random.nextInt(allGroundProps.size()));}
		if(random.nextInt(10)==0){allGroundProps.add(getRandomObjectPropsType());}
		
		avg_props = allGroundProps.get(0);
		for(int i = 1; i < allGroundProps.size(); i++){
			avg_props.averageOut(allGroundProps.get(i));
		}
		//TODO Calculate average % of props and chemicals in land
		
		//set props similar to local ground
		//Mutate values
		avg_fertility += ((2*random.nextDouble())-1)*2;
		avg_friction += ((2*random.nextDouble())-1)*2;
		avg_temp += ((2*random.nextDouble())-1)*2;
		
		averageTemp = avg_temp;
		currentTemp = avg_temp;
		fertility = avg_fertility;
		friction = avg_friction;
		properties = avg_props;
	}

	private ObjectProps getRandomObjectPropsType() {
		int ground = random.nextInt(7);
		if(ground==0){
			return (ObjectProps.generateMuddy());
		}else if(ground==1){
			return (ObjectProps.generateGrass());
		}else if(ground==2){
			return (ObjectProps.generateSand());
		}else if(ground==3){
			return (ObjectProps.generateWatery());
		}else if(ground==4){
			return (ObjectProps.generateWet());
		}else if(ground==5){
			return (ObjectProps.generateDry());
		}else if(ground==6){
			return (ObjectProps.generateRocky());
		}else{
			return (ObjectProps.generateMuddy());
		}
	}

	public ArrayList<ObjectProps> getAllGroundProps() {
		return allGroundProps;
	}

	public byte getFertility() {
		return fertility;
	}

	public byte getAverageTemp() {
		return averageTemp;
	}

	public byte getCurrentTemp() {
		return currentTemp;
	}

	public Chemical[] getContaminants() {
		return contaminants;
	}

	public ObjectProps getProperties() {
		return properties;
	}

	public byte getFriction() {
		return friction;
	}

	public byte getWaterLevel() {
		return waterLevel;
	}

}
