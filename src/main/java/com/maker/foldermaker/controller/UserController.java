package com.maker.foldermaker.controller;

import com.maker.filemanagement.ConfigHandlerDTO;
import com.maker.filemanagement.in.FileManagement;
import com.maker.filemanagement.in.Serializer;
import com.maker.foldermaker.config.in.ConfigWriter;
import com.maker.foldermaker.config.models.UserDTO;
import com.maker.foldermaker.config.out.IConfigWriter;
import com.maker.foldermaker.screen.ScreenController;
import com.maker.foldermaker.screen.ScreenEnum;
import com.maker.utils.Crypter;
import com.maker.utils.Routes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Es el controlador de la pantalla "User".
 */
public class UserController {
    private final ConfigHandlerDTO configHandlerDTO = new ConfigHandlerDTO(
                    new Serializer<>(),
                    new FileManagement()
            );
    private final IConfigWriter<UserDTO> userWriter = new ConfigWriter<>(
            configHandlerDTO,
            Routes.pathToUserCredentials.getPath()
    );
    @FXML private TextField textFieldUsername;
    @FXML private PasswordField passFieldPassword;
    @FXML private Button buttonEnter;

    /**
     * Cuando se inicializa se leen los datos del usuario en el archivo de configuracion
     * y se asignan a los campos correspondientes.
     */
    public void initialize() {
        try {
            String credentials = configHandlerDTO.fileManagement().readFile(Routes.pathToUserCredentials.getPath());
            if (!credentials.isEmpty()) {
                UserDTO userJson = (UserDTO) configHandlerDTO.serializer().deserialize(credentials, UserDTO.class);
                textFieldUsername.setText(userJson.username());
                passFieldPassword.setText(userJson.password());
            }
        }catch (NullPointerException e){
            System.out.println("No se encontraron credenciales");
            configHandlerDTO.fileManagement().createFile(Routes.pathToUserCredentials.getPath());
        }
    }

    /**
     * Es el boton de accion para el boton "Done", si ambos campos estan completos se guarda la configuracion
     * en el archivo de configuracion del usuario.
     */
    public void setCredentials() {
        if (!textFieldUsername.getText().isBlank() || !passFieldPassword.getText().isBlank()) {
            userWriter.write(new UserDTO(textFieldUsername.getText(), passFieldPassword.getText()));
            ScreenController.getInstance().switchScreen(ScreenEnum.MAIN);
        }
    }
}
