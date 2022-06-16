﻿<%@page import="com.rappi.crud.entidades.Pais"%>
<%@page import="java.util.List"%>
﻿<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.dao.EstadoDAO"%>
<%@ page import="com.rappi.crud.entidades.Estado" %>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title>Estados | Detalles</title>
</head>
<%
    final Estado estado = (Estado) request.getAttribute("estado");
    System.out.println("Estado: " + estado);
    
    final List<Pais> paisesDisponibles = (List<Pais>) request.getAttribute("paises");
    
    for (Pais pais : paisesDisponibles)
    {
        System.out.println("Pais: " + pais);
    }
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);
    
    System.out.println("Accion: " + accionForm);

    String idEstadoStr = request.getParameter(EstadoDAO.COLUMNA_ID);
    
    final boolean esRegistroExistente = !accionForm.equals(Accion.CREAR) && idEstadoStr != null;
    final boolean hayDatos = estado != null;

    int idEstado = -1;
    String tituloPagina;
    
    if (esRegistroExistente && hayDatos) 
    {
        idEstado = Integer.parseInt(idEstadoStr);
        
        if (accionForm.equals(Accion.LEER))
        {
            tituloPagina = "Detalles del " + Estado.NOMBRE_ENTIDAD;
            
        } else 
        {
            tituloPagina = "Edita un " + Estado.NOMBRE_ENTIDAD;
        }
        
        System.out.println("Datos desde form JSP: " + estado.toString());
        
    } else 
    {
        tituloPagina = "Agrega un Nuevo " + Estado.NOMBRE_ENTIDAD;
    }
%>
<body>
    <section class="container">
            
        <div class="row">
          <div class="col-3">
          </div>
            <div class="col">
                
                <h1 class="my-5"><%= tituloPagina %></h1>
                
                <form method="POST" action="${pageContext.request.contextPath}/estados">

                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= EstadoDAO.COLUMNA_ID %>" value="<%= idEstado %>" />
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
                            value="<%= hayDatos ? estado.getNombre(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="nombre-pais" class="form-label">País</label>
                        
                        <select class="form-select" name="<%= EstadoDAO.COLUMNA_CODIGO_PAIS %>" aria-label="Seleccionar pais">
                            <% for (Pais pais : paisesDisponibles) { %>
                                <option 
                                    value="<%= pais.getCodigoPais()%>"
                                    <%= hayDatos && pais.getCodigoPais().equals(estado.getCodigoPais()) ? "selected" : "" %>
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
            </div>
            <div class="col-3">
            </div>
        </div>
    </section>
                
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
