package com.harthoric.brickbreaker.brick;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.harthoric.brickbreaker.board.Board;
import com.harthoric.brickbreaker.entity.Ball;
import com.harthoric.brickbreaker.util.Vector2D;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class BrickLoader {

	// Amount of hits each brick requires to break
	private final int D_GRID[][] = new int[][] { { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }, { 3, 2, 1, 1, 1, 1, 1, 1, 2, 3 },
			{ 3, 3, 3, 2, 1, 1, 2, 3, 3, 3 }, { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }, { 4, 5, 6, 6, 4, 4, 6, 6, 5, 4 } };

	// Stores each brick's rectangle
	private Group brickGroup;
	// Maps the Brick object against its rectangle counterpart
	private Map<Brick, Rectangle> bricks;

	private final int ROWS, COLS, WIDTH, HEIGHT, PADDING;

	private boolean lvlUpAll;

	public BrickLoader(final int ROWS, final int COLS, final int WIDTH, final int HEIGHT, final int PADDING,
			boolean lvlUpAll) {
		this.ROWS = ROWS;
		this.COLS = COLS;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.PADDING = PADDING;
		this.lvlUpAll = lvlUpAll;
		bricks = new HashMap<>();
		brickGroup = new Group();
		random = new Random();
		loadBricks();
		addBricks();
		brickGroup.setTranslateX((Board.WIDTH - COLS * (WIDTH + PADDING) + PADDING) / 2);
		brickGroup.setTranslateY((Board.HEIGHT - ROWS * (HEIGHT + PADDING) + PADDING) / 2 - 100);
		brickGroup.setManaged(false);
	}

	private Random random;

	private int[][] randomGrid() {
		int[][] grid = new int[ROWS][COLS];
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				grid[i][j] = random.nextInt(Board.colours.length - 3) + 1;

		return grid;
	}

	private void loadBricks() {
		int[][] GRID = ROWS != 5 || COLS != 10 ? randomGrid() : D_GRID;
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				Rectangle rect = new Rectangle(WIDTH, HEIGHT, Board.colours[GRID[j][i] - 1]);
				rect.setTranslateX(i * (WIDTH + PADDING));
				rect.setTranslateY(j * (HEIGHT + PADDING));
//					rect.setStroke(Color.BLACK);
				bricks.put(new Brick(GRID[j][i],
						new Vector2D((float) (rect.getTranslateX() - (0.5 * COLS * (WIDTH + PADDING))),
								(float) (rect.getTranslateY() - (0.5 * ROWS * (HEIGHT + PADDING) + 100))),
						new Vector2D((float) (rect.getTranslateX() - (0.5 * COLS * (WIDTH + PADDING) - WIDTH)),
								(float) (rect.getTranslateY() - (0.5 * ROWS * (HEIGHT + PADDING) - HEIGHT + 100)))),
						rect);
			}
		}
	}

	private void addBricks() {
		for (Rectangle brick : bricks.values())
			brickGroup.getChildren().add(brick);
	}

	public Group getBrickGroup() {
		return brickGroup;
	}

	public void ballHit(Ball ball) {
		Map<Brick, Rectangle> tmpBricks = bricks;
		for (Brick coords : tmpBricks.keySet()) {
			if (ball.getPosition().getX() > coords.getVertex1().getX()
					&& ball.getPosition().getX() < coords.getVertex2().getX()
					&& (Math.abs(ball.getPosition().getY() - coords.getVertex1().getY()) <= 5
							|| Math.abs(ball.getPosition().getY() - coords.getVertex2().getY()) <= 5)) {
				ball.changeYVel();
				handleBallHit(coords, ball);
				break;
			}
			if ((ball.getPosition().getY() > coords.getVertex1().getY()
					&& ball.getPosition().getY() < coords.getVertex2().getY())
					&& (Math.abs(ball.getPosition().getX() - coords.getVertex1().getX()) <= 5
							|| Math.abs(ball.getPosition().getX() - coords.getVertex2().getX()) <= 5)) {
				ball.changeXVel();
				handleBallHit(coords, ball);
				break;
			}

		}
	}

	public void handleBallHit(Brick coords, Ball ball) {
		ball.increaseVel();
		int i = 0, hits = 0;
		for (Brick bCoords : bricks.keySet()) {
			if (bCoords == coords) {
				hits = bCoords.getHits() - 1;
				bCoords.setHits(hits);
				break;
			}
			i++;
		}

		if (hits == 0) {
			bricks.remove(coords);
			brickGroup.getChildren().remove(i);
			if (bricks.size() > 0) {
				int rndBrick = random.nextInt(bricks.size());
				int j = 0;
				for (Brick bCoords : bricks.keySet()) {
					if (Board.colours.length > coords.getHits() + 1
							&& ((j == rndBrick && !lvlUpAll) || (j != rndBrick && lvlUpAll))) {
						coords.setHits(coords.getHits() + 1);
						bricks.get(bCoords).setFill(Board.colours[coords.getHits()]);
						if (j == rndBrick && !lvlUpAll)
							break;
					}

					j++;
				}
			}
		} else
			bricks.get(coords).setFill(Board.colours[hits - 1]);

		ball.explode();
	}

}
