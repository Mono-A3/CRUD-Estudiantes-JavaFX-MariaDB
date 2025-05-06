# CRUD-Estudiantes-JavaFX-MariaDB

Bienvenido a **CRUD-Estudiantes-JavaFX-MariaDB**, una aplicación de escritorio desarrollada con JavaFX para la gestión
de estudiantes mediante un sistema CRUD (Crear, Leer, Actualizar y Eliminar), con conexión a una base de datos **MariaDB**.

## 🌟 Descripción

Sistema interactivo para el registro y administración de datos de estudiantes. Construido con JavaFX y respaldado por una base de datos MariaDB, implementa operaciones CRUD con validaciones de entrada. Aplica el patrón MVC y utiliza JDBC para asegurar persistencia de datos y mantenimiento escalable.

## 📌 Funcionalidades

- Registro de estudiantes con datos como nombre, edad, carrera, ciudad y estado.
- Búsqueda por ID o por nombre:
    - Al buscar por ID se llena automáticamente el formulario si existe coincidencia.
    - Al buscar por nombre, si hay múltiples coincidencias se muestran en la tabla sin llenar los campos.
- Actualización de registros con confirmación previa.
- Eliminación de registros con alerta de confirmación.
- Visualización del estado (Activo/Inactivo) con estilos diferenciados en la tabla.
- Carga automática de todos los registros al iniciar la aplicación o al limpiar campos.
- Validación de entradas para evitar errores por campos vacíos o datos incorrectos.

## 🛠️ Tecnologías Utilizadas

- **Java 24 (JDK)**
- **JavaFX** – Framework para interfaces gráficas.
- **MariaDB** – Base de datos relacional.
- **JDBC** – Conector para la comunicación entre Java y MariaDB.
- **Maven** – Gestión de dependencias y compilación.
- **IntelliJ IDEA** – Entorno de desarrollo.
- **Scene Builder** – Herramienta para diseño de interfaces FXML.
- **DBeaver** – Cliente de administración de bases de datos.

## 📜 Licencia

Este proyecto está protegido bajo la [Licencia Personalizada de Andres Stiven Araque](./LICENSE.md).  
El uso, modificación o redistribución del código sin autorización previa está prohibido. Para más información, puede
contactarse directamente.

## 📬 Contacto

Para consultas, reportes o sugerencias, puede comunicarse a través del siguiente correo electrónico:

- **Correo**: [aaraqueamaya397@gmail.com](mailto:aaraqueamaya397@gmail.com)
