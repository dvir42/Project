package pieces;

import graphics.Board;
import graphics.PieceType;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	public Place[] movements() {
		return new Place[] { getPlace().movement(Direction.N, 1),
				getPlace().movement(Direction.E, 1),
				getPlace().movement(Direction.S, 1),
				getPlace().movement(Direction.W, 1),
				getPlace().movement(Direction.NE, 1),
				getPlace().movement(Direction.NW, 1),
				getPlace().movement(Direction.SE, 1),
				getPlace().movement(Direction.SW, 1),
				getPlace().movement(Direction.N, 2),
				getPlace().movement(Direction.E, 2),
				getPlace().movement(Direction.S, 2),
				getPlace().movement(Direction.W, 2),
				getPlace().movement(Direction.N, 3),
				getPlace().movement(Direction.E, 3),
				getPlace().movement(Direction.S, 3),
				getPlace().movement(Direction.W, 3), };
	}

	@Override
	public PieceType type() {
		return getColor() == Color.black ? PieceType.pyramidb
				: PieceType.pyramidw;
	}

	private JFrame frame;

	public void displayPieces() {
		if (frame != null)
			frame.dispose();
		frame = new JFrame("Pyramid Pieces");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize((pyramid.size() == 0 ? 1 : pyramid.size())
				* (GamePiece.PANEL_SIZE + 10), GamePiece.PANEL_SIZE + 30);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(new GridLayout(1, pyramid.size()));
		panel.setBackground(Color.lightGray);
		frame.setResizable(false);
		for (GamePiece piece : pyramid) {
			JButton b = new JButton();
			Board.makeInvisible(b);
			b.setIcon(new ImageIcon("imgs/" + piece.type() + piece.getNumber()
					+ ".png"));
			panel.add(b);
		}
		frame.setVisible(true);
	}

	public ArrayList<GamePiece> getPyramid() {
		return pyramid;
	}

	public void setPyramid(ArrayList<GamePiece> pyramid) {
		this.pyramid = pyramid;
	}

}
