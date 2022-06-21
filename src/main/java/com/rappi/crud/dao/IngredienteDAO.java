package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Ingrediente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class IngredienteDAO 
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);

    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";

    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";

    public static final String COLUMNA_ESVEGANO = "esVegano";

    public static final String COLUMNA_ESALERGICO = "esAlergenico";

    public static final String COLUMNA_TIENEGLUTEN = "tieneGluten";

    public static final boolean ID_ES_AUTOMATICO = true;

    public IngredienteDAO()
    {
    }

    public List<Ingrediente> getIngredientes()
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Ingrediente.findAll");
        
        List<Ingrediente> resultados = query.getResultList();
        
        return resultados;
    }

    /**
     * Busca un ingrediente que tenga un ID específico.
     * 
     * @param id El ID del ingrediente.
     * @return El ingrediente, o null si no existe en la BD.
     */
    public Ingrediente getIngredientePorId(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Ingrediente> query = em.createNamedQuery("Ingrediente.findById", Ingrediente.class);
        query.setParameter("id", id);
        
        Ingrediente ingrediente = query.getResultStream().findFirst().orElse(null);
        
        em.close();

        return ingrediente;
    }

    /**
     * Intenta insertar un nuevo registro de país en la BD.
     * 
     * @param nuevoIngrediente Los valores para el nuevo país.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     */
    public int insertarIngrediente(Ingrediente nuevoIngrediente)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevoIngrediente);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza un Ingrediente existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro del Ingrediente.
     */
    public void actualizar(Ingrediente modificaciones)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca un Ingrediente que tenga un código específico y lo elimina.
     * 
     * @param id El ID del Ingrediente.
     */
    public void eliminar(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Ingrediente ingrediente = getIngredientePorId(id);
        
        ingrediente = em.merge(ingrediente);
        em.remove(ingrediente);
        em.getTransaction().commit();
        em.close();
    }
}