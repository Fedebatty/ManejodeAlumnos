package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persona.Alumno;
import persona.AlumnoException;
import persona.MiCalendario;
import persona.MiCalendarioException;
import persona.PersonaException;

public class AlumnoDAOSQL extends DAO<Alumno, Long> {

    private Connection conn;
    private PreparedStatement insertPS;
    private PreparedStatement selectPS;
    private PreparedStatement updatePS;
    private PreparedStatement deletePS;
    private PreparedStatement findAllPS;
    private PreparedStatement findAllActivoPS;
    private PreparedStatement closePS;

    // crear dao a sql
    /**
     * constructor conexion y prepareStatement
     *
     * @param url
     * @param usuario
     * @param password
     * @throws DAOException
     */
    public AlumnoDAOSQL(String url, String usuario, String password) throws DAOException {
        try {
            conn = DriverManager.getConnection(url, usuario, password);
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al conectarse con la BD ==> " + ex.getMessage());
        }

        ////////////  CREAR -  INSERT  ////////////////////////////////////////////////////////////////////////////////////////////////////
        String insertSQL = "INSERT INTO alumnos\n"
                + "(DNI, NOMBRE, APELLIDO, FEC_NAC, FEC_ING, CANT_MAT_APROB, PROMEDIO, SEXO,ACTIVO)\n"
                + "VALUES (?,?,?,?,?,?,?,?,?);";
        try {
            insertPS = conn.prepareStatement(insertSQL);
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al crear sentencia para INSERT - CREAR ==> " + ex.getMessage());
        }

        ////////////   READ / EXISTE - SELECT con DNI   ////////////////////////////////////////////////////////////////////////////////////
        String selectSQL = "SELECT * FROM alumnos WHERE DNI = ?";
        try {
            selectPS = conn.prepareStatement(selectSQL);
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al crear sentencia para READ ==> " + ex.getMessage());
        }

        ////////////  UPDATE con  DNI  ///////////////////////////////
        String updateSQL = "UPDATE alumnos \n"
                + "SET  NOMBRE= ?, \n"
                + "APELLIDO = ?, \n"
                + "FEC_NAC = ?, \n"
                + "FEC_ING = ?, \n"
                + "CANT_MAT_APROB = ?, \n"
                + "PROMEDIO = ?, \n"
                + "SEXO = ?, \n"
                + "ACTIVO = ? \n"
                + "WHERE dni = ?";
        try {
            updatePS = conn.prepareStatement(updateSQL);
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al crear sentencia para UPDATE ==> " + ex.getMessage());
        }

        ////////////  DELETE con  DNI  ///////////////////////////////
        String deleteSQL = "DELETE FROM alumnos WHERE DNI = ?";
        try {
            deletePS = conn.prepareStatement(deleteSQL);
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al crear sentencia para DELETE ==> " + ex.getMessage());
        }

        ////////////   findAll A y B - SELECT con DNI   ////////////////////////////////////////////////////////////////////////////////////
        //  activos True: solo Activos - False: solo Inactivos - otherwise (null): all (Activos + Inactivos)
        String findAllActivoSQL = "SELECT * FROM alumnos WHERE ACTIVO = ?";
        try {
            findAllActivoPS = conn.prepareStatement(findAllActivoSQL);
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al crear sentencia para findAllActivo ==> " + ex.getMessage());
        }

        ////////////   findAll TODO- SELECT con DNI   ////////////////////////////////////////////////////////////////////////////////////
        //  activos True: solo Activos - False: solo Inactivos - otherwise (null): all (Activos + Inactivos)
        String findAllSQL = "SELECT * FROM alumnos";
        try {
            findAllPS = conn.prepareStatement(findAllSQL);
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al crear sentencia para findAll ==> " + ex.getMessage());
        }
    }

    /**
     * Crea alumno en lña base de datos (INSERT)
     * @param alu
     * @throws DAOException
     */
    @Override
    public void create(Alumno alu) throws DAOException {
        try {
            if (existe(alu.getDni())) {
                throw new DAOException("El alumno ya existe");
            }
            int index = 1;
            insertPS.setLong(index++, alu.getDni());
            insertPS.setString(index++, alu.getNombre());
            insertPS.setString(index++, alu.getApellido());
            insertPS.setDate(index++, alu.getFechaNac().toSQLDate());
            insertPS.setDate(index++, alu.getFechaIng().toSQLDate());
            insertPS.setInt(index++, alu.getCantMatAprob());
            insertPS.setDouble(index++, alu.getPromedio());
            insertPS.setString(index++, String.valueOf(alu.getSexo()));
            //insertPS.setString(index++, "A");  // esta como char en la base de datos
            Boolean estado = alu.isActivo();
            //insertPS.setString(index++, String.valueOf(estado ? 'A' : 'B'));
            insertPS.setString(index++, estado ? "A" : "B");
            insertPS.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al insertar en le BD ==>" + ex.getMessage());
        }
    }

