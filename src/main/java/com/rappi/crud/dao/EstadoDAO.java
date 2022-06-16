package com.rappi.crud.dao;

import com.rappi.crud.entidades.Estado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class EstadoDAO
{
    private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "estado";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_CODIGO_PAIS = "codigo_pais";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public EstadoDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Estado> getEstados() throws SQLException 
    {
        List<Estado> listaEstados = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Estado estado = new Estado(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getString(COLUMNA_NOMBRE),
                    resultados.getString(COLUMNA_CODIGO_PAIS)
                );
                
                listaEstados.add(estado);
            }
        }

        return listaEstados;
    }
    
    /**
     * Busca un estado que tenga un id específico.
     * 
     * @param id El ID del estado que se quiere obtener.
     * @return El estado, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Estado getEstadoPorId(int id) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setInt(1, id);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Estado estado = new Estado(
                resultados.getInt(COLUMNA_ID),
                resultados.getString(COLUMNA_NOMBRE),
                resultados.getString(COLUMNA_CODIGO_PAIS)
            );

            return estado;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de estado en la BD.
     * 
     * @param nuevoEstado Los valores para el nuevo estado.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertarEstado(Estado nuevoEstado) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryCrearPais = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + COLUMNA_NOMBRE + ", " + COLUMNA_CODIGO_PAIS + ") VALUES(?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrearPais, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, nuevoEstado.getNombre());
        stmt.setString(2, nuevoEstado.getCodigoPais());

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
     * Actualiza un Estado existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro del Estado.
     * @throws SQLException 
     */
    public void actualizar(Estado modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + COLUMNA_NOMBRE + " = ?, " + COLUMNA_CODIGO_PAIS + " = ? WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);

        stmt.setString(1, modificaciones.getNombre());
        stmt.setString(2, modificaciones.getCodigoPais());
        stmt.setInt(3, modificaciones.getId());

        stmt.executeUpdate();
    }

    /**
     * Busca un Estado que tenga un código específico y lo elimina.
     * 
     * @param id El ID del Estado que va a eliminarse.
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
