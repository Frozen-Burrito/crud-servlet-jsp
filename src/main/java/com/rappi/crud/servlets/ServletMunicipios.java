package com.rappi.crud.servlets;

import com.rappi.crud.dao.EstadoDAO;
import com.rappi.crud.dao.MunicipioDAO;
import com.rappi.crud.entidades.jpa.Estado;
import com.rappi.crud.entidades.jpa.Municipio;
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

@WebServlet(name = "ServletMunicipios", urlPatterns = {"/app/datos-municipios"})
public class ServletMunicipios extends HttpServlet
{
    private static final Logger mLogger = Logger.getLogger(ServletMunicipios.class.getName());
    
    private final MunicipioDAO mMunicipioDAO = new MunicipioDAO();
        
    private static final String VISTA_LISTA = "/app/municipios/lista.jsp";
    private static final String VISTA_FORMULARIO = "/app/municipios/formulario.jsp";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        
        String idMunicipio = null;
        
        if (parametros.get(MunicipioDAO.COLUMNA_ID) != null)
        {
            idMunicipio = parametros.get(MunicipioDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        mostrarVistaConDatos(req, res, idMunicipio, accion);
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
        
        String idMunicipio = null;
        
        if (parametros.get(MunicipioDAO.COLUMNA_ID) != null)
        {
            idMunicipio = parametros.get(MunicipioDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        Municipio datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Municipio.desdeParametros(parametros);

            } catch (NullPointerException | NumberFormatException e)
            {
                mLogger.log(Level.SEVERE, null, e);
                accion = Accion.ELIMINAR;
            }
        }
            
        // Realizar la accion CUD determinada.
        try 
        {
            switch (accion) 
            {
                case CREAR:
                    int idInsertado = mMunicipioDAO.insertar(datosRecibidos);
                    break;
                case ACTUALIZAR:
                    mMunicipioDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    int id = Integer.parseInt(idMunicipio);
                    mMunicipioDAO.eliminar(id);
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
            String idMunicipio, Accion accion) 
            throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (idMunicipio != null || accion.equals(Accion.CREAR))
            {
                if (idMunicipio != null) 
                {
                    int id = Integer.parseInt(idMunicipio);
                    
                    // Obtener un registro especifico de la BD.                               
                    Municipio municipio = mMunicipioDAO.getMunicipioPorId(id);
                    
                    req.setAttribute("municipio", municipio);
                }
                
                // Obtener entidades relacionadas de la BD.
                EstadoDAO estadoDAO = new EstadoDAO();
                List<Estado> estados = estadoDAO.getEstados();
                
                req.setAttribute("estados", estados);
                
                // Determinar el título 
                String encabezadoVista = Utilidades.tituloVistaConAccion(accion, Municipio.NOMBRE_ENTIDAD);
                
                req.setAttribute("encabezadoVista", encabezadoVista);
                                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_FORMULARIO);

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Municipio> municipios = mMunicipioDAO.getMunicipios();
                
                req.setAttribute("municipios", municipios);
                
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
        return "Short description";
    }// </editor-fold>

}
