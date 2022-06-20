<%@page import="com.rappi.crud.entidades.jpa.Estado"%>
<%@ page import="java.util.List"%>
<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@ page import="com.rappi.crud.dao.EstadoDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Estado> estados = (List<Estado>) request.getAttribute("estados");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlEstados = urlBase + "/estados";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlEstados, keyParamAccion, Accion.LEER);
    String urlNuevoEstado = String.format(formatoUrlAccion, urlEstados, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlEstados, keyParamAccion, Accion.ACTUALIZAR);
%>

<html>
<head>
    <jsp:include page="../../includes/head_links.jsp"></jsp:include>
    
    <title>Estados | Vista General</title>
</head>
<body>
    <jsp:include page="../../includes/navbar.jsp"></jsp:include>
                    
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
                    href="<%= urlNuevoEstado %>"
                >
                    Nuevo
                </a>
            </div>
        </div>
        
        <table class="table table-hover">
            <tr class="table-header">
                <th scope="col">ID</th>
                <th scope="col">Nombre</th>
                <th scope="col">País</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Estado estado : estados) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= EstadoDAO.COLUMNA_ID %>=<%= estado.getId() %>">
                            <%= estado.getId() %>
                        </a>
                    </th>

                    <td><%= estado.getNombre() %></td>
                    <td><%= estado.getPais() %></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= EstadoDAO.COLUMNA_ID %>=<%= estado.getId() %>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlEstados %>" style="margin-bottom: 0px">
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
