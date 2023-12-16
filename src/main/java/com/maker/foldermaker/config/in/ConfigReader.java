package com.maker.foldermaker.config.in;

import com.maker.filemanagement.ConfigHandlerDTO;
import com.maker.foldermaker.config.models.CfgDTOParent;
import com.maker.foldermaker.config.out.IConfigReader;
import com.maker.utils.Crypter;

/**
 * Esta clase es la implementacion de la interface IConfigReader, cuyo proposito
 * es servir como "abstraccion" o generalizacion para la lectura de datos en archivos de configuracion.
 * @param <Cfg> Es la clase especifica para la que se leeran las configuraciones, debe ser de tipo DTO y debe implementar la interface comun a todos estos.
 */

public class ConfigReader<Cfg extends CfgDTOParent> implements IConfigReader<Cfg> {

    private final ConfigHandlerDTO configHandlerDTO;
    private final String pathToConfig;
    private Class<Cfg> cfgClass;

    /**
     * En el constructor se inicializan las instancias de las que depende la clase para cumplir su proposito.
     * @param configHandlerDTO Es el handler para la gestion de archivos (Lector de archivos y serializador).
     * @param pathToConfig Es la ruta del archivo de configuracion.
     * @param cfgClass Es la clase especifica para obtener la instancia DTO con las configuraciones.
     */
    public ConfigReader(ConfigHandlerDTO configHandlerDTO, String pathToConfig, Class<Cfg> cfgClass) {
        this.configHandlerDTO = configHandlerDTO;
        this.pathToConfig = pathToConfig;
        this.cfgClass = cfgClass;
    }

    /**
     * Es el metodo que lee las configuraciones, esta hecho de tal forma que, si la clase entregada no implementa la
     * interface comun a los DTO, no se podrán obtener.
     * @return Devuelve el objeto DTO especificado al momento de instanciar esta clase con las configuraciones respectivas.
     */
    @Override
    public Cfg read() {
        Cfg configDTO = null;
        try {
            configDTO = (Cfg) configHandlerDTO.serializer().deserialize(configHandlerDTO.fileManagement().readFile(pathToConfig), cfgClass);
        }catch (ClassCastException e) {
            System.out.println("La clase especificada no implementa la interface CfgDTOParent");
        }catch (NullPointerException e){
            System.out.println("No se encontraron configuraciones");
        }catch (IllegalStateException e){
            System.out.println("El archivo de configuracion esta corrupto");
        }
        catch (Exception e){
            System.out.println("Error desconocido");
        }
        return configDTO;
    }
}
