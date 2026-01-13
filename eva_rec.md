
# ENUNCIADO DE LA PRUEBA TÉCNICA

#### Asignatura: Programación y Plataformas Web

#### Tipo: Prueba técnica backend

#### Tecnología obligatoria: Spring Boot

#### Lenguaje: Java

#### Gestor de dependencias: Gradle con Kotlin DSL

#### Base de datos: H2

#### Configuración: application.yml

# Descripción de la preuba tecnica

## CONTEXTO DEL SISTEMA

Una empresa dedicada a la comercialización y gestión de vehículos se encuentra en un proceso de modernización de su plataforma tecnológica.
El sistema anterior fue migrado a una nueva arquitectura basada en servicios backend desarrollados con Spring Boot.

Como parte de esta migración, la empresa requiere implementar un servicio REST que permita consultar y gestionar información de los vehículos almacenados en su base de datos corporativa.

La información histórica de los vehículos se encuentra en Oracle pero ha sido migrada desde el sistema una sola tabla a una base de datos H2, la cual contiene registros precargados y representa el estado real del negocio.

El servicio a desarrollar **no debe crear ni modificar la estructura de los datos existentes**, únicamente debe exponer y manipular la información de acuerdo con reglas de negocio claramente definidas.



## OBJETIVO DE LA PRUEBA

El objetivo de esta prueba técnica es evaluar la capacidad para:

* Diseñar un backend REST siguiendo una arquitectura por capas.
* Interpretar correctamente reglas de negocio explícitas.
* Implementar eliminación lógica y actualizaciones controladas.
* Construir consultas con múltiples condiciones usando JPA.
* Mantener consistencia del estado del sistema entre operaciones.
* Respetar contratos estrictos de endpoints y DTOs.

La solución será validada mediante pruebas automáticas, por lo que es obligatorio cumplir exactamente con los nombres, rutas y estructuras indicadas en el enunciado.



## ALCANCE DE LA PRUEBA

La prueba se centra exclusivamente en el desarrollo del backend.
No se evaluará interfaz gráfica, autenticación ni seguridad.

El sistema deberá exponer únicamente los endpoints solicitados y responder con los datos esperados en cada escenario.



## NOTA IMPORTANTE

Se evaluará el **razonamiento**, la correcta implementación de reglas de negocio y la coherencia de los resultados obtenidos tras una secuencia de operaciones. Respuestas esperadas en los endpoints deben coincidir exactamente con las especificaciones.






# SECCIÓN 2 – ENTIDAD DEL DOMINIO: VEHICLE

A partir del contexto descrito, la base de datos corporativa contiene información relacionada con los vehículos comercializados por la empresa.

Con esta información, se deberá **construir la entidad JPA correspondiente**, así como los **DTOs y mappers necesarios** para exponer los datos a través de la API REST, respetando las reglas indicadas.


## 2.1 ENTIDAD VEHICLE

La base de datos contiene la tabla `vehicles` con los siguientes campos:

![alt text](uml-entity.png)

Donde:

* `id` es el identificador único del vehículo.
* `brand` representa la marca del vehículo.
* `model` representa el modelo del vehículo.
* `price` representa el precio del vehículo.
* `stock` representa la cantidad disponible en inventario.
* `deleted` representa el estado de eliminación lógica del registro.

El campo `deleted` utiliza los siguientes valores:

* `"N"` → Vehículo activo
* `"S"` → Vehículo eliminado lógicamente


## 2.2 CONSIDERACIONES DE MODELADO

A partir de esta definición, deberá:

* Implementar la clase entidad `Vehicle` utilizando JPA.
* Implementar la clase entidad `Vehicle` para el MODELO.

* Mapear correctamente los tipos de datos.
* Configurar la entidad para ser persistida en la base de datos H2.
* No exponer directamente la entidad en los endpoints.


Esta entidad servirá como **modelo base** para la construcción de:

* Repositories
* Services
* DTOs
* Mappers


## 2.3 USO DE DTOs Y MAPPERS

Los datos expuestos por la API **no deben retornar directamente la entidad `Vehicle`**.

Deberá:

* Definir DTOs específicos para las respuestas de los endpoints.
* Implementar un mapper que permita transformar la entidad `Vehicle` en los DTOs correspondientes. 
* Asegurar que únicamente los campos requeridos sean expuestos.

