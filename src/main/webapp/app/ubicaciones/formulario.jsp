﻿<%@ page import="java.util.List"%>
﻿<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.dao.UbicacionDAO"%>
<%@page import="com.rappi.crud.entidades.jpa.Colonia"%>
<%@page import="com.rappi.crud.entidades.jpa.Ubicacion"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../includes/head_links.jsp"></jsp:include>
    
    <title><%= Ubicacion.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Ubicacion ubicacion = (Ubicacion) request.getAttribute("ubicacion");
    
    final List<Colonia> colonias = (List<Colonia>) request.getAttribute("colonias");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);

    String idUbicacionStr = request.getParameter(UbicacionDAO.COLUMNA_ID);
    final int idUbicacion = idUbicacionStr != null && ubicacion != null 
            ? Integer.parseInt(idUbicacionStr) 
            : -1;
    
    // Booleans de conveniencia para renderizado condicional del html.
    final boolean hayDatos = ubicacion != null && idUbicacion >= 0;
    final boolean esSoloLectura = accionForm.equals(Accion.LEER);
    
    // La URL base de todas las peticiones al servlet de ubicaciones.
    String urlBase = request.getContextPath() + "/app";
    String urlUbicaciones = urlBase + "/ubicaciones";
    
    // El título para el formulario.
    final String encabezadoVista = (String) request.getAttribute("encabezadoVista");
%>
<body>
    <section class="container">
            
        <div class="row">
          <div class="col-3">
          </div>
            <div class="col">
                
                <% if (hayDatos || accionForm.equals(Accion.CREAR)) { %>
                    <h1 class="my-5"><%= encabezadoVista %></h1>

                    <form method="POST" action="<%= urlUbicaciones %>">

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
                                <%= esSoloLectura ? "readonly" : "" %>
                                value="<%= hayDatos ? ubicacion.getCalle(): "" %>" 
                            />
                        </div>

                        <div class="mb-3">
                            <label for="<%= UbicacionDAO.COLUMNA_NUM_EXTERIOR %>" class="form-label">Número Exterior</label>
                            <input 
                                type="text"  
                                class="form-control"
                                name="<%= UbicacionDAO.COLUMNA_NUM_EXTERIOR %>" 
                                placeholder="#" 
                                <%= esSoloLectura ? "readonly" : "" %>
                                value="<%= hayDatos ? ubicacion.getNumeroExterior(): "" %>" 
                            />
                        </div>

                        <div class="mb-3">
                            <label for="<%= UbicacionDAO.COLUMNA_NUM_INTERIOR %>" class="form-label">Número Interior</label>
                            <input 
                                type="text"  
                                class="form-control"
                                name="<%= UbicacionDAO.COLUMNA_NUM_INTERIOR %>" 
                                placeholder="#" 
                                <%= esSoloLectura ? "readonly" : "" %>
                                value="<%= hayDatos ? ubicacion.getNumeroInterior(): "" %>" 
                            />
                        </div>

                        <div class="mb-3">
                            <label for="<%= UbicacionDAO.COLUMNA_ID_COLONIA %>" class="form-label">Colonia</label>

                            <select 
                                class="form-select" 
                                name="<%= UbicacionDAO.COLUMNA_ID_COLONIA %>" 
                                <%= esSoloLectura ? "disabled" : "" %>
                                aria-label="Seleccionar colonia"
                            >
                                <% for (Colonia colonia : colonias) { %>
                                    <option 
                                        value="<%= colonia.getId()%>"
                                        <%= hayDatos && colonia.getId() == ubicacion.getColonia().getId() ? "selected" : "" %>
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
                <% } else { %>
                    <h1 class="my-5">404 - No encontrado</h1>
                    <h3 class="my-5"> No existe la <%= Ubicacion.NOMBRE_ENTIDAD %> que estás buscando</h3>
                <% } %>
            </div>
            <div class="col-3">
            </div>
        </div>
    </section>
                
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
