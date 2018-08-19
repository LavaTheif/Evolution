package uk.co.eiennochat.Universe;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import uk.co.eiennochat.Universe.object.land.Ground;
import uk.co.eiennochat.Universe.organisms.Organism;
import uk.co.eiennochat.Universe.organisms.Shape;

public class Render extends Canvas{
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame();
	private Graphics g;
	private BufferStrategy bs;
	
	@SuppressWarnings("static-access")
	public Render(){
		frame.setVisible(true);
		frame.setSize(MainLoop.WINDOW_SIZE, MainLoop.WINDOW_SIZE);
		frame.setResizable(false);
		frame.setTitle("Testing");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(false);
		frame.getContentPane().add(this);
		
		MainLoop.inputs = new InputManager();

		this.addKeyListener(MainLoop.inputs);
		this.addMouseMotionListener(MainLoop.inputs);
		this.addMouseListener(MainLoop.inputs);
		this.addMouseWheelListener(MainLoop.inputs);
		//TODO Mouse listener
	}
	int xOffs = MainLoop.xOffs;
	int yOffs = MainLoop.yOffs;
	
	public void prepare(){
		//Set up
		if(bs==null){
			this.createBufferStrategy(3);
			bs = this.getBufferStrategy();
		}
		g = bs.getDrawGraphics();
		xOffs = MainLoop.xOffs;
		yOffs = MainLoop.yOffs;
		
		//fill background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MainLoop.WINDOW_SIZE, MainLoop.WINDOW_SIZE);
	}
	
	public void update(){
		//add ground
		for(int y = 0-(yOffs%Ground.HEIGHT); y < frame.getWidth(); y+=Ground.WIDTH){
			for(int x = 0-(xOffs%Ground.WIDTH); x < frame.getWidth(); x+=Ground.HEIGHT){
				if((xOffs+x)/Ground.WIDTH>=MainLoop.UNIVERSE_WIDTH/10 || 
						(yOffs+y)/Ground.HEIGHT>=MainLoop.UNIVERSE_HEIGHT/10){
					g.setColor(Color.BLUE);
				}else{
					try{
						g.setColor(MainLoop.land[(xOffs+x)/Ground.WIDTH][(yOffs+y)/Ground.HEIGHT].getProperties().getColour());
					}catch(ArrayIndexOutOfBoundsException e){
						e.printStackTrace();
						System.out.println((xOffs+x)/Ground.WIDTH+" "+(yOffs+y)/Ground.HEIGHT + " " + MainLoop.land.length);
						continue;
					}
				}
				g.fillRect(x, y, Ground.WIDTH, Ground.HEIGHT);
			}
		}
	}
	
//	public static void renderAtom(Atom atom){
//		
//	}
	
	public void renderOrganism(double x, double z, Color colour, Shape shape, int w, int h, int index) {
		if(x-xOffs > 0 && x-xOffs<MainLoop.WINDOW_SIZE &&
				z-yOffs > 0 && z-yOffs<MainLoop.WINDOW_SIZE){
//			System.out.println(shape);
			g.setColor(colour);
			if(shape==Shape.CIRCLE)
				g.fillOval((int)(x-xOffs), (int)(z-yOffs), w, h);
			if(shape==Shape.TRIANGLE){
				int[] xShape = {(int)(x-xOffs), (int)(x-xOffs)+(w/2), (int)(x-xOffs)+w};
				int[] yShape = {(int)(z-yOffs)+h, (int)(z-yOffs), (int)(z-yOffs)+h};
				g.fillPolygon(xShape, yShape, 3);
			}
			if(shape==Shape.KITE){
				int[] xShape = {(int)(x-xOffs)+(w/2), (int)(x-xOffs)+w, (int)(x-xOffs)+(w/2),(int)(x-xOffs)};
				int[] yShape = {(int)(z-yOffs), (int)(z-yOffs)+((h/3)), (int)(z-yOffs)+h, (int)(z-yOffs)+((h/3))};
				g.fillPolygon(xShape, yShape, 4);
			}
			if(shape==Shape.PENTAGON){
				int[] xShape = {(int)(x-xOffs)+(w/2), (int)(x-xOffs)+w, (int)(x-xOffs)+((w/5)*4),(int)(x-xOffs)+((w/5)),(int)(x-xOffs)};
				int[] yShape = {(int)(z-yOffs), (int)(z-yOffs)+((h/5)*2), (int)(z-yOffs)+h, (int)(z-yOffs)+h, (int)(z-yOffs)+((h/5)*2)};
				g.fillPolygon(xShape, yShape, 5);
			}
			if(shape==Shape.RECTANGLE)
				g.fillRect((int)(x-xOffs), (int)(z-yOffs), w, h);
			if(shape==Shape.SQUARE)
				g.fillRect((int)(x-xOffs), (int)(z-yOffs), w, w);
			if(index == MainLoop.selectedOrganism){
				g.setColor(Color.YELLOW);
				if(shape==Shape.SQUARE)
					g.drawRect((int)(x-xOffs)-5, (int)(z-yOffs)-5, w+9, w+9);
				else
					g.drawRect((int)(x-xOffs)-5, (int)(z-yOffs)-5, w+9, h+9);
			}
		}
	}
	public void finalize(){
		//add overlays
		g.setFont(new Font("Cambri", 0, 10));
		g.setColor(Color.WHITE);
		g.drawString("Move Speed: " + (11-Math.ceil(MainLoop.scrollSpeed/5.00)) + " -- Ticks per day: "+MainLoop.dayLength, 0, 10);
		g.drawString("Date: " + MainLoop.today.format(), 0, 20);
		g.drawString("Organisms: " + MainLoop.organisms.size(), 0, 30);
		Organism org = MainLoop.organisms.get(MainLoop.selectedOrganism);
		g.drawString("Species: "+org.getSpeciesId() + " -- Shape: " + org.getShape()+" -- Age: "+org.getAge()+" -- Hunger: "+org.getHunger()+"/"+org.getHungerDepletionRate()+" -- Thirst: "+org.getThirsty()+"/"+org.getThirstDepletionRate(), 0, 40);
		g.drawString("Speed: "+org.getSpeed()+" -- X Dist: " + (Math.floor(org.getX()-xOffs)) + " -- Y Dist: " + (Math.floor(org.getZ()-yOffs)), 0, 50);
		//render image
		g.dispose();
		bs.show();
	}
}
