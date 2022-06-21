package com.rappi.crud.dao;

import java.sql.SQLException;
import java.util.List;
import com.rappi.crud.entidades.jpa.Pais;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class PaisDAO
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "codigo_iso_3166";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    
    public static final boolean ID_ES_AUTOMATICO = false;
    
    public PaisDAO()
    {
    }
    
    public List<Pais> getPaises() throws SQLException 
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Pais.findAll", Pais.class);
        
        List<Pais> listaPaises = query.getResultList();

        return listaPaises;
    }
    
    /**
     * Busca un país que tenga un código específico.
     * 
     * @param codigoPais Un código de tres letras ISO 3166-1 alpha-3.
     * @return El país, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Pais getPaisPorId(String codigoPais) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Pais> query = em.createNamedQuery("Pais.findByCodigoIso3166", Pais.class);
        query.setParameter("codigoIso3166", codigoPais);
        
        Pais pais = query.getSingleResult();
        
        em.close();
            
        return pais;
    }
    
    /**
     * Intenta insertar un nuevo registro de país en la BD.
     * 
     * @param nuevoPais Los valores para el nuevo país.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertarPais(Pais nuevoPais) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevoPais);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza un País existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro del País.
     * @throws SQLException 
     */
    public void actualizar(Pais modificaciones) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca un país que tenga un código específico y lo elimina.
     * 
     * @param codigoPais Un código de tres letras ISO 3166-1 alpha-3.
     * @throws SQLException 
     */
    public void eliminar(String codigoPais) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Pais pais = getPaisPorId(codigoPais);
        
        pais = em.merge(pais);
        em.remove(pais);
        em.getTransaction().commit();
        em.close();
    }
}
