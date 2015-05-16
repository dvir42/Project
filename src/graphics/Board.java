package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

	public static void makeInvisible(JButton b) {
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

	public GamePiece removePiece(GamePiece piece) {
		return removePiece(piece.getPlace().getY(), piece.getPlace().getX());
	}

	public boolean containsPiece(int i, int j) {
		return i < SIZE_Y && j < SIZE_X && i >= 0 && j >= 0
				&& pieces[i][j] != null;
	}

	public boolean blocked(int startI, int startJ, int endI, int endJ) {
		if (startI < endI) {
			if (startJ < endJ) {
				for (int i = startI + 1, j = startJ + 1; i <= endI || j <= endJ; i++, j++) {
					if (containsPiece(i, j))
						return true;
				}
				return false;
			} else if (startJ > endJ) {
				for (int i = startI + 1, j = startJ - 1; i <= endI || j >= endJ; i++, j--) {
					if (containsPiece(i, j))
						return true;
				}
				return false;
			} else {
				for (int i = startI + 1; i <= endI; i++) {
					if (containsPiece(i, startJ))
						return true;
				}
				return false;
			}
		} else if (startI > endI) {
			if (startJ < endJ) {
				for (int i = startI - 1, j = startJ + 1; i >= endI || j <= endJ; i--, j++) {
					if (containsPiece(i, j))
						return true;
				}
				return false;
			} else if (startJ > endJ) {
				for (int i = startI - 1, j = startJ - 1; i >= endI || j >= endJ; i--, j--) {
					if (containsPiece(i, j))
						return true;
				}
				return false;
			} else {
				for (int i = startI - 1; i >= endI; i--) {
					if (containsPiece(i, startJ))
						return true;
				}
				return false;
			}
		} else {
			if (startJ < endJ) {
				for (int j = startJ + 1; j <= endJ; j++) {
					if (containsPiece(startI, j))
						return true;
				}
				return false;
			} else if (startJ > endJ) {
				for (int j = startJ - 1; j >= endJ; j--) {
					if (containsPiece(startI, j))
						return true;
				}
				return false;
			} else {
				return true;
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
			if (piece instanceof Pyramid)
				((Pyramid) pieces[endI][endJ]).setPyramid(((Pyramid) piece)
						.getPyramid());
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
		((Pyramid) pieces[1][7]).add(new Circle(1, null, Color.black));
		((Pyramid) pieces[1][7]).add(new Triangle(25, null, Color.black));
		((Pyramid) pieces[1][7]).add(new Triangle(36, null, Color.black));
		((Pyramid) pieces[1][7]).add(new Square(49, null, Color.black));
		((Pyramid) pieces[1][7]).add(new Square(64, null, Color.black));
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
		((Pyramid) pieces[SIZE_Y - 1 - 1][SIZE_X - 1 - 6]).add(new Circle(1,
				null, Color.white));
		((Pyramid) pieces[SIZE_Y - 1 - 1][SIZE_X - 1 - 6]).add(new Circle(4,
				null, Color.white));
		((Pyramid) pieces[SIZE_Y - 1 - 1][SIZE_X - 1 - 6]).add(new Triangle(9,
				null, Color.white));
		((Pyramid) pieces[SIZE_Y - 1 - 1][SIZE_X - 1 - 6]).add(new Triangle(16,
				null, Color.white));
		((Pyramid) pieces[SIZE_Y - 1 - 1][SIZE_X - 1 - 6]).add(new Square(25,
				null, Color.white));
		((Pyramid) pieces[SIZE_Y - 1 - 1][SIZE_X - 1 - 6]).add(new Square(36,
				null, Color.white));
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

	private boolean pressedWhitePyramid = false;
	private boolean pressedBlackPyramid = false;

	private final ArrayList<GamePiece> eaten = new ArrayList<>();

	private boolean returnPiece = false;
	private GamePiece returnedPiece = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		int i = Integer.parseInt(e.getActionCommand().split(",")[0]);
		int j = Integer.parseInt(e.getActionCommand().split(",")[1]);
		if (pieces[i][j] != null && !returnPiece) {
			if (pieces[i][j].type() == PieceType.pyramidw
					&& pressedWhitePyramid) {
				((Pyramid) pieces[i][j]).displayPieces();
				pressedWhitePyramid = false;
				pressedBlackPyramid = false;
				return;
			} else if (pieces[i][j].type() == PieceType.pyramidw) {
				pressedWhitePyramid = true;
				pressedBlackPyramid = false;
			} else if (pieces[i][j].type() == PieceType.pyramidb
					&& pressedBlackPyramid) {
				((Pyramid) pieces[i][j]).displayPieces();
				pressedBlackPyramid = false;
				pressedWhitePyramid = false;
				return;
			} else if (pieces[i][j].type() == PieceType.pyramidb) {
				pressedWhitePyramid = false;
				pressedBlackPyramid = true;
			} else {
				pressedWhitePyramid = false;
				pressedBlackPyramid = false;
			}
			removeDots();
			movements(i, j);
			toBeMovedI = i;
			toBeMovedJ = j;
		} else if (returnPiece && isDot(i, j)) {
			returnPiece = false;
			putPiece(returnedPiece.type(), i, j, returnedPiece.getNumber());
			removeDots();
		} else if (isDot(i, j)) {
			move(toBeMovedI, toBeMovedJ, i, j);
			pressedWhitePyramid = false;
			pressedBlackPyramid = false;
			for (GamePiece piece : orthogonalInverted(i, j))
				if (isSieged(piece.getPlace().getY(), piece.getPlace().getX()))
					eaten.add(piece);
			for (GamePiece piece : diagonalInverted(i, j))
				if (isSieged(piece.getPlace().getY(), piece.getPlace().getX()))
					eaten.add(piece);
			for (GamePiece piece : eaten) {
				removePiece(piece);
				returnPiece(piece);
			}
			for (int in = 0; in < eaten.size(); in++) {
				eaten.remove(in);
			}
		}
	}

	public void returnPiece(GamePiece piece) {
		returnPiece = true;
		returnedPiece = piece;
		int count = 0;
		if (piece.getColor() == Color.black) {
			for (int j = 0; j < graphpieces[0].length; j++) {
				if (graphpieces[0][j].getIcon() == null) {
					count++;
					graphpieces[0][j].setIcon(new ImageIcon("imgs/dot.png"));
				}
			}
		} else {
			for (int j = 0; j < graphpieces[SIZE_Y - 1].length; j++) {
				if (graphpieces[SIZE_Y - 1][j].getIcon() == null) {
					count++;
					graphpieces[SIZE_Y - 1][j].setIcon(new ImageIcon(
							"imgs/dot.png"));
				}
			}
		}
		if (count == 0)
			returnPiece = false;
	}

	public boolean inBoard(int i, int j) {
		return i < SIZE_Y && j < SIZE_X && i >= 0 && j >= 0;
	}

	public boolean isInverted(int i1, int j1, int i2, int j2) {
		return inBoard(i1, j1) && inBoard(i2, j2) && pieces[i1][j1] != null
				&& pieces[i2][j2] != null
				&& pieces[i1][j1].getColor() != pieces[i2][j2].getColor();
	}

	public ArrayList<GamePiece> orthogonalInverted(int i, int j) {
		ArrayList<GamePiece> pieces = new ArrayList<>();
		if (isInverted(i, j, i + 1, j))
			pieces.add(this.pieces[i + 1][j]);
		if (isInverted(i, j, i - 1, j))
			pieces.add(this.pieces[i - 1][j]);
		if (isInverted(i, j, i, j + 1))
			pieces.add(this.pieces[i][j + 1]);
		if (isInverted(i, j, i, j - 1))
			pieces.add(this.pieces[i][j - 1]);
		return pieces;
	}

	public ArrayList<GamePiece> diagonalInverted(int i, int j) {
		ArrayList<GamePiece> pieces = new ArrayList<>();
		if (isInverted(i, j, i + 1, j + 1))
			pieces.add(this.pieces[i + 1][j + 1]);
		if (isInverted(i, j, i - 1, j - 1))
			pieces.add(this.pieces[i - 1][j - 1]);
		if (isInverted(i, j, i - 1, j + 1))
			pieces.add(this.pieces[i - 1][j + 1]);
		if (isInverted(i, j, i + 1, j - 1))
			pieces.add(this.pieces[i + 1][j - 1]);
		return pieces;
	}

	public boolean isSieged(int i, int j) {
		return orthogonalInverted(i, j).size() == 4
				|| diagonalInverted(i, j).size() == 4;
	}
}
