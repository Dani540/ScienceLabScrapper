package com.maker.foldermaker;

import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        new Runner(stage).initialize().run();
    }

    public static void main(String[] args) {
        launch();
    }
}