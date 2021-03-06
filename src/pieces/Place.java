package pieces;

import graphics.Board;

public class Place {

	private int x;
	private int y;

	public Place(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move(Direction d, int steps) {
		switch (d) {
		case N:
			if (y + steps < Board.SIZE_Y)
				y += steps;
			break;
		case E:
			if (x + steps < Board.SIZE_X)
				x += steps;
			break;
		case S:
			if (y - steps >= 0)
				y -= steps;
			break;
		case W:
			if (x - steps >= 0)
				x -= steps;
			break;
		case NE:
			if (y + steps < Board.SIZE_Y && x + steps < Board.SIZE_X) {
				y += steps;
				x += steps;
			}
			break;
		case NW:
			if (y + steps < Board.SIZE_Y && x - steps >= 0) {
				y += steps;
				x -= steps;
			}
			break;
		case SE:
			if (y - steps >= 0 && x + steps < Board.SIZE_X) {
				y -= steps;
				x += steps;
			}
			break;
		case SW:
			if (y - steps >= 0 && x - steps >= 0) {
				y -= steps;
				x -= steps;
			}
			break;
		}
	}

	public boolean hasMoved(Place p) {
		return this.x == p.x && this.y == p.y;
	}

	public Place movement(Direction d, int steps) {
		switch (d) {
		case N:
			if (y + steps < Board.SIZE_Y)
				return new Place(x, y + steps);
			break;
		case E:
			if (x + steps < Board.SIZE_X)
				return new Place(x + steps, y);
			break;
		case S:
			if (y - steps >= 0)
				return new Place(x, y - steps);
			break;
		case W:
			if (x - steps >= 0)
				return new Place(x - steps, y);
			break;
		case NE:
			if (y + steps < Board.SIZE_Y && x + steps < Board.SIZE_X) {
				return new Place(x + steps, y + steps);
			}
			break;
		case NW:
			if (y + steps < Board.SIZE_Y && x - steps >= 0) {
				return new Place(x - steps, y + steps);
			}
			break;
		case SE:
			if (y - steps >= 0 && x + steps < Board.SIZE_X) {
				return new Place(x + steps, y - steps);
			}
			break;
		case SW:
			if (y - steps >= 0 && x - steps >= 0) {
				return new Place(x - steps, y - steps);
			}
			break;
		}
		return null;
	}

	@Override
	public String toString() {
		return x + "," + y;
	}

}
