package com.rappi.crud.entidades;

import com.rappi.crud.dao.RestauranteDAO;
import com.rappi.crud.dao.UsuarioDAO;
import com.rappi.crud.servlets.Accion;
import com.rappi.crud.servlets.TipoDeUsuario;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Restaurante
{   
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Restaurante";
    
    // Columnas de la tabla en la BD.
    private int mId;
    
    private String mNombre;
    private String mSitioWeb;
    private String mNumTelefonico;
    private TipoDeCocina mTipoDeCocina;
    
    private int mIdUbicacion;
    private Ubicacion mUbicacion;
    
    public Restaurante()
    {
    }

    public Restaurante(int id, String nombre, String sitioWeb, String numTelefonico, TipoDeCocina tipoDeCocina, int idUbicacion)
    {
        this.mId = id;
        this.mNombre = nombre;
        this.mSitioWeb = sitioWeb;
        this.mNumTelefonico = numTelefonico;
        this.mTipoDeCocina = tipoDeCocina;
        this.mIdUbicacion = idUbicacion;
    }
    
    /**
     * Crea un nuevo Restaurante a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Restaurante correspondiente.
     */
    public static Restaurante desdeParametros(Map<String, String[]> parametros)
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
        
        // Crear nueva instancia de Usuario.
        final Restaurante restaurante = new Restaurante();
        
        String idRestauranteStr = campos.get(RestauranteDAO.COLUMNA_ID);
        
        if (idRestauranteStr != null)
        {
            restaurante.setId(Integer.parseInt(idRestauranteStr));
        }
        
        String nombre = campos.get(RestauranteDAO.COLUMNA_NOMBRE);
        restaurante.setNombre(nombre);
        
        // Obtener los valores a partir de los campos.
        String email = campos.get(RestauranteDAO.COLUMNA_SITIO_WEB);
        restaurante.setSitioWeb(email);
        
        TipoDeCocina tipoDeCocina = TipoDeCocina.valueOf(campos.get(RestauranteDAO.COLUMNA_TIPO_COCINA));
        restaurante.setTipoDeCocina(tipoDeCocina);
        
        String numTelefono = campos.get(RestauranteDAO.COLUMNA_NUM_TELEFONICO);
        restaurante.setNumTelefonico(numTelefono);
        
        String idUbicacionStr = campos.get(RestauranteDAO.COLUMNA_ID_UBICACION);
        
        if (idUbicacionStr != null)
        {
            int idUbicacion = Integer.parseInt(idUbicacionStr);
            restaurante.setIdUbicacion(idUbicacion);
        }
    
        // Retornar nueva instancia de la entidad.
        return restaurante;
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
        return "Restaurante{" + "mId=" + mId + ", mNombre=" + mNombre + ", mSitioWeb=" + mSitioWeb + ", mNumTelefonico=" + mNumTelefonico + ", mTipoDeCocina=" + mTipoDeCocina + ", mIdUbicacion=" + mIdUbicacion + ", mUbicacion=" + mUbicacion + '}';
    }

    public int getId()
    {
        return mId;
    }

    public void setId(int id)
    {
        this.mId = id;
    }

    public String getNombre()
    {
        return mNombre;
    }

    public void setNombre(String nombre)
    {
        this.mNombre = nombre;
    }

    public String getSitioWeb()
    {
        return mSitioWeb;
    }

    public void setSitioWeb(String sitioWeb)
    {
        this.mSitioWeb = sitioWeb;
    }

    public String getNumTelefonico()
    {
        return mNumTelefonico;
    }

    public void setNumTelefonico(String numTelefonico)
    {
        this.mNumTelefonico = numTelefonico;
    }

    public TipoDeCocina getTipoDeCocina()
    {
        return mTipoDeCocina;
    }

    public void setTipoDeCocina(TipoDeCocina tipoDeCocina)
    {
        this.mTipoDeCocina = tipoDeCocina;
    }

    public int getIdUbicacion()
    {
        return mIdUbicacion;
    }

    public void setIdUbicacion(int idUbicacion)
    {
        this.mIdUbicacion = idUbicacion;
    }

    public Ubicacion getUbicacion()
    {
        return mUbicacion;
    }

    public void setUbicacion(Ubicacion ubicacion)
    {
        this.mUbicacion = ubicacion;
    }

        
}
