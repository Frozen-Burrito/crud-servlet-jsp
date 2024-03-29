<%@page import="com.rappi.crud.dao.IngredienteDAO"%>
<%@page import="com.rappi.crud.entidades.jpa.Ingrediente"%>
<%@page import="com.rappi.crud.entidades.TipoDeCocina"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
     <jsp:include page="../../includes/head_links.jsp"></jsp:include>
    
         <title><%= Ingrediente.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Ingrediente ingrediente = (Ingrediente) request.getAttribute("ingrediente");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);
    
    String idIngredienteStr = request.getParameter(IngredienteDAO.COLUMNA_ID);
    final int idIngrediente = idIngredienteStr != null && ingrediente != null 
            ? Integer.parseInt(idIngredienteStr) 
            : -1;
    
    // Booleans de conveniencia para renderizado condicional del html.
    final boolean hayDatos = ingrediente != null && idIngrediente >= 0;
    final boolean esSoloLectura = accionForm.equals(Accion.LEER);
    
    // El título para el formulario.
    final String encabezadoVista = (String) request.getAttribute("encabezadoVista");
    
    // La URL base de todas las peticiones al servlet de ubicaciones.
    final String urlBase = request.getContextPath() + "/app";
    final String urlIngredientes = urlBase + "/ingredientes";
%>
<body>
    <section class="container">
            
        <div class="row">
          <div class="col-3">
          </div>
            <div class="col">
                                
                <% if (hayDatos || accionForm.equals(Accion.CREAR)) { %>
                <h1 class="my-5"><%= encabezadoVista %></h1>
                
                <form method="POST" action="<%= urlIngredientes %>">
                    
                    <!-- 
                        Argumentos implicitos del formulario, que ayudan al Servlet a 
                        identificar el tipo de accion a realizar.
                    -->
                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= IngredienteDAO.COLUMNA_ID %>" value="<%= ingrediente.getId() %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <!-- Campos del formulario -->
                    <div class="mb-3">
                        <label for="<%= IngredienteDAO.COLUMNA_NOMBRE %>" class="form-label">Nombre</label>
                        <input 
                            type="text"  
                            class="form-control"
                            name="<%= IngredienteDAO.COLUMNA_NOMBRE %>" 
                            <%= esSoloLectura ? "readonly" : "" %>
                            value="<%= hayDatos ? ingrediente.getNombre() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <input 
                            class="form-check-input" 
                            type="checkbox" 
                            name="<%= IngredienteDAO.COLUMNA_ESVEGANO %>"
                            id="<%= IngredienteDAO.COLUMNA_ESVEGANO %>"
                            <%= esSoloLectura ? "disabled" : "" %>
                            <%= hayDatos && ingrediente.getEsVegano()? "checked" : "" %>
                        >
                        <label for="<%= IngredienteDAO.COLUMNA_ESVEGANO %>" class="form-label">¿Es Vegano?</label>
                    </div>
                        
                    <div class="mb-3">
                        <input 
                            class="form-check-input" 
                            type="checkbox" 
                            id="<%= IngredienteDAO.COLUMNA_ESALERGICO %>"
                            name="<%= IngredienteDAO.COLUMNA_ESALERGICO %>"
                            <%= esSoloLectura ? "disabled" : "" %>
                            <%= hayDatos && ingrediente.getEsAlergenico() ? "checked" : "" %>
                        >
                        <label for="<%= IngredienteDAO.COLUMNA_ESALERGICO %>" class="form-label">¿Es Alergénico?</label>
                    </div>
                    
                    <div class="mb-3">
                        <input 
                            class="form-check-input" 
                            type="checkbox" 
                            name="<%= IngredienteDAO.COLUMNA_TIENEGLUTEN %>"
                            id="<%= IngredienteDAO.COLUMNA_TIENEGLUTEN %>"
                            <%= esSoloLectura ? "disabled" : "" %>
                            <%= hayDatos && ingrediente.getTieneGluten()? "checked" : "" %>
                        >
                        <label for="<%= IngredienteDAO.COLUMNA_TIENEGLUTEN %>" class="form-label">¿Contiene Gluten?</label>
                    </div>

                    <div class="mb-3">
                        <% if (accionForm.equals(Accion.CREAR)) { %>
                            <input type="submit" class="btn btn-primary" value="Agregar" />

                        <% } else if (accionForm.equals(Accion.ACTUALIZAR)) { %>
                            <input type="submit" class="btn btn-warning" value="Guardar Cambios" />
                        <% } %>
                    </div>

                </form>
                <% } else { %>
                    <h1 class="my-5">404 - No encontrado</h1>
                    <h3 class="my-5"> No existe el <%= Ingrediente.NOMBRE_ENTIDAD %> que estás buscando</h3>
                <% } %>
            </div>
            <div class="col-3">
            </div>
        </div>
    </section>
                
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
