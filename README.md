# Aplicación Spring Boot con Integración de Gemini API

Este proyecto es una aplicación Spring Boot que integra la API de Gemini para generar contenido basado en prompts de texto y mantener un historial de las consultas realizadas.

## Descripción

La aplicación proporciona una interfaz REST para interactuar con la API de Gemini (Google AI), permitiendo a los usuarios enviar prompts de texto y recibir respuestas generadas por el modelo Gemini 2.0 Flash. Además, la aplicación mantiene un historial de todas las consultas realizadas y sus respuestas.

## Características

- Integración con la API de Gemini 2.0 Flash
- Endpoint para generar contenido a partir de prompts de texto
- Almacenamiento y consulta del historial de interacciones
- Manejo de errores y excepciones

## Requisitos

- Java 11 o superior
- Gradle
- Conexión a Internet (para comunicarse con la API de Gemini)
- API Key de Gemini (ya configurada en el servicio)

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── vallegrande/
│   │       └── gemini/
│   │           ├── controller/
│   │           │   └── GeminiController.java
│   │           ├── model/
│   │           │   └── GeminiRequest.java
│   │           ├── service/
│   │           │   └── GeminiService.java
│   │           └── GeminiApplication.java
│   └── resources/
│       └── application.properties
```

## Instalación y Ejecución

1. Clona el repositorio:
   ```
   git clone [URL_DEL_REPOSITORIO]
   cd gemini
   ```

2. Ejecuta la aplicación con Gradle:
   ```
   ./gradlew bootRun
   ```

3. La aplicación estará disponible en `http://localhost:8080`

## Uso de la API

### Generar Contenido

**Endpoint:** `POST /api/gemini/generate`

**Cuerpo de la solicitud:**
```json
{
  "text": "Tu prompt aquí"
}
```

**Ejemplo con curl:**
```bash
curl -X POST http://localhost:8080/api/gemini/generate \
  -H "Content-Type: application/json" \
  -d '{"text":"Escribe un poema sobre la primavera"}'
```

### Consultar Historial

**Endpoint:** `GET /api/gemini/history`

**Ejemplo con curl:**
```bash
curl -X GET http://localhost:8080/api/gemini/history
```

## Configuración

La aplicación utiliza la API de Gemini con el modelo `gemini-2.0-flash`. La API Key está configurada directamente en el servicio.

## Notas Técnicas

- La aplicación utiliza `RestTemplate` para realizar las solicitudes HTTP a la API de Gemini.
- El historial de consultas se almacena en memoria (se pierde al reiniciar la aplicación).
- La estructura de la solicitud a la API de Gemini sigue el formato requerido por la API oficial.

## Posibles Mejoras

- Implementar persistencia para el historial de consultas
- Añadir autenticación y autorización
- Crear una interfaz de usuario web
- Permitir la configuración de la API Key a través de variables de entorno o archivo de propiedades
- Implementar caché para respuestas frecuentes

## Licencia

[Incluir información de licencia aquí]
