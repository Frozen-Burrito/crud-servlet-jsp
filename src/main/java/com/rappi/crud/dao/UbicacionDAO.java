package com.rappi.crud.dao;

import com.rappi.crud.entidades.Ubicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class UbicacionDAO
{
    private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "ubicacion";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_CALLE = "calle";
    public static final String COLUMNA_NUM_EXTERIOR = "numero_exterior";
    public static final String COLUMNA_NUM_INTERIOR = "numero_interior";
    public static final String COLUMNA_ID_COLONIA = "id_colonia";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public UbicacionDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Ubicacion> getUbicaciones() throws SQLException 
    {
        List<Ubicacion> lista = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Ubicacion ubicacion = new Ubicacion(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getString(COLUMNA_CALLE),
                    resultados.getInt(COLUMNA_NUM_EXTERIOR),
                    resultados.getInt(COLUMNA_NUM_INTERIOR),
                    resultados.getInt(COLUMNA_ID_COLONIA)
                );
                
                lista.add(ubicacion);
            }
        }

        return lista;
    }
    
    /**
     * Busca un Ubicacion que tenga un id específico.
     * 
     * @param id El ID de la Ubicacion que se quiere obtener.
     * @return La Ubicacion, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Ubicacion getColoniaPorId(int id) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setInt(1, id);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Ubicacion ubicacion = new Ubicacion(
                resultados.getInt(COLUMNA_ID),
                resultados.getString(COLUMNA_CALLE),
                resultados.getInt(COLUMNA_NUM_EXTERIOR),
                resultados.getInt(COLUMNA_NUM_INTERIOR),
                resultados.getInt(COLUMNA_ID_COLONIA)
            );

            return ubicacion;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de Ubicacion en la BD.
     * 
     * @param nuevaUbicacion Los valores para la nueva Ubicacion.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertar(Ubicacion nuevaUbicacion) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryCrear = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + COLUMNA_CALLE + ", " + 
                COLUMNA_NUM_EXTERIOR + ", " + 
                COLUMNA_NUM_INTERIOR + ", " + 
                COLUMNA_ID_COLONIA 
                + ") VALUES(?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrear, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, nuevaUbicacion.getNombreCalle());
        stmt.setInt(2, nuevaUbicacion.getNumExterior());
        stmt.setInt(3, nuevaUbicacion.getNumInterior());
        stmt.setInt(4, nuevaUbicacion.getIdColonia());

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
    public void actualizar(Ubicacion modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + 
                COLUMNA_CALLE + " = ?, " + 
                COLUMNA_NUM_EXTERIOR + " = ? " + 
                COLUMNA_NUM_INTERIOR + " = ? " + 
                COLUMNA_ID_COLONIA + " = ? " + 
                "WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);

        stmt.setString(1, modificaciones.getNombreCalle());
        stmt.setInt(2, modificaciones.getNumExterior());
        stmt.setInt(3, modificaciones.getNumInterior());
        stmt.setInt(4, modificaciones.getIdColonia());
        stmt.setInt(5, modificaciones.getId());

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
