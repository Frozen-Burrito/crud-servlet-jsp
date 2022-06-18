<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.entidades.Municipio"%>
<%@ page import="com.rappi.crud.dao.MunicipioDAO"%>
<%@ page import="com.rappi.crud.servlets.Accion"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Municipio> municipios = (List<Municipio>) request.getAttribute("municipios");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlMunicipios = urlBase + "/datos-municipios";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlMunicipios, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlMunicipios, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlMunicipios, keyParamAccion, Accion.ACTUALIZAR);
    
    String llaveParamAccionAuth = AccionAutenticacion.class.getSimpleName().toLowerCase();
    String urlAutenticacion = request.getContextPath() + "/autenticacion?" + llaveParamAccionAuth + "=" + AccionAutenticacion.CERRAR_SESION.name();  
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Municipio.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
    <jsp:include page="../../includes/navbar.jsp"></jsp:include>
                    
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Municipio.NOMBRE_ENTIDAD %></h1>
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
                <th scope="col">ID del Estado</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Municipio municipio : municipios) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= MunicipioDAO.COLUMNA_ID %>=<%= municipio.getId() %>">
                            <%= municipio.getId() %>
                        </a>
                    </th>

                    <td><%= municipio.getNombre() %></td>
                    <td><%= municipio.getIdEstado()%></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= MunicipioDAO.COLUMNA_ID %>=<%= municipio.getId() %>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlMunicipios %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= MunicipioDAO.COLUMNA_ID %>" value="<%= municipio.getId() %>"/>

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
