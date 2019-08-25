package com.harthoric.brickbreaker.entity;

import com.harthoric.brickbreaker.util.Vector2D;

import javafx.scene.shape.Rectangle;

public class Paddle extends Vector2D {

	public Rectangle player;

	public Paddle(int x, int y, Rectangle player) {
		super(x, y);
		this.player = player;
	}

	public void moveUp(int moveBy) {
		super.setY(super.getY() + moveBy);
	}

	public void moveDown(int moveBy) {
		super.setY(super.getY() - moveBy);
	}

	public void moveLeft(int moveBy) {
		super.setX(super.getX() + moveBy);
		player.setTranslateX(-getX());

	}

	public void moveRight(int moveBy) {
		super.setX(super.getX() - moveBy);
		player.setTranslateX(-getX());
	}

	public void handleBallHit(Ball ball) {
		if ((ball.getPosition().getY() < player.getTranslateY() + 5
				&& ball.getPosition().getY() > player.getTranslateY() - 5)
				&& ball.getPosition().getX() < player.getTranslateX() + 25
				&& ball.getPosition().getX() > player.getTranslateX() - 25) {
			ball.getVelocity().setX(-ball.getVelocity().getX());
			ball.getVelocity().setY(-ball.getVelocity().getY());
			ball.explode();
		}

		if ((ball.getPosition().getX() > player.getTranslateX() - 25
				&& ball.getPosition().getX() < player.getTranslateX() + 25)
				&& ball.getPosition().getY() < player.getTranslateY() + 5
				&& ball.getPosition().getY() > player.getTranslateY() - 5) {
			ball.getVelocity().setX(-ball.getVelocity().getX());
			ball.explode();
		}
	}

}
