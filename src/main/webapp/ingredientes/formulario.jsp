﻿﻿﻿﻿﻿<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.dao.IngredienteDAO"%>
<%@page import="com.rappi.crud.entidades.Ingrediente"%>
<%@ page import="com.rappi.crud.entidades.Datos" %>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title> <%= Ingrediente.NOMBRE_ENTIDAD %>| Detalles</title>
</head>
<%
    final Ingrediente ingrediente = (Ingrediente) request.getAttribute("ingrediente");

    String codigoIngrediente = request.getParameter(IngredienteDAO.COLUMNA_ID);
    System.out.println(codigoIngrediente);

    final boolean hayDatos = ingrediente != null;
    
    String parametroAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(parametroAccion);
    
    String tituloPagina = accionForm.equals(Accion.ACTUALIZAR)
                ? "Edita un " + Ingrediente.NOMBRE_ENTIDAD
                : "Agrega un Nuevo " + Ingrediente.NOMBRE_ENTIDAD;

    // La URL base de todas las peticiones al servlet de ubicaciones.
    final String urlBase = request.getContextPath() + "/ingredientes";
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
                        <input type="hidden" name="<%= IngredienteDAO.COLUMNA_ID %>" value="<%= codigoIngrediente %>" />
                    <% } %>

                    <input type="hidden" name="<%= parametroAccion %>" value="<%= accionForm.toString() %>"/>

                    <div class="mb-3">
                        <label for="<%= IngredienteDAO.COLUMNA_NOMBRE %>"  class="form-label">Nombre</label>
                        <input
                            type="text" 
                            id="nombre-ingrediente" 
                            class="form-control"
                            name="<%= IngredienteDAO.COLUMNA_NOMBRE %>" 
                            placeholder="Nombre del ingrediente" 
                            value="<%= hayDatos ? ingrediente.getNombre() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= IngredienteDAO.COLUMNA_ESVEGANO %>"  class="form-label">¿Es vegano?</label>
                        <select class="form-select" name="<%= IngredienteDAO.COLUMNA_ESVEGANO %>" aria-label="¿Es vegano?">
                                <option 
                                    value=<%= true %>
                                    <%= hayDatos && ingrediente.getEsVegano() == true ? "selected": "" %>>
                                    <%= "Sí" %>
                                </option>
                                
                                 <option 
                                    value=<%= false %>
                                    <%= hayDatos && ingrediente.getEsVegano() == false ? "selected": "" %>>
                                    <%=  "No" %>
                                </option>   
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label for="<%= IngredienteDAO.COLUMNA_ESALERGICO %>"  class="form-label">¿Es alergenico?</label>
                        <select class="form-select" name="<%= IngredienteDAO.COLUMNA_ESALERGICO %>" aria-label="¿Es alergenico?">
                                <option 
                                    value=<%= true %>
                                    <%= hayDatos && ingrediente.getEsAlergico() == true ? "selected": "" %>>
                                    <%= "Sí" %>
                                </option>
                                
                                 <option 
                                    value=<%= false %>
                                    <%= hayDatos && ingrediente.getEsAlergico() == false ? "selected": "" %>>
                                    <%=  "No" %>
                                </option>   
                        </select>
                    </div>
                        
                    <div class="mb-3">
                       <label for="<%= IngredienteDAO.COLUMNA_TIENEGLUTEN %>"  class="form-label">¿Tiene gluten?</label>
                       <select class="form-select" name="<%= IngredienteDAO.COLUMNA_TIENEGLUTEN %>" aria-label="¿Tiene gluten?">
                                <option 
                                    value=<%= true %>
                                    <%= hayDatos && ingrediente.getTieneGluten() == true ? "selected": "" %>>
                                    <%= "Sí" %>
                                </option>
                                
                                 <option 
                                    value=<%= false %>
                                    <%= hayDatos && ingrediente.getTieneGluten() == false ? "selected": "" %>>
                                    <%=  "No" %>
                                </option>   
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
