package com.rappi.crud.servlets;

import com.rappi.crud.dao.PruebaDAO;
import com.rappi.crud.entidades.Datos;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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

@WebServlet(name = "ServletPruebas", value = { "/pruebas" })
public class ServletPruebas extends HttpServlet
{
    @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger logger = Logger.getLogger(ServletPruebas.class.getName());
    
    private PruebaDAO mPruebaDAO;
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mPruebaDAO = new PruebaDAO(mPoolConexionesDB);
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
        
        String idStr = null;
        
        if (parametros.get(Datos.COLUMNA_ID) != null)
        {
            idStr = parametros.get(Datos.COLUMNA_ID)[0];
        }
        
        obtenerListaDatos(req, res, idStr);
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
        
        int id = -1;
        
        if (parametros.get(Datos.COLUMNA_ID) != null)
        {
            String idStr = parametros.get(Datos.COLUMNA_ID)[0];
            
            id = Integer.parseInt(idStr);
        }
        
        Accion accion;
        boolean esRegistroExistente = id >= 0;
        Datos datosRecibidos = null;
        
        // Determinar el tipo de accion segun el id y los campos recibidos.
        // CREATE recibe datos, pero no un ID.
        // UPDATE recibe datos y un ID.
        // DELETE recibe un ID, pero no datos.
        try 
        {
            datosRecibidos = Datos.desdeParametros(parametros);
            accion = esRegistroExistente ? Accion.ACTUALIZAR : Accion.CREAR;
            
        } catch (NullPointerException | NumberFormatException e)
        {
            e.printStackTrace();
            System.out.println("Excepcion: " + e.getMessage());
            accion = Accion.ELIMINAR;
        }
        
        System.out.println("Datos: " + datosRecibidos + ", accion: " + accion.toString());
            
        // Realizar la accion CUD determinada.
        try 
        {
            switch (accion) 
            {
                case CREAR:
                    int idInsertado = mPruebaDAO.insertarDatos(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mPruebaDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    mPruebaDAO.eliminar(id);
                    break;
            }
            
        } catch (SQLException e)
        {
            Logger.getLogger(ServletPruebas.class.getName()).log(Level.SEVERE, e.getMessage());
        } finally 
        {
            obtenerListaDatos(req, res, null);
        }
    }
    
    private void obtenerListaDatos(HttpServletRequest req, HttpServletResponse res, String idStr) 
            throws ServletException, IOException
    {
        try
        {
            if (idStr != null)
            {
                // Obtener un registro especifico de la BD.
                int id = Integer.valueOf(idStr);
                                
                Datos datos = mPruebaDAO.getDatosPorId(id);
                
                req.setAttribute("datos", datos);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/formulario-prueba.jsp");

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Datos> regDatos = mPruebaDAO.getDatos();
                
                req.setAttribute("datos", regDatos);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/datos.jsp");

                requestDispatcher.forward(req, res);
            }
            
        } catch (SQLException e)
        {
            logger.log(Level.SEVERE, e.getMessage(), e);
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
        return "Un simple servlet de pruebas";
    }
}
