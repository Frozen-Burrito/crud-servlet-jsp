<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.entidades.Pais"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.entidades.Estado" %>
<%@ page import="com.rappi.crud.dao.EstadoDAO" %>

<%
    List<Estado> estados = (List<Estado>) request.getAttribute("estados");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = "estados";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.LEER);
    String urlNuevoEstado = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.ACTUALIZAR);
    
    System.out.println(urlDetalles);
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title>Estados | Vista General</title>
</head>
<body>
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Estado.NOMBRE_ENTIDAD %></h1>
            </div>
            <div class="col-8">
            </div>
            <div class="col">
                <a 
                    class="btn btn-primary"
                    href="${pageContext.request.contextPath}/<%= urlNuevoEstado %>"
                >
                    Nuevo
                </a>
            </div>
        </div>
        
        <table class="table table-hover">
            <tr class="table-header">
                <th scope="col">ID</th>
                <th scope="col">Nombre</th>
                <th scope="col">Código de País</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Estado estado : estados) { %>
                <tr>
                    <th scope="row">                        
                        <a href="${pageContext.request.contextPath}/<%= urlDetalles %>&<%= EstadoDAO.COLUMNA_ID %>=<%= estado.getId() %>">
                            <%= estado.getId() %>
                        </a>
                    </th>

                    <td><%= estado.getNombre() %></td>
                    <td><%= estado.getCodigoPais() %></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="${pageContext.request.contextPath}/<%= urlEditar %>&<%= EstadoDAO.COLUMNA_ID %>=<%= estado.getId() %>">
                                Editar
                            </a>

                            <form method="POST" action="${pageContext.request.contextPath}/estados" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= EstadoDAO.COLUMNA_ID %>" value="<%= estado.getId() %>"/>

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
