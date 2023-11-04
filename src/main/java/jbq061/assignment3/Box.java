package jbq061.assignment3;

public class Box {
    double x, y, height, width;
    boolean isCurrentBox;


    public Box(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isCurrentBox = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setCurrentBox(){
        this.isCurrentBox = true;
    }

    public void unsetCurrentBox(){
        this.isCurrentBox = false;
    }

    public boolean isCurrentBox(){
        return isCurrentBox;
    }
}
