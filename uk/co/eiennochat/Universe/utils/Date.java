package uk.co.eiennochat.Universe.utils;

public class Date {
	private int day;
	private int month;
	private int year;
	
	public Date(int dd, int mm, int yy){
		day = dd;
		month = mm;
		year = yy;
	}
	
	public Date clone(){
		return new Date(day, month, year);
	}
	
	public String format(){
		return day+"/"+month+"/"+year;
	}
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void addDay(int day) {
		this.day += day;
		if(this.day>30){
			this.day = 1;
			addMonth(1);
		}
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void addMonth(int month) {
		this.month += month;
		if(this.month>10){
			this.month = 1;
			addYear(1);
		}
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public void addYear(int year) {
		this.year += year;
	}
}
