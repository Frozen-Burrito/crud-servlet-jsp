<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
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
    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    
    System.out.println("Cantidad de usuarios: " + usuarios.size());
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlUsuarios = urlBase + "/usuarios";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlUsuarios, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlUsuarios, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlUsuarios, keyParamAccion, Accion.ACTUALIZAR);
    
    String llaveParamAccionAuth = AccionAutenticacion.class.getSimpleName().toLowerCase();
    String urlAutenticacion = request.getContextPath() + "/autenticacion?" + llaveParamAccionAuth + "=" + AccionAutenticacion.CERRAR_SESION.name();  
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Usuario.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
    <jsp:include page="../../includes/navbar.jsp"></jsp:include>
                    
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Usuario.NOMBRE_ENTIDAD %></h1>
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
                <th scope="col">Nombre de Usuario</th>
                <th scope="col">Password</th>
                <th scope="col">Tipo</th>
                <th scope="col">E-mail</th>
                <th scope="col">Número Telefónico</th>
                <th scope="col">Domicilio</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Usuario usuario : usuarios) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= UsuarioDAO.COLUMNA_ID %>=<%= usuario.getNombreUsuario()%>">
                            <%= usuario.getNombreUsuario() %>
                        </a>
                    </th>

                    <td><%= usuario.getPasswordOfuscado() %></td>
                    <td><%= usuario.getTipoDeUsuario().toString() %></td>
                    <td><%= usuario.getEmail() %></td>
                    <td><%= usuario.getNumTelefono() %></td>
                    
                    <td>
                        <%= usuario.getUbicacion() != null ? usuario.getUbicacion().toString() : "No se encontró el domicilio registrado" %>
                    </td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= UsuarioDAO.COLUMNA_ID %>=<%= usuario.getNombreUsuario()%>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlUsuarios %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= UsuarioDAO.COLUMNA_ID %>" value="<%= usuario.getNombreUsuario() %>"/>

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
