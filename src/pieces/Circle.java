package pieces;

import java.awt.Color;

public class Circle extends GamePiece {

	public static final int STEPS = 1;

	public Circle(int number, Place place, Color color) {
		super(number, place, color);
	}

	@Override
	public void move(Direction d) {
		getPlace().move(d, STEPS);
	}

}