> NOTA: Puede ser entidad ->  modelo -> DTO o entidad -> DTO.

El campo `deleted` **no debe formar parte de ningún DTO de respuesta**.



# SECCIÓN 3 – ENDPOINTS Y REGLAS DE NEGOCIO

En esta sección se definen **los únicos endpoints que serán evaluados** en la prueba técnica.
Los nombres de las rutas, métodos HTTP, DTOs y comportamientos **deben coincidir exactamente** con lo indicado, ya que serán utilizados por scripts de validación automática.

No se permite agregar endpoints adicionales ni modificar los existentes.



## 3.1 CONSIDERACIONES GENERALES

* Todos los endpoints deben trabajar **únicamente con vehículos no eliminados lógicamente**, salvo que se indique lo contrario.
* La eliminación de vehículos **no es física**, se realiza mediante actualización del campo `deleted`.
* Las respuestas deben retornar **DTOs**, no entidades.
* No se permite hardcodear resultados.
* Todas las validaciones deben realizarse desde la capa de servicio.



## 3.2 ENDPOINT 1 – OBTENER TODOS LOS VEHÍCULOS ACTIVOS

### Endpoint

```
GET /api/vehicles
```

### Regla de negocio

* Retorna todos los vehículos cuyo campo `deleted` sea `"N"`.

### DTO de respuesta

Se debe retornar una lista de objetos con los siguientes campos:

* id
* brand
* model
* price
* stock

El campo `deleted` **no debe incluirse** en la respuesta.

### Comportamiento esperado

* Si existen vehículos activos, retorna la lista correspondiente.
* Si no existen vehículos activos, retorna una lista vacía.
* Código HTTP esperado: `200 OK`.



## 3.3 ENDPOINT 2 – OBTENER VEHÍCULOS POR CONDICIÓN DE NEGOCIO

### Endpoint

```
GET /api/vehicles/low-stock-expensive
```

### Regla de negocio obligatoria

Este endpoint debe retornar únicamente los vehículos que cumplan **todas** las siguientes condiciones:

* `deleted = "N"`
* `price > 20_000`
* `stock < 10`

### DTO de respuesta

La respuesta debe contener los mismos campos definidos para el endpoint `/api/vehicles`:

* id
* brand
* model
* price
* stock

### Comportamiento esperado

* Retorna únicamente los vehículos que cumplan la condición.
* Si ningún vehículo cumple la condición, retorna una lista vacía.
* Código HTTP esperado: `200 OK`.

Este endpoint será utilizado directamente por las pruebas automáticas para validar la correcta implementación de consultas compuestas.



## 3.4 ENDPOINT 3 – ELIMINACIÓN LÓGICA DE VEHÍCULO POR MODELO

### Endpoint

```
PATCH /api/vehicles/delete/{model}
```

### Regla de negocio

La eliminación de un vehículo se realiza de forma **lógica**, actualizando el campo `deleted` a `"S"`.

### Comportamientos esperados

* **Caso 1: modelo no existe**

  * Retornar error indicando que el vehículo no existe.
  * Código HTTP esperado: `404 NOT FOUND`.

* **Caso 2: modelo existe y `deleted = "N"`**

  * Actualizar el campo `deleted` a `"S"`.
  * Retornar mensaje de confirmación.
  * Código HTTP esperado: `200 OK`.

* **Caso 3: modelo existe y `deleted = "S"`**

  * No realizar ninguna actualización.
  * Retornar mensaje indicando que el vehículo ya se encuentra eliminado.
  * Código HTTP esperado: `200 OK` con mensaje descriptivo.

La lógica de este endpoint **debe implementarse en la capa de servicio**.



## 3.5 ENDPOINT 4 – ACTUALIZAR STOCK DE VEHÍCULO

### Endpoint

```
PATCH /api/vehicles/stock
```

### DTO de request obligatorio

El cuerpo de la petición debe contener:

* id
* stock

### Regla de negocio

* Se debe actualizar el stock del vehículo identificado por el `id`.
* No se debe modificar ningún otro campo.

### Comportamientos esperados

* **Caso 1: id no existe**

  * Retornar error indicando que el vehículo no existe.
  * Código HTTP esperado: `404 NOT FOUND`.

* **Caso 2: id existe**

  * Actualizar el valor del stock.
  * Retornar el DTO del vehículo actualizado.
  * Código HTTP esperado: `200 OK`.



