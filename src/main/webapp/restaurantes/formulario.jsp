<%@page import="com.rappi.crud.entidades.TipoDeCocina"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="java.util.List"%>
<%@page import="com.rappi.crud.entidades.Ubicacion"%>
<%@page import="com.rappi.crud.entidades.Restaurante"%>
<%@page import="com.rappi.crud.dao.RestauranteDAO"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Restaurante.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Restaurante restaurante = (Restaurante) request.getAttribute("restaurante");
    System.out.println("ID del restaurante: " + restaurante.getId());
    final boolean hayDatos = restaurante != null;
    
    final List<Ubicacion> ubicaciones = (List<Ubicacion>) request.getAttribute("ubicaciones");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);
    
    // El título para el formulario.
    final String encabezadoVista = (String) request.getAttribute("encabezadoVista");
    
    // La URL base de todas las peticiones al servlet de ubicaciones.
    final String urlBase = request.getContextPath() + "/restaurantes";
%>
<body>
    <section class="container">
            
        <div class="row">
          <div class="col-3">
          </div>
            <div class="col">
                
                <h1 class="my-5"><%= encabezadoVista %></h1>
                
                <form method="POST" action="<%= urlBase %>">
                    
                    <!-- 
                        Argumentos implicitos del formulario, que ayudan al Servlet a 
                        identificar el tipo de accion a realizar.
                    -->
                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= RestauranteDAO.COLUMNA_ID %>" value="<%= restaurante.getId() %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <!-- Campos del formulario -->
                    <div class="mb-3">
                        <label for="<%= RestauranteDAO.COLUMNA_NOMBRE %>" class="form-label">Nombre</label>
                        <input 
                            type="text"  
                            class="form-control"
                            name="<%= RestauranteDAO.COLUMNA_NOMBRE %>" 
                            value="<%= hayDatos ? restaurante.getNombre() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= RestauranteDAO.COLUMNA_SITIO_WEB %>" class="form-label">URL de Sitio Web</label>
                        <input 
                            type="url"  
                            class="form-control"
                            name="<%= RestauranteDAO.COLUMNA_SITIO_WEB %>" 
                            placeholder="https://tu-sitio-genial.com" 
                            value="<%= hayDatos ? restaurante.getSitioWeb() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= RestauranteDAO.COLUMNA_NUM_TELEFONICO %>" class="form-label">Número Telefónico</label>
                        <input 
                            type="tel"  
                            class="form-control"
                            name="<%= RestauranteDAO.COLUMNA_NUM_TELEFONICO %>" 
                            placeholder="00-0000-0000"
                            value="<%= hayDatos ? restaurante.getNumTelefonico(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= RestauranteDAO.COLUMNA_TIPO_COCINA %>" class="form-label">Tipo de Cocina</label>
                        
                        <select class="form-select" name="<%= RestauranteDAO.COLUMNA_TIPO_COCINA %>" aria-label="Seleccione un tipo cocina">
                            <% for (TipoDeCocina tipo : TipoDeCocina.values()) { %>
                                <option 
                                    value="<%= tipo.name()%>"
                                    <%= hayDatos && tipo.equals(restaurante.getTipoDeCocina()) ? "selected" : "" %>
                                >
                                    <%= tipo.toString() %>
                                </option>
                            <% } %>
                        </select>
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= RestauranteDAO.COLUMNA_ID_UBICACION %>" class="form-label">Domicilio</label>
                        
                        <select class="form-select" name="<%= RestauranteDAO.COLUMNA_ID_UBICACION %>" aria-label="Selecciona una ubicación">
                            <% for (Ubicacion ubicacion : ubicaciones) { %>
                                <option 
                                    value="<%= ubicacion.getId()%>"
                                    <%= hayDatos && ubicacion.getId() == restaurante.getIdUbicacion()? "selected" : "" %>
                                >
                                    <%= ubicacion.getNombreCalle()%>
                                </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <% if (accionForm.equals(Accion.CREAR)) { %>
                            <input type="submit" class="btn btn-primary" value="Agregar" />

                        <% } else if (accionForm.equals(Accion.ACTUALIZAR)) { %>
                            <input type="submit" class="btn btn-warning" value="Guardar Cambios" />
                        <% } %>
                    </div>

                </form>
            </div>
            <div class="col-3">
            </div>
        </div>
    </section>
                
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
