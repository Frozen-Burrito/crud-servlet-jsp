package com.rappi.crud.dao;

import com.rappi.crud.entidades.Datos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class PruebaDAO
{
    private final DataSource mDataSource;
    
    private static final String NOMBRE_TABLA = "datos";
    
    private static final String QUERY_TODOS = "SELECT * FROM " + NOMBRE_TABLA;
    private static final String QUERY_POR_ID = "SELECT * FROM " + NOMBRE_TABLA + " WHERE id = ?";
    private static final String QUERY_CREAR = "INSERT INTO " + NOMBRE_TABLA + " (titulo, fecha, descripcion, status) VALUES(?, ?, ?, ?)";
    private static final String QUERY_ACTUALIZAR = "UPDATE " + NOMBRE_TABLA + " SET titulo = ?, fecha = ?, descripcion = ? , status = ? WHERE id = ?";
    private static final String QUERY_ELIMINAR = "DELETE FROM " + NOMBRE_TABLA + " WHERE id = ?";
    
    public PruebaDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    private Connection getConexion() 
    {
        Connection conexion = null;
        
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            
        } catch (SQLException | ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
        
        return conexion;
    }
    
    public List<Datos> getDatos() throws SQLException 
    {
        List<Datos> filasDatos = new ArrayList<>();
        
        System.out.println("Obteniendo conexion: " + mDataSource.toString());
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            System.out.println("Conexion: " + conexion.getClientInfo());

            PreparedStatement stmt = conexion.prepareStatement(QUERY_TODOS);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {
                Datos datos = new Datos(
                    resultados.getInt("id"),
                    resultados.getString("titulo"),
                    resultados.getTimestamp("fecha"),
                    resultados.getString("descripcion"),
                    resultados.getInt("status"),
                    resultados.getTimestamp("fechaCreacion")
                );
                
                filasDatos.add(datos);
            }
        }

        return filasDatos;
    }
    
    public Datos getDatosPorId(int idDatos) throws SQLException
    {
        Connection conexion = getConexion();

        PreparedStatement stmt = conexion.prepareStatement(QUERY_POR_ID);
        stmt.setInt(1, idDatos);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            Datos datos = new Datos(
                resultados.getInt("id"),
                resultados.getString("titulo"),
                resultados.getTimestamp("fecha"),
                resultados.getString("descripcion"),
                resultados.getInt("status"),
                resultados.getTimestamp("fechaCreacion")
            );

            return datos;
        }

        return null;
    }
    
    public int insertarDatos(Datos nuevosDatos) throws SQLException
    {
        Connection connection = mDataSource.getConnection();

        PreparedStatement stmt = connection.prepareStatement(QUERY_CREAR, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, nuevosDatos.getTitulo());
        stmt.setTimestamp(2, nuevosDatos.getFecha());
        stmt.setString(3, nuevosDatos.getDescripcion());
        stmt.setInt(4, nuevosDatos.getStatus());

        stmt.executeUpdate();

        ResultSet resultados = stmt.getGeneratedKeys();

        if (resultados.next())
        {
            return resultados.getInt(1);
        }

        return -1;
    }

    public void actualizar(Datos cambiosADatos) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();

        PreparedStatement stmt = conexion.prepareStatement(QUERY_ACTUALIZAR);

        stmt.setString(1, cambiosADatos.getTitulo());
        stmt.setTimestamp(2, cambiosADatos.getFecha());
        stmt.setString(3, cambiosADatos.getDescripcion());
        stmt.setInt(4, cambiosADatos.getStatus());

        stmt.executeUpdate();
    }

    public void eliminar(int idDatos) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        PreparedStatement stmt = conexion.prepareStatement(QUERY_ELIMINAR);

        stmt.setInt(1, idDatos);

        stmt.executeUpdate();
    }
}
