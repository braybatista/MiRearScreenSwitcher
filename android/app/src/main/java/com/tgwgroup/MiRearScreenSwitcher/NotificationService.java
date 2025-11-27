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

import io.flutter.BuildConfig;
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
                    Log.d(TAG, "[controlReceiver onReceive FIND_AND_SHOW_MEDIA_NOTIFICATION BABZ] Received request to find and show media notification");
                    findAndShowCurrentMediaNotification();
                    break;
                case "com.tgwgroup.MiRearScreenSwitcher.RESTORE_REAR_STATE":
                    Log.d(TAG, "[controlReceiver onReceive RESTORE_REAR_STATE BABZ] Received request to restore rear state");
                    restoreRearScreenLauncher();
                    break;
                case "com.tgwgroup.MiRearScreenSwitcher.MUSIC_SERVICE_ENABLED":
                    Log.d(TAG, "🎵 [controlReceiver onReceive MUSIC_SERVICE_ENABLED BABZ] Music service enabled - showing persistent widget");
                    loadNotificationServiceSettings();
                    if (musicServiceEnabled) {
                        findAndShowCurrentMediaNotification();
                    }
                    break;
                case "com.tgwgroup.MiRearScreenSwitcher.MUSIC_SERVICE_DISABLED":
                    Log.d(TAG, "🚫 [controlReceiver onReceive MUSIC_SERVICE_DISABLED BABZ] Music service disabled - hiding widget");
                    loadNotificationServiceSettings();
                    hideMusicWidget();
                    break;
                case "com.tgwgroup.MiRearScreenSwitcher.PAUSE_AND_PLAY_MEDIA":
                    Log.d(TAG, "[controlReceiver onReceive PAUSE_AND_PLAY_MEDIA BABZ] Received request to pause and play media");
                    loadNotificationServiceSettings();
                    if (musicServiceEnabled) {
                        findAndShowCurrentMediaNotification();
                    }
                    break;
            }
        }
    };

    // 广播接收器：监听设置重新加载
    private BroadcastReceiver settingsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.tgwgroup.MiRearScreenSwitcher.RELOAD_NOTIFICATION_SETTINGS".equals(intent.getAction())) {
                Log.d(TAG, "[settingsReceiver onReceive BABZ] 🔄 收到重新加载设置的广播");
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
            Log.d(TAG, "� 开始读取FlutterSharedPreferences...");
            // 从FlutterSharedPreferences读取开关状态
            SharedPreferences flutterPrefs = getSharedPreferences("FlutterSharedPreferences", MODE_PRIVATE);
            Log.d(TAG, "� FlutterSharedPreferences读取成功");

            serviceEnabled = flutterPrefs.getBoolean("flutter.notification_service_enabled", false);
            Log.d(TAG, "� 通知服务开关状态已恢复: " + serviceEnabled);

            musicServiceEnabled = flutterPrefs.getBoolean("flutter.notification_music_service_enabled", false);
            Log.d(TAG, "🔧 通知服务开关状态已恢复: " + musicServiceEnabled);

            // NotificationListenerService由系统管理，不能手动停止
            // 如果开关关闭，服务仍会运行但不处理通知
            if (!serviceEnabled) {
                Log.d(TAG, "⏸️ 通知服务已禁用，将忽略所有通知");
            } else {
                Log.d(TAG, "✅ 通知服务已启用，将处理通知");
            }
        } catch (Exception e) {
            Log.e(TAG, "✗ 加载通知服务设置失败", e);
            serviceEnabled = false; // 默认关闭
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
            // 注意：不在这里重新设置 serviceEnabled，保持 loadNotificationServiceSettings() 的值

            Log.d(TAG, "⚙️ 已加载设置");
            Log.d(TAG, "   - 启用状态: " + serviceEnabled + " (由loadNotificationServiceSettings设置)");
            Log.d(TAG, "   - 选中应用: " + selectedApps.size() + " 个");
            Log.d(TAG, "   - 隐藏标题: " + privacyHideTitle);
            Log.d(TAG, "   - 隐藏内容: " + privacyHideContent);

            if (!selectedApps.isEmpty()) {
                Log.d(TAG, "� 选中应用列表: " + selectedApps.toString());
            } else {
                Log.w(TAG, "⚠️ 没有选中任何应用");
            }
        } catch (Exception e) {
            Log.e(TAG, "加载设置失败", e);
            selectedApps = new HashSet<>();
            // 不在这里重置 serviceEnabled
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "[onCreate BABZ] 🟢 NotificationService created");
        // 保存实例
        instance = this;
        // 初始化SharedPreferences
        prefs = getSharedPreferences("mrss_settings", Context.MODE_PRIVATE);

        // 注册广播接收器（监听设置变化）
        IntentFilter filter = new IntentFilter("com.tgwgroup.MiRearScreenSwitcher.RELOAD_NOTIFICATION_SETTINGS");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(settingsReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(settingsReceiver, filter);
        }

        // 注册控制广播接收器（用于音乐控制与还原背屏等）
        IntentFilter controlFilter = new IntentFilter();
        controlFilter.addAction("com.tgwgroup.MiRearScreenSwitcher.FIND_AND_SHOW_MEDIA_NOTIFICATION");
        controlFilter.addAction("com.tgwgroup.MiRearScreenSwitcher.RESTORE_REAR_STATE");
        controlFilter.addAction("com.tgwgroup.MiRearScreenSwitcher.MUSIC_SERVICE_ENABLED");
        controlFilter.addAction("com.tgwgroup.MiRearScreenSwitcher.MUSIC_SERVICE_DISABLED");
        controlFilter.addAction("com.tgwgroup.MiRearScreenSwitcher.PAUSE_AND_PLAY_MEDIA");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            // DEBUG 构建下导出以允许 adb shell 测试；Release 下保持不导出提升安全性
            if (BuildConfig.DEBUG) {
                registerReceiver(controlReceiver, controlFilter, Context.RECEIVER_EXPORTED);
                Log.d(TAG, "controlReceiver registered with RECEIVER_EXPORTED (DEBUG for adb testing)");
            } else {
                registerReceiver(controlReceiver, controlFilter, Context.RECEIVER_NOT_EXPORTED);
                Log.d(TAG, "controlReceiver registered with RECEIVER_NOT_EXPORTED (Release secure mode)");
            }
        } else {
            registerReceiver(controlReceiver, controlFilter);
            Log.d(TAG, "controlReceiver registered (pre-API33)");
        }

        Log.d(TAG, "✓ 广播接收器已注册（settings, control）");

        // 添加Shizuku监听器
        Shizuku.addBinderReceivedListenerSticky(binderReceivedListener);
        Shizuku.addBinderDeadListener(binderDeadListener);

        // 绑定TaskService
        bindTaskService();

        // V2.4: 加载通知服务开关状态
        Log.d(TAG, "� 开始加载通知服务开关状态...");
        loadNotificationServiceSettings();
        Log.d(TAG, "� 通知服务开关状态加载完成: " + serviceEnabled);

        // 启动为前台服务，防止被系统杀死
        RearScreenKeeperService service = new RearScreenKeeperService();
        startForeground(NOTIFICATION_ID, service.createServiceNotification(this));
        Log.d(TAG, "✓ 前台服务已启动");

        loadSettings();
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.d(TAG, "X NotificationListener connected");
        loadSettings();
        Log.d(TAG, "✓ 通知监听器已就绪");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "� NotificationService destroyed");

        // 注销广播接收器
        try {
            unregisterReceiver(settingsReceiver);
            unregisterReceiver(controlReceiver);
            Log.d(TAG, "✓ 广播接收器已注销");
        } catch (Exception e) {
            Log.w(TAG, "Failed to unregister receiver", e);
        }

        // 移除Shizuku监听器
        try {
            Shizuku.removeBinderReceivedListener(binderReceivedListener);
            Shizuku.removeBinderDeadListener(binderDeadListener);
        } catch (Exception e) {
            Log.w(TAG, "Failed to remove Shizuku listeners", e);
        }

        // 解绑TaskService
        try {
            if (taskService != null) {
                Shizuku.unbindUserService(serviceArgs, taskServiceConnection, true);
                taskService = null;
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed to unbind TaskService", e);
        }

        // 清除实例
        instance = null;

        stopForeground(true);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Log.d(TAG, "[onNotificationPosted BABZ] 🟢 NotificationService posted");

        // V2.4: 每次收到通知时重新加载开关状态
        loadNotificationServiceSettings();
        
        try {
            Notification notification = sbn.getNotification();
            if (notification == null) return;
            
            // Verificar si es una notificación de medios
            boolean isMediaNotification = notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION) != null;

            if (!isMediaNotification && serviceEnabled) {
                handleNotification(sbn, "posted");
            } else if (isMediaNotification && musicServiceEnabled) {
                handleMusicNotification(sbn, "posted");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error en onNotificationPosted", e);
        }
    }

    @Override
    public void onNotificationRankingUpdate(RankingMap rankingMap) {
        super.onNotificationRankingUpdate(rankingMap);
        // Este método se llama cuando las notificaciones se actualizan sin ser removidas
        // Es crucial para detectar cambios en notificaciones de medios (play/pause/etc)
        Log.d(TAG, "[onNotificationRankingUpdate BABZ] Notification ranking updated - checking for media updates");
        
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

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d(TAG, "[onNotificationRemoved BABZ] 🟢 NotificationService removed");
        Log.d(TAG, sbn.toString());
        Log.d(TAG, sbn.getPackageName());
        Log.d(TAG, sbn.getNotification().toString());
    }

    /**
     * Método centralizado para procesar notificaciones
     * @param sbn La notificación a procesar
     * @param source El origen de la llamada (para logging)
     */
    private void handleNotification(StatusBarNotification sbn, String source) {
        try {
            String packageName = sbn.getPackageName();
            Notification notification = sbn.getNotification();

            Log.d(TAG, "� 收到通知: " + packageName);

            // 忽略常驻通知
            if ((notification.flags & Notification.FLAG_ONGOING_EVENT) != 0) {
                Log.d(TAG, "⏭️ 忽略常驻通知: " + packageName);
                return;
            }

            // 忽略自己的通知
            if (packageName.equals(getPackageName())) {
                Log.d(TAG, "⏭️ 忽略自己的通知");
                return;
            }

            // 每次都重新加载设置（确保实时生效）
            loadSettings();

            // 检查服务是否启用
            if (!serviceEnabled) {
                Log.d(TAG, "⏭️ 通知服务未启用，跳过");
                return;
            }

            // 检查系统勿扰模式
            if (followDndMode) {
                try {
                    android.app.NotificationManager nm = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (nm != null && nm.getCurrentInterruptionFilter() != android.app.NotificationManager.INTERRUPTION_FILTER_ALL) {
                        Log.d(TAG, "⏭️ 系统勿扰模式已开启，跳过通知动画");
                        return;
                    }
                } catch (Exception e) {
                    Log.w(TAG, "检查勿扰模式失败: " + e.getMessage());
                }
            }

            // 检查是否仅在锁屏时通知
            if (onlyWhenLocked) {
                try {
                    android.app.KeyguardManager km = (android.app.KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                    if (km != null && !km.isKeyguardLocked()) {
                        Log.d(TAG, "⏭️ 当前未锁屏，仅锁屏通知模式已开启，跳过");
                        return;
                    }
                } catch (Exception e) {
                    Log.w(TAG, "检查锁屏状态失败: " + e.getMessage());
                }
            }

            Log.d(TAG, "� 当前选中应用数量: " + selectedApps.size());
            Log.d(TAG, "� 选中应用列表: " + selectedApps.toString());

            // 检查是否在选中列表中
            if (!selectedApps.contains(packageName)) {
                Log.d(TAG, "⏭️ 应用不在选中列表中: " + packageName);
                return;
            }

            Log.d(TAG, "✓ 应用在选中列表中: " + packageName);

            // 提取通知内容
            String title = notification.extras.getString(Notification.EXTRA_TITLE, "");
            String text = notification.extras.getString(Notification.EXTRA_TEXT, "");
            long when = notification.when;

            Log.d(TAG, "� 通知标题: " + title);
            Log.d(TAG, "� 通知内容: " + text);

            // V3.2: 隐私模式处理（区分标题和内容）
            if (privacyHideTitle) {
                Log.d(TAG, "� 隐藏通知标题");
                title = getString(R.string.privacy_mode_enabled);
            }
            if (privacyHideContent) {
                Log.d(TAG, "� 隐藏通知内容");
                text = getString(R.string.new_message_placeholder);
            }

            Log.d(TAG, "� 开始显示背屏通知: " + packageName);

            // 通知动画管理器：开始通知动画（返回被打断的旧动画）
            RearAnimationManager.AnimationType oldAnim = RearAnimationManager.startAnimation(RearAnimationManager.AnimationType.NOTIFICATION);

            // 如果有旧动画需要打断，发送打断广播
            if (oldAnim == RearAnimationManager.AnimationType.CHARGING) {
                Log.d(TAG, "� 检测到充电动画正在播放，发送打断广播");

                // V3.5: 检查充电动画是否是常亮模式
                boolean chargingAlwaysOn = prefs.getBoolean("charging_always_on_enabled", false);
                RearAnimationManager.markInterruptedChargingAsAlwaysOn(chargingAlwaysOn);

                RearAnimationManager.sendInterruptBroadcast(this, RearAnimationManager.AnimationType.CHARGING);
            } else if (oldAnim == RearAnimationManager.AnimationType.NOTIFICATION) {
                Log.d(TAG, "� 检测到通知动画正在播放，发送打断广播并重载");
                RearAnimationManager.sendInterruptBroadcast(this, RearAnimationManager.AnimationType.NOTIFICATION);

                // 延迟600ms后重新启动通知动画，确保旧动画完全停止（锁屏+投送app下需要更多时间）
                final String finalPackageName = packageName;
                final String finalTitle = title;
                final String finalText = text;
                final long finalWhen = when;
                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                    Log.d(TAG, "� 重载通知动画");
                    showNotificationOnRearScreen(finalPackageName, finalTitle, finalText, finalWhen);
                }, 600);
                return; // 提前返回，避免重复启动
            }

            Log.d(TAG, "Extracted notification info (" + source + "): " + title + " - " + text);

            // 触发背屏通知显示
            showNotificationOnRearScreen(packageName, title, text, when);

        } catch (Exception e) {
            Log.e(TAG, "❌ 处理通知时出错", e);
        }
    }

    private void handleMusicNotification(StatusBarNotification sbn, String source) {
        if (!musicServiceEnabled) return;

        try {
            String packageName = sbn.getPackageName();
            Notification notification = sbn.getNotification();

            if (packageName.equals(getPackageName())) return;

            loadSettings();

            /* android.app.KeyguardManager km = (android.app.KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (onlyWhenLocked && km != null && !km.isKeyguardLocked()) {
                if (notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION) == null) return;
            } */

            /*NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (followDndMode && nm != null && nm.getCurrentInterruptionFilter() != NotificationManager.INTERRUPTION_FILTER_ALL) {
                if (notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION) == null) return;
            }*/

            // 检查服务是否启用
            if (!musicServiceEnabled) {
                Log.d(TAG, "⏭️ 通知服务未启用，跳过");
                return;
            }

            // 检查系统勿扰模式
            if (followDndMode) {
                try {
                    android.app.NotificationManager nm = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (nm != null && nm.getCurrentInterruptionFilter() != android.app.NotificationManager.INTERRUPTION_FILTER_ALL) {
                        Log.d(TAG, "⏭️ 系统勿扰模式已开启，跳过通知动画");
                        return;
                    }
                } catch (Exception e) {
                    Log.w(TAG, "检查勿扰模式失败: " + e.getMessage());
                }
            }

            // 检查是否仅在锁屏时通知
            if (onlyWhenLocked) {
                try {
                    android.app.KeyguardManager km = (android.app.KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                    if (km != null && !km.isKeyguardLocked()) {
                        Log.d(TAG, "⏭️ 当前未锁屏，仅锁屏通知模式已开启，跳过");
                        return;
                    }
                } catch (Exception e) {
                    Log.w(TAG, "检查锁屏状态失败: " + e.getMessage());
                }
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
        // 参考ChargingService的重试机制
        if (taskService == null) {
            Log.w(TAG, "⚠️ TaskService未连接，尝试重新绑定...");
            bindTaskService();

            // 延迟500ms后重试
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                showNotificationOnRearScreenDirect(packageName, title, text, when);
            }, 500);
        } else {
            showNotificationOnRearScreenDirect(packageName, title, text, when);
        }
    }

    private void showNotificationOnRearScreenDirect(String packageName, String title, String text, long when) {
        try {
            if (taskService == null) {
                Log.e(TAG, "❌ TaskService仍然不可用，放弃显示通知");
                return;
            }

            // 短时局部保活，避免在锁屏/重负载下被挂起
            acquireWakeLock(6000);
            Log.d(TAG, "� 准备启动Activity显示通知");

            // 锁屏状态检查
            android.app.KeyguardManager km = (android.app.KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            boolean isLocked = km != null && km.isKeyguardLocked();

            // 读取主屏前台应用（用于同包名前台场景的保护）
            String mainForegroundApp = null;
            try {
                mainForegroundApp = taskService.getForegroundAppOnDisplay(0);
                Log.d(TAG, "� 主屏前台应用: " + mainForegroundApp);
            } catch (Throwable t) {
                Log.w(TAG, "获取主屏前台应用失败: " + t.getMessage());
            }

            // V3.3: 移除唤醒代码，避免锁屏时跳转到密码界面

            try {
                // 暂停监控，防止被误杀
                RearScreenKeeperService.pauseMonitoring();
            } catch (Throwable t) {
                Log.w(TAG, "pauseMonitoring failed: " + t.getMessage());
            }

            try {
                // 禁用背屏官方Launcher，避免抢占
                taskService.disableSubScreenLauncher();
            } catch (Throwable t) {
                Log.w(TAG, "disableSubScreenLauncher failed: " + t.getMessage());
            }

            // V3.3: 移除 wm dismiss-keyguard 命令，避免锁屏时跳转到密码界面

            // 2) 根据锁屏状态与前台应用选择启动策略
            String componentName = getPackageName() + "/" + RearScreenNotificationActivity.class.getName();

            // 当锁屏且主屏前台就是本条通知所属应用时，避免主屏占位策略，改为直接背屏启动，防止系统冲突
            // 精确匹配包名，避免误判（如 com.tencent.mm 和 com.tencent.mobileqq）
            boolean forceDirectRearDueToSameApp = false;
            if (isLocked && mainForegroundApp != null && !mainForegroundApp.isEmpty()) {
                // 提取主屏前台应用的包名（格式可能是 "com.example.app/com.example.app.MainActivity"）
                String foregroundPackage = mainForegroundApp;
                if (mainForegroundApp.contains("/")) {
                    foregroundPackage = mainForegroundApp.split("/")[0];
                }
                forceDirectRearDueToSameApp = foregroundPackage.equals(packageName);
                Log.d(TAG, String.format("� 锁屏同包检查: 主屏前台=[%s] vs 通知包名=[%s] -> %s", foregroundPackage, packageName, forceDirectRearDueToSameApp ? "匹配(直接背屏)" : "不匹配(占位策略)"));
            }

            // ✅ 统一策略：无论锁屏与否，都直接在背屏启动（避免DPI不匹配问题）
            // 直接在背屏启动可以确保布局使用正确的DPI（450），避免从主屏移动导致的尺寸问题

            // 确保暗夜模式设置是最新的
            notificationDarkMode = prefs.getBoolean("notification_dark_mode", false);
            Log.d(TAG, "� 当前暗夜模式设置: " + notificationDarkMode);

            String directCmd = String.format(
                    "am start --display 1 -n %s --es packageName \"%s\" --es title \"%s\" --es text \"%s\" --el when %d --ez darkMode %b",
                    componentName,
                    packageName,
                    title.replace("\"", "\\\""),
                    text.replace("\"", "\\\""),
                    when,
                    notificationDarkMode
            );

            boolean started = false;
            // 尝试3次直接启动，确保成功
            for (int retry = 0; retry < 3; retry++) {
                try {
                    taskService.executeShellCommand(directCmd);
                    Log.d(TAG, String.format("✓ %s，直接在背屏启动通知Activity (尝试%d)",
                            isLocked ? "锁屏状态" : "非锁屏状态", retry + 1));
                    try { Thread.sleep(150); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                    // 检查是否启动成功
                    String check = taskService.executeShellCommandWithResult("am stack list | grep RearScreenNotificationActivity");
                    if (check != null && !check.trim().isEmpty()) {
                        started = true;
                        Log.d(TAG, "✓ 通知动画已在背屏启动");
                        break;
                    }
                } catch (Throwable t) {
                    Log.w(TAG, String.format("尝试%d失败: %s", retry + 1, t.getMessage()));
                }
            }

            // 如果直接启动失败，使用备用策略（主屏占位+移动）
            if (!started && isLocked) {
                Log.w(TAG, "⚠️ 直接背屏启动失败，回退到主屏占位+移动策略");

                // 主屏启动（Activity 自行占位）
                String startOnMainCmd = String.format(
                        "am start -n %s --es packageName \"%s\" --es title \"%s\" --es text \"%s\" --el when %d --ez darkMode %b",
                        componentName,
                        packageName,
                        title.replace("\"", "\\\""),
                        text.replace("\"", "\\\""),
                        when,
                        notificationDarkMode
                );
                Log.d(TAG, "� 在主屏启动通知Activity（占位符）");
                taskService.executeShellCommand(startOnMainCmd);
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                // 轮询获取taskId
                String notifTaskId = null;
                int attempts = 0;
                int maxAttempts = 60;
                while (notifTaskId == null && attempts < maxAttempts) {
                    try { Thread.sleep(40); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    String result = taskService.executeShellCommandWithResult("am stack list | grep RearScreenNotificationActivity");
                    if (result != null && !result.trim().isEmpty()) {
                        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("taskId=(\\d+)");
                        java.util.regex.Matcher matcher = pattern.matcher(result);
                        if (matcher.find()) {
                            notifTaskId = matcher.group(1);
                            Log.d(TAG, "� 找到通知taskId=" + notifTaskId);
                            break;
                        }
                    }
                    attempts++;
                }

                if (notifTaskId != null) {
                    // 4) 移动到背屏
                    String moveCmd = "service call activity_task 50 i32 " + notifTaskId + " i32 1";
                    taskService.executeShellCommand(moveCmd);
                    try { Thread.sleep(60); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                    // 5) 锁屏时关闭主屏，避免主屏抢焦点
                    // 主屏休眠功能已移除
                    Log.d(TAG, "� 锁屏状态，主屏已关闭");

                    Log.d(TAG, "✓ 通知动画已移动到背屏");
                } else {
                    Log.e(TAG, "❌ 未能找到通知Activity的taskId，最后尝试直接在背屏启动");
                    try {
                        String fallbackCmd = String.format(
                                "am start --display 1 -n %s --es packageName \"%s\" --es title \"%s\" --es text \"%s\" --el when %d --ez darkMode %b",
                                componentName,
                                packageName,
                                title.replace("\"", "\\\""),
                                text.replace("\"", "\\\""),
                                when,
                                notificationDarkMode
                        );
                        taskService.executeShellCommand(fallbackCmd);
                        Log.d(TAG, "� 已尝试直接 --display 1 启动通知Activity（fallback）");
                    } catch (Throwable t) {
                        Log.w(TAG, "Fallback直接在背屏启动失败: " + t.getMessage());
                    }
                }
            }
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

        // 锁屏状态检查
        android.app.KeyguardManager km = (android.app.KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean isLocked = km != null && km.isKeyguardLocked();
        
        PowerManager.WakeLock screenWakeLock = null;
        try {
            // Encender la pantalla
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                screenWakeLock = pm.newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    "MRSS:MusicScreenWake"
                );
                screenWakeLock.acquire(3000);
                Log.d(TAG, "💡 Screen wake lock acquired");
            }
            
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            
            // Wake lock para mantener el servicio activo
            acquireWakeLock(6000);
            
            // Pausar monitoreo de RearScreenKeeperService
            try {
                RearScreenKeeperService.pauseMonitoring();
            } catch (Throwable t) {
                Log.w(TAG, "pauseMonitoring failed: " + t.getMessage());
            }
            
            // Guardar datos en caché
            MusicNotificationCache.getInstance().setData(title, artist, albumArt, isPlaying, token);
            
            // Preparar pantalla trasera
            try {
                taskService.disableSubScreenLauncher();
                taskService.executeShellCommand("input -d 1 keyevent KEYCODE_WAKEUP");
            } catch (Throwable t) {
                Log.w(TAG, "Failed to prepare rear screen: " + t.getMessage());
            }
            
            // Lanzar widget: main screen + move to display 1
            String componentName = getPackageName() + "/" + RearScreenMusicActivity.class.getName();
            taskService.executeShellCommand("am start -n " + componentName + " --ez fromCache true");
            
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            
            // Buscar taskId y mover a display 1
            String musicTaskId = findTaskId("RearScreenMusicActivity", 30);
            if (musicTaskId != null) {
                taskService.executeShellCommand("service call activity_task 50 i32 " + musicTaskId + " i32 1");
                Log.d(TAG, "✅ Music widget moved to display 1");
            } else {
                Log.e(TAG, "❌ Could not find music widget taskId");
            }
            
        } catch (Exception e) {
            Log.e(TAG, "❌ Failed to show music widget", e);
        } finally {
            if (screenWakeLock != null && screenWakeLock.isHeld()) {
                try {
                    screenWakeLock.release();
                } catch (Exception e) {
                    Log.w(TAG, "Failed to release screen wake lock", e);
                }
            }
            releaseWakeLock();
        }
    }
    
    /**
     * Busca el taskId de una actividad en el stack
     * @param activityName Nombre de la actividad a buscar
     * @param maxAttempts Número máximo de intentos
     * @return taskId si se encuentra, null en caso contrario
     */
    private String findTaskId(String activityName, int maxAttempts) {
        try {
            for (int attempt = 0; attempt < maxAttempts; attempt++) {
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                
                String stackResult = taskService.executeShellCommandWithResult("am stack list | grep " + activityName);
                if (stackResult != null && !stackResult.trim().isEmpty()) {
                    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("taskId=(\\d+)");
                    java.util.regex.Matcher matcher = pattern.matcher(stackResult);
                    if (matcher.find()) {
                        return matcher.group(1);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error finding taskId for " + activityName, e);
        }
        return null;
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
    
    /**
     * Ocultar el widget de música y restaurar el launcher
     */
    private void hideMusicWidget() {
        try {
            // Finalizar RearScreenMusicActivity si está activa
            Intent intent = new Intent("com.tgwgroup.MiRearScreenSwitcher.CLOSE_MUSIC_WIDGET");
            sendBroadcast(intent);
            
            // Restaurar launcher
            restoreRearScreenLauncher();
            
            Log.d(TAG, "✓ Music widget hidden");
        } catch (Exception e) {
            Log.e(TAG, "Failed to hide music widget", e);
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
                    Log.d(TAG, "� PARTIAL_WAKE_LOCK acquired for " + timeoutMs + "ms");
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
                Log.d(TAG, "� PARTIAL_WAKE_LOCK released");
            }
        } catch (Throwable t) {
            Log.w(TAG, "Failed to release wakelock: " + t.getMessage());
        }
    }
}