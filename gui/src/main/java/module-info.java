module gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;

    requires FrameWork;

    exports gui;
    opens gui to javafx.fxml;
}