/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.rappi.crud.servlets;

import com.rappi.crud.dao.OfertaDAO;
import com.rappi.crud.dao.ProductoDAO;
import com.rappi.crud.entidades.Oferta;
import com.rappi.crud.entidades.Producto;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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

/**
 *
 * @author laura_epj4q4k
 */
@WebServlet(name = "ServletOfertas", urlPatterns = {"/ofertas"})
public class ServletOfertas extends HttpServlet {
 @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletOfertas.class.getName());
    
    private OfertaDAO mOfertaDAO;
    
    private ProductoDAO mProductoDAO;
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mOfertaDAO = new OfertaDAO(mPoolConexionesDB);
        mProductoDAO = new ProductoDAO(mPoolConexionesDB);
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
        
        String nombreDeOferta = null;
        
        if (parametros.get(OfertaDAO.COLUMNA_ID) != null)
        {
            nombreDeOferta = parametros.get(OfertaDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Accion.LEER;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        mostrarVistaConDatos(request, response, nombreDeOferta, accion);
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
        
        String nombreDeOferta = null;
        
        if (parametros.get(OfertaDAO.COLUMNA_ID) != null)
        {
            nombreDeOferta = parametros.get(OfertaDAO.COLUMNA_ID)[0];
        }
        
        // Obtener el tipo de operación CRUD que va a realizarse.
        Accion accion = Accion.CREAR;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        Oferta datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Oferta.desdeParametros(parametros);
            } catch (NullPointerException | NumberFormatException e){
                mLogger.log(Level.SEVERE, null, e);
                accion = Accion.ELIMINAR;
            } catch (ParseException ex) {
                Logger.getLogger(ServletOfertas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Realizar la accion CUD determinada.
        try 
        {
            switch (accion) 
            {
                case CREAR:
                    int idInsertado = mOfertaDAO.insertar(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mOfertaDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    int id = Integer.parseInt(nombreDeOferta);
                    mOfertaDAO.eliminar(id);
                    break;
            }
            
        } catch (SQLException e)
        {
            mLogger.log(Level.SEVERE, e.getMessage());
            
        } finally 
        {
            // Redirigir al oferta para mostrar los resultados de la operacion.
            mostrarVistaConDatos(request, response, null, Accion.LEER);
        }
    }
    
    private void mostrarVistaConDatos(HttpServletRequest req, HttpServletResponse res, 
        String idOfertaStr, Accion accion) 
        throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (idOfertaStr != null || accion.equals(Accion.CREAR))
            {
                if (idOfertaStr != null) 
                {
                    // Obtener un registro especifico de la BD.                               
                    Oferta oferta = mOfertaDAO.getOfertaPorID(idOfertaStr);
                    
                    req.setAttribute("oferta", oferta);
                }
                
                // Obtener entidades relacionadas de la BD.
                List<Producto> productos = mProductoDAO.getProductos();
                
                req.setAttribute("productos", productos);
                
                // Determinar el título 
                String encabezadoVista = Oferta.tituloVistaConAccion(accion);
                
                req.setAttribute("encabezadoVista", encabezadoVista);
                                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/ofertas/formulario.jsp");

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Oferta> ofertas = mOfertaDAO.getOfertas();
                                
                // Obtener la producto para cada oferta.
                Map<Integer, Producto> productoesOfertas = new HashMap<>();
                
                for (Oferta oferta : ofertas)
                {
                    int idProducto = oferta.getIdProducto();
                    
                    if (productoesOfertas.containsKey(idProducto)) 
                    {
                        // Si ya fue obtenida la producto, solo hacer referencia a ella.
                        Producto producto = productoesOfertas.get(idProducto);
                        oferta.setProducto(producto);
                    }
                    else 
                    {
                        // Si la ubicación no se ha obtenido de la BD, obtenerla y 
                        // registrarla en el mapa.
                        Producto producto = mProductoDAO.getProductoPorId(idProducto);
                        
                        if (producto != null)
                        {
                            productoesOfertas.put(idProducto, producto);
                            oferta.setProducto(producto);
                        }
                    }
                }
                
                req.setAttribute("ofertas", ofertas);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/ofertas/lista.jsp");

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
