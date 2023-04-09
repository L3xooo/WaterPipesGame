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
                this.add(this.board[a][b]);
            }
        }
        generatePipes();
        setTileStatus();
    }

    public void generatePipes() {
        List<Tile> visitedTiles = new ArrayList<>();
        int startRow = random.nextInt(this.board.length);
        boolean chooseNumber = random.nextBoolean();
        int startCol = chooseNumber ? 0 : this.board.length-1;
        int finalCol = chooseNumber ? this.board.length-1 : 0;
        Tile startTile = this.board[startRow][startCol];
        int visitedCells = 1;
        while (visitedCells < this.board.length * this.board.length) {
            if (!visitedTiles.contains(startTile)){
                visitedTiles.add(startTile);
            }
            if (!pipesRoute.contains(startTile)){
                pipesRoute.push(startTile);
            }
            if (startTile.col == finalCol) {
                break;
            }
            List<Tile> unvisitedNeighbours = getUnvisited(startTile.row,startTile.col,visitedTiles);
            if (!unvisitedNeighbours.isEmpty()) {
                startTile = unvisitedNeighbours.get(this.random.nextInt(unvisitedNeighbours.size()));
                visitedCells++;
            } else {
                pipesRoute.pop();
                startTile = pipesRoute.peek();
            }
        }
    }
    public List<Tile> getUnvisited(int x, int y,List<Tile> visited) {
        List<Tile> unvisitedNeighbours = new ArrayList<>();
        if (x > 0 && !visited.contains(this.board[x-1][y])) {
            unvisitedNeighbours.add(this.board[x-1][y]);
        }
        if (x < this.board.length-1 && !visited.contains(this.board[x+1][y])) {
            unvisitedNeighbours.add(this.board[x+1][y]);
        }
        if (y > 0 && !visited.contains(this.board[x][y-1])) {
            unvisitedNeighbours.add(this.board[x][y-1]);
        }
        if (y < this.board.length-1 && !visited.contains(this.board[x][y+1])) {
            unvisitedNeighbours.add(this.board[x][y+1]);
        }
        Collections.shuffle(unvisitedNeighbours);
        return unvisitedNeighbours;
    }

    public void setTileStatus() {
        for (int a = 0; a < this.pipesRoute.size(); a++) {
            Tile tile = this.pipesRoute.get(a);
            if (pipesRoute.indexOf(tile) == 0) {
                Tile nextTile = this.pipesRoute.get(1);
                if (nextTile.getRow() != tile.getRow()) {
                    tile.setTileStatus(TileStatus.L_PIPE);
                    if (tile.getCol() == 0) {
                        System.out.println("SA");

                        if (nextTile.getRow() == tile.getRow()+1) {
                            tile.setAngle(270);
                        } else {
                            tile.setAngle(0);
                        }
                    } else {
                        System.out.println("SAs");

                        if (nextTile.getRow() == tile.getRow()+1) {
                            tile.setAngle(180);
                        } else {
                            tile.setAngle(90);
                        }
                    }
                } else {
                    tile.setTileStatus(TileStatus.PIPE);
                }
                tile.add(new JLabel("START"));
                continue;
            }
            if (pipesRoute.indexOf(tile) == pipesRoute.size()-1) {
                Tile prevTile = this.pipesRoute.get(pipesRoute.size()-2);
                if (prevTile.getRow() != tile.getRow()) {
                    tile.setTileStatus(TileStatus.L_PIPE);
                } else {
                    tile.setTileStatus(TileStatus.PIPE);
                }
                tile.add(new JLabel("END"));
                continue;
            }
            Tile nextTile = this.pipesRoute.get(a+1);
            Tile prevTile = this.pipesRoute.get(a-1);
            tile.setPlayable(true);
            if (nextTile.getRow() != prevTile.getRow() && nextTile.getCol() != prevTile.getCol()) {
                tile.setAngle(tile.getRand().nextInt(2)*90);
                tile.setTileStatus(TileStatus.L_PIPE);
            } else {
                tile.setAngle(tile.getRand().nextInt(2)*90);
                tile.setTileStatus(TileStatus.PIPE);
            }
        }
    }
}
