package sk.stuba.fei.uim.oop.controls;


import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

@Setter @Getter
public class GameLogic extends UniversalAdapter{
    public static final int INITIAL_BOARD_SIZE = 8;
    private JFrame gameWindow;
    private Board gameBoard;
    private int currentBoardSize;
    private JLabel currentLevelLabel;
    private int currentLevel;
    private JLabel boardSizeLabel;

    public GameLogic(JFrame gameWindow) {
        this.gameWindow = gameWindow;
        this.currentBoardSize = INITIAL_BOARD_SIZE;

        this.initializeBoard(currentBoardSize);
        this.gameWindow.add(this.gameBoard);
        this.currentLevel = 1;
        this.currentLevelLabel = new JLabel();
        this.boardSizeLabel = new JLabel();
        updateCurrentLevelLabel();
        updateBoardSizeLabel();
    }


    private void updateCurrentLevelLabel() {
        this.currentLevelLabel.setText("Level : " + currentLevel );
        this.gameWindow.revalidate();
        this.gameWindow.repaint();
    }

    private void updateBoardSizeLabel() {
        this.boardSizeLabel.setText("Current board size : " + this.currentBoardSize);
        this.gameWindow.revalidate();
        this.gameWindow.repaint();
    }

    private void initializeBoard(int dimension) {
        this.gameBoard = new Board(dimension);
        this.gameBoard.addMouseListener(this);

    }

    private void restartGame() {
        this.gameWindow.remove(this.getGameBoard());
        this.initializeBoard(this.getCurrentBoardSize());
        this.gameWindow.add(this.getGameBoard());
        this.updateBoardSizeLabel();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R) {
            System.out.println("Key R was pressed, restarting game!");
            restartGame();
            this.getGameWindow().revalidate();
            this.getGameWindow().repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Key Esc was pressed, quiting the game!");
            this.getGameWindow().dispose();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("Key Enter was pressed, checking the pipes!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            if (((JButton) source).getText().equals("Restart")) {
                System.out.println("RestartButton restarting the game!");
                restartGame();
                this.getGameWindow().revalidate();
                this.getGameWindow().repaint();
            }
            if (((JButton) source).getText().equals("Check pipes")) {
                System.out.println("Check pipe button, checking pipes!");
            }
        }

        if (source instanceof JRadioButton) {
            System.out.println("DSASASASA");
            if (((JRadioButton) source).getText().equals("8x8")) {
                if (this.getCurrentBoardSize() != 8) {
                    System.out.println("Changing border size to 8x8!");
                    this.setCurrentBoardSize(8);
                    restartGame();
                    this.getGameWindow().revalidate();
                    this.getGameWindow().repaint();
                }
            }
            if (((JRadioButton) source).getText().equals("10x10")) {
                if (this.getCurrentBoardSize() != 10) {
                    System.out.println("Changing border size to 8x8!");
                    this.setCurrentBoardSize(10);
                    restartGame();
                    this.getGameWindow().revalidate();
                    this.getGameWindow().repaint();
                }
            }
            if (((JRadioButton) source).getText().equals("12x12")) {
                if (this.getCurrentBoardSize() != 12) {
                    System.out.println("Changing border size to 8x8!");
                    this.setCurrentBoardSize(12);
                    restartGame();
                    this.getGameWindow().revalidate();
                    this.getGameWindow().repaint();
                }
            }
        }
    }

}
