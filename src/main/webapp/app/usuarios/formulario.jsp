<%@page import="com.rappi.crud.entidades.jpa.Ubicacion"%>
﻿﻿﻿<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Stream"%>
﻿
<%@page import="com.rappi.crud.entidades.TipoDeUsuario"%>
<%@page import="com.rappi.crud.dao.UsuarioDAO"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="java.util.List"%>
<%@page import="com.rappi.crud.entidades.jpa.Usuario"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Usuario.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Usuario usuario = (Usuario) request.getAttribute("usuario");
    
    final List<Ubicacion> ubicaciones = (List<Ubicacion>) request.getAttribute("ubicaciones");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);

    String nombreDeUsuario = request.getParameter(UsuarioDAO.COLUMNA_ID);
    
    // Booleans de conveniencia para renderizado condicional del html.
    final boolean hayDatos = usuario != null && nombreDeUsuario != null;
    final boolean esSoloLectura = accionForm.equals(Accion.LEER);
    
    // La URL base de todas las peticiones al servlet de ubicaciones.
    final String urlBase = request.getContextPath() + "/app";
    String urlUsuarios = urlBase + "/usuarios";
    
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

                    <form method="POST" action="<%= urlUsuarios %>">

                        <% if (hayDatos) { %>
                            <input type="hidden" name="<%= UsuarioDAO.COLUMNA_ID %>" value="<%= nombreDeUsuario %>" />
                        <% } %>

                        <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                        <div class="mb-3">
                            <label for="<%= UsuarioDAO.COLUMNA_ID %>" class="form-label">Nombre de Usuario</label>
                            <input 
                                type="text"  
                                readonly
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
                                <%= esSoloLectura ? "readonly" : "" %>
                                value="<%= hayDatos ? usuario.getPassword() : "" %>" 
                            />
                        </div>

                        <div class="mb-3">
                            <label for="<%= UsuarioDAO.COLUMNA_TIPO %>" class="form-label">Tipo de Usuario</label>

                            <select 
                                class="form-select" 
                                name="<%= UsuarioDAO.COLUMNA_TIPO %>" 
                                aria-label="Seleccione un tipo de cuenta"
                                <%= esSoloLectura ? "disabled" : "" %>
                            >
                                <% for (TipoDeUsuario tipo : TipoDeUsuario.values()) { %>
                                    <option 
                                        value="<%= tipo.name()%>"
                                        <%= hayDatos && tipo.equals(usuario.getTipo()) ? "selected" : "" %>
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
                                <%= esSoloLectura ? "readonly" : "" %>
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
                                <%= esSoloLectura ? "readonly" : "" %>
                                value="<%= hayDatos ? usuario.getNumeroTelefonico(): "" %>" 
                            />
                        </div>

                        <div class="mb-3">
                            <label for="<%= UsuarioDAO.COLUMNA_ID_UBICACION %>" class="form-label">Domicilio</label>

                            <select 
                                class="form-select"
                                name="<%= UsuarioDAO.COLUMNA_ID_UBICACION %>" 
                                aria-label="Selecciona tu ubicación"
                                <%= esSoloLectura ? "disabled" : "" %>
                            >
                                <% for (Ubicacion ubicacion : ubicaciones) { %>
                                    <option 
                                        value="<%= ubicacion.getId()%>"
                                        <%= hayDatos && ubicacion.getId()== usuario.getUbicacion().getId() ? "selected" : "" %>
                                    >
                                        <%= ubicacion.toString() %>
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
                    <h3 class="my-5"> No existe el <%= Usuario.NOMBRE_ENTIDAD %> que estás buscando</h3>
                <% } %>
            </div>
            <div class="col-3">
            </div>
        </div>
    </section>
                
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
