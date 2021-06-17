package dao;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOFactory {
    
    public static final String TIPO_DAO = "TIPO_DAO";
    
    public static final String TIPO_DAO_TXT = "TXT";
    public static final String FILE_NAME = "FILE_NAME";
    
    public static final String TIPO_DAO_SQL = "SQL";
    public static final String URL_DB = "URL_DB";
    public static final String USUARIO_DB = "USUARIO_DB";
    public static final String PASS_DB = "PASS_DB";
    
    private AlumnoDAOTXT daoTXT;
    private AlumnoDAOSQL daoSQL;
        
    private static DAOFactory instance;
    
    private DAOFactory() {
    }
    
    public static DAOFactory getIntance() {
        if (instance==null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public DAO createDAO(Map<String, String> config) throws DAOFactoryException {
        try {
            String tipoDAO = config.get(TIPO_DAO);
            switch (tipoDAO) {
                case TIPO_DAO_TXT:
                    String filename = config.get(FILE_NAME);
                    return daoTXT==null?new AlumnoDAOTXT(filename):daoTXT;
                case TIPO_DAO_SQL:
                    String url = config.get(URL_DB);
                    String usuario = config.get(USUARIO_DB);
                    String pass = config.get(PASS_DB);
                    return daoSQL==null?new AlumnoDAOSQL(url, usuario, pass):daoSQL;
                default:
                    throw new DAOFactoryException("Tipo de DAO NO implementado");
            }
        } catch (DAOException ex) {
            Logger.getLogger(DAOFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOFactoryException("Error al crear el DAO");
        }
    }
}
