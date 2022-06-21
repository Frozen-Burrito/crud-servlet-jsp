<%@ page import="java.util.List"%>
<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.entidades.jpa.Pais"%>
<%@ page import="com.rappi.crud.entidades.jpa.Pais" %>
<%@ page import="com.rappi.crud.dao.PaisDAO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Pais> paises = (List<Pais>) request.getAttribute("paises");
    
    // La URL base de todas las peticiones a paises.
    String urlBase = request.getContextPath() + "/app";
    String urlPaises = urlBase + "/paises";
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final String formatoUrlAccion = "%s?%s=%s";

    String urlDetalles = String.format(formatoUrlAccion, urlPaises, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlPaises, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlPaises, keyParamAccion, Accion.ACTUALIZAR);
%>

<html>
<head>
    <jsp:include page="../../includes/head_links.jsp"></jsp:include>

    <title>Países | Vista General</title>
</head>
<body>
    <jsp:include page="../../includes/navbar.jsp"></jsp:include>
                    
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1>Países</h1>
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
                <th scope="col">Código de País</th>
                <th scope="col">Nombre</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Pais pais : paises) { %>
                <tr>
                    <th scope="row">
                        <a href="<%= urlDetalles %>&<%= PaisDAO.COLUMNA_ID %>=<%= pais.getCodigoIso3166()%>">
                            <%= pais.getCodigoIso3166()%>
                        </a>
                    </th>

                    <td><%= pais.getNombre() %></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= PaisDAO.COLUMNA_ID %>=<%= pais.getCodigoIso3166()%>">
                                Editar
                            </a>

                            <!-- TODO: Usar DELETE para esto. -->
                            <form method="POST" action="<%= urlPaises %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= PaisDAO.COLUMNA_ID %>" value="<%= pais.getCodigoIso3166()%>"/>

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
