package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Ubicacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class UbicacionDAO
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_CALLE = "calle";
    public static final String COLUMNA_NUM_EXTERIOR = "numero_exterior";
    public static final String COLUMNA_NUM_INTERIOR = "numero_interior";
    public static final String COLUMNA_ID_COLONIA = "id_colonia";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public UbicacionDAO()
    {
    }
    
    public List<Ubicacion> getUbicaciones()
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Ubicacion.findAll");
        
        List<Ubicacion> resultados = query.getResultList();
        
        return resultados;
    }
    
    /**
     * Busca un Ubicacion que tenga un id específico.
     * 
     * @param id El ID de la Ubicacion que se quiere obtener.
     * @return La Ubicacion, o null si no existe en la BD.
     */
    public Ubicacion getUbicacionPorId(int id)
    {        
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Ubicacion> query = em.createNamedQuery("Ubicacion.findById", Ubicacion.class);
        query.setParameter("id", id);
        
        Ubicacion ubicacion = query.getResultStream().findFirst().orElse(null);
        
        em.close();

        return ubicacion;
    }
    
    /**
     * Intenta insertar un nuevo registro de Ubicacion en la BD.
     * 
     * @param nuevaUbicacion Los valores para la nueva Ubicacion.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     */
    public int insertar(Ubicacion nuevaUbicacion)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevaUbicacion);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza una Ubicacion existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro de la Ubicacion.
     */
    public void actualizar(Ubicacion modificaciones)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca una Ubicacion que tenga un ID específico y la elimina.
     * 
     * @param id El ID de la Ubicacion que va a eliminarse.
     */
    public void eliminar(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Ubicacion ubicacion = getUbicacionPorId(id);
        
        ubicacion = em.merge(ubicacion);
        em.remove(ubicacion);
        em.getTransaction().commit();
        em.close();
    }
}
