<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Stream"%>
﻿
<%@page import="com.rappi.crud.dao.OfertaDAO"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="java.util.List"%>
<%@page import="com.rappi.crud.entidades.Producto"%>
<%@page import="com.rappi.crud.entidades.Oferta"%>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Oferta.NOMBRE_ENTIDAD %> | Detalles</title>
</head>
<%
    final Oferta oferta = (Oferta) request.getAttribute("oferta");
    final boolean hayDatos = oferta != null;
    
    final List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(keyParamAccion);
    String nombreDeOferta = request.getParameter(OfertaDAO.COLUMNA_ID);
    
    // El título para el formulario.
    final String encabezadoVista = (String) request.getAttribute("encabezadoVista");
    
    // La URL base de todas las peticiones al servlet de productos.
    final String urlBase = request.getContextPath() + "/ofertas";
%>
<body>
    <section class="container">
            
        <div class="row">
          <div class="col-3">
          </div>
            <div class="col">
                
                <h1 class="my-5"><%= encabezadoVista %></h1>
                
                <form method="POST" action="<%= urlBase %>">

                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= OfertaDAO.COLUMNA_ID %>" value="<%= nombreDeOferta %>" />
                    <% } %>

                    <input type="hidden" name="<%= keyParamAccion %>" value="<%= accionForm.toString() %>"/>

                    <div class="mb-3">
                        <label for="<%= OfertaDAO.COLUMNA_ID %>" class="form-label">Nombre de Oferta</label>
                        <input 
                            type="text"  
                            class="form-control"
                            name="<%= OfertaDAO.COLUMNA_ID %>" 
                            value="<%= hayDatos ? oferta.getIdOferta(): "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= OfertaDAO.COLUMNA_PORCENTAJE_DESCUENTO %>" class="form-label">Porcentaje de descuento</label>
                        <input 
                            type="text"  
                            class="form-control"
                            name="<%= OfertaDAO.COLUMNA_PORCENTAJE_DESCUENTO%>" 
                            placeholder="#" 
                            value="<%= hayDatos ? oferta.getPorcentajeDescuento() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= OfertaDAO.COLUMNA_FECHA_TERMINO %>" class="form-label">Fceha de término</label>
                        <input 
                            type="date"  
                            class="form-control"
                            name="<%= OfertaDAO.COLUMNA_FECHA_TERMINO%>" 
                            placeholder="#" 
                            value="<%= hayDatos ? oferta.getFechaTermino() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= OfertaDAO.COLUMNA_ID_PRODUCTO %>" class="form-label">Id de Producto</label>
                        
                        <select class="form-select" name="<%= OfertaDAO.COLUMNA_ID_PRODUCTO %>" aria-label="Selecciona tu ubicación">
                            <% for (Producto producto : productos) { %>
                                <option 
                                    value="<%= producto.getIdProducto()%>"
                                    <%= hayDatos && producto.getIdProducto() == oferta.getIdProducto()? "selected" : "" %>
                                >
                                    <%= producto.getNombre()%>
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