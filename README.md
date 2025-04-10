# 🖧 Sistema de Comunicación Cliente-Servidor en JavaFX (MVC)

Este proyecto implementa un sistema de mensajería simple bajo el **patrón de diseño Modelo-Vista-Controlador (MVC)** usando JavaFX y sockets. El servidor recibe mensajes desde clientes y los reenvía a otras direcciones IP especificadas, todo visualizado desde una interfaz gráfica.

---

## 📌 Arquitectura MVC

- **Modelo (`Envios.java`)**: Representa los datos del mensaje. Incluye campos como IP, nombre y contenido del mensaje. Es una clase `Serializable` utilizada para transportar los datos entre cliente y servidor.
  
- **Vista (`FXML`)**: La interfaz gráfica está definida en archivos `.fxml` y utiliza componentes como `TextArea` para mostrar los mensajes.

- **Controlador (`Controller_servidor.java`)**: Contiene la lógica principal del servidor. Maneja eventos, lanza el hilo que escucha las conexiones entrantes y actualiza la vista con los mensajes recibidos.

---

## 🚀 Funcionamiento

1. Al iniciar, el controlador crea un `Thread` que abre un `ServerSocket` en el puerto **1234**.
2. El servidor escucha conexiones entrantes y recibe objetos `Envios` desde los clientes.
3. Extrae el contenido del mensaje, IP y nombre.
4. Muestra el mensaje en la interfaz (vista).
5. Reenvía el mensaje al cliente de destino a través del puerto **1231**.

---

## 📁 Archivos principales

- `Controller_servidor.java`: Controlador JavaFX que implementa `Runnable` y gestiona la lógica del servidor.
- `Envios.java`: Clase modelo que contiene los datos del mensaje. (Debés crearla con `implements Serializable`).
- `servidor.fxml`: Archivo de interfaz gráfica que define el diseño de la vista (no incluido aquí).

---

## 🧪 Requisitos

- Java 8 o superior.
- JavaFX configurado en tu proyecto.
- Clase `Envios` con al menos:
  ```java
  private String mensaje;
  private String nombre;
  private String ip;
