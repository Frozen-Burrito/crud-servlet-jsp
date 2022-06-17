<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page import="com.rappi.crud.servlets.Accion"%>
<%@page import="com.rappi.crud.entidades.Pais"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List"%>
<%@ page import="com.rappi.crud.entidades.Pais" %>
<%@ page import="com.rappi.crud.dao.PaisDAO" %>

<%
    List<Pais> paises = (List<Pais>) request.getAttribute("paises");
    
    // La URL base de todas las peticiones a paises.
    String urlBase = request.getContextPath() + "/app";
    String urlPaises = urlBase + "/paises";
    
    final String keyParamAccion = Accion.class.getSimpleName().toLowerCase();
    final String formatoUrlAccion = "%s?%s=%s";

    String urlDetalles = String.format(formatoUrlAccion, urlPaises, keyParamAccion, Accion.LEER);
    String urlNuevoEstado = String.format(formatoUrlAccion, urlPaises, keyParamAccion, Accion.CREAR);
    String urlEditar = String.format(formatoUrlAccion, urlPaises, keyParamAccion, Accion.ACTUALIZAR);
    
    String llaveParamAccionAuth = AccionAutenticacion.class.getSimpleName().toLowerCase();
    String urlAutenticacion = request.getContextPath() + "/autenticacion?" + llaveParamAccionAuth + "=" + AccionAutenticacion.CERRAR_SESION.name();  
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <title>Países | Vista General</title>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
          <a class="navbar-brand" href="<%= urlPaises %>">Rappi-CRUD</a>
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
                <h1>Países</h1>
            </div>
            <div class="col-8">
            </div>
            <div class="col">
                <a 
                    class="btn btn-primary"
                    href="<%= urlNuevoEstado %>"
                >
                    Nuevo
                </a>
            </div>
        </div>
        
        <table class="table table-hover">
            <tr class="table-header">
                <th scope="col">Código de País</th>
                <th scope="col">Nombre</th>
                <th scope="col">Acciones</th>
            </tr>

            <% for (Pais pais : paises) { %>
                <tr>
                    <th scope="row">
                        <a href="<%= urlDetalles %>?<%= PaisDAO.COLUMNA_ID %>=<%= pais.getCodigoPais()%>">
                            <%= pais.getCodigoPais()%>
                        </a>
                    </th>

                    <td><%= pais.getNombre() %></td>

                    <td class="td-acciones">
                        <div class="hstack gap-3">
                            <a 
                                class="btn btn-warning"
                                href="<%= urlEditar %>?<%= PaisDAO.COLUMNA_ID %>=<%= pais.getCodigoPais() %>">
                                Editar
                            </a>

                            <!-- TODO: Usar DELETE para esto. -->
                            <form method="POST" action="<%= urlPaises %>" style="margin-bottom: 0px">
                                <input type="hidden" name="<%= keyParamAccion %>" value="${Accion.ELIMINAR.toString()}" />
                                <input type="hidden" name="<%= PaisDAO.COLUMNA_ID %>" value="<%= pais.getCodigoPais() %>"/>

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
