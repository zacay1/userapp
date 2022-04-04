# UserApp

Sistema de administración de usuarios 

## Descripción
Básica mente esta aplicación ofrece 2 APIs, las cuales son:
- Api interna Users: Permite administrar usuarios del sistema.
- Api publica Myself: Permite que los usuarios realicen operaciones sobre sus cuentas: Inicio de sesión y Actualización de Password

## Componentes utilizados

- Implementación con Spring boot y java 11.
- Autenticación basada en token con jwt.
- Documentación con swagger.
- Conexión con base de datos en memoria usamdo H2.
- Pruebas unitarias con junit 5 y mockito. 

## Configuración 
Los parámetros de configuración se encuentren en el directorio config. 

Parámetros relevantes de la application.properties

- spring.datasource.username= usuario de la db
- spring.datasource.password= clave de acceso de la db
- spring.h2.console.enabled= bandera que habilita la consola de  la db h2
- spring.h2.console.path= dirección donde será levanta la interfaz de la consola h2
- springdoc.swagger-ui.path=direccion de acceso de la documentación en swagger
- jwt.secret= secreto con el que serán construidos los token de JWT
- user.password.regex= expresión regular con la que se verificara el formato correcto de los passwords
- server.servlet.context-path=contexto de la aplicación 
- server.port=puerto donde será levantada la aplicación 

El archivo schema.sql contiene los query de creación de las tablas asociadas al proyecto 

## Instrucciones para iniciar la aplicación

- Descargar proyecto: git clone https://gitlab.com/josemiguelhidalgo74/userapp.git
- Moverse al directorio: userapp
- Ejecutar: mvn spring-boot:run
- Ir a: http://localhost:8080/company/docs/ui.html
