package uk.co.eiennochat.Universe.organisms.animals;

import java.awt.Color;

import uk.co.eiennochat.Universe.MainLoop;
import uk.co.eiennochat.Universe.object.Item;
import uk.co.eiennochat.Universe.object.land.Ground;
import uk.co.eiennochat.Universe.organisms.Organism;
import uk.co.eiennochat.Universe.organisms.Shape;
import uk.co.eiennochat.Universe.utils.Direction;

public class Animal extends Organism{
	/*
	 * On Startup.
	 * 
	 * Generate 10 animals, and each gives birth 10 times to make 10 species.
	 * 
	 */
	
	private Brain brain;
	private Item[] carrying;
	
	public Animal(Animal[] parents){
		this.parents = parents;
		brain = new Brain(parents, this);
		totalParents = (byte) parents.length;
		double total = 0;
		for(Organism parent : parents){
			total += parent.getBreedRate();
		}
		breedRate = mutateByte(total, 1.1);

		total = 0;
		for(Organism parent : parents){
			total += parent.getMaturityAge();
		}
		maturityAge = mutateByte(total, 1);
		
		total = 0;
		for(Organism parent : parents){
			total += parent.getHealth();
		}
		health = mutateByte(total, 1.1);
		
		if(parents.length==0){
			x = random.nextInt(MainLoop.UNIVERSE_WIDTH);//-MainLoop.UNIVERSE_WIDTH;
			y = 0;
			z = random.nextInt(MainLoop.UNIVERSE_HEIGHT);//-MainLoop.UNIVERSE_HEIGHT;
			facing = new Direction();
		}else{
			x = parents[0].getX()+random.nextInt(10);
			y = parents[0].getY();
			z = parents[0].getZ()+random.nextInt(10);
			facing = parents[0].getFacing();
		}
		renderable = true;
		
		if(parents.length==0){
			speciesId = random.nextDouble() * random.nextInt(500);
		}else{
			total = 0;
			for(Organism parent : parents){
				total += parent.getSpeciesId();
			}
			speciesId = mutateDouble(total, 1.001);
		}
		
		int red;int green; int blue;
		if(parents.length==0){
			red = randomColour();
			green = randomColour();
			blue = randomColour();
		}else{
			total = 0;
			for(Organism parent : parents){
				total += parent.getColour().getBlue();
			}
			blue = Math.abs(mutateInt(total, 1.1));
			if(blue>255)blue = 255;
			if(blue<0)blue = 0;
			
			total = 0;
			for(Organism parent : parents){
				total += parent.getColour().getGreen();
			}
			green = Math.abs(mutateInt(total, 1.1));
			if(green>255)green = 255;
			if(green<0)green = 0;
			
			total = 0;
			for(Organism parent : parents){
				total += parent.getColour().getRed();
			}
			red = Math.abs(mutateInt(total, 1.1));
			if(red>255)red = 255;
			if(red<0)red = 0;
		}
		colour = new Color(red, green, blue);
		if(parents.length==0){
			int s = random.nextInt(6);
			if(s==0)
				shape = Shape.CIRCLE;
			else if(s==1)
				shape = Shape.KITE;
			else if(s==2)
				shape = Shape.PENTAGON;
			else if(s==3)
				shape = Shape.RECTANGLE;
			else if(s==4)
				shape = Shape.SQUARE;
			else if(s==5)
				shape = Shape.TRIANGLE;
		}else{
			shape = parents[0].getShape();
		}
		total = 0;
		for(Organism parent : parents){
			total += parent.getWidth_x();
		}
		width_x = mutateInt(total, 1.5)/5 +1;
		if(total==0)width_x*=5;
		maxWidth_x = mutateInt(total, 1.1);
		total = 0;
		for(Organism parent : parents){
			total += parent.getWidth_y();
		}
		width_y = mutateInt(total, 1.5)/5 +1;
		if(total==0)width_y*=5;
		maxWidth_y = mutateInt(total, 1.1);
		age = 0;
		birthday=MainLoop.today.clone();
		
		total = 0;
		for(Organism parent : parents){
			total += parent.getHeight();
		}
		height = mutateDouble(total, 1.5)/10;
		maxHeight = mutateDouble(total, 1.1);
		total = 0;
		for(Organism parent : parents){
			total += parent.getWaterTolerance();
		}
		waterTolerance = mutateByte(total, 1.15);
		total = 0;
		for(Organism parent : parents){
			total += parent.getHeatTolerance();
		}
		heatTolerance = mutateByte(total, 1.15);
		
		total = 0;
		for(Organism parent : parents){
			total += parent.getWeight();
		}
		weight = mutateDouble(total, 1.5)/10;
		weight = mutateDouble(total, 1.1);

		total = 0;
		for(Organism parent : parents){
			total += parent.getStrength();
		}
		strength = (byte) (mutateByte(total, 1.5)/10);
		strength = mutateByte(total, 1.1);

		total = 0;
		for(Organism parent : parents){
			total += parent.getSpeed();
		}
		speed = (byte) (mutateByte(total, 1.5)/10);
		speed = mutateByte(total, 1.1);

		total = 0;
		for(Organism parent : parents){
			total += parent.getVolume();
		}
		volume = (byte) (mutateByte(total, 1.5)/10);
		volume = mutateByte(total, 1.1);

		hunger = 128;
		thirsty = 128;
		total = 0;
		for(Organism parent : parents){
			total += parent.getPoisonLevel();
		}
		poisonLevel = mutateByte(total, 1.1);
//		excretions = parents[0].getExcretions();//TODO
//		fluids = parents[0].getFluids();//TODO
		urinate = 100;
		poop = 100;
		total = 0;
		for(Organism parent : parents){
			total += parent.getHungerDepletionRate();
		}
		hungerDepletionRate = (byte) (mutateByte(total, 1));
		total = 0;
		for(Organism parent : parents){
			total += parent.getThirstDepletionRate();
		}
		thirstDepletionRate = (byte) (mutateByte(total, 1));
	}
	
