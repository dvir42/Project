package pieces;

import graphics.PieceType;

import java.awt.Color;

public class Square extends GamePiece {

	public static final int STEPS = 3;

	public Square(int number, Place place, Color color) {
		super(number, place, color);
	}

	@Override
	public Place[] movements() {
		return new Place[] { getPlace().movement(Direction.N, STEPS),
				getPlace().movement(Direction.E, STEPS),
				getPlace().movement(Direction.S, STEPS),
				getPlace().movement(Direction.W, STEPS) };
	}

	@Override
	public PieceType type() {
		return getColor() == Color.black ? PieceType.squareb
				: PieceType.squarew;
	}

}
