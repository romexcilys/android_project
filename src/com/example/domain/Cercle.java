package com.example.domain;

public class Cercle {
	
	private float x;
	private float y;
	private float radius;
	
	
	public Cercle(float x, float y, float radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		final int borne = 80;
		Cercle other = (Cercle) o;
		if(this == other)
			return true;
		else if(Math.abs(this.x-other.x) < borne && Math.abs(this.y-other.y) < borne)
			return true;
		
		return false;
	}
	
	
	
	

}
