# MiRearScreenSwitcher (MRSS)

Un cambiador de pantalla con un solo clic para dispositivos de doble pantalla como el Xiaomi 17 Pro/17 Pro Max.

## 📄 Licencia de código abierto

**⚠️ Aviso importante sobre el cambio de licencia**

- **Versiones V3.0.0 y posteriores**: Adoptadas bajo la licencia de código abierto **GPL-3.0**.
- **Versiones anteriores a la V3.0.0**: Aún protegidas bajo la licencia **MIT**.

Este cambio busca proteger las innovaciones principales del proyecto, manteniendo el espíritu de código abierto. Todas las versiones anteriores a la V3.0.0 seguirán siendo de uso libre bajo la licencia MIT.

**💬 Comunicación y soporte**
- Grupo QQ: **932738927** - [Únete al chat grupal](https://tgwgroup.ltd/2025/10/21/%e5%85%b3%e4%ba%8emrss%e4%ba%a4%e6%b5%81%e7%be%a4/)
- Dona para apoyar: [Invita al autor a un café](https://tgwgroup.ltd/2025/10/19/%e5%85%b3%e4%ba%8e%e6%89%93%e8%b5%8f/) ☕

---

## ✨ Funcionalidades (V3.1.2)

- 🎯 **Cambio rápido**: Cambia de aplicación a la pantalla anterior con un solo clic mediante el cambio rápido en el centro de control.
- 📸 **Captura de pantalla trasera**: Captura la pantalla trasera con un solo clic y guárdala en tu álbum de fotos (el centro de control se minimiza automáticamente; se activa con un código de tecla).
- 📹 **Grabación de pantalla trasera**: Control de ventana flotante; graba la pantalla trasera y la guarda en la carpeta Películas (activación continua con un código de tecla).
- ⚡ **Animación de carga**: Muestra una hermosa animación de carga en la pantalla trasera al conectar el dispositivo (efectos de rayo 3D y líquido).
- 📢 **Notificaciones push**: Muestra automáticamente el contenido de las notificaciones en la pantalla trasera al recibir una notificación (compatible con selección de aplicaciones, modo privado y recarga dinámica).
- 🔓 **Disponibilidad en segundo plano**: Permite cambiar de aplicación con normalidad incluso cuando la aplicación está en segundo plano.
- 🚀 **No requiere acceso root**: Basado en Shizuku, no requiere permisos de root.
- 🎨 **Interfaz de usuario refinada**: Diseño Material 3, interfaz de usuario con degradado de cuatro colores, pantalla de 2.84 píxeles. Esquinas redondeadas elípticas
- 🛡️ **Protección inteligente**: Evita que el lanzador del sistema cubra las aplicaciones proyectadas.- 💡 **Pantalla trasera siempre activa**: Función opcional de pantalla trasera siempre activa para evitar el apagado automático de la pantalla.
- 🔄 **Monitoreo inteligente**: Borra automáticamente las notificaciones al salir o cambiar de aplicación en la pantalla trasera.
- 📱 **Ajuste de PPP**: Permite el ajuste dinámico de la PPP de la pantalla trasera para optimizar la visualización (se recomienda entre 260 y 350).
- 🤚 **Detección de oclusión de la pantalla trasera**: Función opcional de detección mediante sensor de proximidad.
- 🔄 **Rotación de la pantalla trasera**: Admite rotación de la pantalla trasera a 0°/90°/180°/270°; las aplicaciones se reactivan automáticamente.
- 🚫 **Ocultar tareas**: La aplicación principal no se muestra en la lista de tareas recientes para evitar que se borre accidentalmente.- 🌐 **Llamada URI**: Permite controlar MRSS desde aplicaciones externas (Tasker/MacroDroid, etc.) mediante el protocolo mrss://.
- 🔔 **Notificaciones inteligentes**: Permite seguir el modo No molestar del sistema y notificar solo cuando la pantalla está bloqueada.
- 🎬 **Integración con la biblioteca multimedia**: Las capturas de pantalla y las grabaciones se actualizan automáticamente en el álbum para una fácil visualización.

## 📋 Requisitos previos

1. **Requisitos del dispositivo**: Teléfonos Xiaomi compatibles con pantalla trasera (Xiaomi 17 Pro/17 Pro Max y otros dispositivos de doble pantalla).
2. **Shizuku**: Es necesario instalar y ejecutar Shizuku.
    - Dirección de descarga: [Sitio web oficial de Shizuku](https://shizuku.rikka.app/)
    - Método de inicio: ADB o depuración inalámbrica

## 🚀 Cómo usar

### 1. Configuración inicial

1. Instala la aplicación MRSS.
2. Asegúrate de que Shizuku esté abierto.
3. Abre la aplicación MRSS y otorga permisos a Shizuku.
4. Espera a que aparezca el mensaje "Todo listo".

### 2. Agregar accesos directos

1. Desliza el dedo hacia abajo desde la parte superior de la pantalla para abrir el **Centro de control**.
2. Pulsa el botón **Editar**.
3. Busca los siguientes accesos directos y agrégalos:
    - **Cambiar a pantalla trasera**: Muestra la aplicación actual en la pantalla trasera.
    - **Capturar pantalla trasera**: Captura y guarda la pantalla de fondo.
    - **Grabar pantalla trasera**: Graba la pantalla de fondo (opcional).
4. ¡Listo!

### 3. Uso diario

**Cambiar aplicaciones a la pantalla trasera:**
1. Abre la aplicación que quieras usar en la pantalla trasera.
2. Desliza hacia abajo el Centro de control.
3. Pulsa el acceso directo "**Cambiar a la pantalla trasera**".
4. La aplicación cambiará inmediatamente a la pantalla trasera y el Centro de control se minimizará automáticamente.
5. Gira el teléfono para ver la pantalla trasera.

**Capturar pantalla trasera:**
1. Desliza hacia abajo el Centro de control.
2. Pulsa el acceso directo "**Capturar pantalla trasera**".
3. El Centro de control se minimizará automáticamente y la captura de pantalla se guardará en tu álbum de fotos.

**Grabar pantalla trasera:**
1. Desliza hacia abajo el Centro de control.
2. Pulsa el acceso directo "**Grabar pantalla trasera**".
3. Aparecerá una ventana flotante; pulsa el botón circular rojo para empezar a grabar.
4. El botón se convertirá en un cuadrado durante la grabación; púlsalo de nuevo para detenerla.
5. El vídeo se guarda en Películas/MRSS_*.mp4

**Volver a la pantalla de inicio:**
- Método 1: Pulsa la notificación «Pulsa para volver a la pantalla de inicio».
- Método 2: Al salir de la aplicación en la pantalla de retroceso, las notificaciones desaparecen automáticamente.

**Animación de carga y notificaciones:**
- Animación de carga: Muestra automáticamente un icono de rayo al conectar el dispositivo. Se puede desactivar en la app.
- Notificaciones push: Se activan en la app. Selecciona las apps para recibir notificaciones. Compatible con el modo privado y el modo No molestar. El tiempo de eliminación automática es personalizable (sin límite).

**Ajuste de la pantalla trasera:**

- Ajuste de PPP: Se configura en la app. Se recomienda entre 260 y 350.
- Control de rotación: Admite rotación de 0°/90°/180°/270°.
- Pantalla trasera siempre activa: Se puede activar o desactivar en la app.
- Detección de oclusión de la pantalla trasera: Se puede activar o desactivar en la app (según el sensor de proximidad).

**💡 Consejo:**
- Aunque la app MRSS esté en segundo plano o cerrada, ¡los ajustes rápidos seguirán funcionando con normalidad! - MRSS no aparecerá en la lista de tareas recientes para evitar que se borre accidentalmente.
- La función de pantalla de fondo siempre activa se puede activar o desactivar dentro de la aplicación.
- Las notificaciones se borran automáticamente al salir de la aplicación o al cambiar de aplicación.
- Las animaciones de carga y las notificaciones push se pueden activar o desactivar de forma independiente.
- Admite llamadas mediante URI (mrss://switch?current=1, etc.).

## 🔧 Implementación técnica

- **Flutter**: Framework de interfaz de usuario multiplataforma, diseño Material 3, degradado de cuatro colores y esquinas redondeadas superelípticas precisas.
- **Shizuku**: Proporciona permisos de shell para operaciones privilegiadas.
- **Tarjeta de ajustes rápidos**: Servicio de alternancia rápida a nivel de sistema Android (alternar/capturar pantalla/grabar pantalla).
- **ActivityTaskManager**: Alternancia de visualización implementada mediante llamadas a servicios del sistema.
- **Servicio en primer plano + WakeLock**: El servicio en primer plano mantiene el bloqueo de activación, con opción de pantalla trasera siempre activa.
- **Servicio de escucha de notificaciones**: Escucha las notificaciones del sistema y las envía a la pantalla trasera en tiempo real.
- **Activación por código de tecla**: Utiliza el evento de entrada de tecla KEYCODE_WAKEUP para activar la pantalla trasera con precisión. **Escáner multimedia**: Actualiza automáticamente la biblioteca multimedia; las capturas de pantalla y las grabaciones aparecen automáticamente en el álbum de fotos.
- **Recarga dinámica de animaciones**: Mecanismo de recarga dinámica para las animaciones de notificaciones, compatible con notificaciones continuas.
- **Administrador de animaciones de la pantalla trasera**: Administración unificada de las animaciones de carga y notificaciones, con opción de interrupción de animaciones.
- **Monitoreo inteligente**: Detecta las aplicaciones en primer plano en la pantalla trasera cada 2 segundos y elimina automáticamente las notificaciones no válidas.
- **Detector de carga**: El receptor de difusión escucha los eventos de carga y activa las animaciones de la pantalla trasera. - **Animación 3D:** Dibujo personalizado en Canvas, animación no lineal, efectos de líquidos con detección de gravedad.
- **Captura de pantalla y grabación:** Captura de pantalla + grabación de pantalla.
- **Protocolo URI:** Admite llamadas externas mediante el protocolo mrss://.

## 📝 Descripción de permisos

- `moe.shizuku.manager.permission.API_V23`: Permiso de la API de Shizuku, utilizado para realizar operaciones con privilegios.
- `android.permission.WAKE_LOCK`: Mantiene la pantalla trasera siempre encendida.
- `android.permission.FOREGROUND_SERVICE`: Permiso para servicio en primer plano
- `android.permission.POST_NOTIFICATIONS`: Permiso de notificaciones (Android 13+)
- `android.permission.BIND_NOTIFICATION_LISTENER_SERVICE`: Servicio de escucha de notificaciones (opcional)
- `android.permission.SYSTEM_ALERT_WINDOW`: Permiso para ventana flotante (necesario para grabar la pantalla)
- `android.permission.QUERY_ALL_PACKAGES`: Obtener la lista de aplicaciones (necesario para la función de notificaciones)
- Recepción de difusión del sistema: Escuchar eventos de carga `ACTION_POWER_CONNECTED/DISCONNECTED`

## 🛠️ Compilación de desarrollo

```bash

# Instalar dependencias
flutter pub get

# Generar APK de depuración
flutter build apk --debug

# Generar APK de lanzamiento (arm64-v8a, (Code (Ofuscación + compresión de recursos)

flutter build apk --release --split-per-abi --target-platform android-arm64

```

El APK generado se encuentra en: `build/app/outputs/flutter-apk/app-arm64-v8a-release.apk`

## 🔍 Detalles técnicos

### Funcionalidades principales de la versión 3.0

1. **Cambio inteligente de aplicaciones** 🎯
    - Se activa mediante el acceso directo en la configuración rápida.
    - Utiliza el comando `am stack` para obtener la aplicación en primer plano.
    - Llama a `service call activity_task 50` para cambiar de pantalla.
    - Cierra automáticamente el lanzador del sistema para evitar que tome el control.
    - Muestra una notificación emergente con el nombre de la aplicación.

2. **Animación de carga** ⚡
    - Muestra automáticamente una animación de carga en la pantalla trasera al conectar el dispositivo.
    - Recipiente de cristal 3D con efecto de rayo y líquido verde.
    - Efecto de líquido realista con sensor de gravedad.
    - Animación no lineal. Desde el 0% hasta el nivel actual de batería
    - Los iconos de la interfaz usan el símbolo del rayo ⚡, que se adapta mejor al tema de carga

- 3. **Notificaciones push** 📢
    - NotificationListenerService escucha las notificaciones del sistema
    - Selecciona las apps a las que enviar notificaciones (admite búsqueda y selección por lotes)
    - El contenido de la notificación se muestra en la pantalla de fondo (icono de la app + título + contenido)
    - Modo privado: Oculta el contenido de la notificación
    - Animación atractiva: Escalado de iconos +Aparición gradual del contenido
    - Tiempo de autodestrucción personalizable (ilimitado, admite cualquier duración)
    - El estado de activación/desactivación de la notificación persiste y se restaura correctamente tras reiniciar el sistema.

4. **Grabación de la pantalla trasera** 📹
    - Control mediante ventana flotante, botón rojo circular/cuadrado
    - Graba la pantalla trasera con screenrecord
    - Mantiene la pantalla trasera activa para evitar interrupciones en la grabación
    - Guarda el vídeo en Movies/MRSS_*.mp4
    - Posición de la ventana flotante ajustable mediante arrastre

5. **Mantenimiento activo del servicio en primer plano** 🛡️
    - Notificaciones unificadas en primer plano del "servicio del kernel MRSS"
    - Función opcional de pantalla trasera siempre activa (FLAG_KEEP_SCREEN_ON)
    - Al pulsar la notificación, se regresa rápidamente a la pantalla de inicio.
    - La aplicación principal se oculta en las tareas recientes para evitar cierres accidentales.

6. **Supervisión y limpieza inteligentes** 🔄
    - Comprueba el estado de las aplicaciones en primer plano en la pantalla trasera cada 2 segundos. - Detiene automáticamente el servicio y borra las notificaciones al salir o cambiar de aplicación.
    - Evita que se muestren varias aplicaciones simultáneamente.

7. **Ajuste dinámico de PPP** 📱
    - Permite ajustar en tiempo real la densidad de píxeles de la pantalla trasera.
    - Se modifica mediante el comando `wm density`.
    - Restaura la configuración predeterminada con un solo clic.
    - Rango recomendado: 260-350

8. **Control de rotación de la pantalla trasera** 🔄
    - Admite 4 direcciones: 0°/90°/180°/270°
    - Controla la pantalla trasera de forma independiente mediante `wm user-rotation -d 1`.
    - Las aplicaciones se reanudan automáticamente tras la rotación.
    - Muestra en tiempo real el estado de rotación actual.

9. **Detección de oclusión de la pantalla trasera** 🤚
    - Función opcional de detección mediante sensor de proximidad.
    - Se puede activar o desactivar desde la aplicación.

10. **Compatibilidad con el protocolo URI** 🌐
    - mrss://switch?current=1 - Cambiar la aplicación actual
    - mrss://switch?packageName=xxx - Cambiar a una aplicación específica
    - mrss://return?current=1 - Volver a la pantalla de inicio
    - mrss://screenshot - Tomar una captura de pantalla
    - mrss://config?dpi=xxx&rotation=x - Configurar la rotación; El valor de rotación debe ser de 0 a 3, igual que en la shell de adb.

### Novedades de la versión 3.0
    - ✅ **Animación de carga**: Contenedor de relámpagos 3D + líquido con sensor de gravedad
    - ✅ **Notificaciones push**: Notificaciones del sistema mostradas en la pantalla trasera en tiempo real
    - ✅ **Grabación de la pantalla trasera**: Función de grabación controlada por una ventana flotante
    - ✅ **Llamada URI**: Compatible con el control de aplicaciones externas (Tasker, etc.)
    - ✅ **Interfaz de usuario atractiva**: Degradado de cuatro colores + diseño de esquinas redondeadas superelípticas
    - ✅ **Optimización de código**: Se eliminó código innecesario y se corrigieron problemas de caracteres ilegibles

### Novedades de la versión 3.1.2
    - ✅ **Selección de aplicaciones optimizada**: Las aplicaciones seleccionadas se muestran automáticamente en la parte superior de la lista para una gestión más sencilla.
    - ✅ **Lógica de cierre optimizada**: La estrategia de cierre del lanzador se ha optimizado para cerrarse solo una vez antes de que comience la animación, evitando operaciones frecuentes.

### Novedades V3.1.1
    - ✅ **Optimización del mantenimiento activo del servicio**: Se añadió la función de mantenimiento activo del servicio en primer plano ChargingService para evitar que el sistema lo finalice.
    - ✅ **Gestión unificada de notificaciones**: Todos los servicios utilizan una notificación de servicio de kernel unificada, lo que evita la acumulación de múltiples notificaciones.
    - ✅ **Corrección de configuración**: Se corrigió un problema de conflicto en la configuración de NotificationService, lo que garantiza la coherencia del estado.

### Novedades V3.1.0
    - ✅ **Icono de animación de carga optimizado**: Se cambió el icono de la interfaz de usuario de la animación de carga de una bombilla a un rayo, para que combine mejor con el tema de carga.
    - ✅ **Corrección del estado del interruptor de notificaciones**: Se corrigió un problema de persistencia del estado del interruptor de notificaciones en la pantalla de fondo; el estado se restaura correctamente después de reiniciar.
    - ✅ **Sincronización del estado del servicio de notificaciones**: Se corrigió un problema de sincronización del estado del interruptor del servicio de notificaciones; ahora funciona correctamente después de apagarlo y volverlo a encender.
    - ✅ **Tiempo de destrucción automática ilimitado:** Se eliminó el límite de 60 segundos para la destrucción automática de notificaciones, permitiendo duraciones personalizables.

### Optimizaciones de rendimiento
    - ✅ Ofuscación de código (ProGuard/R8)
    - ✅ Compresión de recursos
    - ✅ Solo compatible con la arquitectura arm64-v8a
    - ✅ Optimización del tamaño del APK

## 📄 Licencia

**Versión 3.0.0 y posteriores:** Licencia GPL-3.0. Consulte el archivo [LICENSE](LICENSE) para obtener más información.
**Versiones anteriores a la V3.0.0:** Licencia MIT

---

## 📝 Registro de cambios

### V3.1.2 (2025)

#### Optimización de funciones
    - En la lista de selección de aplicaciones, las aplicaciones seleccionadas se muestran automáticamente en la parte superior, mientras que las no seleccionadas se muestran en la parte inferior.
    - Se optimizó la lógica de finalización del lanzador para las animaciones de carga y notificación; ahora, el lanzador solo se finaliza una vez antes de que comience la animación.

### V3.1.1 (2025)

#### Corrección de errores
    - Se corrigió un problema por el cual ChargingService carecía de un servicio en primer plano que mantuviera activo, lo que provocaba un fallo de funcionamiento.
    - Se corrigió un problema por el cual un conflicto en la configuración de NotificationService causaba estados inconsistentes.
    - Se unificó el uso de notificaciones de servicio del kernel para todos los servicios.

Evitar la acumulación en la barra de notificaciones.

### Optimizaciones técnicas
    - ChargingService ahora utiliza un mecanismo unificado de mantenimiento de notificaciones del servicio del kernel.
    - Se optimizó la lógica de carga de la configuración de NotificationService para evitar conflictos de estado.
    - Se mejoró la estabilidad del servicio, reduciendo la probabilidad de que el sistema lo finalice.

### V3.1.0 (2025)

#### Nuevas funciones
    - Se cambió el icono de la interfaz de usuario de la animación de carga de una bombilla a un rayo.
    - Se eliminó el límite de 60 segundos para la eliminación automática de notificaciones.

#### Corrección de errores
    - Se corrigió el problema del estado persistente del interruptor de notificaciones en la pantalla de fondo; ahora el estado se restaura correctamente después de reiniciar.
    - Se corrigió el problema de la sincronización del estado del interruptor del servicio de notificaciones; ahora funciona correctamente después de apagarlo y volverlo a encender.
    - Se corrigió el problema de que el servicio de notificaciones recargara el estado del interruptor cada vez que se recibía una notificación.

### V3.0.0 (2025)

#### Actualizaciones importantes
    - Licencia de código abierto GPL-3.0
    - Nueva animación de carga: contenedor de rayo 3D + efecto líquido con sensor de gravedad
    - Notificaciones push: las notificaciones del sistema se muestran en la pantalla trasera en tiempo real
    - Grabación de pantalla trasera: la grabación se controla mediante una ventana flotante
    - Compatibilidad con el protocolo URI: admite el control de aplicaciones externas (Tasker, etc.)
    - Interfaz de usuario atractiva: degradado de cuatro colores + diseño de esquinas redondeadas superelípticas

## 👥 Equipo

### Autor
**AntiOblivionis**
    - 🎮 QQ: 319641317
    - 📱 Coolapk: [@AntiOblivionis](http://www.coolapk.com/u/8158212)
    - 🐙 Github: [GoldenglowSusie](https://github.com/GoldenglowSusie/)
    - 📺 Bilibili: [Rhodes Island T0 Mechanic Chengshan](https://space.bilibili.com/407059627)

### Jefe de Pruebas

**Ximuze**
    - 📱 Coolapk: [@Ximuze](http://www.coolapk.com/u/4279097)
    - Proporcionó comentarios clave sobre las pruebas y sugerencias de funciones.

## 🤖 Desarrollo Colaborativo con IA

Este proyecto fue desarrollado por el autor en colaboración con los siguientes asistentes de IA:
    - Cursor
    - Claude-4.5-Sonnet
    - GPT-5
    - Gemini-2.5-Pro

## 🙏 Agradecimientos

- [Shizuku](https://github.com/RikkaApps/Shizuku) - Proporciona soporte para API privilegiadas.
- Equipo de Flutter - Excelente framework multiplataforma.
- Equipo de Xiaomi HyperOS - Pantalla trasera para teléfonos Xiaomi. Funcionalidad

---

## 📜 Aviso de derechos de autor

### Propiedad de los iconos

Los iconos y logotipos de marca utilizados en esta aplicación son propiedad de los siguientes:

1. **Icono de la aplicación**: Este icono utiliza directamente recursos del sistema Xiaomi HyperOS. De acuerdo con el [Acuerdo de usuario del sistema operativo Xiaomi](https://terms.miui.com/doc/eula/cn.html), los derechos de autor de las marcas comerciales e iconos relacionados, como Xiaomi, MIUI y Xiaomi HyperOS, pertenecen a Xiaomi Technology Co., Ltd. Esta aplicación es solo una herramienta auxiliar desarrollada por terceros y no está relacionada oficialmente con Xiaomi. Si detecta alguna infracción, póngase en contacto con nosotros para que la eliminemos.

2. **Icono de Coolapk**: El icono de Coolapk utilizado en la aplicación pertenece a Coolapk (Beijing Coolapk Network Technology Co., Ltd.). De acuerdo con el [Acuerdo de Usuario de Coolapk](https://m.coolapk.com/mp/user/agreement), Coolapk posee todos los derechos de propiedad intelectual de sus marcas registradas, iconos, etc. Esta aplicación utiliza el icono de Coolapk únicamente para la identificación del enlace y no implica ninguna asociación oficial con Coolapk.

### Descargo de responsabilidad

Esta aplicación es un proyecto de código abierto, basado en Shizuku para extender la funcionalidad de la pantalla de fondo, y tiene fines exclusivamente educativos y de comunicación. Al usar esta aplicación, usted comprende y acepta lo siguiente:
    - Esta aplicación no es una aplicación oficial de Xiaomi y no tiene ninguna afiliación con Xiaomi Corporation.
    - Los usuarios asumen todos los riesgos asociados con el uso de esta aplicación.
    - El desarrollador no se hace responsable de las pérdidas ocasionadas por el uso de esta aplicación.
    - Si se produce alguna infracción, póngase en contacto con nosotros para su eliminación.

---
