
/***************************************************************
Student Name: Alex Reichel
File Name: App.java
Assignment number Project 03

Other comments regarding the file - description of why it is there, etc.
***************************************************************/
package UWF.Project3;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

	/**
	Array List containing all Horse objects
    */
   public  static ArrayList<Horse> horses = new ArrayList<Horse>();
   
   /**
	Array List containing all threads
   */
   public static ArrayList<Thread> hThreads = new ArrayList<Thread>();
   
   /**
	graphics context obj
   */
   public static GraphicsContext gc;
   
   /**
	start time long for clock
   */
   public static long startTime;
   
   /**
	boolean indicating if race is over
   */
   public static boolean winner = false;
   
   /**
	Lock obj used for locking critical section
   */
   public static Lock lock;
   
  
   /**
	Function used to start the stage
   */
    @Override
    public void start(Stage stage) {
    	
    	 
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        
    
        lock = new ReentrantLock();
    
    	
    	 Canvas horse1 = new Canvas(650, 75);
    	 Canvas horse2 = new Canvas(650, 75);
    	 Canvas horse3 = new Canvas(650, 75);
    	 Canvas horse4 = new Canvas(650, 75);
    	 Canvas horse5 = new Canvas(650, 75);
    	 Canvas horse6 = new Canvas(650, 75);
    	 
    	 //80
    	 moveCanvas(20,20,horse1);
         moveCanvas(20,96,horse2);
         moveCanvas(20,172,horse3);
         moveCanvas(20,248,horse4);
         moveCanvas(20,324,horse5);
         moveCanvas(20,400,horse6);
    	
         Horse h1 = new Horse(gc,horse1,1);
    	 horses.add(h1);
         Horse h2 = new Horse(gc,horse2,2);
         horses.add(h2);
         Horse h3 = new Horse(gc,horse3,3);
    	  horses.add(h3);
         Horse h4 = new Horse(gc,horse4,4);
         horses.add(h4);
    	 Horse h5 = new Horse(gc,horse5,5);
    	 horses.add(h5);
    	 Horse h6 = new Horse(gc,horse6,6);
    	 horses.add(h6);
    		   
    	 

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        BorderPane pane = new BorderPane();
        HBox topMenu = new HBox();
        
       
	    Button run = new Button("Run");
	    run.setOnAction(new EventHandler<ActionEvent>()
	     {
	    	 
            @Override
            public void handle(ActionEvent event) {
       
            	startTime = System.currentTimeMillis();      
            	runRace();
            	
            }
        });
	    
	    
	    
	    Button reset = new Button("Reset");
	    reset.setOnAction(new EventHandler<ActionEvent>()
	     {
	    	 
           @Override
           public void handle(ActionEvent event) {
        	   
        	   
        	      hThreads.get(0).interrupt();
        	      h1.reset();
        		  hThreads.get(1).interrupt();
        		  h2.reset();
        		  hThreads.get(2).interrupt();
        		  h3.reset();
        		  hThreads.get(3).interrupt();
        		  h4.reset();
        		  hThreads.get(4).interrupt();
        		  h5.reset();
        		  hThreads.get(5).interrupt();
        		  h6.reset();
        		  hThreads.clear();
        		  horses.clear();
        		 
        		 
        	   Horse h1 = new Horse(gc,horse1,1);
      		   horses.add(h1);
      		   Horse h2 = new Horse(gc,horse2,2);
      		   horses.add(h2);
      		   Horse h3 = new Horse(gc,horse3,3);
      		   horses.add(h3);
      		   Horse h4 = new Horse(gc,horse4,4);
      		   horses.add(h4);
      		   Horse h5 = new Horse(gc,horse5,5);
      		   horses.add(h5);
      		   Horse h6 = new Horse(gc,horse6,6);
      		   horses.add(h6);
        		 
        	
      		   lock.lock();
      		   winner = false;
      		   lock.unlock();
      		   
      		   
           }
       });
	    
	    
	    
	    Button quit = new Button("Quit");
	    quit.setOnAction(e -> System.exit(0));

	    
	    
	    topMenu.getChildren().addAll(run, reset,quit);
        pane.setTop(topMenu);
        
       
        pane.getChildren().addAll(horse1,horse2,horse3,horse4,horse5,horse6);
        var scene = new Scene(pane, 700, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
	Function used to move the canvas
	@param int representing x coordinate
	@param int representing y coordinate
	@param Canvas representing canvas obj
    */
    private void moveCanvas(int x, int y,Canvas obj) {
       obj.setTranslateX(x);
       obj.setTranslateY(y);
    }
    
    
    /**
	Function used to start the threads therefore starting the race
    */
    public void runRace() {
    	for(int i=0;i<horses.size();i++) {
    		Thread temp;
    		temp = new Thread(horses.get(i));
    		hThreads.add(temp);
    		hThreads.get(i).start();
    		   
  
    	   }    	
    }
    
    

    /**
	Function used launch main
    */
    public static void main(String[] args) {
        launch();
    }

    	
}
   