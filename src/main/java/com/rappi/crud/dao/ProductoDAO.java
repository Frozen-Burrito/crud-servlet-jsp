package com.rappi.crud.dao;

import com.rappi.crud.entidades.Producto;
import com.rappi.crud.entidades.TipoDeProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ProductoDAO {
     private final DataSource mDataSource;
    
    public static final String NOMBRE_TABLA = "producto";
    
    // Nombre de la columna con la llave primaria.
    public static final String COLUMNA_ID = "id";
    
    // Nombres de las demás columnas.
    public static final String COLUMNA_ID_RESTAURANTE = "idrestaurante";
    
    public static final String COLUMNA_NOMBRE = "nombre";
    
    public static final String COLUMNA_DISPONIBLE = "disponible";
    
    public static final String COLUMNA_KCAL_PORCION = "kcalPorcion";
    
    public static final String COLUMNA_TAMAÑO_PORCION = "tamañoPorcion";
    
    public static final String COLUMNA_DESCRIPCION = "descripcion";
    
    public static final String COLUMNA_PRECIO = "precio";
    
    public static final String COLUMNA_CODIGO_MONEDA = "codigoMoneda";
    
    public static final String COLUMNA_TIPO_DE_PRODUCTO = "tipoDeProducto";
    
    public static final boolean ID_ES_AUTOMATICO = true;
        
    public ProductoDAO(DataSource dataSource)
    {
        this.mDataSource = dataSource;
    }
    
    public List<Producto> getProductos() throws SQLException 
    {
        List<Producto> listaProductos = new ArrayList<>();
        
        Connection conexion = mDataSource.getConnection();
        
        if (conexion != null)
        {
            final String query = "SELECT * FROM " + NOMBRE_TABLA;

            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {   
                final Producto producto = new Producto(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getInt(COLUMNA_ID_RESTAURANTE),
                        resultados.getString(COLUMNA_NOMBRE),
                        resultados.getBoolean(COLUMNA_DISPONIBLE),
                        resultados.getInt(COLUMNA_KCAL_PORCION),
                        resultados.getInt(COLUMNA_TAMAÑO_PORCION),
                        resultados.getString(COLUMNA_DESCRIPCION),
                        resultados.getDouble(COLUMNA_PRECIO),
                        resultados.getString(COLUMNA_CODIGO_MONEDA),
                        TipoDeProducto.valueOf(resultados.getString(COLUMNA_TIPO_DE_PRODUCTO))
                    
                );
                
                listaProductos.add(producto);
            }
        }

        return listaProductos;
    }
    
    /**
     * Busca un país que tenga un código específico.
     * 
     * @param codigoProducto Un código de tres letras ISO 3166-1 alpha-3.
     * @return El país, o null si no existe en la BD.
     * @throws SQLException 
     */
    public Producto getProductoPorId(int codigoProducto) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryPorCodigo = "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryPorCodigo);
        stmt.setInt(1, codigoProducto);

        ResultSet resultados = stmt.executeQuery();

        if (resultados.next())
        {
            final Producto producto = new Producto(
                    resultados.getInt(COLUMNA_ID),
                    resultados.getInt(COLUMNA_ID_RESTAURANTE),
                        resultados.getString(COLUMNA_NOMBRE),
                        resultados.getBoolean(COLUMNA_DISPONIBLE),
                        resultados.getInt(COLUMNA_KCAL_PORCION),
                        resultados.getInt(COLUMNA_TAMAÑO_PORCION),
                        resultados.getString(COLUMNA_DESCRIPCION),
                        resultados.getDouble(COLUMNA_PRECIO),
                        resultados.getString(COLUMNA_CODIGO_MONEDA),
                        TipoDeProducto.valueOf(resultados.getString(COLUMNA_TIPO_DE_PRODUCTO))  
                );

            return producto;
        }

        return null;
    }
    
    /**
     * Intenta insertar un nuevo registro de país en la BD.
     * 
     * @param nuevoProducto Los valores para el nuevo país.
     * @return El número de filas agregadas (0 es fallo, 1 es éxito).
     * @throws SQLException 
     */
    public int insertarProducto(Producto nuevoProducto) throws SQLException
    {
        Connection connection = mDataSource.getConnection();
        
        final String queryCrearProducto = "INSERT INTO " + NOMBRE_TABLA 
                + " (" + COLUMNA_ID_RESTAURANTE + ", " + COLUMNA_NOMBRE + ", " + COLUMNA_DISPONIBLE 
                + ", " + COLUMNA_KCAL_PORCION + ", " + COLUMNA_TAMAÑO_PORCION +", " + COLUMNA_DESCRIPCION 
                +", " + COLUMNA_PRECIO + ", " + COLUMNA_CODIGO_MONEDA +", " + COLUMNA_TIPO_DE_PRODUCTO 
                +") VALUES(?, ?, ?,? ,? ,? ,? ,? ,?)";

        PreparedStatement stmt = connection.prepareStatement(queryCrearProducto, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, nuevoProducto.getIdRestaurante());
        stmt.setString(2, nuevoProducto.getNombre());
        stmt.setBoolean(3, nuevoProducto.isDisponible());
        stmt.setInt(4, nuevoProducto.getKcalPorcion());
        stmt.setInt(5, nuevoProducto.getTamañoPorcion());
        stmt.setString(6, nuevoProducto.getDescripcion());
        stmt.setDouble(7, nuevoProducto.getPrecio());
        stmt.setString(8, nuevoProducto.getCodigoMoneda());
        stmt.setString(9, nuevoProducto.getTipoDeProducto().toString());
        
        System.out.println(nuevoProducto);

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
    public void actualizar(Producto modificaciones) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryActualizar = "UPDATE " + NOMBRE_TABLA + 
                " SET " + COLUMNA_ID_RESTAURANTE + " = ?, " + COLUMNA_NOMBRE + " = ?, " + COLUMNA_DISPONIBLE + " = ?," 
                + COLUMNA_KCAL_PORCION + " = ?,  " + COLUMNA_TAMAÑO_PORCION + " = ?,  " + COLUMNA_DESCRIPCION + " = ?, "
                + COLUMNA_PRECIO + " = ?, " + COLUMNA_CODIGO_MONEDA + " = ?," + COLUMNA_TIPO_DE_PRODUCTO + " = ? WHERE " + COLUMNA_ID + " = ?";

        PreparedStatement stmt = conexion.prepareStatement(queryActualizar);
       
        stmt.setInt(1, modificaciones.getIdRestaurante());
        stmt.setString(2, modificaciones.getNombre());
        stmt.setBoolean(3, modificaciones.isDisponible());
        stmt.setInt(4, modificaciones.getKcalPorcion());
        stmt.setInt(5, modificaciones.getTamañoPorcion());
        stmt.setString(6, modificaciones.getDescripcion());
        stmt.setDouble(7, modificaciones.getPrecio());
        stmt.setString(8, modificaciones.getCodigoMoneda());
        stmt.setString(9, modificaciones.getTipoDeProducto().toString());
        stmt.setInt(1, modificaciones.getIdProducto());
        
        stmt.executeUpdate();
    }

    /**
     * Busca un país que tenga un código específico y lo elimina.
     * 
     * @param codigoProducto Un código de tres letras ISO 3166-1 alpha-3.
     * @throws SQLException 
     */
    public void eliminar(int idProducto) throws SQLException
    {
        Connection conexion = mDataSource.getConnection();
        
        final String queryEliminar = "DELETE FROM " + NOMBRE_TABLA 
                                    + " WHERE " + COLUMNA_ID + " = ?";
        
        PreparedStatement stmt = conexion.prepareStatement(queryEliminar);

        stmt.setInt(1, idProducto);

        stmt.executeUpdate();
    }
}
