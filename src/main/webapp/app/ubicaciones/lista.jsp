<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page import="com.rappi.crud.entidades.jpa.Ubicacion"%>
<%@page import="com.rappi.crud.dao.UbicacionDAO"%>
<%@ page import="com.rappi.crud.dao.MunicipioDAO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Ubicacion> ubicaciones = (List<Ubicacion>) request.getAttribute("ubicaciones");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlUbicaciones = urlBase + "/ubicaciones";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlUbicaciones, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlUbicaciones, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlUbicaciones, keyParamAccion, Accion.ACTUALIZAR);
%>

<html>
<head>
    <jsp:include page="../../includes/head_links.jsp"></jsp:include>

    <title><%= Ubicacion.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
    <jsp:include page="../../includes/navbar.jsp"></jsp:include>
                    
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Ubicacion.NOMBRE_ENTIDAD %></h1>
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
                <th scope="col">Nombre de la Calle</th>
                <th scope="col">Número Exterior</th>
                <th scope="col">Número Interior</th>
                <th scope="col">Colonia</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Ubicacion ubicacion : ubicaciones) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= UbicacionDAO.COLUMNA_ID %>=<%= ubicacion.getId() %>">
                            <%= ubicacion.getId() %>
                        </a>
                    </th>

                    <td><%= ubicacion.getCalle()%></td>
                    <td><%= ubicacion.getNumeroExterior()%></td>
                    <td><%= ubicacion.getNumeroInterior()%></td>
                    
                    <td><%= ubicacion.getColonia() != null ? ubicacion.getColonia().getNombre() : "No se encontró la colonia de la ubicación"%></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= UbicacionDAO.COLUMNA_ID %>=<%= ubicacion.getId() %>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlUbicaciones %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= UbicacionDAO.COLUMNA_ID %>" value="<%= ubicacion.getId() %>"/>

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
