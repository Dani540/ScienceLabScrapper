package com.maker.foldermaker.controller;

import com.maker.filemanagement.ConfigHandlerDTO;
import com.maker.filemanagement.in.FileManagement;
import com.maker.filemanagement.in.Serializer;
import com.maker.foldermaker.config.models.ConfigDTO;
import com.maker.foldermaker.config.in.ConfigReader;
import com.maker.foldermaker.config.in.ConfigWriter;
import com.maker.foldermaker.context.in.ConfigContext;
import com.maker.foldermaker.screen.ScreenController;
import com.maker.foldermaker.screen.ScreenEnum;
import com.maker.utils.Routes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * Es el controlador de la escena "Config".
 */

public class ConfigController {
    @FXML
    private TextField textFieldNumberOfGroups;
    @FXML
    private TextField textFieldRouteToCreateFolder;
    @FXML
    private TextField textFieldNameOfMainFolder;
    @FXML
    private CheckBox checkLabNumberUse;
    @FXML
    private TextField textFieldMainUri;
    @FXML
    private Button buttonSave;

    private final ConfigHandlerDTO configHandlerDTO = new ConfigHandlerDTO(
            new Serializer<>(),
            new FileManagement()
    );

    private final ConfigContext configContext = new ConfigContext(
            new ConfigReader<>(configHandlerDTO, Routes.pathToConfig.getPath(), ConfigDTO.class),
            new ConfigWriter<>(configHandlerDTO, Routes.pathToConfig.getPath())
    );

    /**
     * Al momento de inicializar la pantalla, se leen las configuraciones actuales
     * y se asignan a los campos correspondientes.
     */
    public void initialize(){

        try {
            ConfigDTO configDTO = (ConfigDTO) configContext.read();

            textFieldRouteToCreateFolder.setText(configDTO.mainFolderPath());
            textFieldNameOfMainFolder.setText(configDTO.mainFolderName());
            textFieldMainUri.setText(configDTO.mainUri());
            checkLabNumberUse.setSelected(configDTO.useNumberOfLab());
            textFieldNumberOfGroups.setText(String.valueOf(configDTO.rangeGroups()));

            configContext.setUriLabRelation(textFieldMainUri, checkLabNumberUse);

        }   catch (NullPointerException e){
            System.err.println("Config File is null");
            configHandlerDTO.fileManagement().createFile(Routes.pathToConfig.getPath());

        }
    }

    /**
     * Cuando se guardan las configuraciones, se hace una verificacion de la
     * integridad de los datos primitivos y luego se escriben en el archivo
     * de configuracion. Luego se cambia de escena.
     */
    public void onSave() {
        int rangeLabs = configContext.isNumber(textFieldNumberOfGroups.getText())
                ? Integer.parseInt(textFieldNumberOfGroups.getText())
                : 1;
        configContext.write(
                textFieldNameOfMainFolder.getText(),
                textFieldRouteToCreateFolder.getText(),
                textFieldMainUri.getText(),
                rangeLabs,
                checkLabNumberUse.isSelected()
        );
        ScreenController.getInstance().switchScreen(ScreenEnum.MAIN);
    }
}
