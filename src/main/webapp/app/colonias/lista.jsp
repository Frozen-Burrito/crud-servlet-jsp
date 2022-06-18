<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@ page import="java.util.List"%>
<%@page import="com.rappi.crud.dao.ColoniaDAO"%>
<%@page import="com.rappi.crud.entidades.Colonia"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.entidades.Pais"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Colonia> colonias = (List<Colonia>) request.getAttribute("colonias");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlColonias = urlBase + "/datos-colonias";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlColonias, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlColonias, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlColonias, keyParamAccion, Accion.ACTUALIZAR);
        
    String llaveParamAccionAuth = AccionAutenticacion.class.getSimpleName().toLowerCase();
    String urlAutenticacion = request.getContextPath() + "/autenticacion?" + llaveParamAccionAuth + "=" + AccionAutenticacion.CERRAR_SESION.name();  
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Colonia.NOMBRE_ENTIDAD %>s | Vista General</title>
</head>
<body>
    <jsp:include page="../../includes/navbar.jsp"></jsp:include>
                        
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Colonia.NOMBRE_ENTIDAD %></h1>
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
                <th scope="col">Código Postal</th>
                <th scope="col">ID del Municipio</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Colonia colonia : colonias) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= ColoniaDAO.COLUMNA_ID %>=<%= colonia.getId() %>">
                            <%= colonia.getId() %>
                        </a>
                    </th>

                    <td><%= colonia.getNombre() %></td>
                    <td><%= colonia.getCodigoPostal() %></td>
                    <td><%= colonia.getIdMunicipio()%></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= ColoniaDAO.COLUMNA_ID %>=<%= colonia.getId() %>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlColonias %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= ColoniaDAO.COLUMNA_ID %>" value="<%= colonia.getId() %>"/>

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
