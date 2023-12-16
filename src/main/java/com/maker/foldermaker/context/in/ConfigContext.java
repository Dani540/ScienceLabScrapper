package com.maker.foldermaker.context.in;

import com.maker.foldermaker.config.models.CfgDTOParent;
import com.maker.foldermaker.config.models.ConfigDTO;
import com.maker.foldermaker.config.out.IConfigReader;
import com.maker.foldermaker.config.out.IConfigWriter;
import com.maker.foldermaker.context.out.IScreenContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * Esta clase sirve de contexto para la escena de configuracion, aqui esta
 * la implementacion de cada funcionalidad.
 */
public class ConfigContext extends IScreenContext {
    private final IConfigReader<ConfigDTO> configReader;
    private final IConfigWriter<ConfigDTO> configWriter;

    /**
     * Para funcionar, la clase depende de un lector y un escritor de configuracion, ya definidos.
     * @param configReader Es el lector, debe leer archivos de tipo ConfigDTO.
     * @param configWriter Es el escritor, solo puede escribir configuraciones para ConfigDTO.
     */
    public ConfigContext(IConfigReader<ConfigDTO> configReader, IConfigWriter<ConfigDTO> configWriter) {
        this.configReader = configReader;
        this.configWriter = configWriter;
    }

    /**
     * Este metodo se usa al inicializar la pantalla y genera una relacion entre dos elementos
     * con un listener, para deshabilitar un elemento en caso de usar el otro.
     * @param textFieldMainUri Es el textfield para ingresar una URI especifica para el uso del programa.
     * @param checkLabNumberUse Es el checkbox que indica si se usará el metodo del script o la URI especifica.
     */
    public void setUriLabRelation(TextField textFieldMainUri, CheckBox checkLabNumberUse) {
        checkLabNumberUse.setSelected(true);
        textFieldMainUri.setDisable(true);

        checkLabNumberUse.selectedProperty().addListener( (obs, oldV, newV) ->{
            textFieldMainUri.setDisable(newV);
        });
    }

    /**
     * Este metodo es el action del boton "Save", se usa para escribir la configuracion
     * y cambiar la escena.
     * @param mainFolderName Es el nombre de la carpeta principal (Contenedora de grupos).
     * @param mainFolderPath Es la ruta donde se creara la carpeta principal con todas las subcarpetas.
     * @param mainUri Es la ruta web principal, en caso de no especificar ninguna se usa la por defecto
     * @param rangeGroups Es el rango de grupos para la creacion de carpetas internas.
     * @param useNumberOfLab Es el boolean para el uso del metodo del script o de una URI especifica.
     */
    public void write(String mainFolderName, String mainFolderPath, String mainUri, int rangeGroups, boolean useNumberOfLab) {
        configWriter.write(
                new ConfigDTO(
                    rangeGroups,
                    mainFolderName,
                    mainFolderPath,
                    mainUri,
                    useNumberOfLab
        ));
    }

    /**
     * Este metodo lee la configuracion.
     * @return Devuelve una instancia DTO con las configuraciones.
     */
    public CfgDTOParent read() {
        return configReader.read();
    }


}
