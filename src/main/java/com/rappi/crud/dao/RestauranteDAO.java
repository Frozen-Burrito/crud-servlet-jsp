package com.rappi.crud.dao;

import com.rappi.crud.entidades.Restaurante;
import com.rappi.crud.entidades.TipoDeCocina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class RestauranteDAO
{
    private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "restaurante";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_SITIO_WEB = "url_sitio_web";
    public static final String COLUMNA_NUM_TELEFONICO = "numero_telefonico";
    public static final String COLUMNA_TIPO_COCINA = "tipo_cocina";
    public static final String COLUMNA_ID_UBICACION = "id_ubicacion";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public RestauranteDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Restaurante> getRestaurantes() throws SQLException 
    {
        List<Restaurante> lista = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Restaurante restaurante = new Restaurante(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getString(COLUMNA_NOMBRE),
                    resultados.getString(COLUMNA_SITIO_WEB),
                    resultados.getString(COLUMNA_NUM_TELEFONICO),
                    TipoDeCocina.valueOf(resultados.getString(COLUMNA_TIPO_COCINA).toUpperCase()),
                    resultados.getInt(COLUMNA_ID_UBICACION)
                );
                
                lista.add(restaurante);
            }
        }

        return lista;
    }
    
    /**
     * Busca un Restaurante que tenga un username específico.
     * 
     * @param id El username de el Usuario que se quiere obtener.
     * @return El Usuario, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Restaurante getRestaurantePorID(int id) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setInt(1, id);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Restaurante restaurante = new Restaurante(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getString(COLUMNA_NOMBRE),
                    resultados.getString(COLUMNA_SITIO_WEB),
                    resultados.getString(COLUMNA_NUM_TELEFONICO),
                    TipoDeCocina.valueOf(resultados.getString(COLUMNA_TIPO_COCINA).toUpperCase()),
                    resultados.getInt(COLUMNA_ID_UBICACION)
                );

            return restaurante;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de Restaurante en la BD.
     * 
     * @param nuevoRestaurante Los valores para el nuevo Usuario.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertar(Restaurante nuevoRestaurante) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        System.out.println("Nuevo restaurante: " + nuevoRestaurante);
        
        final String queryCrear = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + 
                    COLUMNA_NOMBRE + ", " + 
                    COLUMNA_SITIO_WEB + ", " + 
                    COLUMNA_NUM_TELEFONICO + ", " + 
                    COLUMNA_TIPO_COCINA + ", " + 
                    COLUMNA_ID_UBICACION 
                + ") VALUES(?, ?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrear, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, nuevoRestaurante.getNombre());
        stmt.setString(2, nuevoRestaurante.getSitioWeb());
        stmt.setString(3, nuevoRestaurante.getNumTelefonico());
        stmt.setString(4, nuevoRestaurante.getTipoDeCocina().toString());
        stmt.setInt(5, nuevoRestaurante.getIdUbicacion());

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
    public void actualizar(Restaurante modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + 
                COLUMNA_NOMBRE + " = ?, " + 
                COLUMNA_SITIO_WEB + " = ?, " + 
                COLUMNA_NUM_TELEFONICO + " = ?, " + 
                COLUMNA_TIPO_COCINA + " = ?, " + 
                COLUMNA_ID_UBICACION + " = ? " + 
                "WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);
        
        System.out.println("Modificaciones: " + modificaciones);

        stmt.setString(1, modificaciones.getNombre());
        stmt.setString(2, modificaciones.getSitioWeb());
        stmt.setString(3, modificaciones.getNumTelefonico());
        stmt.setString(4, modificaciones.getTipoDeCocina().toString());
        stmt.setInt(5, modificaciones.getIdUbicacion());
        stmt.setInt(6, modificaciones.getId());

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
