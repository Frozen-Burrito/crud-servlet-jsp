package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Colonia;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class ColoniaDAO
{       
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_CODIGO_POSTAL = "codigo_postal";
    public static final String COLUMNA_ID_MUNICIPIO = "id_municipio";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public ColoniaDAO()
    {
    }
    
    public List<Colonia> getColonias()
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Colonia.findAll");
        
        List<Colonia> resultados = query.getResultList();
        
        return resultados;
    }
    
    /**
     * Busca un Colonia que tenga un id específico.
     * 
     * @param id El ID de la Colonia que se quiere obtener.
     * @return La Colonia, o null si no existe en la BD.
     */
    public Colonia getColoniaPorId(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Colonia> query = em.createNamedQuery("Colonia.findById", Colonia.class);
        query.setParameter("id", id);
        
        Colonia colonia = query.getResultStream().findFirst().orElse(null);
        
        em.close();

        return colonia;
    }
    
    /**
     * Intenta insertar un nuevo registro de Colonia en la BD.
     * 
     * @param nuevaColonia Los valores para la nueva Colonias.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     */
    public int insertar(Colonia nuevaColonia)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevaColonia);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza una Colonia existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro de la Colonia.
     */
    public void actualizar(Colonia modificaciones)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca una Colonia que tenga un código específico y lo elimina.
     * 
     * @param id El ID de la Colonia que va a eliminarse.
     */
    public void eliminar(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Colonia colonia = getColoniaPorId(id);
        
        colonia = em.merge(colonia);
        em.remove(colonia);
        em.getTransaction().commit();
        em.close();
    }
}
