/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rappi.crud.dao;

import com.rappi.crud.entidades.Oferta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author laura_epj4q4k
 */
public class OfertaDAO {
     private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "oferta";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_PORCENTAJE_DESCUENTO = "porcentaje_descuento";
    public static final String COLUMNA_FECHA_TERMINO = "fecha_termino";
    public static final String COLUMNA_ID_PRODUCTO = "id_producto";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public OfertaDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Oferta> getOfertas() throws SQLException 
    {
        List<Oferta> lista = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Oferta oferta = new Oferta(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getInt(COLUMNA_PORCENTAJE_DESCUENTO),
                    resultados.getDate(COLUMNA_FECHA_TERMINO),
                    resultados.getInt(COLUMNA_ID_PRODUCTO)
                );
                
                lista.add(oferta);
            }
        }

        return lista;
    }
    
    /**
     * Busca un Oferta que tenga un username específico.
     * 
     * @param nombreOferta El username de el Oferta que se quiere obtener.
     * @return El Oferta, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Oferta getOfertaPorID(String nombreOferta) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setString(1, nombreOferta);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Oferta oferta = new Oferta(
                resultados.getInt(COLUMNA_ID),
                resultados.getInt(COLUMNA_PORCENTAJE_DESCUENTO),
                resultados.getDate(COLUMNA_FECHA_TERMINO),
                resultados.getInt(COLUMNA_ID_PRODUCTO)
            );

            return oferta;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de Oferta en la BD.
     * 
     * @param nuevoOferta Los valores para el nuevo Oferta.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertar(Oferta nuevoOferta) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryCrear = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + 
                    COLUMNA_PORCENTAJE_DESCUENTO + ", " + 
                    COLUMNA_FECHA_TERMINO + ", " + 
                    COLUMNA_ID_PRODUCTO + ") VALUES(?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrear, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, nuevoOferta.getPorcentajeDescuento());
        stmt.setDate(2, new java.sql.Date(((Date) nuevoOferta.getFechaTermino()).getTime()));
        stmt.setInt(3, nuevoOferta.getIdProducto());
        
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
    public void actualizar(Oferta modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + 
                COLUMNA_PORCENTAJE_DESCUENTO + " = ?, " + 
                COLUMNA_FECHA_TERMINO + " = ?, " + 
                COLUMNA_ID_PRODUCTO+ " = ?, " + 
                "WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);
        
        System.out.println("Modificaciones: " + modificaciones);

        stmt.setInt(1, modificaciones.getPorcentajeDescuento());
        stmt.setDate(2, new java.sql.Date(((Date) modificaciones.getFechaTermino()).getTime()));
        stmt.setInt(3, modificaciones.getIdProducto());
        stmt.setInt(4, modificaciones.getIdOferta());

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
