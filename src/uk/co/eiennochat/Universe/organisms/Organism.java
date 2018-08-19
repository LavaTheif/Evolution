package uk.co.eiennochat.Universe.organisms;

import java.awt.Color;
import java.util.Random;

import uk.co.eiennochat.Universe.MainLoop;
import uk.co.eiennochat.Universe.Render;
import uk.co.eiennochat.Universe.object.Chemical;
import uk.co.eiennochat.Universe.utils.Date;
import uk.co.eiennochat.Universe.utils.Direction;

/**Main organism class
 * 
 * All living organisms extend this class.
 * 
 * Contains default data that all living creatures have, and handles the updating and 
 * rendering of objects.
 *
 */
public class Organism {
	
	/**
	 * Variables
	 */
	
	//Random instance for random mutations
	protected Random random = new Random();
	
	//Who is/are the organisms parents?
	protected Organism[] parents;
	protected byte totalParents;
	//What species is it?  Can breed with any species within 5 points.
	protected double speciesId;
	
	/*Breeding Variables*/
	//How often will it try to breed? (in days).  May change depending on external factors, but
	//is initially set at birth;
	protected int breedRate;
	//When did it last mate?
	protected int lastMate = 0;
	//How old must it be to breed?
	protected byte maturityAge;
	
	/*World Variables
	 * 
	 * Variables used to determine the organisms position etc.
	 */
	//Position
	protected double x;
	protected double y;
	protected double z;
	//What direction is it looking?
	protected Direction facing;
	
	
	/*Render Variables
	 * 
	 * Controls the rendering of an organism
	 */
	//Should we render this organism?
	protected boolean renderable = true;
	//What colour should it be?
	protected Color colour;
	//What shape should it be?
	protected Shape shape;
	//How long and wide should it be?
	protected int width_x;
	protected int width_y;
	//How long and wide can it get?
	protected int maxWidth_x;
	protected int maxWidth_y;
	
	
	/*Organism Stats
	 * 
	 * The statistics of the organism
	 */
	//How old is the organism (in years)
	protected byte age;
	//When was it born/ created
	protected Date birthday;
	//How tall is this organism?  How high can it reach?
	protected double height;
	//How much does it like water?  0-avoids at all costs.  100-lives in it.
	protected byte waterTolerance;
	//How tolerent is it to heat?  0-lives in cold, dies in hot.  100-lives in hot, dies in cold.
	protected byte heatTolerance;
	//How much does it weigh?
	protected double weight;
	//How strong is it?
	protected byte strength;
	//How fast is it?
	protected byte speed;
	//How loud is it?
	protected byte volume;
	//How tall can this organism get?  How high can it reach?
	protected double maxHeight;
	//How much can it weigh?
	protected double maxWeight;
	//How strong can it get?
	protected byte maxStrength;
	//How fast can it get?
	protected byte maxSpeed;
	//How loud can it get?
	protected byte maxVolume;
	//How quick does it get hungry?
	protected byte hungerDepletionRate;
	//How quick does it get thirsty?
	protected byte thirstDepletionRate;
	//How hungry is it? 0-Dead, 100-Throwing up
	protected float hunger;
	//How thirsty is it? 0-Dead, 100-fine
	protected float thirsty;
	//How healthy is it?  Is it ill or damaged
	protected double health;
	//What defines it?  What does it produce that could poison things etc.
	protected byte poisonLevel;//Usually low.  0-not generally poisonous.  100-touch it to die
	protected Chemical[] excretions;//A list of chemicals it produces.  These are consumed when it is consumed
	protected Fluid[] fluids;//What fluids does it have?  Blood, Sap etc (excludes water)
	//Does it need the toilet?
	protected byte urinate;//0-needs to go, 100-just went
	protected byte poop;//0-needs to go, 100-just went
	
	
	/**
	 * Functions
	 */
	
	/*Mutation functions.
	 * These functions take all the parents values, average it out, and adds a slight mutation to it.
	 */
	protected byte mutateByte(double total, double multiplier){
		if(parents.length==0)//First gen
			return (byte)random.nextInt(50);
		total+=(((2*random.nextDouble())-1)*multiplier);
		total/=parents.length;
		if(total>255)total = 255;
		return (byte) total;
	}
	protected int mutateInt(double total, double multiplier){
		if(parents.length==0)//First gen
			return random.nextInt(50);
		total+=(((2*random.nextDouble())-1)*10*multiplier);
		total/=parents.length;
		return (int) total;
	}
	protected double mutateDouble(double total, double multiplier){
		if(parents.length==0)//First gen
			return random.nextDouble()*50;
		total+=(((2*random.nextDouble())-1)*10*multiplier);
		total/=parents.length;
		return (double) total;
	}
	protected int randomColour(){
		return random.nextInt(64)+64;
	}
	/*Update functions
	 * These functions control updating of the creatures each tick
	 */
	
	public void tick(){
		speciesTick();
	}
	
	public void newDay(){
		lastMate++;
		thirsty-=random.nextDouble()*thirstDepletionRate/10.00000;
		hunger-=random.nextDouble()*hungerDepletionRate/20.00000;
//		System.out.println(hungerDepletionRate + " " + hunger);
//		System.out.println(lastMate+" "+breedRate+" "+ maturityAge+ " "+age);
		if(thirsty<=0 || hunger <= 0){
			//die
			MainLoop.deaths.add(this);
			return;
		}
		if(MainLoop.today.getDay()==birthday.getDay()&&MainLoop.today.getMonth()==birthday.getMonth()){
			age++;
		}
		if(MainLoop.today.getDay()==birthday.getDay()){
			if(random.nextInt(5)==0){
				if(width_x < maxWidth_x)
					width_x+=random.nextInt(5);
				if(width_y < maxWidth_y)
					width_y+=random.nextInt(5);
				if(weight < maxWeight)
					weight+=random.nextDouble();
				if(speed < maxSpeed)
					speed+=random.nextInt(2);
			}
		}
	}
	
