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


public class Main extends Application
{
   
   //calling methods
   Canvas theCanvas = new Canvas(600,600);
   
   TestObject thePlayer;
   
   TestObject testPoint = new TestObject(300,300);
   
   TestObject originPoint = new TestObject (300,300);
   
   DrawableObject distance;
   
   DrawableObject act;
   
   GraphicsContext gc;
   
   Mine drawMe;
   
   private ArrayList<Mine> objectsList = new ArrayList<>();
   
   Iterator<Mine> iterator = objectsList.iterator();

    

   public void start(Stage stage)
   {
      
      FlowPane fp = new FlowPane();
      fp.getChildren().add(theCanvas);
      gc = theCanvas.getGraphicsContext2D();
      drawBackground(300,300,gc);
      
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
      
      thePlayer = new TestObject(300,300);
      
      AnimationHandler animationHandler = new AnimationHandler();
      animationHandler.start();
      
      Mine test = new Mine(400,300);
      test.drawMe(400,300,gc);
      
      boolean up;
      boolean down;
      boolean left;
      boolean right;
      
      //event of when keys are pressed
      scene.setOnKeyPressed(new EventHandler<KeyEvent>()
      {
         @Override
         public void handle(KeyEvent event)  
         {
            if (event.getCode() == KeyCode.A)
            {
               thePlayer.left = true;
            }
            if (event.getCode() == KeyCode.D)
            {
               thePlayer.right = true;
            }
            if (event.getCode() == KeyCode.W)
            {
               thePlayer.up = true;
            }
            if (event.getCode() == KeyCode.S)
            {
               thePlayer.down = true;
            }
         }
      });
      
      //event of when keys are released
      scene.setOnKeyReleased(new EventHandler<KeyEvent>()
      {
         @Override
         public void handle(KeyEvent event)
         {
            if (event.getCode() == KeyCode.A)
            {
               thePlayer.left = false;
            }
            if (event.getCode() == KeyCode.D)
            {
               thePlayer.right = false;
            }
            if (event.getCode() == KeyCode.W)
            {
               thePlayer.up = false;
            }
            if (event.getCode() == KeyCode.S)
            {
               thePlayer.down = false;
            }
         }
      }); 
   }
   
   
   
   //Background
   
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
      
	  //draw a certain many of the tiled images
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
   
   
   
   
   boolean gameOver = false;
   
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         gc.clearRect(0,0,600,600);
         
         
         
