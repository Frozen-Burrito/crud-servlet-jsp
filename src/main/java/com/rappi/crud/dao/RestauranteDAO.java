package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Restaurante;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class RestauranteDAO
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_SITIO_WEB = "url_sitio_web";
    public static final String COLUMNA_NUM_TELEFONICO = "numero_telefonico";
    public static final String COLUMNA_TIPO_COCINA = "tipo_cocina";
    public static final String COLUMNA_ID_UBICACION = "id_ubicacion";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public RestauranteDAO()
    {
    }
    
    public List<Restaurante> getRestaurantes()
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Restaurante.findAll");
        
        List<Restaurante> resultados = query.getResultList();
        
        return resultados;
    }
    
    /**
     * Busca un Restaurante que tenga un username específico.
     * 
     * @param id El username de el Usuario que se quiere obtener.
     * @return El Usuario, o null si no existe en la BD.
     */
    public Restaurante getRestaurantePorID(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Restaurante> query = em.createNamedQuery("Restaurante.findById", Restaurante.class);
        query.setParameter("id", id);
        
        Restaurante restaurante = query.getResultStream().findFirst().orElse(null);
        
        em.close();

        return restaurante;
    }
    
    /**
     * Intenta insertar un nuevo registro de Restaurante en la BD.
     * 
     * @param nuevoRestaurante Los valores para el nuevo Usuario.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     */
    public int insertar(Restaurante nuevoRestaurante)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevoRestaurante);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza un Restaurante existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro del Restaurante.
     */
    public void actualizar(Restaurante modificaciones)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca un Restaurante que tenga un ID específico y la elimina.
     * 
     * @param id El ID del Restaurante que va a eliminarse.
     */
    public void eliminar(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Restaurante restaurante = getRestaurantePorID(id);
        
        restaurante = em.merge(restaurante);
        em.remove(restaurante);
        em.getTransaction().commit();
        em.close();
    }
}
