package com.rappi.crud.servlets;

import com.rappi.crud.dao.UsuarioDAO;
import com.rappi.crud.entidades.Usuario;
import java.io.IOException;
import java.sql.SQLException;
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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet(name = "ServletAutenticacion", urlPatterns = {"/autenticacion"})
public class ServletAutenticacion extends HttpServlet
{
    @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletColonias.class.getName());
    
    private UsuarioDAO mUsuarioDAO;
    
    private static final String VISTA_INICIO_SESION = "/autenticacion/inicio-sesion.jsp";
    private static final String VISTA_CREAR_CUENTA = "/autenticacion/crear-cuenta.jsp";
    private static final String VISTA_AUTENTICADA = "/app/index.jsp";
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mUsuarioDAO = new UsuarioDAO(mPoolConexionesDB);
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        AccionAutenticacion accion = getAccionAuthDesdeParams(request.getParameterMap());
        
        RequestDispatcher requestDispatcher;
        
        switch(accion)
        {
            case CREAR_CUENTA:
                requestDispatcher = request.getRequestDispatcher(VISTA_CREAR_CUENTA);
                break;
            case CERRAR_SESION:
                HttpSession session = request.getSession();
        
                session.invalidate();

                requestDispatcher = request.getRequestDispatcher(VISTA_INICIO_SESION);
                break;
            case INICIAR_SESION:
            default:
                requestDispatcher = request.getRequestDispatcher(VISTA_INICIO_SESION);
                break;
        }
        
        requestDispatcher.forward(request, response);
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
        AccionAutenticacion accion = getAccionAuthDesdeParams(request.getParameterMap());
        
        switch (accion)
        {
            case INICIAR_SESION:
                iniciarSesion(request, response);
                break;
            case CREAR_CUENTA:
                crearCuenta(request, response);
                break;
        }
    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String nombreUsuario = request.getParameter(UsuarioDAO.COLUMNA_ID);
        String password = request.getParameter(UsuarioDAO.COLUMNA_PASSWORD);
        
        HttpSession httpSession = request.getSession();
        RequestDispatcher dispatcher = request.getRequestDispatcher(VISTA_INICIO_SESION);

        try 
        {
            boolean credCorrectas = mUsuarioDAO.iniciarSesion(nombreUsuario, password);
            
            if (credCorrectas)
            {
                httpSession.setAttribute(UsuarioDAO.COLUMNA_ID, nombreUsuario);
                
                dispatcher = request.getRequestDispatcher(VISTA_AUTENTICADA);
            }
            
        } catch (SQLException ex) 
        {
            mLogger.log(Level.SEVERE, null, ex);
        }
        
        dispatcher.forward(request, response);
    }
    
    private void crearCuenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String nombreUsuario = request.getParameter(UsuarioDAO.COLUMNA_ID);
        String password = request.getParameter(UsuarioDAO.COLUMNA_PASSWORD);
        String email = request.getParameter(UsuarioDAO.COLUMNA_EMAIL);
        String numeroTelefonico = request.getParameter(UsuarioDAO.COLUMNA_NUM_TELEFONICO);
        
        HttpSession httpSession = request.getSession();
        RequestDispatcher dispatcher = request.getRequestDispatcher(VISTA_CREAR_CUENTA);
        
        try 
        {
            Usuario nuevoUsuario = new Usuario(
                nombreUsuario,
                email,
                TipoDeUsuario.CLIENTE,
                password,
                numeroTelefonico,
                3
            );
            
            System.out.println("Nueva cuenta de usuario: " + nuevoUsuario);
            
            int filasModificadas = mUsuarioDAO.insertar(nuevoUsuario);
            
            System.out.println("Filas modificadas: " + filasModificadas);
            
            if (filasModificadas > 0)
            {
                httpSession.setAttribute(UsuarioDAO.COLUMNA_ID, nombreUsuario);
                
                dispatcher = request.getRequestDispatcher(VISTA_AUTENTICADA);
            }
            
        } catch (SQLException ex) 
        {
            mLogger.log(Level.SEVERE, null, ex);
        }
        
        dispatcher.forward(request, response);
    }
    
    private AccionAutenticacion getAccionAuthDesdeParams(Map<String, String[]> parametros)
    {
        AccionAutenticacion accion = AccionAutenticacion.INICIAR_SESION;
        final String llaveParamAccion = AccionAutenticacion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(llaveParamAccion) != null) 
        {
            accion = AccionAutenticacion.valueOf(parametros.get(llaveParamAccion)[0]);
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
