package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

@Setter @Getter
public class Tile extends JPanel{
    private boolean playable;
    private boolean hover;
    int row;
    int col;
    Random rand;
    private int angle;
    private TileStatus tileStatus;
    public Tile(int row, int col){
        this.tileStatus = TileStatus.EMPTY;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(new Color(208, 219, 219));
        this.row = row;
        this.col = col;
        this.hover = false;
        this.rand = new Random();
        this.angle = 0;
    }
    public void increaseAngle() {
        this.angle += 90;
        if (this.tileStatus.equals(TileStatus.PIPE)) {
            if (angle == 180) {
                this.angle = 0;
            }
        }
        if (this.tileStatus.equals(TileStatus.L_PIPE)) {
            if (angle == 360) {
                this.angle = 0;
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (this.getTileStatus().equals(TileStatus.L_PIPE) ||this.getTileStatus().equals(TileStatus.PIPE)) {
            if (this.isPlayable()) {
                if (this.isHover()) {
                    g.setColor(new Color(56, 201, 40));
                    this.setHover(false);
                } else {
                    g.setColor(Color.BLACK);
                }
            }

            g2d.rotate(Math.toRadians(this.angle),(double) getWidth()/2,(double) getHeight()/2);

            if (this.tileStatus.equals(TileStatus.PIPE)) {
                g.fillRect(0,this.getHeight()/3-5,5,this.getHeight()/3+10);
                g.fillRect(this.getWidth()-5,this.getHeight()/3-5,5,this.getHeight()/3+10);
                g.setColor(new Color(61, 62, 64));
                g.fillRect(5,this.getHeight()/3,this.getWidth()-10,this.getHeight()/3); //5 5 vyska
            } else {
                g.fillRect(0,this.getHeight()/3-5,5,this.getHeight()/3+10);
                g.fillRect(getWidth()/3-5,0,this.getWidth()/3+10,5);
                int[] x = {5,getWidth()/3,getWidth()/3,getWidth()/3*2,getWidth()/3*2,5,5};
                int[] y = {getHeight()/3,getHeight()/3,5,5,getHeight()/3*2,getHeight()/3*2,getHeight()/3};
                g.setColor(new Color(61, 62, 64));
                g.fillPolygon(x,y,7);
            }

        }
    }
}
