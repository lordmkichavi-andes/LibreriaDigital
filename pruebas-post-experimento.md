# 🧪 Script de Pruebas de Performance - Librería Digital

## 📋 Descripción
Este script automatiza las pruebas de performance comparativas entre la versión monolítica y modernizada de la aplicación Librería Digital desplegada en AWS Elastic Beanstalk.

## 🎯 Objetivo
Comparar los tiempos de respuesta de ambos endpoints para evaluar la mejora en performance después de la modernización.

## 🔧 Configuración

### **Endpoints Configurados:**
- **Monolítico:** `http://libreria-digital-war-env-monolit-env.eba-hdzjpmj8.us-east-2.elasticbeanstalk.com`
- **Modernizado:** `http://libreria-digital-war-env-modernizada.eba-xxguactp.us-east-2.elasticbeanstalk.com`

### **Endpoints Probados:**
- `/` - Página principal
- `/clientes` - Gestión de clientes
- `/productos` - Gestión de productos

## 🚀 Uso

### **Ejecutar el Script:**
```bash
# Dar permisos de ejecución (solo la primera vez)
chmod +x pruebas-post-experimento.sh

# Ejecutar pruebas
./pruebas-post-experimento.sh
```

### **Ejemplo de Salida:**
```
==========================================
   PERFORMANCE TEST - LIBRERÍA DIGITAL    
==========================================
Date: Mon Jan 15 10:30:00 EST 2024

==========================================
Endpoint: /
------------------------------------------
Testing MONOLITH   - /:
  Test 1: 245ms
  Test 2: 198ms
  Test 3: 267ms
  Test 4: 223ms
  Test 5: 189ms

Testing MODERNIZED - /:
  Test 1: 156ms
  Test 2: 134ms
  Test 3: 142ms
  Test 4: 148ms
  Test 5: 139ms

==========================================
Test completed!
==========================================
```

## 📊 Métricas Recolectadas

### **Por Endpoint:**
- **5 mediciones** consecutivas por endpoint
- **Tiempo de respuesta** en milisegundos
- **Pausa de 0.5 segundos** entre mediciones

### **Cálculos Automáticos:**
- Tiempo promedio por endpoint
- Comparación directa monolítico vs modernizado
- Identificación de mejoras en performance

## 🔍 Interpretación de Resultados

### **Tiempos de Respuesta:**
- **< 200ms:** Excelente performance
- **200-500ms:** Performance aceptable
- **> 500ms:** Requiere optimización

### **Comparación:**
- **Mejora > 20%:** Modernización exitosa
- **Mejora 10-20%:** Mejora moderada
- **Mejora < 10%:** Mejora mínima

## 🛠️ Personalización

### **Modificar Endpoints:**
Editar la variable `endpoints` en el script:
```bash
endpoints=("/" "/clientes" "/productos" "/ventas")
```

### **Cambiar URLs:**
Actualizar las variables `MONO` y `MODERN`:
```bash
MONO="tu-nuevo-endpoint-monolitico"
MODERN="tu-nuevo-endpoint-modernizado"
```

### **Ajustar Número de Pruebas:**
Modificar el bucle en la función `test_endpoint`:
```bash
for i in 1 2 3 4 5 6 7 8 9 10; do
```

## 📈 Análisis de Resultados

### **Factores que Afectan Performance:**
- **Latencia de red** entre cliente y servidor
- **Carga del servidor** en el momento de la prueba
- **Tamaño de la respuesta** del endpoint
- **Complejidad de procesamiento** en el backend

### **Recomendaciones:**
- Ejecutar pruebas en **horarios de baja carga**
- Realizar **múltiples ejecuciones** para promediar resultados
- Considerar **factores externos** (red, servidor, etc.)
- Documentar **condiciones de prueba** para reproducibilidad

## 🔧 Troubleshooting

### **Error: "Permission denied"**
```bash
chmod +x pruebas-post-experimento.sh
```

### **Error: "curl: command not found"**
```bash
# Instalar curl en Ubuntu/Debian
sudo apt install curl

# Instalar curl en macOS
brew install curl
```

### **Error: "Connection refused"**
- Verificar que los endpoints estén activos
- Comprobar conectividad de red
- Validar URLs en el script

## 📝 Logs y Reportes

### **Generar Reporte Detallado:**
```bash
./pruebas-post-experimento.sh > performance-report-$(date +%Y%m%d).log
```

### **Análisis de Logs:**
```bash
# Filtrar solo tiempos de respuesta
grep "Test [0-9]:" performance-report-*.log

# Calcular promedios
awk '/Test [0-9]:/ {sum+=$3; count++} END {print "Average:", sum/count "ms"}' performance-report-*.log
```

## 🔗 Enlaces Relacionados

- **[README.md](./README.md)** - Documentación principal del proyecto
- **[architecture.md](./architecture.md)** - Análisis arquitectónico
- **[diagrams.md](./diagrams.md)** - Diagramas del sistema

---

*📅 Última actualización: Enero 2024*  
*🏷️ Versión del Script: 1.0.0*  
*👨‍💻 Desarrollado por el equipo de Bancamía* 