## 3.6 ESCENARIOS DE VALIDACIÓN FUNCIONAL

Durante la evaluación, se validará la correcta implementación de los endpoints mediante una secuencia de operaciones que simula el comportamiento real del sistema, incluyendo:

* Consulta inicial de vehículos activos.
* Consulta por condición de precio y stock.
* Eliminación lógica de vehículos.
* Validación de intentos de eliminación repetidos.
* Actualización de stock.
* Verificación del impacto de las operaciones en consultas posteriores.



# SECCIÓN 4 – PERSISTENCIA Y CONFIGURACIÓN DE BASE DE DATOS

Para la ejecución de esta prueba técnica, la empresa ha dispuesto una base de datos **H2 en memoria**, la cual contiene información previamente migrada desde el sistema corporativo.

Deberá configurar correctamente la persistencia para que la aplicación pueda acceder y consultar los datos existentes.



## 4.1 BASE DE DATOS H2

* Tipo de base de datos: H2 (en memoria)
* La estructura de la tabla ya existe.
* Los datos se cargan automáticamente al iniciar la aplicación.
* No se deben crear ni modificar tablas manualmente.



## 4.2 CONFIGURACIÓN DE DEPENDENCIAS (GRADLE)

El proyecto debe configurarse utilizando **Gradle con Kotlin DSL** (`build.gradle.kts`).

Se deben incluir, como mínimo, las siguientes dependencias:

* Spring Web
* Spring Data JPA
* H2 Database
* Validation (Jakarta Validation)

Estas dependencias son obligatorias para el correcto funcionamiento de la aplicación y para la ejecución de las pruebas automáticas.

No se permite el uso de librerías adicionales para persistencia o mapeo que no hayan sido indicadas explícitamente en el enunciado.

La dependencia debe agregarse **dentro del bloque `dependencies {}`** del archivo:

```
build.gradle.kts
```

Bloque mínimo **correcto y completo** para esta prueba:

```kotlin
dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    runtimeOnly("com.h2database:h2")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
```

Lo que deben adcionar es:

**runtimeOnly("com.h2database:h2")**

Notas importantes para la prueba:

* `runtimeOnly("com.h2database:h2")` es obligatorio.
* No usar `implementation` para H2.
* No agregar otras dependencias de base de datos.
* Este bloque es suficiente para que:

  * H2 funcione
  * JPA funcione
  * Los tests automáticos se ejecuten correctamente




## 4.3 CONFIGURACIÓN DE APPLICATION.YML

La aplicación utilizará una base de datos **H2 en memoria**.
La siguiente configuración debe copiarse **sin modificaciones** en los archivso:

```
src/main/resources/application.yml
```

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:vehiclesdb
    driver-class-name: org.h2.Driver
    username: sa
    password:

  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: none
    show-sql: true
    properties:
      hibernate:
        format_sql: true


  sql:
    init:
      mode: always
      schema-locations: classpath:schema.sql
      data-locations: classpath:data.sql

server:
  port: 8080
```



### CONSIDERACIONES IMPORTANTES

* `ddl-auto: none` es obligatorio para:

  * Evitar que Hibernate modifique la estructura existente.
  * Garantizar que la aplicación use **exclusivamente** la tabla proporcionada.
* La base de datos se inicializa automáticamente con `data.sql`.
* La consola H2 está habilitada únicamente para pruebas locales.
* No se deben agregar perfiles adicionales.



## 4.4 CARGA DE DATOS INICIALES

Como parte de la prueba, se proporcionará un archivo con datos precargados.

### Archivos requeridos

```
data.sql
schema.sql
```

### Ubicación obligatoria

El archivo `data.sql` y `schema.sql` deberan copiarse **exactamente** en la siguiente ruta del proyecto:

```
src/main/resources/data.sql
src/main/resources/schema.sql

