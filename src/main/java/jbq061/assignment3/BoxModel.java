package jbq061.assignment3;

import java.util.ArrayList;

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

    private void notifySubscribers() {
        // for each subscriber call the model changed method
        subscribers.forEach(subscriber -> subscriber.modelChanged(boxes));
    }

    public void addBox(double x, double y, double width, double height) {
        boxes.add(new Box(x, y, width, height));
        notifySubscribers();
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
        notifySubscribers();

    }

    public void moveCursorLeft() {
        if (this.cursorPosition == this.getSize() - 1) {
            this.currentBox.unsetCurrentBox();
            this.cursorPosition = -1;
        }
        if (cursorPosition != -1) {
            this.getCursorBox().unsetCurrentBox();
        }
        this.cursorPosition -= 1;
        this.currentBox = getCursorBox();
        this.currentBox.setCurrentBox();
        notifySubscribers();
    }

    public Box getCursorBox() {
        return boxes.get(cursorPosition);
    }

}
