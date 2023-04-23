package sk.stuba.fei.uim.oop.tile;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    @Getter
    private final int row;
    @Getter
    private final int col;
    @Setter @Getter
    private boolean hover;
    public Tile(int row,int col){
        this.row = row;
        this.col = col;
        this.hover = false;
        this.setBackground(Color.lightGray);
    }

    public void setValidTile(boolean value){}
    public void setNeighbours(Tile neighbourTile){}
    public void increaseAngle(){}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.isHover()){
            this.setBorder(BorderFactory.createLineBorder(Color.red,2));
        } else {
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
    }
}


