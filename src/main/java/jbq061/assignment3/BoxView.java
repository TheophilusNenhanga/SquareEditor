package jbq061.assignment3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.List;

public class BoxView extends StackPane implements Subscriber {

	Canvas canvas;
	GraphicsContext graphicsContext;

	ImageView guide;

	public BoxView() {
		this.canvas = new Canvas(800, 800);
		graphicsContext = canvas.getGraphicsContext2D();
		this.setStyle("-fx-background-color: #004f0a");
		graphicsContext.setFill(Color.FORESTGREEN);
		this.getChildren().add(canvas);
		guide = new ImageView("guide.png");
	}

	public void setupEvents(AppController controller) {
		this.setOnKeyPressed(controller::handleKeyPressed);
		this.setOnKeyReleased(controller::handleKeyReleased);
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

	@Override
	public void receiveNotification(String channelKey, Object changedState) {
		if (changedState instanceof String stateString) {
			if (stateString.equals("show")) {
				// show the guide
				if (!(this.getChildren().contains(guide))) this.getChildren().add(guide);
			}
			if (stateString.equals("hide")) {
				// hide the guide
				this.getChildren().remove(guide);
			}
		}
		if (changedState instanceof List<?>) {
			List<Box> boxes = (List<Box>) changedState;
			draw(boxes);
		}
	}
}
