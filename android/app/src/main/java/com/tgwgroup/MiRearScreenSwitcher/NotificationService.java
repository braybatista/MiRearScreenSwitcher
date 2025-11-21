/*
 * Author: AntiOblivionis
 * QQ: 319641317
 * Github: https://github.com/GoldenglowSusie/
 * Bilibili: 罗德岛T0驭械术师澄闪
 *
 * Chief Tester: 汐木泽
 *
 * Co-developed with AI assistants:
 * - Cursor
 * - Claude-4.5-Sonnet
 * - GPT-5
 * - Gemini-2.5-Pro
 */

package com.tgwgroup.MiRearScreenSwitcher;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import com.tgwgroup.MiRearScreenSwitcher.misc.Constants;

import rikka.shizuku.Shizuku;

/**
 * 通知监听服务
 * 监听系统通知，将选中应用的通知显示到背屏
 */
public class NotificationService extends NotificationListenerService {
    private static final String TAG = "NotificationService";
    private static final int NOTIFICATION_ID = 1001; // 与其他Service共用ID

    private Set<String> selectedApps = new HashSet<>();
    private boolean privacyHideTitle = false; // V3.2: 隐私模式 - 隐藏标题
    private boolean privacyHideContent = false; // V3.2: 隐私模式 - 隐藏内容
    private boolean followDndMode = true; // 跟随系统勿扰模式（默认开启）
    private boolean onlyWhenLocked = false; // 仅在锁屏时通知（默认关闭）
    private boolean notificationDarkMode = false; // 通知暗夜模式（默认关闭）
    private boolean serviceEnabled = false; // 服务是否启用

    private boolean musicServiceEnabled = false; // 服务是否启用
    private ITaskService taskService; // 自己的TaskService实例
    private SharedPreferences prefs;
    private PowerManager.WakeLock wakeLock;

    // 静态实例，供外部访问
    private static NotificationService instance;

    public static ITaskService getTaskService() {
        return instance != null ? instance.taskService : null;
    }

    private final BroadcastReceiver controlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;

