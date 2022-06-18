package com.rappi.crud.entidades;

import com.rappi.crud.dao.PedidoDAO;
import com.rappi.crud.servlets.Accion;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Pedido
{   
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Pedido";
    
    // Columnas de la tabla en la BD.
    private int mId;
    
    private Timestamp mFecha;
    
    private String mNombreUsuarioCliente;
    private Usuario mUsuarioCliente;
    
    private int mIdRestaurante;
    private Restaurante mRestaurante;
    
    public Pedido()
    {
    }

    public Pedido(int id, Timestamp fecha, String nombreUsuarioCliente, int idRestaurante)
    {
        this.mId = id;
        this.mFecha = fecha;
        this.mNombreUsuarioCliente = nombreUsuarioCliente;
        this.mIdRestaurante = idRestaurante;
    }
    
    /**
     * Crea un nuevo Producto a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Producto correspondiente.
     */
    public static Pedido desdeParametros(Map<String, String[]> parametros)
    {
        Map<String, String> campos = new HashMap<>(); 
        
        // Obtener solo el primer valor para cada atributo (transformar un String[]
        // en solo un String.
        parametros.entrySet().forEach(entrada -> {
            System.out.println(entrada.getKey() + ": " + entrada.getValue()[0]);
            campos.put(
                    entrada.getKey(), 
                    entrada.getValue() != null ? entrada.getValue()[0] : null
            );
        });
        
        // Crear nueva instancia de Producto.
        final Pedido pedido = new Pedido();
        
        // Obtener los valores a partir de los campos.
        String idPedidoString = campos.get(PedidoDAO.COLUMNA_ID);
        
        if (idPedidoString != null)
        {
            pedido.setId(Integer.parseInt(idPedidoString));
        }
        
        // Las fechas necesitan cambios, porque el HTML las produce con una "T" 
        // entre la fecha y hora, además de que no incluyen segundos.
        String strFecha = campos.get(PedidoDAO.COLUMNA_FECHA);
        
        if (strFecha != null)
        {
            pedido.setFecha(Timestamp.valueOf(strFecha.replace("T", " ").concat(":00")));
        }
        
        String idClienteStr = campos.get(PedidoDAO.COLUMNA_ID_CLIENTE);
        pedido.setNombreUsuarioCliente(idClienteStr);
        
        String idRestauranteStr = campos.get(PedidoDAO.COLUMNA_ID_RESTAURANTE);
        
        if (idRestauranteStr != null)
        {
            int idRestaurante = Integer.parseInt(idRestauranteStr);
            pedido.setIdRestaurante(idRestaurante);
        }
    
        // Retornar nueva instancia de la entidad.
        return pedido;
    }
    
    /**
     * Determina el encabezado adecuado para la vista de detalles de la entidad,
     * según la acción de la vista.
     * 
     * @param accionVista La acción de la vista.
     * @return El texto para el encabezado de la vista.
     */
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
        return "Pedido{" + "mId=" + mId + ", mFecha=" + mFecha + ", mNombreUsuarioCliente=" + mNombreUsuarioCliente + ", mUsuarioCliente=" + mUsuarioCliente + ", mIdRestaurante=" + mIdRestaurante + ", mRestaurante=" + mRestaurante + '}';
    }

    public int getId()
    {
        return mId;
    }

    public void setId(int id)
    {
        this.mId = id;
    }

    public Timestamp getFecha()
    {
        return mFecha;
    }

    public void setFecha(Timestamp fecha)
    {
        this.mFecha = fecha;
    }

    public String getNombreUsuarioCliente()
    {
        return mNombreUsuarioCliente;
    }

    public void setNombreUsuarioCliente(String nombreUsuarioCliente)
    {
        this.mNombreUsuarioCliente = nombreUsuarioCliente;
    }

    public Usuario getUsuarioCliente()
    {
        return mUsuarioCliente;
    }

    public void setUsuarioCliente(Usuario usuarioCliente)
    {
        this.mUsuarioCliente = usuarioCliente;
    }

    public int getIdRestaurante()
    {
        return mIdRestaurante;
    }

    public void setIdRestaurante(int idRestaurante)
    {
        this.mIdRestaurante = idRestaurante;
    }

    public Restaurante getRestaurante()
    {
        return mRestaurante;
    }

    public void setmRestaurante(Restaurante restaurante)
    {
        this.mRestaurante = restaurante;
    }
}
