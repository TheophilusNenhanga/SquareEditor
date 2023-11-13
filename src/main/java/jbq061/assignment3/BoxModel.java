package jbq061.assignment3;

import java.util.ArrayList;
import java.util.List;

// This model stores all objects
public class BoxModel {
	private final ArrayList<Subscriber> subscribers;
	private final ArrayList<Box> boxes;
	private int cursorPosition;
	private Box currentBox;


	public BoxModel() {
		this.subscribers = new ArrayList<>();
		this.boxes = new ArrayList<>();
		cursorPosition = -1;
	}

	public void addSubscriber(Subscriber sub) {
		subscribers.add(sub);
	}

	public void addBox(double x, double y, double width, double height) {
		boxes.add(new Box(x, y, width, height));
	}

	public int getSize() {
		return this.boxes.size();
	}

	public void moveCursorRight() {
		if (this.cursorPosition == this.getSize() - 1) {
			this.currentBox.unsetCurrentBox();
			this.cursorPosition = -1;
		}
		if (cursorPosition != -1) {
			this.getCursorBox().unsetCurrentBox();
		}
		this.cursorPosition += 1;
		this.currentBox = getCursorBox();
		this.currentBox.setCurrentBox();
	}

	public Box getCursorBox() {
		if (boxes.isEmpty()) {
			return null;
		}
		if (cursorPosition == -1)
			return boxes.get(cursorPosition + 1);
		return boxes.get(cursorPosition);
	}

	/**
	 * Gets the closest boxes.
	 *
	 * @return closestLeft, closestRight, closestTop, closestBottom
	 */
	public Box[] getClosestBoxes() {
		currentBox = getCursorBox();
		Box closestLeft = null, closestRight = null, closestTop = null, closestBottom = null;
		double deltaLeft = Double.MAX_VALUE, deltaRight = Double.MAX_VALUE, deltaTop = Double.MAX_VALUE, deltaBottom = Double.MAX_VALUE;

		for (Box box : boxes) {
			if (box == currentBox) continue;

			if (currentBox.x - box.x < 0 && Math.abs(currentBox.x - box.x) < deltaRight) {
				deltaRight = Math.abs(currentBox.x - box.x);
				closestRight = box;
			}

			if (currentBox.x - box.x > 0 && Math.abs(currentBox.x - box.x) < deltaLeft) {
				deltaLeft = Math.abs(currentBox.x - box.x);
				closestLeft = box;
			}

			if (currentBox.y - box.y < 0 && Math.abs(currentBox.y - box.y) < deltaBottom) {
				deltaBottom = Math.abs(currentBox.y - box.y);
				closestBottom = box;
			}

			if (currentBox.y - box.y > 0 && Math.abs(currentBox.y - box.y) < deltaTop) {
				deltaTop = Math.abs(currentBox.y - box.y);
				closestTop = box;
			}
		}
		return new Box[]{closestLeft, closestRight, closestTop, closestBottom};
	}

	public void moveCursorToBox(Box moveToBox) {
		if (currentBox == null) {
			return;
		}
		currentBox.unsetCurrentBox();
		for (int i = 0; i < boxes.size(); i++) {
			if (boxes.get(i) == moveToBox) {
				cursorPosition = i;
				this.currentBox = getCursorBox();
				this.currentBox.setCurrentBox();
			}
		}
	}

	public ArrayList<Box> getBoxes() {
		return this.boxes;
	}

	public void delete(ArrayList<Box> boxesToDelete) {
		boxesToDelete.forEach(boxes::remove);
		cursorPosition = -1; // resetting the cursor
	}

	public void moveLeft(ArrayList<Box> boxes) {
		boxes.forEach(box -> box.setX(box.getX() - 15));
	}

	public void moveRight(ArrayList<Box> boxes) {
		boxes.forEach(box -> box.setX(box.getX() + 15));
	}

	public void moveUp(ArrayList<Box> boxes) {
		boxes.forEach(box -> box.setY(box.getY() - 15));
	}

	public void moveDown(ArrayList<Box> boxes) {
		boxes.forEach(box -> box.setY(box.getY() + 15));
	}

	public void increaseSize(ArrayList<Box> boxes) {
		boxes.forEach(box -> {
			box.setWidth(box.getWidth() + 5);
			box.setHeight(box.getHeight() + 5);
		});
	}


