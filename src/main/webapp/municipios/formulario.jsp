<%@page import="com.rappi.crud.dao.MunicipioDAO"%>
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

    <title><%= Municipio.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Municipio municipio = (Municipio) request.getAttribute("municipio");
    
    final List<Estado> estadosDisponibles = (List<Estado>) request.getAttribute("estados");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);
    
    System.out.println("Accion: " + accionForm);

    String idMunicipioStr = request.getParameter(MunicipioDAO.COLUMNA_ID);
    
    final boolean esRegistroExistente = !accionForm.equals(Accion.CREAR) && idMunicipioStr != null;
    final boolean hayDatos = municipio != null;

    int idMunicipio = -1;
    String tituloPagina;
    
    if (esRegistroExistente && hayDatos) 
    {
        idMunicipio = Integer.parseInt(idMunicipioStr);
        
        if (accionForm.equals(Accion.LEER))
        {
            tituloPagina = "Detalles del " + Municipio.NOMBRE_ENTIDAD;
            
        } else 
        {
            tituloPagina = "Edita un " + Municipio.NOMBRE_ENTIDAD;
        }
        
    } else 
    {
        tituloPagina = "Agrega un Nuevo " + Municipio.NOMBRE_ENTIDAD;
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
                        <input type="hidden" name="<%= MunicipioDAO.COLUMNA_ID %>" value="<%= idMunicipio %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <div class="mb-3">
                        <label for="codigo-pais" class="form-label">Nombre</label>
                        <input 
                            type="text"  
                            class="form-control"
                            name="<%= MunicipioDAO.COLUMNA_NOMBRE %>" 
                            placeholder="Guadalajara" 
                            value="<%= hayDatos ? municipio.getNombre(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= MunicipioDAO.COLUMNA_ID_ESTADO %>" class="form-label">Estado</label>
                        
                        <select class="form-select" name="<%= MunicipioDAO.COLUMNA_ID_ESTADO %>" aria-label="Seleccionar estado">
                            <% for (Estado estado : estadosDisponibles) { %>
                                <option 
                                    value="<%= estado.getId()%>"
                                    <%= hayDatos && estado.getId() == municipio.getIdEstado() ? "selected" : "" %>
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
            </div>
            <div class="col-3">
            </div>
        </div>
    </section>
                
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
