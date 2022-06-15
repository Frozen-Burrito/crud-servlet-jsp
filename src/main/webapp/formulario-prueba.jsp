<%@ page import="com.rappi.crud.entidades.Datos" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Datos de Prueba</title>
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

    input.btn-primario {
        background-color: #007bff;
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
//    final Accion accionFormulario = request.getParameter("accion") != null
//            ? Accion.valueOf(request.getParameter("accion"))
//            : Accion.LEER;

    final Datos datos = (Datos) request.getAttribute("datos");

    String id = request.getParameter("id-datos");

    final boolean hayDatos = datos != null;
    final boolean formularioEnviable = false;

    if (hayDatos)
    {
        System.out.println("Datos " + datos.toString());
    }
%>
<body>
    <h1>
        
        <%= "Formulario de Prueba"
//            accionFormulario == Accion.CREAR
//                ? "Registra un Nuevo Producto"
//                : accionFormulario == Accion.ACTUALIZAR
//                    ? "Editar Producto"
//                    : "Detalles del Producto"
        %>
    </h1>

    <form method="post" action="${pageContext.request.contextPath}/productos/detalles">
        <!--
        <input type="hidden" name="accion" value="<%= // accionFormulario.toString() %>" />
        <input type="hidden" name="id" value="<%= //id != null ? id : "" %>" />
        -->
        
        <input type="text" name="titulo" placeholder="Titulo" value="<%= hayDatos ? datos.getTitulo(): "" %>" />
        <input type="datetime" name="fecha" placeholder="Fecha" value="<%= hayDatos ? datos.getFecha() : ""%>" />
        <input type="text" name="descripcion" placeholder="Descripción" value="<%= hayDatos ? datos.getDescripcion() : ""%>"/>
        <input type="number" step="1" name="status" placeholder="Estatus" value="<%= hayDatos ? datos.getStatus(): ""%>"/>

        <% if (formularioEnviable) { %>
            <input class="btn-primario" type="submit" value="Enviar" />
        <% } %>

    </form>
</body>
</html>