	protected void speciesTick(){
		brain.think();
	}
	
	public void turn(float direction){
		facing.turn(direction);
	}
	
	public void walk(float distance) {
		Ground land = MainLoop.land[(int)(x)/Ground.HEIGHT][(int)(z)/Ground.WIDTH];
		float friction = land.getFriction();
//		System.out.println(friction + " " + land.getFriction());
		x+=Math.cos(facing.getDirection())*distance*((speed+weight*friction)/40.00);
		if(x<=0)x=1;
		if(x>=MainLoop.UNIVERSE_WIDTH-width_x-10)x=MainLoop.UNIVERSE_WIDTH-width_x-11;
		z+=Math.sin(facing.getDirection())*distance*((speed+weight*friction)/40.00);
		if(z<=0)z=1;
		if(z>=MainLoop.UNIVERSE_HEIGHT-width_y-10)z=MainLoop.UNIVERSE_HEIGHT-width_y-11;
	}
	
	public void attemptToMate() {
		if(!isReadyToMate() || MainLoop.organisms.size()>1000)return;
		for(Organism o : MainLoop.organisms){
			if(o instanceof Animal){
				if(inRange(x, o.getX(), 25) && inRange(z, o.getZ(), 25)){
					if(inRange(speciesId, o.getSpeciesId(), 15)){
						lastMate=0;
						o.setLastMate(0);
						Animal[] parents = new Animal[2];
						parents[0] = this;
						parents[1] = (Animal)o;
						MainLoop.babies.add(new Animal(parents));
						return;
					}
				}
			}
		}
	}
	public boolean isReadyToMate(){
		return (age>maturityAge)&&(lastMate>breedRate);
	}
	
	public boolean inRange(double a, double b, double add){
		return ((a>b-add)&&(a<b+add));
	}
	
	public Brain getBrain() {
		return brain;
	}

	public void setBrain(Brain brain) {
		this.brain = brain;
	}

	public Item[] getCarrying() {
		return carrying;
	}

	public void setCarrying(Item[] carrying) {
		this.carrying = carrying;
	}
}
