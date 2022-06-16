package com.rappi.crud.servlets;

import com.rappi.crud.dao.PaisDAO;
import com.rappi.crud.entidades.Pais;
import java.io.IOException;
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

@WebServlet(name = "ServletPaises", urlPatterns = {"/paises"})
public class ServletPaises extends HttpServlet
{
    @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletPaises.class.getName());
    
    private PaisDAO mPaisDAO;
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mPaisDAO = new PaisDAO(mPoolConexionesDB);
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
        
        String codigoPais = null;
        
        if (parametros.get(PaisDAO.COLUMNA_ID) != null)
        {
            codigoPais = parametros.get(PaisDAO.COLUMNA_ID)[0];
        }
        
        obtenerListaDatos(req, res, codigoPais);
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
        
        String idPais = null;
        
        if (parametros.get(PaisDAO.COLUMNA_ID) != null)
        {
            idPais = parametros.get(PaisDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Accion.CREAR;
        
        if (parametros.get(Accion.class.getName()) != null) 
        {
            accion = Accion.valueOf(parametros.get(Accion.class.getName())[0]);
        }
        
        Pais datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Pais.desdeParametros(parametros);

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
                    int idInsertado = mPaisDAO.insertarPais(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mPaisDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    mPaisDAO.eliminar(idPais);
                    break;
            }
            
        } catch (SQLException e)
        {
            mLogger.log(Level.SEVERE, e.getMessage());
            
        } finally 
        {
            obtenerListaDatos(req, res, null);
        }
    }
    
    private void obtenerListaDatos(HttpServletRequest req, HttpServletResponse res, String codigoPais) 
            throws ServletException, IOException
    {
        try
        {
            if (codigoPais != null)
            {
                // Obtener un registro especifico de la BD.                               
                Pais pais = mPaisDAO.getPaisPorId(codigoPais);
                
                req.setAttribute("pais", pais);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/paises/formulario.jsp");

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Pais> paises = mPaisDAO.getPaises();
                
                req.setAttribute("paises", paises);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/paises/lista.jsp");

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
