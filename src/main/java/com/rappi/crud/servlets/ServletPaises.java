package com.rappi.crud.servlets;

import com.rappi.crud.dao.PaisDAO;
import com.rappi.crud.entidades.Pais;
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

@WebServlet(name = "ServletPaises", urlPatterns = {"/app/paises"})
public class ServletPaises extends HttpServlet
{
    @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletPaises.class.getName());
    
    private PaisDAO mPaisDAO;
        
    private static final String VISTA_LISTA = "/app/paises/lista.jsp";
    private static final String VISTA_FORMULARIO = "/app/paises/formulario.jsp";
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mPaisDAO = new PaisDAO(mPoolConexionesDB);
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
        
        String codigoPais = null;
        
        if (parametros.get(PaisDAO.COLUMNA_ID) != null)
        {
            codigoPais = parametros.get(PaisDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = getAccionDesdeParams(parametros);
        
        mostrarVistaConDatos(request, response, codigoPais, accion);
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
        
        String idPais = null;
        
        if (parametros.get(PaisDAO.COLUMNA_ID) != null)
        {
            idPais = parametros.get(PaisDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = getAccionDesdeParams(parametros);
        
        Pais datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Pais.desdeParametros(parametros);

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
                    int idInsertado = mPaisDAO.insertarPais(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mPaisDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    mPaisDAO.eliminar(idPais);
                    break;
            }
            
        } catch (SQLException e)
        {
            mLogger.log(Level.SEVERE, e.getMessage());
            
        } finally 
        {
            mostrarVistaConDatos(request, response, null, Accion.LEER);
        }
    }
    
    private void mostrarVistaConDatos(HttpServletRequest req, HttpServletResponse res, 
            String codigoPais, Accion accion) 
            throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (codigoPais != null || accion.equals(Accion.CREAR))
            {
                if (codigoPais != null)
                {
                    // Obtener un registro especifico de la BD.                               
                    Pais pais = mPaisDAO.getPaisPorId(codigoPais);
                
                    req.setAttribute("pais", pais);
                }

                // Determinar el título 
                String encabezadoVista = Pais.tituloVistaConAccion(accion);
                
                req.setAttribute("encabezadoVista", encabezadoVista);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_FORMULARIO);

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Pais> paises = mPaisDAO.getPaises();
                
                req.setAttribute("paises", paises);
                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_LISTA);

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
        return "Acceso y modificación de países.";
    }

}
