package com.rappi.crud.entidades;

import java.util.HashMap;
import java.util.Map;

import com.rappi.crud.dao.PaisDAO;
import static com.rappi.crud.entidades.Restaurante.NOMBRE_ENTIDAD;
import com.rappi.crud.servlets.Accion;

public class Pais
{   
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "País";
    
    // Columnas de la tabla en la BD.
    private final String mCodigoPais;
    
    private String mNombre;

    public Pais(String mCodigoPais, String mNombre)
    {
        this.mCodigoPais = mCodigoPais;
        this.mNombre = mNombre;
    }
    
    /**
     * Crea un nuevo Pais a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Pais correspondiente.
     */
    public static Pais desdeParametros(Map<String, String[]> parametros)
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
        
        // Si no hay datos, indicar que el objeto de Datos estaría vacío usando
        // un NullPointerException.
        
        int numMinCampos = PaisDAO.ID_ES_AUTOMATICO ? 2 : 3;
        
        System.out.println("Size campos: " + campos.size());
        
        if (campos.size() < numMinCampos) 
        {
            throw new NullPointerException();
        }
        
        // Obtener los valores a partir de los campos.
        String nombre = campos.get(PaisDAO.COLUMNA_NOMBRE);
        
        System.out.println("Nombre: " + nombre);
        
        String codigo_pais = null;
        
        if (campos.get(PaisDAO.COLUMNA_ID) != null)
        {
            codigo_pais = campos.get(PaisDAO.COLUMNA_ID);
        }
    
        // Retornar nueva instancia de la entidad.
        return new Pais(codigo_pais, nombre);
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
        return "Pais{" + "codigoPais=" + mCodigoPais + ", nombre=" + mNombre + '}';
    }

    public String getCodigoPais()
    {
        return mCodigoPais;
    }

    public String getNombre()
    {
        return mNombre;
    }

    public void setmNombre(String nombre)
    {
        this.mNombre = nombre;
    }
}
