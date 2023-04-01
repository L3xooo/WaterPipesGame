package sk.stuba.fei.uim.oop.board;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    private boolean playable;
    int row;
    int col;

    public Tile(int row, int col){
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.ORANGE);
        this.row = row;
        this.col = col;
    }


}