         //USE THIS CALL ONCE YOU HAVE A PLAYER
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc); 


	      //example calls of draw - this should be the player's call for draw
         thePlayer.draw(300,300,gc,true); //all other objects will use false in the parameter.
         
         thePlayer.act();
         
         //example call of a draw where m is a non-player object. Note that you are passing the player's position in and not m's position.
         //m.draw(thePlayer.getX(),thePlayer.getY(),gc,false);
         grid();
         
         drawMines(gc, thePlayer.getX(), thePlayer.getY());
         
         drawScore();
         
         
      }
   }
   
   //draws score in scene
   private void drawScore()
   {
      //current score
      gc.setFill(Color.WHITE);
      gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
      gc.fillText("Score: " + getScore(), 15, 20);
      
      //high score
      int score = (int) originPoint.distance(thePlayer);
      gc.setFill(Color.WHITE);
      gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
      gc.fillText("Highscore: " + readHighScore(), 15, 40);
      
      //updates high score if current score is larger than previous highscore
      updateHighScore(score);


   }
   
   //gets score from distance between origin and player coordinates
   private int getScore()
   {
      return (int) originPoint.distance(thePlayer);
   }
   
   //sets score from return value of getScore()
   private int setScore()
   {
      return getScore();
   }
   
   //reads high score from file
   private int readHighScore()
   {
      try
      {
         BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"));
         String line = reader.readLine();
         reader.close();
         if (line != null)
         {
            return Integer.parseInt(line);
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return 0;
   }
   
   //updates highscore if score is higher than previous highscore
   private void updateHighScore(int score)
   {
      int currentHighScore = readHighScore();
      if (score > currentHighScore)
      {
         try
         {
            BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"));
            writer.write(Integer.toString(score));
            writer.close();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
      }
   }
   
   
   int cgridx;
   int cgridy;
   
   //creates grid of where mines should spawn
   private void grid()
   {
      
      cgridx = ((int)thePlayer.getX())/100;
      cgridy = ((int)thePlayer.getY())/100;
      
      for (int i = 0; i < 9; i++)
      {
         int gx = (cgridx - 5 + i) * 100;
         int gy = (cgridy - 5) * 100;
         int sx = cgridx - 5 + i;
         int sy = cgridy - 5;
         int many = (int)(testPoint.distance(originPoint)/1000);
         createMines(gx,gy,many);
      }
      for (int i = 0; i < 9; i++)
      {
         int gx = (cgridx - 5 + i) * 100;
         int gy = (cgridy + 5) * 100;
         int sx = cgridx - 5 + i;
         int sy = cgridy + 5;
         int many = (int)(testPoint.distance(originPoint)/1000);
         createMines(gx,gy,many);
      }
      for (int i = 0; i < 9; i++)
      {
         int gx = (cgridx - 5) * 100;
         int gy = (cgridy - 5 + i) * 100;
         int sx = cgridx - 5;
         int sy = cgridy - 5 + i;
         int many = (int)(testPoint.distance(originPoint)/1000);
         createMines(gx,gy,many);
      }
      for (int i = 0; i < 9; i++)
      {
         int gx = (cgridx + 5) * 100;
         int gy = (cgridy - 5 + i) * 100;
         int sx = cgridx + 5;
         int sy = cgridy - 5 + i;
         int many = (int)(testPoint.distance(originPoint)/1000);
         createMines(gx,gy,many);
      }   
   }
   
   //creates mines
   private void createMines(float gx, float gy, int many)
   {
      int originx = 300;
      int originy = 300;
      double distance = Math.sqrt(Math.pow(cgridx - originx, 2) + Math.pow(cgridy - originy, 2));
      int maxMines = (int) (distance / 1000);
      Random rand = new Random();
      
      for (int i = Math.max((int) (gx / 100) - 4, 0); i <= Math.min((int) (gx / 100) + 4, 9); i++)
      {
         for (int j = Math.max((int) (gy / 100) - 4, 0); j <= Math.min((int) (gy / 100) + 4, 9); j++)
         {
            if (rand.nextDouble() <= 0.3 && objectsList.size() < maxMines)
            {
               Mine mine = new Mine(i * 100, j * 100);
               objectsList.add(mine);
            }
         }
      }


      // Remove mines that are 800 units away from the player
      while (iterator.hasNext())
      {
         DrawableObject obj = iterator.next();
         if (obj instanceof Mine)
         {
            Mine mine = (Mine) obj;
            double distToMine = Math.sqrt(Math.pow(mine.getX() - thePlayer.getX(), 2) + Math.pow(mine.getY() - thePlayer.getY(), 2));
            if (distToMine >= 800)
            {
               iterator.remove();
            }
         }
      }
   }  
   
   //draws mines
   private void drawMines(GraphicsContext gc, float playerx, float playery)
   {
      for (DrawableObject obj : objectsList)
      {
         if (obj instanceof Mine)
         {
            Mine mine = (Mine) obj;
            mine.drawMe(mine.getX(), mine.getY(), gc);
         }
      }
   }
   
   double playerDiameter;
   double mineDiameter;
   
   //checks if player and mine hits
   private boolean checkCollision(double playerX, double playerY, double playerDiameter, double mineX, double mineY, double mineDiameter)
   {
      double dx = playerX - mineX;
      double dy = playerY - mineY;
      double distance = Math.sqrt(dx * dx + dy * dy);
      
      return distance < (playerDiameter / 2 + mineDiameter / 2);
   }
      

   public static void main(String[] args)
   {
      launch(args);
   }
}

