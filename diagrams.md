# Estructura del Proyecto Librería Digital

## 📁 Estructura Completa de Carpetas y Archivos

```
LibreriaDigital/
├── 📄 pom.xml                                    # Configuración principal de Maven - define dependencias y plugins
├── 📄 README.md                                  # Documentación del proyecto - instrucciones de instalación y uso
├── 📄 architecture.md                            # Análisis arquitectónico detallado de la aplicación
├── 📄 diagrams.md                                # Este archivo - estructura de carpetas y archivos
│
├── 📁 src/                                       # Código fuente principal
│   └── 📁 main/                                  # Código fuente de la aplicación
│       ├── 📁 java/                              # Código Java
│       │   └── 📁 org/                           # Paquete organizacional
│       │       └── 📁 example/                   # Paquete de ejemplo
│       │           ├── 📄 ConsultaServlet.java   # Servlet de prueba - muestra "Hola Mundo"
│       │           │
│       │           ├── 📁 model/                 # Capa de Modelo - Entidades de dominio
│       │           │   ├── 📄 Categoria.java     # Entidad Categoria - representa categorías de productos
│       │           │   ├── 📄 Cliente.java       # Entidad Cliente - datos de clientes del sistema
│       │           │   ├── 📄 Descuento.java     # Entidad Descuento - cupones y promociones
│       │           │   ├── 📄 DetalleVenta.java  # Entidad DetalleVenta - líneas de venta individuales
│       │           │   ├── 📄 Producto.java      # Entidad Producto - libros y productos de la librería
│       │           │   ├── 📄 Reporte.java       # Entidad Reporte - estructura de datos para reportes
│       │           │   └── 📄 Venta.java         # Entidad Venta - transacciones de venta completas
│       │           │
│       │           ├── 📁 service/               # Capa de Servicios - Lógica de negocio
│       │           │   ├── 📄 AlmacenamientoService.java  # Servicio principal - gestión de datos en memoria
│       │           │   └── 📄 ReporteService.java         # Servicio de reportes - generación de estadísticas
│       │           │
│       │           └── 📁 servlet/               # Capa de Controladores - Servlets (MVC Controller)
│       │               ├── 📄 AcercaDeServlet.java        # Servlet informativo - página "Acerca de"
│       │               ├── 📄 CategoriaServlet.java       # Controlador de categorías - CRUD categorías
│       │               ├── 📄 ClienteServlet.java         # Controlador de clientes - CRUD clientes
│       │               ├── 📄 DescuentoServlet.java       # Controlador de descuentos - gestión de promociones
│       │               ├── 📄 ProductoServlet.java        # Controlador de productos - CRUD productos
│       │               ├── 📄 ReporteServlet.java         # Controlador de reportes - generación de informes
│       │               └── 📄 VentaServlet.java           # Controlador de ventas - gestión de transacciones
│       │
│       └── 📁 webapp/                            # Recursos web - vistas y configuración
│           ├── 📄 index.jsp                      # Página principal - menú de navegación
│           └── 📁 WEB-INF/                       # Configuración de la aplicación web
│               └── 📄 web.xml                    # Descriptor de despliegue - mapeo de servlets
│
└── 📁 target/                                    # Archivos compilados (generado por Maven)
    └── (archivos generados automáticamente)
```

## 🏗️ Arquitectura por Capas

### **Capa de Presentación (View)**
- **Ubicación**: `src/main/webapp/`
- **Tecnología**: JSP, HTML, CSS
- **Archivos**:
  - `index.jsp` - Interfaz principal con menú de navegación
  - HTML generado dinámicamente desde los Servlets

### **Capa de Control (Controller)**
- **Ubicación**: `src/main/java/org/example/servlet/`
- **Tecnología**: Jakarta EE Servlets
- **Archivos**:
  - `ClienteServlet.java` - Gestión CRUD de clientes
  - `ProductoServlet.java` - Gestión CRUD de productos
  - `VentaServlet.java` - Gestión de transacciones de venta
  - `CategoriaServlet.java` - Gestión CRUD de categorías
  - `DescuentoServlet.java` - Gestión de descuentos y promociones
  - `ReporteServlet.java` - Generación de reportes y estadísticas
  - `AcercaDeServlet.java` - Página informativa del sistema

