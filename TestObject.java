import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import javafx.event.*;

//this is an example object
public class TestObject extends DrawableObject
{

	//takes in its position
   public TestObject(float x, float y)
   {
      super(x,y);
   }
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      gc.setFill(Color.BLACK);
      gc.fillOval(300-14,300-14,27,27);
      gc.setFill(Color.GRAY);
      gc.fillOval(300-13,300-13,25,25);
      gc.setFill(Color.BLACK);
      gc.fillOval(300-6,300-6,11,11);
      gc.setFill(Color.TEAL);
      gc.fillOval(300-5,300-5,9,9);
   }
   
   double diameter = 25;
   
   public double getDiameter()
   {
      return diameter;
   }



   
   
   
   
   
   
   
}
