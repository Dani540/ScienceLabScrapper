package com.maker.utils;

/**
 * Este enumerador contiene todas las rutas usadas durante el programa, probablemente tambien
 * deberia segregar con algun controlador.
 */
public enum Routes {
    pathToConfig("src/main/java/com/maker/config.json"),
    pathToUserCredentials("src/main/java/com/maker/utils/downloader/user_credentials.json"),
    PYTHON_DOWNLOADER("/src/main/java/com/maker/utils/downloader"),
    MAIN_FXML("view.fxml"),
    CONFIG_FXML("config.fxml"),
    USER_CRED_FXML("user.fxml");

    private final String path;
    Routes(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }
}
