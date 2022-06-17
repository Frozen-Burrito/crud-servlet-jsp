package com.rappi.crud.servlets;

import com.rappi.crud.dao.RestauranteDAO;
import com.rappi.crud.dao.UbicacionDAO;
import com.rappi.crud.entidades.Restaurante;
import com.rappi.crud.entidades.Ubicacion;
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

@WebServlet(name = "ServletRestaurantes", urlPatterns = {"/restaurantes"})
public class ServletRestaurantes extends HttpServlet
{
    @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletColonias.class.getName());
    
    private RestauranteDAO mRestauranteDAO;
    
    private UbicacionDAO mUbicacionDAO;
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mRestauranteDAO = new RestauranteDAO(mPoolConexionesDB);
        mUbicacionDAO = new UbicacionDAO(mPoolConexionesDB);
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
        
        String idRestauranteStr = null;
        
        if (parametros.get(RestauranteDAO.COLUMNA_ID) != null)
        {
            idRestauranteStr = parametros.get(RestauranteDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = getAccionDesdeParams(parametros);
        
        mostrarVistaConDatos(request, response, idRestauranteStr, accion);
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
        
        String idRestauranteStr = null;
        
        if (parametros.get(RestauranteDAO.COLUMNA_ID) != null)
        {
            idRestauranteStr = parametros.get(RestauranteDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = getAccionDesdeParams(parametros);
        
        Restaurante datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Restaurante.desdeParametros(parametros);

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
                    int idInsertado = mRestauranteDAO.insertar(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mRestauranteDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    int id = Integer.parseInt(idRestauranteStr);
                    mRestauranteDAO.eliminar(id);
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
        String idRestauranteStr, Accion accion) 
        throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (idRestauranteStr != null || accion.equals(Accion.CREAR))
            {
                if (idRestauranteStr != null) 
                {
                    int idRestaurante = Integer.parseInt(idRestauranteStr);
                    
                    // Obtener un registro especifico de la BD.                               
                    Restaurante restaurante = mRestauranteDAO.getRestaurantePorID(idRestaurante);
                    
                    req.setAttribute("restaurante", restaurante);
                }
                
                // Obtener entidades relacionadas de la BD.
                List<Ubicacion> ubicaciones = mUbicacionDAO.getUbicaciones();
                
                req.setAttribute("ubicaciones", ubicaciones);
                
                // Determinar el título 
                String encabezadoVista = Restaurante.tituloVistaConAccion(accion);
                
                req.setAttribute("encabezadoVista", encabezadoVista);
                                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/restaurantes/formulario.jsp");

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Restaurante> restaurantes = mRestauranteDAO.getRestaurantes();
                                
                // Obtener la ubicacion para cada usuario.
                Map<Integer, Ubicacion> ubicacionesRestaurantes = new HashMap<>();
                
                for (Restaurante restaurante : restaurantes)
                {
                    int idUbicacion = restaurante.getIdUbicacion();
                    Ubicacion registroUbicacion = ubicacionesRestaurantes.get(idUbicacion);
                    
                    if (registroUbicacion == null)
                    {
                        // Si la ubicación no se ha obtenido de la BD, obtenerla y 
                        // registrarla en el mapa.
                        Ubicacion ubicacion = mUbicacionDAO.getUbicacionPorId(idUbicacion);
                        
                        if (ubicacion != null)
                        {
                            registroUbicacion = ubicacion;
                            ubicacionesRestaurantes.put(idUbicacion, ubicacion);
                        }
                    }
                    
                    restaurante.setUbicacion(registroUbicacion);
                }
                
                req.setAttribute("restaurantes", restaurantes);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/restaurantes/lista.jsp");

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
        Accion accion = Accion.CREAR;
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
