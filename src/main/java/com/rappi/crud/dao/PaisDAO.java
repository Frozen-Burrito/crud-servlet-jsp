package com.rappi.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import com.rappi.crud.entidades.Pais;

public class PaisDAO
{
    private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "pais";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "codigo_iso_3166";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    
    public static final boolean ID_ES_AUTOMATICO = false;
        
    public PaisDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Pais> getPaises() throws SQLException 
    {
        List<Pais> listaPaises = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Pais pais = new Pais(
                    resultados.getString(COLUMNA_ID),
                    resultados.getString(COLUMNA_NOMBRE)
                );
                
                listaPaises.add(pais);
            }
        }

        return listaPaises;
    }
    
    /**
     * Busca un país que tenga un código específico.
     * 
     * @param codigoPais Un código de tres letras ISO 3166-1 alpha-3.
     * @return El país, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Pais getPaisPorId(String codigoPais) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setString(1, codigoPais);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Pais pais = new Pais(
                resultados.getString(COLUMNA_ID),
                resultados.getString(COLUMNA_NOMBRE)
            );

            return pais;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de país en la BD.
     * 
     * @param nuevoPais Los valores para el nuevo país.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertarPais(Pais nuevoPais) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryCrearPais = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + COLUMNA_ID + ", " + COLUMNA_NOMBRE + ") VALUES(?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrearPais, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, nuevoPais.getCodigoPais());
        stmt.setString(2, nuevoPais.getNombre());

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
     * Actualiza un País existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro del País.
     * @throws SQLException 
     */
    public void actualizar(Pais modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + COLUMNA_ID + " = ?, " + COLUMNA_NOMBRE + " = ? WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);

        stmt.setString(1, modificaciones.getCodigoPais());
        stmt.setString(2, modificaciones.getNombre());
        stmt.setString(3, modificaciones.getCodigoPais());

        stmt.executeUpdate();
    }

    /**
     * Busca un país que tenga un código específico y lo elimina.
     * 
     * @param codigoPais Un código de tres letras ISO 3166-1 alpha-3.
     * @throws SQLException 
     */
    public void eliminar(String codigoPais) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryEliminar = "DELETE FROM " + NOMBRE_TABLA 
                                    + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryEliminar);

        stmt.setString(1, codigoPais);

        stmt.executeUpdate();
    }
}
