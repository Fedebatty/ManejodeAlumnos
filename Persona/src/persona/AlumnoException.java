package persona;

public class AlumnoException extends PersonaException {
    /**
     * Constructor para manejar una excepción del un Alumno
     */
    public AlumnoException() {
        super("");
    }

    /**
     *Constructor para manejar una excepción del un Alumno
     * @param mensaje
     */
    public AlumnoException(String mensaje) {
        super(mensaje);
    }
    
}