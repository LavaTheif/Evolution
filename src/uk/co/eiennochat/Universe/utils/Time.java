package uk.co.eiennochat.Universe.utils;

public class Time {
	int hours;
	int mins;
	int sec;
	
	public Time(int hh, int mm, int ss){
		hours = hh;
		mins = mm;
		sec = ss;
	}
	
	public Time clone(){
		return new Time(hours, mins, sec);
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMins() {
		return mins;
	}

	public void setMins(int mins) {
		this.mins = mins;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}
}
