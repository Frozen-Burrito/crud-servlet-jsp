<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String urlBase = request.getContextPath() + "/app";
    
    String llaveParamAccion = AccionAutenticacion.class.getSimpleName().toLowerCase();
    
    String urlAutenticacion = request.getContextPath() + "/autenticacion?" + llaveParamAccion + "=" + AccionAutenticacion.CERRAR_SESION.name();  
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

        <title>Sistema CRUD de Comida</title>
    </head>
    
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
              <a class="navbar-brand" href="<%= urlBase %>">Rappi-CRUD</a>
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
                    <h1>Sistema CRUD</h1>
                </div>
            </div>
            
            <p class="lead">
                Realiza operaciones CRUD con los datos. 
            </p>
        </section>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    </body>
</html>
