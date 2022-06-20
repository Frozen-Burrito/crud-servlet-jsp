package com.rappi.crud.servlets;

import com.rappi.crud.dao.ColoniaDAO;
import com.rappi.crud.dao.UbicacionDAO;
import com.rappi.crud.entidades.jpa.Colonia;
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

@WebServlet(name = "ServletUbicaciones", urlPatterns = {"/app/ubicaciones"})
public class ServletUbicaciones extends HttpServlet
{    
    private static final Logger mLogger = Logger.getLogger(ServletMunicipios.class.getName());
    
    private final UbicacionDAO mUbicacionDAO = new UbicacionDAO();
    private final ColoniaDAO mColoniaDAO = new ColoniaDAO();
    
    private static final String VISTA_LISTA = "/app/ubicaciones/lista.jsp";
    private static final String VISTA_FORMULARIO = "/app/ubicaciones/formulario.jsp";

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param req servlet request
     * @param res servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        Map<String, String[]> parametros = req.getParameterMap();
        
        String idUbicacionStr = null;
        
        if (parametros.get(UbicacionDAO.COLUMNA_ID) != null)
        {
            idUbicacionStr = parametros.get(UbicacionDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        mostrarVistaConDatos(req, res, idUbicacionStr, accion);
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
        
        String idUbicacionStr = null;
        
        if (parametros.get(UbicacionDAO.COLUMNA_ID) != null)
        {
            idUbicacionStr = parametros.get(UbicacionDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        Ubicacion datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Ubicacion.desdeParametros(parametros);

            } catch (IllegalArgumentException e)
            {
                // Error de validacion.
                mLogger.log(Level.SEVERE, null, e);
            }
        }
            
        // Realizar la accion CUD determinada.
        switch (accion) 
        {
            case CREAR:
                mUbicacionDAO.insertar(datosRecibidos);
                break;
            case ACTUALIZAR:
                mUbicacionDAO.actualizar(datosRecibidos);
                break;
            case ELIMINAR:
                int id = Integer.parseInt(idUbicacionStr);
                mUbicacionDAO.eliminar(id);
                break;
        }
        
        mostrarVistaConDatos(request, response, null, Accion.LEER);
    }
    
    private void mostrarVistaConDatos(HttpServletRequest req, HttpServletResponse res, 
            String idUbicacionStr, Accion accion) 
            throws ServletException, IOException
    {
        req.setAttribute("accion", accion);

        if (idUbicacionStr != null || accion.equals(Accion.CREAR))
        {
            if (idUbicacionStr != null) 
            {
                try 
                {
                    int id = Integer.parseInt(idUbicacionStr);

                    // Obtener un registro especifico de la BD.                               
                    Ubicacion ubicacion = mUbicacionDAO.getUbicacionPorId(id);

                    req.setAttribute("ubicacion", ubicacion);
                    
                } catch (NumberFormatException e)
                {
                    req.setAttribute("ubicacion", null);
                }
            }

            // Obtener entidades relacionadas de la BD.
            List<Colonia> colonias = mColoniaDAO.getColonias();

            req.setAttribute("colonias", colonias);

            // Determinar el título 
            String encabezadoVista = Utilidades.tituloVistaConAccion(accion, Colonia.NOMBRE_ENTIDAD);

            req.setAttribute("encabezadoVista", encabezadoVista);

            // Enviar jsp con resultado.                
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_FORMULARIO);

            requestDispatcher.forward(req, res);

        } else 
        {
            // Obtener todos los registros disponibles.
            List<Ubicacion> ubicaciones = mUbicacionDAO.getUbicaciones();

            req.setAttribute("ubicaciones", ubicaciones);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_LISTA);

            requestDispatcher.forward(req, res);
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
        return "Acceso y modificaciones a ubicaciones";
    }// </editor-fold>

}
