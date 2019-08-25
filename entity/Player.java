package com.harthoric.brickbreaker.entity;

import com.harthoric.brickbreaker.board.Board;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Paddle {

	private int lives;

	public Player(int x, int y, int lives) {
		super(x, y, new Rectangle(50, 10, Color.DARKGOLDENROD));
		super.player.setTranslateY(Board.HEIGHT / 2 - 20);
		super.player.setStroke(Color.WHITE);
		this.lives = lives;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void decrementLives() {
		lives--;
	}

	public void setPaddleColour(Color colour) {
		super.player.setFill(colour);
	}

}
