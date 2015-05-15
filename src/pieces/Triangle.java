package pieces;

import graphics.PieceType;

import java.awt.Color;

public class Triangle extends GamePiece {

	public static final int STEPS = 2;

	public Triangle(int number, Place place, Color color) {
		super(number, place, color);
	}

	@Override
	public void move(Direction d) {
		if (d.orthogonal())
			getPlace().move(d, STEPS);
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
		return getColor() == Color.black ? PieceType.triangleb
				: PieceType.trianglew;
	}

	@Override
	public void move(int x, int y) {
		getPlace().move(x, y);
	}

}
