
package com.tgwgroup.MiRearScreenSwitcher.misc;

public class Constants {

    // MainActivity
    public static final String COOLAPK_NOT_INSTALLED = Utils.getLocalizedString("Please install CoolApk first", "Por favor, instale CoolApk primero", "请先安装酷安应用");
    public static final String FAILED_TO_OPEN_TUTORIAL = Utils.getLocalizedString("Failed to open tutorial: %s", "Fallo al abrir el tutorial: %s", "打开失败: %s");
    public static final String FAILED_TO_OPEN_DONATION_PAGE = Utils.getLocalizedString("Failed to open donation page: %s", "Fallo al abrir la página de donaciones: %s", "打开失败: %s");
    public static final String FAILED_TO_OPEN_QQ_GROUP_PAGE = Utils.getLocalizedString("Failed to open QQ group page: %s", "Fallo al abrir la página del grupo de QQ: %s", "打开失败: %s");
    public static final String TASK_SERVICE_NOT_AVAILABLE = Utils.getLocalizedString("TaskService not available", "TaskService no disponible", "TaskService not available");

    // ScreenRecordService
    public static final String SCREEN_RECORD_SERVICE_FAILED_TO_SHOW_FLOATING_WINDOW = Utils.getLocalizedString("Failed to show floating window: %s", "Error al mostrar la ventana flotante: %s", "显示悬浮窗失败: %s");
    public static final String SCREEN_RECORD_SERVICE_PLEASE_STOP_RECORDING_FIRST = Utils.getLocalizedString("Please stop recording first", "Por favor, detenga la grabación primero", "请先停止录制");
    public static final String SCREEN_RECORD_SERVICE_NOT_READY = Utils.getLocalizedString("Service not ready, please try again later", "Servicio no listo, por favor intente de nuevo más tarde", "服务未就绪，请稍后重试");
    public static final String SCREEN_RECORD_SERVICE_NOT_SUPPORTED = Utils.getLocalizedString("System does not support screenrecord command", "El sistema no soporta el comando screenrecord", "系统不支持screenrecord命令");
    public static final String SCREEN_RECORD_SERVICE_FAILED_TO_START = Utils.getLocalizedString("Failed to start screen recording", "Error al iniciar la grabación de pantalla", "启动录屏失败");
    public static final String SCREEN_RECORD_SERVICE_PROCESS_FAILED_TO_START = Utils.getLocalizedString("Screen recording process failed to start", "El proceso de grabación de pantalla no se pudo iniciar", "录屏进程启动失败");
    public static final String SCREEN_RECORD_SERVICE_STARTED = Utils.getLocalizedString("Started recording rear screen", "Iniciada la grabación de la pantalla trasera", "开始录制背屏");
    public static final String SCREEN_RECORD_SERVICE_FAILED = Utils.getLocalizedString("Screen recording failed: %s", "Error en la grabación de pantalla: %s", "录屏失败: %s");
    public static final String SCREEN_RECORD_SERVICE_CANNOT_STOP = Utils.getLocalizedString("Service not ready, cannot stop recording", "Servicio no listo, no se puede detener la grabación", "服务未就绪，无法停止录制");
    public static final String SCREEN_RECORD_SERVICE_SAVED = Utils.getLocalizedString("Screen recording saved to Movies folder", "Grabación de pantalla guardada en la carpeta Movies", "录屏已保存到Movies文件夹");
    public static final String SCREEN_RECORD_SERVICE_MAY_HAVE_FAILED = Utils.getLocalizedString("Screen recording may have failed, please check the Movies folder", "La grabación de pantalla puede haber fallado, por favor revise la carpeta Movies", "录屏可能失败，请检查Movies文件夹");

