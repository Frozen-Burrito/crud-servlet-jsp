package com.rappi.crud.dao;

import com.rappi.crud.entidades.Pedido;
import com.rappi.crud.entidades.Review;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class PedidoDAO
{
    private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "pedido";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_FECHA = "fecha";
    public static final String COLUMNA_ID_CLIENTE = "id_cliente";
    public static final String COLUMNA_ID_RESTAURANTE = "id_restaurante";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public PedidoDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Pedido> getPedidos() throws SQLException 
    {
        List<Pedido> lista = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Pedido review = new Pedido(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getTimestamp(COLUMNA_FECHA),
                    resultados.getString(COLUMNA_ID_CLIENTE),
                    resultados.getInt(COLUMNA_ID_RESTAURANTE)
                );
                
                lista.add(review);
            }
        }

        return lista;
    }
    
    /**
     * Busca un Pedido que tenga un ID específico.
     * 
     * @param id El ID del Pedido que se quiere obtener.
     * @return El Pedido, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Pedido getPedidoPorId(int id) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setInt(1, id);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Pedido pedido = new Pedido(
                resultados.getInt(COLUMNA_ID),
                resultados.getTimestamp(COLUMNA_FECHA),
                resultados.getString(COLUMNA_ID_CLIENTE),
                resultados.getInt(COLUMNA_ID_RESTAURANTE)
            );

            return pedido;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de Pedido en la BD.
     * 
     * @param nuevoPedido Los valores para el nuevo Pedido.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertar(Pedido nuevoPedido) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryCrear = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + 
                    COLUMNA_FECHA + ", " + 
                    COLUMNA_ID_CLIENTE + ", " + 
                    COLUMNA_ID_RESTAURANTE 
                + ") VALUES(?, ?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrear, Statement.RETURN_GENERATED_KEYS);

        stmt.setTimestamp(1, nuevoPedido.getFecha());
        stmt.setString(2, nuevoPedido.getNombreUsuarioCliente());
        stmt.setInt(3, nuevoPedido.getIdRestaurante());

        int filasModificadas = stmt.executeUpdate();
        
        return filasModificadas;
    }

    /**
     * Actualiza un Pedido existente con nuevos valores.
     * 
     * @param modificaciones Los nuevos valores para el registro de la Reseña.
     * @throws SQLException 
     */
    public void actualizar(Pedido modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + 
                COLUMNA_FECHA + " = ?, " + 
                COLUMNA_ID_CLIENTE + " = ?, " + 
                COLUMNA_ID_RESTAURANTE + " = ? " + 
                "WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);
        
        stmt.setTimestamp(1, modificaciones.getFecha());
        stmt.setString(2, modificaciones.getNombreUsuarioCliente());
        stmt.setInt(3, modificaciones.getIdRestaurante());
        stmt.setInt(4, modificaciones.getId());

        stmt.executeUpdate();
    }

    /**
     * Busca un Pedido que tenga un ID específico y la elimina.
     * 
     * @param id El ID de la Pedido que va a eliminarse.
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
