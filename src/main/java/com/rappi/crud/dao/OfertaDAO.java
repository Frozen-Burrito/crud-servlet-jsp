package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Oferta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class OfertaDAO 
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);

    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";

    // Nombres de las demás columnas.
    public static final String COLUMNA_PORCENTAJE_DESCUENTO = "porcentaje_descuento";
    public static final String COLUMNA_FECHA_TERMINO = "fecha_termino";
    public static final String COLUMNA_ID_PRODUCTO = "id_producto";

    public static final boolean ID_ES_AUTOMATICO = true;

    public OfertaDAO()
    {
    }

    public List<Oferta> getOfertas()
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Oferta.findAll");
        
        List<Oferta> resultados = query.getResultList();
        
        return resultados;
    }

    /**
     * Busca una Oferta que tenga un ID específico.
     * 
     * @param id El ID de la Oferta.
     * @return La Oferta, o null si no existe en la BD.
     */
    public Oferta getOfertaPorId(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Oferta> query = em.createNamedQuery("Oferta.findById", Oferta.class);
        query.setParameter("id", id);
        
        Oferta oferta = query.getResultStream().findFirst().orElse(null);
        
        em.close();

        return oferta;
    }

    /**
     * Intenta insertar un nuevo registro de país en la BD.
     * 
     * @param nuevaOferta Los valores para el nuevo país.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     */
    public int insertar(Oferta nuevaOferta)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevaOferta);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza una Oferta existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro de la Oferta.
     */
    public void actualizar(Oferta modificaciones)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca una Oferta que tenga un código específico y lo elimina.
     * 
     * @param id El ID de la Oferta.
     */
    public void eliminar(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Oferta oferta = getOfertaPorId(id);
        
        oferta = em.merge(oferta);
        em.remove(oferta);
        em.getTransaction().commit();
        em.close();
    }
}