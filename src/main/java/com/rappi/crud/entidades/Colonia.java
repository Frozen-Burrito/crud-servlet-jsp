package com.rappi.crud.entidades;

import com.rappi.crud.dao.ColoniaDAO;
import com.rappi.crud.dao.EstadoDAO;
import java.util.HashMap;
import java.util.Map;

public class Colonia
{   
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Colonia";
    
    // Columnas de la tabla en la BD.
    private int mId;
    
    private String mNombre;
    
    private int mCodigoPostal;
    
    private int mIdMunicipio;
    private Municipio mMunicipio;
    
    public Colonia()
    {
        mId = -1;
    }

    public Colonia(int id, String nombre, int codigoPostal, int idMunicipio)
    {
        this.mId = id;
        this.mNombre = nombre;
        this.mCodigoPostal = codigoPostal;
        this.mIdMunicipio = idMunicipio;
    }
    
    /**
     * Crea una nueva Colonia a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Colonia correspondiente.
     */
    public static Colonia desdeParametros(Map<String, String[]> parametros)
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
        final Colonia colonia = new Colonia();
        
        if (campos.get(ColoniaDAO.COLUMNA_ID) != null)
        {
            int id = Integer.parseInt(campos.get(ColoniaDAO.COLUMNA_ID));
            colonia.setId(id);
        }
        
        // Obtener los valores a partir de los campos.
        String nombre = campos.get(EstadoDAO.COLUMNA_NOMBRE);
        colonia.setNombre(nombre);
        
        String strCodigoPostal = campos.get(ColoniaDAO.COLUMNA_CODIGO_POSTAL);
        
        if (strCodigoPostal != null)
        {
            int codigoPostal = Integer.parseInt(strCodigoPostal);
            colonia.setCodigoPostal(codigoPostal);
        }
        
        String idMunicipioStr = campos.get(ColoniaDAO.COLUMNA_ID_MUNICIPIO);
        
        System.out.println("ID municipio: " );
        
        if (idMunicipioStr != null)
        {
            int idMunicipio = Integer.parseInt(idMunicipioStr);
            colonia.setmIdMunicipio(idMunicipio);
        }
    
        // Retornar nueva instancia de la entidad.
        return colonia;
    }

    @Override
    public String toString()
    {
        return "Colonia{" + "mId=" + mId + ", mNombre=" + mNombre + ", mCodigoPostal=" + mCodigoPostal + ", mIdMunicipio=" + mIdMunicipio + ", mMunicipio=" + mMunicipio + '}';
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

    public int getCodigoPostal()
    {
        return mCodigoPostal;
    }

    public void setCodigoPostal(int codigoPostal)
    {
        this.mCodigoPostal = codigoPostal;
    }

    public int getIdMunicipio()
    {
        return mIdMunicipio;
    }

    public void setmIdMunicipio(int idMunicipio)
    {
        this.mIdMunicipio = idMunicipio;
    }

    public Municipio getMunicipio()
    {
        return mMunicipio;
    }

    public void setMunicipio(Municipio municipio)
    {
        this.mMunicipio = municipio;
    }
}
