package sk.stuba.fei.uim.oop.controls;


import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.board.Tile;
import sk.stuba.fei.uim.oop.board.TileStatus;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@Setter @Getter
public class GameLogic extends UniversalAdapter {
    private static final String RESTART_BUTTON_NAME = "Restart";
    private static final String CHECK_BUTTON_NAME = "Check";
    private static final int INITIAL_BOARD_SIZE = 8;
    private static final int INITIAL_BOARD_LEVEL = 1;
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
        this.currentLevel = INITIAL_BOARD_LEVEL;
        this.currentLevelLabel = new JLabel();
        this.boardSizeLabel = new JLabel();
        updateCurrentLevelLabel();
        updateBoardSizeLabel();
    }

    private void updateBoardSizeLabel() {
        this.boardSizeLabel.setText("Board size : " + currentBoardSize);
        this.gameWindow.revalidate();
        this.gameWindow.repaint();
    }

    private void updateCurrentLevelLabel() {
        this.currentLevelLabel.setText("Level : " + currentLevel );
        this.gameWindow.revalidate();
        this.gameWindow.repaint();
    }

    private void initializeBoard(int dimension) {
        this.gameBoard = new Board(dimension);
        this.gameBoard.addMouseListener(this);
        this.gameBoard.addMouseMotionListener(this);
    }

    private void restartGame() {
        this.gameWindow.remove(this.getGameBoard());
        this.initializeBoard(this.getCurrentBoardSize());
        this.gameWindow.add(this.getGameBoard());
        this.updateCurrentLevelLabel();
    }

    public void paintValidTiles(ArrayList<Tile> validTiles) {
        for (Tile tile : validTiles) {
            tile.setValidTile(true);
        }
        this.getGameBoard().repaint();
        this.getGameBoard().revalidate();
    }

    private boolean checkPreviousPipe(Tile prevTile,ArrayList<Tile> validTiles,int angle) {
        if (prevTile.getAngle() != angle) {
            return false;
        } else {
            if (!validTiles.contains(prevTile)) {
                validTiles.add(prevTile);
            }
            return true;
        }
    }

    private boolean checkPreviousLPipe(Tile prevtile,ArrayList<Tile> validTiles,int angle1,int angle2) {
        if (prevtile.getAngle() != angle1 && prevtile.getAngle() != angle2) {
            return false;
        } else {
            if (!validTiles.contains(prevtile)) {
                validTiles.add(prevtile);
            }
            return true;
        }
    }

    private boolean checkActualPipe(Tile actualTile,int angle) {
        return actualTile.getAngle() == angle;
    }

    private boolean checkActualLPipe(Tile actualTile,int angle1,int angle2) {
        return actualTile.getAngle() == angle1 || actualTile.getAngle() == angle2;
    }

    private int checkConnection(Tile prevTile,Tile actualTile,ArrayList<Tile> validTiles,int countInvalid) {
        int divRow = actualTile.getRow() - prevTile.getRow();
        int divCol = actualTile.getCol() - prevTile.getCol();
        int straightPipeAngle;
        int angle1;
        int angle2;
        int div;
        if (divRow != 0) {
            div = divRow;
            straightPipeAngle = 90;
            angle1 = 90;
            angle2 = 270;
        } else {
            div = divCol;
            straightPipeAngle = 0;
            angle1 = 270;
            angle2 = 90;
        }

        if (prevTile.getTileStatus().equals(TileStatus.PIPE)) {
            if (!checkPreviousPipe(prevTile, validTiles, straightPipeAngle)) {
                countInvalid++;
            } else {
                if(checkActualTileStatus(actualTile,div,straightPipeAngle,angle1,angle2)) {
                    countInvalid = 0;
                } else {
                    countInvalid++;
                }
            }
        } else if (prevTile.getTileStatus().equals(TileStatus.L_PIPE)) {
            int expectedStartAngle = (div > 0) ? 180 : 0;
            int expectedEndAngle = (div > 0) ? angle2 : angle1;
            if (!checkPreviousLPipe(prevTile,validTiles,expectedStartAngle,expectedEndAngle)) {
                countInvalid++;
            } else {
                if(checkActualTileStatus(actualTile,div,straightPipeAngle,angle1,angle2)) {
                    countInvalid = 0;
                } else {
                    countInvalid++;
                }
            }
        }
        return countInvalid;
    }

    private boolean checkActualTileStatus(Tile actualTile,int div,int straightPipeAngle,int angle1,int angle2) {
        if (actualTile.getTileStatus().equals(TileStatus.PIPE)) {
            return checkActualPipe(actualTile, straightPipeAngle);
        } else {
            int expectedLPipeStartAngle = (div > 0) ? 0 : 180;
            int expectedLPipeEndAngle = (div > 0) ? angle1 : angle2;
            return checkActualLPipe(actualTile, expectedLPipeStartAngle, expectedLPipeEndAngle);
        }
    }
    private void checkPipes() {
        ArrayList<Tile> validTiles = new ArrayList<>();
        Tile prevTile = this.getGameBoard().getStartTile();
        int countInvalid;
        while (true) {
            if (prevTile == this.getGameBoard().getEndTile()) {
                setCurrentLevel(this.getCurrentLevel()+1);
                restartGame();
            }
            countInvalid = 0;
            for (Tile actualTile : prevTile.getNeighbours()) {
                if (validTiles.contains(actualTile)) {
                    countInvalid++;
                    continue;
                }
                countInvalid = checkConnection(prevTile,actualTile,validTiles,countInvalid);
                if (countInvalid == 0) {
                    prevTile = actualTile;
                    break;
                }
            }
            if (countInvalid == prevTile.getNeighbours().size()) {
                if (!validTiles.contains(prevTile)) {
                    validTiles.add(prevTile);
                }
                paintValidTiles(validTiles);
                break;
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R) {
            this.setCurrentLevel(INITIAL_BOARD_LEVEL);
            restartGame();
            this.getGameWindow().revalidate();
            this.getGameWindow().repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.getGameWindow().dispose();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            checkPipes();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            if (((JButton) source).getText().equals(RESTART_BUTTON_NAME)) {
                this.setCurrentLevel(INITIAL_BOARD_LEVEL);
                restartGame();
                this.getGameWindow().revalidate();
                this.getGameWindow().repaint();
            }
            if (((JButton) source).getText().equals(CHECK_BUTTON_NAME)) {
                checkPipes();
            }
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        Component currentComponent = this.getGameBoard().getComponentAt(e.getX(),e.getY());
        if (!(currentComponent instanceof Tile)) {
            for (Component component : this.getGameBoard().getComponents()) {
                if (component instanceof Tile) {
                    ((Tile) component).setHover(false);
                }
            }
        } else {
            ((Tile) currentComponent).setHover(true);
            for (Component component : this.getGameBoard().getComponents()) {
                if (component instanceof Tile) {
                    if (component != currentComponent) {
                        ((Tile) component).setHover(false);
                    }
                }
            }
        }
        this.getGameBoard().repaint();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Component currentComponent = this.getGameBoard().getComponentAt(e.getX(),e.getY());
        if (!(currentComponent instanceof Tile)) {
            return;
        }
        if (((Tile) currentComponent).isPlayable()) {
            this.getGameBoard().deletePipeColor();
            ((Tile) currentComponent).increaseAngle();
            currentComponent.repaint();
        }
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        if (this.getCurrentBoardSize() != ((JSlider) e.getSource()).getValue()) {
            this.setCurrentBoardSize(((JSlider) e.getSource()).getValue());
            this.updateBoardSizeLabel();
            this.setCurrentLevel(1);
            this.restartGame();
        }
    }
}