            switch (action) {
                case "com.tgwgroup.MiRearScreenSwitcher.FIND_AND_SHOW_MEDIA_NOTIFICATION":
                    Log.d(TAG, "Received request to find and show media notification");
                    findAndShowCurrentMediaNotification();
                    break;
                case "com.tgwgroup.MiRearScreenSwitcher.RESTORE_REAR_STATE":
                    Log.d(TAG, "Received request to restore rear state");
                    restoreRearScreenLauncher();
                    break;
            }
        }
    };


    // 广播接收器：监听设置重新加载
    private final BroadcastReceiver settingsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.tgwgroup.MiRearScreenSwitcher.RELOAD_NOTIFICATION_SETTINGS".equals(intent.getAction())) {
                Log.d(TAG, "🔄 收到重新加载设置的广播");
                loadNotificationServiceSettings(); // 重新加载开关状态
                loadSettings(); // 重新加载其他设置
            }
        }
    };

    // Shizuku服务配置
    private final Shizuku.UserServiceArgs serviceArgs = new Shizuku.UserServiceArgs(new ComponentName("com.tgwgroup.MiRearScreenSwitcher", TaskService.class.getName()))
            .daemon(false)
            .processNameSuffix("notification_task_service")
            .debuggable(false)
            .version(1);

    // TaskService连接
    private final ServiceConnection taskServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG, "✓ TaskService connected");
            taskService = ITaskService.Stub.asInterface(binder);
            
            // 初始化显示屏信息缓存
            try {
                DisplayInfoCache.getInstance().initialize(taskService);
            } catch (Exception e) {
                Log.w(TAG, "初始化显示屏缓存失败: " + e.getMessage());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "✗ TaskService disconnected");
            taskService = null;
            // 自动重连
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                if (taskService == null) {
                    bindTaskService();
                }
            }, 1000);
        }
    };

    // Shizuku监听器
    private final Shizuku.OnBinderReceivedListener binderReceivedListener = () -> {
        Log.d(TAG, "Shizuku binder received");
        bindTaskService();
    };

    private final Shizuku.OnBinderDeadListener binderDeadListener = () -> {
        Log.d(TAG, "Shizuku binder dead");
        taskService = null;
        // 尝试重连
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            bindTaskService();
        }, 1000);
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "🟢 NotificationService created");
        // 保存实例
        instance = this;
        // 初始化SharedPreferences
        prefs = getSharedPreferences("mrss_settings", Context.MODE_PRIVATE);

        // 注册广播接收器（监听设置变化）
        IntentFilter settingsFilter = new IntentFilter("com.tgwgroup.MiRearScreenSwitcher.RELOAD_NOTIFICATION_SETTINGS");
        registerReceiver(settingsReceiver, settingsFilter, Context.RECEIVER_NOT_EXPORTED);

        IntentFilter controlFilter = new IntentFilter();
        controlFilter.addAction("com.tgwgroup.MiRearScreenSwitcher.FIND_AND_SHOW_MEDIA_NOTIFICATION");
        controlFilter.addAction("com.tgwgroup.MiRearScreenSwitcher.RESTORE_REAR_STATE");
        registerReceiver(controlReceiver, controlFilter, Context.RECEIVER_NOT_EXPORTED);

        Log.d(TAG, "✓ 广播接收器已注册");

        Shizuku.addBinderReceivedListenerSticky(binderReceivedListener);
        Shizuku.addBinderDeadListener(binderDeadListener);
        bindTaskService();

        Log.d(TAG, "🔧 开始加载通知服务开关状态...");
        loadNotificationServiceSettings();
        Log.d(TAG, "🔧 通知服务开关状态加载完成: " + serviceEnabled);

        Log.d(TAG, "🔧 通知服务开关状态加载完成: " + musicServiceEnabled);

        startForeground(NOTIFICATION_ID, RearScreenKeeperService.createServiceNotification(this));
        Log.d(TAG, "✓ 前台服务已启动");
        loadSettings();
    }

    private void bindTaskService() {
        try {
            if (taskService != null) {
                Log.d(TAG, "TaskService already bound");
                return;
            }
            if (!Shizuku.pingBinder()) {
                Log.w(TAG, "Shizuku not available");
                return;
            }
            Log.d(TAG, "🔗 开始绑定TaskService...");
            Shizuku.bindUserService(serviceArgs, taskServiceConnection);
        } catch (Exception e) {
            Log.e(TAG, "Failed to bind TaskService", e);
        }
    }

    private void loadNotificationServiceSettings() {
        try {
            SharedPreferences flutterPrefs = getSharedPreferences("FlutterSharedPreferences", MODE_PRIVATE);
            serviceEnabled = flutterPrefs.getBoolean("flutter.notification_service_enabled", false);
            musicServiceEnabled = flutterPrefs.getBoolean("flutter.notification_music_service_enabled", false);
            Log.d(TAG, "🔧 通知服务开关状态已恢复: " + serviceEnabled);
            Log.d(TAG, "🔧 通知服务开关状态已恢复: " + musicServiceEnabled);
        } catch (Exception e) {
            Log.e(TAG, "✗ 加载通知服务设置失败", e);
            serviceEnabled = false;
            musicServiceEnabled = false;
        }
    }

    private void loadSettings() {
        try {
            selectedApps = prefs.getStringSet("notification_selected_apps", new HashSet<>());
            privacyHideTitle = prefs.getBoolean("notification_privacy_hide_title", false);
            privacyHideContent = prefs.getBoolean("notification_privacy_hide_content", false);
            followDndMode = prefs.getBoolean("notification_follow_dnd_mode", true);
            onlyWhenLocked = prefs.getBoolean("notification_only_when_locked", false);
            notificationDarkMode = prefs.getBoolean("notification_dark_mode", false);
            Log.d(TAG, "⚙️ 已加载设置");
        } catch (Exception e) {
            Log.e(TAG, "加载设置失败", e);
            selectedApps = new HashSet<>();
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        loadNotificationServiceSettings();
        
        try {
            Notification notification = sbn.getNotification();
            if (notification == null) return;
            
            // Verificar si es una notificación de medios
            boolean isMediaNotification = notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION) != null;
            
            if (isMediaNotification && musicServiceEnabled) {
                handleMusicNotification(sbn, "posted");
            } else if (!isMediaNotification && serviceEnabled) {
                handleNotification(sbn, "posted");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error en onNotificationPosted", e);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d(TAG, "Notification removed from: " + sbn.getPackageName());
        // Cuando se remueve una notificación de medios, podríamos restaurar el launcher
        // pero solo si no hay otras notificaciones de medios activas
    }

    @Override
    public void onNotificationRankingUpdate(RankingMap rankingMap) {
        super.onNotificationRankingUpdate(rankingMap);
        // Este método se llama cuando las notificaciones se actualizan sin ser removidas
        // Es crucial para detectar cambios en notificaciones de medios (play/pause/etc)
        Log.d(TAG, "Notification ranking updated - checking for media updates");
        
        loadNotificationServiceSettings();
        if (!musicServiceEnabled) return;
        
        // Buscar notificaciones de medios activas en el ranking actual
        StatusBarNotification[] activeNotifications = getActiveNotifications();
        if (activeNotifications != null) {
            for (StatusBarNotification sbn : activeNotifications) {
                Notification notification = sbn.getNotification();
                if (notification != null && notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION) != null) {
                    // Encontramos una notificación de medios, procesarla
                    Log.d(TAG, "Media notification update detected from: " + sbn.getPackageName());

                    //handleNotification(sbn, "ranking_update");
                    handleMusicNotification(sbn, "ranking_update");
                    break; // Solo procesar la primera notificación de medios encontrada
                }
            }
        }
    }

    /**
     * Método centralizado para procesar notificaciones
     * @param sbn La notificación a procesar
     * @param source El origen de la llamada (para logging)
     */
    private void handleNotification(StatusBarNotification sbn, String source) {
        loadNotificationServiceSettings();
        if (!serviceEnabled) return;

        try {
            String packageName = sbn.getPackageName();
            Notification notification = sbn.getNotification();

            if (packageName.equals(getPackageName())) return;

            loadSettings();

            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (followDndMode && nm != null && nm.getCurrentInterruptionFilter() != NotificationManager.INTERRUPTION_FILTER_ALL) {
                if (notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION) == null) return;
            }

            android.app.KeyguardManager km = (android.app.KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (onlyWhenLocked && km != null && !km.isKeyguardLocked()) {
                if (notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION) == null) return;
            }

            if (!selectedApps.contains(packageName)) return;

            // Extraer título y texto de manera segura, manejando SpannableString
            CharSequence titleSeq = notification.extras.getCharSequence(Notification.EXTRA_TITLE);
            CharSequence textSeq = notification.extras.getCharSequence(Notification.EXTRA_TEXT);
            String title = titleSeq != null ? titleSeq.toString() : "";
            String text = textSeq != null ? textSeq.toString() : "";
            
            if (privacyHideTitle) title = Constants.NOTIFICATION_SERVICE_PRIVACY_MODE_ENABLED;
            if (privacyHideContent) text = Constants.NOTIFICATION_SERVICE_NEW_MESSAGE;

            Log.d(TAG, "Extracted notification info (" + source + "): " + titleSeq + " - " + textSeq);
            Log.d(TAG, "Extracted notification info (" + source + "): " + title + " - " + text);

            showNotificationOnRearScreen(packageName, title, text, notification.when);

        } catch (Exception e) {
            Log.e(TAG, "❌ 处理通知时出错 (" + source + ")", e);
        }
    }

    private void handleMusicNotification(StatusBarNotification sbn, String source) {
        loadNotificationServiceSettings();
        if (!musicServiceEnabled) return;

        try {
            String packageName = sbn.getPackageName();
            Notification notification = sbn.getNotification();

            if (packageName.equals(getPackageName())) return;

            loadSettings();

            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (followDndMode && nm != null && nm.getCurrentInterruptionFilter() != NotificationManager.INTERRUPTION_FILTER_ALL) {
                if (notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION) == null) return;
            }

            android.app.KeyguardManager km = (android.app.KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (onlyWhenLocked && km != null && !km.isKeyguardLocked()) {
                if (notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION) == null) return;
            }

            Log.d(TAG, "Media notification from " + source + ": " + sbn.getPackageName());
            Bundle extras = notification.extras;
            
            // Extraer título y artista de manera segura, manejando SpannableString
            CharSequence titleSeq = extras.getCharSequence(Notification.EXTRA_TITLE);
            CharSequence artistSeq = extras.getCharSequence(Notification.EXTRA_TEXT);
            String title = titleSeq != null ? titleSeq.toString() : "";
            String artist = artistSeq != null ? artistSeq.toString() : "";
            
            Bitmap albumArt = getBitmapFromNotification(extras);
            MediaSession.Token token = extras.getParcelable(Notification.EXTRA_MEDIA_SESSION);
            boolean isPlaying = false;
            if (token != null) {
                MediaController mc = new MediaController(this, token);
                PlaybackState playbackState = mc.getPlaybackState();
                if (playbackState != null) {
                    isPlaying = playbackState.getState() == PlaybackState.STATE_PLAYING;
                }
            }
            Log.d(TAG, "Extracted media info (" + source + "): " + title + " - " + artist + " | isPlaying: " + isPlaying);
            showMusicOnRearScreen(title, artist, albumArt, isPlaying, token);
        } catch (Exception e) {
            Log.e(TAG, "❌ 处理通知时出错 (" + source + ")", e);
        }
    }

    private Bitmap getBitmapFromNotification(Bundle extras) {
        Object largeIconObj = extras.get(Notification.EXTRA_LARGE_ICON);
        if (largeIconObj instanceof Bitmap) {
            return (Bitmap) largeIconObj;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && largeIconObj instanceof Icon) {
            Icon icon = (Icon) largeIconObj;
            Drawable drawable = icon.loadDrawable(this);
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else {
                int width = drawable.getIntrinsicWidth();
                int height = drawable.getIntrinsicHeight();
                if (width > 0 && height > 0) {
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);
                    return bitmap;
                }
            }
        }
        return null;
    }

    private void findAndShowCurrentMediaNotification() {
        StatusBarNotification[] activeNotifications = getActiveNotifications();
        if (activeNotifications == null) {
            Log.d(TAG, "No active notifications found.");
            return;
        }

        for (StatusBarNotification sbn : activeNotifications) {
            Notification notification = sbn.getNotification();
            if (notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION) != null) {
                Log.d(TAG, "Found active media notification from: " + sbn.getPackageName());

                Bundle extras = notification.extras;
                
                // Extraer título y artista de manera segura, manejando SpannableString
                CharSequence titleSeq = extras.getCharSequence(Notification.EXTRA_TITLE);
                CharSequence artistSeq = extras.getCharSequence(Notification.EXTRA_TEXT);
                String title = titleSeq != null ? titleSeq.toString() : "";
                String artist = artistSeq != null ? artistSeq.toString() : "";
                
                Bitmap albumArt = getBitmapFromNotification(extras);
                MediaSession.Token token = extras.getParcelable(Notification.EXTRA_MEDIA_SESSION);

                boolean isPlaying = false;
                if (token != null) {
                    MediaController mc = new MediaController(this, token);
                    PlaybackState playbackState = mc.getPlaybackState();
                    if (playbackState != null) {
                        isPlaying = playbackState.getState() == PlaybackState.STATE_PLAYING;
                    }
                }
                Log.d(TAG, "Extracted media info: " + title + " - " + artist + " | isPlaying: " + isPlaying);
                showMusicOnRearScreen(title, artist, albumArt, isPlaying, token);
                return; 
            }
        }
        Log.d(TAG, "No active media notification found.");
    }

    private void showNotificationOnRearScreen(String packageName, String title, String text, long when) {
        if (taskService == null) {
            bindTaskService();
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> showNotificationOnRearScreenDirect(packageName, title, text, when), 500);
        } else {
            showNotificationOnRearScreenDirect(packageName, title, text, when);
        }
    }

    private void showNotificationOnRearScreenDirect(String packageName, String title, String text, long when) {
        try {
            if (taskService == null) return;
            acquireWakeLock(6000);
            taskService.disableSubScreenLauncher();

            String componentName = getPackageName() + "/" + RearScreenNotificationActivity.class.getName();
            String directCmd = String.format(
                "am start --display 1 -n %s --es packageName \"%s\" --es title \"%s\" --es text \"%s\" --el when %d --ez darkMode %b",
                componentName, packageName, title.replace("\"", "\\\""), text.replace("\"", "\\\""), when, notificationDarkMode
            );

            taskService.executeShellCommand(directCmd);
        } catch (Exception e) {
            Log.e(TAG, "❌ 显示背屏通知失败", e);
        } finally {
            releaseWakeLock();
        }
    }

    private void showMusicOnRearScreen(String title, String artist, Bitmap albumArt, boolean isPlaying, MediaSession.Token token) {
        if (taskService == null) {
            Log.e(TAG, "TaskService is not available. Cannot show music widget.");
            return;
        }
        
        try {
            // 1. Guardar los datos en el caché (no se pueden pasar por shell command)
            MusicNotificationCache.getInstance().setData(title, artist, albumArt, isPlaying, token);
            
            // 2. Deshabilitar el launcher predeterminado y despertar la pantalla
            taskService.disableSubScreenLauncher();
            taskService.executeShellCommand("input -d 1 keyevent KEYCODE_WAKEUP");
            
            // 3. Iniciar la actividad directamente en la pantalla trasera (display 1)
            String componentName = getPackageName() + "/" + RearScreenMusicActivity.class.getName();
            String directCmd = String.format(
                "am start --display 1 -n %s --ez fromCache true",
                componentName
            );
            
            taskService.executeShellCommand(directCmd);
            Log.d(TAG, "Music widget launched directly on rear screen");
            
        } catch (Exception e) {
            Log.e(TAG, "Failed to show music widget on rear screen", e);
        }
    }

    private void restoreRearScreenLauncher() {
        if (taskService != null) {
            try {
                taskService.enableSubScreenLauncher();
                Log.d(TAG, "Rear screen launcher restored.");
            } catch (Exception e) {
                Log.e(TAG, "Failed to restore rear screen launcher", e);
            }
        }
    }

    private void acquireWakeLock(long timeoutMs) {
        try {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                if (wakeLock == null) {
                    wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MRSS:NotificationWake");
                    wakeLock.setReferenceCounted(false);
                }
                if (!wakeLock.isHeld()) {
                    wakeLock.acquire(timeoutMs);
                }
            }
        } catch (Throwable t) {
            Log.w(TAG, "Failed to acquire wakelock: " + t.getMessage());
        }
    }

    private void releaseWakeLock() {
        try {
            if (wakeLock != null && wakeLock.isHeld()) {
                wakeLock.release();
            }
        } catch (Throwable t) {
            Log.w(TAG, "Failed to release wakelock: " + t.getMessage());
        }
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.d(TAG, "🔗 NotificationListener connected");
        loadSettings();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "🔴 NotificationService destroyed");
        try {
            unregisterReceiver(settingsReceiver);
            unregisterReceiver(controlReceiver);
        } catch (Exception e) {
            Log.w(TAG, "Failed to unregister receiver", e);
        }
        try {
            Shizuku.removeBinderReceivedListener(binderReceivedListener);
            Shizuku.removeBinderDeadListener(binderDeadListener);
        } catch (Exception e) {
            Log.w(TAG, "Failed to remove Shizuku listeners", e);
        }
        try {
            if (taskService != null) {
                Shizuku.unbindUserService(serviceArgs, taskServiceConnection, true);
                taskService = null;
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed to unbind TaskService", e);
        }
        instance = null;
        stopForeground(true);
    }
}