### **Capa de Servicios (Service)**
- **Ubicación**: `src/main/java/org/example/service/`
- **Tecnología**: Java POJOs
- **Archivos**:
  - `AlmacenamientoService.java` - Servicio principal con patrón Singleton para gestión de datos en memoria
  - `ReporteService.java` - Servicio especializado para generación de reportes

### **Capa de Modelo (Model)**
- **Ubicación**: `src/main/java/org/example/model/`
- **Tecnología**: Java POJOs (Entidades de dominio)
- **Archivos**:
  - `Cliente.java` - Entidad que representa un cliente del sistema
  - `Producto.java` - Entidad que representa un producto/libro
  - `Venta.java` - Entidad que representa una transacción de venta
  - `DetalleVenta.java` - Entidad que representa una línea de venta
  - `Categoria.java` - Entidad que representa una categoría de productos
  - `Descuento.java` - Entidad que representa un descuento o promoción
  - `Reporte.java` - Entidad que estructura los datos de reportes

## 📋 Detalles de Archivos por Categoría

### **Archivos de Configuración**
- `pom.xml` - Configuración Maven con dependencias Jakarta EE, plugins de compilación y despliegue
- `web.xml` - Configuración de la aplicación web con mapeo de servlets y configuración de sesiones

### **Archivos de Documentación**
- `README.md` - Documentación del proyecto con instrucciones de instalación y uso
- `architecture.md` - Análisis arquitectónico detallado de la aplicación
- `diagrams.md` - Este archivo con estructura de carpetas

### **Archivos de Prueba**
- `ConsultaServlet.java` - Servlet de prueba ubicado en el paquete raíz

## 🔄 Flujo de Datos

```
Cliente Web → Servlet (Controller) → Service (Business Logic) → Model (Data)
                ↓
            JSP/HTML (View) ← Response
```

## 📊 Estadísticas del Proyecto

- **Total de archivos Java**: 15
- **Total de archivos JSP**: 1
- **Total de archivos XML**: 2
- **Total de archivos Markdown**: 3
- **Total de carpetas**: 10
- **Capa de Controladores**: 7 servlets
- **Capa de Servicios**: 2 servicios
- **Capa de Modelo**: 7 entidades

## 🎯 Patrones Arquitectónicos Identificados

1. **MVC (Model-View-Controller)**
   - Model: Entidades en `model/`
   - View: JSP y HTML generado
   - Controller: Servlets en `servlet/`

2. **Singleton Pattern**
   - `AlmacenamientoService` implementa patrón Singleton

3. **Service Layer Pattern**
   - Servicios en `service/` encapsulan lógica de negocio

4. **Front Controller Pattern**
   - Cada Servlet actúa como front controller para su dominio

5. **Data Transfer Object (DTO)**
   - Las entidades actúan como DTOs para transferir datos

## 🚀 Endpoints Disponibles

- `/` - Página principal (index.jsp)
- `/clientes` - Gestión de clientes
- `/productos` - Gestión de productos
- `/ventas` - Gestión de ventas
- `/categorias` - Gestión de categorías
- `/descuentos` - Gestión de descuentos
- `/reportes` - Generación de reportes
- `/acerca-de` - Información del sistema

---

# 📊 Diagramas Arquitectónicos

## 1. Diagrama de Componentes

