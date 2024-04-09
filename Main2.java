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

import java.io.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;


public class Main2 extends Application
{
   
   Canvas theCanvas = new Canvas(600,600);
   
   TestObject thePlayer=new TestObject(0,0);
   
   StackPane fp = new StackPane();
   
   VBox box;
   
   int hScore=0;
   int cScore=0;
   
   Label scoreLabel=new Label("Score: "+cScore);
   Label hScoreLabel=new Label("HighScore: "+hScore);
   FileOutputStream fos;
   PrintWriter pw;
   
   ArrayList<Mine> mineArray=new ArrayList<Mine>();
   
   public void start(Stage stage)
   {
      
   
      
      fp.getChildren().add(theCanvas);
      gc = theCanvas.getGraphicsContext2D();
      
      
      fp.setOnKeyPressed(new KeyListenerDown());
      fp.setOnKeyReleased(new KeyListenerUp());
      AnimationHandler ta = new AnimationHandler();
      ta.start();
      
      
      
      try
      {
         Scanner scan=new Scanner(new File("spaceship.txt"));
         hScore=scan.nextInt();
         
         
         fos = new FileOutputStream("Project.txt", false);
         pw = new PrintWriter(fos);
         
         pw.close();
         
         box=new VBox();
         box.setAlignment(Pos.TOP_LEFT);
         hScoreLabel.setTextFill(Color.WHITE);
         scoreLabel.setTextFill(Color.WHITE);
         
         box.getChildren().add(scoreLabel);
         box.getChildren().add(hScoreLabel);
         fp.getChildren().add(box);
         
      }
      catch(FileNotFoundException fnfe)
      {
         System.out.println("bad");
      }
      
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
      
      
      
      fp.requestFocus();
   }
   
   GraphicsContext gc;
   
   
   
