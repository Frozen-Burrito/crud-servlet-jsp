<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String variableJava = "Esto es un mensaje desde Java.";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prueba de Datos</title>
    </head>
    <body>
        <h1>Hola Mundo!</h1>
        
        <a href="pruebas">Ir a CRUD de Pruebas</a>
        <script type="text/javascript">
            console.log("Esto es un mensaje desde JS.");

            let mensaje = "<%= variableJava %>";

            console.log(`Java dice: `, mensaje);
    //        console.log(`Java dice: ${mensaje}`);
        </script>
    </body>
</html>
