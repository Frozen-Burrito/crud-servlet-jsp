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
    
    String urlBase = request.getContextPath() + "/reviews";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.ACTUALIZAR);
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Review.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
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

                            <form method="POST" action="<%= urlBase %>" style="margin-bottom: 0px">
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
