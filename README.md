# 📚 Librería Digital 

## 📝 Descripción
Es un sistema de gestión para librerías desarrollado en Java con Jakarta EE. Permite administrar clientes, productos, ventas con descuentos dinámicos y reportes a través de una interfaz web simple.

**⚠️ Nota:** La aplicación utiliza almacenamiento en memoria, por lo que los datos se reiniciarán cada vez que se detenga el servidor.

## 🛠️ Tecnologías
- **☕ Lenguaje:** Java 11
- **🌐 Plataforma:** Jakarta EE 6.0 (Servlets y JSP)
- **📦 Gestión de Dependencias:** Maven 3.6+
- **🚀 Servidor:** Jetty 9.4.51 (embebido)
- **🏷️ JSTL:** Jakarta Standard Tag Library

## 🚀 Instalación y Ejecución

### ✅ Requisitos Previos

#### **Versiones Específicas Requeridas:**
- **JDK:** Oracle OpenJDK 11.0.12 o superior
- **Maven:** Apache Maven 3.6.3 o superior
- **Git:** 2.30.0 o superior

#### **Verificar Instalaciones:**
```bash
# Verificar Java
java -version
# Debe mostrar: openjdk version "11.0.12" 2021-07-20

# Verificar Maven
mvn -version
# Debe mostrar: Apache Maven 3.6.3

# Verificar Git
git --version
# Debe mostrar: git version 2.30.0
```

### 📥 Clonar el Repositorio
```bash
git clone https://github.com/lordmkichavi-andes/LibreriaDigital.git
cd LibreriaDigital
```

### 🔧 Configuración de Variables de Entorno

#### **Windows:**
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk-11.0.12
set MAVEN_HOME=C:\Program Files\Apache\maven-3.6.3
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%
```

#### **Linux/macOS:**
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
export MAVEN_HOME=/opt/apache-maven-3.6.3
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
```

### 🏗️ Compilar el Proyecto
```bash
# Limpiar y compilar
mvn clean install

# Verificar compilación exitosa
# Debe mostrar: BUILD SUCCESS
```

### ▶️ Ejecutar la Aplicación
```bash
# Iniciar servidor Jetty
mvn jetty:run

# Verificar inicio exitoso
# Debe mostrar: Started ServerConnector@...{HTTP/1.1, (http/1.1)}{0.0.0.0:8080}
```

### 🌍 Acceder a la Aplicación
Abre tu navegador web y dirígete a:
```
http://localhost:8080/LibreriaDigital/
```

## 📸 Capturas de Pantalla

