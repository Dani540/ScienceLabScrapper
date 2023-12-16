package com.maker.foldermaker.context.out;

/**
 * Clase de abstraccion para uso comun de contextos.
 */
public abstract class IScreenContext {
    /**
     * Comprueba que una cadena pueda ser un numero mayor a 0.
     * @param number Es la cadena para la verificacion.
     * @return Devuelve si el numero es efectivamente un numero mayor a 0 o no.
     */
    public boolean isNumber(String number) {
        try{
            int test = Integer.parseInt(number);
            return test>0;
        }catch (NumberFormatException e){
            System.out.println("Insert Valid Number");
            return false;
        }
    }
}
