<%@page import="com.rappi.crud.dao.IngredienteDAO"%>
<%@page import="com.rappi.crud.entidades.jpa.Ingrediente"%>
<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Ingrediente> ingredientes = (List<Ingrediente>) request.getAttribute("ingredientes");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlIngredientes = urlBase + "/ingredientes";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlIngredientes, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlIngredientes, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlIngredientes, keyParamAccion, Accion.ACTUALIZAR);
%>

<html>
<head>
    <jsp:include page="../../includes/head_links.jsp"></jsp:include>

    <title><%= Ingrediente.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
   <jsp:include page="../../includes/navbar.jsp"></jsp:include>
                    
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Ingrediente.NOMBRE_ENTIDAD %></h1>
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
                <th scope="col">Vegano</th>
                <th scope="col">Alergénico</th>
                <th scope="col">Tiene Gluten</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Ingrediente ingrediente : ingredientes) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= IngredienteDAO.COLUMNA_ID %>=<%= ingrediente.getId()%>">
                            <%= ingrediente.getId() %>
                        </a>
                    </th>

                    <td><%= ingrediente.getNombre() %></td>
                    
                    <td><%= ingrediente.getEsVegano() ? "Sí" : "No" %></td>
                    <td><%= ingrediente.getEsAlergenico()? "Sí" : "No" %></td>
                    <td><%= ingrediente.getTieneGluten()? "Sí" : "No" %></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= IngredienteDAO.COLUMNA_ID %>=<%= ingrediente.getId()%>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlIngredientes %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= IngredienteDAO.COLUMNA_ID %>" value="<%= ingrediente.getId() %>"/>

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
