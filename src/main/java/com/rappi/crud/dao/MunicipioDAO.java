package com.rappi.crud.dao;

import com.rappi.crud.entidades.Municipio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class MunicipioDAO
{
    private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "municipio";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_ID_ESTADO = "id_estado";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public MunicipioDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Municipio> getMunicipios() throws SQLException 
    {
        List<Municipio> listaMunicipios = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Municipio municipio = new Municipio(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getString(COLUMNA_NOMBRE),
                    resultados.getInt(COLUMNA_ID_ESTADO)
                );
                
                listaMunicipios.add(municipio);
            }
        }

        return listaMunicipios;
    }
    
    /**
     * Busca un Municipio que tenga un id específico.
     * 
     * @param id El ID del Municipio que se quiere obtener.
     * @return El Municipio, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Municipio getMunicipioPorId(int id) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setInt(1, id);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Municipio municipio = new Municipio(
                resultados.getInt(COLUMNA_ID),
                resultados.getString(COLUMNA_NOMBRE),
                resultados.getInt(COLUMNA_ID_ESTADO)
            );

            return municipio;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de Municipio en la BD.
     * 
     * @param nuevoMunicipio Los valores para el nuevo Municipio.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertar(Municipio nuevoMunicipio) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryInsertar = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + COLUMNA_NOMBRE + ", " + COLUMNA_ID_ESTADO + ") VALUES(?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryInsertar, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, nuevoMunicipio.getNombre());
        stmt.setInt(2, nuevoMunicipio.getIdEstado());

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
     * Actualiza un Municipio existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro del Municipio.
     * @throws SQLException 
     */
    public void actualizar(Municipio modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + COLUMNA_NOMBRE + " = ?, " + COLUMNA_ID_ESTADO + " = ? WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);

        stmt.setString(1, modificaciones.getNombre());
        stmt.setInt(2, modificaciones.getIdEstado());
        stmt.setInt(3, modificaciones.getId());

        stmt.executeUpdate();
    }

    /**
     * Busca un Municipio que tenga un código específico y lo elimina.
     * 
     * @param id El ID del Municipio que va a eliminarse.
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
