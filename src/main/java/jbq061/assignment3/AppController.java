package jbq061.assignment3;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AppController {
    private BoxModel model;
    private IModel iModel;

    private long timeStart;
    private boolean keyPressed;

    private enum InteractionState {
        READY,
        PREPARE_CREATE
    }
    private InteractionState state = InteractionState.READY;

    public void setModel(BoxModel model) {
        this.model = model;
    }

    public void setIModel(IModel iModel) {
        this.iModel = iModel;
    }

    public void handleKeyPressed(KeyEvent event){
        switch (this.state){
            case PREPARE_CREATE -> {

            }
            case READY -> {
                if (event.isControlDown() && event.getCode() == KeyCode.C){
                    double[] position = boxPosition(100, 100);
                    this.model.addBox(position[0], position[1], 50, 50);
                }

                if (event.getCode() == KeyCode.TAB){
                    model.moveCursorRight();
                    iModel.select(model.getCursorBox());
                }
            }
        }
    }

    public void handleKeyReleased(KeyEvent event){
        switch (this.state){

        }
    }

    public double[] boxPosition(double x, double y){
        int numBoxes = model.getSize();
        return new double[]{x + ((numBoxes - 1) * 50) ,  y + ((numBoxes - 1) * 50)};
    }
}
