package com.rappi.crud.servlets;

import com.rappi.crud.dao.EstadoDAO;
import com.rappi.crud.dao.PaisDAO;
import com.rappi.crud.entidades.Estado;
import com.rappi.crud.entidades.Pais;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

@WebServlet(name = "ServletEstados", urlPatterns = {"/estados"})
public class ServletEstados extends HttpServlet
{
    @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletEstados.class.getName());
    
    private EstadoDAO mEstadoDAO;
    
    private PaisDAO mPaisesDAO;
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mEstadoDAO = new EstadoDAO(mPoolConexionesDB);
        mPaisesDAO = new PaisDAO(mPoolConexionesDB);
    }

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
        
        Accion accion = Accion.LEER;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        obtenerListaDatos(req, res, idEstado, accion);
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
        
        Accion accion = Accion.CREAR;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
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
                accion = Accion.ELIMINAR;
            }
        }
        
        System.out.println("Accion: " + accion);
            
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
            obtenerListaDatos(req, res, null, Accion.LEER);
        }
    }
    
    private void obtenerListaDatos(HttpServletRequest req, HttpServletResponse res, 
            String idEstado, Accion accion) 
            throws ServletException, IOException
    {
        System.out.println("En doGet");
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
                List<Pais> paises = mPaisesDAO.getPaises();
                
                req.setAttribute("paises", paises);
                                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/estados/formulario.jsp");
               
                System.out.println(requestDispatcher == null);

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Estado> estados = mEstadoDAO.getEstados();
                
                req.setAttribute("estados", estados);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/estados/lista.jsp");

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
