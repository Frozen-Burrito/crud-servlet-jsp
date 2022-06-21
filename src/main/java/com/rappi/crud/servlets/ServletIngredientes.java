package com.rappi.crud.servlets;

import com.rappi.crud.dao.IngredienteDAO;
import com.rappi.crud.entidades.jpa.Ingrediente;
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

@WebServlet(name = "ServletIngredientes", urlPatterns = {"/app/ingredientes"})
public class ServletIngredientes extends HttpServlet
{    
    private static final Logger mLogger = Logger.getLogger(ServletIngredientes.class.getName());
    
    private final IngredienteDAO mIngredienteDAO = new IngredienteDAO();
        
    private static final String VISTA_LISTA = "/app/ingredientes/lista.jsp";
    private static final String VISTA_FORMULARIO = "/app/ingredientes/formulario.jsp";

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
        
        String idIngredienteStr = null;
        
        if (parametros.get(IngredienteDAO.COLUMNA_ID) != null)
        {
            idIngredienteStr = parametros.get(IngredienteDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        mostrarVistaConDatos(request, response, idIngredienteStr, accion);
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
        
        String idIngredienteStr = null;
        
        if (parametros.get(IngredienteDAO.COLUMNA_ID) != null)
        {
            idIngredienteStr = parametros.get(IngredienteDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        Ingrediente datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Ingrediente.desdeParametros(parametros);

            } catch (NullPointerException | NumberFormatException e)
            {
                mLogger.log(Level.SEVERE, null, e);
            }
        }
                    
        // Realizar la accion CUD determinada.
        switch (accion) 
        {
            case CREAR:
                mIngredienteDAO.insertarIngrediente(datosRecibidos);
                break;
            case ACTUALIZAR:
                mIngredienteDAO.actualizar(datosRecibidos);
                break;
            case ELIMINAR:
                int id = Integer.parseInt(idIngredienteStr);
                mIngredienteDAO.eliminar(id);
                break;
        }
        
        mostrarVistaConDatos(request, response, null, Accion.LEER);
    }
    
    private void mostrarVistaConDatos(HttpServletRequest req, HttpServletResponse res, 
            String idIngredienteStr, Accion accion) 
            throws ServletException, IOException
    {
        req.setAttribute("accion", accion);

        if (idIngredienteStr != null || accion.equals(Accion.CREAR))
        {
            if (idIngredienteStr != null)
            {
                try 
                {
                    int id = Integer.parseInt(idIngredienteStr);

                    // Obtener un registro especifico de la BD.                               
                    Ingrediente ingrediente = mIngredienteDAO.getIngredientePorId(id);

                    req.setAttribute("ingrediente", ingrediente);

                } catch (NumberFormatException e)
                {
                    req.setAttribute("ingrediente", null);
                }
            }

            // Determinar el título 
            String encabezadoVista = Utilidades.tituloVistaConAccion(accion, Ingrediente.NOMBRE_ENTIDAD);

            req.setAttribute("encabezadoVista", encabezadoVista);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_FORMULARIO);

            requestDispatcher.forward(req, res);

        } else 
        {
            // Obtener todos los registros disponibles.
            List<Ingrediente> ingredientes = mIngredienteDAO.getIngredientes();

            req.setAttribute("ingredientes", ingredientes);

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
        return "Acceso y modificación de ingredientes.";
    }

}
