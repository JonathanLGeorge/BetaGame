/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;
import java.awt.*;
public class Player {
    
    // x_cord and y_cord are location, x_direction/dy are direction
    private int x_cord, y_cord, r, x_direction, y_direction, speed, lives;
    private boolean left, right, up, down, firing;
    private Color player_color;
    private Color color2;
    private long fireTimer, bullet_delay; // why use long? change it and find out :P (nano time)
    
    
    public Player(){
        x_cord = GameLooper.WIDTH / 2; // this will make player start in center of screen 
        y_cord = GameLooper.HEIGHT / 2;
        r = 20; 
        x_direction = 0;
        y_direction = 0;
        speed = 5;
        lives = 5;
        player_color = Color.BLACK;
        
        firing = false;
        fireTimer = System.nanoTime();
        bullet_delay = 100;
    }
    
    public void setLeft(boolean b){
        left = b;
    }
      public void setRight(boolean b){
        right = b;
    }
      public void setUp(boolean b){
        up = b;
    }
      public void setDown(boolean b){
        down = b;
    }
      
      public void setFiring(boolean b){
          firing = b;
      }
    //i might refactor to player Update so its not confusing 
    public void update(){
        if(left){
            x_direction = -speed;
        }
        if(right){
            x_direction = speed;
        }
        if(up){
            y_direction = -speed;
        }
        if(down){
            y_direction = speed;
            
        }
       x_cord +=x_direction;
       y_cord+=y_direction;
       
       if(x_cord < r){
           x_cord = r;
        }
       if(y_cord < r){
           y_cord = r;
       }
       
       //this will keep player from escaping the screen! 
       if(x_cord > GameLooper.WIDTH - r){
           x_cord = GameLooper.WIDTH - r;
       }
       if(y_cord > GameLooper.HEIGHT - r){
           y_cord = GameLooper.HEIGHT - r;
       }
       // will stop movment 
       x_direction = 0; 
       y_direction = 0;
       
       if(firing){
           long elapsed = (System.nanoTime() - fireTimer) / 1000000;
           if(elapsed > bullet_delay){
               GameLooper.bullets.add(new Bullet(270, x_cord, y_cord));
               fireTimer = System.nanoTime();
           }
           
       }
       
       
       
    }
     public void draw(Graphics2D g){
            g.setColor(player_color);
            g.fillOval(x_cord -r, y_cord-r, 2*r, 2*r);
            
            g.setStroke(new BasicStroke(3));
            g.setColor(player_color.darker());
            g.drawOval(x_cord-r, y_cord-r, 2*r, 2*r);
            g.setStroke(new BasicStroke(1));
            
            
    }
}
