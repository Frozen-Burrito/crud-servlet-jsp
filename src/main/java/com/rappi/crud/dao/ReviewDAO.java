package com.rappi.crud.dao;

import com.rappi.crud.entidades.Restaurante;
import com.rappi.crud.entidades.Review;
import com.rappi.crud.entidades.TipoDeCocina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ReviewDAO
{
    private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "review";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_PUNTAJE = "puntaje";
    public static final String COLUMNA_CONTENIDO = "contenido";
    public static final String COLUMNA_FECHA = "fecha";
    public static final String COLUMNA_ID_AUTOR = "id_autor";
    public static final String COLUMNA_ID_RESTAURANTE = "id_restaurante";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public ReviewDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Review> getReviews() throws SQLException 
    {
        List<Review> lista = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Review review = new Review(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getInt(COLUMNA_PUNTAJE),
                    resultados.getString(COLUMNA_CONTENIDO),
                    resultados.getTimestamp(COLUMNA_FECHA),
                    resultados.getString(COLUMNA_ID_AUTOR),
                    resultados.getInt(COLUMNA_ID_RESTAURANTE)
                );
                
                lista.add(review);
            }
        }

        return lista;
    }
    
    /**
     * Busca una Reseña que tenga un ID específico.
     * 
     * @param id El ID de la Reseña que se quiere obtener.
     * @return La Reseña, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Review getReviewPorID(int id) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setInt(1, id);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Review review = new Review(
                resultados.getInt(COLUMNA_ID),
                resultados.getInt(COLUMNA_PUNTAJE),
                resultados.getString(COLUMNA_CONTENIDO),
                resultados.getTimestamp(COLUMNA_FECHA),
                resultados.getString(COLUMNA_ID_AUTOR),
                resultados.getInt(COLUMNA_ID_RESTAURANTE)
            );

            return review;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de Reseña en la BD.
     * 
     * @param nuevoReview Los valores para la nueva Reseña.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertar(Review nuevoReview) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryCrear = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + 
                    COLUMNA_PUNTAJE + ", " + 
                    COLUMNA_CONTENIDO + ", " + 
                    COLUMNA_FECHA + ", " + 
                    COLUMNA_ID_AUTOR + ", " + 
                    COLUMNA_ID_RESTAURANTE 
                + ") VALUES(?, ?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrear, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, nuevoReview.getPuntaje());
        stmt.setString(2, nuevoReview.getContenido());
        stmt.setTimestamp(3, nuevoReview.getFecha());
        stmt.setString(4, nuevoReview.getNombreUsuarioAutor());
        stmt.setInt(5, nuevoReview.getIdRestaurante());

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
     * Actualiza una Reseña existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro de la Reseña.
     * @throws SQLException 
     */
    public void actualizar(Review modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + 
                COLUMNA_PUNTAJE + " = ?, " + 
                COLUMNA_CONTENIDO + " = ?, " + 
                COLUMNA_FECHA + " = ?, " + 
                COLUMNA_ID_AUTOR + " = ?, " + 
                COLUMNA_ID_RESTAURANTE + " = ? " + 
                "WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);
        
        System.out.println("Modificaciones: " + modificaciones);

        stmt.setInt(1, modificaciones.getPuntaje());
        stmt.setString(2, modificaciones.getContenido());
        stmt.setTimestamp(3, modificaciones.getFecha());
        stmt.setString(4, modificaciones.getNombreUsuarioAutor());
        stmt.setInt(5, modificaciones.getIdRestaurante());
        stmt.setInt(6, modificaciones.getId());

        stmt.executeUpdate();
    }

    /**
     * Busca una Reseña que tenga un ID específico y la elimina.
     * 
     * @param id El ID de la Reseña que va a eliminarse.
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
