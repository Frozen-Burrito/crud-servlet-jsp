<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rappi.crud.entidades.Pedido"%>
<%@page import="com.rappi.crud.entidades.Usuario"%>
<%@page import="com.rappi.crud.entidades.Restaurante"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.dao.PedidoDAO"%>
<%@page import="com.rappi.crud.dao.RestauranteDAO"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Pedido.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Pedido pedido = (Pedido) request.getAttribute("pedido");
    final boolean hayDatos = pedido != null;
    
    final List<Usuario> clientes = new ArrayList<Usuario>();
    final List<Restaurante> restaurantes = new ArrayList<Restaurante>();
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);
    
    // El título para el formulario.
    final String encabezadoVista = (String) request.getAttribute("encabezadoVista");
    
    // La URL base de todas las peticiones al servlet de ubicaciones.
    final String urlBase = request.getContextPath() + "/app/datos-pedidos";
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
                        <input type="hidden" name="<%= PedidoDAO.COLUMNA_ID %>" value="<%= pedido.getId() %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <!-- Campos del formulario -->
                    <div class="mb-3">
                        <label for="<%= PedidoDAO.COLUMNA_FECHA %>" class="form-label">Fecha</label>
                        <input 
                            type="datetime-local"
                            class="form-control"
                            name="<%= PedidoDAO.COLUMNA_FECHA %>" 
                            value="<%= hayDatos ? pedido.getFecha() : "" %>" 
                        />
                    </div>
                        
                    <p>Incluir campo para seleccionar productos</p>
                        
                    <div class="mb-3">
                        <label for="<%= PedidoDAO.COLUMNA_ID_CLIENTE %>" class="form-label">Cliente</label>
                        
                        <select class="form-select" name="<%= PedidoDAO.COLUMNA_ID_CLIENTE %>" aria-label="Selecciona un cliente">
                            <% for (Usuario usuario : clientes) { %>
                                <option 
                                    value="<%= usuario.getNombreUsuario()%>"
                                    <%= hayDatos && usuario.getNombreUsuario() == pedido.getNombreUsuarioCliente() ? "selected" : "" %>
                                >
                                    <%= usuario.getNombreUsuario()%>
                                </option>
                            <% } %>
                        </select>
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= PedidoDAO.COLUMNA_ID_RESTAURANTE %>" class="form-label">Restaurante Calificado</label>
                        
                        <select class="form-select" name="<%= PedidoDAO.COLUMNA_ID_RESTAURANTE %>" aria-label="Selecciona un restaurante">
                            <% for (Restaurante restaurante : restaurantes) { %>
                                <option 
                                    value="<%= restaurante.getId()%>"
                                    <%= hayDatos && restaurante.getId() == pedido.getIdRestaurante() ? "selected" : "" %>
                                >
                                    <%= restaurante.getNombre() %>
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
