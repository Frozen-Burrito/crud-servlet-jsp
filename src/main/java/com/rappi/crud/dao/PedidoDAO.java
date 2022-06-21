package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Pedido;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class PedidoDAO
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_FECHA = "fecha";
    public static final String COLUMNA_ID_CLIENTE = "id_cliente";
    public static final String COLUMNA_ID_RESTAURANTE = "id_restaurante";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public PedidoDAO()
    {
    }
    
    public List<Pedido> getPedidos() throws SQLException 
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Pedido.findAll");
        
        List<Pedido> resultados = query.getResultList();
        
        return resultados;
    }
    
    /**
     * Busca un Pedido que tenga un ID específico.
     * 
     * @param id El ID del Pedido que se quiere obtener.
     * @return El Pedido, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Pedido getPedidoPorId(int id) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Pedido> query = em.createNamedQuery("Pedido.findById", Pedido.class);
        query.setParameter("id", id);
        
        Pedido pedido = query.getSingleResult();
        
        em.close();

        return pedido;
    }
    
    /**
     * Intenta insertar un nuevo registro de Pedido en la BD.
     * 
     * @param nuevoPedido Los valores para el nuevo Pedido.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertar(Pedido nuevoPedido) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(nuevoPedido);
        em.getTransaction().commit();
        em.close();
        
        return -1;
    }

    /**
     * Actualiza un Pedido existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro de la Reseña.
     * @throws SQLException 
     */
    public void actualizar(Pedido modificaciones) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca un Pedido que tenga un ID específico y la elimina.
     * 
     * @param id El ID de la Pedido que va a eliminarse.
     * @throws SQLException 
     */
    public void eliminar(int id) throws SQLException
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Pedido pedido = getPedidoPorId(id);
        
        pedido = em.merge(pedido);
        em.remove(pedido);
        em.getTransaction().commit();
        em.close();
    }
}
