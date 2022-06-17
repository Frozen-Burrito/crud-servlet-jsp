package com.rappi.crud.servlets;

import com.rappi.crud.dao.ColoniaDAO;
import com.rappi.crud.dao.UbicacionDAO;
import com.rappi.crud.entidades.Colonia;
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

@WebServlet(name = "ServletUbicaciones", urlPatterns = {"/app/ubicaciones"})
public class ServletUbicaciones extends HttpServlet
{
    @Resource(name = "jdbc/dataSourcePrincipal")
    private DataSource mPoolConexionesDB;
    
    private static final Logger mLogger = Logger.getLogger(ServletMunicipios.class.getName());
    
    private UbicacionDAO mUbicacionDAO;
    
    private ColoniaDAO mColoniaDAO;
        
    private static final String VISTA_LISTA = "/app/ubicaciones/lista.jsp";
    private static final String VISTA_FORMULARIO = "/app/ubicaciones/formulario.jsp";
    
    @Override
    public void init() throws ServletException
    {
        super.init();

        mUbicacionDAO = new UbicacionDAO(mPoolConexionesDB);
        mColoniaDAO = new ColoniaDAO(mPoolConexionesDB);
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
        
        String idUbicacionStr = null;
        
        if (parametros.get(UbicacionDAO.COLUMNA_ID) != null)
        {
            idUbicacionStr = parametros.get(UbicacionDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Accion.LEER;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        mostrarVistaConDatos(req, res, idUbicacionStr, accion);
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
        
        String idUbicacionStr = null;
        
        if (parametros.get(UbicacionDAO.COLUMNA_ID) != null)
        {
            idUbicacionStr = parametros.get(UbicacionDAO.COLUMNA_ID)[0];
        }
        
        Accion accion = Accion.CREAR;
        String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
        
        if (parametros.get(keyParamAccion) != null) 
        {
            accion = Accion.valueOf(parametros.get(keyParamAccion)[0]);
        }
        
        Ubicacion datosRecibidos = null;
        
        // Obtener datos del formulario si la accion es CREAR o ACTUALIZAR.
        if (!accion.equals(Accion.ELIMINAR))
        {
            try 
            {
                datosRecibidos = Ubicacion.desdeParametros(parametros);

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
                    int idInsertado = mUbicacionDAO.insertar(datosRecibidos);
                    
                    if (idInsertado >= 0) 
                    {
                        System.out.println("Datos insertados, ID = " + idInsertado);
                    }
                    break;
                case ACTUALIZAR:
                    mUbicacionDAO.actualizar(datosRecibidos);
                    break;
                case ELIMINAR:
                    int id = Integer.parseInt(idUbicacionStr);
                    mUbicacionDAO.eliminar(id);
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
            String idUbicacionStr, Accion accion) 
            throws ServletException, IOException
    {
        try
        {
            req.setAttribute("accion", accion);

            if (idUbicacionStr != null || accion.equals(Accion.CREAR))
            {
                if (idUbicacionStr != null) 
                {
                    int id = Integer.parseInt(idUbicacionStr);
                    
                    // Obtener un registro especifico de la BD.                               
                    Ubicacion ubicacion = mUbicacionDAO.getUbicacionPorId(id);
                    
                    req.setAttribute("ubicacion", ubicacion);
                }
                
                // Obtener entidades relacionadas de la BD.
                List<Colonia> colonias = mColoniaDAO.getColonias();
                
                req.setAttribute("colonias", colonias);
                                
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(VISTA_FORMULARIO);
               
                System.out.println(requestDispatcher == null);

                requestDispatcher.forward(req, res);
                
            } else 
            {
                // Obtener todos los registros disponibles.
                List<Ubicacion> ubicaciones = mUbicacionDAO.getUbicaciones();
                
                // Obtener la colonia para cada ubicacion.
                Map<Integer, Colonia> coloniasDeUbicacion = new HashMap<>();
                
                for (Ubicacion ubicacion : ubicaciones)
                {
                    int idColonia = ubicacion.getIdColonia();
                    
                    if (coloniasDeUbicacion.containsKey(idColonia)) 
                    {
                        // Si ya fue obtenida la ubicacion, solo hacer referencia a ella.
                        Colonia colonia = coloniasDeUbicacion.get(idColonia);
                        ubicacion.setColonia(colonia);
                    }
                    else 
                    {
                        // Si la ubicación no se ha obtenido de la BD, obtenerla y 
                        // registrarla en el mapa.
                        Colonia colonia = mColoniaDAO.getColoniaPorId(idColonia);
                        
                        if (colonia != null)
                        {
                            coloniasDeUbicacion.put(idColonia, colonia);
                            ubicacion.setColonia(colonia);
                        }
                    }
                }
                
                req.setAttribute("ubicaciones", ubicaciones);
                
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
        return "Short description";
    }// </editor-fold>

}
