package jbq061.assignment3;

public class IModel {
    private Box selected; // which circle is selected

    public IModel(){
        selected = null;
    }

    public void select(Box box) {
        selected = box;
    }

    public Box getSelected(){
        return selected;
    }

    public void unSelect(){
        this.selected = null;
    }
}

