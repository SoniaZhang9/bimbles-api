# PFG | ETSISI - UPM 
***Implementación de un sistema de recomendación basado en el filtrado colaborativo para la asistencia de personas alergicas o con necesidades especiales***

## Intalación del proyecto
Para poder ejecutar el proyecto, primero de todo se necesita descargar lo siguiente:
### Descarga de SDK 17
1. Ir a enlace de [descarga de JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. Bajo el apartado *"Java SE Development Kit"*, elegir el archivo instalador (*installer*)
que se corresponda con su sistema operativo.
3. Se le va a redireccionar a otra página donde se le pedirá que inicie
sesión o crear una cuenta para poder iniciar la descarga.
4. Ejecutar el archivo .exe
5. Comprobacion: abrir su intérprete de comandos (cmd) y escribir
`java -version`

### Descarga MySQL
1. Ir a la [dirección](https://dev.mysql.com/downloads) y descargar **MySQL
Installer for Windows**
2. Elegir el instalador que pesa 431M
3. Aparecerá una página como la mostrada en la siguiente imagen. Hacer
click en el link señalado.
![sql-2](https://user-images.githubusercontent.com/78765878/217398050-7fee4231-b5e1-4b06-ad36-4d7c201283ce.PNG)
4. Ejecutar el archivo .exe que se ha descargado.
5. Seleccionar la opcion señalada en la imagen y dar siempre a siguiente: 
![sql-3](https://user-images.githubusercontent.com/78765878/217398149-24e4cabc-b6b3-4120-b91b-608ed757d059.PNG)
6. Al llegar aquí, le tenemos que dar una contraseña al root. La contraseña debe coincidir con lo configurado en el fichero `application.propperties` del proyecto. En nuestro caso, tanto el usuario como contraseña tienen el valor `root`. Después damos a siguiente y seguimos el proceso hasta terminar.
![sql-7](https://user-images.githubusercontent.com/78765878/217398747-27b434dc-34a0-42e4-9804-4060243068ff.PNG)
![sql-9](https://user-images.githubusercontent.com/78765878/217401882-8176686d-e772-4887-8190-3ab4cce47cdb.PNG)

### Abrir en su IDE correspondinte
1. Instalar las dependencias del proyecto con `mvn install` o a través de su IDE: 

En **IntelliJ IDEA**

![maven](https://user-images.githubusercontent.com/78765878/217403162-bcb299db-28c7-4d18-9f17-22da0e6f41ae.PNG)
*hola*

En **Eclipse Spring Tool Suite 4**

![eclip1](https://github.com/SoniaZhang9/bimbles-api/assets/78765878/cacdbc35-a5bb-4021-b17b-4c93a4f1cafa)

2. Ejecutar `BimblesApiApplication.java`

En **IntelliJ IDEA**

![maven2](https://user-images.githubusercontent.com/78765878/217403161-e4ffab47-cc41-4064-b88d-e68cb4ba0290.PNG)

En **Eclipse Spring Tool Suite 4**

![eclip2](https://github.com/SoniaZhang9/bimbles-api/assets/78765878/df951b01-e605-4efa-91e7-12da15bd1471)

## Documentacion generada con swagger
Puede ver la documentacion en:

- https://app.swaggerhub.com/apis-docs/SoniaZhang9/bimbles-api/v0
- http://localhost:8080/swagger-ui/index.html *(Es necesario ejecutar el proyecto)*
