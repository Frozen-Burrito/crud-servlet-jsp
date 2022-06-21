package com.rappi.crud.servlets;

import com.rappi.crud.dao.RestauranteDAO;
import com.rappi.crud.dao.UbicacionDAO;
import com.rappi.crud.entidades.jpa.Restaurante;
import com.rappi.crud.entidades.jpa.Ubicacion;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletRestaurantes", urlPatterns = {"/app/restaurantes"})
public class ServletRestaurantes extends HttpServlet
{
    private static final Logger mLogger = Logger.getLogger(ServletColonias.class.getName());
    
    private final RestauranteDAO mRestauranteDAO = new RestauranteDAO();
    
    private final UbicacionDAO mUbicacionDAO = new UbicacionDAO();
        
    private static final String VISTA_LISTA = "/app/restaurantes/lista.jsp";
    private static final String VISTA_FORMULARIO = "/app/restaurantes/formulario.jsp";

    // <editor-fold defaultstate="collapsed" desc="Métodos para manejar peticiones HTTP GET y POST.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Map<String, String[]> parametros = request.getParameterMap();
        
        String idRestauranteStr = null;
        
        if (parametros.get(RestauranteDAO.COLUMNA_ID) != null)
        {
            idRestauranteStr = parametros.get(RestauranteDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        mostrarVistaConDatos(request, response, idRestauranteStr, accion);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Map<String, String[]> parametros = request.getParameterMap();
        
        String idRestauranteStr = null;
        
        if (parametros.get(RestauranteDAO.COLUMNA_ID) != null)
        {
            idRestauranteStr = parametros.get(RestauranteDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        Restaurante datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Restaurante.desdeParametros(parametros);

            } catch (NullPointerException | NumberFormatException e)
            {
                mLogger.log(Level.SEVERE, null, e);
            }
        }
        
        // Realizar la accion CUD determinada.
        switch (accion) 
        {
            case CREAR:
                mRestauranteDAO.insertar(datosRecibidos);
                break;
            case ACTUALIZAR:
                mRestauranteDAO.actualizar(datosRecibidos);
                break;
            case ELIMINAR:
                int id = Integer.parseInt(idRestauranteStr);
                mRestauranteDAO.eliminar(id);
                break;
        }
        
        // Redirigir al usuario para mostrar los resultados de la operacion.
        mostrarVistaConDatos(request, response, null, Accion.LEER);
    }
    
    private void mostrarVistaConDatos(HttpServletRequest request, HttpServletResponse response, 
        String idRestauranteStr, Accion accion) 
        throws ServletException, IOException
    {

        request.setAttribute("accion", accion);

        if (idRestauranteStr != null || accion.equals(Accion.CREAR))
        {
            if (idRestauranteStr != null) 
            {
                try 
                {
                    int idRestaurante = Integer.parseInt(idRestauranteStr);

                    // Obtener un registro especifico de la BD.                               
                    Restaurante restaurante = mRestauranteDAO.getRestaurantePorID(idRestaurante);

                    request.setAttribute("restaurante", restaurante);
                    
                } catch (NumberFormatException e)
                {
                    request.setAttribute("ubicacion", null);
                }
            }

            // Obtener entidades relacionadas de la BD.
            List<Ubicacion> ubicaciones = mUbicacionDAO.getUbicaciones();

            request.setAttribute("ubicaciones", ubicaciones);

            // Determinar el título 
            String encabezadoVista = Utilidades.tituloVistaConAccion(accion, Restaurante.NOMBRE_ENTIDAD);

            request.setAttribute("encabezadoVista", encabezadoVista);

            // Enviar jsp con resultado.  
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(VISTA_FORMULARIO);

            requestDispatcher.forward(request, response);

        } else 
        {
            // Obtener todos los registros disponibles.
            List<Restaurante> restaurantes = mRestauranteDAO.getRestaurantes();

            request.setAttribute("restaurantes", restaurantes);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(VISTA_LISTA);

            requestDispatcher.forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Acceso y modificacion de restaurantes";
    }// </editor-fold>

}