   Image background = new Image("stars.png");
   Image overlay = new Image("starsoverlay.png");
   Random backgroundRand = new Random();
   //this piece of code doesn't need to be modified
   public void drawBackground(float playerx, float playery, GraphicsContext gc)
   {
 //re-scale player position to make the background move slower. 
      playerx*=.1;
      playery*=.1;
   
//figuring out the tile's position.
      float x = (playerx) / 400;
      float y = (playery) / 400;
      
      int xi = (int) x;
      int yi = (int) y;
      
 //draw a certain amount of the tiled images
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(background,-playerx+i*400,-playery+j*400);
         }
      }
      
 //below repeats with an overlay image
      playerx*=2f;
      playery*=2f;
   
      x = (playerx) / 400;
      y = (playery) / 400;
      
      xi = (int) x;
      yi = (int) y;
      
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(overlay,-playerx+i*400,-playery+j*400);
         }
      }
   }
   
   float x=0,y=0;
   float distance;
   boolean up,down,left,right;
   boolean alive=true;
   Random rand = new Random();
   int cgridx;
   int cgridy;
   int cgridx2;
   int cgridy2;
   
   public class KeyListenerDown implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      { 
         if (event.getCode() == KeyCode.A) 
         {
            left = true;
            
         }
         if (event.getCode() == KeyCode.W)  
         {
            up = true;
            
         }
         if (event.getCode() == KeyCode.S)  
         {
            down = true;
            
         }
         if (event.getCode() == KeyCode.D)  
         {
            right = true;
            
         }
         

      }
   }
   
   public class KeyListenerUp implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      { 
         if (event.getCode() == KeyCode.A) 
         {
            left = false;
         }
         if (event.getCode() == KeyCode.W)  
         {
            up = false;
         }
         if (event.getCode() == KeyCode.S)  
         {
            down = false;
         }
         if (event.getCode() == KeyCode.D)  
         {
            right = false;
         }
      }
   }
   
   int mineChance;
   double xForce=0;
   double yForce=0;
   TestObject thePlayer2=new TestObject(0,0);
   
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         if(alive)
         {//
            gc.clearRect(0,0,600,600);
             
            drawBackground(thePlayer.getX(),thePlayer.getY(),gc);
            thePlayer.draw(300,300,gc,true);
   
            if(left)
            {
               if(xForce>0.25)
               {
                  xForce-=0.025;          
               }
               else if(xForce<0.25)
               {
                  if(xForce>-5)
                  {
                     xForce-=0.1;
                     if(xForce<-5)
                        xForce=-5;
                  }
               }
               
               x=x+(float)xForce;
               x--;
            }
            if(right)
            {
               if(xForce<-0.25)
               {
                  xForce+=0.025;          
               }
               else if(xForce>-0.25)
               {
                  if(xForce<5)
                  {
                     xForce+=0.1;
                     if(xForce>5)
                        xForce=5;
                  }
                  
               }
               
               x=x+(float)xForce;
               x++;
            }
            if(down)
            {
               if(yForce<-0.25)
               {
                  yForce+=0.025;          
               }
               else if(yForce>-0.25)
               {
                  if(yForce<5)
                  {
                     yForce+=0.1;
                     if(yForce>5)
                        yForce=5;
                  }
                  
               }
               
               y=y+(float)yForce;
               y++;
            }
            if(up)
            {
               if(yForce>0.25)
               {
                  yForce-=0.025;          
               }
               else if(yForce<0.25)
               {
                  if(yForce>-5)
                  {
                     yForce-=0.1;
                     if(yForce<-5)
                        yForce=-5;
                  }
               }
               
               y=y+(float)yForce;
               y--;
            }
            
            if(up || down || left || right)
            {
               thePlayer.setX(x);
               thePlayer.setY(y);        
               
            }
            
            if(!up)
            {
               if(yForce<0 && yForce!=0)
               {
                  yForce+=0.025;
                  y+=yForce;
                  if(yForce>-0.25)
                  {
                     yForce=0;
                  }
               }
            }
            if(!down)
            {
               if(yForce!=0 && yForce>0)
               {
                  yForce-=0.025;
                  y+=yForce;
                  
               
                  if(yForce<0.25)
                  {
                     yForce=0;
                  }
               }
            }
            if(!right)
            {
              if(xForce!=0 && xForce>0)
               {
                  xForce-=0.025;
                  x+=xForce;
                  
               
                  if(xForce<0.25)
                  {
                     xForce=0;
                  }
               }
               
            }
            if(!left)
            {
               if(xForce<0 && xForce!=0)
               {
                  xForce+=0.025;
                  x+=xForce;
                  if(xForce>-0.25)
                  {
                     xForce=0;
                  }
               }
            }
            thePlayer.setX(x);
            thePlayer.setY(y);
            
            for(int i=0;i<mineArray.size();i++)
            {
               if(mineArray.get(i).distance(thePlayer)>800)
               {
                  mineArray.remove(i);
                  i--;
               }
               else
                  mineArray.get(i).draw(thePlayer.getX(),thePlayer.getY(),gc,false);
            }
            
            int num=(int)((Math.sqrt((cgridx*100-thePlayer2.getX())*(cgridx*100-thePlayer2.getX())+(cgridy*100-thePlayer2.getY())*(cgridy*100-thePlayer.getY()))/1000));
            cgridx=((int)thePlayer.getX())/100;
            cgridy=((int)thePlayer.getY())/100;
            
          
               
            if(cgridx>cgridx2 || cgridy>cgridy2 || cgridx<cgridx2 || cgridy<cgridy2)
            {
               for(int i=0;i<num;i++)
               {
                  for(int j=-5;j<5;j++)
                  {
                     int percentage=rand.nextInt(3);
                     if(percentage == 0)
                     {
                        float bottomY=j*100;
                        float tempx=400+cgridx*100;
                        float tempx2=499+cgridx*100;
                        
                        float tempy=bottomY+cgridy*100;
                        float tempy2=bottomY+99+cgridy*100;
                        
                        float xMine=rand.nextFloat(tempx2-tempx)+tempx;
                        float yMine=rand.nextFloat(tempy2-tempy)+tempy;
                        
                        
                        
                        Mine mine=new Mine(xMine,yMine);
                        mine.setX(xMine);
                        mine.setY(yMine);
                        
                        mineArray.add(mine);
                     }
                  }
                  
                  for(int j=-5;j<5;j++)
                  {
                     int percentage=rand.nextInt(3);
                     if(percentage == 0)
                     {
                        float bottomY=j*100;
                        float tempy=400+cgridy*100;
                        float tempy2=499+cgridy*100;
                        
                        float tempx=bottomY+cgridx*100;
                        float tempx2=bottomY+99+cgridx*100;
                        
                        float xMine=rand.nextFloat(tempx2-tempx)+tempx;
                        float yMine=rand.nextFloat(tempy2-tempy)+tempy;
                        
                        
                        
                        Mine mine=new Mine(xMine,yMine);
                        mine.setX(xMine);
                        mine.setY(yMine);
                        
                        mineArray.add(mine);
                     }
                  }
                  for(int j=-5;j<5;j++)
                  {
                     int percentage=rand.nextInt(3);
                     if(percentage == 0)
                     {
                        float bottomY=j*100;
                        float tempy=0;
                        float tempy2=0;
                        
                        float tempx=bottomY+cgridx*100;
                        float tempx2=bottomY+99+cgridx*100;
                        
                        if(cgridy*100>0)
                        {
                           if(cgridy*100==500)
                           {
                              tempy=cgridy*100-500;
                              tempy2=cgridy*100-599;
                              tempy2=tempy2*-1;
                           }
                           else if(cgridy*100<500)
                           {
                              tempy=cgridy*100-500;
                              tempy2=cgridy*100-599;
                           }
                           else
                           {
                              tempy=cgridy*100-500;
                              tempy2=cgridy*100-401;
                           }
                        }
                        else if(cgridy*100<0)
                        {
                           tempy=cgridy*100-500;
                           tempy2=cgridy*100-599;
                        }
                        else if(cgridy*100==0)
                        {
                           tempy=cgridy*100-500;
                           tempy2=cgridy*100-599;
                        }
                        float xMine=rand.nextFloat(tempx2-tempx)+tempx;
                        float yMine=0;
                        
                        if(tempy<0 && tempy2<0)
                        {
                           tempy=tempy*-1;
                           tempy2=tempy2*-1;
                           yMine=rand.nextFloat(tempy2-tempy)+tempy;
                           yMine=yMine*-1;
                        }
                        else
                        {
                           yMine=rand.nextFloat(tempy2-tempy)+tempy;
                        }
                        Mine mine=new Mine(xMine,yMine);
                        mine.setX(xMine);
                        mine.setY(yMine);
                        
                        mineArray.add(mine);
                     }
                  }
                  for(int j=-5;j<5;j++)
                  {
                     int percentage=rand.nextInt(3);
                     if(percentage == 0)
                     {
                        float bottomY=j*100;
                        float tempx=0;
                        float tempx2=0;
                        
                        float tempy=bottomY+cgridy*100;
                        float tempy2=bottomY+99+cgridy*100;
                        
                        if(cgridx*100>0)
                        {
                           if(cgridx*100==500)
                           {
                              tempx=cgridx*100-500;
                              tempx2=cgridx*100-599;
                              tempx2=tempx2*-1;
                           }
                           else if(cgridy*100<500)
                           {
                              tempx=cgridx*100-500;
                              tempx2=cgridx*100-599;
                           }
                           else
                           {
                              tempx=cgridx*100-500;
                              tempx2=cgridx*100-401;
                           }
                        }
                        else if(cgridx*100<0)
                        {
                           tempx=cgridx*100-500;
                           tempx2=cgridx*100-599;
                        }
                        else if(cgridx*100==0)
                        {
                           tempx=cgridx*100-500;
                           tempx2=cgridx*100-599;
                        }
                        float yMine=rand.nextFloat(tempy2-tempy)+tempy;
                        float xMine=0;
                        
                        if(tempx<0 && tempx2<0)
                        {
                           tempx=tempx*-1;
                           tempx2=tempx2*-1;
                           xMine=rand.nextFloat(tempx2-tempx)+tempx;
                           xMine=xMine*-1;
                        }
                        else
                        {
                           xMine=rand.nextFloat(tempx2-tempx)+tempx;
                        }
                           
                        Mine mine=new Mine(xMine,yMine);
                        mine.setX(xMine);
                        mine.setY(yMine);
                        
                        mineArray.add(mine);
                     }
                  }
               }
               cgridy2=cgridy;
               cgridx2=cgridx;
               
            }
               
               cScore=(int)thePlayer.distance(thePlayer2);
               scoreLabel.setText("Score: "+cScore);
               
               
               if(cScore>hScore)
                  hScoreLabel.setText("HighScore: "+cScore);
               else
                  hScoreLabel.setText("HighScore: "+hScore);
            }
            for(int i=0;i<mineArray.size();i++)
            {
               distance=(float)thePlayer.distance(mineArray.get(i));
               if(distance<20)
               {
                  mineArray.remove(i);
                  gc.clearRect(0,0,600,600);
                  drawBackground(thePlayer.getX(),thePlayer.getY(),gc);
                  
                  
                  for(int k=0;k<mineArray.size();k++)
                  {
                     mineArray.get(k).draw(thePlayer.getX(),thePlayer.getY(),gc,false);
                     
                  }
                  alive=false;
                  
                  
                  if(cScore>hScore)
                  {
                     pw.print(cScore);
                     pw.close();
                  }
                  
                  else
                  {
                     pw.print(hScore);
                     pw.close();
                  }
               }
            }
            if(!alive)
            {
               gc.clearRect(0,0,600,600);
               drawBackground(thePlayer.getX(),thePlayer.getY(),gc);
               
               
               
               for(int k=0;k<mineArray.size();k++)
               {
                  mineArray.get(k).draw(thePlayer.getX(),thePlayer.getY(),gc,false);
               }
            }
         }
         //System.out.println(rand.nextInt(3));
         
      }
      
   
   
   public static void main(String[] args)
   {
      launch(args);
   }
}
