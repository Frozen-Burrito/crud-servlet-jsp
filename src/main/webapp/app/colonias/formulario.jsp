<%@page import="java.util.List"%>
﻿<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.entidades.jpa.Municipio"%>
<%@page import="com.rappi.crud.entidades.jpa.Colonia"%>
﻿﻿﻿<%@page import="com.rappi.crud.dao.ColoniaDAO"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../includes/head_links.jsp"></jsp:include>
    
    <title><%= Colonia.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Colonia colonia = (Colonia) request.getAttribute("colonia");
    
    final List<Municipio> municipios = (List<Municipio>) request.getAttribute("municipios");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);

    String idColoniaStr = request.getParameter(ColoniaDAO.COLUMNA_ID);
    
    final boolean hayDatos = colonia != null;
    final boolean esSoloLectura = accionForm.equals(Accion.LEER);
    
    String urlBase = request.getContextPath() + "/app";
    String urlColonias = urlBase + "/datos-colonias";
       
    // El título para el formulario.
    final String encabezadoVista = (String) request.getAttribute("encabezadoVista");
%>
<body>
    <section class="container">
            
        <div class="row">
          <div class="col-3">
          </div>
            <div class="col">
                
                <h1 class="my-5"><%= encabezadoVista %></h1>
                
                <form method="POST" action="<%= urlColonias %>">

                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= ColoniaDAO.COLUMNA_ID %>" value="<%= idColoniaStr %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre de la Colonia</label>
                        <input 
                            type="text" 
                            id="nombre" 
                            class="form-control"
                            name="<%= ColoniaDAO.COLUMNA_NOMBRE %>" 
                            placeholder="Providencia" 
                            <%= esSoloLectura ? "readonly" : "" %>
                            value="<%= hayDatos ? colonia.getNombre(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= ColoniaDAO.COLUMNA_CODIGO_POSTAL %>" class="form-label">Código Postal</label>
                        <input 
                            type="text" 
                            id="<%= ColoniaDAO.COLUMNA_CODIGO_POSTAL %>" 
                            class="form-control"
                            name="<%= ColoniaDAO.COLUMNA_CODIGO_POSTAL %>" 
                            placeholder="45000" 
                            <%= esSoloLectura ? "readonly" : "" %>
                            value="<%= hayDatos ? colonia.getCodigoPostal(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= ColoniaDAO.COLUMNA_ID_MUNICIPIO %>" class="form-label">Municipio</label>
                        
                        <select 
                            class="form-select" 
                            name="<%= ColoniaDAO.COLUMNA_ID_MUNICIPIO %>" 
                            aria-label="Seleccionar municipio"
                            <%= esSoloLectura ? "disabled" : "" %>
                        >
                            <% for (Municipio municipio : municipios) { %>
                                <option 
                                    value="<%= municipio.getId() %>"
                                    <%= hayDatos && municipio.equals(colonia.getMunicipio()) ? "selected" : "" %>
                                >
                                    <%= municipio.getNombre() %>
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
