package com.rappi.crud.servlets;

import com.rappi.crud.dao.ProductoDAO;
import com.rappi.crud.entidades.Producto;
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

@WebServlet(name = "ServletProductos", urlPatterns = {"/Productos"})
public class ServletProductos extends HttpServlet {
@Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletProductos.class.getName());
    
    private ProductoDAO mProductoDAO;
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mProductoDAO = new ProductoDAO(mPoolConexionesDB);
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
        
        String idProducto = null;
        
        if (parametros.get(ProductoDAO.COLUMNA_ID) != null)
        {
            idProducto = parametros.get(ProductoDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Accion.LEER;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        mostrarVistaConDatos(req, res, idProducto, accion);
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
        
        String idProducto = null;
        int intIdProducto = 0;
        
        if (parametros.get(ProductoDAO.COLUMNA_ID) != null)
        {
            idProducto = parametros.get(ProductoDAO.COLUMNA_ID)[0];
            intIdProducto = Integer.parseInt(idProducto);
        }
        
        Accion accion = Accion.CREAR;
        String parametroAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(parametroAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(parametroAccion)[0]);
        }
        
        Producto datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Producto.desdeParametros(parametros);

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
                    int idInsertado = mProductoDAO.insertarProducto(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mProductoDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    mProductoDAO.eliminar(intIdProducto);
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
        String idProducto, Accion accion) 
        throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (idProducto != null || accion.equals(Accion.CREAR))
            {
                if (idProducto != null) 
                {
                    int id = Integer.parseInt(idProducto);
                    
                    // Obtener un registro especifico de la BD.                               
                    Producto producto = mProductoDAO.getProductoPorId(id);
                    
                    req.setAttribute("producto", producto);
                }
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/Productos/formulario.jsp");

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Producto> productos = mProductoDAO.getProductos();
                
                req.setAttribute("productos", productos);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/Productos/lista.jsp");

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
        return "Acceso y modificación de productos.";
    }
}
