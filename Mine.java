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

public class Mine extends DrawableObject
{
   
   
   
   
   public Mine(float x, float y)
   {
      super(x, y);
   }
   
   @Override
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      gc.fillOval(x, y, 10, 10);
      gc.setFill(Color.RED);
   }
   
   double diameter = 10;
   
   public double getDiameter()
   {
      return diameter;
   }   

}
