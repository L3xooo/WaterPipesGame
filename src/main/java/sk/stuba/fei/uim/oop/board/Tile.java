package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@Setter @Getter
public class Tile extends JPanel implements MouseListener {
    @Setter
    @Getter
    private boolean playable;
    int row;
    int col;

    public Tile(int row, int col){
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.ORANGE);
        this.row = row;
        this.col = col;
        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.isPlayable()) {
            g.setColor(Color.red);
            System.out.println(this.getSize());
/*
            g.drawRect(5,this.getHeight()/3,this.getWidth()-10,this.getHeight()/3); //5 5 vyska
            g.fillRect(0,this.getHeight()/3-5,5,this.getHeight()/3+10);
            g.fillRect(this.getWidth()-5,this.getHeight()/3-5,5,this.getHeight()/3+10);
*/


            int[] x = {5,20,20,15};
            int[] y = {this.getHeight()/3,this.getHeight()/3,this.getHeight()/3+10,this.getHeight()/3+10};
            g.drawPolygon(x,y,4);

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
        if (isPlayable()) {
            setBackground(Color.blue);
        }
    }
}
