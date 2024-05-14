module com.example.maruthielectricals {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires kernel;
    requires layout;
    requires io;
    requires java.sql;
    requires java.desktop;

    opens com.example.maruthielectricals to javafx.fxml;
    exports com.example.maruthielectricals;
}