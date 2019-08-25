package com.harthoric.brickbreaker.board;

import java.util.HashMap;

import com.harthoric.brickbreaker.Main;
import com.harthoric.brickbreaker.brick.BrickLoader;
import com.harthoric.brickbreaker.entity.Ball;
import com.harthoric.brickbreaker.entity.Player;
import com.harthoric.brickbreaker.util.Vector2D;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Board {

	public static final int WIDTH = 600, HEIGHT = 600;
	public static final Color colours[] = new Color[] { Color.BEIGE, Color.DARKGRAY, Color.DIMGRAY, Color.GRAY,
			Color.WHITE, Color.DARKRED, Color.DARKGOLDENROD };
	public static HashMap<KeyCode, Boolean> keys = new HashMap<>();

	private Canvas canvas;
	private StackPane root;
	private Scene scene;

	private BrickLoader brickLoader;
	private static Player player;
	private static Ball ball;

	Text lifeCounter;
	Rectangle leftBorder, rightBorder, topBorder, topLeftSquare, topRightSquare;

	public Board() {
		canvas = new Canvas(WIDTH, HEIGHT);
		canvas.setFocusTraversable(true);
		root = new StackPane();
		root.setStyle("-fx-background-color: black");
		player = new Player(0, HEIGHT / 2 - 20, 5);
		ball = new Ball(1.005f, new Vector2D(-2.0f * 0.722f, 2.35f * 0.722f), new Vector2D(0, HEIGHT / 2 - 50),
				new Circle(5, Color.DARKRED));

		brickLoader = new BrickLoader(5, 10, 40, 20, 5, true);
		setupBorders();
		setupScore();
		addRootChild(canvas, brickLoader.getBrickGroup(), ball.ball, player.player, lifeCounter, leftBorder,
				rightBorder, topBorder, topLeftSquare, topRightSquare);
		scene = new Scene(root);
		scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
		scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
	}

	private void setupBorders() {
		lifeCounter = new Text(String.valueOf(player.getLives()) + " 💕");
		leftBorder = new Rectangle(30, HEIGHT - 30, Color.DARKGOLDENROD);
		leftBorder.setTranslateX(-WIDTH / 2);
		leftBorder.setStroke(Color.WHITE);
		rightBorder = new Rectangle(30, HEIGHT - 30, Color.DARKGOLDENROD);
		rightBorder.setTranslateX(WIDTH / 2);
		rightBorder.setStroke(Color.WHITE);
		topBorder = new Rectangle(WIDTH, 30, Color.DARKGOLDENROD);
		topBorder.setTranslateY(-HEIGHT / 2);
		topBorder.setStroke(Color.WHITE);
		topLeftSquare = new Rectangle(30, 40, Color.DARKGOLDENROD);
		topLeftSquare.setTranslateY(-HEIGHT / 2);
		topLeftSquare.setTranslateX(-WIDTH / 2);
		topRightSquare = new Rectangle(30, 40, Color.DARKGOLDENROD);
		topRightSquare.setTranslateY(-HEIGHT / 2);
		topRightSquare.setTranslateX(WIDTH / 2);
	}

	private void setupScore() {
		lifeCounter.setStroke(Color.WHITE);
		lifeCounter.setStrokeWidth(1);
		lifeCounter.setFill(colours[player.getLives()]);
		lifeCounter.setScaleX(2.5);
		lifeCounter.setScaleY(2.5);
		lifeCounter.setTranslateX(-WIDTH / 2 + 60);
		lifeCounter.setTranslateY(-HEIGHT / 2 + 30);
	}

	public void addRootChild(Node... node) {
		root.getChildren().addAll(node);
	}

	public Scene getScene() {
		return scene;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public static Player getPlayer() {
		return player;
	}

	public void update() {
		ball.resetExplode();
		ball.setPosition(new Vector2D(ball.getPosition().getX() + ball.getVelocity().getX(),
				ball.getPosition().getY() + ball.getVelocity().getY()));
		ball.ball.setTranslateX(ball.getPosition().getX());
		ball.ball.setTranslateY(ball.getPosition().getY());
		handleBallHit();
		if (root.getChildren().contains(player.player)) {
			player.handleBallHit(ball);
		}
		if (brickLoader.getBrickGroup().getChildren().size() > 0) {
			brickLoader.ballHit(ball);
		}
		if (hitFloor()) {
			if (player.getLives() > 0) {
				setComponentFill(colours[player.getLives()]);
				player.decrementLives();
			}
			if (player.getLives() == 0) {
				lifeCounter.setFill(Color.BLACK);
				root.getChildren().removeAll(player.player, leftBorder, rightBorder, topBorder, topLeftSquare,
						topRightSquare, lifeCounter);
			}

			lifeCounter.setText(String.valueOf(player.getLives()) + " 💕");
		}
	}

	// TODO: Dynamic balls
	private void handleBallHit() {
		if (!(ball.getPosition().getX() > -WIDTH / 2 + (player.getLives() > 0 ? 20 : 0)
				&& ball.getPosition().getX() < WIDTH / 2 - (player.getLives() > 0 ? 20 : 0))) {
			ball.getVelocity().setX(-ball.getVelocity().getX());
			ball.explode();
		}
		if (!(ball.getPosition().getY() > -HEIGHT / 2 + (player.getLives() > 0 ? 20 : 0)
				&& ball.getPosition().getY() < HEIGHT / 2)) {
			ball.getVelocity().setY(-ball.getVelocity().getY());
			ball.explode();
		}

	}

	private boolean hitFloor() {
		if (Math.round(ball.getPosition().getY()) >= HEIGHT / 2)
			return true;

		return false;
	}

	private void setComponentFill(Color colour) {
		player.player.setFill(colour);
		lifeCounter.setFill(colour);
		leftBorder.setFill(colour);
		rightBorder.setFill(colour);
		topBorder.setFill(colour);
		topLeftSquare.setFill(colour);
		topRightSquare.setFill(colour);
	}

}
