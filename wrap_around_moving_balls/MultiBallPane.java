/**
 * MultiBallPane.java File
 * Author Daniel Glauber
 */
package wrap_around_moving_balls;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import javafx.scene.shape.Circle;

import javafx.util.Duration;

public class MultiBallPane extends Pane {

    private Timeline animation;
    private Ball redBall;
    ArrayList<Ball> arrayListOfGreenBalls;

    public MultiBallPane() {
        arrayListOfGreenBalls = new ArrayList<>();
        this.redBall = new Ball(450, 250, 20, RED);
        for (int i = 0; i < 10; i++) {
            arrayListOfGreenBalls.add(new Ball(250, 250, 20, GREEN));
        }
        getChildren().add(redBall);
        getChildren().addAll(arrayListOfGreenBalls); // Place a ball into this pane

        // Create an animation for moving the ball
        animation = new Timeline(
                new KeyFrame(Duration.millis(5), e -> moveBall()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation
    }

    public void play() {
        animation.play();
    }

    public void pause() {
        animation.pause();
    }

    public void increaseSpeed() {
        animation.setRate(animation.getRate() + 0.1);
    }

    public void decreaseSpeed() {
        animation.setRate(
                animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
    }

    public DoubleProperty rateProperty() {
        return animation.rateProperty();
    }

    protected void addBall(double x, double y) {
        Ball addedBall = new Ball(x, y, 20, GREEN);
        arrayListOfGreenBalls.add(addedBall);
        getChildren().add(addedBall);
    }

    // Performs wrap around effect when moving balls
    protected void moveBall() {
        double lineYSlope = redBall.yVelocity / redBall.xVelocity;
        double lineXSlope = redBall.xVelocity / redBall.yVelocity;
        double yIntercept = (redBall.yCenter - (redBall.xCenter * lineYSlope));
        double xIntercept = (redBall.xCenter - (redBall.yCenter * lineXSlope));
        if (redBall.xNextCenter < 0 - redBall.ballRadius) {
            redBall.yNextCenter = yIntercept + lineYSlope * (getWidth() + redBall.ballRadius);
            redBall.xNextCenter = getWidth() + redBall.ballRadius;
        } else if (redBall.xNextCenter > getWidth() + redBall.ballRadius) {
            redBall.yNextCenter = yIntercept + lineYSlope * (-redBall.ballRadius);
            redBall.xNextCenter = 0 - redBall.ballRadius;
        } else if (redBall.yNextCenter < 0 - redBall.ballRadius) {
            if (redBall.xVelocity == 0) {
                redBall.yNextCenter = getHeight() + redBall.ballRadius;
            } else {
                redBall.xNextCenter = getHeight() * lineXSlope + xIntercept;
                redBall.yNextCenter = getHeight() + redBall.ballRadius;
            }
        } else if (redBall.yNextCenter > (getHeight() + redBall.ballRadius)) {
            if (redBall.xVelocity == 0) {
                redBall.yNextCenter = -redBall.ballRadius;
            } else {
                redBall.xNextCenter = xIntercept;
                redBall.yNextCenter = -redBall.ballRadius;
            }
        }
        // Loops ArrayList while performing wrap around effect on each ball
        for (Ball T : arrayListOfGreenBalls) {
            lineYSlope = T.yVelocity / T.xVelocity;
            lineXSlope = T.xVelocity / T.yVelocity;
            yIntercept = (T.yCenter - (T.xCenter * lineYSlope));
            xIntercept = (T.xCenter - (T.yCenter * lineXSlope));
            if (T.xNextCenter < 0 - T.ballRadius) {
                T.yNextCenter = yIntercept + lineYSlope * (getWidth() + T.ballRadius);
                T.xNextCenter = getWidth() + T.ballRadius;
            } else if (T.xNextCenter > getWidth() + T.ballRadius) {
                T.yNextCenter = yIntercept + lineYSlope * (-T.ballRadius);
                T.xNextCenter = 0 - T.ballRadius;
            } else if (T.yNextCenter < 0 - T.ballRadius) {
                if (T.xVelocity == 0) {
                    T.yNextCenter = getHeight() + T.ballRadius;
                } else {
                    T.xNextCenter = getHeight() * lineXSlope + xIntercept;
                    T.yNextCenter = getHeight() + T.ballRadius;
                }
            } else if (T.yNextCenter > (getHeight() + T.ballRadius)) {
                if (T.xVelocity == 0) {
                    T.yNextCenter = -T.ballRadius;
                } else {
                    T.xNextCenter = xIntercept;
                    T.yNextCenter = -T.ballRadius;
                }
            }
            T.xNextCenter += T.xVelocity;
            T.yNextCenter += T.yVelocity;
            T.setCenterX(T.xNextCenter);
            T.setCenterY(T.yNextCenter);
            // Need to check if any balls collide with red ball after being moved
            if (T.intersects(redBall.xNextCenter, redBall.yNextCenter, redBall.ballRadius, redBall.ballRadius)) {
                getChildren().remove(T);
                redBall.setyVelocity();
                arrayListOfGreenBalls.remove(T);
            }
        }
        redBall.xNextCenter += redBall.xVelocity;
        redBall.yNextCenter += redBall.yVelocity;
        redBall.setCenterX(redBall.xNextCenter);
        redBall.setCenterY(redBall.yNextCenter);
        // Need to check if red ball collides with any balls after being moved
        collisionDetection();
    }

    // Sets new random velocity for red ball when collision is detected
    // Deletes green ball that was collided with
    void collisionDetection() {
        for (Ball T : arrayListOfGreenBalls) {
            if (T.intersects(redBall.xNextCenter, redBall.yNextCenter, redBall.ballRadius, redBall.ballRadius)) {
                getChildren().remove(T);
                redBall.setyVelocity();
                arrayListOfGreenBalls.remove(T);
            }
        }
    }
}
