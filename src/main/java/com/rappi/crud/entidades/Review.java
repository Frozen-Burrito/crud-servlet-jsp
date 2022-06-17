package com.rappi.crud.entidades;

import com.rappi.crud.dao.RestauranteDAO;
import com.rappi.crud.dao.ReviewDAO;
import com.rappi.crud.servlets.Accion;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Review
{   
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Reseña";
    
    // Columnas de la tabla en la BD.
    private int mId;
    
    private int mPuntaje;
    private String mContenido;
    private Timestamp mFecha;
    
    private String mNombreUsuarioAutor;
    private Usuario mUsuarioAutor;
    
    private int mIdRestaurante;
    private Restaurante mRestaurante;
    
    public Review()
    {
    }

    public Review(int id, int puntaje, String contenido, Timestamp fecha, String nombreUsuarioAutor, int idRestaurante)
    {
        this.mId = id;
        this.mPuntaje = puntaje;
        this.mContenido = contenido;
        this.mFecha = fecha;
        this.mNombreUsuarioAutor = nombreUsuarioAutor;
        this.mIdRestaurante = idRestaurante;
    }
    
    /**
     * Crea una nueva Reseña a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Reseña correspondiente.
     */
    public static Review desdeParametros(Map<String, String[]> parametros)
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
        
        // Crear nueva instancia de Usuario.
        final Review review = new Review();
        
        // Obtener los valores a partir de los campos.
        String idReviewStr = campos.get(ReviewDAO.COLUMNA_ID);
        
        if (idReviewStr != null)
        {
            review.setId(Integer.parseInt(idReviewStr));
        }
        
        String puntajeStr = campos.get(ReviewDAO.COLUMNA_PUNTAJE);
        
        if (puntajeStr != null)
        {
            review.setPuntaje(Integer.parseInt(puntajeStr));
        }
        
        String contenido = campos.get(ReviewDAO.COLUMNA_CONTENIDO);
        review.setContenido(contenido);
        
        // Las fechas necesitan cambios, porque el HTML las produce con una "T" 
        // entre la fecha y hora, además de que no incluyen segundos.
        String strFecha = campos.get(ReviewDAO.COLUMNA_FECHA);
        
        if (strFecha != null)
        {
            review.setFecha(Timestamp.valueOf(strFecha.replace("T", " ").concat(":00")));
        }
        
        String idAutorStr = campos.get(ReviewDAO.COLUMNA_ID_AUTOR);
        review.setNombreUsuarioAutor(idAutorStr);
        
        String idRestauranteStr = campos.get(ReviewDAO.COLUMNA_ID_RESTAURANTE);
        
        if (idRestauranteStr != null)
        {
            int idRestaurante = Integer.parseInt(idRestauranteStr);
            review.setIdRestaurante(idRestaurante);
        }
    
        // Retornar nueva instancia de la entidad.
        return review;
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
        return "Review{" + "id=" + mId + ", puntaje=" + mPuntaje + ", contenido=" + mContenido + ", fecha=" + mFecha + ", nombreUsuarioAutor=" + mNombreUsuarioAutor + ", usuarioAutor=" + mUsuarioAutor + ", idRestaurante=" + mIdRestaurante + ", restaurante=" + mRestaurante + '}';
    }

    public int getId()
    {
        return mId;
    }

    public void setId(int id)
    {
        this.mId = id;
    }

    public int getPuntaje()
    {
        return mPuntaje;
    }

    public void setPuntaje(int puntaje)
    {
        this.mPuntaje = puntaje;
    }

    public String getContenido()
    {
        return mContenido;
    }

    public void setContenido(String contenido)
    {
        this.mContenido = contenido;
    }

    public Timestamp getFecha()
    {
        return mFecha;
    }

    public void setFecha(Timestamp fecha)
    {
        this.mFecha = fecha;
    }

    public String getNombreUsuarioAutor()
    {
        return mNombreUsuarioAutor;
    }

    public void setNombreUsuarioAutor(String nombreUsuarioAutor)
    {
        this.mNombreUsuarioAutor = nombreUsuarioAutor;
    }

    public Usuario getUsuarioAutor()
    {
        return mUsuarioAutor;
    }

    public void setUsuarioAutor(Usuario usuarioAutor)
    {
        this.mUsuarioAutor = usuarioAutor;
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

    public void setRestaurante(Restaurante restaurante)
    {
        this.mRestaurante = restaurante;
    }
}
