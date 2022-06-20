package com.rappi.crud.servlets;

import java.util.Map;

public class Utilidades
{
    /**
     * Determina el encabezado adecuado para la vista de detalles de la entidad,
     * según la acción de la vista.
     * 
     * @param accionVista La acción de la vista.
     * @param nombreEntidad El nombre de la entidad.
     * @return El texto para el encabezado de la vista.
     */
    public static String tituloVistaConAccion(Accion accionVista, String nombreEntidad)
    {
        switch (accionVista) 
        {
            case LEER: return "Detalles del " + nombreEntidad;
            case CREAR: return "Agrega un Nuevo " +nombreEntidad;
            case ACTUALIZAR: return "Edita el " + nombreEntidad;
            case ELIMINAR: return "¿Deseas eliminar este " + nombreEntidad + "?";
        }
        
        return "Vista de " + nombreEntidad;
    }
    
    /**
     * Obtiene el valor de un parámetro con un tipo de acción, o null si el 
     * valor no existe.
     * 
     * @param parametros El mapa de parámetros del query.
     * @return La acción especificada en el query.
     */
    public static Accion getAccionDesdeParams(Map<String, String[]> parametros)
    {
        Accion accion = Accion.LEER;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        return accion;
    }
    
    /**
     * Obtiene el valor de un parámetro con un tipo de acción de autenticación, 
     * o null si el valor no existe.
     * 
     * @param parametros El mapa de parámetros del query.
     * @return La acción especificada en el query.
     */
    public static AccionAutenticacion getAccionAuthDesdeParams(Map<String, String[]> parametros)
    {
        AccionAutenticacion accion = AccionAutenticacion.INICIAR_SESION;
        final String llaveParamAccion = AccionAutenticacion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(llaveParamAccion) != null) 
        {
            accion = AccionAutenticacion.valueOf(parametros.get(llaveParamAccion)[0]);
        }
        
        return accion;
    }
}
