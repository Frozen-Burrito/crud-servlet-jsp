<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%@page import="com.rappi.crud.dao.UsuarioDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String urlServletRegistro = request.getContextPath() + "/autenticacion";
    
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
        
        <!-- Font Icon -->
        <link rel="stylesheet" href="autenticacion/fonts/material-icon/css/material-design-iconic-font.min.css">
        
        <!-- CSS Principal -->
        <link rel="stylesheet" href="autenticacion/css/style.css">
        
        <title>Crear Cuenta</title>
    </head>
    <body>
        <input type="hidden" id="status" value="<%= request.getAttribute("status") %>">

	<div class="main">
            <!-- Sign up form -->
            <section class="signup">
                <div class="container">
                    <div class="signup-content">
                        <div class="signup-form">
                            <h2 class="form-title">Crear cuenta</h2>

                            <form method="POST" action="<%= urlServletRegistro %>?<%= paramAccionSignup %>" class="register-form"
                                id="register-form">
                                
                                <input 
                                    type="hidden" 
                                    id="<%= llaveParamAccion %>" 
                                    value="<%= AccionAutenticacion.CREAR_CUENTA.toString() %>"
                                >
                                
                                <div class="form-group">
                                    <label for="name"><i
                                            class="zmdi zmdi-account material-icons-name"></i></label> 
                                            <input 
                                                type="text" 
                                                name="<%= UsuarioDAO.COLUMNA_ID %>" 
                                                id="name" 
                                                placeholder="Nombre de usuario" 
                                            />
                                </div>
                                <div class="form-group">
                                    <label for="<%= UsuarioDAO.COLUMNA_EMAIL %>">
                                        <i class="zmdi zmdi-email"></i>
                                    </label> 
                                    <input
                                        type="email" 
                                        name="<%= UsuarioDAO.COLUMNA_EMAIL %>" 
                                        id="<%= UsuarioDAO.COLUMNA_EMAIL %>" 
                                        placeholder="Email" 
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
                                    <label for="re-pass"><i class="zmdi zmdi-lock-outline"></i></label>
                                    <input type="password" name="re_pass" id="re_pass"
                                            placeholder="Confirma tu contraseña" />
                                </div>
                                <div class="form-group">
                                    <label for="<%= UsuarioDAO.COLUMNA_NUM_TELEFONICO %>">
                                        <i class="zmdi zmdi-lock-outline"></i>
                                    </label>
                                    <input 
                                        type="text" 
                                        name="<%= UsuarioDAO.COLUMNA_NUM_TELEFONICO %>" 
                                        id="<%= UsuarioDAO.COLUMNA_NUM_TELEFONICO %>"
                                        placeholder="Número Telefónico" 
                                    />
                                </div>
                                <div class="form-group">
                                    <input type="checkbox" name="agree-term" id="agree-term"
                                            class="agree-term" /> <label for="agree-term"
                                            class="label-agree-term"><span><span></span></span>
                                            Estoy de acuerdo con todos los <a href="#" class="term-service">Términos
                                                    de servicio</a></label>
                                </div>
                                <div class="form-group form-button">
                                    <input 
                                        type="submit"
                                        class="form-submit" 
                                        value="Crear Cuenta" 
                                    />
                                </div>
                            </form>
                        </div>
                        <div class="signup-image">
                            <figure>
                                    <img src="autenticacion/images/signup-image.jpg" alt="Imagen de sign up">
                            </figure>
                            <a href="<%= urlServletRegistro %>?<%= paramAccionLogin %>" class="signup-image-link">Ya tengo una cuenta</a>
                        </div>
                    </div>
                </div>
            </section>
	</div>
        
	<!-- JS -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="js/main.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<link rel="stylesheet" href="alert/dist/sweetalert.css">
	
	<script type="text/javascript">
	
		var status = document.getElementById("status").value;
		
		if(status === "success"){
			
			swal("Felicidades", "Cuenta Creada Exitosamente", "success");
		}
	
	</script>
    </body>
</html>
