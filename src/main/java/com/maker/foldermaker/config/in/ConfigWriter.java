package com.maker.foldermaker.config.in;

import com.maker.filemanagement.ConfigHandlerDTO;
import com.maker.foldermaker.config.models.CfgDTOParent;
import com.maker.foldermaker.config.out.IConfigWriter;
import org.apache.commons.codec.digest.Crypt;

/**
 * Es el escritor de configuraciones, su funcionamiento es similar al lector.
 * @param <Cfg> Es la clase DTO especifica para la cual se escribiran las configuraciones.
 */
public class ConfigWriter<Cfg extends CfgDTOParent> implements IConfigWriter<Cfg> {
    private final ConfigHandlerDTO configHandlerDTO;
    private final String pathToConfig;

    /**
     * Inicializa las instancias de las que depende la clase.
     * @param configHandlerDTO Handler de configuraciones.
     * @param pathToConfig Ruta para la cual se escribiran las configuraciones (De no existir se creará el archivo.)
     */
    public ConfigWriter(ConfigHandlerDTO configHandlerDTO, String pathToConfig) {
        this.configHandlerDTO = configHandlerDTO;
        this.pathToConfig = pathToConfig;
    }

    /**
     * Escribe las configuraciones en el archivo de configuracion ubicado en la ruta pasa por el constructor.
     * @param configDTO Instancia DTO para la cual se escribiran las configuraciones.
     */
    @Override
    public void write(Cfg configDTO) {
        configHandlerDTO.fileManagement().deleteFile(pathToConfig);
        configHandlerDTO.fileManagement().writeLine(
                pathToConfig,
                configHandlerDTO.serializer().serialize(configDTO)
        );
    }
}
