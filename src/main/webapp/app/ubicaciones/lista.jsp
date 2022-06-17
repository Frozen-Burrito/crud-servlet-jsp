<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page import="com.rappi.crud.dao.UbicacionDAO"%>
<%@page import="com.rappi.crud.entidades.Ubicacion"%>
<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.entidades.Municipio"%>
<%@ page import="com.rappi.crud.dao.MunicipioDAO"%>
<%@ page import="com.rappi.crud.servlets.Accion"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Ubicacion> ubicaciones = (List<Ubicacion>) request.getAttribute("ubicaciones");
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlUbicaciones = urlBase + "/ubicaciones";
    
    final String formatoUrlAccion = "%s?%s=%s";
    
    String urlDetalles = String.format(formatoUrlAccion, urlUbicaciones, keyParamAccion, Accion.LEER);
    String urlNuevo = String.format(formatoUrlAccion, urlUbicaciones, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlUbicaciones, keyParamAccion, Accion.ACTUALIZAR);
    
    String llaveParamAccionAuth = AccionAutenticacion.class.getSimpleName().toLowerCase();
    String urlAutenticacion = request.getContextPath() + "/autenticacion?" + llaveParamAccionAuth + "=" + AccionAutenticacion.CERRAR_SESION.name();  
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title><%= Ubicacion.NOMBRE_ENTIDAD %> | Vista General</title>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
          <a class="navbar-brand" href="<%= urlUbicaciones %>">Rappi-CRUD</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="<%= urlBase %>">Inicio</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="<%= urlBase %>/usuarios">Usuarios</a>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Restaurantes
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <li><a class="dropdown-item" href="<%= urlBase %>/restaurantes">Todos</a></li>
                  <li><a class="dropdown-item" href="<%= urlBase %>/reviews">Reseñas</a></li>
                </ul>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Ubicación
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <li><a class="dropdown-item" href="<%= urlBase %>/paises">Países</a></li>
                  <li><a class="dropdown-item" href="<%= urlBase %>/estados">Estados</a></li>
                  <li><a class="dropdown-item" href="<%= urlBase %>/datos-municipios">Municipios</a></li>
                  <li><a class="dropdown-item" href="<%= urlBase %>/datos-colonias">Colonias</a></li>
                  <li><hr class="dropdown-divider"></li>
                  <li><a class="dropdown-item" href="<%= urlBase %>/ubicaciones">Ubicaciones</a></li>
                </ul>
              </li>
            </ul>
          </div>

            <div class="d-flex">
                <a 
                    class="btn btn-outline-danger me-2" 
                    href="<%= urlAutenticacion %>">
                    Cerrar Sesión
                </a>
            </div>
        </div>
    </nav>
                    
    <section class="container">
        
        <div class="row my-5">
            <div class="col">
                <h1><%= Ubicacion.NOMBRE_ENTIDAD %></h1>
            </div>
            <div class="col-8">
            </div>
            <div class="col">
                <a 
                    class="btn btn-primary"
                    href="<%= urlNuevo %>"
                >
                    Nuevo
                </a>
            </div>
        </div>
        
        <table class="table table-hover">
            <tr class="table-header">
                <th scope="col">ID</th>
                <th scope="col">Nombre de la Calle</th>
                <th scope="col">Número Exterior</th>
                <th scope="col">Número Interior</th>
                <th scope="col">Colonia</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Ubicacion ubicacion : ubicaciones) { %>
                <tr>
                    <th scope="row">                        
                        <a href="<%= urlDetalles %>&<%= UbicacionDAO.COLUMNA_ID %>=<%= ubicacion.getId() %>">
                            <%= ubicacion.getId() %>
                        </a>
                    </th>

                    <td><%= ubicacion.getNombreCalle()%></td>
                    <td><%= ubicacion.getNumExterior()%></td>
                    <td><%= ubicacion.getNumInterior()%></td>
                    
                    <td><%= ubicacion.getColonia() != null ? ubicacion.getColonia().getNombre() : "No se encontró la colonia de la ubicación"%></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>&<%= UbicacionDAO.COLUMNA_ID %>=<%= ubicacion.getId() %>">
                                Editar
                            </a>

                            <form method="POST" action="<%= urlUbicaciones %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="<%= Accion.ELIMINAR.toString() %>" />
                                <input type="hidden" name="<%= UbicacionDAO.COLUMNA_ID %>" value="<%= ubicacion.getId() %>"/>

                                <input class="btn btn-danger" type="submit" value="Eliminar"/>
                            </form>
                        </div>
                    </td>
                </tr>
            <% } %>
        </table>
    </section>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

</body>
</html>
