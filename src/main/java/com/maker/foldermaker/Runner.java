package com.maker.foldermaker;

import com.maker.filemanagement.in.FileManagement;
import com.maker.filemanagement.out.IFileManagement;
import com.maker.foldermaker.screen.ScreenController;
import com.maker.foldermaker.screen.ScreenDTO;
import com.maker.foldermaker.screen.ScreenEnum;
import com.maker.utils.Routes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Esta clase sirve como punto de arranque del programa.
 */
public class Runner {
    private final Stage stage;
    public Runner(Stage stage) {
        this.stage = stage;
    }

    /**
     * Al inicializar se instancian, configuran y guardan todos los objetos que
     * no serán modificados y siempre deben estar disponibles en el programa, como
     * la ventana principal, la configuracion del gestor de ventanas, la carga y
     * asignacion de escenas, los estilos y demas.
     * @return Devuelve una instancia con el stage listo para mostrar. (Puro capricho esta clase jldskds)
     */
    public RunnerConfig initialize() {
        stage.setTitle("Science Laboratory Automation by dani-tk");
        stage.setMinHeight(200);
        stage.setMinWidth(300);

        ScreenController.getInstance().setStage(stage);
        ScreenEnum.MAIN.setScreenDTO(new ScreenDTO(loadScene(Routes.MAIN_FXML)));
        ScreenEnum.CONFIG.setScreenDTO(new ScreenDTO(loadScene(Routes.CONFIG_FXML)));
        ScreenEnum.USER_CRED.setScreenDTO(new ScreenDTO(loadScene(Routes.USER_CRED_FXML)));

        ScreenEnum.MAIN.getScreenDTO()     .scene().getStylesheets().add(String.valueOf(Objects.requireNonNull(getClass().getResource("style.css"))));
        ScreenEnum.CONFIG.getScreenDTO()   .scene().getStylesheets().add(String.valueOf(Objects.requireNonNull(getClass().getResource("style.css"))));
        ScreenEnum.USER_CRED.getScreenDTO().scene().getStylesheets().add(String.valueOf(Objects.requireNonNull(getClass().getResource("style.css"))));

        return new RunnerConfig(stage, new FileManagement());
    }

    /**
     * Este metodo sirve para cargar escenas desde archivos fxml, para simplificar y generalizar codigo.
     * @param path Es la ruta donde se aloja el archivo fxml, es una constante del enumerador de rutas.
     * @return Devuelve la instancia de la escena cargada.
     */
    private Scene loadScene(Routes path){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path.getPath()));
        Scene scene = null;
        try {
            scene = new Scene(loader.load(), 400, 320);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return scene;
    }

    /**
     * Esta clase fue puro capricho, pero obvio sigue siendo util, funciona
     * como un tipo de "estructura" pero con logica y sirve para mostrar el stage o ventana
     * con la escena correspondiente.
     */
    protected static final class RunnerConfig {
        private final Stage stage;
        private final IFileManagement fileManagement;
        public RunnerConfig(Stage stage, IFileManagement fileManagement){
            this.stage = stage;
            this.fileManagement = fileManagement;
        }

        /**
         * Este metodo muestra la ventana y asigna la escena correspondiente en funcion de si
         * el programa contiene credenciales de usuario, en caso de no contener, deben ingresarse,
         * si no solo se muestra la pantalla principal
         */
        public void run(){
            String credentials = fileManagement.readFile(Routes.pathToUserCredentials.getPath());
            if (credentials != null && !credentials.isEmpty()) {
                ScreenController.getInstance().switchScreen(ScreenEnum.MAIN);
            } else {
                ScreenController.getInstance().switchScreen(ScreenEnum.USER_CRED);
            }
            stage.show();
        }
    }
}
