# Conexa Examen Técnico

### Levantar el Proyecto en Local

#### Clonar el Repositorio:

```bash
git clone https://github.com/Joairazabal/conexa_test.git
cd conexa
```

#### Ejecutar Docker Compose:

```bash
docker-compose up --build 
```

Antes de ejecutar el comando, es necesario que tengas instalado Docker Desktop y que este abierto. Esto hara un build de
los contenedores definidos en el docker-compose y los levantará para poder compilar el servidor.

#### Acceder a la Aplicación:

Una vez que los contenedores estén corriendo, la aplicación estará disponible en http://localhost:8080.

### Ejecución de Tests y Actualización de Jacoco

#### Ejecutar Contenedor de Pruebas:

```bash
docker-compose up test
```

Esto levantará el contenedor configurado para ejecutar los tests y generar el reporte de Jacoco.

#### Actualizar y acceder al Reporte de Jacoco:

```bash
docker cp <id_container_test>:/target/site/jacoco ./jacoco-report

```

Remplazar <id_contedor_test> con el ID del contenedor donde se ejecutaron los tests. Este comando copiará el reporte
generado por Jacoco desde el contenedor al directorio local ./jacoco-report.

### Codigo de Errores

Esta parte detalla los errores y excepciones personalizados utilizados en la aplicación. Cada excepción está asociada
con un código de error único para facilitar la identificación y el manejo de errores en la misma.

#### Formato de Código de Error

**SIGLA-CODIGO DE ERROR HTTP**

Por ejemplo, AUTH-404 indica un error de autenticación con un código HTTP 404.

```json
AUTH-404: Error

AUTH -> Hace referencia a que el error proviene de la logica del Auth.

```

```json
EXT-500: Error al realizar una petición al servidor externo

EXT -> Hace referencia a que el error proviene de un servidor externo en este caso swapi, despues de las siglas EXT- puede ser cualquier codigo de error http.

```

### Documentación de la API

Esta aplicación utiliza Swagger para generar automáticamente la documentación de la API. Swagger proporciona una
interfaz interactiva para explorar y probar los endpoints de la API.
Acceso a la Documentación

Una vez que la aplicación está en funcionamiento, puedes acceder a la documentación de Swagger en la siguiente URL:

```bash
http://localhost:8080/swagger-ui.html
```

## Deploy

El deploy esta automatizado mediante el uso de git y el hosting de *Render*, cada vez que actualicemos la rama main,
Render hará un re despliegue utilizando el Dockerfile.prod situado en la raíz del proyecto

```bash
  git push 
```

