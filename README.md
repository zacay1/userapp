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

- **spring.datasource.username:** Usuario de la db
- **spring.datasource.password:** Clave de acceso de la db
- **spring.h2.console.enabled:** Bandera que habilita la consola de  la db h2
- **spring.h2.console.path:** Dirección donde será levanta la interfaz de la consola h2
- **springdoc.swagger-ui.path:** Direccion de acceso de la documentación en swagger
- **jwt.secret:** Secreto con el que serán construidos los token de JWT
- **user.password.regex:** Expresión regular con la que se verificara el formato correcto de los passwords
- **server.servlet.context-path:** Contexto de la aplicación 
- **server.port:** Puerto donde será levantada la aplicación 
- **show.internal.api:** Allows you to hide the api Users of the documentation, useful when you want to share only the public documentation to the users.

El archivo schema.sql contiene los query de creación de las tablas asociadas al proyecto 

## Instrucciones para iniciar la aplicación

- Descargar proyecto: git clone https://gitlab.com/josemiguelhidalgo74/userapp.git
- Moverse al directorio: userapp
- Ejecutar: mvn spring-boot:run
- Ir a: http://localhost:8080/company/docs/ui.html

## Descripción de elementos en la documentación 
swagger nos permite establecer algunas configuración que permiten hacer mas dinámica la documentación. En la siguiente imagen se pueden observar 2 de estos elementos.  

![](https://gitlab.com/josemiguelhidalgo74/userapp/-/raw/main/ui.jpg)

- El primero nos permite movernos entre las distintas versiones, este proyecto tiene 2, una interna identificado con **i1** y una publica identificada con **v1**.
- El segundo elementó nos permite establecer un token, en este proyecto son tokens de jwt, la configuración del swagger entiende esto y internamente el agrega el texto **Bearer** al principio del token antes de realizar una llamada

**Nota:** El método login del Api Myself y el Api interna de Users no requiere un token. 