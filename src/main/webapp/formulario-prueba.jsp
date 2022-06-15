<%@ page import="com.rappi.crud.entidades.Datos" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
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
    
    button.btn-secundario {
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
    final Datos datos = (Datos) request.getAttribute("datos");

    String id = request.getParameter("id");

    final boolean esRegistroExistente = id != null;
    final boolean hayDatos = datos != null;
    
    String accionForm = esRegistroExistente ? "ACTUALIZAR" : "CREAR";

    if (esRegistroExistente)
    {
        System.out.println("Datos " + datos.toString());
    }
%>
<body>
    <h1>
        
        <%=
            esRegistroExistente
                ? "Modifica un Recurso"
                : "Crea un Nuevo Registro"
        %>
    </h1>

    <form method="POST" action="${pageContext.request.contextPath}/pruebas">
        
        <% if (hayDatos) { %>
            <input type="hidden" name="id" value="<%= id %>" />
        <% } %>
        
        <input type="hidden" name="metodo" value="<%= accionForm %>"/>
        
        <input type="text" id="campo-titulo" name="titulo" placeholder="Titulo" value="<%= hayDatos ? datos.getTitulo(): "" %>" />
        <input type="datetime-local" id="campo-fecha" name="fecha" placeholder="Fecha" value="<%= hayDatos ? datos.getFecha() : ""%>" />
        <input type="text" id="campo-descripcion" name="descripcion" placeholder="Descripción" value="<%= hayDatos ? datos.getDescripcion() : ""%>"/>
        <input type="number" id="campo-status" step="1" name="status" placeholder="Estatus" value="<%= hayDatos ? datos.getStatus(): ""%>"/>

        <% if (accionForm.equals("CREAR")) { %>
            <input type="submit" class="btn-primario" value="Crear" />
            
        <% } else if (accionForm.equals("ACTUALIZAR")) { %>
            <input type="submit" class="btn-secundario" value="Guardar Cambios" />
        <% } %>

    </form>
</body>
</html>
