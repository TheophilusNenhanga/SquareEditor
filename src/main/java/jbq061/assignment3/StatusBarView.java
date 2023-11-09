package jbq061.assignment3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class StatusBarView extends HBox implements Subscriber {

	private final IntegerProperty sinceStartProperty;
	private final IntegerProperty deletedProperty;
	private final IntegerProperty currentProperty;

	public StatusBarView() {
		currentProperty = new SimpleIntegerProperty(0);
		deletedProperty = new SimpleIntegerProperty(0);
		sinceStartProperty = new SimpleIntegerProperty(0);

		Label startLabel = new Label();
		Label deletedLabel = new Label();
		Label currentLabel = new Label();

		startLabel.textProperty().bind(Bindings.concat("Created: ", sinceStartProperty, "\t"));
		deletedLabel.textProperty().bind(Bindings.concat("Deleted: ", deletedProperty, "\t"));
		currentLabel.textProperty().bind(Bindings.concat("Current: ", currentProperty, "\t"));

		Pane statusBarContainer = new HBox();
		statusBarContainer.setMinWidth(600);
		statusBarContainer.setMaxWidth(1000);
		statusBarContainer.setPrefWidth(800);
		statusBarContainer.setStyle("-fx-padding: 0.75em; -fx-background-color: white; -fx-font-weight: bold");
		statusBarContainer.getChildren().addAll(startLabel, deletedLabel, currentLabel);

		this.getChildren().add(statusBarContainer);
	}

	@Override
	public void receiveNotification(String channelKey, Object changedState) {
		switch (channelKey) {
			case "create" -> {
				sinceStartProperty.set(sinceStartProperty.get() + 1);
				currentProperty.set(currentProperty.get() + 1);
			}
			case "delete" -> {
				currentProperty.set(currentProperty.get() - 1);
				deletedProperty.set(deletedProperty.get() + 1);
			}
		}
	}
}
