package com.rappi.crud.entidades;

import com.rappi.crud.dao.MunicipioDAO;
import java.util.HashMap;
import java.util.Map;

public class Municipio
{   
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Municipio";
    
    // Columnas de la tabla en la BD.
    private int mId;
    
    private String mNombre;
    
    private int mIdEstado;
    private Estado mEstado;

    public Municipio()
    {
        mId = -1;
    }
    
    public Municipio(int id, String nombre, int idEstado)
    {
        this.mId = id;
        this.mNombre = nombre;
        this.mIdEstado = idEstado;
    }
    
    /**
     * Crea un nuevo Municipio a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Municipio correspondiente.
     */
    public static Municipio desdeParametros(Map<String, String[]> parametros)
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
        
        // Crear nueva instancia de Municipio.
        final Municipio municipio = new Municipio();
        
        if (campos.get(MunicipioDAO.COLUMNA_ID) != null)
        {
            int id = Integer.parseInt(campos.get(MunicipioDAO.COLUMNA_ID));
            municipio.setId(id);
        }
        
        // Obtener los valores a partir de los campos.
        String nombre = campos.get(MunicipioDAO.COLUMNA_NOMBRE);
        municipio.setNombre(nombre);
        
        String idEstadoStr = campos.get(MunicipioDAO.COLUMNA_ID_ESTADO);
        
        if (idEstadoStr != null)
        {
            int idEstado = Integer.parseInt(idEstadoStr);
            municipio.setIdEstado(idEstado);
        }
    
        // Retornar la instancia de la entidad.
        return municipio;
    }

    @Override
    public String toString()
    {
        return "Municipio{" + "mId=" + mId + ", mNombre=" + mNombre + ", mIdEstado=" + mIdEstado + ", mEstado=" + mEstado + '}';
    }

    public int getIdEstado()
    {
        return mIdEstado;
    }

    public void setIdEstado(int idEstado)
    {
        this.mIdEstado = idEstado;
    }

    public Estado getEstado()
    {
        return mEstado;
    }

    public void setEstado(Estado estado)
    {
        this.mEstado = estado;
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
}