	public void decreaseSize(ArrayList<Box> boxes) {
		boxes.forEach(box -> {
			box.setWidth(box.getWidth() - 5);
			box.setHeight(box.getHeight() - 5);
		});
	}

	public void alignLeft(Box alignTarget, ArrayList<Box> boxes) {
		if (alignTarget == null) {
			alignTarget = getLeftmost(boxes);
		}

		Box finalAlignTarget = alignTarget;
		boxes.forEach(box -> {
			box.setX(finalAlignTarget.getX());
		});
	}

	public void alignRight(Box alignTarget, ArrayList<Box> boxes) {
		if (alignTarget == null) {
			alignTarget = getRightmost(boxes);
		}

		Box finalAlignTarget = alignTarget;
		boxes.forEach(box -> {
			box.setX(finalAlignTarget.getX());
		});
	}

	public void alignTop(Box alignTarget, ArrayList<Box> boxes) {
		if (alignTarget == null) {
			alignTarget = getTopmost(boxes);
		}

		Box finalAlignTarget = alignTarget;
		boxes.forEach(box -> {
			box.setY(finalAlignTarget.getY());
		});
	}

	public void alignBottom(Box alignTarget, ArrayList<Box> boxes) {
		if (alignTarget == null) {
			alignTarget = getBottommost(boxes);
		}

		Box finalAlignTarget = alignTarget;
		boxes.forEach(box -> {
			box.setY(finalAlignTarget.getY());
		});
	}

	public Box getLeftmost(ArrayList<Box> boxes) {
		Box leftMost = null;
		double minDistance = Double.MAX_VALUE;

		for (Box box : boxes) {
			if (box.x < minDistance) {
				minDistance = box.x;
				leftMost = box;
			}
		}

		return leftMost;
	}


	public Box getRightmost(ArrayList<Box> boxes) {
		Box rightMost = null;
		double maxDistance = Double.MIN_VALUE;

		for (Box box : boxes) {
			if (box.x > maxDistance) {
				maxDistance = box.x;
				rightMost = box;
			}
		}

		return rightMost;
	}

	/**
	 * Returns the box closest to the top
	 * @param boxes The list of boxes to check
	 * @return Returns the box closest to the top
	 */
	public Box getTopmost(ArrayList<Box> boxes) {
		Box topMost = null;
		double minDistance = Double.MAX_VALUE;

		for (Box box : boxes) {
			if (box.y < minDistance) {
				minDistance = box.y;
				topMost = box;
			}
		}

		return topMost;
	}

	/**
	 * Gets the box closest to the bottom.
	 *
	 * @param boxes The list of boxes to check
	 * @return The box with the greatest y coordinate
	 */
	public Box getBottommost(ArrayList<Box> boxes) {
		Box bottomMost = null;
		double maxDistance = Double.MIN_VALUE;
		for (Box box : boxes) {
			if (box.y > maxDistance) {
				maxDistance = box.y;
				bottomMost = box;
			}
		}
		return bottomMost;
	}

	/**
	 * Divides boxes evenly in the x plane.
	 *
	 * @param boxes The list of boxes which must be divided.
	 */
	public void divideEvenlyHorizontal(ArrayList<Box> boxes) {
		if (boxes == null || boxes.isEmpty()) {
			return;
		}

		Box leftMost = getLeftmost(boxes);
		Box rightMost = getRightmost(boxes);

		double areaWidth = rightMost.getX() + rightMost.getWidth() - leftMost.getX() + rightMost.getWidth();
		double parcel = areaWidth / boxes.size();

		for (int i = 0; i < boxes.size(); i++) {
			boxes.get(i).setX(leftMost.getX() + (i * parcel));
		}
	}

	/**
	 * Divides boxes evenly in the y plane.
	 *
	 * @param boxes The list of boxes which must be divided.
	 */
	public void divideEvenlyVertical(ArrayList<Box> boxes) {
		if (boxes == null || boxes.isEmpty()) {
			return;
		}

		Box topMost = getTopmost(boxes);
		Box bottomMost = getBottommost(boxes);

		double areaHeight = bottomMost.getY() + bottomMost.getHeight() - topMost.getY() + bottomMost.getHeight();
		double parcel = areaHeight / boxes.size();

		for (int i = 0; i < boxes.size(); i++) {
			boxes.get(i).setY(topMost.getY() + (i * parcel));
		}
	}
}
