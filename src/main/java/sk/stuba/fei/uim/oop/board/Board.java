package sk.stuba.fei.uim.oop.board;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Board extends JPanel {
    private Tile[][] board;
    private Random random;

    public Board(int dimension) {
        initializeBoard(dimension);
        this.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
    }

    private void initializeBoard(int dimension) {
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
    }

    public void generatePipes() {
        Stack<Tile> pipesRoute = new Stack<>();
        List<Tile> visitedTiles = new ArrayList<>();

        int startRow = random.nextInt(this.board.length);
        boolean chooseNumber = random.nextBoolean();

        int startCol = chooseNumber ? 0 : this.board.length-1;
        int finalCol = chooseNumber ? this.board.length-1 : 0;

        Tile startTile = this.board[startRow][startCol];
        int visitedCells = 1;
       // System.out.println("Start row " + startRow);
        //System.out.println("Start col " + startCol);
        //System.out.println("Final col " + finalCol);

        while (visitedCells < this.board.length * this.board.length) {
            //
            // System.out.println("Row = " + startTile.row + " Col = " + startTile.col + "VisitedCells = " + visitedCells);
            if (!visitedTiles.contains(startTile)){
                visitedTiles.add(startTile);
            }
            if (!pipesRoute.contains(startTile)){
                pipesRoute.push(startTile);
            }

            if (startTile.col == finalCol) {
                //System.out.println("Ending");
                break;
            }
            List<Tile> unvisitedNeighbours = getUnvisited(startTile.row,startTile.col,visitedTiles);
            if (!unvisitedNeighbours.isEmpty()) {
               // System.out.println("Not empty");
                Tile neighbour = unvisitedNeighbours.get(this.random.nextInt(unvisitedNeighbours.size()));
                startTile = neighbour;
                visitedCells++;
            } else {
                //System.out.println("Deleting");
                //System.out.printf("Row1 = %d col1 = %d\n",pipesRoute.peek().row,pipesRoute.peek().col);
                pipesRoute.pop();
                //System.out.printf("Row1 = %d col1 = %d\n",pipesRoute.peek().row,pipesRoute.peek().col);
                startTile = pipesRoute.peek();
            }
        }
        int i=0;
        for (Tile tile : pipesRoute) {
            i++;
            JLabel m = new JLabel(String.valueOf(i));
            tile.setPlayable(true);
            tile.setBackground(new Color(209, 209, 224));
            tile.add(m);
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
       // System.out.println("Size = " + unvisitedNeighbours.size());
        Collections.shuffle(unvisitedNeighbours);
        for (Tile tile : unvisitedNeighbours) {
         //   System.out.printf("Tile row = %d col = %d\n",tile.row,tile.col);
        }
        return unvisitedNeighbours;
    }


}
