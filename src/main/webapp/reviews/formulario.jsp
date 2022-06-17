<%@page import="com.rappi.crud.dao.ReviewDAO"%>
<%@page import="com.rappi.crud.entidades.Usuario"%>
<%@page import="com.rappi.crud.entidades.Review"%>
﻿<%@page import="com.rappi.crud.entidades.TipoDeCocina"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="java.util.List"%>
<%@page import="com.rappi.crud.entidades.Ubicacion"%>
<%@page import="com.rappi.crud.entidades.Restaurante"%>
<%@page import="com.rappi.crud.dao.RestauranteDAO"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Review.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Review review = (Review) request.getAttribute("review");
    final boolean hayDatos = review != null;
    
    final List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    final List<Restaurante> restaurantes = (List<Restaurante>) request.getAttribute("restaurantes");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);
    
    // El título para el formulario.
    final String encabezadoVista = (String) request.getAttribute("encabezadoVista");
    
    // La URL base de todas las peticiones al servlet de ubicaciones.
    final String urlBase = request.getContextPath() + "/reviews";
%>
<body>
    <section class="container">
            
        <div class="row">
          <div class="col-3">
          </div>
            <div class="col">
                
                <h1 class="my-5"><%= encabezadoVista %></h1>
                
                <form method="POST" action="<%= urlBase %>">
                    
                    <!-- 
                        Argumentos implicitos del formulario, que ayudan al Servlet a 
                        identificar el tipo de accion a realizar.
                    -->
                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= ReviewDAO.COLUMNA_ID %>" value="<%= review.getId() %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <!-- Campos del formulario -->
                    <div class="mb-3">
                        <label for="<%= ReviewDAO.COLUMNA_PUNTAJE %>" class="form-label">Puntaje</label>
                        <input 
                            type="numeber"
                            step="1"
                            class="form-control"
                            name="<%= ReviewDAO.COLUMNA_PUNTAJE %>" 
                            value="<%= hayDatos ? review.getPuntaje() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= ReviewDAO.COLUMNA_CONTENIDO %>" class="form-label">¿Qué comentarios tienes para el restaurante?</label>
                        <textarea class="form-control" name="<%= ReviewDAO.COLUMNA_CONTENIDO %>" rows="3">
                         <%= hayDatos ? review.getContenido(): "" %>
                        </textarea>
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= ReviewDAO.COLUMNA_FECHA %>" class="form-label">Fecha de visita</label>
                        <input 
                            type="datetime-local"  
                            class="form-control"
                            name="<%= ReviewDAO.COLUMNA_FECHA %>" 
                            value="<%= hayDatos ? review.getFecha(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= ReviewDAO.COLUMNA_ID_AUTOR %>" class="form-label">Autor</label>
                        
                        <select class="form-select" name="<%= ReviewDAO.COLUMNA_ID_AUTOR %>" aria-label="Selecciona un autor">
                            <% for (Usuario usuario : usuarios) { %>
                                <option 
                                    value="<%= usuario.getNombreUsuario()%>"
                                    <%= hayDatos && usuario.getNombreUsuario() == review.getNombreUsuarioAutor() ? "selected" : "" %>
                                >
                                    <%= usuario.getNombreUsuario()%>
                                </option>
                            <% } %>
                        </select>
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= ReviewDAO.COLUMNA_ID_RESTAURANTE %>" class="form-label">Restaurante Calificado</label>
                        
                        <select class="form-select" name="<%= ReviewDAO.COLUMNA_ID_RESTAURANTE %>" aria-label="Selecciona un restaurante">
                            <% for (Restaurante restaurante : restaurantes) { %>
                                <option 
                                    value="<%= restaurante.getId()%>"
                                    <%= hayDatos && restaurante.getId() == review.getIdRestaurante() ? "selected" : "" %>
                                >
                                    <%= restaurante.getNombre() %>
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
