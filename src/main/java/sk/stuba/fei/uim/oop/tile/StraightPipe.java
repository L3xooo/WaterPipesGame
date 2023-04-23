package sk.stuba.fei.uim.oop.tile;
import java.awt.*;

public class StraightPipe extends Pipe {
    public StraightPipe(int row,int col,int angle){
        super(row,col);
        this.setAngle(angle);
    }
    public StraightPipe(int row,int col){
        super(row,col);
        this.setAngle(this.getRand().nextInt(2)*90);
    }
    @Override
    public void increaseAngle(){
        this.setAngle((this.getAngle()+90)%180);
    }
    private void paintWater(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(5,this.getHeight()/3+5,this.getWidth()-10,this.getHeight()/3-10);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(this.getAngle()),(double) getWidth()/2,(double) getHeight()/2);
        g.setColor(new Color(73,70,70,255));
        g.fillRect(5,this.getHeight()/3,this.getWidth()-10,this.getHeight()/3);
        if (this.isValidTile()) {
            paintWater(g);
        }
        g.setColor(Color.BLACK);
        g.fillRect(0,this.getHeight()/3-5,5,this.getHeight()/3+10);
        g.fillRect(this.getWidth()-5,this.getHeight()/3-5,5,this.getHeight()/3+10);


    }
}
