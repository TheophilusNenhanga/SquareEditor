package jbq061.assignment3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.List;

public class BoxView extends StackPane implements Subscriber {

	Canvas canvas;
	GraphicsContext graphicsContext;

	public BoxView() {
		this.canvas = new Canvas(800, 800);
		this.getChildren().add(canvas);
		graphicsContext = canvas.getGraphicsContext2D();
	}

	public void setupEvents(AppController controller) {
		this.setOnKeyPressed(controller::handleKeyPressed);
		this.setOnKeyReleased(controller::handleKeyReleased);
	}

	@Override
	public void modelChanged(List<Box> boxes) {
		// whenever the model is changed redraw tha canvas
		draw(boxes);
	}

	public void draw(List<Box> boxes) {
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.setFill(Color.BLACK);
		for (Box box : boxes) {
			if (box.isCurrentBox()) {
				graphicsContext.setFill(Color.RED);

			} else {
				graphicsContext.setFill(Color.LIGHTBLUE);
			}

			if (box.isSelected) {
				graphicsContext.setStroke(Color.YELLOW);
				graphicsContext.setLineWidth(5);
			} else {
				graphicsContext.setStroke(Color.DARKGRAY);
			}
			graphicsContext.fillRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
			graphicsContext.setLineWidth(2);
			graphicsContext.strokeRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());

		}
	}

}
