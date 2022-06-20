/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.rappi.crud.servlets;

import com.rappi.crud.dao.IngredienteDAO;
import com.rappi.crud.entidades.Ingrediente;
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

/**
 *
 * @author laura_epj4q4k
 */
@WebServlet(name = "ServletDatosIngredientes", urlPatterns = {"/ingredientes"})
public class ServletDatosIngredientes extends HttpServlet {
@Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletDatosIngredientes.class.getName());
    
    private IngredienteDAO mIngredienteDAO;
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mIngredienteDAO = new IngredienteDAO(mPoolConexionesDB);
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
        
        String idIngrediente = null;
        
        if (parametros.get(IngredienteDAO.COLUMNA_ID) != null)
        {
            idIngrediente = parametros.get(IngredienteDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Accion.LEER;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        mostrarVistaConDatos(req, res, idIngrediente, accion);
        //obtenerListaDatos(req, res, idIngrediente);
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
        
        String idIngrediente = null;
        int intIdIngrediente = 0;
        
        if (parametros.get(IngredienteDAO.COLUMNA_ID) != null)
        {
            idIngrediente = parametros.get(IngredienteDAO.COLUMNA_ID)[0];
            intIdIngrediente = Integer.parseInt(idIngrediente);
        }
        
        Accion accion = Accion.CREAR;
        String parametroAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(parametroAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(parametroAccion)[0]);
        }
        
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
                    int idInsertado = mIngredienteDAO.insertarIngrediente(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mIngredienteDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    mIngredienteDAO.eliminar(intIdIngrediente);
                    break;
            }
            
        } catch (SQLException e)
        {
            mLogger.log(Level.SEVERE, e.getMessage());
            
        } finally 
        {
            mostrarVistaConDatos(req, res, null, Accion.LEER);
            //obtenerListaDatos(req, res, null);
        }
    }

    private void mostrarVistaConDatos(HttpServletRequest req, HttpServletResponse res, 
        String idIngrediente, Accion accion) 
        throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (idIngrediente != null || accion.equals(Accion.CREAR))
            {
                if (idIngrediente != null) 
                {
                    int id = Integer.parseInt(idIngrediente);
                    
                    // Obtener un registro especifico de la BD.                               
                    Ingrediente ingrediente = mIngredienteDAO.getIngredientePorId(id);
                    
                    req.setAttribute("ingrediente", ingrediente);
                }
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/ingredientes/formulario.jsp");

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Ingrediente> ingredientes = mIngredienteDAO.getIngredientes();
                
                req.setAttribute("ingredientes", ingredientes);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/ingredientes/lista.jsp");

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
        return "Acceso y modificación de ingredientes.";
    }
    
}
