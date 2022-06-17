package com.rappi.crud.servlets;

import com.rappi.crud.dao.RestauranteDAO;
import com.rappi.crud.dao.ReviewDAO;
import com.rappi.crud.dao.UbicacionDAO;
import com.rappi.crud.dao.UsuarioDAO;
import com.rappi.crud.entidades.Restaurante;
import com.rappi.crud.entidades.Review;
import com.rappi.crud.entidades.Ubicacion;
import com.rappi.crud.entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet(name = "ServletReviews", urlPatterns = {"/reviews"})
public class ServletReviews extends HttpServlet
{
    @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletColonias.class.getName());
    
    private ReviewDAO mReviewDAO;
    
    private RestauranteDAO mRestauranteDAO;
    
    private UsuarioDAO mUsuarioDAO;
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mReviewDAO = new ReviewDAO(mPoolConexionesDB);
        mRestauranteDAO = new RestauranteDAO(mPoolConexionesDB);
        mUsuarioDAO = new UsuarioDAO(mPoolConexionesDB);
    }

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
        
        Accion accion = getAccionDesdeParams(parametros);
        
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
        
        Accion accion = getAccionDesdeParams(parametros);
        
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
                accion = Accion.ELIMINAR;
            }
        }
        
        // Realizar la accion CUD determinada.
        try 
        {
            switch (accion) 
            {
                case CREAR:
                    int idInsertado = mReviewDAO.insertar(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mReviewDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    int id = Integer.parseInt(idReviewStr);
                    mReviewDAO.eliminar(id);
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
        String idReviewStr, Accion accion) 
        throws ServletException, IOException
    {
        try
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
                String encabezadoVista = Restaurante.tituloVistaConAccion(accion);
                
                req.setAttribute("encabezadoVista", encabezadoVista);
                                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/reviews/formulario.jsp");

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Review> reviews = mReviewDAO.getReviews();
                                
                // Obtener el restaurante para cada review.
                Map<Integer, Restaurante> restaurantesDeReviews = new HashMap<>();
                
                // Obtener el usuario para cada review.
                Map<String, Usuario> autoresDeReviews = new HashMap<>();
                
                for (Review review : reviews)
                {
                    int idRestaurante = review.getIdRestaurante();
                    Restaurante registroRestaurante = restaurantesDeReviews.get(idRestaurante);
                    
                    if (registroRestaurante == null)
                    {
                        // Si el restaurante no se ha obtenido de la BD, obtenerlo y 
                        // registrarla en el mapa.
                        Restaurante restaurante = mRestauranteDAO.getRestaurantePorID(idRestaurante);
                        
                        if (restaurante != null)
                        {
                            registroRestaurante = restaurante;
                            restaurantesDeReviews.put(idRestaurante, restaurante);
                        }
                    }
                    
                    review.setRestaurante(registroRestaurante);
                    
                    // Obtener el autor de cada review.
                    String usernameAutor = review.getNombreUsuarioAutor();
                    Usuario registroAutor = autoresDeReviews.get(usernameAutor);
                    
                    if (registroAutor == null)
                    {
                        // Si el restaurante no se ha obtenido de la BD, obtenerlo y 
                        // registrarla en el mapa.
                        Usuario autor = mUsuarioDAO.getUsuarioPorID(usernameAutor);
                        
                        if (autor != null)
                        {
                            registroAutor = autor;
                            autoresDeReviews.put(usernameAutor, autor);
                        }
                    }
                    
                    review.setUsuarioAutor(registroAutor);
                }
                
                req.setAttribute("reviews", reviews);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/reviews/lista.jsp");

                requestDispatcher.forward(req, res);
            }
            
        } catch (SQLException e)
        {
            mLogger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
     
    /**
     * Obtiene el valor de un parámetro con un tipo de acción, o null si el 
     * valor no existe.
     * 
     * @param nombreParam La llave del parámetro de la query.
     * @param parametros El mapa de parámetros del query.
     * @return La acción especificada en el query.
     */
    private Accion getAccionDesdeParams(Map<String, String[]> parametros)
    {
        Accion accion = Accion.LEER;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        return accion;
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
