package com.rappi.crud.entidades;

import com.rappi.crud.dao.OfertaDAO;
import com.rappi.crud.servlets.Accion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Oferta {
    public static final String NOMBRE_ENTIDAD = "Oferta";
    
    // Columnas de la tabla en la BD.
    private int mIdOferta;
    private int mPorcentajeDescuento;
    private Date mFechaTermino;
    private int mIdProducto;
    private Producto mProducto;
  
    public Oferta(){
    }
    
    public Oferta(int idOferta, int porcentajeDescuento, Date fechaTermino, int idProducto)
    {
        mIdOferta = idOferta;
        mPorcentajeDescuento = porcentajeDescuento;
        mFechaTermino = fechaTermino;
        mIdProducto = idProducto;
    }
    
    /**
     * Crea un nuevo Oferta a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Oferta correspondiente.
     */
    public static Oferta desdeParametros(Map<String, String[]> parametros) throws ParseException
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

        // Crear nueva instancia de Oferta.
        final Oferta oferta = new Oferta();
        
        // Obtener los valores a partir de los campos. 
        String idOferta = null;
        
        if (campos.get(OfertaDAO.COLUMNA_ID) != null)
        {
            idOferta = campos.get(OfertaDAO.COLUMNA_ID);
            oferta. setIdOferta(Integer.parseInt(idOferta));
        }
    
        String porcentajeDescuento = campos.get(OfertaDAO.COLUMNA_PORCENTAJE_DESCUENTO);
        oferta.setPorcentajeDescuento(Integer.parseInt(porcentajeDescuento));
        System.out.println("porcentajeDescuento: " + porcentajeDescuento);
        
        String fechaTermino = campos.get(OfertaDAO.COLUMNA_FECHA_TERMINO);
        oferta.setFechaTermino(new SimpleDateFormat("dd/MM/yyyy").parse(fechaTermino));
        System.out.println("fechaTermino: " + fechaTermino);
        
        String idProducto = campos.get(OfertaDAO.COLUMNA_ID_PRODUCTO);
         if (idProducto != null)
        {
            oferta.setIdProducto(Integer.parseInt(idProducto));
        }
        System.out.println("idProducto: " + idProducto);
       
        // Retornar nueva instancia de la entidad.
        return oferta;
    }

       public static String tituloVistaConAccion(Accion accionVista)
    {
        switch (accionVista) 
        {
            case LEER: return "Detalles del " + NOMBRE_ENTIDAD;
            case CREAR: return "Agrega un Nuevo " +NOMBRE_ENTIDAD;
            case ACTUALIZAR: return "Edita el " + NOMBRE_ENTIDAD;
            case ELIMINAR: return "¿Deseas eliminar este " + NOMBRE_ENTIDAD + "?";
        }
        
        return "Vista de Detalles de " + NOMBRE_ENTIDAD;
    }
       
    @Override
    public String toString()
    {
        return "Oferta{" + "idOferta=" + mIdOferta + ", porcentajeDescuento=" + mPorcentajeDescuento+'}';
    }

    public int getPorcentajeDescuento() {
        return mPorcentajeDescuento;
    }

    public void setPorcentajeDescuento(int mPorcentajeDescuento) {
        this.mPorcentajeDescuento = mPorcentajeDescuento;
    }

    public Date getFechaTermino() {
        return mFechaTermino;
    }

    public void setFechaTermino(Date mFechaTermino) {
        this.mFechaTermino = mFechaTermino;
    }

    public int getIdProducto() {
        return mIdProducto;
    }

    public void setIdProducto(int mIdProducto) {
        this.mIdProducto = mIdProducto;
    }
    
    public int getIdOferta() {
        return mIdProducto;
    }

    public void setIdOferta(int mIdOferta) {
        this.mIdOferta = mIdOferta;
    }
    
    public Producto getProducto()
    {
        return mProducto;
    }

    public void setProducto(Producto producto)
    {
        this.mProducto = producto;
    }
}
