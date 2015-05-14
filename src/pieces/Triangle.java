package pieces;

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

}
