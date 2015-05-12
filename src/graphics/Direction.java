package graphics;

public enum Direction {

	N, E, S, W, NE, NW, SE, SW;

	/**
	 * 
	 * @return True if the direction is orthogonal (horizontal or vertical).
	 */
	public boolean orthogonal() {
		return this.ordinal() < 4;
	}

}