	public void render(){
		if(renderable){
			MainLoop.render.renderOrganism(x, z, colour, shape, width_x, width_y, MainLoop.organisms.indexOf(this));
		}
	}
	
	protected void speciesTick(){}
	
	
	/*Getters and setters
	 * 
	 */
	
	public Organism[] getParents() {
		return parents;
	}
	public void setParents(Organism[] parents) {
		this.parents = parents;
	}
	public byte getTotalParents() {
		return totalParents;
	}
	public void setTotalParents(byte totalParents) {
		this.totalParents = totalParents;
	}
	public int getBreedRate() {
		return breedRate;
	}
	public void setBreedRate(int breedRate) {
		this.breedRate = breedRate;
	}
	public byte getMaturityAge() {
		return maturityAge;
	}
	public void setMaturityAge(byte maturityAge) {
		this.maturityAge = maturityAge;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public Direction getFacing() {
		return facing;
	}
	public void setFacing(Direction facing) {
		this.facing = facing;
	}
	public boolean isRenderable() {
		return renderable;
	}
	public void setRenderable(boolean renderable) {
		this.renderable = renderable;
	}
	public Color getColour() {
		return colour;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	public int getWidth_x() {
		return width_x;
	}
	public void setWidth_x(int width_x) {
		this.width_x = width_x;
	}
	public int getWidth_y() {
		return width_y;
	}
	public void setWidth_y(int width_y) {
		this.width_y = width_y;
	}
	public byte getAge() {
		return age;
	}
	public void setAge(byte age) {
		this.age = age;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public byte getWaterTolerance() {
		return waterTolerance;
	}
	public void setWaterTolerance(byte waterTolerance) {
		this.waterTolerance = waterTolerance;
	}
	public byte getHeatTolerance() {
		return heatTolerance;
	}
	public void setHeatTolerance(byte heatTolerance) {
		this.heatTolerance = heatTolerance;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public byte getStrength() {
		return strength;
	}
	public void setStrength(byte strength) {
		this.strength = strength;
	}
	public byte getSpeed() {
		return speed;
	}
	public void setSpeed(byte speed) {
		this.speed = speed;
	}
	public byte getVolume() {
		return volume;
	}
	public void setVolume(byte volume) {
		this.volume = volume;
	}
	public float getHunger() {
		return hunger;
	}
	public void setHunger(float hunger) {
		this.hunger = hunger;
	}
	public float getThirsty() {
		return thirsty;
	}
	public void setThirsty(float thirsty) {
		this.thirsty = thirsty;
	}
	public byte getPoisonLevel() {
		return poisonLevel;
	}
	public void setPoisonLevel(byte poisonLevel) {
		this.poisonLevel = poisonLevel;
	}
	public Chemical[] getExcretions() {
		return excretions;
	}
	public void setExcretions(Chemical[] excretions) {
		this.excretions = excretions;
	}
	public Fluid[] getFluids() {
		return fluids;
	}
	public void setFluids(Fluid[] fluids) {
		this.fluids = fluids;
	}
	public byte getUrinate() {
		return urinate;
	}
	public void setUrinate(byte urinate) {
		this.urinate = urinate;
	}
	public byte getPoop() {
		return poop;
	}
	public void setPoop(byte poop) {
		this.poop = poop;
	}
	public int getMaxWidth_x() {
		return maxWidth_x;
	}
	public void setMaxWidth_x(int maxWidth_x) {
		this.maxWidth_x = maxWidth_x;
	}
	public int getMaxWidth_y() {
		return maxWidth_y;
	}
	public void setMaxWidth_y(int maxWidth_y) {
		this.maxWidth_y = maxWidth_y;
	}
	public double getMaxHeight() {
		return maxHeight;
	}
	public void setMaxHeight(double maxHeight) {
		this.maxHeight = maxHeight;
	}
	public double getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}
	public byte getMaxStrength() {
		return maxStrength;
	}
	public void setMaxStrength(byte maxStrength) {
		this.maxStrength = maxStrength;
	}
	public byte getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(byte maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public byte getMaxVolume() {
		return maxVolume;
	}
	public void setMaxVolume(byte maxVolume) {
		this.maxVolume = maxVolume;
	}
	public byte getHungerDepletionRate() {
		return hungerDepletionRate;
	}
	public void setHungerDepletionRate(byte hungerDepletionRate) {
		this.hungerDepletionRate = hungerDepletionRate;
	}
	public byte getThirstDepletionRate() {
		return thirstDepletionRate;
	}
	public void setThirstDepletionRate(byte thirstDepletionRate) {
		this.thirstDepletionRate = thirstDepletionRate;
	}
	public double getSpeciesId() {
		return speciesId;
	}
	public void setSpeciesId(double speciesId) {
		this.speciesId = speciesId;
	}
	public double getHealth() {
		return health;
	}
	public int getLastMate() {
		return lastMate;
	}
	public void setLastMate(int lastMate) {
		this.lastMate = lastMate;
	}
	public void setHealth(double health) {
		this.health = health;
	}

}
