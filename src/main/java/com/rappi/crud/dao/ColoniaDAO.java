package com.rappi.crud.dao;

import com.rappi.crud.entidades.Colonia;
import com.rappi.crud.entidades.Estado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ColoniaDAO
{
    private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "colonia";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_CODIGO_POSTAL = "codigo_postal";
    public static final String COLUMNA_ID_MUNICIPIO = "id_municipio";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public ColoniaDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Colonia> getColonias() throws SQLException 
    {
        List<Colonia> listaColonias = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Colonia colonia = new Colonia(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getString(COLUMNA_NOMBRE),
                    resultados.getInt(COLUMNA_CODIGO_POSTAL),
                    resultados.getInt(COLUMNA_ID_MUNICIPIO)
                );
                
                listaColonias.add(colonia);
            }
        }

        return listaColonias;
    }
    
    /**
     * Busca un Colonia que tenga un id específico.
     * 
     * @param id El ID de la Colonia que se quiere obtener.
     * @return La Colonia, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Colonia getColoniaPorId(int id) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setInt(1, id);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Colonia colonia = new Colonia(
                resultados.getInt(COLUMNA_ID),
                resultados.getString(COLUMNA_NOMBRE),
                resultados.getInt(COLUMNA_CODIGO_POSTAL),
                resultados.getInt(COLUMNA_ID_MUNICIPIO)
            );

            return colonia;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de Colonia en la BD.
     * 
     * @param nuevaColonia Los valores para la nueva Colonias.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertar(Colonia nuevaColonia) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryCrear = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + COLUMNA_NOMBRE + ", " + 
                COLUMNA_CODIGO_POSTAL + ", " + 
                COLUMNA_ID_MUNICIPIO 
                + ") VALUES(?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrear, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, nuevaColonia.getNombre());
        stmt.setInt(2, nuevaColonia.getCodigoPostal());
        stmt.setInt(3, nuevaColonia.getIdMunicipio());

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
     * Actualiza una Colonia existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro de la Colonia.
     * @throws SQLException 
     */
    public void actualizar(Colonia modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + 
                COLUMNA_NOMBRE + " = ?, " + 
                COLUMNA_CODIGO_POSTAL + " = ? " + 
                COLUMNA_ID_MUNICIPIO + " = ? " + 
                "WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);

        stmt.setString(1, modificaciones.getNombre());
        stmt.setInt(2, modificaciones.getCodigoPostal());
        stmt.setInt(3, modificaciones.getIdMunicipio());
        stmt.setInt(4, modificaciones.getId());

        stmt.executeUpdate();
    }

    /**
     * Busca una Colonia que tenga un código específico y lo elimina.
     * 
     * @param id El ID de la Colonia que va a eliminarse.
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
