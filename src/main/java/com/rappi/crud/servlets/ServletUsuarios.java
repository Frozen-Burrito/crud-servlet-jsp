package com.rappi.crud.servlets;

import com.rappi.crud.dao.UbicacionDAO;
import com.rappi.crud.dao.UsuarioDAO;
import com.rappi.crud.entidades.Ubicacion;
import com.rappi.crud.entidades.Usuario;
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

@WebServlet(name = "ServletUsuarios", urlPatterns = {"/usuarios"})
public class ServletUsuarios extends HttpServlet
{
    @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletColonias.class.getName());
    
    private UsuarioDAO mUsuarioDAO;
    
    private UbicacionDAO mUbicacionDAO;
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mUsuarioDAO = new UsuarioDAO(mPoolConexionesDB);
        mUbicacionDAO = new UbicacionDAO(mPoolConexionesDB);
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
        
        String nombreDeUsuario = null;
        
        if (parametros.get(UsuarioDAO.COLUMNA_ID) != null)
        {
            nombreDeUsuario = parametros.get(UsuarioDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Accion.LEER;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        mostrarVistaConDatos(request, response, nombreDeUsuario, accion);
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
        
        String nombreDeUsuario = null;
        
        if (parametros.get(UsuarioDAO.COLUMNA_ID) != null)
        {
            nombreDeUsuario = parametros.get(UsuarioDAO.COLUMNA_ID)[0];
        }
        
        // Obtener el tipo de operación CRUD que va a realizarse.
        Accion accion = Accion.CREAR;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        Usuario datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Usuario.desdeParametros(parametros);

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
                    int idInsertado = mUsuarioDAO.insertar(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mUsuarioDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    int id = Integer.parseInt(nombreDeUsuario);
                    mUsuarioDAO.eliminar(id);
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
        String idUsuarioStr, Accion accion) 
        throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (idUsuarioStr != null || accion.equals(Accion.CREAR))
            {
                if (idUsuarioStr != null) 
                {
                    // Obtener un registro especifico de la BD.                               
                    Usuario usuario = mUsuarioDAO.getUsuarioPorID(idUsuarioStr);
                    
                    req.setAttribute("usuario", usuario);
                }
                
                // Obtener entidades relacionadas de la BD.
                List<Ubicacion> ubicaciones = mUbicacionDAO.getUbicaciones();
                
                req.setAttribute("ubicaciones", ubicaciones);
                
                // Determinar el título 
                String encabezadoVista = Usuario.tituloVistaConAccion(accion);
                
                req.setAttribute("encabezadoVista", encabezadoVista);
                                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/usuarios/formulario.jsp");

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Usuario> usuarios = mUsuarioDAO.getUsuarios();
                                
                // Obtener la ubicacion para cada usuario.
                Map<Integer, Ubicacion> ubicacionesUsuarios = new HashMap<>();
                
                for (Usuario usuario : usuarios)
                {
                    int idUbicacion = usuario.getIdUbicacion();
                    
                    if (ubicacionesUsuarios.containsKey(idUbicacion)) 
                    {
                        // Si ya fue obtenida la ubicacion, solo hacer referencia a ella.
                        Ubicacion ubicacion = ubicacionesUsuarios.get(idUbicacion);
                        usuario.setUbicacion(ubicacion);
                    }
                    else 
                    {
                        // Si la ubicación no se ha obtenido de la BD, obtenerla y 
                        // registrarla en el mapa.
                        Ubicacion ubicacion = mUbicacionDAO.getUbicacionPorId(idUbicacion);
                        
                        if (ubicacion != null)
                        {
                            ubicacionesUsuarios.put(idUbicacion, ubicacion);
                            usuario.setUbicacion(ubicacion);
                        }
                    }
                }
                
                req.setAttribute("usuarios", usuarios);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/usuarios/lista.jsp");

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
