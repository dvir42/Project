package pieces;

import graphics.PieceType;

import java.awt.Color;

public abstract class GamePiece {

	public static final int PANEL_SIZE = 60; // pixels
	public static final int ACTUAL_SIZE = 45; // pixels

	private final int number;
	private final Place place;
	private Color color;

	public GamePiece(int number, Place place, Color color) {
		this.number = number;
		this.place = place;
		this.color = color;
	}

	public int getNumber() {
		return number;
	}

	public Place getPlace() {
		return place;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public abstract Place[] movements();

	public abstract PieceType type();

}
