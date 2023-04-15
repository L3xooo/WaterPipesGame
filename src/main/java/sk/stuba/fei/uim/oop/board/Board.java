package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
@Setter @Getter
public class Board extends JPanel {
    private Tile[][] board;
    private Random random;
    private Tile startTile;
    private Tile endTile;
    private Stack<Tile> pipesRoute;
    public Board(int dimension) {
        initializeBoard(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
    }

    public void setEndTile(Tile endTile) {
        this.endTile = endTile;
        this.endTile.setTileStatus(TileStatus.PIPE);
        this.endTile.setBackground(Color.PINK);
        this.endTile.add(new JLabel("END"));
    }

    private void setStartTile(Tile startTile) {
        this.startTile = startTile;
    }
    private void initializeBoard(int dimension) {
        this.pipesRoute = new Stack<>();
        this.random = new Random();
        this.board = new Tile[dimension][dimension];
        this.setLayout(new GridLayout(dimension,dimension));
        for (int a = 0; a < this.board.length; a++) {
            for (int b = 0; b < this.board.length; b++) {
                this.board[a][b] = new Tile(a,b);
                this.add(this.board[a][b]);
            }
        }
        generatePipes();
        setTileStatus();
        setNeighbours();
    }

    private void generatePipes() {
        List<Tile> visitedTiles = new ArrayList<>();
        int startRow = getRandom().nextInt(this.getBoard().length);
        boolean chooseNumber = getRandom().nextBoolean();
        int startCol = chooseNumber ? 0 : this.getBoard().length-1;
        int finalCol = chooseNumber ? this.getBoard().length-1 : 0;
        Tile startTile = this.getBoard()[startRow][startCol];
        setStartTile(startTile);

        int visitedCells = 1;
        while (visitedCells < this.getBoard().length * this.getBoard().length) {
            if (!visitedTiles.contains(startTile)){
                visitedTiles.add(startTile);
            }
            if (!this.getPipesRoute().contains(startTile)){
                this.getPipesRoute().push(startTile);
            }
            if (startTile.getCol() == finalCol) {
                setEndTile(startTile);
                break;
            }
            List<Tile> unvisitedNeighbours = getUnvisited(startTile.getRow(), startTile.getCol(),visitedTiles);
            if (!unvisitedNeighbours.isEmpty()) {
                startTile = unvisitedNeighbours.get(this.getRandom().nextInt(unvisitedNeighbours.size()));
                visitedCells++;
            } else {
                this.getPipesRoute().pop();
                startTile = this.getPipesRoute().peek();
            }
        }
    }
    private List<Tile> getUnvisited(int x, int y,List<Tile> visited) {
        List<Tile> unvisitedNeighbours = new ArrayList<>();
        if (x > 0 && !visited.contains(this.getBoard()[x-1][y])) {
            unvisitedNeighbours.add(this.getBoard()[x-1][y]);
        }
        if (x < this.getBoard().length-1 && !visited.contains(this.getBoard()[x+1][y])) {
            unvisitedNeighbours.add(this.getBoard()[x+1][y]);
        }
        if (y > 0 && !visited.contains(this.getBoard()[x][y-1])) {
            unvisitedNeighbours.add(this.getBoard()[x][y-1]);
        }
        if (y < this.getBoard().length-1 && !visited.contains(this.getBoard()[x][y+1])) {
            unvisitedNeighbours.add(this.getBoard()[x][y+1]);
        }
        Collections.shuffle(unvisitedNeighbours);
        return unvisitedNeighbours;
    }

    private void setNeighbours() {
        for (int a = 0; a < this.getPipesRoute().size(); a++) {
            Tile tile = this.getPipesRoute().get(a);
            if (tile.getRow() > 0 && !this.getBoard()[tile.getRow()-1][tile.getCol()].getTileStatus().equals(TileStatus.EMPTY)) {
                tile.getNeighbours().add(this.getBoard()[tile.getRow()-1][tile.getCol()]);
            }
            if (tile.getRow() < this.getBoard().length-1 && !this.getBoard()[tile.getRow()+1][tile.getCol()].getTileStatus().equals(TileStatus.EMPTY)) {
                tile.getNeighbours().add(this.getBoard()[tile.getRow()+1][tile.getCol()]);
            }
            if (tile.getCol() > 0 && !this.getBoard()[tile.getRow()][tile.getCol()-1].getTileStatus().equals(TileStatus.EMPTY)) {
                tile.getNeighbours().add(this.getBoard()[tile.getRow()][tile.getCol()-1]);
            }
            if (tile.getCol() < this.getBoard().length-1 && !this.getBoard()[tile.getRow()][tile.getCol()+1].getTileStatus().equals(TileStatus.EMPTY)) {
                tile.getNeighbours().add(this.getBoard()[tile.getRow()][tile.getCol()+1]);
            }
        }
    }

    public void deleteBackgroundColor() {
        for (int a = 0; a < this.getPipesRoute().size()-1; a++) {
            this.getPipesRoute().get(a).setBackground(new Color(208, 219, 219));
            if (this.getPipesRoute().get(a) == this.getStartTile()) {
                this.getStartTile().setBackground(Color.ORANGE);
            }
        }
    }

    private void setTileStatus() {
        for (int a = 0; a < this.getPipesRoute().size()-1; a++) {
            Tile tile = this.getPipesRoute().get(a);
            if (this.getPipesRoute().indexOf(tile) == 0) {
                Tile nextTile = this.getPipesRoute().get(1);
                if (nextTile.getRow() != tile.getRow()) {
                    tile.setTileStatus(TileStatus.L_PIPE);
                    if (tile.getCol() == 0) {
                        if (nextTile.getRow() == tile.getRow()+1) {
                            tile.setAngle(270);
                        } else {
                            tile.setAngle(0);
                        }
                    } else {
                        if (nextTile.getRow() == tile.getRow()+1) {
                            tile.setAngle(180);
                        } else {
                            tile.setAngle(90);
                        }
                    }
                } else {
                    tile.setTileStatus(TileStatus.PIPE);
                }
                tile.setBackground(Color.ORANGE);
                continue;
            }
            Tile nextTile = this.getPipesRoute().get(a+1);
            Tile prevTile = this.getPipesRoute().get(a-1);
            tile.setPlayable(true);
            if (nextTile.getRow() != prevTile.getRow() && nextTile.getCol() != prevTile.getCol()) {
                tile.setAngle(tile.getRand().nextInt(4)*90);
                tile.setTileStatus(TileStatus.L_PIPE);
            } else {
                tile.setAngle(tile.getRand().nextInt(2)*90);
                tile.setTileStatus(TileStatus.PIPE);
            }
        }
    }
}
