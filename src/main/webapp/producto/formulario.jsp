<%@page import="com.rappi.crud.entidades.TipoDeProducto"%>
﻿﻿﻿﻿<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.dao.ProductoDAO"%>
<%@page import="com.rappi.crud.entidades.Producto"%>
<%@ page import="com.rappi.crud.entidades.Datos" %>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title>Países | Detalles</title>
</head>
<%
    final Producto producto = (Producto) request.getAttribute("producto");

    String codigoProducto = request.getParameter(ProductoDAO.COLUMNA_ID);
    
    System.out.println(codigoProducto);
    final boolean hayDatos = producto != null;
    
    String parametroAccion = Accion.class.getSimpleName().toLowerCase();
    final Accion accionForm = (Accion) request.getAttribute(parametroAccion);
    
    String tituloPagina = accionForm.equals(Accion.ACTUALIZAR)
                ? "Edita un " + Producto.NOMBRE_ENTIDAD
                : "Agrega un Nuevo " + Producto.NOMBRE_ENTIDAD;
    
    // La URL base de todas las peticiones al servlet de ubicaciones.
    final String urlBase = request.getContextPath() + "/Productos";

%>
<body>
    <section class="container">
            
        <div class="row">
          <div class="col-3">
          </div>
            <div class="col">
                
                <h1 class="my-5"><%= tituloPagina %></h1>
                
                <form method="POST" action="<%= urlBase %>">

                    <% if (hayDatos) { %>
                        <input type="hidden" name="<%= ProductoDAO.COLUMNA_ID %>" value="<%= codigoProducto %>" />
                    <% } %>

                    <input type="hidden" name="<%= parametroAccion %>" value="<%= accionForm.toString() %>"/>

                    <div class="mb-3">
                        <label for="<%= ProductoDAO.COLUMNA_ID_RESTAURANTE %>"  class="form-label">Id del restaurante</label>
                        <input
                            type="text" 
                            id="id-restaurante" 
                            class="form-control"
                            name="<%= ProductoDAO.COLUMNA_ID_RESTAURANTE %>" 
                            placeholder="Id del restaurante" 
                            value="<%= hayDatos ? producto.getIdRestaurante() : "" %>" 
                        />
                    </div>
                    
                    <div class="mb-3">
                        <label for="<%= ProductoDAO.COLUMNA_NOMBRE %>"  class="form-label">Nombre</label>
                        <input
                            type="text" 
                            id="nombre-producto" 
                            class="form-control"
                            name="<%= ProductoDAO.COLUMNA_NOMBRE %>" 
                            placeholder="Nombre del producto" 
                            value="<%= hayDatos ? producto.getNombre() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= ProductoDAO.COLUMNA_DISPONIBLE %>"  class="form-label">¿Está disponible?</label>
                        <select class="form-select" name="<%= ProductoDAO.COLUMNA_DISPONIBLE %>" aria-label="¿Está disponible?">
                                <option 
                                    value=<%= true %>
                                    <%= hayDatos && producto.isDisponible() == true ? "selected": "" %>>
                                    <%= "Sí" %>
                                </option>
                                
                                 <option 
                                    value=<%= false %>
                                    <%= hayDatos && producto.isDisponible() == false ? "selected": "" %>>
                                    <%=  "No" %>
                                </option>   
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label for="<%= ProductoDAO.COLUMNA_KCAL_PORCION %>"  class="form-label">Kcal por porcion</label>
                        <input
                            type="text" 
                            id="kcalPorcion-producto" 
                            class="form-control"
                            name="<%= ProductoDAO.COLUMNA_KCAL_PORCION  %>" 
                            placeholder="Kcal por porción" 
                            value="<%= hayDatos ? producto.getKcalPorcion() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= ProductoDAO.COLUMNA_TAMAÑO_PORCION %>"  class="form-label">Tamaño de porcion</label>
                        <input
                            type="text" 
                            id="tamañoPorcion-producto" 
                            class="form-control"
                            name="<%= ProductoDAO.COLUMNA_TAMAÑO_PORCION %>" 
                            placeholder="Tamaño de porción" 
                            value="<%= hayDatos ? producto.getTamañoPorcion() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= ProductoDAO.COLUMNA_DESCRIPCION %>"  class="form-label">Descripción</label>
                        <input
                            type="text" 
                            id="descripcion-producto" 
                            class="form-control"
                            name="<%= ProductoDAO.COLUMNA_DESCRIPCION %>" 
                            placeholder="Descripción de producto" 
                            value="<%= hayDatos ? producto.getDescripcion() : "" %>" 
                        />
                    </div>
                        
                    <div class="mb-3">
                        <label for="<%= ProductoDAO.COLUMNA_PRECIO %>"  class="form-label">Precio</label>
                        <input
                            type="text" 
                            id="precio-producto" 
                            class="form-control"
                            name="<%= ProductoDAO.COLUMNA_PRECIO %>" 
                            placeholder="Precio de producto" 
                            value="<%= hayDatos ? producto.getPrecio() : "" %>" 
                        />
                    </div>
                    
                    <div class="mb-3">
                        <label for="<%= ProductoDAO.COLUMNA_CODIGO_MONEDA %>"  class="form-label">Código de moneda</label>
                        <input
                            type="text" 
                            id="codigoMoneda-producto" 
                            class="form-control"
                            name="<%= ProductoDAO.COLUMNA_CODIGO_MONEDA %>" 
                            placeholder="Código de moneda" 
                            value="<%= hayDatos ? producto.getCodigoMoneda() : "" %>" 
                        />
                    </div>
                    
                    <div class="mb-3">
                        <label for="<%= ProductoDAO.COLUMNA_TIPO_DE_PRODUCTO %>"  class="form-label">Tipo de producto</label>
                        <select class="form-select" name="<%= ProductoDAO.COLUMNA_TIPO_DE_PRODUCTO %>" aria-label="Seleccione un tipo de producto">
                            <% for (TipoDeProducto tipo : TipoDeProducto.values()) { %>
                                <option 
                                    value="<%= tipo.name()%>"
                                    <%= hayDatos && tipo.equals(producto.getTipoDeProducto()) ? "selected" : "" %>
                                >
                                    <%= tipo.toString() %>
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
