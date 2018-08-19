package uk.co.eiennochat.Universe;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import uk.co.eiennochat.Universe.object.Atom;
import uk.co.eiennochat.Universe.object.land.Ground;
import uk.co.eiennochat.Universe.organisms.Organism;
import uk.co.eiennochat.Universe.organisms.animals.Animal;
import uk.co.eiennochat.Universe.utils.Date;
import uk.co.eiennochat.Universe.utils.Time;

public class MainLoop {
	public static Date today = new Date(1, 1, 0);
	public static Time now = new Time(0, 0, 0);
	public static int UNIVERSE_WIDTH = 0;
	public static int UNIVERSE_HEIGHT = 0;
	public static final int WINDOW_SIZE = 650;
	public static Ground[][] land = new Ground[UNIVERSE_WIDTH/Ground.WIDTH][UNIVERSE_HEIGHT/Ground.HEIGHT];
	
//	public static ArrayList<Atom> atoms = new ArrayList<>();
	public static ArrayList<Organism> organisms = new ArrayList<>();
	public static ArrayList<Organism> babies = new ArrayList<>();
	public static ArrayList<Organism> deaths = new ArrayList<>();
	
	public MainLoop(boolean loadData) {
		if(!loadData){
			generateLand();
			spawnOrganisms();
			/*TODO
			 * Generate land
			 * create 10 animals, plants and bacteria
			 * breed all living things 10 times to increase populations
			 */
			startMainLoop();
		}else{
			//TODO
		}
	}
	
	private boolean running = false;
	private Thread loop;
	public static Render render;
	
	public static InputManager inputs;
	
	private void startMainLoop(){
		if(running) return;
		loop = new Thread(new Runnable() {
			public void run() {
				while(running){
					render();
					tick();
				}
			}
		});
		running = true;
		render = new Render();
		loop.start();
		stopMainLoop();
	}
	private void stopMainLoop(){
		try {
			loop.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
		System.out.println("Quitting");
		System.exit(0);
	}
	
	private void render(){
		render.prepare();
		render.update();
//		for(Atom atom : atoms){
//			
//		}
		for(Organism o : organisms){
			o.render();
		}
		render.finalize();
	}
	public static int xOffs = 0;
	public static int yOffs = 0;
	public static int ticks = 0;
	public static int scrollSpeed = 15;
	
	public static int selectedOrganism = 0;
	public static int dayLength = 1000;
	
	private void tick(){
		ticks++;
		if(ticks%(Math.ceil(scrollSpeed/5.00))==0){
			if(inputs.keyID[37]){
				xOffs--;
			}
			if(inputs.keyID[38]){
				yOffs--;
			}
			if(inputs.keyID[39]){
				xOffs++;
			}
			if(inputs.keyID[40]){
				yOffs++;
			}
		}
		while((xOffs+WINDOW_SIZE) > UNIVERSE_WIDTH/10-1)xOffs--;
		if(xOffs < 0)xOffs=0;

		while((yOffs+WINDOW_SIZE) > UNIVERSE_HEIGHT/10)yOffs--;
		if(yOffs < 0)yOffs=0;

		if(ticks%30==0){
			if(inputs.keyID[KeyEvent.VK_COMMA]){
				selectedOrganism--;
				if(selectedOrganism<0)selectedOrganism=organisms.size()-1;
			}
			if(inputs.keyID[KeyEvent.VK_PERIOD]){
				selectedOrganism++;
				if(selectedOrganism>organisms.size()-1)selectedOrganism=0;
			}
			
			if(inputs.keyID[45]||inputs.keyID[KeyEvent.VK_SUBTRACT]){
				dayLength-=10;
				if(dayLength<1)dayLength=1;
			}
			if(inputs.keyID[61]||inputs.keyID[KeyEvent.VK_ADD]){
				dayLength+=10;
				if(dayLength>10000)dayLength=10000;
			}
		}
		
		if(ticks%dayLength==0){
			today.addDay(1);
			for(Organism o : organisms){
				o.newDay();
			}
		}
		
		for(Organism o : organisms){
			o.tick();
		}
		organisms.addAll(babies);
		babies.removeAll(babies);
		organisms.removeAll(deaths);
		deaths.removeAll(deaths);
		if(organisms.size()==0)spawnOrganisms();
	}
	
	private void spawnOrganisms(){
		for(int i = 0; i < 10; i ++){
			Animal p = new Animal(new Animal[0]);
			organisms.add(p);
			for(int ii = 0; ii < 10; ii ++){
				Animal[] parent = new Animal[1];
				parent[0] = p;
				organisms.add(new Animal(parent));
			}
		}
	}
	
	/* Code to zoom
			if(){
				Ground.HEIGHT--;
				Ground.WIDTH--;
				if(Ground.HEIGHT < 3) Ground.HEIGHT=3;
				if(Ground.WIDTH < 3) Ground.WIDTH=3;
			}
			if(){
				Ground.HEIGHT++;
				Ground.WIDTH++;
				if(Ground.HEIGHT > 25) Ground.HEIGHT=25;
				if(Ground.WIDTH > 25) Ground.WIDTH=25;
			}
	 */
	
	private void generateLand(){
		for(int y = 0; y < UNIVERSE_HEIGHT/Ground.HEIGHT; y++){
			for(int x = 0; x < UNIVERSE_WIDTH/Ground.WIDTH; x++){
				land[x][y] = new Ground(x, y);
			}
		}
	}
}