```mermaid
graph TB
    classDef clientStyle fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    classDef presentationStyle fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    classDef controllerStyle fill:#e8f5e8,stroke:#1b5e20,stroke-width:2px
    classDef serviceStyle fill:#fff3e0,stroke:#e65100,stroke-width:2px
    classDef modelStyle fill:#fce4ec,stroke:#880e4f,stroke-width:2px
    classDef configStyle fill:#f1f8e9,stroke:#33691e,stroke-width:2px
    
    subgraph "🌐 Cliente Web"
        Browser[🌐 Navegador Web]
    end
    
    subgraph "🎨 Capa de Presentación"
        JSP[index.jsp]
        HTML[HTML Generado]
    end
    
    subgraph "🎮 Capa de Control"
        CS[ClienteServlet]
        PS[ProductoServlet]
        VS[VentaServlet]
        CatS[CategoriaServlet]
        DS[DescuentoServlet]
        RS[ReporteServlet]
        AS[AcercaDeServlet]
    end
    
    subgraph "⚙️ Capa de Servicios"
        ASvc[AlmacenamientoService]
        RSvc[ReporteService]
    end
    
    subgraph "📊 Capa de Modelo"
        Cliente[Cliente]
        Producto[Producto]
        Venta[Venta]
        DetalleVenta[DetalleVenta]
        Categoria[Categoria]
        Descuento[Descuento]
        Reporte[Reporte]
    end
    
    subgraph "⚙️ Configuración"
        WebXML[web.xml]
        PomXML[pom.xml]
    end
    
    Browser --> JSP
    Browser --> HTML
    JSP --> CS
    JSP --> PS
    JSP --> VS
    JSP --> CatS
    JSP --> DS
    JSP --> RS
    JSP --> AS
    
    CS --> ASvc
    PS --> ASvc
    VS --> ASvc
    CatS --> ASvc
    DS --> ASvc
    RS --> RSvc
    AS --> ASvc
    
    ASvc --> Cliente
    ASvc --> Producto
    ASvc --> Venta
    ASvc --> Categoria
    ASvc --> Descuento
    RSvc --> Reporte
    
    Venta --> Cliente
    Venta --> DetalleVenta
    DetalleVenta --> Producto
    Producto --> Categoria
    
    WebXML -.-> CS
    WebXML -.-> PS
    WebXML -.-> VS
    WebXML -.-> CatS
    WebXML -.-> DS
    WebXML -.-> RS
    WebXML -.-> AS
    
    class Browser clientStyle
    class JSP,HTML presentationStyle
    class CS,PS,VS,CatS,DS,RS,AS controllerStyle
    class ASvc,RSvc serviceStyle
    class Cliente,Producto,Venta,DetalleVenta,Categoria,Descuento,Reporte modelStyle
    class WebXML,PomXML configStyle
```

## 2. Diagrama de Despliegue

```mermaid
graph TB
    classDef userStyle fill:#e3f2fd,stroke:#1565c0,stroke-width:3px
    classDef clientStyle fill:#f3e5f5,stroke:#7b1fa2,stroke-width:3px
    classDef serverStyle fill:#e8f5e8,stroke:#2e7d32,stroke-width:3px
    classDef containerStyle fill:#fff3e0,stroke:#f57c00,stroke-width:3px
    classDef appStyle fill:#fce4ec,stroke:#c2185b,stroke-width:3px
    classDef infraStyle fill:#f1f8e9,stroke:#388e3c,stroke-width:3px
    
    subgraph "👤 Cliente"
        User[👤 Usuario]
        Browser[🌐 Navegador Web]
    end
    
    subgraph "🖥️ Servidor Web"
        subgraph "🐳 Contenedor Web (Jetty/Tomcat)"
            subgraph "📦 Aplicación WAR"
                subgraph "🎨 Capa Web"
                    JSP[index.jsp]
                    WebXML[web.xml]
                end
                
                subgraph "⚙️ Capa de Negocio"
                    Servlets[Servlets]
                    Services[Services]
                end
                
                subgraph "📊 Capa de Datos"
                    Models[Modelos]
                    Memory[Almacenamiento en Memoria]
                end
            end
        end
    end
    
    subgraph "🏗️ Infraestructura"
        JVM[JVM Java 11]
        OS[Sistema Operativo]
        Server[Servidor Físico/Virtual]
    end
    
    User --> Browser
    Browser --> JSP
    Browser --> Servlets
    
    JSP --> Servlets
    Servlets --> Services
    Services --> Models
    Models --> Memory
    
    Servlets --> WebXML
    WebXML --> JSP
    
    Memory --> JVM
    JVM --> OS
    OS --> Server
    
    class User,Browser userStyle
    class JSP,WebXML,Servlets,Services,Models,Memory clientStyle
    class JVM,OS,Server infraStyle
```

