/**
 * MultiBallPane.java File 
 * Author Daniel Glauber 
 */

package wrap_around_moving_balls;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class BounceBallControl extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    MultiBallPane ballPane = new MultiBallPane(); // Create a ball pane

    // On mouse click and drag green balls are spawned at the location of mouse  
    ballPane.setOnMouseClicked(e -> {
            ballPane.addBall(e.getSceneX(),e.getSceneY());
                    });
    ballPane.setOnMouseDragged(e -> {
            ballPane.addBall(e.getSceneX(),e.getSceneY());
                    });
    
    // Increase and decrease animation   
    ballPane.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.UP) {
        ballPane.increaseSpeed();
      } 
      else if (e.getCode() == KeyCode.DOWN) {
        ballPane.decreaseSpeed();
      }
    });

    // Create a scene and place it in the stage
    Scene scene = new Scene(ballPane, 500, 500);
    primaryStage.setTitle("BounceBallControl"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    // Must request focus after the primary stage is displayed
    ballPane.requestFocus();
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
     * @param args 
   */
  public static void main(String[] args) {
    launch(args);
  }
}
