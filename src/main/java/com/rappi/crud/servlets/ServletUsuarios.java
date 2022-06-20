package com.rappi.crud.servlets;

import com.rappi.crud.dao.UbicacionDAO;
import com.rappi.crud.dao.UsuarioDAO;
import com.rappi.crud.entidades.jpa.Ubicacion;
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

@WebServlet(name = "ServletUsuarios", urlPatterns = {"/app/usuarios"})
public class ServletUsuarios extends HttpServlet
{    
    private static final Logger mLogger = Logger.getLogger(ServletColonias.class.getName());
    
    private final UsuarioDAO mUsuarioDAO = new UsuarioDAO();
    
    private final UbicacionDAO mUbicacionDAO = new UbicacionDAO();
        
    private static final String VISTA_LISTA = "/app/usuarios/lista.jsp";
    private static final String VISTA_FORMULARIO = "/app/usuarios/formulario.jsp";

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
        
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
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
        Accion accion = Utilidades.getAccionDesdeParams(parametros);
        
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
            }
        }
        
        // Realizar la accion CUD determinada.
        switch (accion) 
        {
            case CREAR:
                mUsuarioDAO.insertar(datosRecibidos);
                break;
            case ACTUALIZAR:
                mUsuarioDAO.actualizar(datosRecibidos);
                break;
            case ELIMINAR:
                mUsuarioDAO.eliminar(nombreDeUsuario);
                break;
        }
        
        // Redirigir al usuario para mostrar los resultados de la operacion.
        mostrarVistaConDatos(request, response, null, Accion.LEER);
    }
    
    private void mostrarVistaConDatos(HttpServletRequest req, HttpServletResponse res, 
        String idUsuarioStr, Accion accion) 
        throws ServletException, IOException
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

            // Determinar el título.
            String encabezadoVista = Utilidades.tituloVistaConAccion(accion, Usuario.NOMBRE_ENTIDAD);

            req.setAttribute("encabezadoVista", encabezadoVista);

            // Enviar jsp con resultado.                
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_FORMULARIO);

            requestDispatcher.forward(req, res);

        } else 
        {
            // Obtener todos los registros disponibles.
            List<Usuario> usuarios = mUsuarioDAO.getUsuarios();

            req.setAttribute("usuarios", usuarios);

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
        return "Acceso y modificaciones a usuarios";
    }
}
