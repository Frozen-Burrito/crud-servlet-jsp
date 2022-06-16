<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.dao.PaisDAO"%>
<%@page import="com.rappi.crud.entidades.Pais"%>
<%@ page import="com.rappi.crud.entidades.Datos" %>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Países | Detalles</title>
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
    final Pais pais = (Pais) request.getAttribute("pais");

    String codigoPais = request.getParameter(PaisDAO.COLUMNA_ID);
    
    System.out.println(codigoPais);

    final boolean esRegistroExistente = codigoPais != null;
    final boolean hayDatos = pais != null;
    
    Accion accionForm = esRegistroExistente ? Accion.ACTUALIZAR : Accion.CREAR;

    if (esRegistroExistente)
    {
        System.out.println("Datos desde form JSP: " + pais.toString());
    }
%>
<body>
    <h1>
        
        <%=
            esRegistroExistente
                ? "Edita un " + Pais.NOMBRE_ENTIDAD
                : "Agrega un Nuevo " + Pais.NOMBRE_ENTIDAD
        %>
    </h1>

    <form method="POST" action="${pageContext.request.contextPath}/paises">
        
        <% if (hayDatos) { %>
            <input type="hidden" name="<%= PaisDAO.COLUMNA_ID %>" value="<%= codigoPais %>" />
        <% } %>
        
        <input type="hidden" name="<%= Accion.class.getName() %>" value="<%= accionForm.toString() %>"/>
        
        <input 
            type="text" 
            id="campo-codigo" 
            name="<%= PaisDAO.COLUMNA_ID %>" 
            placeholder="Código ISO 3166 de tres letras" 
            value="<%= hayDatos ? pais.getCodigoPais(): "" %>" 
        />
        
        <input
            type="text" 
            id="campo-nombre" 
            name="<%= PaisDAO.COLUMNA_NOMBRE %>" 
            placeholder="Nombre del país" 
            value="<%= hayDatos ? pais.getNombre() : "" %>" 
        />

        <% if (accionForm.equals(Accion.CREAR)) { %>
            <input type="submit" class="btn-primario" value="Agregar" />
            
        <% } else if (accionForm.equals(Accion.ACTUALIZAR)) { %>
            <input type="submit" class="btn-secundario" value="Guardar Cambios" />
        <% } %>

    </form>
</body>
</html>
