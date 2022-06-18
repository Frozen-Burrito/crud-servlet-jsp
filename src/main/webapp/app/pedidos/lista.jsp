<%@page import="com.rappi.crud.dao.PedidoDAO"%>
<%@page import="com.rappi.crud.entidades.Pedido"%>
<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page import="com.rappi.crud.dao.RestauranteDAO"%>
<%@page import="com.rappi.crud.entidades.Restaurante"%>
<%@page import="com.rappi.crud.dao.UsuarioDAO"%>
<%@page import="com.rappi.crud.entidades.Usuario"%>
<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.servlets.Accion"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlPedidos = urlBase + "/datos-pedidos";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlPedidos, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlPedidos, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlPedidos, keyParamAccion, Accion.ACTUALIZAR);
%>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Pedido.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
    
    <jsp:include page="../../includes/navbar.jsp"></jsp:include>
                    
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Pedido.NOMBRE_ENTIDAD %></h1>
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
                <th scope="col">Restaurante</th>
                <th scope="col">Fecha</th>
                <th scope="col">Cliente</th>
                <th scope="col">Productos</th>
                <th scope="col">Total</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Pedido pedido : pedidos) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= PedidoDAO.COLUMNA_ID %>=<%= pedido.getId()%>">
                            <%= pedido.getId() %>
                        </a>
                    </th>

                    <td><%= pedido.getIdRestaurante() %></td>
                    
                    <td><%= pedido.getFecha()%></td>
                    
                    <td><%= pedido.getNombreUsuarioCliente()%></td>
                    
                    <td>TODO: incluir productos de cada pedido (con cantidad)</td>
                    
                    <td>TODO: incluir monto total de cada pedido</td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= PedidoDAO.COLUMNA_ID %>=<%= pedido.getId()%>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlPedidos %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= PedidoDAO.COLUMNA_ID %>" value="<%= pedido.getId() %>"/>

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
