<%@page import="com.rappi.crud.entidades.jpa.Restaurante"%>
<%@ page import="java.util.List"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Stream"%>
<%@ page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page import="com.rappi.crud.dao.RestauranteDAO"%>
<%@page import="com.rappi.crud.dao.UsuarioDAO"%>
<%@page import="com.rappi.crud.dao.UbicacionDAO"%>
<%@ page import="com.rappi.crud.dao.MunicipioDAO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Restaurante> restaurantes = (List<Restaurante>) request.getAttribute("restaurantes");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlRestaurantes = urlBase + "/restaurantes";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlRestaurantes, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlRestaurantes, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlRestaurantes, keyParamAccion, Accion.ACTUALIZAR);
%>

<html>
<head>
    <jsp:include page="../../includes/head_links.jsp"></jsp:include>

    <title><%= Restaurante.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
   <jsp:include page="../../includes/navbar.jsp"></jsp:include>
                    
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
                        <a href="<%= restaurante.getUrlSitioWeb()%>"> 
                            <%= restaurante.getUrlSitioWeb()%>
                        </a>
                    </td>
                    
                    <td><%= restaurante.getNumeroTelefonico()%></td>
                    <td><%= restaurante.getTipoCocina() %></td>
                    
                    <td>
                        <%= restaurante.getUbicacion()!= null ? restaurante.getUbicacion().toString() : "No se encontró el domicilio registrado" %>
                    </td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= RestauranteDAO.COLUMNA_ID %>=<%= restaurante.getId()%>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlRestaurantes %>" style="margin-bottom: 0px">
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