## 3. Diagrama de Flujo de Datos

```mermaid
flowchart TD
    classDef startEndStyle fill:#e8f5e8,stroke:#2e7d32,stroke-width:3px
    classDef processStyle fill:#e3f2fd,stroke:#1565c0,stroke-width:2px
    classDef decisionStyle fill:#fff3e0,stroke:#f57c00,stroke-width:3px
    classDef dataStyle fill:#fce4ec,stroke:#c2185b,stroke-width:2px
    classDef serviceStyle fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px
    
    Start([🚀 Inicio]) --> Browser[🌐 Navegador]
    Browser --> Request{❓ ¿Tipo de Petición?}
    
    Request -->|GET /| JSP[index.jsp]
    Request -->|GET /clientes| ClienteServlet
    Request -->|POST /clientes| ClienteServlet
    Request -->|GET /productos| ProductoServlet
    Request -->|POST /productos| ProductoServlet
    Request -->|GET /ventas| VentaServlet
    Request -->|POST /ventas| VentaServlet
    Request -->|GET /reportes| ReporteServlet
    Request -->|POST /reportes| ReporteServlet
    
    JSP --> Menu[📋 Menú de Navegación]
    Menu --> Browser
    
    ClienteServlet --> ClienteAction{🔧 Acción Cliente}
    ClienteAction -->|📋 Listar| AlmacenamientoService
    ClienteAction -->|➕ Agregar| AlmacenamientoService
    ClienteAction -->|❌ Eliminar| AlmacenamientoService
    
    ProductoServlet --> ProductoAction{🔧 Acción Producto}
    ProductoAction -->|📋 Listar| AlmacenamientoService
    ProductoAction -->|➕ Agregar| AlmacenamientoService
    ProductoAction -->|❌ Eliminar| AlmacenamientoService
    
    VentaServlet --> VentaAction{🔧 Acción Venta}
    VentaAction -->|🆕 Crear| AlmacenamientoService
    VentaAction -->|✅ Completar| AlmacenamientoService
    VentaAction -->|❌ Cancelar| AlmacenamientoService
    
    ReporteServlet --> ReporteAction{🔧 Acción Reporte}
    ReporteAction -->|📊 Generar| ReporteService
    ReporteAction -->|🔍 Consultar| ReporteService
    
    AlmacenamientoService --> Memory[(💾 Memoria)]
    ReporteService --> Memory
    
    Memory --> Response[📄 Respuesta HTML]
    Response --> Browser
    
    Browser --> End([🏁 Fin])
    
    class Start,End startEndStyle
    class Browser,JSP,Menu,ClienteServlet,ProductoServlet,VentaServlet,ReporteServlet,Response processStyle
    class Request,ClienteAction,ProductoAction,VentaAction,ReporteAction decisionStyle
    class Memory dataStyle
    class AlmacenamientoService,ReporteService serviceStyle
```

## 4. Diagrama de Clases Principal

