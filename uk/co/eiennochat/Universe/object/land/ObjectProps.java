package uk.co.eiennochat.Universe.object.land;

import java.awt.Color;
import java.util.Random;

import uk.co.eiennochat.Universe.object.Chemical;

public class ObjectProps {
	 /*
	  *It can be sandy, muddy, watery, wet, dry, rocky etc.
	  */
	private byte frictionModifier;
	private byte waterContentModifier;
	private byte fertilityModifier;
	private Chemical[] contaminantModifier;
	private Color colour;
	
	public static Random random = new Random();

	private ObjectProps(byte fric, byte water, byte fert, Chemical[] contamin, Color colour){
		frictionModifier = fric;
		waterContentModifier = water;
		fertilityModifier = fert;
		contaminantModifier = contamin;
		this.colour = colour;
	}
	
	public void averageOut(ObjectProps props){
		frictionModifier = (byte) ((props.getFrictionModifier()+frictionModifier)/2);
		waterContentModifier = (byte) ((props.getWaterContentModifier()+waterContentModifier)/2);
		fertilityModifier = (byte) ((props.getFertilityModifier()+fertilityModifier)/2);
		Color combine = props.getColour();
		colour = new Color((colour.getRed()+combine.getRed())/2, (colour.getGreen()+combine.getGreen())/2, (colour.getGreen()+combine.getGreen())/2);
	}
	
	public static ObjectProps generateMuddy(){
		byte fric = (byte) (-5+(random.nextInt(6)-3));
		byte water = (byte) (10+(random.nextInt(20)-10));
		byte fert = (byte) (-2+(random.nextInt(6)-4));
		Chemical[] contamin = new Chemical[0];
		Color colour = new Color(132+(random.nextInt(20)-10), 94+(random.nextInt(20)-10), 60+(random.nextInt(20)-10));
		return new ObjectProps(fric, water, fert, contamin, colour);
	}
	
	public static ObjectProps generateSand(){
		byte fric = (byte) ((random.nextInt(20)-10));
		byte water = (byte) (10+(random.nextInt(20)-10));
		byte fert = (byte) (10+(random.nextInt(5)-5));
		Chemical[] contamin = new Chemical[0];
		Color colour = new Color(245+(random.nextInt(20)-10), 224+(random.nextInt(20)-10), 71+(random.nextInt(20)-10));
		return new ObjectProps(fric, water, fert, contamin, colour);
	}
	
	public static ObjectProps generateGrass(){
		byte fric = (byte) (15+(random.nextInt(20)-10));
		byte water = (byte) (5+(random.nextInt(20)-10));
		byte fert = (byte) (10+(random.nextInt(6)-3));
		Chemical[] contamin = new Chemical[0];
		Color colour = new Color(20+(random.nextInt(20)-10), 171+(random.nextInt(20)-10), 33+(random.nextInt(20)-10));
		return new ObjectProps(fric, water, fert, contamin, colour);
	}
	
	public static ObjectProps generateWatery(){
		byte fric = (byte) (-5+(random.nextInt(20)-10));
		byte water = (byte) (15+(random.nextInt(20)-10));
		byte fert = (byte) ((random.nextInt(6)-3));
		Chemical[] contamin = new Chemical[0];
		Color colour = new Color(49+(random.nextInt(20)-10), 28+(random.nextInt(20)-10), 240+(random.nextInt(20)-10));
		return new ObjectProps(fric, water, fert, contamin, colour);
	}
	public static ObjectProps generateWet(){
		byte fric = (byte) (15+(random.nextInt(20)-10));
		byte water = (byte) (5+(random.nextInt(20)-10));
		byte fert = (byte) (10+(random.nextInt(6)-3));
		Chemical[] contamin = new Chemical[0];
		Color colour = new Color(81+(random.nextInt(20)-10), 187+(random.nextInt(20)-10), 187+(random.nextInt(20)-10));
		return new ObjectProps(fric, water, fert, contamin, colour);
	}
	public static ObjectProps generateDry(){
		byte fric = (byte) (15+(random.nextInt(20)-10));
		byte water = (byte) (5+(random.nextInt(20)-10));
		byte fert = (byte) (10+(random.nextInt(6)-3));
		Chemical[] contamin = new Chemical[0];
		Color colour = new Color(245+(random.nextInt(20)-10), 224+(random.nextInt(20)-10), 71+(random.nextInt(20)-10));
		return new ObjectProps(fric, water, fert, contamin, colour);
	}
	public static ObjectProps generateRocky(){
		byte fric = (byte) (15+(random.nextInt(20)-10));
		byte water = (byte) (5+(random.nextInt(20)-10));
		byte fert = (byte) (10+(random.nextInt(6)-3));
		Chemical[] contamin = new Chemical[0];
		Color colour = new Color(128+(random.nextInt(20)-10), 128+(random.nextInt(20)-10), 128+(random.nextInt(20)-10));
		return new ObjectProps(fric, water, fert, contamin, colour);
	}

	public byte getFrictionModifier() {
		return frictionModifier;
	}

	public void setFrictionModifier(byte frictionModifier) {
		this.frictionModifier = frictionModifier;
	}

	public byte getWaterContentModifier() {
		return waterContentModifier;
	}

	public void setWaterContentModifier(byte waterContentModifier) {
		this.waterContentModifier = waterContentModifier;
	}

	public byte getFertilityModifier() {
		return fertilityModifier;
	}

	public void setFertilityModifier(byte fertilityModifier) {
		this.fertilityModifier = fertilityModifier;
	}

	public Chemical[] getContaminantModifier() {
		return contaminantModifier;
	}

	public void setContaminantModifier(Chemical[] contaminantModifier) {
		this.contaminantModifier = contaminantModifier;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}
}
