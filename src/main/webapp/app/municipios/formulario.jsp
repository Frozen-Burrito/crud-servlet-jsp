<%@page import="com.rappi.crud.dao.MunicipioDAO"%>
<%@page import="com.rappi.crud.entidades.jpa.Estado"%>
<%@page import="com.rappi.crud.entidades.jpa.Municipio"%>
<%@ page import="java.util.List"%>
﻿<%@page import="com.rappi.crud.servlets.Accion"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
     <jsp:include page="../../includes/head_links.jsp"></jsp:include>
     
    <title><%= Municipio.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Municipio municipio = (Municipio) request.getAttribute("municipio");
    
    final List<Estado> estadosDisponibles = (List<Estado>) request.getAttribute("estados");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);

    String idMunicipioStr = request.getParameter(MunicipioDAO.COLUMNA_ID);
    
    final boolean hayDatos = municipio != null;
    final boolean esSoloLectura = accionForm.equals(Accion.LEER);

    // La URL base de todas las peticiones.
    String urlBase = request.getContextPath() + "/app";
    String urlEstados = urlBase + "/datos-municipios";
    
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
                
                <form method="POST" action="<%= urlEstados %>">

                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= MunicipioDAO.COLUMNA_ID %>" value="<%= idMunicipioStr %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <div class="mb-3">
                        <label for="codigo-pais" class="form-label">Nombre</label>
                        <input 
                            type="text"  
                            class="form-control"
                            name="<%= MunicipioDAO.COLUMNA_NOMBRE %>" 
                            placeholder="Guadalajara" 
                            <%= esSoloLectura ? "readonly" : "" %>
                            value="<%= hayDatos ? municipio.getNombre(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= MunicipioDAO.COLUMNA_ID_ESTADO %>" class="form-label">Estado</label>
                        
                        <select 
                            class="form-select" 
                            name="<%= MunicipioDAO.COLUMNA_ID_ESTADO %>" 
                            <%= esSoloLectura ? "disabled" : "" %>
                            aria-label="Seleccionar estado"
                        >
                            <% for (Estado estado : estadosDisponibles) { %>
                                <option 
                                    value="<%= estado.getId()%>"
                                    <%= hayDatos && estado.equals(municipio.getEstado()) ? "selected" : "" %>
                                >
                                    <%= estado.getNombre() %>
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
                    <h3 class="my-5"> No existe el <%= Municipio.NOMBRE_ENTIDAD %> que estás buscando</h3>
                <% } %>
            </div>
            <div class="col-3">
            </div>
        </div>
    </section>
                
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
