package jbq061.assignment3;

import java.util.ArrayList;

public class IModel {
	private final ArrayList<Box> selected; // which circle is selected
	public boolean allSelected;

	public IModel() {
		selected = new ArrayList<>();
		allSelected = false;
	}

	public void select(Box box) {
		box.select();
		selected.add(box);
	}

	public ArrayList<Box> getSelected() {
		return selected;
	}

	public void unSelect(Box box) {
		selected.remove(box);
		box.unselect();
	}


	public void toggleSelect(Box box) {
		if (box == null) return;
		if (box.isSelected) {
			unSelect(box);
		} else {
			select(box);
		}
	}

	public void unselectAll() {
		selected.forEach(Box::unselect);
		selected.clear();
	}

	public void selectAll(ArrayList<Box> boxes) {
		if (!allSelected) {
			boxes.forEach(Box::select);
			this.selected.addAll(boxes);
			allSelected = true;
		} else {
			boxes.forEach(Box::unselect);
			this.unselectAll();
			allSelected = false;
		}

	}
}

