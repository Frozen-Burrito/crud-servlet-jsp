<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page import="com.rappi.crud.dao.ReviewDAO"%>
<%@page import="com.rappi.crud.entidades.Review"%>
<%@page import="com.rappi.crud.dao.RestauranteDAO"%>
<%@page import="com.rappi.crud.entidades.Restaurante"%>
<%@page import="com.rappi.crud.dao.UsuarioDAO"%>
<%@page import="com.rappi.crud.entidades.Usuario"%>
<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.servlets.Accion"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Review> reviews = (List<Review>) request.getAttribute("reviews");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlReviews = urlBase + "/reviews";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlReviews, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlReviews, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlReviews, keyParamAccion, Accion.ACTUALIZAR);
           
    String llaveParamAccionAuth = AccionAutenticacion.class.getSimpleName().toLowerCase();
    String urlAutenticacion = request.getContextPath() + "/autenticacion?" + llaveParamAccionAuth + "=" + AccionAutenticacion.CERRAR_SESION.name();  
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Review.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
          <a class="navbar-brand" href="<%= urlReviews %>">Rappi-CRUD</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="<%= urlBase %>">Inicio</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="<%= urlBase %>/usuarios">Usuarios</a>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Restaurantes
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <li><a class="dropdown-item" href="<%= urlBase %>/restaurantes">Todos</a></li>
                  <li><a class="dropdown-item" href="<%= urlBase %>/reviews">Reseñas</a></li>
                </ul>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Ubicación
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <li><a class="dropdown-item" href="<%= urlBase %>/paises">Países</a></li>
                  <li><a class="dropdown-item" href="<%= urlBase %>/estados">Estados</a></li>
                  <li><a class="dropdown-item" href="<%= urlBase %>/datos-municipios">Municipios</a></li>
                  <li><a class="dropdown-item" href="<%= urlBase %>/datos-colonias">Colonias</a></li>
                  <li><hr class="dropdown-divider"></li>
                  <li><a class="dropdown-item" href="<%= urlBase %>/ubicaciones">Ubicaciones</a></li>
                </ul>
              </li>
            </ul>
          </div>

            <div class="d-flex">
                <a 
                    class="btn btn-outline-danger me-2" 
                    href="<%= urlAutenticacion %>">
                    Cerrar Sesión
                </a>
            </div>
        </div>
    </nav>
                    
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Review.NOMBRE_ENTIDAD %></h1>
            </div>
            <div class="col-8">
            </div>
            <div class="col">
                <a 
                    class="btn btn-primary"
                    href="<%= urlNuevo %>"
                >
                    Nuevo
                </a>
            </div>
        </div>
        
        <table class="table table-hover">
            <tr class="table-header">
                <th scope="col">ID</th>
                <th scope="col">Puntaje</th>
                <th scope="col">Contenido</th>
                <th scope="col">Fecha</th>
                <th scope="col">Autor</th>
                <th scope="col">Restaurante</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Review review : reviews) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= ReviewDAO.COLUMNA_ID %>=<%= review.getId()%>">
                            <%= review.getId() %>
                        </a>
                    </th>

                    <td><%= review.getPuntaje() %></td>
                    
                    <td><%= review.getContenido()%></td>
                    
                    <td><%= review.getFecha()%></td>
                    
                    <td><%= review.getUsuarioAutor().getNombreUsuario() %></td>
                    <td><%= review.getRestaurante().getNombre() %></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= ReviewDAO.COLUMNA_ID %>=<%= review.getId()%>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlReviews %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= ReviewDAO.COLUMNA_ID %>" value="<%= review.getId() %>"/>

                                <input class="btn btn-danger" type="submit" value="Eliminar"/>
                            </form>
                        </div>
                    </td>
                </tr>
            <% } %>
        </table>
    </section>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

</body>
</html>
