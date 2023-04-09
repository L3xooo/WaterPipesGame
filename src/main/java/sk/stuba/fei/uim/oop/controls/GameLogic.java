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

    private void checkPipes() {
        int count = 0;
        for (int a = 1; a < this.getGameBoard().getPipesRoute().size()-1; a++) {
            Tile currentTile = this.getGameBoard().getPipesRoute().get(a);
            Tile nextTile = this.getGameBoard().getPipesRoute().get(a+1);
            Tile prevTile = this.getGameBoard().getPipesRoute().get(a-1);
            if (currentTile.getTileStatus().equals(TileStatus.PIPE)) {
                if ((currentTile.getRow() == nextTile.getRow()) && (currentTile.getRow() == prevTile.getRow())) {
                    if (currentTile.getAngle() == 0) {
                        currentTile.setBackground(Color.green);
                        count++;
                    } else {
                        currentTile.setBackground(Color.red);
                    }
                }
                if ((currentTile.getCol() == nextTile.getCol()) && (currentTile.getCol() == prevTile.getCol())) {
                    if (currentTile.getAngle() == 90) {
                        currentTile.setBackground(Color.green);
                        count++;
                    } else {
                        currentTile.setBackground(Color.red);
                    }
                }
            }


            if (currentTile.getTileStatus().equals(TileStatus.L_PIPE)) {
                if (prevTile.getRow() == currentTile.getRow()+1) {
                    if (nextTile.getCol() == currentTile.getCol() + 1) {
                        if (currentTile.getAngle() == 180) {
                            currentTile.setBackground(Color.green);
                            count++;
                        } else {
                            currentTile.setBackground(Color.red);
                        }
                    } else {
                        if (currentTile.getAngle() == 270) {
                            count++;
                            currentTile.setBackground(Color.green);
                        } else {
                            currentTile.setBackground(Color.red);
                        }
                    }
                    continue;
                }
                if (prevTile.getRow() == currentTile.getRow()-1) {
                    if (nextTile.getCol() == currentTile.getCol() + 1) {
                        if (currentTile.getAngle() == 90) {
                            count++;
                            currentTile.setBackground(Color.green);
                        } else {
                            currentTile.setBackground(Color.red);
                        }
                    } else {
                        if (currentTile.getAngle() == 0) {
                            count++;
                            currentTile.setBackground(Color.green);
                        } else {
                            currentTile.setBackground(Color.red);
                        }
                    }
                    continue;
                }
                if (prevTile.getCol() == currentTile.getCol()+1) {
                    if (nextTile.getRow() == currentTile.getRow() + 1) {
                        if (currentTile.getAngle() == 180) {
                            count++;
                            currentTile.setBackground(Color.green);
                        } else {
                            currentTile.setBackground(Color.red);
                        }
                    } else {
                        if (currentTile.getAngle() == 90) {
                            count++;
                            currentTile.setBackground(Color.green);
                        } else {
                            currentTile.setBackground(Color.red);
                        }
                    }
                    continue;
                }
                if (prevTile.getCol() == currentTile.getCol()-1) {
                    if (nextTile.getRow() == currentTile.getRow() + 1) {
                        if (currentTile.getAngle() == 270) {
                            count++;
                            currentTile.setBackground(Color.green);
                        } else {
                            currentTile.setBackground(Color.red);
                        }
                    } else {
                        if (currentTile.getAngle() == 0) {
                            count++;
                            currentTile.setBackground(Color.green);
                        } else {
                            currentTile.setBackground(Color.red);
                        }
                    }
                    continue;
                }
            }
        }
        if (count == this.getGameBoard().getPipesRoute().size()-2) {
            this.setCurrentLevel(this.getCurrentLevel()+1);
            restartGame();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R) {
            System.out.println("Key R was pressed, restarting game!");
            this.setCurrentLevel(1);
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
            checkPipes();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            if (((JButton) source).getText().equals("Restart")) {
                System.out.println("RestartButton restarting the game!");
                this.setCurrentLevel(1);
                restartGame();
                this.getGameWindow().revalidate();
                this.getGameWindow().repaint();
            }
            if (((JButton) source).getText().equals("Check pipes")) {
                System.out.println("Check pipe button, checking pipes!");
                checkPipes();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component currentComponent = this.getGameBoard().getComponentAt(e.getX(),e.getY());
        if (!(currentComponent instanceof Tile)) {
            return;
        }
        if (((Tile) currentComponent).isPlayable()) {
            ((Tile) currentComponent).setHover(true);
        }
        this.getGameBoard().repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("klikam");
        Component currentComponent = this.getGameBoard().getComponentAt(e.getX(),e.getY());
        if (!(currentComponent instanceof Tile)) {
            return;
        }
        if (((Tile) currentComponent).isPlayable()) {
            System.out.println("PREd zmemnou = " + ((Tile) currentComponent).getAngle());
            ((Tile) currentComponent).increaseAngle();
            currentComponent.repaint();
            System.out.println("Po zmene " + ((Tile) currentComponent).getAngle());

        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (this.getCurrentBoardSize() != ((JSlider) e.getSource()).getValue()) {
            this.setCurrentBoardSize(((JSlider) e.getSource()).getValue());
            this.setCurrentLevel(1);
            this.restartGame();
        }
    }
}