```

Spring Boot cargará automáticamente este archivo al iniciar la aplicación.



## 4.5 RESTRICCIONES IMPORTANTES

* No modificar el contenido del archivo `data.sql` ni `schema.sql`.
* No agregar scripts SQL adicionales.
* No crear datos manualmente desde el código.
* No cambiar la estructura de la tabla existente.
* No usar inicialización programática de datos.

El incumplimiento de estas restricciones afectará la validación automática de la prueba.



## 4.6 CONSIDERACIONES PARA LA EVALUACIÓN

Los endpoints definidos en la Sección 3 serán evaluados **únicamente** en función de los datos cargados desde `data.sql`.

La correcta configuración de la persistencia es fundamental para:

* Obtener resultados consistentes.
* Validar las reglas de negocio.
* Ejecutar correctamente los scripts y pruebas automáticas de calificación.





# SECCIÓN 5 – ESTRUCTURA DEL PROYECTO

El proyecto debe organizarse siguiendo una **arquitectura por capas**, con una separación clara de responsabilidades.
La correcta organización del código es parte fundamental de la evaluación.

No se evaluará únicamente que los endpoints funcionen, sino que la estructura del proyecto respete los lineamientos definidos en esta sección.


## 5.1 ORGANIZACIÓN GENERAL

El proyecto debe estar organizado bajo un **paquete base único**, por ejemplo:

```
com.company.vehicles
```

A partir de este paquete base, se deben definir los siguientes subpaquetes obligatorios.


## 5.2 ESTRUCTURA DE PAQUETES 

La estructura mínima del proyecto debe ser la siguiente:

```
src/main/java/com/company/vehicles
├── controller
├── service
│   └── impl
├── repository
├── entity
├── dto
├── mapper
└── exception
```

Cada paquete tiene una responsabilidad específica que debe respetarse.




# SECCIÓN 6 – CONTRATOS DE DTOs (REQUEST Y RESPONSE)

Esta sección define **los contratos de datos obligatorios** que deben utilizarse en los endpoints.
Los nombres de las clases DTO, sus atributos y tipos **deben coincidir exactamente** con lo indicado.

No se permite agregar, eliminar ni renombrar campos.


## 6.1 PRINCIPIOS GENERALES

* Los endpoints **no deben exponer entidades JPA**.
* Todas las respuestas deben retornar DTOs.
* Los DTOs **no deben contener** anotaciones JPA.
* El campo `deleted` **no debe exponerse** en ningún DTO de respuesta.
* Los DTOs deben usarse exactamente como se define en esta sección para permitir la validación automática.


## 6.2 DTO DE RESPUESTA – VEHICLE

### Nombre obligatorio de la clase

```
VehicleResponseDto
```

### Campos obligatorios

| Campo | Tipo       | Descripción                |
| ----- | ---------- | -------------------------- |
| id    | Long       | Identificador del vehículo |
| brand | String     | Marca del vehículo         |
| model | String     | Modelo del vehículo        |
| price | Double | Precio del vehículo        |
| stock | Integer    | Stock disponible           |

### Uso

Este DTO debe utilizarse como respuesta en los siguientes endpoints:

* `GET /api/vehicles`
* `GET /api/vehicles/low-stock-expensive`
* `PATCH /api/vehicles/stock`


## 6.3 DTO DE REQUEST – ACTUALIZACIÓN DE STOCK

### Nombre obligatorio de la clase

```
VehicleStockRequestDto
```

### Campos obligatorios

| Campo | Tipo    | Descripción                |
| ----- | ------- | -------------------------- |
| id    | Long    | Identificador del vehículo |
| stock | Integer | Nuevo valor de stock       |

### Reglas

* El campo `id` es obligatorio.
* El campo `stock` debe ser un valor entero mayor o igual a cero.
* Este DTO **solo** se utiliza para el endpoint de actualización de stock.

### Endpoint asociado

```
PATCH /api/vehicles/stock
```


## 6.4 DTO DE RESPUESTA – MENSAJES DE OPERACIÓN

Para los endpoints que realizan operaciones de negocio (eliminación lógica), se debe retornar un DTO de respuesta con mensaje descriptivo.

### Nombre obligatorio de la clase

```
OperationResponseDto
```

### Campos obligatorios

| Campo   | Tipo   | Descripción                                       |
| ------- | ------ | ------------------------------------------------- |
| message | String | Mensaje descriptivo del resultado de la operación |

### Uso

Este DTO debe utilizarse como respuesta en:

* `PATCH /api/vehicles/delete/{model}`


## 6.5 MAPPERS

Se debe implementar un mapper que permita transformar:

* `Vehicle` → `VehicleResponseDto`

El mapper:

* Es obligatorio.
* No debe contener lógica de negocio.


El mapper debe utilizarse exclusivamente desde la capa de servicio.


# SECCIÓN 7 – MANEJO DE ERRORES Y CÓDIGOS HTTP

Esta sección define el comportamiento esperado del sistema frente a situaciones de error y estados inválidos de negocio.
El manejo correcto de errores forma parte de la evaluación técnica.

Las respuestas deben ser consistentes, claras y alineadas con los códigos HTTP indicados.


## 7.1 PRINCIPIOS GENERALES

* Los errores deben manejarse en la **capa de servicio**.
* Los controladores no deben contener lógica de validación de negocio.
* Se recomienda el uso de excepciones personalizadas.
* Todas las respuestas de error deben retornar un cuerpo con un mensaje descriptivo.


## 7.2 ERRORES FUNCIONALES OBLIGATORIOS

### 7.2.1 VEHÍCULO NO ENCONTRADO

#### Escenarios

* Eliminación lógica por modelo cuando el modelo no existe.
* Actualización de stock por id cuando el id no existe.

#### Código HTTP esperado

```
404 NOT FOUND
```

#### Respuesta esperada

Se debe retornar un objeto con un mensaje descriptivo, por ejemplo:

```
{
  "message": "Vehicle not found"
}
```


### 7.2.2 DATOS DE REQUEST INVÁLIDOS

#### Escenario

* Envío de un DTO de request con datos inválidos (por ejemplo, stock negativo).

#### Código HTTP esperado

```
400 BAD REQUEST
```

#### Respuesta esperada

```
{
  "message": "Invalid request data"
}
```






# SECCIÓN 8 – ESCENARIOS DE VALIDACIÓN FUNCIONAL

Esta sección describe los **escenarios de validación** que serán utilizados para comprobar el correcto funcionamiento del backend.
Los escenarios representan una secuencia de operaciones reales sobre el sistema y validan la **consistencia del estado** tras cada acción.

Las pruebas automáticas ejecutarán estos escenarios **en el orden indicado**.

## 8.1 ESTADO INICIAL DEL SISTEMA

* La base de datos se inicializa automáticamente a partir del archivo `data.sql`.
* Existen vehículos activos (`deleted = "N"`) y vehículos eliminados lógicamente (`deleted = "S"`).
* No se revelan cantidades exactas ni valores específicos en el enunciado.

## 8.2 SECUENCIA DE VALIDACIÓN

### Paso 1 – Consulta inicial de vehículos activos

**Operación**

```
GET /api/vehicles
```

**Validación**

* Retorna únicamente vehículos con `deleted = "N"`.

### Paso 2 – Consulta por condición de negocio

**Operación**

```
GET /api/vehicles/low-stock-expensive
```

**Validación**

* Retorna únicamente vehículos activos.
* Cumplen las condiciones:

  * `price > 20_000`
  * `stock < 10`

### Paso 3 – Eliminación lógica con modelo inexistente

**Operación**

```
PATCH /api/vehicles/delete/{model}
```

**Condición**

* El modelo no existe en la base de datos.

**Validación**

* Retorna error.
* Código HTTP: `404 NOT FOUND`.

### Paso 4 – Eliminación lógica con modelo existente

**Operación**

```
PATCH /api/vehicles/delete/{model}
```

**Condición**

* El modelo existe y `deleted = "N"`.

**Validación**

* El campo `deleted` se actualiza a `"S"`.
* Retorna mensaje de confirmación.

### Paso 5 – Eliminación lógica de un vehículo ya eliminado

**Operación**

```
PATCH /api/vehicles/delete/{model}
```

**Condición**

* El modelo existe y `deleted = "S"`.

**Validación**

* No se realiza ninguna actualización.
* Retorna mensaje indicando que el vehículo ya se encuentra eliminado.

### Paso 6 – Nueva consulta de vehículos activos

**Operación**

```
GET /api/vehicles
```

**Validación**

* La cantidad de vehículos activos disminuye respecto al Paso 1.
* No se incluyen vehículos con `deleted = "S"`.

### Paso 7 – Nueva consulta por condición de negocio

**Operación**

```
GET /api/vehicles/low-stock-expensive
```

**Validación**

* El resultado se mantiene consistente con las reglas de negocio.
* La eliminación lógica realizada no afecta el resultado si el vehículo eliminado no cumplía la condición.

### Paso 8 – Actualización de stock con id inexistente

**Operación**

```
PATCH /api/vehicles/stock
```

**Condición**

* El id enviado no existe.

**Validación**

* Retorna error.
* Código HTTP: `404 NOT FOUND`.

### Paso 9 – Actualización de stock con id existente

**Operación**

```
PATCH /api/vehicles/stock
```

**Condición**

* El id existe.

**Validación**

* El stock se actualiza correctamente.
* Retorna el DTO del vehículo con el nuevo valor de stock.

### Paso 10 – Consulta de vehículos activos tras actualización

**Operación**

```
GET /api/vehicles
```

**Validación**

* La cantidad de vehículos activos no cambia respecto al Paso 6.

### Paso 11 – Consulta por condición tras actualización de stock

**Operación**

```
GET /api/vehicles/low-stock-expensive
```

**Validación**

* El resultado refleja correctamente el impacto de la actualización de stock.
* La cantidad de vehículos que cumplen la condición puede variar según el nuevo estado.




# SECCIÓN 9 – CRITERIOS DE EVALUACIÓN Y RÚBRICA

La evaluación de la prueba técnica se realizará considerando tanto el **correcto funcionamiento** del sistema como la **calidad de la solución implementada**.

El puntaje total de la prueba es de **10 puntos**.


## 9.1 DISTRIBUCIÓN DEL PUNTAJE

| Criterio evaluado | Puntaje |
|------------------|---------|
| Arquitectura por capas y estructura del proyecto | 2.0 |
| Endpoint 1 – GET /api/vehicles | 2.0 |
| Endpoint 2 – GET /api/vehicles/low-stock-expensive | 2.0 |
| Endpoint 3 – PATCH delete por model | 2.0 |
| Endpoint 4 – PATCH update stock | 2.0 |
| **TOTAL** | **10.0** |

Implementación correcta de endpoints (Consultas JPA y reglas de negocio - Uso correcto de DTOs y mappers - Manejo de errores y códigos HTTP ) 

Si el endpoint responde exactamente lo esperado:

* DTO correcto

* Filtros correctos

* HTTP correcto
→ 2 pts

Si falla:
→ 0 pts ese endpoint


# SECCIÓN 10 – INSTRUCCIONES DE ENTREGA

La entrega de la prueba técnica se realizará **exclusivamente mediante un repositorio remoto**.

El cumplimiento estricto de estas instrucciones es obligatorio para que la prueba sea evaluada.


## 10.1 ENTREGA DEL PROYECTO

* El proyecto debe subirse a un **repositorio remoto** (GitHub, GitLab u otro indicado por el docente).
* El enlace del repositorio debe ser **subido al AVAC** dentro del tiempo asignado para la prueba.
* No se aceptan entregas por otros medios.


## 10.2 TIEMPO LÍMITE

* El repositorio debe contener el código **hasta la finalización exacta de la hora de la prueba**.
* Commits realizados **después del tiempo establecido** no serán considerados bajo ninguna circunstancia.
* La hora del commit en el repositorio será el único criterio válido para verificar el tiempo de entrega.


## 10.3 CONDICIONES DE EVALUACIÓN

* Si el repositorio **no contiene código**, la prueba **no será evaluada** y la calificación será **0**.
* Si existe algún error en la carga del proyecto al repositorio y el código no es visible, **no se realizará evaluación** y la calificación será **0**.
* Es responsabilidad del estudiante verificar que el repositorio:

  * Sea accesible.
  * Contenga el código completo.
  * Compile y ejecute correctamente.

No se aceptarán reclamos posteriores relacionados con problemas de carga, permisos o visibilidad del repositorio.


## 10.4 VALIDACIÓN POSTERIOR

Una vez finalizada la prueba:

* SE **clonará el repositorio** entregado.
* Se ejecutarán **scripts de validación automática** sobre el proyecto.
* La calificación se basará en:

  * El commit este dentro del tiempo correcto 
  * El código existente en el repositorio.
  * El cumplimiento del enunciado.
  * Los resultados obtenidos por los scripts de evaluación.

No se permitirá realizar correcciones posteriores a la finalización del tiempo de la prueba.



## 10.5 CONSIDERACIÓN FINAL

El correcto envío del repositorio forma parte del proceso de evaluación.

Una prueba no entregada correctamente se considerará **no rendida**.


