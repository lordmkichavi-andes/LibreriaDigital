📝 Descripción
Librería Digital es un sistema de gestión para librerías desarrollado en Java con Jakarta EE. Permite administrar clientes, productos, ventas y reportes a través de una interfaz web simple.

Nota: La aplicación utiliza almacenamiento en memoria, por lo que los datos se reiniciarán cada vez que se detenga el servidor.

🛠️ Tecnologías
☕ Lenguaje: Java 11

🌐 Plataforma: Jakarta EE (Servlets y JSP)

📦 Gestión de Dependencias: Maven

🚀 Servidor: Jetty (embebido)

🚀 Instalación y Ejecución
Para ejecutar este proyecto en tu máquina local, sigue estos pasos:

1. ✅ Requisitos Previos

Tener instalado JDK 11 o superior.

Tener instalado Maven 3.6 o superior.

2. 📥 Clonar el Repositorio

git clone https://github.com/lordmkichavi-andes/LibreriaDigital.git
cd LibreriaDigital

3. 🏗️ Compilar el Proyecto
   Este comando descargará las dependencias necesarias y compilará el código fuente.

mvn clean install

4. ▶️ Ejecutar la Aplicación
   El proyecto utiliza un servidor Jetty integrado, por lo que no necesitas un servidor externo.

mvn jetty:run

5. 🌍 Acceder a la Aplicación
   Abre tu navegador web y dirígete a la siguiente URL:
   http://localhost:8080/LibreriaDigital/

⭐ Funcionalidades
🧑‍💼 Gestión de Clientes: Alta, búsqueda y edición de clientes.

📖 Gestión de Productos: Administración del catálogo de libros.

🛒 Ventas: Proceso para registrar transacciones.

📊 Reportes: Generación de informes de ventas y productos.

📜 Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.

🤝 Contribución
Las contribuciones son bienvenidas. Si deseas mejorar el proyecto, por favor haz un "Fork" y envía un "Pull Request" con tus cambios.