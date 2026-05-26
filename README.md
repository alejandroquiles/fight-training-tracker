<h1 align="center">Fight Training Tracker</h1>

<p align="center">
  Aplicación de escritorio desarrollada en <b>JavaFX</b> y <b>Maven</b> para registrar entrenamientos, visualizar progreso y guardar sesiones de forma local.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/JavaFX-Desktop%20GUI-2563EB?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Maven-Build%20Tool-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" />
  <img src="https://img.shields.io/badge/Git-Version%20Control-F05032?style=for-the-badge&logo=git&logoColor=white" />
  <img src="https://img.shields.io/badge/Status-V1%20Functional-22C55E?style=for-the-badge" />
</p>

---

## Sobre el proyecto

**Fight Training Tracker** es un proyecto personal creado para practicar el desarrollo de una aplicación Java de escritorio más completa, pasando de una interfaz básica a una aplicación con dashboard, historial, filtros y persistencia local.

El objetivo principal del proyecto es reforzar conceptos como **Programación Orientada a Objetos**, **JavaFX**, **Maven**, **Git/GitHub**, separación por capas y gestión de datos en archivo CSV.

---

## Funcionalidades principales

<table>
  <tr>
    <td width="50%">
      <h3>Registro de entrenamientos</h3>
      <p>Permite añadir sesiones indicando título, tipo, fecha, duración, intensidad y notas.</p>
    </td>
    <td width="50%">
      <h3>Dashboard de progreso</h3>
      <p>Muestra sesiones totales, minutos entrenados, intensidad media y último tipo de entrenamiento registrado.</p>
    </td>
  </tr>
  <tr>
    <td width="50%">
      <h3>Historial visual</h3>
      <p>Las sesiones se muestran en tarjetas con colores según el tipo de entrenamiento.</p>
    </td>
    <td width="50%">
      <h3>Filtros por tipo</h3>
      <p>Permite filtrar el historial por Fuerza, Cardio, Técnica, Sparring, Movilidad u Otro.</p>
    </td>
  </tr>
  <tr>
    <td width="50%">
      <h3>Eliminar sesiones</h3>
      <p>Cada entrenamiento puede eliminarse desde el historial, actualizando automáticamente las estadísticas.</p>
    </td>
    <td width="50%">
      <h3>Persistencia CSV</h3>
      <p>Los entrenamientos se guardan en un archivo local y se cargan automáticamente al volver a abrir la aplicación.</p>
    </td>
  </tr>
</table>

---

## Tecnologías utilizadas

<table>
  <tr>
    <td width="33%">
      <h3>Java 21</h3>
      <p>Lenguaje principal utilizado para construir la lógica, los modelos y la estructura general del proyecto.</p>
    </td>
    <td width="33%">
      <h3>JavaFX</h3>
      <p>Framework utilizado para crear la interfaz gráfica de escritorio, el dashboard, formularios y componentes visuales.</p>
    </td>
    <td width="33%">
      <h3>Maven</h3>
      <p>Herramienta utilizada para gestionar el proyecto, las dependencias y la ejecución de JavaFX.</p>
    </td>
  </tr>
  <tr>
    <td width="33%">
      <h3>CSV</h3>
      <p>Formato utilizado para guardar y cargar entrenamientos de forma local.</p>
    </td>
    <td width="33%">
      <h3>Git</h3>
      <p>Control de versiones mediante commits progresivos y descriptivos durante todo el desarrollo.</p>
    </td>
    <td width="33%">
      <h3>GitHub</h3>
      <p>Repositorio remoto para publicar el proyecto y mantenerlo como parte del portfolio.</p>
    </td>
  </tr>
</table>

---

## Estructura del proyecto

```txt
src/main/java/com/alejandroquiles/fighttracker
├── app
│   └── MainApp.java
├── model
│   ├── TrainingSession.java
│   └── TrainingType.java
├── repository
│   └── TrainingRepository.java
├── service
│   └── TrainingManager.java
└── ui
    └── MainView.java
```

<table>
  <tr>
    <th>Package</th>
    <th>Responsabilidad</th>
  </tr>
  <tr>
    <td><code>app</code></td>
    <td>Contiene el punto de entrada principal de la aplicación JavaFX.</td>
  </tr>
  <tr>
    <td><code>model</code></td>
    <td>Define las clases que representan los datos del dominio.</td>
  </tr>
  <tr>
    <td><code>service</code></td>
    <td>Contiene la lógica de gestión de sesiones y cálculo de estadísticas.</td>
  </tr>
  <tr>
    <td><code>repository</code></td>
    <td>Gestiona la lectura y escritura de entrenamientos en archivo CSV.</td>
  </tr>
  <tr>
    <td><code>ui</code></td>
    <td>Construye la interfaz gráfica con JavaFX.</td>
  </tr>
</table>

---

## Cómo ejecutar el proyecto

Clonar el repositorio:

```bash
git clone https://github.com/alejandroquiles/fight-training-tracker.git
```

Entrar en la carpeta del proyecto:

```bash
cd fight-training-tracker
```

Ejecutar con Maven:

```bash
mvn javafx:run
```

También puede abrirse desde Eclipse como proyecto Maven y ejecutarse usando:

```txt
Run As → Maven build → javafx:run
```

---

## Persistencia local

La aplicación guarda los entrenamientos en un archivo local llamado:

```txt
training_sessions.csv
```

Este archivo está ignorado por Git para evitar subir datos personales al repositorio.

---

## Estado actual

Versión **V1 funcional**.

Actualmente la aplicación permite:

```txt
- Registrar entrenamientos
- Validar datos de entrada
- Mostrar estadísticas
- Ver historial visual
- Filtrar sesiones por tipo
- Eliminar entrenamientos
- Guardar y cargar datos en CSV
```

---


## Autor

**Alejandro Quiles Rodríguez**

Estudiante de Desarrollo de Aplicaciones Multiplataforma (DAM), trabajando en proyectos personales con Java, Git, GitHub y desarrollo de aplicaciones de escritorio.

GitHub: [@alejandroquiles](https://github.com/alejandroquiles)