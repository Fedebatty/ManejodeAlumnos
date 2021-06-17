package dao;

import java.util.List;

/**
 * @param <T> clave
 * @param <K> valor
 */
public abstract class DAO<T,K> {
    // CRUD
    public abstract void create(T entidad) throws DAOException;
    public abstract T read(K clave) throws DAOException;
    public abstract void update(T entidad) throws DAOException;
    public abstract void delete(K clave) throws DAOException;    
    public abstract boolean existe(K clave) throws DAOException;
    public abstract List<T> findAll(Boolean activos) throws DAOException;
    public abstract void close() throws DAOException;
}

