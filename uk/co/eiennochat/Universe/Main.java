package uk.co.eiennochat.Universe;

/**Charlies Universe.
 * @author Charlie
 * 
 * Aims:  Create an eco-system that is able to evolve
 * 
 * Should be able to save all data to files.
 * 
 * Features:
 * 
 * Animals:  Creatures that need to find food and water.  
 * They can learn to attack and can evolve to become more smart, have kids with other
 * animals that are similar to them, become new species over time, and etc.
 * Can pick up and carry up to 2 things
 * 
 * Plants:  Need water to survive.
 * They can grow almost anywhere, but need to meet specific criteria.  ie if 'water tolerance'
 * is low, they need to be in less soggy ground, but still need some water.  can change colour
 * over time and release pollen that can mutate with other plants and grow new plants
 * 
 * Bacteria:  Can't be viewed, but its stats are recorded and viewable.
 * Short life span.  Can be toggled on & off to prevent lag.  If it produces something that
 * an animal or plant finds toxic, it could kill them.  Has preferred areas like 'water
 * tolerance'
 * 
 * Weather:  Random events can occour depending on the location and time.
 * Rain, snow, drought, thunder etc.
 * 
 * Land:  Changes over time, and allows an area for things to stand on
 * Each section has an average temperature (it fluctuates around this temp dayly and depending
 * on the seasons).  It can be sandy, muddy, watery, wet, dry, rocky etc.  Can change overtime
 * depending on plants, weather and animals.  Has 'fertility' for how well plants grow
 * 
 * 
 * 
 */


public class Main {
	public static void main(String[] args){
		new MainLoop(false);
	}
}
