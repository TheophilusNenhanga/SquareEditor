package jbq061.assignment3;

public class Box {
	double x, y, height, width;
	boolean isCurrentBox, isSelected;


	public Box(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isCurrentBox = false;
		this.isSelected = false;
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

	public void setCurrentBox() {
		this.isCurrentBox = true;
	}

	public void unsetCurrentBox() {
		this.isCurrentBox = false;
	}

	public boolean isCurrentBox() {
		return isCurrentBox;
	}

	public void select() {
		this.isSelected = true;
	}

	public void unselect() {
		this.isSelected = false;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setWidth(double width) {
		this.width = width;
	}
}
