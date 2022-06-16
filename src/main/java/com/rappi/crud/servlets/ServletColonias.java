package com.rappi.crud.servlets;

import com.rappi.crud.dao.ColoniaDAO;
import com.rappi.crud.dao.EstadoDAO;
import com.rappi.crud.dao.MunicipioDAO;
import com.rappi.crud.entidades.Colonia;
import com.rappi.crud.entidades.Municipio;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ServletColonias", urlPatterns = {"/datos-colonias"})
public class ServletColonias extends HttpServlet
{
    @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletColonias.class.getName());
    
    private ColoniaDAO mColoniaDAO;
    
    private MunicipioDAO mMunicipioDAO;
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mColoniaDAO = new ColoniaDAO(mPoolConexionesDB);
        mMunicipioDAO = new MunicipioDAO(mPoolConexionesDB);
    }

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
        
        String idColoniaStr = null;
        
        if (parametros.get(EstadoDAO.COLUMNA_ID) != null)
        {
            idColoniaStr = parametros.get(EstadoDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Accion.LEER;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        mostrarVistaConDatos(request, response, idColoniaStr, accion);
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
        
        String idColoniaStr = null;
        
        if (parametros.get(EstadoDAO.COLUMNA_ID) != null)
        {
            idColoniaStr = parametros.get(EstadoDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Accion.CREAR;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        Colonia datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Colonia.desdeParametros(parametros);

            } catch (NullPointerException | NumberFormatException e)
            {
                mLogger.log(Level.SEVERE, null, e);
                accion = Accion.ELIMINAR;
            }
        }
        
        System.out.println("Colonia: " + datosRecibidos);
        
        System.out.println("Accion: " + accion);
            
        // Realizar la accion CUD determinada.
        try 
        {
            switch (accion) 
            {
                case CREAR:
                    int idInsertado = mColoniaDAO.insertar(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mColoniaDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    int id = Integer.parseInt(idColoniaStr);
                    mColoniaDAO.eliminar(id);
                    break;
            }
            
        } catch (SQLException e)
        {
            mLogger.log(Level.SEVERE, e.getMessage());
            
        } finally 
        {
            mostrarVistaConDatos(request, response, null, Accion.LEER);
        }
    }
    
    private void mostrarVistaConDatos(HttpServletRequest req, HttpServletResponse res, 
        String idColonia, Accion accion) 
        throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (idColonia != null || accion.equals(Accion.CREAR))
            {
                if (idColonia != null) 
                {
                    int id = Integer.parseInt(idColonia);
                    
                    // Obtener un registro especifico de la BD.                               
                    Colonia colonia = mColoniaDAO.getColoniaPorId(id);
                    
                    req.setAttribute("colonia", colonia);
                }
                
                // Obtener entidades relacionadas de la BD.
                List<Municipio> municipios = mMunicipioDAO.getMunicipios();
                
                req.setAttribute("municipios", municipios);
                                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/colonias/formulario.jsp");

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Colonia> colonias = mColoniaDAO.getColonias();
                
                req.setAttribute("colonias", colonias);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/colonias/lista.jsp");

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
    }
}
