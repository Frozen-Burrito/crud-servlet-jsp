package com.rappi.crud.entidades;

import com.rappi.crud.dao.EstadoDAO;
import java.util.HashMap;
import java.util.Map;

public class Estado
{   
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Estado";
    
    // Columnas de la tabla en la BD.
    private final int mId;
    
    private String mNombre;
    
    private String mCodigoPais;
    private Pais mPais;

    public Estado(int id, String nombre, String codigoPais)
    {
        this.mId = id;
        this.mNombre = nombre;
        this.mCodigoPais = codigoPais;
    }
    
    /**
     * Crea un nuevo Pais a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Pais correspondiente.
     */
    public static Estado desdeParametros(Map<String, String[]> parametros)
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
        
        int id = -1;
        
        if (campos.get(EstadoDAO.COLUMNA_ID) != null)
        {
            id = Integer.parseInt(campos.get(EstadoDAO.COLUMNA_ID));
        }
        
        // Obtener los valores a partir de los campos.
        String nombre = campos.get(EstadoDAO.COLUMNA_NOMBRE);
        String codigoPais = campos.get(EstadoDAO.COLUMNA_CODIGO_PAIS);
    
        // Retornar nueva instancia de la entidad.
        return new Estado(id, nombre, codigoPais);
    }

    @Override
    public String toString()
    {
        return "Estado{" + "mId=" + mId + ", mNombre=" + mNombre + ", mCodigoPais=" + mCodigoPais + ", mPais=" + mPais + '}';
    }
    
    public int getId() 
    {
        return mId;
    }

    public String getNombre()
    {
        return mNombre;
    }

    public void setNombre(String nombre)
    {
        this.mNombre = nombre;
    }

    public String getCodigoPais()
    {
        return mCodigoPais;
    }

    public void setCodigoPais(String codigoPais)
    {
        this.mCodigoPais = codigoPais;
    }

    public Pais getPais()
    {
        return mPais;
    }

    public void setPais(Pais pais)
    {
        this.mPais = pais;
    }
}
