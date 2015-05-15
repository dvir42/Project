package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pieces.Circle;
import pieces.GamePiece;
import pieces.Place;
import pieces.Pyramid;
import pieces.Square;
import pieces.Triangle;

public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = -6436319130451785316L;

	public static final int SIZE_X = 8;
	public static final int SIZE_Y = 16;

	private final JButton[][] graphpieces;
	private final GamePiece[][] pieces;

	private int toBeMovedI;
	private int toBeMovedJ;

	public Board() {
		graphpieces = new JButton[SIZE_Y][SIZE_X];
		pieces = new GamePiece[SIZE_Y][SIZE_X];
		JFrame frame = new JFrame("Rithmomachy");
		frame.setSize(SIZE_X * GamePiece.PANEL_SIZE + 10, SIZE_Y
				* GamePiece.PANEL_SIZE + 30);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setResizable(false);
		setLayout(new GridLayout(SIZE_Y, SIZE_X));
		repaint();
		for (int i = 0; i < graphpieces.length; i++) {
			for (int j = 0; j < graphpieces[i].length; j++) {
				graphpieces[i][j] = new JButton();
				makeInvisible(graphpieces[i][j]);
				graphpieces[i][j].addActionListener(this);
				graphpieces[i][j].setActionCommand(i + "," + j);
				add(graphpieces[i][j]);
			}
		}
		init();
		frame.setVisible(true);
	}

	private static void makeInvisible(JButton b) {
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
	}

	public void putPiece(PieceType type, int i, int j, int number) {
		graphpieces[i][j].setIcon(new ImageIcon("imgs/" + type + number
				+ ".png"));
		switch (type) {
		case circleb:
			pieces[i][j] = new Circle(number, new Place(j, i), Color.black);
			break;
		case circlew:
			pieces[i][j] = new Circle(number, new Place(j, i), Color.white);
			break;
		case triangleb:
			pieces[i][j] = new Triangle(number, new Place(j, i), Color.black);
			break;
		case trianglew:
			pieces[i][j] = new Triangle(number, new Place(j, i), Color.white);
			break;
		case squareb:
			pieces[i][j] = new Square(number, new Place(j, i), Color.black);
			break;
		case squarew:
			pieces[i][j] = new Square(number, new Place(j, i), Color.white);
			break;
		case pyramidb:
			pieces[i][j] = new Pyramid(new Place(j, i), Color.black);
			graphpieces[i][j].setIcon(new ImageIcon("imgs/" + type + ".png"));
			break;
		case pyramidw:
			pieces[i][j] = new Pyramid(new Place(j, i), Color.white);
			graphpieces[i][j].setIcon(new ImageIcon("imgs/" + type + ".png"));
			break;
		}
	}

	public GamePiece removePiece(int i, int j) {
		graphpieces[i][j].setIcon(null);
		GamePiece piece = pieces[i][j];
		pieces[i][j] = null;
		return piece;
	}

	public boolean containsPiece(int i, int j) {
		return pieces[i][j] != null;
	}

	public boolean blocked(int startI, int startJ, int endI, int endJ) {
		if (startI < endI) {
			if (startJ < endJ) {
				for (int i = startI, j = startJ; i <= endI || j <= endJ; i++, j++)
					if (containsPiece(i, j))
						return true;
				return false;
			} else {
				for (int i = startI, j = endJ; i <= endI || j >= startJ; i++, j--)
					if (containsPiece(i, j))
						return true;
				return false;
			}
		} else {
			if (startJ < endJ) {
				for (int i = endI, j = startJ; i >= endI || j <= endJ; i--, j++)
					if (containsPiece(i, j))
						return true;
				return false;
			} else {
				for (int i = endI, j = endJ; i >= endI || j >= startJ; i--, j--)
					if (containsPiece(i, j))
						return true;
				return false;
			}
		}
	}

	public void movements(int i, int j) {
		for (Place place : pieces[i][j].movements()) {
			if (place != null && !blocked(i, j, place.getY(), place.getX()))
				graphpieces[place.getY()][place.getX()].setIcon(new ImageIcon(
						"imgs/dot.png"));
		}
	}

	private void removeDots() {
		for (JButton[] bs : graphpieces)
			for (JButton b : bs)
				if (isDot(b))
					b.setIcon(null);
	}

	private boolean isDot(int i, int j) {
		return graphpieces[i][j].getIcon() != null
				&& graphpieces[i][j].getIcon().getIconHeight() == 58;
	}

	private boolean isDot(JButton b) {
		return b.getIcon() != null && b.getIcon().getIconHeight() == 58;
	}

	public void move(int startI, int startJ, int endI, int endJ) {
		if (isDot(endI, endJ) && pieces[startI][startJ] != null) {
			GamePiece piece = removePiece(startI, startJ);
			putPiece(piece.type(), endI, endJ, piece.getNumber());
			piece.move(endJ, endI);
			removeDots();
		}
	}

	private void init() {
		putPiece(PieceType.squareb, 0, 0, 49);
		putPiece(PieceType.squareb, 1, 0, 28);
		putPiece(PieceType.squareb, 0, 1, 121);
		putPiece(PieceType.squareb, 1, 1, 66);
		putPiece(PieceType.triangleb, 2, 0, 16);
		putPiece(PieceType.triangleb, 2, 1, 12);
		putPiece(PieceType.triangleb, 1, 2, 36);
		putPiece(PieceType.triangleb, 1, 3, 30);
		putPiece(PieceType.triangleb, 1, 4, 56);
		putPiece(PieceType.triangleb, 1, 5, 64);
		putPiece(PieceType.squareb, 1, 6, 120);
		putPiece(PieceType.pyramidb, 1, 7, 0);
		putPiece(PieceType.squareb, 0, 6, 225);
		putPiece(PieceType.squareb, 0, 7, 361);
		putPiece(PieceType.triangleb, 2, 6, 90);
		putPiece(PieceType.triangleb, 2, 7, 100);
		putPiece(PieceType.circleb, 2, 2, 9);
		putPiece(PieceType.circleb, 2, 3, 25);
		putPiece(PieceType.circleb, 2, 4, 49);
		putPiece(PieceType.circleb, 2, 5, 81);
		putPiece(PieceType.circleb, 3, 2, 3);
		putPiece(PieceType.circleb, 3, 3, 5);
		putPiece(PieceType.circleb, 3, 4, 7);
		putPiece(PieceType.circleb, 3, 5, 9);

		putPiece(PieceType.squarew, SIZE_Y - 1 - 0, SIZE_X - 1 - 0, 25);
		putPiece(PieceType.squarew, SIZE_Y - 1 - 1, SIZE_X - 1 - 0, 15);
		putPiece(PieceType.squarew, SIZE_Y - 1 - 0, SIZE_X - 1 - 1, 81);
		putPiece(PieceType.squarew, SIZE_Y - 1 - 1, SIZE_X - 1 - 1, 45);
		putPiece(PieceType.trianglew, SIZE_Y - 1 - 2, SIZE_X - 1 - 0, 9);
		putPiece(PieceType.trianglew, SIZE_Y - 1 - 2, SIZE_X - 1 - 1, 6);
		putPiece(PieceType.trianglew, SIZE_Y - 1 - 1, SIZE_X - 1 - 2, 25);
		putPiece(PieceType.trianglew, SIZE_Y - 1 - 1, SIZE_X - 1 - 3, 20);
		putPiece(PieceType.trianglew, SIZE_Y - 1 - 1, SIZE_X - 1 - 4, 42);
		putPiece(PieceType.trianglew, SIZE_Y - 1 - 1, SIZE_X - 1 - 5, 49);
		putPiece(PieceType.pyramidw, SIZE_Y - 1 - 1, SIZE_X - 1 - 6, 0);
		putPiece(PieceType.squarew, SIZE_Y - 1 - 1, SIZE_X - 1 - 7, 153);
		putPiece(PieceType.squarew, SIZE_Y - 1 - 0, SIZE_X - 1 - 6, 169);
		putPiece(PieceType.squarew, SIZE_Y - 1 - 0, SIZE_X - 1 - 7, 289);
		putPiece(PieceType.trianglew, SIZE_Y - 1 - 2, SIZE_X - 1 - 6, 72);
		putPiece(PieceType.trianglew, SIZE_Y - 1 - 2, SIZE_X - 1 - 7, 81);
		putPiece(PieceType.circlew, SIZE_Y - 1 - 2, SIZE_X - 1 - 2, 4);
		putPiece(PieceType.circlew, SIZE_Y - 1 - 2, SIZE_X - 1 - 3, 16);
		putPiece(PieceType.circlew, SIZE_Y - 1 - 2, SIZE_X - 1 - 4, 36);
		putPiece(PieceType.circlew, SIZE_Y - 1 - 2, SIZE_X - 1 - 5, 64);
		putPiece(PieceType.circlew, SIZE_Y - 1 - 3, SIZE_X - 1 - 2, 2);
		putPiece(PieceType.circlew, SIZE_Y - 1 - 3, SIZE_X - 1 - 3, 4);
		putPiece(PieceType.circlew, SIZE_Y - 1 - 3, SIZE_X - 1 - 4, 6);
		putPiece(PieceType.circlew, SIZE_Y - 1 - 3, SIZE_X - 1 - 5, 8);
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("imgs/grid.png")), 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Board();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int i = Integer.parseInt(e.getActionCommand().split(",")[0]);
		int j = Integer.parseInt(e.getActionCommand().split(",")[1]);
		if (pieces[i][j] != null) {
			movements(i, j);
			toBeMovedI = i;
			toBeMovedJ = j;
		} else if (isDot(i, j)) {
			move(toBeMovedI, toBeMovedJ, i, j);
		}
	}
}
