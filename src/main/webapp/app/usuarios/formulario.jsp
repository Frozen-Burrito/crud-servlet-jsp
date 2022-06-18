﻿﻿<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Stream"%>
﻿
<%@page import="com.rappi.crud.entidades.TipoDeUsuario"%>
<%@page import="com.rappi.crud.dao.UsuarioDAO"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="java.util.List"%>
<%@page import="com.rappi.crud.entidades.Ubicacion"%>
<%@page import="com.rappi.crud.entidades.Usuario"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Usuario.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Usuario usuario = (Usuario) request.getAttribute("usuario");
    final boolean hayDatos = usuario != null;
    
    final List<Ubicacion> ubicaciones = (List<Ubicacion>) request.getAttribute("ubicaciones");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);

    String nombreDeUsuario = request.getParameter(UsuarioDAO.COLUMNA_ID);
    
    // El título para el formulario.
    final String encabezadoVista = (String) request.getAttribute("encabezadoVista");
    
    // La URL base de todas las peticiones al servlet de ubicaciones.
    final String urlBase = request.getContextPath() + "/app/usuarios";

%>
<body>
    <section class="container">
            
        <div class="row">
          <div class="col-3">
          </div>
            <div class="col">
                
                <h1 class="my-5"><%= encabezadoVista %></h1>
                
                <form method="POST" action="<%= urlBase %>">

                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= UsuarioDAO.COLUMNA_ID %>" value="<%= nombreDeUsuario %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <div class="mb-3">
                        <label for="<%= UsuarioDAO.COLUMNA_ID %>" class="form-label">Nombre de Usuario</label>
                        <input 
                            type="text"  
                            class="form-control"
                            name="<%= UsuarioDAO.COLUMNA_ID %>" 
                            value="<%= hayDatos ? usuario.getNombreUsuario(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= UsuarioDAO.COLUMNA_PASSWORD %>" class="form-label">Contraseña</label>
                        <input 
                            type="password"  
                            class="form-control"
                            name="<%= UsuarioDAO.COLUMNA_PASSWORD %>" 
                            placeholder="#" 
                            value="<%= hayDatos ? usuario.getPassword() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= UsuarioDAO.COLUMNA_TIPO %>" class="form-label">Tipo de Usuario</label>
                        
                        <select class="form-select" name="<%= UsuarioDAO.COLUMNA_TIPO %>" aria-label="Seleccione un tipo de cuenta">
                            <% for (TipoDeUsuario tipo : TipoDeUsuario.values()) { %>
                                <option 
                                    value="<%= tipo.name()%>"
                                    <%= hayDatos && tipo.equals(usuario.getTipoDeUsuario()) ? "selected" : "" %>
                                >
                                    <%= tipo.toString() %>
                                </option>
                            <% } %>
                        </select>
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= UsuarioDAO.COLUMNA_EMAIL %>" class="form-label">Dirección de Correo Electrónico</label>
                        <input 
                            type="email"  
                            class="form-control"
                            name="<%= UsuarioDAO.COLUMNA_EMAIL %>" 
                            placeholder="ejemplo@gmail.com" 
                            value="<%= hayDatos ? usuario.getEmail(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= UsuarioDAO.COLUMNA_NUM_TELEFONICO %>" class="form-label">Número de Teléfono</label>
                        <input 
                            type="tel"  
                            class="form-control"
                            name="<%= UsuarioDAO.COLUMNA_NUM_TELEFONICO %>" 
                            placeholder="00-0000-0000" 
                            value="<%= hayDatos ? usuario.getNumTelefono(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= UsuarioDAO.COLUMNA_ID_UBICACION %>" class="form-label">Domicilio</label>
                        
                        <select class="form-select" name="<%= UsuarioDAO.COLUMNA_ID_UBICACION %>" aria-label="Selecciona tu ubicación">
                            <% for (Ubicacion ubicacion : ubicaciones) { %>
                                <option 
                                    value="<%= ubicacion.getId()%>"
                                    <%= hayDatos && ubicacion.getId() == usuario.getIdUbicacion()? "selected" : "" %>
                                >
                                    <%= ubicacion.getNombreCalle()%>
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
