package persona;

public final class Alumno extends Persona {
    
    private MiCalendario fechaIng;
    private Integer cantMatAprob;
    private Double promedio;
    private boolean activo = true;

     /**
     * constructor VACIO - solo persona
     */
    public Alumno() {
        super();
    }

    /**
     * costructor para complemetar el vacio
     * @param fechaIng
     * @param cantMatAprob
     * @param promedio
     * @throws persona.AlumnoException
     */
    public Alumno(MiCalendario fechaIng, Integer cantMatAprob, Double promedio) throws AlumnoException {
        setFechaIng(fechaIng);
        setCantMatAprob(cantMatAprob);
        setPromedio(promedio);
    }

    /**
     * constructor para TODOS los ATRIBUTOS de PERSONA y ALUMNO
     * @param dni
     * @param fechaIng
     * @param cantMatAprob
     * @param promedio
     * @throws PersonaException
     */
    public Alumno(long dni, MiCalendario fechaIng, Integer cantMatAprob, Double promedio) throws PersonaException {
        super(dni);
        setFechaIng(fechaIng);
        setCantMatAprob(cantMatAprob);
        setPromedio(promedio);
    }

    /**
     * constructor para TODOS los ATRIBUTOS de PERSONA + ALUMNO  faltante
     * @param dni
     * @param nombre
     * @param apellido
     * @param fechaIng
     * @param cantMatAprob
     * @param promedio
     * @throws PersonaException
     */
    public Alumno(long dni, String nombre, String apellido, MiCalendario fechaIng, Integer cantMatAprob, Double promedio) 
            throws PersonaException {
        super(dni, nombre, apellido);
        setFechaIng(fechaIng);
        setCantMatAprob(cantMatAprob);
        setPromedio(promedio);
    }

      /**
     * constructor para TODOS los ATRIBUTOS de PERSONA + ALUMNO  completo
     * @param dni
     * @param nombre
     * @param apellido
     * @param fechaNac
     * @param fechaIng
     * @param cantMatAprob
     * @param promedio
     * @param sexo
     * @param activo
     * @throws PersonaException
     */
    public Alumno(long dni, String nombre, String apellido, MiCalendario fechaNac, MiCalendario fechaIng, 
            Integer cantMatAprob, Double promedio, char sexo, boolean activo) 
            throws PersonaException {
        super(dni, nombre, apellido, fechaNac, sexo);
        setFechaIng(fechaIng);
        setCantMatAprob(cantMatAprob);
        setPromedio(promedio);
        this.activo = activo;
    }
    
    /**
     *Devuelve fecha de ingreso
     * @return fechaIng
     */
    public MiCalendario getFechaIng() {
        return fechaIng;
    }

    /**
     * setea la fecha de ingreso
     * @param fechaIng
     * @throws AlumnoException
     */
    public final void setFechaIng(MiCalendario fechaIng) throws AlumnoException {
        if (fechaNac==null) {
            throw new AlumnoException("Se debe setear la fecha de Nacimiento");
        }
        if (fechaIng.before(fechaNac)) {
            throw new AlumnoException("La fecha de Ingreso deber ser mayor a la fecha de Nacimineto");
        }
        this.fechaIng = fechaIng;
    }

    /** 
     * Devuelve Cantidad de Materias Aprobadas
     * @return cantMatAprob
     */
    public Integer getCantMatAprob() {
        return cantMatAprob;
    }

    /**
     * setea cantidad de materias aprobadas
     * @param cantMatAprob
     * @throws AlumnoException
     */
    public void setCantMatAprob(Integer cantMatAprob) throws AlumnoException {
        if(cantMatAprob==null){
            throw new AlumnoException("La cantidad de materias no puede estar vacio");
        }
        if(cantMatAprob<0){
            throw new AlumnoException("La cantidad de materias aprobadad no puede ser negativo");
        }       
        this.cantMatAprob = cantMatAprob;
    }

    /**
     * Devuelve el promedio del alumno
     * @return promedio
     */
    public Double getPromedio() {
        return promedio;
    }

    /**
     * Setea promedio del alumno
     * @param promedio
     * @throws AlumnoException
     */
    public void setPromedio(Double promedio) throws AlumnoException {
        if(promedio < 0 || promedio > 10 ) {
            throw new AlumnoException("El promedio debe comprenderse entre 0 y 10");
        }
        else {
        this.promedio = promedio;
        }
    }

    /**
     * Devuelve si el alumno está activo o no
     * @return
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * setea el estado del alumno
     * @param activo
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * devuelve los datos del alumno  en String
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + DELIM +
                fechaIng + DELIM + 
                String.format("%2d", cantMatAprob) + DELIM +
                String.format("%5.2f", promedio) + DELIM +
                (activo?"A":"B");
    }

}
