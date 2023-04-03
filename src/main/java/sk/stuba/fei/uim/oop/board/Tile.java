package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

@Setter @Getter
public class Tile extends JPanel implements MouseListener {
    private boolean playable;
    int row;
    int col;
    Random rand;
    private int angle;
    private TileStatus tileStatus;
    public Tile(int row, int col){
        this.tileStatus = TileStatus.EMPTY;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.ORANGE);
        this.row = row;
        this.col = col;
        this.rand = new Random();
        this.angle = rand.nextInt(4)*90;
        this.addMouseListener(this);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (this.isPlayable()) {
            g.setColor(Color.red);
            System.out.println(this.getSize());
            g2d.rotate(Math.toRadians(this.angle),getWidth()/2,getHeight()/2);

            g.drawRect(5,this.getHeight()/3,this.getWidth()-10,this.getHeight()/3); //5 5 vyska
            g.fillRect(0,this.getHeight()/3-5,5,this.getHeight()/3+10);
            g.fillRect(this.getWidth()-5,this.getHeight()/3-5,5,this.getHeight()/3+10);

/*
            g.fillRect(0,this.getHeight()/3-5,5,this.getHeight()/3+10);
            g.fillRect(getWidth()/3-5,0,this.getWidth()/3+10,5);
            int[] x = {5,getWidth()/3,getWidth()/3,getWidth()/3*2,getWidth()/3*2,5,5};
            int[] y = {getHeight()/3,getHeight()/3,5,5,getHeight()/3*2,getHeight()/3*2,getHeight()/3};
            g.drawPolygon(x,y,7);*/
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.angle += 90;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (isPlayable()) {
            this.setBackground(Color.pink);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
