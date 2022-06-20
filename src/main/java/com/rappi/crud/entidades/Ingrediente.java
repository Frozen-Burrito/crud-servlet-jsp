package com.rappi.crud.entidades;

import com.rappi.crud.dao.IngredienteDAO;
import java.util.HashMap;
import java.util.Map;

public class Ingrediente {
     // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Ingrediente";
    
    // Columnas de la tabla en la BD.
    private final int mIdIngrediente;
    private String mNombre;
    private boolean mEsVegano;
    private boolean mEsAlergico;
    private boolean mTieneGluten;

    public Ingrediente(int idIngrediente, String nombre, boolean esVegano, boolean esAlergico, boolean tieneGluten)
    {
        mIdIngrediente = idIngrediente;
        mNombre = nombre;
        mEsVegano = esVegano;
        mEsAlergico = esAlergico;
        mTieneGluten = tieneGluten;
    }
    
    /**
     * Crea un nuevo Ingrediente a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Ingrediente correspondiente.
     */
    public static Ingrediente desdeParametros(Map<String, String[]> parametros)
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

        // Obtener los valores a partir de los campos.
        String nombre = campos.get(IngredienteDAO.COLUMNA_NOMBRE);
        System.out.println("Nombre: " + nombre);
        
        String idIngrediente = null;
        int intIdIngrediente = 0;
        
        if (campos.get(IngredienteDAO.COLUMNA_ID) != null)
        {
            idIngrediente = campos.get(IngredienteDAO.COLUMNA_ID);
            intIdIngrediente = Integer.parseInt(idIngrediente);
        }
    
        String esVegano = campos.get(IngredienteDAO.COLUMNA_ESVEGANO);
        System.out.println("esVegano: " + esVegano);
        
        String esAlergico = campos.get(IngredienteDAO.COLUMNA_ESALERGICO);
        System.out.println("esAlergico: " + esAlergico);
        
        String tieneGluten = campos.get(IngredienteDAO.COLUMNA_TIENEGLUTEN);
        System.out.println("tieneGluten: " + tieneGluten);
       
        // Retornar nueva instancia de la entidad.
        return new Ingrediente(intIdIngrediente, nombre,Boolean.parseBoolean(esVegano),Boolean.parseBoolean(esAlergico),Boolean.parseBoolean(tieneGluten));
    }

    @Override
    public String toString()
    {
        return "Ingrediente{" + "idIngrediente=" + mIdIngrediente + ", nombre=" + mNombre + ", esVegano=" + mEsVegano + ", esAlergico=" + mEsAlergico + ", tieneGluten=" + mTieneGluten +'}';
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public boolean getEsVegano() {
        return mEsVegano;
    }

    public void setEsVegano(boolean mEsVegano) {
        this.mEsVegano = mEsVegano;
    }

    public boolean getEsAlergico() {
        return mEsAlergico;
    }

    public void setEsAlergico(boolean mEsAlergico) {
        this.mEsAlergico = mEsAlergico;
    }

    public boolean getTieneGluten() {
        return mTieneGluten;
    }

    public void setTieneGluten(boolean mTieneGluten) {
        this.mTieneGluten = mTieneGluten;
    }
   
     public int getIdIngrediente() {
        return mIdIngrediente;
    }
}
