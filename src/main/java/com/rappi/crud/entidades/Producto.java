package com.rappi.crud.entidades;

import com.rappi.crud.dao.ProductoDAO;
import java.util.HashMap;
import java.util.Map;

public class Producto {
     // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "producto";
    
    // Columnas de la tabla en la BD.
    private final int mIdProducto;
    private int mIdRestaurante;
    private String mNombre;
    private boolean mDisponible;
    private int mKcalPorcion;
    private int mTamañoPorcion;
    private String mDescripcion;
    private double mPrecio;
    private String mCodigoMoneda;
    private TipoDeProducto mTipoDeProducto;

    public Producto(int idProducto, int idRestaurante, String nombre, boolean disponible, int kcalPorcion, int tamañoPorcion, 
            String descripcion, double precio, String codigoMoneda, TipoDeProducto tipoDeProducto)
    {
       mIdProducto = idProducto;
       mIdRestaurante = idRestaurante;
       mNombre = nombre;
       mDisponible = disponible;
       mKcalPorcion = kcalPorcion;
       mTamañoPorcion = tamañoPorcion;
       mDescripcion = descripcion;
       mPrecio = precio;
       mCodigoMoneda = codigoMoneda;
       mTipoDeProducto = tipoDeProducto;
    }
    
    /**
     * Crea un nuevo Producto a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Producto correspondiente.
     */
    public static Producto desdeParametros(Map<String, String[]> parametros)
    {
        Map<String, String> campos = new HashMap<>(); 
        
        // Obtener solo el primer valor para cada atributo (transformar un String[]
        // en solo un String.
        parametros.entrySet().forEach(entrada -> {
            campos.put(
                    entrada.getKey(), 
                    entrada.getValue() != null ? entrada.getValue()[0] : null
            );
        });
        
        String idProducto = null;
        int intIdProducto = 0;
        
        if (campos.get(ProductoDAO.COLUMNA_ID) != null)
        {
            idProducto = campos.get(ProductoDAO.COLUMNA_ID);
            intIdProducto = Integer.parseInt(idProducto);
        }
    
        String idRestaurante = campos.get(ProductoDAO.COLUMNA_ID_RESTAURANTE);
        System.out.println("idRestaurante: " + idRestaurante);
        
        String nombre = campos.get(ProductoDAO.COLUMNA_NOMBRE);
        System.out.println("nombre: " + nombre);
        
        String disponible = campos.get(ProductoDAO.COLUMNA_DISPONIBLE);
        System.out.println("disponible: " + disponible);
        
        String kcalPorcion = campos.get(ProductoDAO.COLUMNA_KCAL_PORCION);
        System.out.println("kcalPorcion: " + kcalPorcion);
        
        String tamañoPorcion = campos.get(ProductoDAO.COLUMNA_TAMAÑO_PORCION);
        System.out.println("tamañoPorcion: " + tamañoPorcion);
        
        String descripcion = campos.get(ProductoDAO.COLUMNA_DESCRIPCION);
        System.out.println("descripcion: " + descripcion);
        
        String precio = campos.get(ProductoDAO.COLUMNA_PRECIO);
        System.out.println("precio: " + precio);
        
        String codigoMoneda = campos.get(ProductoDAO.COLUMNA_CODIGO_MONEDA);
        System.out.println("codigoMoneda: " + codigoMoneda);
        
        String tipoDeProducto = campos.get(ProductoDAO.COLUMNA_TIPO_DE_PRODUCTO);
        System.out.println("tipoDeProducto: " + tipoDeProducto);
        
        
       
        // Retornar nueva instancia de la entidad.
        return new Producto(intIdProducto, Integer.parseInt(idRestaurante),nombre,Boolean.parseBoolean(disponible),
                Integer.parseInt(kcalPorcion),Integer.parseInt(tamañoPorcion),descripcion,Double.parseDouble(precio), codigoMoneda,TipoDeProducto.valueOf(tipoDeProducto));
    }

    @Override
    public String toString()
    {
        return "Producto{" + "idProducto=" + mIdProducto + ", idRestaurante=" + mIdRestaurante + ", nombre=" + mNombre + 
                ", disponibleo=" + mDisponible + ", kcalPorcion=" + mKcalPorcion + 
                ", tamañoPorcion=" + mTamañoPorcion +", descripcion=" + mDescripcion +
                ", precio=" + mPrecio +", codigoMoneda=" + mCodigoMoneda +", tipoDeProducto=" + mTipoDeProducto +'}';
    }

    public int getIdProducto() {
        return mIdProducto;
    }

    public int getIdRestaurante() {
        return mIdRestaurante;
    }

    public void setIdRestaurante(int mIdRestaurante) {
        this.mIdRestaurante = mIdRestaurante;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public boolean isDisponible() {
        return mDisponible;
    }

    public void setDisponible(boolean mDisponible) {
        this.mDisponible = mDisponible;
    }

    public int getKcalPorcion() {
        return mKcalPorcion;
    }

    public void setKcalPorcion(int mKcalPorcion) {
        this.mKcalPorcion = mKcalPorcion;
    }

    public int getTamañoPorcion() {
        return mTamañoPorcion;
    }

    public void setTamañoPorcion(int mTamañoPorcion) {
        this.mTamañoPorcion = mTamañoPorcion;
    }

    public String getDescripcion() {
        return mDescripcion;
    }

    public void setDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public double getPrecio() {
        return mPrecio;
    }

    public void setPrecio(double mPrecio) {
        this.mPrecio = mPrecio;
    }

    public String getCodigoMoneda() {
        return mCodigoMoneda;
    }

    public void setCodigoMoneda(String mCodigoMoneda) {
        this.mCodigoMoneda = mCodigoMoneda;
    }

    public TipoDeProducto getTipoDeProducto() {
        return mTipoDeProducto;
    }

    public void setTipoDeProducto(TipoDeProducto mTipoDeProducto) {
        this.mTipoDeProducto = mTipoDeProducto;
    }
    
}
