# 🍔 Sistema de Gestión de Comidas Rápidas (FoodSystem)

Aplicación de escritorio desarrollada en **Java** con interfaz gráfica en **Swing**, construida para la gestión integral de operaciones CRUD (Crear, Leer, Actualizar y Eliminar) de clientes y productos de comida rápida. 

El proyecto fue diseñado aplicando buenas prácticas de ingeniería de software, respetando rigurosamente los 4 pilares de la **Programación Orientada a Objetos (POO)**, los 5 principios **SOLID** y patrones arquitectónicos como **MVC**, **DAO** y **Singleton**, optimizado para el análisis de calidad y métricas con **Source Monitor**.

---

## 📑 Tabla de Contenidos
1. [Arquitectura del Sistema](#-1-arquitectura-del-sistema)
2. [Aplicación de los Pilares de la POO](#-2-aplicación-de-los-pilares-de-la-poo)
3. [Cumplimiento de los Principios SOLID](#-3-cumplimiento-de-los-principios-solid)
4. [Estructura del Proyecto y Paquetes](#-4-estructura-del-proyecto-y-paquetes)
5. [Configuración y Puesta en Marcha](#-5-configuración-y-puesta-en-marcha)
6. [Análisis de Métricas de Software (Source Monitor)](#-6-análisis-de-métricas-de-software-source-monitor)
7. [Documentación y Diagramas Entregables](#-7-documentación-y-diagramas-entregables)

---

## 🏛️ 1. Arquitectura del Sistema

La aplicación implementa una arquitectura por capas desacoplada que asegura la separación de responsabilidades:

```
[ Vista (Swing) ]  ──(usa interfaces)──>  [ Controlador ]  ──(usa interfaces)──>  [ DAO (JDBC) ]  ──>  [ Base de Datos (MySQL) ]
        │                                        │                                       │
        └────────────────────────────────────────┴───────────────────────────────────────┴─────────>  [ Modelo (POJOs) ]
```

### Patrones de Diseño Utilizados:
* **MVC (Modelo - Vista - Controlador):**
  * **Modelo (`com.empresa.modelo`):** Clases entidad puras (`Cliente`, `Comida`) que almacenan el estado y datos del dominio.
  * **Vista (`com.empresa.vista`):** Formularios interactivos y tablas (`VistaCliente`, `VistaComida`, `VistaPrincipal`). Captura eventos y renderiza datos sin contener lógica de negocio.
  * **Controlador (`com.empresa.control`):** Orquestador inteligente (`ControlCliente`, `ControlComida`) que valida datos, coordina operaciones y comunica la vista con los DAOs.
* **DAO (Data Access Object):**
  * Interfaz genérica `IDao<T, ID>` implementada por `ClienteDao` y `ComidaDao`, encapsulando las sentencias SQL (JDBC) y aislando la persistencia del resto de la aplicación.
* **Singleton:**
  * `ConexionBD`: Garantiza una única instancia de conexión activa hacia la base de datos MySQL, optimizando recursos del sistema.

---

## 🧩 2. Aplicación de los Pilares de la POO

El sistema refleja de forma explícita los cuatro pilares fundamentales de la Programación Orientada a Objetos:

### 1. Encapsulamiento
* Todos los atributos de las entidades (`Cliente`, `Comida`) son privados (`private`), restringiendo el acceso directo.
* La lectura y modificación se realiza exclusivamente mediante métodos de acceso públicos (`getters` y `setters`).
* Se implementaron los métodos canónicos de `Object`: `equals()`, `hashCode()` y `toString()` para garantizar integridad en colecciones y depuración.
* Constructores privados en clases que no deben instanciarse (`ConexionBD` para Singleton y `Validador` para utilidades estáticas).

### 2. Abstracción
* Las entidades `Cliente` y `Comida` modelan fielmente los elementos esenciales del mundo real requeridos por el negocio.
* Los detalles técnicos de JDBC, SQL, drivers y conexiones quedan ocultos tras las interfaces abstractas `IDao`, `IControlCliente` e `IControlComida`.

### 3. Polimorfismo
* Implementación de la interfaz genérica `IDao<T, ID>` tanto en `ClienteDao` (`IDao<Cliente, Integer>`) como en `ComidaDao` (`IDao<Comida, Integer>`).
* La capa visual interactúa polimórficamente con las interfaces `IControlCliente` e `IControlComida`, permitiendo intercambiar la lógica subyacente sin modificar la interfaz gráfica.

### 4. Herencia
* Herencia de componentes visuales de Java Swing: `VistaCliente` y `VistaComida` heredan de `javax.swing.JPanel`, mientras que `VistaPrincipal` hereda de `javax.swing.JFrame`.
* Jerarquía de excepciones personalizadas: `ClienteNoEncontradoException` y `ComidaNoEncontradaException` heredan de `java.lang.RuntimeException`.

---

## 💎 3. Cumplimiento de los Principios SOLID

| Principio | Definición | Implementación en FoodSystem |
| :--- | :--- | :--- |
| **S - Single Responsibility** *(Responsabilidad Única)* | Cada clase debe tener una sola razón para cambiar. | • `Cliente` / `Comida`: Solo representan datos.<br>• `ClienteDao` / `ComidaDao`: Solo ejecutan sentencias SQL.<br>• `ControlCliente` / `ControlComida`: Solo ejecutan reglas de negocio.<br>• `Validador`: Solo valida tipos y formatos.<br>• `VistaCliente` / `VistaComida`: Solo capturan inputs y renderizan la UI. |
| **O - Open/Closed** *(Abierto / Cerrado)* | El código debe estar abierto a extensión pero cerrado a modificación. | Se pueden incorporar nuevos orígenes de datos (ej. un DAO en memoria o para PostgreSQL) implementando `IDao<T, ID>` sin tocar una sola línea de los controladores ni de las vistas. |
| **L - Liskov Substitution** *(Sustitución de Liskov)* | Los subtipos deben poder sustituir a sus tipos base sin alterar el comportamiento del sistema. | Cualquier clase que implemente `IDao` o `IControlCliente` puede sustituir a la implementación por defecto en tiempo de ejecución sin romper la aplicación. |
| **I - Interface Segregation** *(Segregación de Interfaces)* | Los clientes no deben depender de métodos que no utilizan. | En lugar de un controlador monolítico, se segregaron las interfaces: `IControlCliente` solo define operaciones de clientes y `IControlComida` solo operaciones del menú. |
| **D - Dependency Inversion** *(Inversión de Dependencias)* | Los módulos de alto nivel no deben depender de los de bajo nivel; ambos deben depender de abstracciones. | • Las vistas dependen de interfaces (`IControlCliente`, `IControlComida`).<br>• Los controladores dependen de abstracciones (`IDao<T, ID>`) y permiten inyección de dependencias mediante sus constructores sobrecargados. |

---

## 📁 4. Estructura del Proyecto y Paquetes

```
FoodSystem
 ├── pom.xml                                  → Configuración de Maven y dependencias (MySQL Connector)
 ├── README.md                                → Documentación principal del sistema
 ├── sources.txt                              → Lista de archivos fuente para herramientas de métricas
 ├── docs/                                    → Entregables formales del taller
 │    ├── DOCUMENTACION_Y_METRICAS.md         → Informe de calidad e interpretación de Source Monitor
 │    ├── GUIA_DIAGRAMA_DE_CLASES.md          → Especificación, matriz y código del Diagrama de Clases
 │    └── GUIA_DIAGRAMA_CASOS_DE_USO.md       → Especificación RUP y código del Diagrama de Casos de Uso
 └── src
      └── main
           ├── resources
           │    └── food_system.sql           → Script de creación de base de datos y datos de prueba
           └── java
                └── com
                     └── empresa
                          ├── modelo          → Entidades puras (Cliente.java, Comida.java)
                          ├── dao             → Persistencia (IDao.java, ClienteDao.java, ComidaDao.java, ConexionBD.java)
                          ├── control         → Controladores e interfaces (IControlCliente.java, ControlCliente.java, etc.)
                          ├── util            → Utilidades generales (Validador.java)
                          ├── excepciones     → Excepciones del dominio (ClienteNoEncontradoException.java, etc.)
                          ├── vista           → Interfaz gráfica Swing (VistaCliente.java, VistaComida.java, VistaPrincipal.java)
                          └── launcher        → Clase de arranque (Main.java)
```

---

## 🚀 5. Configuración y Puesta en Marcha

### Requisitos Previos:
* **Java Development Kit (JDK):** Versión 17 o superior.
* **Servidor MySQL / MariaDB:** (ej. a través de XAMPP, WampServer o servicio local).

### Pasos de Configuración:

1. **Crear y poblar la base de datos:**
   * Abre tu gestor de base de datos (phpMyAdmin, MySQL Workbench o consola).
   * Ejecuta el script SQL ubicado en [`src/main/resources/food_system.sql`](src/main/resources/food_system.sql).

2. **Verificar parámetros de conexión:**
   * Revisa la clase [`com.empresa.dao.ConexionBD`](src/main/java/com/empresa/dao/ConexionBD.java):
   ```java
   private final String url = "jdbc:mysql://localhost:3307/food_system"; // Ajusta el puerto (3306 / 3307)
   private final String user = "root";
   private final String pass = "";                                       // Ajusta tu contraseña local
   ```

3. **Compilar y Ejecutar:**
   * **Desde IDE (IntelliJ IDEA / Eclipse / NetBeans):** Ejecuta la clase `Main.java` ubicada en `com.empresa.launcher`.
   * **Desde terminal (PowerShell / Bash):**
     ```powershell
     # Compilar clases
     javac -d target/classes (Get-ChildItem -Recurse -Filter *.java src/main/java).FullName
     
     # Ejecutar aplicación
     java -cp "target/classes;src/main/resources/*" com.empresa.launcher.Main
     ```

---

## 📊 6. Análisis de Métricas de Software (Source Monitor)

La herramienta **Source Monitor** fue utilizada para auditar la calidad interna del código fuente. Los resultados obtenidos demuestran un software de alta calidad, bajo acoplamiento y excelente mantenibilidad:

| Métrica | Rango en FoodSystem | Valor de Referencia en la Industria | Interpretación de Calidad |
| :--- | :---: | :---: | :--- |
| **Comment Percentage (% Comentarios)** | **~38% - 45%** | 20% - 40% | **Excelente.** Documentación formal en JavaDoc presente en todas las clases, métodos, parámetros y excepciones. |
| **Avg. Cyclomatic Complexity** | **1.2 - 1.5** | 1.0 - 2.0 | **Óptimo.** Métodos directos, modulares y fáciles de someter a pruebas unitarias. |
| **Max. Cyclomatic Complexity** | **4 - 5** | ≤ 10 | **Bajo riesgo.** No existen métodos complejos o con bifurcaciones excesivas. |
| **Max. Block Depth (Anidamiento)** | **3** | ≤ 4 | **Estructura limpia.** Ausencia de "código espagueti" o anidamientos profundos. |

---

## 📚 7. Documentación y Diagramas Entregables

Para consultar el detalle de los entregables formales solicitados en el taller, revisa los siguientes documentos:

* 📄 [**Informe de Métricas e Interpretación de Resultados**](docs/DOCUMENTACION_Y_METRICAS.md)
* 📐 [**Guía Técnica y Código del Diagrama de Clases UML**](docs/GUIA_DIAGRAMA_DE_CLASES.md)
* 👥 [**Guía Técnica y Código del Diagrama de Casos de Uso**](docs/GUIA_DIAGRAMA_CASOS_DE_USO.md)
