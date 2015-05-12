package graphics;

import java.awt.Color;

public class Square extends GamePiece {

	public static final int STEPS = 3;

	public Square(int number, Place place, Color color) {
		super(number, place, color);
	}

	@Override
	public void move(Direction d) {
		if (d.orthogonal())
			getPlace().move(d, STEPS);
	}

}
