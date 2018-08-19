package uk.co.eiennochat.Universe;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

public class InputManager implements KeyListener, MouseMotionListener, MouseInputListener, MouseWheelListener {
//	public KeyManager() {new KeyEvent(new Component() {}, 1, 4, 2, 3);}
	public boolean keyID[] = new boolean[255];
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()>255)return;
		keyID[arg0.getKeyCode()]=true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode()>255)return;
		keyID[arg0.getKeyCode()]=false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		MainLoop.scrollSpeed += (arg0.getWheelRotation());
		if(MainLoop.scrollSpeed <= 0)MainLoop.scrollSpeed = 1;
		if(MainLoop.scrollSpeed >= 50)MainLoop.scrollSpeed = 50;
	}

}