### 🏠 Página Principal
![Página Principal](https://github.com/lordmkichavi-andes/LibreriaDigital/blob/main/images/principal.png)

### 📂 Gestión de Categorías
![Gestión Categorías](https://github.com/lordmkichavi-andes/LibreriaDigital/blob/main/images/categorias.png)

### 📊 Reportes y Estadísticas
![Reportes](https://github.com/lordmkichavi-andes/LibreriaDigital/blob/main/images/reportes.png)

## 📈 Análisis de Calidad con CodeScene

Nuestro proyecto incluye un análisis completo de calidad del código realizado con CodeScene, que proporciona insights valiosos sobre la arquitectura, mantenibilidad y salud del sistema.

### 🔍 [Ver Análisis Completo](./codescene-analysis.md)

El análisis incluye:
- **🏗️ Hotspots de código** - Áreas que requieren mayor atención
- **🏥 Salud del sistema** - Métricas de calidad y mantenibilidad  
- **💻 Análisis de código** - Métricas detalladas de desarrollo
- **📊 Dashboard** - Vista general con métricas clave
- **🔄 Simulaciones** - Análisis de riesgos y offboarding

### 🎯 Hallazgos Principales
- **Áreas críticas identificadas** para refactorización prioritaria
- **Métricas de complejidad** y acoplamiento entre componentes
- **Recomendaciones específicas** para mejorar la calidad del código
- **Análisis de tendencias** de evolución del proyecto

## ⭐ Funcionalidades Principales

### 🧑‍💼 Gestión de Clientes
- ✅ **Alta de clientes** con validación de datos
- ✅ **Búsqueda y listado** de clientes registrados
- ✅ **Edición de información** de clientes existentes
- ✅ **Eliminación** de clientes del sistema

### 📖 Gestión de Productos
- ✅ **Catálogo de libros** con información detallada
- ✅ **Control de inventario** (stock disponible)
- ✅ **Categorización** de productos
- ✅ **Gestión de precios** y descripciones

### 🛒 Sistema de Ventas
- ✅ **Proceso de venta** paso a paso
- ✅ **Selección de cliente** y productos
- ✅ **Cálculo automático** de totales
- ✅ **Descuentos dinámicos** con códigos promocionales
- ✅ **Generación de recibos** de venta

### 🎫 Sistema de Descuentos
- ✅ **Códigos de descuento** configurables
- ✅ **Descuentos por porcentaje** o monto fijo
- ✅ **Validación de fechas** de vigencia
- ✅ **Aplicación automática** en ventas

### 📊 Reportes y Estadísticas
- ✅ **Reporte de ventas** por período
- ✅ **Productos más vendidos** con ranking
- ✅ **Clientes más frecuentes** con estadísticas
- ✅ **Ingresos por categoría** de productos
- ✅ **Análisis de descuentos** aplicados

## 📚 Documentación Técnica

Para información técnica detallada, consulta los siguientes archivos:

### 🏗️ [architecture.md](./architecture.md)
- **Análisis arquitectónico completo** del sistema
- **Patrones y tácticas** de arquitectura utilizados
- **Stack tecnológico** detallado
- **Análisis de seguridad** y escalabilidad
- **Endpoints y API** de comunicación

### 📊 [diagrams.md](./diagrams.md)
- **Diagramas de componentes** y despliegue
- **Flujos de datos** y procesos
- **Diagramas de clases** por capa
- **Estructura de paquetes** detallada
- **Relaciones entre módulos**

### 📈 [codescene-analysis.md](./codescene-analysis.md)
- **Análisis completo de calidad** del código con CodeScene
- **Hotspots y áreas críticas** que requieren atención
- **Métricas de salud del sistema** y tendencias
- **Análisis de arquitectura** y dependencias
- **Simulaciones de offboarding** y gestión de riesgos
- **Dashboard con métricas clave** del proyecto

## 🏗️ Estructura del Proyecto

```
LibreriaDigital/
├── 📄 pom.xml                    # Configuración Maven
├── 📄 README.md                  # Este archivo
├── 📄 architecture.md            # Análisis arquitectónico
├── 📄 diagrams.md                # Diagramas del sistema
│
├── 📁 src/
│   └── 📁 main/
│       ├── 📁 java/org/example/
│       │   ├── 📁 model/         # Entidades de dominio
│       │   ├── 📁 service/       # Lógica de negocio
│       │   └── 📁 servlet/       # Controladores web
│       └── 📁 webapp/
│           ├── 📄 index.jsp      # Página principal
│           └── 📁 WEB-INF/
│               └── 📄 web.xml    # Configuración web
│
└── 📁 target/                    # Archivos compilados
```

## 🔧 Comandos Útiles

### **Desarrollo:**
```bash
# Compilar sin ejecutar tests
mvn compile

# Ejecutar tests
mvn test

# Limpiar proyecto
mvn clean

# Empaquetar WAR
mvn package
```

### **🧪 Pruebas de Performance**
```bash
# Ejecutar pruebas de performance comparativas
./pruebas-post-experimento.sh

# Ver documentación completa en: [pruebas-post-experimento.md](./pruebas-post-experimento.md)
```

### **🔌 Pruebas de API REST**
```bash
# Probar endpoints del API REST
curl http://libreria-digital-war-env-modernizada.eba-xxguactp.us-east-2.elasticbeanstalk.com/api/clientes

# Crear un cliente
curl -X POST http://libreria-digital-war-env-modernizada.eba-xxguactp.us-east-2.elasticbeanstalk.com/api/clientes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan", "email":"juan@ejemplo.com"}'

# Obtener cliente por ID
curl http://libreria-digital-war-env-modernizada.eba-xxguactp.us-east-2.elasticbeanstalk.com/api/clientes/1

# Actualizar cliente
curl -X PUT http://libreria-digital-war-env-modernizada.eba-xxguactp.us-east-2.elasticbeanstalk.com/api/clientes/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan Actualizado", "email":"juan@ejemplo.com"}'

# Eliminar cliente
curl -X DELETE http://libreria-digital-war-env-modernizada.eba-xxguactp.us-east-2.elasticbeanstalk.com/api/clientes/1
```

### **Despliegue:**
```bash
# Ejecutar en modo desarrollo
mvn jetty:run

# Ejecutar en modo producción
mvn jetty:run -Djetty.port=8080

# Detener servidor
Ctrl + C
```

### **Troubleshooting:**
```bash
# Verificar dependencias
mvn dependency:tree

# Actualizar dependencias
mvn dependency:resolve

# Verificar configuración
mvn validate
```

## 🐛 Solución de Problemas

### **Error: "Port 8080 already in use"**
```bash
# Encontrar proceso usando el puerto
lsof -i :8080

# Terminar proceso
kill -9 <PID>

# O usar puerto alternativo
mvn jetty:run -Djetty.port=8081
```

### **Error: "Java version not compatible"**
```bash
# Verificar versión de Java
java -version

# Configurar JAVA_HOME correctamente
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
```

### **Error: "Maven not found"**
```bash
# Instalar Maven (Ubuntu/Debian)
sudo apt install maven

# Instalar Maven (macOS)
brew install maven

# Verificar instalación
mvn -version
```

## 📜 Licencia
Este proyecto está bajo la **Licencia MIT**. Consulta el archivo `LICENSE` para más detalles.

## 🤝 Contribución
Las contribuciones son bienvenidas. Si deseas mejorar el proyecto:

1. 🍴 Haz un **Fork** del repositorio
2. 🌿 Crea una **rama** para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. 💾 **Commit** tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. 📤 **Push** a la rama (`git push origin feature/nueva-funcionalidad`)
5. 🔄 Crea un **Pull Request**

## 📞 Contacto
- **📧 Email:** javier.fajardo@bancamia.com.co
- **🌐 Sitio Web:** [https://www.bancamia.com.co](https://www.bancamia.com.co)
- **📱 Teléfono:** +57 1 3185149128

## 🚀 Acerca de este Proyecto

Este proyecto es una **Prueba de Concepto (PoC)** desarrollada para validar procesos de **autoescalado con Kubernetes** en entornos de nube. La aplicación Librería Digital sirve como base para demostrar capacidades de escalabilidad horizontal, gestión de recursos dinámicos y despliegue automatizado en infraestructura cloud.

### 🎯 Objetivos de la PoC:
- **Validar autoescalado** de aplicaciones Java en Kubernetes
- **Probar gestión de recursos** dinámicos en entornos cloud
- **Demostrar despliegue automatizado** con CI/CD
- **Evaluar performance** bajo diferentes cargas de trabajo
- **Implementar monitoreo** y observabilidad en contenedores

---

*📅 Última actualización: Enero 2024*  
*🏷️ Versión: 1.0.0*  
*👨‍💻 Desarrollado con ❤️ por el equipo de Bancamía*