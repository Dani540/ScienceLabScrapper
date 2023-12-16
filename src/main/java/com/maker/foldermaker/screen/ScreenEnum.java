package com.maker.foldermaker.screen;

/**
 * Este enumerador guarda las constantes que hacen referencia a las distintas escenas
 * y sus referencias DTO.
 */
public enum ScreenEnum {
    MAIN, CONFIG, USER_CRED;
    private ScreenDTO screenDTO;
    public ScreenDTO getScreenDTO() {
        return screenDTO;
    }
    public void setScreenDTO(ScreenDTO screenDTO) {
        this.screenDTO = screenDTO;
    }
}

