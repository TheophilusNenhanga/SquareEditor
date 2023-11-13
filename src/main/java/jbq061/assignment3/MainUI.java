package jbq061.assignment3;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainUI extends StackPane {
	private final BoxView view;

	public MainUI() {
		PublishSubscribe publishSubscribe = new PublishSubscribe();
		view = new BoxView();
		StatusBarView statusView = new StatusBarView();
		// BoxView subscriptions
		publishSubscribe.subscribe("create", view);
		publishSubscribe.subscribe("delete", view);
		publishSubscribe.subscribe("update", view);
		publishSubscribe.subscribe("selection", view);
		// statusView subscriptions
		publishSubscribe.subscribe("create", statusView);
		publishSubscribe.subscribe("delete", statusView);

		BoxModel model = new BoxModel();
		AppController controller = new AppController(publishSubscribe);
		IModel iModel = new IModel();

		// Linking
		view.setupEvents(controller);
		controller.setModel(model);
		model.addSubscriber(view);
		controller.setIModel(iModel);

		VBox container = new VBox();
		container.getChildren().addAll(view, statusView);
		container.setAlignment(Pos.BOTTOM_LEFT);
		this.setStyle("-fx-background-color: #004f0a");
		this.getChildren().add(container);

	}

	public void setFocus() {
		view.requestFocus();
	}
}