    /**
     * Lee los datos del alumno segun DNI - SELECT-WHERE
     * @param dni
     * @return
     * @throws DAOException
     */
    @Override
    public Alumno read(Long dni) throws DAOException {
        Alumno alu = null;
        try {
            selectPS.setLong(1, dni);
            ResultSet rs = selectPS.executeQuery();
            if (rs.next()) {
                alu = new Alumno();
                alu.setDni(dni);
                rsToAlu(alu, rs);
            }
        } catch (SQLException | PersonaException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al leer de la BD con DNI ==>" + ex.getMessage());
        }

        return alu;
    }

    /**
     *
     * @param alu
     * @param rs
     * @throws PersonaException
     * @throws SQLException
     * @throws AlumnoException
     */
    private void rsToAlu(Alumno alu, ResultSet rs) throws PersonaException, SQLException, AlumnoException {
        alu.setNombre(rs.getString("NOMBRE"));
        alu.setApellido(rs.getString("APELLIDO"));
        alu.setFechaNac(new MiCalendario(rs.getDate("FEC_NAC")));
        alu.setFechaIng(new MiCalendario(rs.getDate("FEC_ING")));
        alu.setCantMatAprob(rs.getInt("CANT_MAT_APROB"));
        alu.setPromedio(rs.getDouble("PROMEDIO"));
        alu.setSexo(rs.getString("SEXO").charAt(0));
        String estadoActivo = rs.getString("ACTIVO");
        alu.setActivo("A".equals(estadoActivo));
    }

    /**
     * Actualiza los datos del alumno  - UPDATE
     * @param alu
     * @throws DAOException
     */
    @Override
    public void update(Alumno alu) throws DAOException {
        try {
            if (!existe(alu.getDni())) {
                throw new DAOException("El alumno no existe");
            }
            int index = 1;
            updatePS.setString(index++, alu.getNombre());
            updatePS.setString(index++, alu.getApellido());
            updatePS.setDate(index++, alu.getFechaNac().toSQLDate());
            updatePS.setDate(index++, alu.getFechaIng().toSQLDate());
            updatePS.setInt(index++, alu.getCantMatAprob());
            updatePS.setDouble(index++, alu.getPromedio());
            updatePS.setString(index++, String.valueOf(alu.getSexo()));

            Boolean estado = alu.isActivo();
            updatePS.setString(index++, estado ? "A" : "B");

            updatePS.setLong(index++, alu.getDni());

            updatePS.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al updatear en le BD ==>" + ex.getMessage());
        }
    }

    /**
     * Borra al alumno  - Fisicamente en la DB  - DELETE
     * @param dni
     * @throws DAOException
     */
    @Override
    public void delete(Long dni) throws DAOException {
        try {
            Alumno alu = read(dni);
            if (alu == null) {
                throw new DAOException("El alumno no existe");
            }
            deletePS.setLong(1, dni);
            deletePS.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al deletear en le BD ==>" + ex.getMessage());
        }

    }

    /**
     * Verifica si el alumno existe en la BD
     * @param dni
     * @return True - False
     * @throws DAOException
     */
    @Override
    public boolean existe(Long dni) throws DAOException {
        try {
            return read(dni) != null;

        } catch (DAOException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al deletear en le BD ==>" + ex.getMessage());
        }
    }
 
    /**
     * pasa datos a lista de objetos de lo que se recibe de sql 
     * @param rs
     * @return
     * @throws SQLException
     * @throws persona.PersonaException
     * @throws persona.MiCalendarioException
     * @throws dao.DAOException
     */
    public List<Alumno> recibirVariosDatos(ResultSet rs) throws SQLException, PersonaException, MiCalendarioException, DAOException {
        List<Alumno> alumnos = new ArrayList<>();
        Alumno alu = null;
        try {
            while (rs.next()) { //To Do Crear metodo 
                alu = new Alumno();
                alu.setDni(rs.getLong("DNI"));
                rsToAlu(alu, rs);
                alumnos.add(alu);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error alxxxxxxxxxxxxx BD  ==>" + ex.getMessage());
        }

        return alumnos;
    }

    /**
     * Encuentra todos los alumnos ssegun parametro
     * @param activos True: solo Activos - False: solo Inactivos - otherwise (null): all (Activos + Inactivos)
     * @return
     * @throws DAOException
     */
    @Override
    public List<Alumno> findAll(Boolean activos) throws DAOException {
        List<Alumno> alumnos = new ArrayList<>();
        try {
            if (activos == null) {
                try (ResultSet rs = findAllPS.executeQuery()) {
                    alumnos = recibirVariosDatos(rs);
                } catch (SQLException ex) {
                    Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new DAOException("Error findAll Todos en BD ==>" + ex.getMessage());
                }
            }else {
                String activ = (!activos? "B": "A");
                findAllActivoPS.setString(1, activ);
                try (ResultSet rs = findAllActivoPS.executeQuery()) {
                    alumnos = recibirVariosDatos(rs);
                } catch (SQLException ex) {
                    Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new DAOException("Error findAll Inactivos/Activos en BD ==>" + ex.getMessage());
                }
            }
        } catch (SQLException | PersonaException | MiCalendarioException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error l encontrar alumnos en BD ==>" + ex.getMessage());
        }

        return alumnos;
    }

    /**
     * Cierra la conecion a la DB
     * @throws DAOException
     */
    @Override
    public void close() throws DAOException {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al cerrar la BD ==> " + ex.getMessage());
        }
    }

}
