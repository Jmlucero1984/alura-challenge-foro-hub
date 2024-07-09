# alura-challenge-foro-hub
Cuarto desafío del curso de Backend de Alura Latam


## 🎯 Funcionalidades implementadas
- Requerimientos mínimos planteados en el desafío según reglas del negocio:
 - [x] Registrar un tópico de discusión
 - [x] Retornar un tópico según su id
 - [x] Listado paginado de los tópicos
 - [x] Listar tópicos según el titulo del curso al que estan asociados
 - [x] Listar tópicos según el año de creación
 - [x] Registrar una respuesta a un tópico determinado
 - [x] Registrar un nuevo usuario
 - [x] Autenticación de usuarios registrados 
- Validación de contraseña segura mediante regex, requiriendo cantidad mínima, alfanumérica y caracteres especiales.
- Persistencia permanente de datos a DB.
- Test de ejemplo de la funcionalidad de 'Buscar tópicos por año'.
- Documentación de la api con Swagger.
- Migraciones con FlyWay incluyendo fake data para corroborar funcionalidades.
- Modelado de la relación más compleja propuesta: Many-To-Many entre Usuarios y Perfiles
 
## 🔎 Requisitos
- JAVA 17
- Maven 3.3.2
- Git

## 📷 Screenshots
*SWAGGER-UI: Documentación ordenada de la API con etiquetas y autenticación disponible.* 
![swagger_screen_capture](https://github.com/Jmlucero1984/alura-challenge-foro-hub/assets/91501518/e1fe63af-eec1-4591-bbab-25c2e350730a)

*Diagrama de Entidades y Relaciones: Mostrando tabla intermedia entre Usuarios y Perfiles para la relación Many To Many.* 
![dbeaver_db_diagram](https://github.com/Jmlucero1984/alura-challenge-foro-hub/assets/91501518/a87b226e-e6b5-41f0-a068-c365ab9bf127)

*JUNIT 5 Test: Test parametrizado de una de las queries de consulta.* 
![test_screen_capture](https://github.com/Jmlucero1984/alura-challenge-foro-hub/assets/91501518/02d891fb-32e7-439c-8cd6-00136de42751)


## 🔩 Configuración
Para poder conectar con una base de datos local se deben configurar los siguientes parámetros en el archivo src/main/resources/application.properties.

spring.datasource.url=jdbc:mysql://{tu_host}:3306/{tu_base_de_datos}

spring.datasource.username= {tu_nombre_usuario} 

spring.datasource.password= {tu_password_usuario}

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect


🤘 Si tienes DOCKER puede rápidamente crear tu container de MySQL y levantarlo en local, pull de la imagen incluido, con la siguiente línea de comando en tu CMD o Bash 
(reemplazando los parámetros en concordancia con lo detallado en el application.properties, obviamente sin colocar las llaves {}, sólo las variables, siendo tu_host el valor 'localhost')
docker run --name {nombre_que_gustes_para_tu_contenedor} -p 3306:3306  -e MYSQL_ROOT_PASSWORD={tu_password_usuario}  -e MYSQL_DATABASE={tu_base_de_datos} mysql
PD: Por cierto, el usuario por defecto es 'root'.
 
 
