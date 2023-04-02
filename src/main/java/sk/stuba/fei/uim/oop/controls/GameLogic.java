package sk.stuba.fei.uim.oop.controls;


import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Board;

import javax.swing.*;
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

    @Override
    public void keyPressed(KeyEvent e) {

    }


}
