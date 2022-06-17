<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String urlBase = request.getContextPath();
    
    String llaveParamAccion = AccionAutenticacion.class.getSimpleName().toLowerCase();
    
    String urlAutenticacion = urlBase + "/autenticacion";
    
    String urlInciarSesion = urlAutenticacion + "?" + llaveParamAccion + "=" + AccionAutenticacion.INICIAR_SESION.name();  
    String urlCrearCuenta = urlAutenticacion + "?" + llaveParamAccion + "=" + AccionAutenticacion.CREAR_CUENTA.name();  
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

        <title>Sistema CRUD | Inicio</title>
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
                    <a class="nav-link" href="app">CRUD</a>
                  </li>
                </ul>
              </div>
                  
                <div class="d-flex">
                    <a 
                        class="btn btn-outline-info me-2" 
                        href="<%= urlInciarSesion %>">
                        Iniciar Sesión
                    </a>
                        
                    <a 
                        class="btn btn-outline-primary me-2" 
                        href="<%= urlCrearCuenta %>">
                        Crear Cuenta
                    </a>
                </div>
            </div>
        </nav>
        
        <section class="container">
        
            <div class="row my-5">
                <div class="col">
                    <h1>Pedidos y Envíos de Comida</h1>
                </div>
            </div>
            
            <p class="lead">
                Un Sistema CRUD de restaurantes, pedidos y platillos. 
            </p>
        </section>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    </body>
</html>
