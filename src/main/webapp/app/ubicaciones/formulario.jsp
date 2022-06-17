﻿﻿<%@page import="com.rappi.crud.dao.UbicacionDAO"%>
<%@page import="com.rappi.crud.entidades.Colonia"%>
<%@page import="com.rappi.crud.entidades.Ubicacion"%>
﻿﻿<%@page import="com.rappi.crud.dao.MunicipioDAO"%>
<%@page import="com.rappi.crud.entidades.Municipio"%>
﻿<%@ page import="com.rappi.crud.entidades.Pais"%>
<%@ page import="java.util.List"%>
﻿<%@page import="com.rappi.crud.servlets.Accion"%>
<%@ page import="com.rappi.crud.dao.EstadoDAO"%>
<%@ page import="com.rappi.crud.entidades.Estado" %>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Ubicacion.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Ubicacion ubicacion = (Ubicacion) request.getAttribute("ubicacion");
    System.out.println("Ubicacion: " + ubicacion);
    
    final List<Colonia> colonias = (List<Colonia>) request.getAttribute("colonias");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);
    
    System.out.println("Accion: " + accionForm);

    String idUbicacionStr = request.getParameter(UbicacionDAO.COLUMNA_ID);
    
    // La URL base de todas las peticiones al servlet de ubicaciones.
    String urlBase = request.getContextPath() + "/app/ubicaciones";
    
    final boolean esRegistroExistente = !accionForm.equals(Accion.CREAR) && idUbicacionStr != null;
    final boolean hayDatos = ubicacion != null;

    int idUbicacion = -1;
    String tituloPagina;
    
    // Convertir ID del municipio a un int y determinar el titulo del formulario.
    if (esRegistroExistente && hayDatos) 
    {
        idUbicacion = Integer.parseInt(idUbicacionStr);
        
        if (accionForm.equals(Accion.LEER))
        {
            // Ver
            tituloPagina = "Detalles del " + Ubicacion.NOMBRE_ENTIDAD;
            
        } else 
        {
            // Actualizar
            tituloPagina = "Edita un " + Ubicacion.NOMBRE_ENTIDAD;
        }
        
    } else 
    {
        // Crear
        tituloPagina = "Agrega un Nuevo " + Ubicacion.NOMBRE_ENTIDAD;
    }
%>
<body>
    <section class="container">
            
        <div class="row">
          <div class="col-3">
          </div>
            <div class="col">
                
                <h1 class="my-5"><%= tituloPagina %></h1>
                
                <form method="POST" action="<%= urlBase %>">

                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= UbicacionDAO.COLUMNA_ID %>" value="<%= idUbicacion %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <div class="mb-3">
                        <label for="<%= UbicacionDAO.COLUMNA_CALLE %>" class="form-label">Nombre de la Calle</label>
                        <input 
                            type="text"  
                            class="form-control"
                            name="<%= UbicacionDAO.COLUMNA_CALLE %>" 
                            value="<%= hayDatos ? ubicacion.getNombreCalle(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= UbicacionDAO.COLUMNA_NUM_EXTERIOR %>" class="form-label">Número Exterior</label>
                        <input 
                            type="text"  
                            class="form-control"
                            name="<%= UbicacionDAO.COLUMNA_NUM_EXTERIOR %>" 
                            placeholder="#" 
                            value="<%= hayDatos ? ubicacion.getNumExterior(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= UbicacionDAO.COLUMNA_NUM_INTERIOR %>" class="form-label">Número Interior</label>
                        <input 
                            type="text"  
                            class="form-control"
                            name="<%= UbicacionDAO.COLUMNA_NUM_INTERIOR %>" 
                            placeholder="#" 
                            value="<%= hayDatos ? ubicacion.getNumInterior(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= UbicacionDAO.COLUMNA_ID_COLONIA %>" class="form-label">Colonia</label>
                        
                        <select class="form-select" name="<%= UbicacionDAO.COLUMNA_ID_COLONIA %>" aria-label="Seleccionar colonia">
                            <% for (Colonia colonia : colonias) { %>
                                <option 
                                    value="<%= colonia.getId()%>"
                                    <%= hayDatos && colonia.getId() == ubicacion.getIdColonia()? "selected" : "" %>
                                >
                                    <%= colonia.getNombre() %>
                                </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <% if (accionForm.equals(Accion.CREAR)) { %>
                            <input type="submit" class="btn btn-primary" value="Agregar" />

                        <% } else if (accionForm.equals(Accion.ACTUALIZAR)) { %>
                            <input type="submit" class="btn btn-warning" value="Guardar Cambios" />
                        <% } %>
                    </div>

                </form>
            </div>
            <div class="col-3">
            </div>
        </div>
    </section>
                
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
