package com.rappi.crud.servlets;

import com.rappi.crud.dao.RestauranteDAO;
import com.rappi.crud.dao.ReviewDAO;
import com.rappi.crud.dao.UsuarioDAO;
import com.rappi.crud.entidades.jpa.Restaurante;
import com.rappi.crud.entidades.jpa.Review;
import com.rappi.crud.entidades.jpa.Usuario;
import java.io.IOException;
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

@WebServlet(name = "ServletReviews", urlPatterns = {"/app/reviews"})
public class ServletReviews extends HttpServlet
{    
    private static final Logger mLogger = Logger.getLogger(ServletColonias.class.getName());
    
    private final ReviewDAO mReviewDAO = new ReviewDAO();
    
    private final UsuarioDAO mUsuarioDAO = new UsuarioDAO();
    private final RestauranteDAO mRestauranteDAO = new RestauranteDAO();
    
    private static final String VISTA_LISTA = "/app/reviews/lista.jsp";
    private static final String VISTA_FORMULARIO = "/app/reviews/formulario.jsp";

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
        Map<String, String[]> parametros = request.getParameterMap();
        
        String idReviewStr = null;
        
        if (parametros.get(RestauranteDAO.COLUMNA_ID) != null)
        {
            idReviewStr = parametros.get(RestauranteDAO.COLUMNA_ID)[0];
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
        
        if (parametros.get(RestauranteDAO.COLUMNA_ID) != null)
        {
            idReviewStr = parametros.get(RestauranteDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
        Review datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Review.desdeParametros(parametros);

            } catch (NullPointerException | NumberFormatException e)
            {
                mLogger.log(Level.SEVERE, null, e);
            }
        }
        
        // Realizar la accion CUD determinada.
        switch (accion) 
        {
            case CREAR:
                mReviewDAO.insertar(datosRecibidos);
                break;
            case ACTUALIZAR:
                mReviewDAO.actualizar(datosRecibidos);
                break;
            case ELIMINAR:
                int id = Integer.parseInt(idReviewStr);
                mReviewDAO.eliminar(id);
                break;
        }
        
        // Redirigir al usuario para mostrar los resultados de la operacion.
        mostrarVistaConDatos(request, response, null, Accion.LEER);
    }
    
    private void mostrarVistaConDatos(HttpServletRequest req, HttpServletResponse res, 
        String idReviewStr, Accion accion) 
        throws ServletException, IOException
    {
        req.setAttribute("accion", accion);

        if (idReviewStr != null || accion.equals(Accion.CREAR))
        {
            if (idReviewStr != null) 
            {
                int idReview = Integer.parseInt(idReviewStr);

                // Obtener un registro especifico de la BD.                               
                Review review = mReviewDAO.getReviewPorID(idReview);

                req.setAttribute("review", review);
            }

            // Obtener entidades relacionadas de la BD.
            List<Usuario> usuarios = mUsuarioDAO.getUsuarios();

            req.setAttribute("usuarios", usuarios);

            List<Restaurante> restaurantes = mRestauranteDAO.getRestaurantes();

            req.setAttribute("restaurantes", restaurantes);

            // Determinar el título 
            String encabezadoVista = Utilidades.tituloVistaConAccion(accion, Review.NOMBRE_ENTIDAD);

            req.setAttribute("encabezadoVista", encabezadoVista);

            // Enviar jsp con resultado. 
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_FORMULARIO);

            requestDispatcher.forward(req, res);

        } else 
        {
            // Obtener todos los registros disponibles.
            List<Review> reviews = mReviewDAO.getReviews();

            req.setAttribute("reviews", reviews);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_LISTA);

            requestDispatcher.forward(req, res);
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