```mermaid
classDiagram
    class Cliente {
        <<Entity>>
        -int id
        -String nombre
        -String email
        -String telefono
        +Cliente(id, nombre, email, telefono)
        +getId() int
        +setId(id) void
        +getNombre() String
        +setNombre(nombre) void
        +getEmail() String
        +setEmail(email) void
        +getTelefono() String
        +setTelefono(telefono) void
    }
    
    class Producto {
        <<Entity>>
        -int id
        -String nombre
        -double precio
        -int stock
        -Categoria categoria
        +Producto(id, nombre, precio, stock, categoria)
        +getId() int
        +setId(id) void
        +getNombre() String
        +setNombre(nombre) void
        +getPrecio() double
        +setPrecio(precio) void
        +getStock() int
        +setStock(stock) void
        +getCategoria() Categoria
        +setCategoria(categoria) void
        +reducirStock(cantidad) void
        +aumentarStock(cantidad) void
    }
    
    class Categoria {
        <<Entity>>
        -int id
        -String nombre
        -String descripcion
        +Categoria(id, nombre, descripcion)
        +getId() int
        +setId(id) void
        +getNombre() String
        +setNombre(nombre) void
        +getDescripcion() String
        +setDescripcion(descripcion) void
        +toString() String
    }
    
    class Venta {
        <<Entity>>
        -int id
        -Cliente cliente
        -List~DetalleVenta~ detalles
        -LocalDateTime fecha
        -double total
        -String estado
        -String notas
        +Venta(id, cliente)
        +agregarProducto(producto, cantidad) void
        +calcularTotal() void
        +completarVenta() void
        +cancelarVenta() void
        +getId() int
        +getCliente() Cliente
        +getDetalles() List~DetalleVenta~
        +getFecha() LocalDateTime
        +getTotal() double
        +getEstado() String
        +setEstado(estado) void
        +getNotas() String
        +setNotas(notas) void
    }
    
    class DetalleVenta {
        <<Entity>>
        -Producto producto
        -int cantidad
        -double precioUnitario
        +DetalleVenta(producto, cantidad)
        +getSubtotal() double
        +getProducto() Producto
        +getCantidad() int
        +setCantidad(cantidad) void
        +getPrecioUnitario() double
    }
    
    class Descuento {
        <<Entity>>
        -int id
        -String codigo
        -double porcentaje
        -LocalDate fechaInicio
        -LocalDate fechaFin
        -String tipo
        -int referenciaId
        -boolean activo
        +Descuento(id, codigo, porcentaje, fechaInicio, fechaFin, tipo, referenciaId)
        +esValido() boolean
        +calcularDescuento(monto) double
        +getId() int
        +setId(id) void
        +getCodigo() String
        +setCodigo(codigo) void
        +getPorcentaje() double
        +setPorcentaje(porcentaje) void
        +getFechaInicio() LocalDate
        +setFechaInicio(fechaInicio) void
        +getFechaFin() LocalDate
        +setFechaFin(fechaFin) void
        +getTipo() String
        +setTipo(tipo) void
        +getReferenciaId() int
        +setReferenciaId(referenciaId) void
        +isActivo() boolean
        +setActivo(activo) void
    }
    
    class Reporte {
        <<Entity>>
        -String tipo
        -LocalDate fechaInicio
        -LocalDate fechaFin
        -Map~String, Object~ datos
        -List~Map~String, Object~~ detalles
        +Reporte()
        +Reporte(tipo, fechaInicio, fechaFin)
        +getTipo() String
        +setTipo(tipo) void
        +getFechaInicio() LocalDate
        +setFechaInicio(fechaInicio) void
        +getFechaFin() LocalDate
        +setFechaFin(fechaFin) void
        +getDatos() Map~String, Object~
        +setDatos(datos) void
        +getDetalles() List~Map~String, Object~~
        +setDetalles(detalles) void
    }
    
    Venta "1" *-- "1..*" DetalleVenta : contiene
    Venta "*" -- "1" Cliente : pertenece a
    DetalleVenta "*" -- "1" Producto : referencia a
    Producto "*" -- "1" Categoria : pertenece a
```

## 5. Diagrama de Paquetes

