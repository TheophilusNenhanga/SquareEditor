module jbq061.assignment3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens jbq061.assignment3 to javafx.fxml;
    exports jbq061.assignment3;
}