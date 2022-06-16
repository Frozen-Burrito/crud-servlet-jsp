package com.rappi.crud.dao;

import com.rappi.crud.entidades.Ubicacion;
import com.rappi.crud.entidades.Usuario;
import com.rappi.crud.servlets.TipoDeUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class UsuarioDAO
{
    private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "usuario";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "nombre_usuario";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_PASSWORD = "password";
    public static final String COLUMNA_EMAIL = "email";
    public static final String COLUMNA_NUM_TELEFONICO = "numero_telefonico";
    public static final String COLUMNA_TIPO = "tipo";
    public static final String COLUMNA_ID_UBICACION = "id_ubicacion";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public UsuarioDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Usuario> getUsuarios() throws SQLException 
    {
        List<Usuario> lista = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Usuario usuario = new Usuario(
                    resultados.getString(COLUMNA_ID),
                    resultados.getString(COLUMNA_EMAIL),
                    TipoDeUsuario.valueOf(resultados.getString(COLUMNA_TIPO).toUpperCase()),
                    resultados.getString(COLUMNA_PASSWORD),
                    resultados.getString(COLUMNA_NUM_TELEFONICO),
                    resultados.getInt(COLUMNA_ID_UBICACION)
                );
                
                lista.add(usuario);
            }
        }

        return lista;
    }
    
    /**
     * Busca un Usuario que tenga un username específico.
     * 
     * @param nombreUsuario El username de el Usuario que se quiere obtener.
     * @return El Usuario, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Usuario getUsuarioPorID(String nombreUsuario) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setString(1, nombreUsuario);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Usuario usuario = new Usuario(
                resultados.getString(COLUMNA_ID),
                resultados.getString(COLUMNA_EMAIL),
                TipoDeUsuario.valueOf(resultados.getString(COLUMNA_TIPO).toUpperCase()),
                resultados.getString(COLUMNA_PASSWORD),
                resultados.getString(COLUMNA_NUM_TELEFONICO),
                resultados.getInt(COLUMNA_ID_UBICACION)
            );

            return usuario;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de Usuario en la BD.
     * 
     * @param nuevoUsuario Los valores para el nuevo Usuario.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertar(Usuario nuevoUsuario) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryCrear = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + 
                    COLUMNA_ID + ", " + 
                    COLUMNA_EMAIL + ", " + 
                    COLUMNA_TIPO + ", " + 
                    COLUMNA_PASSWORD + ", " + 
                    COLUMNA_NUM_TELEFONICO + ", " + 
                    COLUMNA_ID_UBICACION 
                + ") VALUES(?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrear, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, nuevoUsuario.getNombreUsuario());
        stmt.setString(2, nuevoUsuario.getEmail());
        stmt.setString(3, nuevoUsuario.getTipoDeUsuario().toString());
        stmt.setString(4, nuevoUsuario.getPassword());
        stmt.setString(5, nuevoUsuario.getNumTelefono());
        stmt.setInt(6, nuevoUsuario.getIdUbicacion());

        stmt.executeUpdate();

        ResultSet resultados = stmt.getGeneratedKeys();

        if (resultados.next())
        {
            System.out.println("Insertado");
            return resultados.getInt(1);
        }

        return -1;
    }

    /**
     * Actualiza una Ubicacion existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro de la Ubicacion.
     * @throws SQLException 
     */
    public void actualizar(Usuario modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + 
                COLUMNA_ID + " = ?, " + 
                COLUMNA_EMAIL + " = ?, " + 
                COLUMNA_TIPO + " = ?, " + 
                COLUMNA_PASSWORD + " = ?, " + 
                COLUMNA_NUM_TELEFONICO + " = ?, " + 
                COLUMNA_ID_UBICACION + " = ? " +
                "WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);
        
        System.out.println("Modificaciones: " + modificaciones);

        stmt.setString(1, modificaciones.getNombreUsuario());
        stmt.setString(2, modificaciones.getEmail());
        stmt.setString(3, modificaciones.getTipoDeUsuario().toString());
        stmt.setString(4, modificaciones.getPassword());
        stmt.setString(5, modificaciones.getNumTelefono());
        stmt.setInt(6, modificaciones.getIdUbicacion());
        stmt.setString(7, modificaciones.getNombreUsuario());

        stmt.executeUpdate();
    }

    /**
     * Busca una Ubicacion que tenga un ID específico y la elimina.
     * 
     * @param id El ID de la Ubicacion que va a eliminarse.
     * @throws SQLException 
     */
    public void eliminar(int id) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryEliminar = "DELETE FROM " + NOMBRE_TABLA 
                                    + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryEliminar);

        stmt.setInt(1, id);

        stmt.executeUpdate();
    }
}
