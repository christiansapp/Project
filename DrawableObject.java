import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.event.*;

import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.event.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.animation.*;


public abstract class DrawableObject
{
   public DrawableObject(float x, float y)
   {
      this.x = x;
      this.y = y;
   }

   //positions
   private float x;
   private float y;
   
   //takes the position of the player and calls draw me with appropriate positions
   public void draw(float playerx, float playery, GraphicsContext gc, boolean isPlayer)
   {
      //the 300,300 places the player at 300,300, if you want to change it you will have to modify it here
      
      if(isPlayer)
         drawMe(playerx,playery,gc);
      else
         drawMe(-playerx+300+x,-playery+300+y,gc);
   }
   
   //this method you implement for each object you want to draw. Act as if the thing you want to draw is at x,y.
   //NOTE: DO NOT CALL DRAWME YOURSELF. Let the the "draw" method do it for you. I take care of the math in that method for a reason.
   public abstract void drawMe(float x, float y, GraphicsContext gc);
   
   
   boolean up;
   boolean down;
   boolean left;
   boolean right; 
   private double forceX;
   private double forceY;
   private final double maxForce = 5.0;
   
   //where to implement force and direction 
   public void act()
   {
      //increases speed when pressed down to max of 5
      if (left)
      {
         if (forceX > -maxForce)
         {
            forceX = Math.max(-maxForce, forceX - 0.1);
         }
      }
      if (right)
      {
         if (forceX < maxForce)
         {
            forceX = Math.min(maxForce, forceX + 0.1);
         }
      }
      if (down)
      {
         if (forceY < maxForce)
         {
            forceY = Math.min(maxForce, forceY + 0.1);
         }
      }
      if (up)
      {
         if (forceY > -maxForce)
         {
            forceY = Math.max(-maxForce, forceY - 0.1);
         }
      }
      
      //when keys are released, speed decreases
      if (!left && !right)
      {
         if (forceX > 0)
         {
            forceX = Math.max(0, forceX - 0.025);
         }
         else if (forceX < 0)
         {
            forceX = Math.min(0, forceX + 0.025);
         }
      }
      if (!up && !down)
      {
         if (forceY > 0)
         {
            forceY = Math.max(0, forceY - 0.025);
         }
         else if (forceY < 0)
         {
            forceY = Math.min(0, forceY + 0.025);
         }
      }
      
      //new x and y coordinates based on speed
      x += forceX;
      y += forceY;
   }

  
   


   
   public float getX()
   {
      return x;
   }
   
   public float getY()
   {
      return y;
   }
   
   public void setX(float x_)
   {
      x = x_;
   }
   
   public void setY(float y_)
   {
      y = y_;
   }
   
   public double distance(DrawableObject other)
   {
      return (Math.sqrt((other.x-x)*(other.x-x) +  (other.y-y)*(other.y-y)));
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}