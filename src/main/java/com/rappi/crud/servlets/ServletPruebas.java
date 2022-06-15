package com.rappi.crud.servlets;

import com.rappi.crud.dao.PruebaDAO;
import com.rappi.crud.entidades.Datos;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
        Map<String, String[]> params = req.getParameterMap();
        
        params.entrySet().stream().forEach(parametro -> {
            System.out.println(parametro.getKey() + ": " + parametro.getKey());
        });
            
        String idStr = params.get("id") != null ? params.get("id")[0] : null;

        try
        {
            List<Datos> datos = new ArrayList<>();
            
            if (idStr != null)
            {
                int id = Integer.valueOf(idStr);
                                
                datos.add(mPruebaDAO.getDatosPorId(id));
                
            } else 
            {
                datos.addAll(mPruebaDAO.getDatos());
            }

            req.setAttribute("datos", datos);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/datos.jsp");

            requestDispatcher.forward(req, res);
            
        } catch (SQLException e)
        {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
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
        System.out.println("En doPost");
        
        try 
        {
            Map<String, String> campos = new HashMap<>();
            
            for (Map.Entry<String, String[]> dato : req.getParameterMap().entrySet())
            {
                System.out.println(dato.getKey() + ": " + dato.getValue()[0]);
                campos.put(dato.getKey(), dato.getValue()[0]);
            }
            
            Datos nuevosDatos = Datos.desdeMapa(campos);
            
            System.out.println("Nuevos datos: " + nuevosDatos);
            
            int idInsertado = mPruebaDAO.insertarDatos(nuevosDatos);

            if (idInsertado >= 0)
            {
                System.out.println("Producto registrado: " + idInsertado);
            }
            
        } catch (SQLException e)
        {
            Logger.getLogger(ServletPruebas.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        System.out.println("En doPut");
        
        Map<String, String> campos = new HashMap<>();
            
        for (Map.Entry<String, String[]> dato : req.getParameterMap().entrySet())
        {
            System.out.println(dato.getKey() + ": " + dato.getValue()[0]);
            campos.put(dato.getKey(), dato.getValue()[0]);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        System.out.println("En doDelete");
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
