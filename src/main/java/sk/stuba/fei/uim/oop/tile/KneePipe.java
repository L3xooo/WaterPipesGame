package sk.stuba.fei.uim.oop.tile;

import java.awt.*;

public class KneePipe extends Pipe {

    public KneePipe(int row,int col){
        super(row,col);
        this.setAngle(this.getRand().nextInt(4)*90);
    }
    public KneePipe(int row,int col,int angle){
        super(row,col);
        this.setAngle(angle);
    }
    @Override
    public void increaseAngle(){
        this.setAngle((this.getAngle()+90)%360);
    }

    private void paintWater(Graphics g){
        g.setColor(Color.BLUE);
        int[] x = {5,(getWidth()/3)+5,(getWidth()/3)+5,(getWidth()/3*2)-5,(getWidth()/3*2)-5,5,5};
        int[] y = {getHeight()/3+5,getHeight()/3+5,5,5,getHeight()/3*2-5,getHeight()/3*2-5,getHeight()/3};
        g.fillPolygon(x,y,7);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(this.getAngle()),(double) getWidth()/2,(double) getHeight()/2);
        int[] x = {5,getWidth()/3,getWidth()/3,getWidth()/3*2,getWidth()/3*2,5,5};
        int[] y = {getHeight()/3,getHeight()/3,5,5,getHeight()/3*2,getHeight()/3*2,getHeight()/3};
        g.setColor(new Color(73,70,70,255));
        g.fillPolygon(x,y,7);
        if (this.isValidTile()) {
            paintWater(g);
        }
        g.setColor(Color.BLACK);
        g.fillRect(0,this.getHeight()/3-5,5,this.getHeight()/3+10);
        g.fillRect(getWidth()/3-5,0,this.getWidth()/3+10,5);
    }
}
