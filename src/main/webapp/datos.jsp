<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.entidades.Datos" %>

<html>
<head>
    <title>Mostrar Datos</title>
</head>
<style>

    * {
        font-family: "Roboto Thin", sans-serif;
    }

    .contenedor {
        max-width: 80vw;
        margin: 2rem auto;
    }

    .contenedor.row {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .btn {
        font-size: 18px;
        font-weight: 400;
        text-align: center;
        outline: none;
        border: none;
        padding: 0.6rem 1.4rem;
        border-radius: 5px;
        cursor: pointer;
    }

    button.btn-primario, input.btn-primario {
        background-color: #007bff;
        color: #ffffff;
    }

    button.btn-error, input.btn-error {
        background-color: #dc3545;
        color: #ffffff;
    }

    td.td-acciones {
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
        gap: 16px;
    }

    @media (min-width: 1224px) {
        .contenedor {
            max-width: 1200px;
        }
    }

    table {
        border-collapse: collapse;
    }

    tr.table-header {
        border-bottom: 1px solid black;
    }

</style>
<%
    List<Datos> datos = (List<Datos>) request.getAttribute("datos");
%>
<body>

<header>
    <div class="contenedor row">
        <h1>Datos</h1>

        <button class="btn btn-primario" value="Nuevo" onclick="window.location.href='formulario-prueba.jsp'">
            Nuevo
        </button>
    </div>
</header>

<div class="contenedor">
    <table>
        <tr class="table-header">
            <th>ID</th>
            <th>Titulo</th>
            <th>Fecha</th>
            <th>Descripción</th>
            <th>Fecha de Creacion</th>
            <th>Acciones</th>
        </tr>

        <% for (Datos registroDatos : datos) { %>
            <tr>
                <td><%= registroDatos.getId() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/pruebas?id=<%= registroDatos.getId() %>">
                        <%= registroDatos.getTitulo()%>
                    </a>
                </td>

                <td>$<%= registroDatos.getFecha() %></td>
                <td><%= registroDatos.getDescripcion() %></td>
                <td><%= registroDatos.getStatus()%></td>
                <td><%= registroDatos.getFechaCreacion()%></td>

                <td class="td-acciones">
                    <a href="formulario-prueba.jsp?id-datos=<%= registroDatos.getId() %>">Editar</a>

                    <!-- TODO: Usar DELETE para esto. -->
                    <form method="post" action="${pageContext.request.contextPath}/pruebas">
                        <input type="hidden" name="accion" value="${Accion.ELIMINAR.toString()}" />
                        <input type="hidden" name="id" value="<%= registroDatos.getId() %>"/>

                        <input class="btn btn-error" type="submit" value="Eliminar"/>
                    </form>
                </td>
            </tr>
        <% } %>
    </table>
</div>

</body>
</html>
