<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.entidades.Ingrediente"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.entidades.Ingrediente" %>
<%@ page import="com.rappi.crud.dao.IngredienteDAO" %>

<%
    List<Ingrediente> ingredientes = (List<Ingrediente>) request.getAttribute("ingredientes");
    
    String parametroAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/ingredientes";

    final String formatoUrlAccion = "%s?%s=%s";

    String urlDetalles = String.format(formatoUrlAccion, urlBase, parametroAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlBase, parametroAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlBase, parametroAccion, Accion.ACTUALIZAR);
%>



<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title> <%= Ingrediente.NOMBRE_ENTIDAD %>  | Vista General</title>
</head>
<body>
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Ingrediente.NOMBRE_ENTIDAD %> </h1>
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
                <th scope="col">Id de Ingrediente</th>
                <th scope="col">Nombre</th>
                <th scope="col">¿Es vegano?</th>
                <th scope="col">¿Es alergico?</th>
                <th scope="col">¿Tiene gluten?</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Ingrediente ingrediente : ingredientes) { %>
                <tr>
                    <!-- boton Id -->
                    <th scope="row">
                        <a href="<%=urlDetalles%>&<%= IngredienteDAO.COLUMNA_ID %>=<%= ingrediente.getIdIngrediente()%>">
                            <%= ingrediente.getIdIngrediente()%>
                        </a>
                    </th>

                    <td><%= ingrediente.getNombre() %></td>
                    <td><%= ingrediente.getEsVegano() ? "Sí":"No"%></td>
                    <td><%= ingrediente.getEsAlergico() ? "Sí":"No"%></td>
                    <td><%= ingrediente.getTieneGluten() ? "Sí":"No"%></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <!-- boton Editar -->
                            <a 
                                class="btn btn-warning"
                                href="<%=urlEditar%>&<%= IngredienteDAO.COLUMNA_ID %>=<%= ingrediente.getIdIngrediente() %>">
                                Editar
                            </a>

                            <!-- boton Eliminar -->
                            <form method="POST" action="<%= urlBase %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= parametroAccion %>" value="<%= Accion.ELIMINAR.toString()%>" />
                                <input type="hidden" name="<%= IngredienteDAO.COLUMNA_ID %>" value="<%= ingrediente.getIdIngrediente() %>"/>

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
