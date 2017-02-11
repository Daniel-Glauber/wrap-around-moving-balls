/**
 * Ball.java File
 * Author Daniel Glauber
 */
package wrap_around_moving_balls;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

//Class for creating Balls
public final class Ball extends Circle {

    Random rand = new Random();
    protected double xNextCenter, yNextCenter, xVelocity, yVelocity, ballRadius, xCenter, yCenter;

    public Ball(double x, double y, double radius, Color color) {
        super(x, y, radius);
        setX(x);
        setY(y);
        // This stores the starting x center value
        xCenter = x;
        // This stores the starting y center value
        yCenter = y;
        setBallRadius();
        setFill(color);
        setxVelocity();
        setyVelocity();
    }

    public double getBallRadius() {
        return ballRadius;
    }

    public void setBallRadius() {
        ballRadius = getRadius();
    }

    public double getX() {
        return xNextCenter;
    }

    public void setX(double x) {
        this.xNextCenter = x;
    }

    public double getY() {
        return yNextCenter;
    }

    public void setY(double y) {
        this.yNextCenter = y;
    }

    public double getxVelocity() {
        return xVelocity;
    }
    
    // Sets random velocity for y axis movement
    // X velocity can range from -4 to 4 excluding 0
    public final void setxVelocity() {
        int Low = 1;
        int High = 5;
        int randomVelocity = rand.nextInt(High - Low) + Low;
        int postiveOrNegative = rand.nextInt(2);
        if (postiveOrNegative == 0) {
            xVelocity = randomVelocity;
        } else {
            xVelocity = randomVelocity * -1;
        }
    }

    public double getyVelocity() {
        return yVelocity;
    }

    // Sets random velocity for y axis movement
    // Y velocity can range from -5 to 5
    public final void setyVelocity() {
        int randomVelocity = rand.nextInt(6);
        int postiveOrNegative = rand.nextInt(2);
        if (postiveOrNegative == 0) {
            yVelocity = randomVelocity;
        } else {
            yVelocity = randomVelocity * -1;
        }
    }
}
