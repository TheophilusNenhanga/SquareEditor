package jbq061.assignment3;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AppController {
	private BoxModel model;
	private IModel iModel;
	private long timeStart;
	private final PublishSubscribe publishSubscribe;

	public AppController(PublishSubscribe publishSubscribe) {
		this.publishSubscribe = publishSubscribe;
	}

	private enum InteractionState {
		READY, // ready to move into another state
		CREATE, // The state used when creating boxes
		DELETE, // The state used when deleting boxes
		UPDATE,  // The state used when updating boxes
		SELECTION, // The state used when selecting boxes
		CONTROL_DOWN // When the control key is down
	}

	private InteractionState state = InteractionState.READY;

	public void setModel(BoxModel model) {
		this.model = model;
	}

	public void setIModel(IModel iModel) {
		this.iModel = iModel;
	}

	public void handleKeyPressed(KeyEvent event) {
		if (event.isControlDown() && !(state == InteractionState.CONTROL_DOWN)) {
			this.timeStart = System.currentTimeMillis();
			state = InteractionState.CONTROL_DOWN;
		}
		if (event.isControlDown() && state == InteractionState.CONTROL_DOWN) {
			long timeEnd = System.currentTimeMillis();
			long deltaTime = timeEnd - this.timeStart;
			System.out.println(deltaTime);
			if (deltaTime >= 1000) {
				publishSubscribe.publish("update", "show");
			}
		}

		// When keys are pressed we decide which state we must enter based on which key was pressed.
		if (event.isControlDown() && event.getCode() == KeyCode.C) {
			this.state = InteractionState.CREATE;
		}

		if (event.isControlDown() && (event.getCode() == KeyCode.S || event.getCode() == KeyCode.A)) {
			this.state = InteractionState.SELECTION;
		}

		if (event.isControlDown() && event.getCode() == KeyCode.D) {
			this.state = InteractionState.DELETE;
		}

		if (event.isControlDown() && (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.U || event.getCode() == KeyCode.J || event.getCode() == KeyCode.L || event.getCode() == KeyCode.R || event.getCode() == KeyCode.B || event.getCode() == KeyCode.T || event.getCode() == KeyCode.H || event.getCode() == KeyCode.V)) {
			this.state = InteractionState.UPDATE;
		}
	}

	public void handleKeyReleased(KeyEvent event) {
		switch (this.state) {
			case CREATE -> {
				double[] position = boxPosition(100, 100); // getting the position of the box to be created.
				this.model.addBox(position[0], position[1], 50, 50);
				publishSubscribe.publish("create", model.getBoxes());
				state = InteractionState.READY;
			}

			case SELECTION -> {
				if (event.getCode() == KeyCode.A) {
					iModel.selectAll(model.getBoxes());
					publishSubscribe.publish("selection", model.getBoxes());
				}
				if (event.getCode() == KeyCode.S) {
					iModel.toggleSelect(model.getCursorBox());
					publishSubscribe.publish("selection", model.getBoxes());
				}
				state = InteractionState.READY;
			}

			case DELETE -> {
				int numberSelected = iModel.getSelected().size();
				model.delete(iModel.getSelected());
				iModel.unselectAll();
				for (int i = 0; i < numberSelected; i++) {
					publishSubscribe.publish("delete", model.getBoxes());
				}
				state = InteractionState.READY;
			}

			case UPDATE -> {
				if (event.getCode() == KeyCode.LEFT) {
					model.moveLeft(iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.RIGHT) {
					model.moveRight(iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.UP) {
					model.moveUp(iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.DOWN) {
					model.moveDown(iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.U) {
					model.increaseSize(iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.J) {
					model.decreaseSize(iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.L) {
					model.alignLeft(model.getCursorBox(), iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.R) {
					model.alignRight(model.getCursorBox(), iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.B) {
					model.alignBottom(model.getCursorBox(), iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.T) {
					model.alignTop(model.getCursorBox(), iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.H) {
					model.divideEvenlyHorizontal(iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				if (event.getCode() == KeyCode.V) {
					model.divideEvenlyVertical(iModel.getSelected());
					publishSubscribe.publish("update", model.getBoxes());
				}
				state = InteractionState.READY;
			}

			case READY -> { // The events for traversing with the cursor - not attached to a state.
				if (event.getCode() == KeyCode.TAB) {
					model.moveCursorRight();
					publishSubscribe.publish("update", model.getBoxes());
				}

				if ((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) && !event.isControlDown()) {
					Box[] boxes = model.getClosestBoxes();
					Box closestLeft = boxes[0], closestRight = boxes[1], closestTop = boxes[2], closestBottom = boxes[3];

					switch (event.getCode()) {
						case LEFT -> {
							model.moveCursorToBox(closestLeft);
							publishSubscribe.publish("update", model.getBoxes());
						}
						case RIGHT -> {
							model.moveCursorToBox(closestRight);
							publishSubscribe.publish("update", model.getBoxes());
						}
						case UP -> {
							model.moveCursorToBox(closestTop);
							publishSubscribe.publish("update", model.getBoxes());
						}
						case DOWN -> {
							model.moveCursorToBox(closestBottom);
							publishSubscribe.publish("update", model.getBoxes());
						}
					}
					event.consume();
				}

				if (event.getCode() == KeyCode.CONTROL) {
					state = InteractionState.READY;
					publishSubscribe.publish("update", "hide");
				}
			}
		}
	}

	/**
	 * gets the x and y positions of the next box to be placed.
	 * @param x The start x position of all boxes
	 * @param y the start y position of all boxes
	 * @return an array with the x and y coordinates of the new box position
	 */
	public double[] boxPosition(double x, double y) {
		int numBoxes = model.getSize();
		return new double[]{x + ((numBoxes - 1) * 50), y + ((numBoxes - 1) * 50)};
	}
}
