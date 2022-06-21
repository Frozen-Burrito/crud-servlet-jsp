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
        <jsp:include page="./includes/navbar.jsp"></jsp:include>
        
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
