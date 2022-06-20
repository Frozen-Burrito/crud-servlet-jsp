package com.rappi.crud.servlets;

import com.rappi.crud.dao.EstadoDAO;
import com.rappi.crud.dao.PaisDAO;
import com.rappi.crud.entidades.jpa.Estado;
import com.rappi.crud.entidades.jpa.Pais;
import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet(name = "ServletEstados", urlPatterns = {"/app/estados"})
public class ServletEstados extends HttpServlet
{
    private static final Logger mLogger = Logger.getLogger(ServletEstados.class.getName());
    
    private final EstadoDAO mEstadoDAO = new EstadoDAO();
    private final PaisDAO mPaisDAO = new PaisDAO();
    
    private static final String VISTA_LISTA = "/app/estados/lista.jsp";
    private static final String VISTA_FORMULARIO = "/app/estados/formulario.jsp";

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
        
        String idEstado = null;
        
        if (parametros.get(EstadoDAO.COLUMNA_ID) != null)
        {
            idEstado = parametros.get(EstadoDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        mostrarVistaConDatos(req, res, idEstado, accion);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param req servlet request
     * @param res servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        Map<String, String[]> parametros = req.getParameterMap();
        
        String idEstado = null;
        
        if (parametros.get(EstadoDAO.COLUMNA_ID) != null)
        {
            idEstado = parametros.get(EstadoDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        Estado datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Estado.desdeParametros(parametros);

            } catch (NullPointerException | NumberFormatException e)
            {
                mLogger.log(Level.SEVERE, null, e);
            }
        }
            
        // Realizar la accion CUD determinada.
        try 
        {
            switch (accion) 
            {
                case CREAR:                    
                    int idInsertado = mEstadoDAO.insertarEstado(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mEstadoDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    int id = Integer.parseInt(idEstado);
                    mEstadoDAO.eliminar(id);
                    break;
            }
            
        } catch (SQLException e)
        {
            mLogger.log(Level.SEVERE, e.getMessage());
            
        } finally 
        {
            mostrarVistaConDatos(req, res, null, Accion.LEER);
        }
    }
    
    private void mostrarVistaConDatos(HttpServletRequest req, HttpServletResponse res, 
            String idEstado, Accion accion) 
            throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (idEstado != null || accion.equals(Accion.CREAR))
            {
                if (idEstado != null) 
                {
                    int id = Integer.parseInt(idEstado);
                    
                    // Obtener un registro especifico de la BD.                               
                    Estado estado = mEstadoDAO.getEstadoPorId(id);
                    
                    req.setAttribute("estado", estado);
                }
                
                // Obtener entidades relacionadas de la BD.
                List<Pais> paises = mPaisDAO.getPaises();
                
                req.setAttribute("paises", paises);
                
                // Determinar el título 
                String encabezadoVista = Utilidades.tituloVistaConAccion(accion, Estado.NOMBRE_ENTIDAD);
                
                req.setAttribute("encabezadoVista", encabezadoVista);
                                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_FORMULARIO);
                
                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Estado> estados = mEstadoDAO.getEstados();
                
                req.setAttribute("estados", estados);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_LISTA);

                requestDispatcher.forward(req, res);
            }
            
        } catch (SQLException e)
        {
            mLogger.log(Level.SEVERE, e.getMessage(), e);
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
        return "Acceso y modificación de países.";
    }

}
