package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Setter @Getter
public class Tile extends JPanel{
    private boolean playable;
    private boolean hover;
    private final int row;
    private final int col;
    private final Random rand;
    private int angle;
    private TileStatus tileStatus;
    private final List<Tile> neighbours;
    public Tile(int row, int col){
        this.neighbours = new ArrayList<>();
        this.tileStatus = TileStatus.EMPTY;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(new Color(208, 219, 219));
        this.row = row;
        this.col = col;
        this.hover = false;
        this.rand = new Random();
        this.angle = 0;
    }
    public void increaseAngle() {
        this.angle = (this.angle + 90) % (this.tileStatus == TileStatus.PIPE ? 180 : 360);
    }
    private void drawStraightPipe(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,this.getHeight()/3-5,5,this.getHeight()/3+10);
        g.fillRect(this.getWidth()-5,this.getHeight()/3-5,5,this.getHeight()/3+10);
        g.setColor(new Color(61, 62, 64));
        g.fillRect(5,this.getHeight()/3,this.getWidth()-10,this.getHeight()/3);
    }
    private void drawLPipe(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,this.getHeight()/3-5,5,this.getHeight()/3+10);
        g.fillRect(getWidth()/3-5,0,this.getWidth()/3+10,5);
        int[] x = {5,getWidth()/3,getWidth()/3,getWidth()/3*2,getWidth()/3*2,5,5};
        int[] y = {getHeight()/3,getHeight()/3,5,5,getHeight()/3*2,getHeight()/3*2,getHeight()/3};
        g.setColor(new Color(61, 62, 64));
        g.fillPolygon(x,y,7);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (this.isHover()) {
            this.setBorder(BorderFactory.createLineBorder(Color.red,2));
        } else {
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        if (this.getTileStatus().equals(TileStatus.L_PIPE) || this.getTileStatus().equals(TileStatus.PIPE)) {
            g2d.rotate(Math.toRadians(this.getAngle()),(double) getWidth()/2,(double) getHeight()/2);
            if (this.getTileStatus().equals(TileStatus.PIPE)) {
                drawStraightPipe(g);
            } else {
                drawLPipe(g);
            }
        }
    }
}
