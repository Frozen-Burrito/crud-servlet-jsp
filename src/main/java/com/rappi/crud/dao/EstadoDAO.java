package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Estado;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class EstadoDAO
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_CODIGO_PAIS = "codigo_pais";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public EstadoDAO()
    {
    }
    
    public List<Estado> getEstados() throws SQLException 
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Estado.findAll");
        
        List<Estado> resultados = query.getResultList();
        
        return resultados;
    }
    
    /**
     * Busca un estado que tenga un id específico.
     * 
     * @param id El ID del estado que se quiere obtener.
     * @return El estado, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Estado getEstadoPorId(int id) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Estado> query = em.createNamedQuery("Estado.findById", Estado.class);
        query.setParameter("id", id);
        
        Estado estado = query.getResultStream().findFirst().orElse(null);
        
        em.close();

        return estado;
    }
    
    /**
     * Intenta insertar un nuevo registro de estado en la BD.
     * 
     * @param nuevoEstado Los valores para el nuevo estado.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertarEstado(Estado nuevoEstado) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevoEstado);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza un Estado existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro del Estado.
     * @throws SQLException 
     */
    public void actualizar(Estado modificaciones) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca un Estado que tenga un código específico y lo elimina.
     * 
     * @param id El ID del Estado que va a eliminarse.
     * @throws SQLException 
     */
    public void eliminar(int id) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Estado estado = getEstadoPorId(id);
        
        estado = em.merge(estado);
        em.remove(estado);
        em.getTransaction().commit();
        em.close();
    }
}