```mermaid
graph TB
    classDef packageStyle fill:#e8f5e8,stroke:#2e7d32,stroke-width:3px
    classDef modelStyle fill:#fce4ec,stroke:#c2185b,stroke-width:2px
    classDef serviceStyle fill:#fff3e0,stroke:#f57c00,stroke-width:2px
    classDef controllerStyle fill:#e3f2fd,stroke:#1565c0,stroke-width:2px
    classDef configStyle fill:#f1f8e9,stroke:#388e3c,stroke-width:2px
    
    subgraph "📦 org.example"
        subgraph "📊 model"
            Cliente[Cliente.java]
            Producto[Producto.java]
            Venta[Venta.java]
            DetalleVenta[DetalleVenta.java]
            Categoria[Categoria.java]
            Descuento[Descuento.java]
            Reporte[Reporte.java]
        end
        
        subgraph "⚙️ service"
            AlmacenamientoService[AlmacenamientoService.java]
            ReporteService[ReporteService.java]
        end
        
        subgraph "🎮 servlet"
            ClienteServlet[ClienteServlet.java]
            ProductoServlet[ProductoServlet.java]
            VentaServlet[VentaServlet.java]
            CategoriaServlet[CategoriaServlet.java]
            DescuentoServlet[DescuentoServlet.java]
            ReporteServlet[ReporteServlet.java]
            AcercaDeServlet[AcercaDeServlet.java]
        end
        
        ConsultaServlet[ConsultaServlet.java]
    end
    
    subgraph "🌐 webapp"
        indexJSP[index.jsp]
        webXML[web.xml]
    end
    
    subgraph "⚙️ config"
        pomXML[pom.xml]
    end
    
    servlet --> service
    servlet --> model
    service --> model
    ConsultaServlet --> model
    
    webXML -.-> servlet
    pomXML -.-> model
    pomXML -.-> service
    pomXML -.-> servlet
    
    class Cliente,Producto,Venta,DetalleVenta,Categoria,Descuento,Reporte modelStyle
    class AlmacenamientoService,ReporteService serviceStyle
    class ClienteServlet,ProductoServlet,VentaServlet,CategoriaServlet,DescuentoServlet,ReporteServlet,AcercaDeServlet,ConsultaServlet controllerStyle
    class indexJSP,webXML,pomXML configStyle
```

## 6. Diagramas de Clases por Carpeta

### 6.1 Diagrama de Clases - Carpeta `model`

```mermaid
classDiagram
    class Cliente {
        <<Entity>>
        -int id
        -String nombre
        -String email
        -String telefono
        +Cliente(id, nombre, email, telefono)
        +getId() int
        +setId(id) void
        +getNombre() String
        +setNombre(nombre) void
        +getEmail() String
        +setEmail(email) void
        +getTelefono() String
        +setTelefono(telefono) void
    }
    
    class Producto {
        <<Entity>>
        -int id
        -String nombre
        -double precio
        -int stock
        -Categoria categoria
        +Producto(id, nombre, precio, stock, categoria)
        +getId() int
        +setId(id) void
        +getNombre() String
        +setNombre(nombre) void
        +getPrecio() double
        +setPrecio(precio) void
        +getStock() int
        +setStock(stock) void
        +getCategoria() Categoria
        +setCategoria(categoria) void
        +reducirStock(cantidad) void
        +aumentarStock(cantidad) void
    }
    
    class Categoria {
        <<Entity>>
        -int id
        -String nombre
        -String descripcion
        +Categoria(id, nombre, descripcion)
        +getId() int
        +setId(id) void
        +getNombre() String
        +setNombre(nombre) void
        +getDescripcion() String
        +setDescripcion(descripcion) void
        +toString() String
    }
    
    class Venta {
        <<Entity>>
        -int id
        -Cliente cliente
        -List~DetalleVenta~ detalles
        -LocalDateTime fecha
        -double total
        -String estado
        -String notas
        +Venta(id, cliente)
        +agregarProducto(producto, cantidad) void
        +calcularTotal() void
        +completarVenta() void
        +cancelarVenta() void
        +getId() int
        +getCliente() Cliente
        +getDetalles() List~DetalleVenta~
        +getFecha() LocalDateTime
        +getTotal() double
        +getEstado() String
        +setEstado(estado) void
        +getNotas() String
        +setNotas(notas) void
    }
    
    class DetalleVenta {
        <<Entity>>
        -Producto producto
        -int cantidad
        -double precioUnitario
        +DetalleVenta(producto, cantidad)
        +getSubtotal() double
        +getProducto() Producto
        +getCantidad() int
        +setCantidad(cantidad) void
        +getPrecioUnitario() double
    }
    
    class Descuento {
        <<Entity>>
        -int id
        -String codigo
        -double porcentaje
        -LocalDate fechaInicio
        -LocalDate fechaFin
        -String tipo
        -int referenciaId
        -boolean activo
        +Descuento(id, codigo, porcentaje, fechaInicio, fechaFin, tipo, referenciaId)
        +esValido() boolean
        +calcularDescuento(monto) double
        +getId() int
        +setId(id) void
        +getCodigo() String
        +setCodigo(codigo) void
        +getPorcentaje() double
        +setPorcentaje(porcentaje) void
        +getFechaInicio() LocalDate
        +setFechaInicio(fechaInicio) void
        +getFechaFin() LocalDate
        +setFechaFin(fechaFin) void
        +getTipo() String
        +setTipo(tipo) void
        +getReferenciaId() int
        +setReferenciaId(referenciaId) void
        +isActivo() boolean
        +setActivo(activo) void
    }
    
    class Reporte {
        <<Entity>>
        -String tipo
        -LocalDate fechaInicio
        -LocalDate fechaFin
        -Map~String, Object~ datos
        -List~Map~String, Object~~ detalles
        +Reporte()
        +Reporte(tipo, fechaInicio, fechaFin)
        +getTipo() String
        +setTipo(tipo) void
        +getFechaInicio() LocalDate
        +setFechaInicio(fechaInicio) void
        +getFechaFin() LocalDate
        +setFechaFin(fechaFin) void
        +getDatos() Map~String, Object~
        +setDatos(datos) void
        +getDetalles() List~Map~String, Object~~
        +setDetalles(detalles) void
    }
    
    Venta "1" *-- "1..*" DetalleVenta : contiene
    Venta "*" -- "1" Cliente : pertenece a
    DetalleVenta "*" -- "1" Producto : referencia a
    Producto "*" -- "1" Categoria : pertenece a
```

