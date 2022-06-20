<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.entidades.Producto"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.entidades.Producto" %>
<%@ page import="com.rappi.crud.dao.ProductoDAO" %>

<%
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    
    String parametroAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/Productos";

    final String formatoUrlAccion = "%s?%s=%s";

    String urlDetalles = String.format(formatoUrlAccion, urlBase, parametroAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlBase, parametroAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlBase, parametroAccion, Accion.ACTUALIZAR);
%>



<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Producto.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Producto.NOMBRE_ENTIDAD %></h1>
            </div>
            <div class="col-8">
            </div>
            <div class="col">
                <a class="btn btn-primary" href="<%=urlNuevo%>">
                    Nuevo
                </a>
            </div>
        </div>
        
        <table class="table table-hover">
            <tr class="table-header">
                <th scope="col">Id de Producto</th>
                <th scope="col">Id de Restaurante</th>
                <th scope="col">Nombre</th>
                <th scope="col">¿Está disponible?</th>
                <th scope="col">Kcal/Porcion</th>
                <th scope="col">Tamaño de porción</th>
                <th scope="col">Descripción</th>
                <th scope="col">Precio</th>
                <th scope="col">Código de moneda</th>
                <th scope="col">Tipo de producto</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Producto producto : productos) { %>
                <tr>
                    <!-- boton Id -->
                    <th scope="row">
                        <a href="<%= urlDetalles %>&<%= ProductoDAO.COLUMNA_ID %>=<%= producto.getIdProducto()%>">
                            <%= producto.getIdProducto()%>
                        </a>
                    </th>

                    <td><%= producto.getIdRestaurante() %></td>
                    <td><%= producto.getNombre() %></td>
                    <td><%= producto.isDisponible() ? "Sí" : "No" %></td>
                    <td><%= producto.getKcalPorcion() %></td>
                    <td><%= producto.getTamañoPorcion() %></td>
                    <td><%= producto.getDescripcion() %></td>
                    <td><%= producto.getPrecio() %></td>
                    <td><%= producto.getCodigoMoneda() %></td>
                    <td><%= producto.getTipoDeProducto() %></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <!-- boton Editar -->
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= ProductoDAO.COLUMNA_ID %>=<%= producto.getIdProducto() %>">
                                Editar
                            </a>

                            <!-- boton Eliminar -->
                            <form method="POST"  action="<%= urlBase %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= parametroAccion %>" value="<%= Accion.ELIMINAR.toString()%>" />
                                <input type="hidden" name="<%= ProductoDAO.COLUMNA_ID %>" value="<%= producto.getIdProducto() %>"/>

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
