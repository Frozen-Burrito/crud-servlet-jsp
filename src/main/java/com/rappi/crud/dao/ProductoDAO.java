package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class ProductoDAO 
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);

    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";

    // Nombres de las demás columnas.
    public static final String COLUMNA_ID_RESTAURANTE = "idrestaurante";

    public static final String COLUMNA_NOMBRE = "nombre";

    public static final String COLUMNA_DISPONIBLE = "disponible";

    public static final String COLUMNA_KCAL_PORCION = "kcalPorcion";

    public static final String COLUMNA_TAMANIO_PORCION = "tamañoPorcion";

    public static final String COLUMNA_DESCRIPCION = "descripcion";

    public static final String COLUMNA_PRECIO = "precio";

    public static final String COLUMNA_CODIGO_MONEDA = "codigoMoneda";

    public static final String COLUMNA_TIPO_DE_PRODUCTO = "tipoDeProducto";

    public static final boolean ID_ES_AUTOMATICO = true;

    public ProductoDAO()
    {
    }

    public List<Producto> getProductos()
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Producto.findAll");
        
        List<Producto> resultados = query.getResultList();
        
        return resultados;
    }

    /**
     * Busca un Producto que tenga un ID específico.
     * 
     * @param id El ID del Producto.
     * @return El Producto, o null si no existe en la BD.
     */
    public Producto getProductoPorId(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Producto> query = em.createNamedQuery("Producto.findById", Producto.class);
        query.setParameter("id", id);
        
        Producto ingrediente = query.getResultStream().findFirst().orElse(null);
        
        em.close();

        return ingrediente;
    }

    /**
     * Intenta insertar un nuevo registro de Producto en la BD.
     * 
     * @param nuevoProducto Los valores para el nuevo Producto.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     */
    public int insertarProducto(Producto nuevoProducto)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevoProducto);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza un Producto existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro del Producto.
     */
    public void actualizar(Producto modificaciones)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca un Producto que tenga un código específico y lo elimina.
     * 
     * @param id El ID del Producto.
     */
    public void eliminar(int id)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Producto producto = getProductoPorId(id);
        
        producto = em.merge(producto);
        em.remove(producto);
        em.getTransaction().commit();
        em.close();
    }
}