package com.maker.foldermaker.context.in;

import com.maker.filemanagement.in.FileManagement;
import com.maker.filemanagement.out.IFileManagement;
import com.maker.foldermaker.config.in.ConfigReader;
import com.maker.foldermaker.config.models.ConfigDTO;
import com.maker.foldermaker.config.out.IConfigReader;
import com.maker.foldermaker.context.out.IScreenContext;
import com.maker.utils.PythonRunnable;
import com.maker.utils.Routes;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * Es el contexto principal de la aplicacion. Esta clase se encarga
 * de lo relacionado al uso practico, como la creacion de directorios
 * y la ejecucion del script.
 */

public class MainContext extends IScreenContext {
    private final IFileManagement fileManagement;
    private final PythonRunnable pythonRunnable;
    private final IConfigReader<ConfigDTO> configReader;
    public MainContext(FileManagement fileManagement, IConfigReader<ConfigDTO> configReader) {
        this.fileManagement = fileManagement;
        this.configReader = configReader;
        String currentDir = System.getProperty("user.dir");
        pythonRunnable = new PythonRunnable(
                currentDir+ Routes.PYTHON_DOWNLOADER.getPath(),
                "main.py"
        );
    }

    /**
     * Crea los directorios y, en caso de poder ejecuta el script.
     * @param textFieldNumberOfLab Es el numero de laboratorio para el cual se usará el programa.
     * @param checkBoxDownloadFiles Es el checkbox para la descarga de archivos (En caso de False solo se crearan directorios).
     */
    public void onDone(TextField textFieldNumberOfLab, CheckBox checkBoxDownloadFiles) {
        String numberOfLaboratory = textFieldNumberOfLab.getText();
        if (!numberOfLaboratory.isBlank() && !numberOfLaboratory.isEmpty() && isNumber(numberOfLaboratory)) {
            String dir = configReader.read().mainFolderPath() + "/" + configReader.read().mainFolderName().replace("*", numberOfLaboratory);
            fileManagement.createDir(dir);

            for (int i = 1; i <= configReader.read().rangeGroups(); i++)
                fileManagement.createDir(dir + "/GRUPO_" + i);
            sleep(1000);
            if (checkBoxDownloadFiles.isSelected())
                pythonRunnable.download(numberOfLaboratory, configReader.read().mainUri() );
        }
    }

    /**
     * Sirve para detener el hilo de ejecucion.
     * @param millis Son los milisegundos a detener.
     */
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
