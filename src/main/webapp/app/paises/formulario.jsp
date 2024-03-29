﻿<%@page import="com.rappi.crud.entidades.jpa.Pais"%>
﻿﻿﻿﻿<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.dao.PaisDAO"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../includes/head_links.jsp"></jsp:include>

    <title>Países | Detalles</title>
</head>
<%
    final Pais pais = (Pais) request.getAttribute("pais");

    String codigoPais = request.getParameter(PaisDAO.COLUMNA_ID);

    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);
    
    final boolean hayDatos = pais != null;
    final boolean esSoloLectura = accionForm.equals(Accion.LEER);
    
    // La URL base de todas las peticiones a paises.
    String urlBase = request.getContextPath() + "/app";
    String urlPaises = urlBase + "/paises";
    
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
                
                <form method="POST" action="<%= urlPaises %>">

                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= PaisDAO.COLUMNA_ID %>" value="<%= codigoPais %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <div class="mb-3">
                        <label for="codigo-pais" class="form-label">Código</label>
                        <input 
                            type="text" 
                            id="codigo-pais" 
                            class="form-control"
                            name="<%= PaisDAO.COLUMNA_ID %>" 
                            placeholder="Código ISO 3166 de tres letras" 
                            <%= esSoloLectura ? "readonly" : "" %>
                            value="<%= hayDatos ? pais.getCodigoIso3166(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="nombre-pais" class="form-label">Nombre</label>
                        <input
                            type="text" 
                            id="nombre-pais" 
                            class="form-control"
                            name="<%= PaisDAO.COLUMNA_NOMBRE %>" 
                            placeholder="Nombre del país" 
                            <%= esSoloLectura ? "readonly" : "" %>
                            value="<%= hayDatos ? pais.getNombre() : "" %>" 
                        />
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
