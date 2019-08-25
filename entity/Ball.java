package com.harthoric.brickbreaker.entity;

import com.harthoric.brickbreaker.util.Vector2D;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {

	// Amount the ball increases each time it hits a brick
	private final float ACCELERATION;
	private Vector2D velocity, position;

	public Circle ball;

	public Ball(final float ACCELERATION, Vector2D velocity, Vector2D position, Circle ball) {
		this.ACCELERATION = ACCELERATION;
		this.velocity = velocity;
		this.position = position;
		this.ball = ball;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public void updatePos() {
		position.setX(position.getX() + velocity.getX());
		position.setY(position.getY() + velocity.getY());
	}

	public void increaseVel() {
		velocity.setY(velocity.getY() * ACCELERATION);
		velocity.setX(velocity.getX() * ACCELERATION);
	}

	public void changeXVel() {
		velocity.setX(-velocity.getX());
	}

	public void changeYVel() {
		velocity.setY(-velocity.getY());
	}

	public void explode() {
		ball.setFill(Color.WHITE);
		ball.setScaleX(2);
		ball.setScaleY(2);
	}

	public void resetExplode() {
		ball.setFill(Color.DARKRED);
		ball.setScaleX(1);
		ball.setScaleY(1);
	}

}
