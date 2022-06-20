package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Review;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class ReviewDAO
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_PUNTAJE = "puntaje";
    public static final String COLUMNA_CONTENIDO = "contenido";
    public static final String COLUMNA_FECHA = "fecha";
    public static final String COLUMNA_ID_AUTOR = "id_autor";
    public static final String COLUMNA_ID_RESTAURANTE = "id_restaurante";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public ReviewDAO()
    {
    }
    
    public List<Review> getReviews() 
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Review.findAll");
        
        List<Review> resultados = query.getResultList();
        
        return resultados;
    }
    
    /**
     * Busca una Reseña que tenga un ID específico.
     * 
     * @param id El ID de la Reseña que se quiere obtener.
     * @return La Reseña, o null si no existe en la BD.
     */
    public Review getReviewPorID(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Review> query = em.createNamedQuery("Review.findById", Review.class);
        query.setParameter("id", id);
        
        Review review = query.getResultStream().findFirst().orElse(null);
        
        em.close();

        return review;
    }
    
    /**
     * Intenta insertar un nuevo registro de Reseña en la BD.
     * 
     * @param nuevoReview Los valores para la nueva Reseña.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     */
    public int insertar(Review nuevoReview)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevoReview);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza una Reseña existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro de la Reseña.
     */
    public void actualizar(Review modificaciones) 
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca una Reseña que tenga un ID específico y la elimina.
     * 
     * @param id El ID de la Reseña que va a eliminarse.
     */
    public void eliminar(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Review review = getReviewPorID(id);
        
        review = em.merge(review);
        em.remove(review);
        em.getTransaction().commit();
        em.close();
    }
}
