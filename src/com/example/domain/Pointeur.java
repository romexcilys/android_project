package com.example.domain;

public class Pointeur {
	
	private float x;
	private float y;
	
	public Pointeur(float x, float y) {
		super();
		this.x = x;
		this.y = y;
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
	
	public float getDistance(Pointeur pointeur)
	{
		return (float)Math.sqrt(Math.pow(this.x-pointeur.x, 2) + Math.pow(this.y - pointeur.y, 2));
	}
	
	
	
}
