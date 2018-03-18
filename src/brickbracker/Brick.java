
package brickbracker;
import java.awt.Color;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Lumi
 */
public class Brick {
    private boolean isActive;
    private Color color;
    
    public Brick()
    {
        isActive = true;
        color = randomColor(); 
    }
    
    private Color randomColor()
    {
         Random rand = new Random();
         float R = rand.nextFloat();
         float G = rand.nextFloat()/2f;
         float B = rand.nextFloat()/2f;
         return new Color(R,G,B);
    }
    
    public void unactiveBrick()
    {
        this.isActive=false;
    }
    
    public boolean isActive()
    {
        return isActive;
    }

    public Color getColor() {
        return this.color;
    }
}
