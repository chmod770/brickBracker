package brickbracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener{//JPanel
    private boolean play = false;
    private int score = 0 ;
    
    private int col=5;
    private int row=7;
    private int totalBricks=col*row;
    
    private Timer timer;
    private int delay = 8;
    
    private int playerX  = 310;
    
    private int ballposX  = 120;
    private int ballposY  = 350;
    private int ballXdir  = -1;
    private int ballYdir  = -2;
    
    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(col, row);//utworzenie mapy z trzema rzędami klocków po 7
        totalBricks = col*row;
        addKeyListener(this);//dodanie zdarzenia nasłuchującego przyciski 
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g)
    {
        
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);//plansza bez 8 px;
        
        //drawing map
        map.draw((Graphics2D)g);
        
        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 600);//od lewego górengo rogu, prostokąt o szerokości 3 i wysokości 592(lewa strona)
        g.fillRect(0, 0, 700, 3);//od lewego górengo rogu, prostokąt  o szerokości 692  i wysokości 3(góra)
        g.fillRect(691, 0, 3, 600);//od prawego górengo rogu, prostokąt o szerokości 3 i wysokości 592(prawa strona strona)
        
        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(score+"" , 590, 30);
        
        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        
        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        if(totalBricks<=0)
        {
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, Scores: "+score , 190, 300);
            
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press enter to restart" , 230, 350);
        }
        
        if(ballposY>570)
        {
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, Scores: "+score , 190, 300);
            
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press enter to restart" , 230, 350);
        }
        
        g.dispose();

    }
    

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode()==KeyEvent.VK_RIGHT)
        {
            if(playerX<=580){
               moveRight();
        }
        }
        if(ke.getKeyCode()==KeyEvent.VK_LEFT)
        {
            if(playerX>=30){
                moveLeft();
            }
        }
        if(ke.getKeyCode()==KeyEvent.VK_ENTER)
        {
            if(!play)
            {
                setStartValues();
            }
        }
    }
 

    @Override
    public void keyReleased(KeyEvent ke) {}

    @Override
    public void actionPerformed(ActionEvent ae) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX,ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
               reverseYdirection();
            }
        
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {
                if(map.map[i][j].isActive()){
                    int brickX = j* map.getBrickWidth()+80;//przesuniędzcie w prawo o 80
                    int brickY = i* map.getBrickHeight()+50;//przesuniędzie w dół o 50
                    
                    Rectangle brickRect = new Rectangle(brickX,brickY, map.getBrickWidth(), map.getBrickHeight());//utworzenie ramki nowego kolocka, o wyliczonych wcześniej wymiarach
                    Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);//utworznie ramki wogół piłki
                    
                    if(ballRect.intersects(brickRect)){
                        map.map[i][j].unactiveBrick();
                        totalBricks--;
                        score+=5;
                        
                        if(ballposX+19<=brickRect.x || ballposX+1>=brickRect.x+brickRect.width){
                            reverseXdirection();
                        }else{
                           reverseYdirection();
                        }
                      
                        i=map.map.length-1;
                        break;                      
                    }
                }
            }
        }
        
        
            ballposX += ballXdir;
            ballposY +=ballYdir;
            //checking left and right border
            if(ballposX<0 || ballposX>670)
            {
                reverseXdirection();
            }
            
            //checking top border
            if(ballposY<0)
            {
                reverseYdirection();
            }
            
        }
        repaint();
    }
    
    
    //move methods
    private void moveRight() {
        this.play = true;
        playerX+=20;
    }

    private void moveLeft() {
        this.play = true;
        playerX-=20;
    }  
    
    private void reverseXdirection()
    {
        ballXdir = -ballXdir;
    }
    
    private void reverseYdirection()
    {
        ballYdir = -ballYdir;
    }
    
    private void setStartValues()
    {
        play = true;
                ballposX =120;
                ballposY= 350;
                ballXdir = -2;
                ballYdir = -2;
                score = 0 ;
                map = new MapGenerator(3, 7);
                totalBricks = col*row;
    }
    
    
}
