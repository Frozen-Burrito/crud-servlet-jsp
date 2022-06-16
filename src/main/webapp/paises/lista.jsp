<%@page import="com.rappi.crud.entidades.Pais"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.entidades.Pais" %>
<%@ page import="com.rappi.crud.dao.PaisDAO" %>

<html>
<head>
    <title>Países | Vista General</title>
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
    
    td.td-acciones > a.btn-editar {
        background-color: #f0ab00;
        color: #ffffff;
        font-size: 18px;
        font-weight: 400;
        text-align: center;
        outline: none;
        border: none;
        padding: 0.6rem 1.4rem;
        border-radius: 5px;
        cursor: pointer;
    }

</style>
<%
    List<Pais> paises = (List<Pais>) request.getAttribute("paises");
%>
<body>

<header>
    <div class="contenedor row">
        <h1>Países</h1>

        <button class="btn btn-primario" value="Nuevo" onclick="window.location.href='paises/formulario.jsp'">
            Nuevo
        </button>
    </div>
</header>

<div class="contenedor">
    <table>
        <tr class="table-header">
            <th>Código de País</th>
            <th>Nombre</th>
            <th>Acciones</th>
        </tr>

        <% for (Pais pais : paises) { %>
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/paises?<%= PaisDAO.COLUMNA_ID %>=<%= pais.getCodigoPais()%>">
                        <%= pais.getCodigoPais()%>
                    </a>
                </td>

                <td><%= pais.getNombre() %></td>

                <td class="td-acciones">
                    <a 
                        class="btn-editar" 
                        href="${pageContext.request.contextPath}/paises?<%= PaisDAO.COLUMNA_ID %>=<%= pais.getCodigoPais() %>">
                        Editar
                    </a>

                    <!-- TODO: Usar DELETE para esto. -->
                    <form method="POST" action="${pageContext.request.contextPath}/paises">
                        <input type="hidden" name="accion" value="${Accion.ELIMINAR.toString()}" />
                        <input type="hidden" name="<%= PaisDAO.COLUMNA_ID %>" value="<%= pais.getCodigoPais() %>"/>

                        <input class="btn btn-error" type="submit" value="Eliminar"/>
                    </form>
                </td>
            </tr>
        <% } %>
    </table>
</div>

</body>
</html>
