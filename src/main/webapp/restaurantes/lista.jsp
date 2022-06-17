<%@page import="com.rappi.crud.dao.RestauranteDAO"%>
<%@page import="com.rappi.crud.entidades.Restaurante"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Stream"%>
<%@page import="com.rappi.crud.dao.UsuarioDAO"%>
<%@page import="com.rappi.crud.entidades.Usuario"%>
<%@page import="com.rappi.crud.dao.UbicacionDAO"%>
<%@page import="com.rappi.crud.entidades.Ubicacion"%>
<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.entidades.Municipio"%>
<%@ page import="com.rappi.crud.dao.MunicipioDAO"%>
<%@ page import="com.rappi.crud.servlets.Accion"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Restaurante> restaurantes = (List<Restaurante>) request.getAttribute("restaurantes");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/restaurantes";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.ACTUALIZAR);
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Restaurante.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Restaurante.NOMBRE_ENTIDAD %></h1>
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
                <th scope="col">Nombre</th>
                <th scope="col">URL de Sitio Web</th>
                <th scope="col">Número Telefónico</th>
                <th scope="col">Tipo de Cocina</th>
                <th scope="col">Domicilio</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Restaurante restaurante : restaurantes) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= RestauranteDAO.COLUMNA_ID %>=<%= restaurante.getId()%>">
                            <%= restaurante.getId() %>
                        </a>
                    </th>

                    <td><%= restaurante.getNombre() %></td>
                    
                    <td>
                        <a href="<%= restaurante.getSitioWeb()%>"> 
                            <%= restaurante.getSitioWeb()%>
                        </a>
                    </td>
                    
                    <td><%= restaurante.getNumTelefonico()%></td>
                    <td><%= restaurante.getTipoDeCocina().toString() %></td>
                    
                    <td>
                        <%= restaurante.getUbicacion() != null ? restaurante.getUbicacion().toString() : "No se encontró el domicilio registrado" %>
                    </td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= RestauranteDAO.COLUMNA_ID %>=<%= restaurante.getId()%>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlBase %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= RestauranteDAO.COLUMNA_ID %>" value="<%= restaurante.getId() %>"/>

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
