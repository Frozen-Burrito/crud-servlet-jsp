package com.rappi.crud.dao;

import com.rappi.crud.entidades.jpa.Usuario;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class UsuarioDAO
{
    public static final String NOMBRE_UNIDAD_PERSISTENCE = "CRUD_PU";
    
    private static final EntityManagerFactory mEMFactory = 
            Persistence.createEntityManagerFactory(NOMBRE_UNIDAD_PERSISTENCE);
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "nombre_usuario";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_PASSWORD = "password";
    public static final String COLUMNA_EMAIL = "email";
    public static final String COLUMNA_NUM_TELEFONICO = "numero_telefonico";
    public static final String COLUMNA_TIPO = "tipo";
    public static final String COLUMNA_ID_UBICACION = "id_ubicacion";
    
    public static final boolean ID_ES_AUTOMATICO = true;
    
    public List<Usuario> getUsuarios()
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        Query query = em.createNamedQuery("Usuario.findAll");
        
        List<Usuario> lista = query.getResultList();

        return lista;
    }
    
    /**
     * Busca un Usuario que tenga un username específico.
     * 
     * @param nombreUsuario El username de el Usuario que se quiere obtener.
     * @return El Usuario, o null si no existe en la BD.
     */
    public Usuario getUsuarioPorID(String nombreUsuario)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByNombreUsuario", Usuario.class);
        query.setParameter("nombreUsuario", nombreUsuario);
        
        Usuario usuario = query.getResultStream().findFirst().orElse(null);
        
        em.close();

        return usuario;
    }
    
    public boolean iniciarSesion(String nombreUsuario, String password)
    {
        System.out.println("Iniciando sesion");
        Usuario usuario = getUsuarioPorID(nombreUsuario);
        
        return usuario != null && usuario.getPassword().equals(password);
    }
    
    /**
     * Intenta insertar un nuevo registro de Usuario en la BD.
     * 
     * @param nuevoUsuario Los valores para el nuevo Usuario.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     */
    public int insertar(Usuario nuevoUsuario)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        try 
        {
            em.getTransaction().begin();
            em.persist(nuevoUsuario);
            em.getTransaction().commit();
            
            return 1;
        } catch (EntityExistsException | IllegalArgumentException e)
        {
            return -1;
        } finally 
        {
            em.close();
        }
    }

    /**
     * Actualiza una Ubicacion existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro de la Ubicacion.
     */
    public void actualizar(Usuario modificaciones)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(modificaciones);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Busca un Usuario que tenga un ID específico y la elimina.
     * 
     * @param nombreUsuario El ID del Usuario que va a eliminarse.
     */
    public void eliminar(String nombreUsuario)
    {
        EntityManager em = mEMFactory.createEntityManager();
        
        em.getTransaction().begin();
        
        Usuario usuario = getUsuarioPorID(nombreUsuario);
        
        usuario = em.merge(usuario);
        em.remove(usuario);
        em.getTransaction().commit();
        em.close();
    }
}
