package com.maker.foldermaker.screen;

import javafx.scene.Scene;

/**
 * Este DTO sirve para la inicializacion de las escenas disponibles en el programa, se usa
 * en el Runner.
 * @param scene Es la escena de la pantalla.
 */
public record ScreenDTO(Scene scene) {
}
