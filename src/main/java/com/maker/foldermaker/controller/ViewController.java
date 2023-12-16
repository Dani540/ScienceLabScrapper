/**
 * Controlador para la vista principal, contiene la interaccion con el bot
 * y la interaccion con el resto de pantallas del programa.
 */

package com.maker.foldermaker.controller;

import com.maker.filemanagement.ConfigHandlerDTO;
import com.maker.filemanagement.in.FileManagement;
import com.maker.filemanagement.in.Serializer;
import com.maker.foldermaker.config.in.ConfigReader;
import com.maker.foldermaker.config.models.ConfigDTO;
import com.maker.foldermaker.config.out.IConfigReader;
import com.maker.foldermaker.context.in.MainContext;
import com.maker.foldermaker.screen.ScreenController;
import com.maker.foldermaker.screen.ScreenEnum;
import com.maker.utils.Routes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class ViewController {
    /**
     * ConfigHandlerDTO es un objeto de transferencia de datos
     * que me permite acceder de una forma mas dinamica tanto al gestor de archivos
     * como al serializador
     */
    private final ConfigHandlerDTO configHandlerDTO = new ConfigHandlerDTO(
            new Serializer<>(),
            new FileManagement()
    );
    /**
     * ConfigReader Es el lector de configuraciones especifico para
     * las configuraciones enviadas a traves del objeto de transferencia
     * de datos de tipo "Config", tuve que hacer un getter porque necesitaba sobreescribir el objeto por uno nuevo
     * con la misma configuracion o no se actualizaban los datos al disparar el evento del boton.
     */

    private IConfigReader<ConfigDTO> getConfigReader() {
        return new ConfigReader<>(
                configHandlerDTO,
                Routes.pathToConfig.getPath(),
                ConfigDTO.class
        );
    }

    private MainContext contextApp;
    @FXML
    private TextField textFieldNumberOfLab;
    @FXML
    private VBox vBoxContainer;
    @FXML
    private FlowPane flowPane;
    @FXML
    private CheckBox checkBoxDownloadFiles;
    @FXML
    private Button buttonDone;

    /**
     * Establece la descarga de archivos en true e instancia el
     * contexto de la pantalla donde se procesaran los datos.
     */
    public void initialize(){
        checkBoxDownloadFiles.setSelected(true);
        contextApp = new MainContext(new FileManagement(), new ConfigReader<>(
                configHandlerDTO,
                Routes.pathToConfig.getPath(),
                ConfigDTO.class
        ));
    }

    /**
     * Metodo de accion para el boton "Done", crea los directorios y
     * ejecuta el Script para la descarga automatica.
     */
    public void onDone(){
        contextApp.onDone(textFieldNumberOfLab,checkBoxDownloadFiles);
    }

    /**
     * Metodo de accion para el boton "Config", cambia la escena a
     * la escena de configuracion.
     */
    public void onConfigAction() {
        ScreenController.getInstance().switchScreen(ScreenEnum.CONFIG);
    }

    /**
     * Metodo de accion para el boton "User", cambia la escena a
     * la escena de credenciales del usuario.
     */
    public void onUserAction() {
        ScreenController.getInstance().switchScreen(ScreenEnum.USER_CRED);
    }
}
