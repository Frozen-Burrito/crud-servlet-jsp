<%@page import="com.rappi.crud.entidades.jpa.Pais"%>
<%@page import="com.rappi.crud.entidades.jpa.Estado"%>
<%@page import="java.util.List"%>
﻿<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.dao.EstadoDAO"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../includes/head_links.jsp"></jsp:include>
    
    <title><%= Estado.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    String idEstadoStr = request.getParameter(EstadoDAO.COLUMNA_ID);

    final Estado estado = (Estado) request.getAttribute("estado");
    
    final List<Pais> paisesDisponibles = (List<Pais>) request.getAttribute("paises");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);
    
    final boolean hayDatos = estado != null;
    final boolean esSoloLectura = accionForm.equals(Accion.LEER);
    
    // La URL base de todas las peticiones a paises.
    String urlBase = request.getContextPath() + "/app";
    String urlEstados = urlBase + "/estados";
    
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
                            <input type="hidden" name="<%= EstadoDAO.COLUMNA_ID %>" value="<%= idEstadoStr %>" />
                        <% } %>

                        <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                        <div class="mb-3">
                            <label for="codigo-pais" class="form-label">Nombre</label>
                            <input 
                                type="text" 
                                id="codigo-pais" 
                                class="form-control"
                                name="<%= EstadoDAO.COLUMNA_NOMBRE %>" 
                                placeholder="Jalisco"
                                <%= esSoloLectura ? "readonly" : "" %>
                                value="<%= hayDatos ? estado.getNombre(): "" %>" 
                            />
                        </div>

                        <div class="mb-3">
                            <label for="nombre-pais" class="form-label">País</label>

                            <select 
                                class="form-select" 
                                name="<%= EstadoDAO.COLUMNA_CODIGO_PAIS %>" 
                                aria-label="Seleccionar pais"
                                <%= esSoloLectura ? "disabled" : "" %>
                            >
                                <% for (Pais pais : paisesDisponibles) { %>
                                    <option 
                                        value="<%= pais.getCodigoIso3166()%>"
                                        <%= hayDatos && pais.equals(estado.getPais()) ? "selected" : "" %>
                                    >
                                        <%= pais.getNombre() %>
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
                    <h3 class="my-5"> No existe el <%= Estado.NOMBRE_ENTIDAD %> que estás buscando</h3>
                <% } %>
            </div>
            <div class="col-3">
            </div>
        </div>
    </section>
                
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
