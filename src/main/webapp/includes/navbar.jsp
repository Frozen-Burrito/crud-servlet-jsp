<%@page import="com.rappi.crud.dao.UsuarioDAO"%>
<%@page import="com.rappi.crud.servlets.AccionAutenticacion"%>
<%
    // Evaluar si el usuario ha iniciado sesion.
    HttpSession sesionHttp = request.getSession(false);
    
    String idUsuario = (String) sesionHttp.getAttribute(UsuarioDAO.COLUMNA_ID);
    
    boolean usuarioAutenticado = idUsuario != null;
    
    // URLs base del navbar
    String llaveParamAccion = AccionAutenticacion.class.getSimpleName().toLowerCase();
    
    String urlBase = request.getContextPath() + "/app";
    String urlAutenticacion = urlBase + "/autenticacion";
           
    String llaveParamAccionAuth = AccionAutenticacion.class.getSimpleName().toLowerCase();
    String urlCerrarSesion = urlAutenticacion + "?" + llaveParamAccionAuth + "=" + AccionAutenticacion.CERRAR_SESION.name();  

    String urlInciarSesion = urlAutenticacion + "?" + llaveParamAccion + "=" + AccionAutenticacion.INICIAR_SESION.name();  
    String urlCrearCuenta = urlAutenticacion + "?" + llaveParamAccion + "=" + AccionAutenticacion.CREAR_CUENTA.name(); 
%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<%= urlBase %>">Rappi-CRUD</a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            
            <% if (usuarioAutenticado) { %>
            <li class="nav-item">
              <a class="nav-link active" aria-current="page" href="<%= urlBase %>">Inicio</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="<%= urlBase %>/usuarios">Usuarios</a>
            </li>

            <!-- Sub-menu de restaurantes -->
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Restaurantes
              </a>
              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="<%= urlBase %>/restaurantes">Todos</a></li>
                <li><a class="dropdown-item" href="<%= urlBase %>/datos-pedidos">Pedidos</a></li>
                <li><a class="dropdown-item" href="<%= urlBase %>/reviews">Reseñas</a></li>
                <li><a class="dropdown-item" href="<%= urlBase %>/reviews">Productos</a></li>
                <li><a class="dropdown-item" href="<%= urlBase %>/reviews">Orfertas</a></li>
                <li><a class="dropdown-item" href="<%= urlBase %>/reviews">Ingredientes</a></li>
              </ul>
            </li>
          
            <!-- Sub-menu de ubicaciones -->
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
            
            <% } else { %>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="<%= urlBase %>">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="app">CRUD</a>
                </li>
            <% } %>
        </ul>
      </div>
            
        <div class="d-flex">
            <% if (usuarioAutenticado) { %>
                <a 
                    class="btn btn-outline-danger me-2" 
                    href="<%= urlCerrarSesion  %>">
                    Cerrar Sesión
                </a>
            <% } else { %>
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
            <% } %>
        </div>
    </div>
</nav>