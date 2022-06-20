package com.rappi.crud.dao;

import com.rappi.crud.entidades.Ingrediente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class IngredienteDAO {
    private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "ingrediente";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_NOMBRE = "nombre";
    
    public static final String COLUMNA_ESVEGANO = "esVegano";
    
    public static final String COLUMNA_ESALERGICO = "esAlergenico";
    
    public static final String COLUMNA_TIENEGLUTEN = "tieneGluten";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public IngredienteDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Ingrediente> getIngredientes() throws SQLException 
    {
        List<Ingrediente> listaIngredientes = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Ingrediente ingrediente = new Ingrediente(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getString(COLUMNA_NOMBRE),
                    resultados.getBoolean(COLUMNA_ESVEGANO),
                    resultados.getBoolean(COLUMNA_ESALERGICO),
                    resultados.getBoolean(COLUMNA_TIENEGLUTEN)    
                        
                );
                
                listaIngredientes.add(ingrediente);
            }
        }

        return listaIngredientes;
    }
    
    /**
     * Busca un país que tenga un código específico.
     * 
     * @param codigoIngrediente Un código de tres letras ISO 3166-1 alpha-3.
     * @return El país, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Ingrediente getIngredientePorId(int codigoIngrediente) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setInt(1, codigoIngrediente);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Ingrediente ingrediente = new Ingrediente(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getString(COLUMNA_NOMBRE),
                    resultados.getBoolean(COLUMNA_ESVEGANO),
                    resultados.getBoolean(COLUMNA_ESALERGICO),
                    resultados.getBoolean(COLUMNA_TIENEGLUTEN)    
                        
                );

            return ingrediente;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de país en la BD.
     * 
     * @param nuevoIngrediente Los valores para el nuevo país.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertarIngrediente(Ingrediente nuevoIngrediente) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryCrearIngrediente = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + COLUMNA_NOMBRE + ", " + COLUMNA_ESVEGANO + ", " + COLUMNA_ESALERGICO + ", " + COLUMNA_TIENEGLUTEN + ") VALUES(?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrearIngrediente, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, nuevoIngrediente.getNombre());
        stmt.setBoolean(2, nuevoIngrediente.getEsVegano());
        stmt.setBoolean(3, nuevoIngrediente.getEsAlergico());
        stmt.setBoolean(4, nuevoIngrediente.getTieneGluten());
        
        System.out.println(nuevoIngrediente);

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
    public void actualizar(Ingrediente modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + COLUMNA_NOMBRE + " = ?, " + COLUMNA_ESVEGANO + " = ?, " + COLUMNA_ESALERGICO + " = ?, " + COLUMNA_TIENEGLUTEN + " = ? WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);
       
        stmt.setString(1, modificaciones.getNombre());
        stmt.setBoolean(2, modificaciones.getEsVegano());
        stmt.setBoolean(3, modificaciones.getEsAlergico());
        stmt.setBoolean(4, modificaciones.getTieneGluten());
         stmt.setInt(5, modificaciones.getIdIngrediente());

        stmt.executeUpdate();
    }

    /**
     * Busca un país que tenga un código específico y lo elimina.
     * 
     * @param codigoIngrediente Un código de tres letras ISO 3166-1 alpha-3.
     * @throws SQLException 
     */
    public void eliminar(int idIngrediente) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryEliminar = "DELETE FROM " + NOMBRE_TABLA 
                                    + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryEliminar);

        stmt.setInt(1, idIngrediente);

        stmt.executeUpdate();
    }
}