### 6.2 Diagrama de Clases - Carpeta `service`

```mermaid
classDiagram
    class AlmacenamientoService {
        <<Service>>
        -static AlmacenamientoService instance
        -List~Cliente~ clientes
        -List~Producto~ productos
        -List~Venta~ ventas
        -List~Categoria~ categorias
        -List~Descuento~ descuentos
        -AtomicInteger clienteIdCounter
        -AtomicInteger productoIdCounter
        -AtomicInteger ventaIdCounter
        -AtomicInteger categoriaIdCounter
        -AtomicInteger descuentoIdCounter
        +AlmacenamientoService()
        +getInstance() AlmacenamientoService
        +getClientes() List~Cliente~
        +agregarCliente(nombre, email, telefono) Cliente
        +getCliente(id) Cliente
        +eliminarCliente(id) boolean
        +getProductos() List~Producto~
        +agregarProducto(nombre, precio, stock, categoria) Producto
        +getProducto(id) Producto
        +eliminarProducto(id) boolean
        +getProductosPorCategoria(categoriaId) List~Producto~
        +getVentas() List~Venta~
        +crearVenta(clienteId) Venta
        +getVenta(id) Venta
        +getVentasPorCliente(clienteId) List~Venta~
        +getVentasPorEstado(estado) List~Venta~
        +completarVenta(ventaId) boolean
        +cancelarVenta(ventaId) boolean
        +getCategorias() List~Categoria~
        +agregarCategoria(nombre, descripcion) Categoria
        +getCategoria(id) Categoria
        +getDescuentos() List~Descuento~
        +agregarDescuento(codigo, porcentaje, fechaInicio, fechaFin, tipo, referenciaId) Descuento
        +getDescuentosValidos() List~Descuento~
        +getDescuentosPorTipo(tipo) List~Descuento~
        +calcularDescuentoTotal(venta) double
    }
    
    class ReporteService {
        <<Service>>
        +ReporteService()
        +generarReporte(tipo, fechaInicio, fechaFin) Reporte
        +generarReporteVentasPorPeriodo(fechaInicio, fechaFin) Reporte
        +generarReporteProductosMasVendidos(fechaInicio, fechaFin) Reporte
        +generarReporteClientesMasFrecuentes(fechaInicio, fechaFin) Reporte
        +generarReporteIngresosPorCategoria(fechaInicio, fechaFin) Reporte
        +generarReporteDescuentosAplicados(fechaInicio, fechaFin) Reporte
    }
    
    AlmacenamientoService --> Cliente
    AlmacenamientoService --> Producto
    AlmacenamientoService --> Venta
    AlmacenamientoService --> Categoria
    AlmacenamientoService --> Descuento
    ReporteService --> Reporte
    ReporteService --> AlmacenamientoService
```

