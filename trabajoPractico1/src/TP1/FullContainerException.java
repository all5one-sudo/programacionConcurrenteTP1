package TP1;

/**
 * Error a lanzar cuando se llena un contenedor
 */
public class FullContainerException extends Exception {
    /**
     * @param errorMessage Mensaje de error a mostrar: tipicamente: "Contenedor
     *                     lleno"
     */
    public FullContainerException(String errorMessage) {
        super(errorMessage);
    }
}
