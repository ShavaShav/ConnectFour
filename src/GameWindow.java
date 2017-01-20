import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameWindow extends JFrame {
	private static final long serialVersionUID = 5446208496818554929L;
	private static final int PLAYER_1 = 1;
	private static final int PLAYER_2 = 2;
	private int width = 650, height = 550;
	
	private int currentPlayer;
	
	public GameWindow(){
		setSize(width, height);
		setVisible(true);
		currentPlayer = PLAYER_1;
		add(new JLabel("Connect Four"), BorderLayout.PAGE_START);
		add(new GameBoard(), BorderLayout.CENTER);
		add(new JLabel("Player Info Here"), BorderLayout.PAGE_END);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private class GameBoard extends JPanel {
		private static final long serialVersionUID = 4757271972473024822L;
		private Board board;
		private int rowSize, colSize;
		
		public GameBoard(){
			board = new Board();
			this.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					int column = e.getX()/colSize;
					int row = board.placeToken(currentPlayer, column);
					repaint();
					if (board.checkWin(currentPlayer, row, column)){
						JOptionPane.showMessageDialog(null, currentPlayer==PLAYER_1? "Player 1 Wins!" : "Player 2 Wins!");
						resetBoard();
					} else {
						if (currentPlayer == PLAYER_1)
							currentPlayer = PLAYER_2;
						else
							currentPlayer = PLAYER_1;						
					}
				}
			});
		}
		
		private void resetBoard(){
			board = new Board();
			currentPlayer = PLAYER_1;
			repaint();
		}
	
		public void paintComponent(Graphics g){
			this.colSize = this.getWidth()/Board.COLS;
			this.rowSize = this.getHeight()/Board.ROWS;
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			// draw lines
			paintGrid(g);
			paintTokens(g);
		}
		
		private void paintTokens(Graphics g){
			for (int r = 0, y = 0; r < Board.ROWS; r++, y+=rowSize){
				for (int c = 0, x = 0; c < Board.COLS; c++, x+=colSize){
					int token = board.getCell(r, c);
					if (token > 0){
						switch (token){
							case PLAYER_1: g.setColor(Color.RED);
								break;
							case PLAYER_2: g.setColor(Color.BLUE);
								break;
						}
						g.fillOval(x, y, colSize, rowSize);
					}
				}
			}
		}
		
		private void paintGrid(Graphics g){
			g.setColor(Color.BLACK);
			for (int x = 0; x <= this.getWidth(); x += colSize){
				g.drawLine(x, 0, x, this.getHeight());
			}
			for (int y = this.getHeight(); y > 20; y -= rowSize){
				g.drawLine(0, y, this.getWidth(), y);
			}
		}
	}
}