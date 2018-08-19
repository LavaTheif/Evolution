package uk.co.eiennochat.Universe.utils;

public class Direction {
	private float degrees = 0;
	public void turn(float dir){
		if(dir>0)
			degrees += 0.001;
		else
			degrees -= 0.001;
		if(degrees>180)degrees-=360;
		if(degrees<-180)degrees+=360;
	}
	public float getDirection() {
		return degrees;
	}
}
