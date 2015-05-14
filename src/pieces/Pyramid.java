package pieces;

import java.awt.Color;
import java.util.ArrayList;

public class Pyramid extends GamePiece {

	ArrayList<GamePiece> pyramid;

	public Pyramid(Place place, Color color) {
		super(0, place, color);
		this.pyramid = new ArrayList<GamePiece>();
	}

	public void add(GamePiece piece) {
		pyramid.add(piece);
	}

	public void remove(int number) {
		GamePiece toBeRemoved = null;
		for (GamePiece gamePiece : pyramid)
			if (gamePiece.getNumber() == number)
				toBeRemoved = gamePiece;
		pyramid.remove(toBeRemoved);
	}

	@Override
	public int getNumber() {
		int number = 0;
		for (GamePiece gamePiece : pyramid)
			number += gamePiece.getNumber();
		return number;
	}

	@Override
	public void move(Direction d) {
	}

	public void move(Direction d, int steps) {
		if (steps == 1)
			getPlace().move(d, steps);
		else if ((steps == 2 || steps == 3) && d.orthogonal())
			getPlace().move(d, steps);
	}

}
