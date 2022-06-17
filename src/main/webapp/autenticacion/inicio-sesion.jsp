<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page import="com.rappi.crud.dao.UsuarioDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String urlServletLogin = request.getContextPath() + "/autenticacion";
    String llaveParamAccion = AccionAutenticacion.class.getSimpleName().toLowerCase();
    
    String paramAccionLogin = llaveParamAccion + "=" + AccionAutenticacion.INICIAR_SESION.toString();
    String paramAccionSignup = llaveParamAccion + "=" + AccionAutenticacion.CREAR_CUENTA.toString();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        
        <link rel="stylesheet" href="alert/dist/sweetalert.css">
        
        <!-- Font Icon -->
        <link rel="stylesheet" href="autenticacion/fonts/material-icon/css/material-design-iconic-font.min.css">

        <!-- CSS Principal -->
        <link rel="stylesheet" href="autenticacion/css/style.css">
        
        <title>Iniciar Sesión</title>
    </head>
    <body>
        <input type="hidden" id="status" value="<%= request.getAttribute("status") %>">

	<div class="main">
            <section class="sign-in">
                <div class="container">
                    <div class="signin-content">
                        <div class="signin-image">
                            <figure>
                                    <img src="autenticacion/images/signin-image.jpg" alt="Imagen de inicio de sesion">
                            </figure>
                            <a 
                                href="<%= urlServletLogin %>?<%= paramAccionSignup %>" 
                                class="signup-image-link">
                                Crear una cuenta
                            </a>
                        </div>

                        <div class="signin-form">
                            <h2 class="form-title">Iniciar Sesión</h2>
                            
                            <form method="POST" action="<%= urlServletLogin %>?<%= paramAccionLogin %>" class="register-form" id="login-form">
                                
                                    <div class="form-group">
                                        <label for="<%= UsuarioDAO.COLUMNA_ID %>">
                                            <i class="zmdi zmdi-account material-icons-name"></i>
                                        </label> 

                                        <input
                                            type="text" 
                                            name="<%= UsuarioDAO.COLUMNA_ID %>" 
                                            id="<%= UsuarioDAO.COLUMNA_ID %>"
                                            placeholder="Nombre de usuario" 
                                        />
                                    </div>
                                    <div class="form-group">
                                        <label for="<%= UsuarioDAO.COLUMNA_PASSWORD %>">
                                            <i class="zmdi zmdi-lock"></i>
                                        </label> 
                                        <input
                                            type="password" 
                                            name="<%= UsuarioDAO.COLUMNA_PASSWORD %>"
                                            id="<%= UsuarioDAO.COLUMNA_PASSWORD %>"
                                            placeholder="Contraseña" 
                                        />
                                    </div>
                                    <div class="form-group">
                                            <input 
                                                type="checkbox" 
                                                name="remember-me" 
                                                id="remember-me"
                                                class="agree-term" 
                                            /> 
                                            <label 
                                                for="remember-me"
                                                class="label-agree-term">
                                                <span><span></span></span>Recordarme
                                            </label>
                                    </div>
                                    <div class="form-group form-button">
                                        <input 
                                            type="submit" 
                                            class="form-submit" 
                                            value="Continuar" 
                                        />
                                    </div>
                            </form> <!-- Formulario para inicio de sesion -->
                            
                            <div class="social-login">
                                <span class="social-label">Iniciar Sesión con</span>
                                <ul class="socials">
                                    <li>
                                        <a href="#">
                                            <i class="display-flex-center zmdi zmdi-facebook"></i>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="display-flex-center zmdi zmdi-twitter"></i>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="display-flex-center zmdi zmdi-google"></i>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
	</div>
                                            
        <!-- JS -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="autenticacion/js/main.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	
	<script type="text/javascript">
	
		var status = document.getElementById("status").value;
		
		if(status === "failed"){
			
                    swal("Lo siento", "Nombre de usuario o contrase�a incorrecta", "error");
		}
	
	</script>
    </body>
</html>
