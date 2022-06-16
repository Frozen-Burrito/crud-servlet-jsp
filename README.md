# Sistema CRUD de Envíos de Comida

Un sistema crud web basado desarrollado con Java, MySQL y HTML, usando Servlets y JSPs.

## Requisitos Previos
Los siguientes elementos son necesarios para poder ejecutar el proyecto:

- Java SE 8 o posterior
- Apache NetBeans 12.5 o posterior
- MySQL (MariaDB 10.4.24)
- Servidor GlassFish 4.0 o posterior

## Comenzar

Primero, es necesario obtener los archivos del proyecto ejecutando los siguientes comandos Git:

```bash
git clone https://github.com/Frozen-Burrito/crud-servlet-jsp sistema-crud
```

Esto debería crear el directorio `sistema-crud`, que puede ser abierto como un proyecto en NetBeans.

## Configurar la BD

Hace falta configurar la BD misma desde XAMPP y agregar configuración al proyecto de NetBeans para poder lograr que se conecte a la BD.

1. Crear una base de datos nueva en phpMyAdmin.

2. Descargar el archivo `.sql` de la BD (que está en el drive, para Belén) e importarlo en la base de datos nueva en phpMyAdmin:

![image](https://user-images.githubusercontent.com/59635185/173942695-127b60a9-b270-4ad4-86b0-d6dd0a5c357c.png)

3. Asegurar que el usuario local de la base de datos requiere una contraseña (es necesario para la conexión con NetBeans). Si no tiene contraseña, configurarla en la sección "Editar Privilegios" de la página de la base de datos:

![image](https://user-images.githubusercontent.com/59635185/173956835-3fc420ad-d05d-4458-9c52-4dca33a1fda2.png)

### Crear String de Conexión a BD

1. En NetBeans, en la pestaña de Servicios > Bases de Datos, crear una nueva conexión a una base de datos haciendo click derecho en la sección de databases y seleccionando "Nueva Conexión...":

![image](https://user-images.githubusercontent.com/59635185/173942994-58832f38-8d68-4b10-a1df-0b96af5b71e5.png)

2. Seleccionar "MySQL (Connector / J Driver)" como driver y localizar el archivo `.jar` del driver de MySQL. (Revisar ubicación, porque es determinada por Maven):

![image](https://user-images.githubusercontent.com/59635185/173943793-ede9c63e-0a21-46eb-a3c9-70940ba9545c.png)

3. En la siguiente página, especificar el nombre de la BD en el campo "Base de datos". Asegurar que el username y el password sean correctos. Si la conexión es incorrecta y falla, no podrás avanzar al siguiente paso.

![image](https://user-images.githubusercontent.com/59635185/173944998-e86c5aba-3a07-4933-901d-ff3a47e01b5b.png)

4. Omitir la selección de esquema de base de datos.
5. Elegir un nombre significativo para el string de conexión.

![image](https://user-images.githubusercontent.com/59635185/173946761-d204ab83-557f-4a23-b57a-218bc4ec8be5.png)

6. Hacer click en "Terminar". En la pestaña de Servicios, en la sección de Bases de Datos, debería aparecer la conexión creada, incluyendo las tablas existentes:

![image](https://user-images.githubusercontent.com/59635185/173947716-125c4722-0255-4fa8-88b8-9ff679eab2fd.png)

### Recurso JDBC y Pool de Conexiones 

### Troubleshoot

**No se muestran los recursos JDBC**

En teoría, el archivo `glassfish-resources.xml` es desplegado de forma automática en el servidor de Glassfish, pero no siempre es el caso. Esto puede confirmarse si, desde la pestaña de Servicios en NetBeans, en su sección de `Servidores > Glassfish > Recursos > JDBC > Recursos JDBC` no se muestra el recurso JDBC para el DataSource de la aplicación.

![image](https://user-images.githubusercontent.com/59635185/173955315-82144d6c-9f08-4d04-b594-f5249d0252fd.png)

Si no se muestra `jdbc/dataSourcePrincipal` será necesario subir el archivo `glassfish-resources.xml` manualmente al servidor. Esto se puede hacer de dos maneras:

1. En el servicio de administración de Glassfish en `http://localhost:4848`, navegando a la sección `` y seleccionando el archivo `glassfish-resources.xml` del proyecto.

2. Abriendo un cmd en el directorio de instalación de GlassFish (por ejemplo, "C:\Program Files\glassfish-4.1\bin"), para luego ejecutar el siguiente comando: 

```bash
asadmin add-resources [directorio de glassfish-resources.xml]
```

**MySQL en XAMPP no inicia**

Puede que al intentar iniciar MySQL desde el panel de control de XAMPP, se produzca este error:

![image](https://user-images.githubusercontent.com/59635185/173956256-c2c0c64a-a487-4c71-9d28-d1b8fdd58e92.png)

Para resolverlo, probar si se puede conectar o iniciar MySQL desde el shell del propio XAMPP. Si es así, lo más probable es que haya otra instancia de MySQL ya ejecutándose. 

Si tal cual no se puede solucionar, como último recurso se puede desinstalar y volver a instalar XAMPP.

**Error de Conexión en phpMyAdmin**

Si después de cambiar o configurar la contraseña de usuario para MySQL, sale el siguiente error en phpMyAdmin:

![image](https://user-images.githubusercontent.com/59635185/173956600-11f8078a-ae4d-44b1-ba45-0a26bc3495c9.png)

Abrir el archivo `config.inc.php` desde el panel de control de XAMPP y comprobar que la contraseña (en la línea "password") sea la correcta:

![image](https://user-images.githubusercontent.com/59635185/173956727-4428862e-cc2c-44c8-a8d4-a2a3e486ec1a.png)

![image](https://user-images.githubusercontent.com/59635185/173956769-901bfd09-50df-4345-8092-039179391452.png)

**Excepción WELD-000227**

Si, al estar desarrollando y desplegando la aplicación con cambios, surge el siguiente error HTTP 500:

![image](https://user-images.githubusercontent.com/59635185/173956019-ee1afedc-0eff-4d25-a77d-9718bbce47d5.png)

Para solucionarlo, solo hace falta detener el servidor y volverlo a iniciar.


## Recursos Útiles
[Crear una App Web con Java y MySQL](https://netbeans.apache.org/kb/docs/web/mysql-webapp.html)

[Referencia de API de HttpServlet](https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServlet.html)

[Filtros en Java EE](https://stackoverflow.com/tags/servlet-filters/info)

[Agregar recursos en Glassfish](https://glassfish.org/docs/5.1.0/reference-manual/add-resources.html)
