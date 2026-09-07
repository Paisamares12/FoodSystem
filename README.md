# Proyecto Gestión de Comidas Rápidas

Aplicación CRUD para la gestión de clientes y comidas rápidas de una empresa, desarrollada en Java siguiendo el patrón **MVC**, con **DAO** para acceso a datos y **Singleton** para la conexión a base de datos, respetando los principios **SOLID**.

Esta guía es para que **todo el equipo mantenga la misma estructura y no se rompa la arquitectura** al agregar código nuevo.

---

## 1. Estructura de paquetes

```
com.empresa
├── modelo      → Clases de datos puras (Cliente, Comida)
├── dao         → Acceso a base de datos (CRUD real con SQL)
├── control     → Lógica de negocio y validaciones
├── vista       → Interfaz gráfica (Swing / JFrame)
├── launcher    → Main.java (arranque de la app)
└── resources   → Plantilla de la base de datos (schema.sql)
```

**Regla de oro: cada capa solo puede hablar con la de al lado.**

```
Vista  →  Control  →  DAO  →  Base de datos
Vista  ←  Control  ←  DAO  ←  Base de datos
```

La Vista **nunca** salta directo al DAO, y el DAO **nunca** habla con la Vista.

---

## 2. Reglas por capa (léelas antes de programar)

### 🟦 `modelo`
- Son clases **planas**: solo atributos, constructores, getters y setters.
- **Prohibido**: `Scanner`, `System.out.println`, `JOptionPane`, validaciones, lógica de negocio, conexiones a BD.
- Si necesitas validar algo (ej. "el precio no puede ser negativo"), **esa validación va en `control`, no aquí**.

```java
// ✅ BIEN — Cliente.java
public class Cliente {
    private int id;
    private String nombre;
    // constructores, getters, setters. Nada más.
}
```

### 🟩 `dao`
- Solo contiene código SQL (`INSERT`, `UPDATE`, `DELETE`, `SELECT`).
- **Prohibido**: validar datos, mostrar mensajes, tomar decisiones de negocio.
- Cada DAO recibe la `Connection` por constructor (no la crea él mismo).
- No agregues métodos nuevos que no sean parte del CRUD sin avisar al equipo (para no romper la interfaz `IDao`).

### 🟨 `control`
- **Aquí va TODA la lógica de negocio y validaciones.** Es la única capa "inteligente".
- Ejemplos de lo que va aquí: verificar que un campo no esté vacío, que el precio sea mayor a cero, calcular totales, decidir si algo se puede eliminar, etc.
- **Prohibido**: código de Swing (`JFrame`, `JTextField`, `JOptionPane`) y código SQL directo. El control no sabe si hay una ventana o una consola del otro lado, y no sabe cómo se guarda en BD (para eso están el DAO).
- Los métodos de "crear" **no reciben `id`** (lo genera la BD). Los de "actualizar" sí lo reciben.

### 🟥 `vista`
- **Solo hace tres cosas: pedir datos al usuario (input), mostrar datos (output) y llamar a `control`.**
- **Prohibido total**:
  - Validar datos (ej. `if (precio < 0)`) → eso va en `control`.
  - Hacer cálculos o lógica de negocio.
  - Conectarse directo al DAO o a la base de datos.
  - Crear objetos del modelo con datos "decididos" por la vista (solo arma el objeto con lo que el usuario escribió y se lo pasa a `control`, o mejor aún, le pasa los datos sueltos y deja que `control` arme el objeto).
- La vista puede **recibir** objetos del modelo (ej. una `List<Cliente>`) para pintarlos en una tabla — eso no es lógica, es mostrar datos.
- Todo error de negocio debe venir como excepción desde `control`, y la vista solo lo muestra con `JOptionPane`. La vista no decide *qué* es un error, solo lo comunica.

### ⬜ `launcher` (Main.java)
- Solo instancia los controles y lanza la ventana principal. Nada más. Si `Main` empieza a crecer, algo está mal ubicado.

---

## 3. Configuración de la base de datos

La conexión está centralizada en **`ConexionBD.java`** (patrón Singleton), dentro del paquete `dao`.

📍 **Si tu MySQL local usa un puerto distinto a 3307, cámbialo ahí mismo:**

```java
// dao/ConexionBD.java
private final String url = "jdbc:mysql://localhost:3307/comidas_rapidas"; // ← cambia el puerto aquí
private final String user = "root";
private final String pass = "password"; // ← ajusta también tu password si es distinto
```

- El puerto por defecto de MySQL suele ser **3306**, pero en esta config quedó en **3307**. Si a ti te sirve por el 3306 (o cualquier otro), **solo modifica esta clase**, no toques nada más.
- **No subas tu contraseña real al repositorio** si vamos a compartir el código públicamente; si van a usar `.gitignore`, ya está contemplado en el proyecto.

### Plantilla de la base de datos

En `resources/schema.sql` está la plantilla con la estructura de las tablas (`cliente`, `comida`). Antes de correr la aplicación:

1. Crea la base de datos `comidas_rapidas` en tu MySQL local.
2. Ejecuta el script `schema.sql` para crear las tablas.
3. Verifica el puerto/usuario/contraseña en `ConexionBD.java` como se explicó arriba.

---

## 4. Checklist antes de hacer commit/push

- [ ] ¿Mi clase de `vista` tiene algún `if` de validación de negocio? → Muévelo a `control`.
- [ ] ¿Mi clase de `control` tiene algo de `Swing` o SQL? → Sácalo de ahí.
- [ ] ¿Mi clase de `modelo` tiene lógica o imprime algo? → Debe quedar solo con atributos y getters/setters.
- [ ] ¿Agregué un método nuevo al DAO? → Avisa al equipo, puede afectar la interfaz `IDao`.
- [ ] ¿Cambié el puerto o usuario de la BD? → Solo debería estar en `ConexionBD.java`, en ningún otro archivo.

---

## 5. Dudas de arquitectura

Si no saben en qué capa va algo, pregúntense:

> **"¿Esto es una decisión (negocio) o una acción mecánica (mostrar/guardar)?"**

- Decisión → `control`
- Mostrar/pedir datos → `vista`
- Guardar/consultar en BD → `dao`
- Solo datos → `modelo`

Cualquier duda, mejor preguntar en el grupo antes de improvisar, para no tener que reestructurar después. 🙌
