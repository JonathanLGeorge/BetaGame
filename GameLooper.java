/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author Jonathan
 */
public class GameLooper extends JPanel implements Runnable, KeyListener{
    public static int HEIGHT = 800; // these feild are the screen 
    public static int WIDTH = 800;
    
    private BufferedImage image; //this is raster, or canvas 
    private Graphics2D g; //paintbrush || look at run()
    private final int FPS = 60; //setting frm cap so we dont over use gpu/cpu, also will mak game not look to fast
    private double FPSave; // this isgettig the average FPS, for testing only
    private Thread thread; 
    private boolean running; 
    
public static  Player player;
    public static ArrayList<Bullet> bullets;
    
    
    //constructor
    public GameLooper() {
        
        super(); // jpanel
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true); //makes game panle focusable, to allow keyboard input
        requestFocus();
    }
    
    
    //I honestly have little understanding of this 
    public void addNotify() {
        super.addNotify();
        
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }
    
    
    //EVERYTHING NEEDS TO GO IN HERE TO UPDATE!!!!
    private void gameUpdate() {
        player.update(); // this is listening for user inputs
        
        
        for(int i = 0; i < bullets.size(); i++ ){
            boolean remove = bullets.get(i).update();
            if(remove){
                bullets.remove(i);
                i--;
            }
        }
    }
    
    private void gameRender(){
        
        //just coloring the backround
        g.setColor(new Color(50,255,50)); 
        g.fillRect(0, 0, WIDTH, HEIGHT); 
        
        //example of how text works
        g.setColor(Color.BLACK);
        g.drawString("this is one way to dra to the screen", 50,50);
        
        //notice that i didnt need to set color again, thats because 
        //its already been set to black
        g.drawString("FPS: " + FPSave, 10, 10);
        
        
        // draws player 
        player.draw(g); // look at player class to change look
        
        //bullet count for testing
        g.drawString("number of bullets in program: " + bullets.size(), 10, 20);
       
        //draw bullets
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).draw(g);        
        }
    }
    
    
    private void gameDraw(){
        //g2 is the actual paint brust thzt draws to the screen 
        Graphics g2 = this.getGraphics();// read up on these things might be useful
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        
    }
    
    
    
    public void keyTyped(KeyEvent key){
        
    }
    
    
    //logic for how user input will work 
    public void keyPressed(KeyEvent key){
        int keyCode = key.getKeyCode();
       
        //player movement
        if(keyCode== KeyEvent.VK_UP){
            player.setUp(true);
        }
        if(keyCode== KeyEvent.VK_DOWN){
            player.setDown(true);
        }  
            if(keyCode== KeyEvent.VK_LEFT){
            player.setLeft(true);
        }
        if(keyCode== KeyEvent.VK_RIGHT){
            player.setRight(true);
        }
        
        
           //player gun fire 
        if(keyCode == KeyEvent.VK_SPACE){
            player.setFiring(true);
        }
            
        }
    
    
    
    public void keyReleased(KeyEvent key){
        int keyCode = key.getKeyCode();
   
        if(keyCode== KeyEvent.VK_UP){
            player.setUp(false);
        }
        if(keyCode== KeyEvent.VK_DOWN){
            player.setDown(false);
        }
        if(keyCode== KeyEvent.VK_LEFT){
            player.setLeft(false);
        }
        if(keyCode== KeyEvent.VK_RIGHT){
            player.setRight(false);
        }
            
            //player cease fire
         if(keyCode == KeyEvent.VK_SPACE){
            player.setFiring(false);
        }
    }
    
    
    
  
    
    
    
    
    //this is where our game loop is located 
    public void run(){
        long startTime;
        long waitTime;
        long totalTime = 0;
        long time_Mil; 
        long targetTime = 1000 / FPS; // one loop to run 60fps
        int frameCount = 0; // book keeping 
        int maxFrame = 60; //FPS max
        
        running = true; //program is running in loop
        
        
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); // 8bit graphic
        g = (Graphics2D)image.getGraphics(); //paintbrush to image
        
       
        

        //adding the player objects
        player = new Player();
        bullets = new ArrayList<Bullet>();
         
        
        
      /*
      
        So this is our simplified game loop
        dont mess with it please. this is how we 
      */
        while(running){
            
            startTime = System.nanoTime(); // current time in nanosec || to good!
            
            
            gameUpdate(); // for now just user input
            gameRender(); // creat frame and draws objects in them  
            gameDraw(); // its what draws on the screen 
            
            
            time_Mil = (System.nanoTime() - startTime)/1000000; //to get millsec
            waitTime = targetTime - time_Mil;
            
            try{
                Thread.sleep(waitTime);
        }
            catch(Exception e) {
                
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
               
            // this is just to see the fps, not really doing anything but book keeping here
            if(frameCount == maxFrame){
                FPSave = 1000.0 / ((totalTime / frameCount) / 1000000); 
                frameCount = 0; //resetting 
                totalTime = 0;
            }
        }
    }
}
