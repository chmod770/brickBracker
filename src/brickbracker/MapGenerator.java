package brickbracker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;


public class MapGenerator {
    public Brick map[][];
    private int brickWidth;
    private int brickHeight;
    
    public MapGenerator(int row, int col)
    {
        map = new Brick[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Brick();
            }
        }  
        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    
    public void draw(Graphics2D g)
    {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j].isActive())
                {    
                   g.setColor(map[i][j].getColor().brighter());
                   g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                   
                   g.setStroke(new BasicStroke(3));
                   g.setColor(Color.black);
                   g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
            }
        }
    }
     
    public Color randomColor()
    {
         Random rand = new Random();
         float R = rand.nextFloat();
         float G = rand.nextFloat()/2f;
         float B = rand.nextFloat()/2f;
         return new Color(R,G,B);
    }

      public int getBrickWidth() {
        return brickWidth;
    }

    public void setBrickWidth(int brickWidth) {
        this.brickWidth = brickWidth;
    }

    public int getBrickHeight() {
        return brickHeight;
    }

    public void setBrickHeight(int brickHeight) {
        this.brickHeight = brickHeight;
    }
    
}
