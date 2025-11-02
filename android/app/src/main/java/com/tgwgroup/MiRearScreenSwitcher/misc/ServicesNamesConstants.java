
package com.tgwgroup.MiRearScreenSwitcher.misc;

public class ServicesNamesConstants {

    public static final String REAR_SCREEN_KEEPER_SERVICE_NOTIFICATION_APPLICATION = Utils.getLocalizedString("Application", "APP", "应用");
    public static final String REAR_SCREEN_KEEPER_SERVICE_NOTIFICATION_CHANNEL_NAME = Utils.getLocalizedString("MRSS Core Service", "Servicio Principal de MRSS", "MRSS内核服务");
    public static final String REAR_SCREEN_KEEPER_SERVICE_NOTIFICATION_CONTENT_TEXT = Utils.getLocalizedString("MRSS is currently running", "MRSS se está ejecutando actualmente", "MRSS目前正在运行");
    public static final String REAR_SCREEN_KEEPER_SERVICE_NOTIFICATION_TITLE = Utils.getLocalizedString("%s is running on the rear screen", "%s se está ejecutando en la pantalla trasera", "%s 正在背屏运行");
    public static final String REAR_SCREEN_KEEPER_SERVICE_NOTIFICATION_TEXT = Utils.getLocalizedString("Tap to switch %s back to the main screen", "Toque para devolver %s a la pantalla principal", "点击将 %s 切换回主屏");

    // SwitchToRearTileService
    public static final String SWITCH_TO_REAR_TILE_SERVICE_TILE_SUBTITLE_SWITCHING = Utils.getLocalizedString("Switching...", "Cambiando...", "切换中...");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_TILE_SUBTITLE_REAR_SCREEN_OCCUPIED = Utils.getLocalizedString("✗ Rear screen occupied", "✗ Pantalla trasera ocupada", "✗ 背屏已占用");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_TILE_SUBTITLE_SWITCHED = Utils.getLocalizedString("✓ Switched", "✓ Cambiado", "✓ 已切换");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_TILE_SUBTITLE_FAILED = Utils.getLocalizedString("✗ Failed", "✗ Falló", "✗ 失败");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_TILE_SUBTITLE_APP_NOT_FOUND = Utils.getLocalizedString("✗ App not found", "✗ Aplicación no encontrada", "✗ 未找到应用");
    public static final String SWITCH_TO_REAR_TILE_SERVICE_TILE_SUBTITLE_OPERATION_FAILED = Utils.getLocalizedString("✗ Operation failed", "✗ Operación fallida", "✗ 操作失败");

    // RearScreenshotTileService
    public static final String REAR_SCREENSHOT_TILE_SERVICE_TILE_SUBTITLE_SAVED = Utils.getLocalizedString("✓ Saved", "✓ Guardado", "✓ 已保存");

    // AlwaysWakeUpService
    public static final String ALWAYS_WAKE_UP_SERVICE_NOTIFICATION_CHANNEL_NAME = Utils.getLocalizedString("MRSS Core Service", "Servicio Principal de MRSS", "MRSS内核服务");
    public static final String ALWAYS_WAKE_UP_SERVICE_NOTIFICATION_CHANNEL_DESCRIPTION = Utils.getLocalizedString("MRSS is currently running", "MRSS se está ejecutando actualmente", "MRSS目前正在运行");
    public static final String ALWAYS_WAKE_UP_SERVICE_NOTIFICATION_TITLE = Utils.getLocalizedString("MRSS Core Service", "Servicio Principal de MRSS", "MRSS内核服务");
    public static final String ALWAYS_WAKE_UP_SERVICE_NOTIFICATION_TEXT = Utils.getLocalizedString("MRSS is currently running", "MRSS se está ejecutando actualmente", "MRSS目前正在运行");
}
