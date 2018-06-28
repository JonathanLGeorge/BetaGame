/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;
import java.awt.*;
/**
 *
 * @author Jonathan
 */
public class Bullet {
   private double x_cord, y_cord, x_draw, y_draw, rad, speed;
   private int bullet_size;
   
   private Color color1;
   
   public Bullet(double angle, int x, int y){
        speed = 10;
       this.x_cord = x;
       this.y_cord = y;
       bullet_size = 5;
       
       rad = Math.toRadians(angle);
       x_draw = Math.cos(rad) *speed;
       y_draw = Math.sin(rad) * speed;
        color1 = Color.RED;
       
   }
   
   public boolean update(){
       
       x_cord  += x_draw;
       y_cord  += y_draw;
       
       if(x_cord < -bullet_size || x_cord > GameLooper.WIDTH + bullet_size || 
               y_cord < -bullet_size || y_cord > GameLooper.HEIGHT + bullet_size){
           return true;
       }
       
       return false;
   }
   
   public void draw(Graphics2D g){
       g.setColor(color1);
       g.fillOval((int)(x_cord-bullet_size), (int)(y_cord - bullet_size), 2*bullet_size, 2*bullet_size);
   }
   
}
