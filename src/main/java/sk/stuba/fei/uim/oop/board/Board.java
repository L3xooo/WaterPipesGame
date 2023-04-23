package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.tile.KneePipe;
import sk.stuba.fei.uim.oop.tile.Pipe;
import sk.stuba.fei.uim.oop.tile.StraightPipe;
import sk.stuba.fei.uim.oop.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Board extends JPanel {
    @Getter
    private Tile[][] board;
    @Getter
    private Random random;
    @Getter @Setter
    private Tile startTile;
    @Getter @Setter
    private Tile endTile;
    @Getter
    private Stack<Tile> pipesRoute;
    public Board(int dimension) {
        initializeBoard(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
    }
    private void initializeBoard(int dimension) {
        this.pipesRoute = new Stack<>();
        this.random = new Random();
        this.board = new Tile[dimension][dimension];
        this.setLayout(new GridLayout(dimension,dimension));
        for (int a = 0; a < this.board.length; a++) {
            for (int b = 0; b < this.board.length; b++) {
                this.board[a][b] = new Tile(a,b);
            }
        }
        generatePipes();
        setTileStatus();
        for (Tile[] tiles : this.board) {
            for (int b = 0; b < this.board.length; b++) {
                this.add(tiles[b]);
            }
        }
        setNeighbours();
    }

    private void generatePipes() {
        List<Tile> visitedTiles = new ArrayList<>();
        int startRow = getRandom().nextInt(this.getBoard().length);
        int finalRow = getRandom().nextInt(this.getBoard().length);
        Tile startTile = this.getBoard()[startRow][0];
        int visitedCells = 1;
        while (visitedCells < this.getBoard().length * this.getBoard().length) {
            if (!visitedTiles.contains(startTile)){
                visitedTiles.add(startTile);
            }
            if (!this.getPipesRoute().contains(startTile)){
                this.getPipesRoute().push(startTile);
            }
            if (startTile.getCol() == this.getBoard().length-1 && startTile.getRow() == finalRow) {
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
        for (int a = 0; a < this.getBoard().length; a++) {
            for (int b = 0; b < this.getBoard().length; b++) {
                Tile tile = this.getBoard()[a][b];
                List<Tile> tileNeighbours = new ArrayList<>();
                if (tile instanceof Pipe) {
                    if (tile.getRow() > 0 && this.getBoard()[tile.getRow()-1][tile.getCol()] instanceof Pipe) {
                        tileNeighbours.add(this.getBoard()[tile.getRow()-1][tile.getCol()]);
                    }
                    if (tile.getRow() < this.getBoard().length-1 && this.getBoard()[tile.getRow()+1][tile.getCol()] instanceof Pipe) {
                        tileNeighbours.add(this.getBoard()[tile.getRow()+1][tile.getCol()]);
                    }
                    if (tile.getCol() > 0 && this.getBoard()[tile.getRow()][tile.getCol()-1] instanceof Pipe) {
                        tileNeighbours.add(this.getBoard()[tile.getRow()][tile.getCol()-1]);
                    }
                    if (tile.getCol() < this.getBoard().length-1 && this.getBoard()[tile.getRow()][tile.getCol()+1] instanceof Pipe) {
                        tileNeighbours.add(this.getBoard()[tile.getRow()][tile.getCol()+1]);
                    }
                }
                tile.setNeighbours(tileNeighbours);
            }
        }
    }

    public void deletePipeColor() {
        for (Tile[] tiles : this.board) {
            for (int b = 0; b < this.board.length; b++) {
                if (tiles[b] instanceof Pipe) {
                    tiles[b].setValidTile(false);
                }
            }
        }
        this.repaint();
        this.revalidate();
    }


    private void setTileStatus() {
        for (int a = 0; a < this.getPipesRoute().size(); a++) {
            Tile tile = this.getPipesRoute().get(a);
            if (tile == this.getPipesRoute().get(0)) {
                Tile next = this.getPipesRoute().get(1);
                if (tile.getRow() != next.getRow()) {
                    int angle = next.getRow() > tile.getRow() ? 270 : 0;
                    this.getBoard()[tile.getRow()][tile.getCol()] = new KneePipe(tile.getRow(),tile.getCol(),angle);
                } else {
                    this.getBoard()[tile.getRow()][tile.getCol()] = new StraightPipe(tile.getRow(),tile.getCol(),0);
                }
                setStartTile(this.getBoard()[tile.getRow()][tile.getCol()]);
                this.getBoard()[tile.getRow()][tile.getCol()].setBackground(Color.orange);
                continue;
            }
            if (tile == this.getPipesRoute().lastElement()) {
                Tile next = this.getPipesRoute().get(a-1);
                if (tile.getRow() != next.getRow()) {
                    int angle = next.getRow() > tile.getRow() ? 180 : 90;
                    this.getBoard()[tile.getRow()][tile.getCol()] = new KneePipe(tile.getRow(),tile.getCol(),angle);
                } else {
                    this.getBoard()[tile.getRow()][tile.getCol()] = new StraightPipe(tile.getRow(),tile.getCol(),0);
                }
                setEndTile(this.getBoard()[tile.getRow()][tile.getCol()]);
                this.getBoard()[tile.getRow()][tile.getCol()].setBackground(Color.CYAN);
                continue;
            }
            Tile nextTile = this.getPipesRoute().get(a+1);
            Tile prevTile = this.getPipesRoute().get(a-1);
            if (nextTile.getRow() != prevTile.getRow() && nextTile.getCol() != prevTile.getCol()) {
                this.getBoard()[tile.getRow()][tile.getCol()] = new KneePipe(tile.getRow(),tile.getCol());
            } else {
                this.getBoard()[tile.getRow()][tile.getCol()] = new StraightPipe(tile.getRow(),tile.getCol());
            }
        }
    }
}