    // SwitchToRearTileService
    public static final String SWITCH_TO_REAR_TILE_SERVICE_NOT_READY = Utils.getLocalizedString("Service not ready", "Servicio no listo", "服务未就绪");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_GRANT_PERMISSIONS = Utils.getLocalizedString("Please open the app and grant permissions first", "Por favor, abra la aplicación y otorgue los permisos primero", "请先打开应用授权");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_SWITCHING = Utils.getLocalizedString("Switching...", "Cambiando...", "切换中...");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_SWITCH_BACK_FIRST = Utils.getLocalizedString("Please switch %s back to the main screen first", "Por favor, cambie %s de nuevo a la pantalla principal primero", "请先将 %s 切换回主屏");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_REAR_SCREEN_OCCUPIED = Utils.getLocalizedString("✗ Rear screen occupied", "✗ Pantalla trasera ocupada", "✗ 背屏已占用");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_MOVED_TO_REAR_SCREEN = Utils.getLocalizedString("%s has been moved to the rear screen", "%s ha sido movido a la pantalla trasera", "%s 已投放到背屏");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_SWITCHED = Utils.getLocalizedString("✓ Switched", "✓ Cambiado", "✓ 已切换");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_FAILED = Utils.getLocalizedString("Switch failed", "Cambio fallido", "切换失败");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_FAILED_GENERIC = Utils.getLocalizedString("✗ Failed", "✗ Falló", "✗ 失败");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_APP_NOT_FOUND = Utils.getLocalizedString("✗ App not found", "✗ Aplicación no encontrada", "✗ 未找到应用");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_OPERATION_FAILED = Utils.getLocalizedString("✗ Operation failed", "✗ Operación fallida", "✗ 操作失败");

    // UriCommandService
    public static final String URI_COMMAND_SERVICE_MOVED_TO_REAR_SCREEN = Utils.getLocalizedString("%s moved to rear screen", "%s movido a la pantalla trasera", "%s 已投放到背屏");
    public static final String URI_COMMAND_SERVICE_SWITCH_FAILED = Utils.getLocalizedString("Switch failed", "Cambio fallido", "切换失败");
    public static final String URI_COMMAND_SERVICE_SWITCH_FAILED_WITH_REASON = Utils.getLocalizedString("Switch failed: %s", "Cambio fallido: %s", "切换失败: %s");
    public static final String URI_COMMAND_SERVICE_RETURNED_TO_MAIN_SCREEN = Utils.getLocalizedString("%s returned to main screen", "%s devuelto a la pantalla principal", "%s 已返回主屏");
    public static final String URI_COMMAND_SERVICE_APP_NOT_ON_REAR_SCREEN = Utils.getLocalizedString("App not on rear screen", "La aplicación no está en la pantalla trasera", "应用不在背屏");
    public static final String URI_COMMAND_SERVICE_APP_TO_RETURN_NOT_FOUND = Utils.getLocalizedString("App to return not found", "No se encontró la aplicación para devolver", "未找到要拉回的应用");
    public static final String URI_COMMAND_SERVICE_REAR_SCREEN_SCREENSHOT_SAVED = Utils.getLocalizedString("Rear screen screenshot saved", "Captura de pantalla trasera guardada", "背屏截图已保存");

    // RearScreenshotTileService
    public static final String REAR_SCREENSHOT_TILE_SERVICE_NOT_READY = Utils.getLocalizedString("✗ Service not ready", "✗ Servicio no listo", "✗ 服务未就绪");
    public static final String REAR_SCREENSHOT_TILE_SERVICE_SAVED = Utils.getLocalizedString("✓ Saved", "✓ Guardado", "✓ 已保存");

    // RearScreenKeeperService
    public static final String REAR_SCREEN_KEEPER_SERVICE_RETURNED_TO_MAIN_SCREEN = Utils.getLocalizedString("%s returned to main screen", "%s devuelto a la pantalla principal", "%s 已返回主屏");

    // RearScreenRecordTileService
    public static final String REAR_SCREEN_RECORD_TILE_SERVICE_GRANT_OVERLAY_PERMISSION = Utils.getLocalizedString("Please grant overlay permission first", "Por favor, conceda el permiso de superposición primero", "请先授予悬浮窗权限");
}
