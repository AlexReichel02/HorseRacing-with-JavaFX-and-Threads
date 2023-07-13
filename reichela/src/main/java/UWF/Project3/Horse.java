
/***************************************************************
Student Name: Alex Reichel
File Name: Horse.java
Assignment number Project 03

Other comments regarding the file - description of why it is there, etc.
***************************************************************/
package UWF.Project3;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Horse  implements Runnable{
	
	
	/**
	Canvas object
    */
	 private Canvas can;
	 
	 /**
	  GraphicsContext object
	  */
	 private GraphicsContext gc;
	 
	 /**
		Random object
	    */
	 private Random rand ;
	 
	 /**
		int representing horse number
	    */
	 private int horseNumber;
	
	
	 /**
		Horse constructor that draws the horse
		@param GraphicsContext object
		@param Canvas object representing horses canvas
		@param int represents horse number
	   */
	 Horse(GraphicsContext gc,Canvas horse,int horseNum){
		
		    gc = horse.getGraphicsContext2D();
		    can = horse;
		    horseNumber = horseNum;
		    rand = new Random();
	    	gc.setLineWidth(1);
	    	gc.strokeRect(20,35, 5, 5);
	    	gc.strokeRect(50,35, 5, 5);
	    	gc.strokeRect(20,25, 35, 10);
	    	gc.strokeRect(51,15, 15, 10);
	    	gc.strokeLine(550,0,550,75);
	   }
	 
	
	 
	 /**
		Function called when the first horse crosses the finish line
	  */
	 public void finish() {
		 
		 
	    	App.lock.lock();
	    	App.winner = true;
	    	Platform.runLater(new Runnable() {
	    		public void run() {
	    			Alert alert = new Alert(AlertType.INFORMATION);
	                   alert.setTitle("Information Dialog");
	              	 long stopTime = System.currentTimeMillis();
	                 long elapsedTime = stopTime - App.startTime;
	                 //System.out.println("total time = " + elapsedTime / 1000);
	                   alert.setContentText("Winner is horse#" + horseNumber + " total time taken: " + elapsedTime / 1000.00 + " seconds");
	                   alert.showAndWait();
	    		}
	    	});	
	    		
	    		
	    		
	    		 
	    		 
	    		 int j=0;
	    		 for(int i =0;i<App.hThreads.size();i++) {
	    		 j++;
	    		
	    			 if(j == horseNumber) {
	    				
	    				 continue;
	    				
	    				 
	    			 }else 
	    				
	    				 App.hThreads.get(i).interrupt();
	    		 }
	    			
	    		 
	    	
	    	App.lock.unlock();
	    	
	    	
		 }
	    	
	    
	 
	 
	
	 /**
		Function that redraws the horses until a winner is found
	 */
	public void run() {
		 
		
	     int randomNum=1;
		
		
		while(!App.winner) {
			
			int temp = randomNum;
			
            randomNum = rand.nextInt((550 - temp) + 1) + temp;
    
            System.out.println("Drawing horse " + horseNumber + " @" + randomNum);
           
            
            if(randomNum==550 && !App.winner) {
            	
            	
            	
             	
            	redrawHorses(randomNum);
            	
            	
           	    finish();
          
           	    break;
            	
            }
            
            
            redrawHorses(randomNum);
            
            try {
                Thread.sleep(100);
                //Thread.sleep(Math.abs(UUID.randomUUID().getMostSignificantBits())%60);
                continue;
            } catch (InterruptedException ex) {
            	Thread.currentThread().interrupt();
                System.out.println("Horse interrupted");
               
                return;
            }
            //under
		
		}
		

	}
	
	
	
	/**
	Function that redraws the horse in a different position
	@param int representing x coordinate of new horse drawing
    */
 public void redrawHorses(int x){
   	 gc = can.getGraphicsContext2D();
   	 gc.clearRect(0, 0, can.getWidth(), can.getHeight());
   	 gc.strokeRect(x,35, 5, 5);
   	 gc.strokeRect(x+30,35, 5, 5);
     gc.strokeRect(x,25, 35, 10);
   	 gc.strokeRect(x+31,15, 15, 10);
   	gc.strokeLine(550,0,550,75);
   }
  
 
 /**
	Function that clears the horse's canvas
 */
  public void reset() {

	   gc = can.getGraphicsContext2D();
	   gc.clearRect(0, 0, can.getWidth(), can.getHeight());
	
  }
	
}