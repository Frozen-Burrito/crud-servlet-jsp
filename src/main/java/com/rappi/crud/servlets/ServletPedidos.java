package com.rappi.crud.servlets;

import com.rappi.crud.dao.PedidoDAO;
import com.rappi.crud.entidades.jpa.Pedido;
import com.rappi.crud.entidades.jpa.Restaurante;
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

@WebServlet(name = "ServletPedidos", urlPatterns = {"/app/datos-pedidos"})
public class ServletPedidos extends HttpServlet
{
    private static final Logger mLogger = Logger.getLogger(ServletPedidos.class.getName());
    
    private final PedidoDAO mPedidoDAO = new PedidoDAO();
    
    private static final String VISTA_LISTA = "/app/pedidos/lista.jsp";
    private static final String VISTA_FORMULARIO = "/app/pedidos/formulario.jsp";

    // <editor-fold defaultstate="collapsed" desc="Métodos para manejar peticiones HTTP GET y POST.">
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
        System.out.println("doGet");
        Map<String, String[]> parametros = request.getParameterMap();
        
        String idReviewStr = null;
        
        if (parametros.get(PedidoDAO.COLUMNA_ID) != null)
        {
            idReviewStr = parametros.get(PedidoDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        mostrarVistaConDatos(request, response, idReviewStr, accion);
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
        
        String idReviewStr = null;
        
        if (parametros.get(PedidoDAO.COLUMNA_ID) != null)
        {
            idReviewStr = parametros.get(PedidoDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        Pedido datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Pedido.desdeParametros(parametros);

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
                    int idInsertado = mPedidoDAO.insertar(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mPedidoDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    int id = Integer.parseInt(idReviewStr);
                    mPedidoDAO.eliminar(id);
                    break;
            }
            
        } catch (SQLException e)
        {
            mLogger.log(Level.SEVERE, e.getMessage());
            
        } finally 
        {
            // Redirigir al usuario para mostrar los resultados de la operacion.
            mostrarVistaConDatos(request, response, null, Accion.LEER);
        }
    }
    
    private void mostrarVistaConDatos(HttpServletRequest req, HttpServletResponse res, 
        String idPedidoStr, Accion accion) 
        throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (idPedidoStr != null || accion.equals(Accion.CREAR))
            {
                if (idPedidoStr != null) 
                {
                    int idPedido = Integer.parseInt(idPedidoStr);
                    
                    // Obtener un registro especifico de la BD.                               
                    Pedido pedido = mPedidoDAO.getPedidoPorId(idPedido);
                    
                    req.setAttribute("pedido", pedido);
                }
                
                // Obtener entidades relacionadas de la BD.
                // TODO: Obtener los productos incluidos en el pedido.
                
                // Determinar el título de la vista.
                String encabezadoVista = Utilidades.tituloVistaConAccion(accion, Restaurante.NOMBRE_ENTIDAD);
                
                req.setAttribute("encabezadoVista", encabezadoVista);
                                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_FORMULARIO);

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Pedido> pedidos = mPedidoDAO.getPedidos();
                
                req.setAttribute("pedidos", pedidos);
                
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
        return "Accede y modifica pedidos";
    }// </editor-fold>

}
