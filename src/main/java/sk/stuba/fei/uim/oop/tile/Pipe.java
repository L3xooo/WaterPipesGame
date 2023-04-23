package sk.stuba.fei.uim.oop.tile;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public abstract class Pipe extends Tile{
    @Getter @Setter
    private List<Tile> neighbours;
    @Getter @Setter
    private int angle;
    @Getter
    private final Random rand;
    @Getter @Setter
    private boolean validTile;

    public Pipe(int row, int col) {
        super(row, col);
        this.neighbours = new ArrayList<>();
        this.rand = new Random();
        this.validTile = false;
        this.angle = 0;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
