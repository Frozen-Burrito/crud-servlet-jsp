package com.rappi.crud.entidades;

import com.rappi.crud.dao.ColoniaDAO;
import com.rappi.crud.dao.EstadoDAO;
import com.rappi.crud.dao.UbicacionDAO;
import java.util.HashMap;
import java.util.Map;

public class Ubicacion
{   
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Ubicación";
    
    // Columnas de la tabla en la BD.
    private int mId;
    
    private String mNombreCalle;
    
    private int mNumExterior;
    private int mNumInterior;
    
    private int mIdColonia;
    private Colonia mColonia;
    
    public Ubicacion()
    {
        mId = -1;
    }

    public Ubicacion(int id, String nombreCalle, int numeroExterior, int numeroInterior, int idColonia)
    {
        this.mId = id;
        this.mNombreCalle = nombreCalle;
        this.mNumExterior = numeroExterior;
        this.mNumInterior = numeroInterior;
        this.mIdColonia = idColonia;
    }
    
    /**
     * Crea una nueva Ubicacion a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Ubicacion correspondiente.
     */
    public static Ubicacion desdeParametros(Map<String, String[]> parametros)
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
        
        // Crear nueva instancia de Colonia.
        final Ubicacion ubicacion = new Ubicacion();
        
        if (campos.get(UbicacionDAO.COLUMNA_ID) != null)
        {
            int id = Integer.parseInt(campos.get(UbicacionDAO.COLUMNA_ID));
            ubicacion.setId(id);
        }
        
        // Obtener los valores a partir de los campos.
        String calle = campos.get(UbicacionDAO.COLUMNA_CALLE);
        ubicacion.setNombreCalle(calle);
        
        String strNumExt = campos.get(UbicacionDAO.COLUMNA_NUM_EXTERIOR);
        String strNumInt = campos.get(UbicacionDAO.COLUMNA_NUM_INTERIOR);
        
        if (strNumExt != null && strNumInt != null)
        {
            int numExterior = Integer.parseInt(strNumExt);
            ubicacion.setNumExterior(numExterior);
            
            int numInterior = Integer.parseInt(strNumInt);
            ubicacion.setNumInterior(numInterior);
        }
        
        String idColoniaStr = campos.get(UbicacionDAO.COLUMNA_ID_COLONIA);
        
        if (idColoniaStr != null)
        {
            int idColonia = Integer.parseInt(idColoniaStr);
            ubicacion.setIdColonia(idColonia);
        }
    
        // Retornar nueva instancia de la entidad.
        return ubicacion;
    }

    @Override
    public String toString()
    {
        return String.format("%s, #%s-%s", mNombreCalle, mNumExterior, mNumInterior);
    }
    
    public int getId() 
    {
        return mId;
    }
    
    public void setId(int id)
    {
        this.mId = id;
    }

    public String getNombreCalle()
    {
        return mNombreCalle;
    }

    public void setNombreCalle(String nombreCalle)
    {
        this.mNombreCalle = nombreCalle;
    }

    public int getNumExterior()
    {
        return mNumExterior;
    }

    public void setNumExterior(int numExterior)
    {
        this.mNumExterior = numExterior;
    }

    public int getNumInterior()
    {
        return mNumInterior;
    }

    public void setNumInterior(int numInterior)
    {
        this.mNumInterior = numInterior;
    }

    public int getIdColonia()
    {
        return mIdColonia;
    }

    public void setIdColonia(int idColonia)
    {
        this.mIdColonia = idColonia;
    }

    public Colonia getColonia()
    {
        return mColonia;
    }

    public void setColonia(Colonia colonia)
    {
        this.mColonia = colonia;
    }
}
