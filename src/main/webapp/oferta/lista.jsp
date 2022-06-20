<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Stream"%>
<%@page import="com.rappi.crud.dao.OfertaDAO"%>
<%@page import="com.rappi.crud.entidades.Oferta"%>
<%@page import="com.rappi.crud.dao.ProductoDAO"%>
<%@page import="com.rappi.crud.entidades.Producto"%>
<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.servlets.Accion"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Oferta> ofertas = (List<Oferta>) request.getAttribute("ofertas");
    
    System.out.println("Cantidad de ofertas: " + ofertas.size());
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/ofertas";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlBase, keyParamAccion, Accion.ACTUALIZAR);
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Oferta.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Oferta.NOMBRE_ENTIDAD %></h1>
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
                <th scope="col">Id de la oferta</th>
                <th scope="col">Porcentaje descuento</th>
                <th scope="col">Fecha término</th>
                <th scope="col">Id producto</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Oferta oferta : ofertas) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= OfertaDAO.COLUMNA_ID %>=<%= oferta.getIdOferta()%>">
                            <%= oferta.getIdOferta() %>
                        </a>
                    </th>

                    <td><%= oferta.getPorcentajeDescuento() %></td>
                    <td><%= oferta.getFechaTermino().toString() %></td>
                    <td><%= oferta.getIdProducto() %></td>
                    
                    <td>
                        <%= oferta.getProducto() != null ? oferta.getProducto().toString() : "No se encontró el producto" %>
                    </td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= OfertaDAO.COLUMNA_ID %>=<%= oferta.getIdOferta()%>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlBase %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= OfertaDAO.COLUMNA_ID %>" value="<%= oferta.getIdOferta() %>"/>

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