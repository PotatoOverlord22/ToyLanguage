module ToyLanguage {
    requires javafx.fxml;
    requires javafx.controls;
    requires org.junit.jupiter.api;

    opens view.graphicalview;
    opens view.textview;
}