### 6.3 Diagrama de Clases - Carpeta `servlet`

```mermaid
classDiagram
    class HttpServlet {
        <<abstract>>
        +doGet(request, response) void
        +doPost(request, response) void
    }
    
    class ClienteServlet {
        <<Controller>>
        -AlmacenamientoService almacenamiento
        +ClienteServlet()
        +doGet(request, response) void
        +doPost(request, response) void
        -mostrarClientes(response, clientes) void
        -mostrarCliente(response, cliente) void
    }
    
    class ProductoServlet {
        <<Controller>>
        -AlmacenamientoService almacenamiento
        +ProductoServlet()
        +doGet(request, response) void
        +doPost(request, response) void
        -mostrarProductos(response, productos) void
        -mostrarProducto(response, producto) void
    }
    
    class VentaServlet {
        <<Controller>>
        -AlmacenamientoService almacenamiento
        +VentaServlet()
        +doGet(request, response) void
        +doPost(request, response) void
        -mostrarVentas(response, ventas) void
        -mostrarVenta(response, venta) void
        -mostrarFormularioVenta(response) void
    }
    
    class CategoriaServlet {
        <<Controller>>
        -AlmacenamientoService almacenamiento
        +CategoriaServlet()
        +doGet(request, response) void
        +doPost(request, response) void
        -mostrarCategorias(response, categorias) void
        -mostrarCategoria(response, categoria) void
    }
    
    class DescuentoServlet {
        <<Controller>>
        -AlmacenamientoService almacenamiento
        +DescuentoServlet()
        +doGet(request, response) void
        +doPost(request, response) void
        -mostrarDescuentos(response, descuentos) void
        -mostrarDescuento(response, descuento) void
    }
    
    class ReporteServlet {
        <<Controller>>
        -ReporteService reporteService
        -DateTimeFormatter formatter
        +ReporteServlet()
        +doGet(request, response) void
        -mostrarFormularioReportes(response) void
        -mostrarReporte(reporte, response) void
        -obtenerTituloReporte(tipo) String
        -obtenerColumnasReporte(tipo) String[]
    }
    
    class AcercaDeServlet {
        <<Controller>>
        +AcercaDeServlet()
        +doGet(request, response) void
    }
    
    HttpServlet <|-- ClienteServlet
    HttpServlet <|-- ProductoServlet
    HttpServlet <|-- VentaServlet
    HttpServlet <|-- CategoriaServlet
    HttpServlet <|-- DescuentoServlet
    HttpServlet <|-- ReporteServlet
    HttpServlet <|-- AcercaDeServlet
    
    ClienteServlet --> AlmacenamientoService
    ProductoServlet --> AlmacenamientoService
    VentaServlet --> AlmacenamientoService
    CategoriaServlet --> AlmacenamientoService
    DescuentoServlet --> AlmacenamientoService
    ReporteServlet --> ReporteService
```

### 6.4 Diagrama de Clases - Carpeta `webapp`

```mermaid
classDiagram
    class JSP {
        <<View>>
        +index.jsp
        +contentType: text/html;charset=UTF-8
        +language: java
        +title: Librería Digital
        +menu: Gestión de Clientes, Productos, Categorías, Ventas, Reportes, Descuentos, Acerca de
    }
    
    class WebXML {
        <<Configuration>>
        +web.xml
        +servlet-mappings
        +welcome-file-list
        +version: 4.0
    }
    
    class HTML {
        <<Generated View>>
        +dynamic content
        +tables
        +forms
        +navigation
    }
    
    JSP --> HTML : generates
    WebXML --> JSP : configures
    WebXML --> HTML : configures
``` 