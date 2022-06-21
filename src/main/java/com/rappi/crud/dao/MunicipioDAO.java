package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Municipio;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class MunicipioDAO
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_ID_ESTADO = "id_estado";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public MunicipioDAO()
    {
    }
    
    public List<Municipio> getMunicipios() throws SQLException 
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Municipio.findAll");
        
        List<Municipio> resultados = query.getResultList();
        
        return resultados;
    }
    
    /**
     * Busca un Municipio que tenga un id específico.
     * 
     * @param id El ID del Municipio que se quiere obtener.
     * @return El Municipio, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Municipio getMunicipioPorId(int id) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Municipio> query = em.createNamedQuery("Municipio.findById", Municipio.class);
        query.setParameter("id", id);
        
        Municipio colonia = query.getResultStream().findFirst().orElse(null);
        
        em.close();

        return colonia;
    }
    
    /**
     * Intenta insertar un nuevo registro de Municipio en la BD.
     * 
     * @param nuevoMunicipio Los valores para el nuevo Municipio.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertar(Municipio nuevoMunicipio) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevoMunicipio);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza un Municipio existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro del Municipio.
     * @throws SQLException 
     */
    public void actualizar(Municipio modificaciones) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca un Municipio que tenga un código específico y lo elimina.
     * 
     * @param id El ID del Municipio que va a eliminarse.
     * @throws SQLException 
     */
    public void eliminar(int id) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Municipio municipio = getMunicipioPorId(id);
        
        municipio = em.merge(municipio);
        em.remove(municipio);
        em.getTransaction().commit();
        em.close();
    }
}
