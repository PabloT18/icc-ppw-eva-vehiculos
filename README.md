# Vehicles API - Evaluación

Este proyecto es una API REST desarrollada con Spring Boot para la gestión de vehículos.

## Prerrequisitos

- Java 17 o superior
- Python 3.6 o superior
- Gradle

## Cómo ejecutar la evaluación con grader.py

### 1. Instalar dependencias de Python

Antes de ejecutar el grader.py, instala la dependencia requerida:

```bash
python3 -m pip install requests
```

### 2. Iniciar el servicio Spring Boot

El grader.py requiere que el servicio esté corriendo en `http://localhost:8080`. Para iniciarlo:

```bash
# Dar permisos de ejecución al gradlew (solo la primera vez)
chmod +x gradlew

# Compilar y ejecutar la aplicación
./gradlew bootRun
```

**Importante:** Mantén el servicio corriendo en una terminal separada.

### 3. Ejecutar el grader.py

En una nueva terminal, ejecuta el archivo de evaluación:

```bash
python3 grader.py
```

## Endpoints evaluados

El grader.py evaluará los siguientes endpoints:

- `GET /api/vehicles` - Obtener todos los vehículos
- `GET /api/vehicles/low-stock-expensive` - Vehículos con bajo stock y precio alto
- `PATCH /api/vehicles/delete/{model}` - Eliminación lógica por modelo
- `PATCH /api/vehicles/stock` - Actualizar stock de un vehículo

## Puntuación

- GET /api/vehicles: 2 puntos
- GET /low-stock-expensive: 2 puntos  
- PATCH delete/{model}: 1 punto
- PATCH /stock: 1 punto
- Flujo completo: 2 puntos

**Total: 8 puntos**

## Solución de problemas

- Si el grader.py falla con "Connection refused", verifica que el servicio Spring Boot esté corriendo en el puerto 8080
- Si hay errores de importación, asegúrate de haber instalado `requests` con pip
- Verifica que el puerto 8080 no esté siendo usado por otra aplicación