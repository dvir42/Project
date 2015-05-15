package pieces;

import graphics.PieceType;

import java.awt.Color;

public class Circle extends GamePiece {

	public static final int STEPS = 1;

	public Circle(int number, Place place, Color color) {
		super(number, place, color);
	}

	@Override
	public Place[] movements() {
		return new Place[] { getPlace().movement(Direction.N, STEPS),
				getPlace().movement(Direction.E, STEPS),
				getPlace().movement(Direction.S, STEPS),
				getPlace().movement(Direction.W, STEPS),
				getPlace().movement(Direction.NE, STEPS),
				getPlace().movement(Direction.NW, STEPS),
				getPlace().movement(Direction.SE, STEPS),
				getPlace().movement(Direction.SW, STEPS) };
	}

	@Override
	public PieceType type() {
		return getColor() == Color.black ? PieceType.circleb
				: PieceType.circlew;
	}

}
