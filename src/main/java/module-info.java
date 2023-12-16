module com.maker.foldermaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.commons.codec;

    opens com.maker.foldermaker to javafx.fxml;
    exports com.maker.foldermaker;
    exports com.maker.foldermaker.controller;
    exports com.maker.foldermaker.screen;
    opens com.maker.foldermaker.screen to javafx.fxml;
    opens com.maker.foldermaker.controller to javafx.fxml;
    opens com.maker.foldermaker.config.models to com.google.gson;

}