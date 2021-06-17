package persona;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Persona {
    
    public static final String DELIM = "\t";
    private long dni;
    private String nombre;
    private String apellido;
    protected MiCalendario fechaNac;
    private char sexo;

    // Suits de constructores
    /**
     * costructor vacio
     */
    public Persona() {
    }
   
    /**
     *  costructor DNI
     * @param dni
     * @throws PersonaException
     */
    public Persona(long dni) throws PersonaException {
        setDni(dni);
    }

      /**
     * costructor dni nombre apellido
     * @param dni
     * @param nombre
     * @param apellido
     * @throws PersonaException
     */
    public Persona(long dni, String nombre, String apellido) throws PersonaException {
        setDni(dni);
        setNombre(nombre);
        setApellido(apellido);
    }

    /**
     * costructor completo
     * @param dni
     * @param nombre
     * @param apellido
     * @param fechaNac
     * @param sexo
     * @throws PersonaException
     */
   public Persona(long dni, String nombre, String apellido, MiCalendario fechaNac, char sexo) 
            throws PersonaException {
        setDni(dni);
        setNombre(nombre);
        setApellido(apellido);
        this.fechaNac = fechaNac;
        setSexo(sexo);
    }

    /**
     * Devuelve el dni del alumno
     * @return El dni
     */
    public long getDni() {
        return dni;
    }

      /**
     * Setea el DNI
     * @param dni
     * @throws persona.PersonaException
     */
    public  void setDni(long dni) throws PersonaException {
        if (dni<=0) {
            throw new PersonaException("El DNI debe ser positivo ("+dni+")");
        } 
        this.dni = dni;
    }

    /**
     * Devuelve el Nombre del alumno
     * @return El Nombre
     */
    public String getNombre() {
        return nombre;
    }
    
      /**
     * Setea el nombre del alumno
     * @param nombre
     * @throws persona.PersonaException
     */
    public  void setNombre(String nombre) throws PersonaException {
        if (nombre==null || nombre.trim().equals("")) {
            throw new PersonaException("El nombre no debe estar vacio");
        }
        this.nombre = nombre.trim();
    }

    /**
     * Devuelve el apellido del alumno
     * @return El apellido
     */
    public String getApellido() {
        return apellido;
    }

      /**
     * Setea el apellido del alumno
     * @param apellido
     * @throws persona.PersonaException
     */
    public  void setApellido(String apellido) throws PersonaException {
        if (apellido==null || apellido.trim().equals("")) {
            throw new PersonaException("El apellido no debe estar vacio");
        }
        this.apellido = apellido.trim();
    }

    /**
     * Devuelve la fecha de nacimiento del alumno
     * @return La fecha de nacimiento
     */
    public MiCalendario getFechaNac() {
        return fechaNac;
    }

      /**
     * Setea la fecha de nacimiento
     * @param fechaNac
     */
    public void setFechaNac(MiCalendario fechaNac) {
        this.fechaNac = fechaNac;
    }
 
     /**
     * Devuelve la Edad del alumno
     * @return La edad
     */   
    public int getEdad() { 
        LocalDateTime ahora = LocalDateTime.now();  
        LocalDate  fechaActual= LocalDate.of(ahora.getYear(),ahora.getMonth(), ahora.getDayOfMonth());
        LocalDate  fechaDeNacimiento = LocalDate.of(getFechaNac().getAnio(), getFechaNac().getMes(),getFechaNac().getDia());
        Period period = Period.between(fechaDeNacimiento, fechaActual);
        return period.getYears();
    }

    /**
     * Devuelve el sexo del alumno
     * @return El sexo
     */    
     public char getSexo() {
        return sexo;
    }

      /**
     * Setea el sexo del alumno
     * @param sexo
     * @throws persona.PersonaException
     */
    public void setSexo(char sexo) throws PersonaException {
        sexo = Character.toUpperCase(sexo);
        if (sexo!='F' && sexo!='M') {
            throw new PersonaException("El sexo debe ser F o M (o f o m)"); 
        }
        this.sexo = sexo;
    }  
    
    /**
     * Pasa a String los datos d persona
     * @return
     */
    @Override 
    public String toString() { 
        String nombreStr = nombre.length()>20?nombre.substring(0,20):nombre;
        String apellidoStr = apellido.length()>20?apellido.substring(0,20):apellido;
        return String.format("%8d", dni) + DELIM +
                String.format("%20s", nombreStr) + DELIM +
                String.format("%15s", apellido) + DELIM +
                fechaNac + DELIM +
                sexo;
    }   

}


