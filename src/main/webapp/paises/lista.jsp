<%@page import="com.rappi.crud.entidades.Pais"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.entidades.Pais" %>
<%@ page import="com.rappi.crud.dao.PaisDAO" %>

<%
    List<Pais> paises = (List<Pais>) request.getAttribute("paises");
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title>Países | Vista General</title>
</head>
<body>
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1>Países</h1>
            </div>
            <div class="col-8">
            </div>
            <div class="col">
                <button class="btn btn-primary" value="Nuevo" onclick="window.location.href='paises/formulario.jsp'">
                    Nuevo
                </button>
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
                        <a href="${pageContext.request.contextPath}/paises?<%= PaisDAO.COLUMNA_ID %>=<%= pais.getCodigoPais()%>">
                            <%= pais.getCodigoPais()%>
                        </a>
                    </th>

                    <td><%= pais.getNombre() %></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="${pageContext.request.contextPath}/paises?<%= PaisDAO.COLUMNA_ID %>=<%= pais.getCodigoPais() %>">
                                Editar
                            </a>

                            <!-- TODO: Usar DELETE para esto. -->
                            <form method="POST" action="${pageContext.request.contextPath}/paises" style="margin-bottom: 0px">
                                <input type="hidden" name="accion" value="${Accion.ELIMINAR.toString()}" />
                                <input type="hidden" name="<%= PaisDAO.COLUMNA_ID %>" value="<%= pais.getCodigoPais() %>"/>

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
