package com.harthoric.brickbreaker.brick;

import com.harthoric.brickbreaker.util.Vector2D;

public class Brick {

	private int hits;
	private Vector2D vertex1;
	private Vector2D vertex2;

	public Brick(int hits, Vector2D vertex1, Vector2D vertex2) {
		this.hits = hits;
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public Vector2D getVertex1() {
		return vertex1;
	}

	public Vector2D getVertex2() {
		return vertex2;
	}

	public void setVertex1(Vector2D vertex1) {
		this.vertex1 = vertex1;
	}

	public void setVertex2(Vector2D vertex2) {
		this.vertex2 = vertex2;
	}

}
