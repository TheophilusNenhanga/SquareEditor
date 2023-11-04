package jbq061.assignment3;

import javafx.scene.layout.StackPane;

public class MainUI extends StackPane {
    private final BoxView view;

    public MainUI(){
        BoxModel model = new BoxModel();
        view = new BoxView();
        AppController controller = new AppController();
        IModel iModel = new IModel();

        // Linking
        view.setupEvents(controller);
        controller.setModel(model);
        model.addSubscriber(view);
        controller.setIModel(iModel);
        this.getChildren().add(view);
    }

    public void setFocus(){
        view.requestFocus();
    }
}
