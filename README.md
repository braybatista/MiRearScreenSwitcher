## 🌍 Select your language | 选择语言 | Selecciona tu idioma

<!-- - [English](docs/en/README.md)
- [中文](docs/zh/README.md)
- [Español](docs/es/README.md) -->

- <details>
    <summary>English</summary>

    # MiRearScreenSwitcher (MRSS)

    A one-click screen switcher for dual-screen devices like the Xiaomi 17 Pro/17 Pro Max.

    ## 📄 Open Source License

    **⚠️ Important License Change Notice**

    - **Versions V3.0.0 and later**: Adopted under the **GPL-3.0** open source license.
    - **Versions prior to V3.0.0**: Still protected under the **MIT** license.

    This change aims to protect the project's core innovations while maintaining the open source spirit. All versions prior to V3.0.0 will continue to be freely used under the MIT license.

    **💬 Communication and Support**
    - QQ Group: **932738927** - [Join Group Chat](https://tgwgroup.ltd/2025/10/21/%e5%85%b3%e4%ba%8emrss%e4%ba%a4%e6%b5%81%e7%be%a4/)
    - Donate to Support: [Treat the author to a coffee](https://tgwgroup.ltd/2025/10/19/%e5%85%b3%e4%ba%8e%e6%89%93%e8%b5%8f/) ☕

    ---

    ## ✨ Features (V3.1.3)

    - 🎯 **Quick Switching**: Switch applications to the back screen with one click via the quick switch in the control center.
    - 📸 **Back Screen Screenshot**: Capture the back screen with one click and save it to your photo album (automatically collapses the control center, keycode wake-up)
    - 📹 **Back Screen Recording**: Floating window control, records the back screen and saves it to the Movies folder (continuous keycode wake-up)
    - ⚡ **Charging Animation**: Displays a beautiful charging animation on the back screen when plugged in (3D lightning + flowing liquid effects)
    - 📢 **Notification Push**: Automatically displays notification content on the back screen when a notification is received (supports app selection + privacy mode + dynamic reload)
    - 🔓 **Background Availability**: Allows normal app switching even when the app is in the background
    - 🚀 **No ROOT Required**: Based on Shizuku, no ROOT permission required
    - 🎨 **Refined UI**: Material 3 design, four-color gradient UI, precise 2.84 super elliptical rounded corners
    - 🛡️ **Smart Protection**: Prevents the system launcher from covering projected apps
    - 💡 **Always-On Back Screen**: Optional always-on backscreen function to prevent automatic screen off
    - 🔄 **Smart Monitoring**: Automatically clears notifications when apps on the backscreen are exited or switched.
    - 📱 **DPI Adjustment**: Supports dynamic adjustment of the backscreen DPI to optimize display (260-350 recommended).
    - 🤚 **Backscreen Occlusion Detection**: Optional proximity sensor detection function.
    - 🔄 **Backscreen Rotation**: Supports 0°/90°/180°/270° backscreen rotation, apps automatically revive.
    - 🚫 **Task Hiding**: The main app is not displayed in the recent tasks list to prevent accidental clearing.
    - 🌐 **URI Call**: Supports controlling MRSS from external apps (Tasker/MacroDroid, etc.) via the mrss:// protocol.
    - 🔔 **Smart Notifications**: Supports following the system Do Not Disturb mode and notifying only when the screen is locked.
    - 🎬 **Media Library Integration**: Screenshots and recordings automatically refresh to the album for easy viewing.

    ## 📋 Prerequisites

    1. **Device Requirements**: Xiaomi phones that support rear screens (Xiaomi 17 Pro/17 Pro Max and other dual-screen devices)
    2. **Shizuku**: Shizuku needs to be installed and launched.
    - Download Address: [Shizuku Official Website](https://shizuku.rikka.app/)
    - Launch Method: ADB or wireless debugging

    ## 🚀 How to Use

    ### 1. Initial Setup

    1. Install the MRSS app.
    2. Ensure Shizuku is launched.
    3. Open the MRSS app and grant Shizuku permissions.
    4. Wait for the status to display "Everything is ready".

    ### 2. Add Quick Switches

    1. Pull down from the top of the screen to open the **Control Center**.
    2. Tap the **Edit button**.
    3. Find the following quick switches and add them:
    - **Switch to Rear Screen**: Render the current application onto the rear screen.
    - **Get Rear Screen Screenshot**: Capture and save the background screen.
    - **Back Screen Recording**: Record the background screen (optional)
    4. Done!

    ### 3. Daily Use

    **Switch Apps to Back Screen:**
    1. Open any app you want to switch to the back screen.
    2. Pull down the Control Center.
    3. Tap the "**Switch to Back Screen**" shortcut switch.
    4. The app immediately switches to the back screen, and the Control Center automatically collapses.
    5. Flip your phone to view it on the back screen.

    **Capture Back Screen:**
    1. Pull down the Control Center.
    2. Tap the "**Capture Back Screen Screenshot**" shortcut switch.
    3. The Control Center automatically collapses, and the screenshot is saved to your photo album.

    **Record Back Screen:**
    1. Pull down the Control Center.
    2. Tap the "**Record Back Screen**" shortcut switch.
    3. A floating window appears; tap the red circular button to start recording.
    4. The button changes to a square during recording; tap it again to stop recording.
    5. The video is saved to Movies/MRSS_*.mp4

    **Return to Home Screen:**
    - Method 1: Tap the notification "Tap to switch apps back to home screen".
    - Method 2: When exiting the app on the back screen, notifications disappear automatically.

    **Charging Animation and Notifications:**
    - Charging Animation: Automatically displays a lightning bolt icon charging animation when plugged in. Can be turned off within the app.
    - Push Notifications: Enabled within the app. Select the apps to receive notifications. Supports following in privacy mode and Do Not Disturb mode. Automatic deletion time is customizable (unlimited).

    **Adjust Back Screen Display:**
    - DPI Adjustment: Set within the app. Recommended 260-350.
    - Rotation Control: Supports 0°/90°/180°/270° rotation.
    - Always-On Back Screen: Can be toggled on/off within the app.
    - Back Screen Occlusion Detection: Can be toggled on/off within the app (based on proximity sensor).

    **💡 Tip:**
    - Even if the MRSS app is in the background or closed, the quick settings still work normally!
    - MRSS will not appear in the recent tasks list to avoid accidental clearing.
    - Always-on backscreen functionality can be toggled within the app.
    - Notifications are automatically cleared when the app is exited or switched.
    - Charging animations and push notifications can be independently toggled.
    - Supports calls via URI (mrss://switch?current=1, etc.)

    ## 🔧 Technical Implementation

    - **Flutter**: Cross-platform UI framework, Material 3 design, four-color gradient + precise super-elliptical rounded corners
    - **Shizuku**: Provides shell permissions for privileged operations.
    - **Quick Settings Tile**: Android system-level quick toggle service (toggle/screenshot/screen recording).
    - **ActivityTaskManager**: Display toggle implemented via system service calls.
    - **Foreground Service + WakeLock**: Foreground service holds a wake-up lock, optional always-on backscreen.
    - **NotificationListenerService**: System notification listener, pushes notifications to the backscreen in real time.
    - **Keycode Wakeup**: Uses input keyevent KEYCODE_WAKEUP to precisely wake up the backscreen. **Media Scanner:** Automatically refreshes the media library; screenshots and recordings automatically appear in the photo album.
    - **Dynamic Animation Reload:** Dynamic reload mechanism for notification animations, supporting continuous notifications.
    - **Rear Animation Manager:** Unified management of charging and notification animations, enabling animation interruption.
    - **Smart Monitoring:** Detects foreground applications on the back screen every 2 seconds and automatically clears invalid notifications.
    - **Charging Listener:** BroadcastReceiver listens for charging events and triggers back screen animations.
    - **3D Animation:** Custom Canvas drawing, non-linear animation, gravity-sensing liquid effects.
    - **Screenshot & Record:** Screencap screenshot + screenrecord screen recording.
    - **URI Protocol:** Supports external calls via mrss:// protocol.

    ## 📝 Permission Description

    - `moe.shizuku.manager.permission.API_V23`: Shizuku API permission, used to perform privileged operations.
    - `android.permission.WAKE_LOCK`: Keeps the back screen always on.
    - `android.permission.FOREGROUND_SERVICE`: Foreground service permission
    - `android.permission.POST_NOTIFICATIONS`: Notification permission (Android 13+)
    - `android.permission.BIND_NOTIFICATION_LISTENER_SERVICE`: Notification listener service (optional)
    - `android.permission.SYSTEM_ALERT_WINDOW`: Floating window permission (required for screen recording)
    - `android.permission.QUERY_ALL_PACKAGES`: Get application list (required for notification function)
    - System broadcast reception: Listen for `ACTION_POWER_CONNECTED/DISCONNECTED` charging events

    ## 🛠️ Development Build

    ```bash

    # Install dependencies
    flutter pub get

    # Build Debug APK
    flutter build apk --debug

    # Build Release APK (arm64-v8a, (Code obfuscation + resource compression)
    flutter build apk --release --split-per-abi --target-platform android-arm64
    ```

    The generated APK is located at: `build/app/outputs/flutter-apk/app-arm64-v8a-release.apk`

    ## 🔍 Technical Details

    ### V3.0 Core Features

    1. **Smart App Switching** 🎯
    - Triggered via Quick Settings Tile shortcut switch
    - Uses the `am stack` command to get the foreground app
    - Calls `service call activity_task 50` to switch displays
    - Automatically kills the system launcher to prevent it from taking over
    - Toast notification displays the specific app name

    2. **Charging Animation** ⚡
    - Automatically displays a charging animation on the back screen when plugged in
    - 3D glass lightning container + flowing green liquid
    - Gravity-sensing realistic liquid effect
    - Non-linear animation, from 0% to current battery level
    - UI icons use lightning bolt ⚡ symbols, better matching the charging theme

    - 3. **Push Notifications** 📢
    - NotificationListenerService listens for system notifications
    - Select the apps to push notifications to (supports search and batch selection)
    - Notification content is displayed on the back screen (app icon + title + content)
    - Privacy mode: Hides the specific notification content
    - Beautiful animation: Icon scaling + content fade-in
    - Customizable auto-destruction time (unlimited, supports any duration)
    - Notification on/off state is persistent and correctly restored after restart

    4. **Back Screen Recording** 📹
    - Floating window control, red circular/square button
    - Record back screen using screenrecord
    - Continuously wakes the back screen to prevent recording interruption
    - Saves video to Movies/MRSS_*.mp4
    - Draggable floating window position

    5. **Foreground Service Keep-Alive** 🛡️
    - Unified "MRSS kernel service" foreground notifications
    - Optional back screen always-on function (FLAG_KEEP_SCREEN_ON)
    - Clicking the notification quickly returns you to the home screen.
    - The main app is hidden in recent tasks to prevent accidental cleanup.

    6. **Smart Monitoring and Cleanup** 🔄
    - Checks the status of foreground apps on the back screen every 2 seconds.
    - Automatically stops service and clears notifications when an app exits or switches.
    - Prevents multiple apps from being displayed simultaneously.

    7. **Dynamic DPI Adjustment** 📱
    - Supports real-time adjustment of back screen display density.
    - Modifies using the `wm density` command.
    - One-click restore to default settings.
    - Recommended range: 260-350

    8. **Back Screen Rotation Control** 🔄
    - Supports 4 directions: 0°/90°/180°/270°
    - Independently controls the back screen using `wm user-rotation -d 1`.
    - Apps automatically revive after rotation.
    - Real-time display of current rotation status.

    9. **Back Screen Occlusion Detection** 🤚
    - Optional proximity sensor detection function.
    - Can be toggled within the app.

    10. **URI Protocol Support** 🌐
    - mrss://switch?current=1 - Switch the current application
    - mrss://switch?packageName=xxx - Switch to a specified application
    - mrss://return?current=1 - Return to the home screen
    - mrss://screenshot - Take a screenshot
    - mrss://config?dpi=xxx&rotation=x - Configure the rotation; the rotation value should be 0-3, the same as adb shell

    ### V3.1.3 New Features
    - ✅ **Multi-language Support**: Fully supports Simplified Chinese, Traditional Chinese, and English, automatically following the system language.
    - ✅ **UI Detail Optimizations**: Fixed UI display issues in multi-language environments, optimized text wrapping and alignment.
    - ✅ **Notification Localization**: Notification titles and content support multi-language display.

    ### V3.1.2 New Features
    - ✅ **Optimized Application Selection**: Selected applications are automatically displayed at the top of the list for easier management.
    - ✅ **Optimized Kill Logic**: The launcher kill strategy has been optimized to kill only once before the animation starts, avoiding frequent operations.

    ### V3.1.1 New Features
    - ✅ **Service Keep-Alive Optimization**: Added foreground service keep-alive to ChargingService to prevent it from being killed by the system
    - ✅ **Unified Notification Management:** All services use a unified kernel service notification, avoiding the accumulation of multiple notifications.
    - ✅ **Settings Fix:** Fixed a NotificationService settings conflict issue, ensuring state consistency.

    ### V3.1.0 New Features
    - ✅ **Optimized Charging Animation Icon:** Changed the charging animation UI icon from a light bulb to a lightning bolt icon, better matching the charging theme.
    - ✅ **Notification Switch Status Fix:** Fixed a back-screen notification switch status persistence issue; the status is correctly restored after a restart.
    - ✅ **Notification Service Status Synchronization:** Fixed a notification service switch status synchronization issue; it works correctly after being turned off and then on again.
    - ✅ **Unlimited Automatic Destruction Time:** Removed the 60-second limit on automatic notification destruction time, supporting customizable durations.

    ### V3.0 New Features
    - ✅ **Charging Animation**: 3D lightning container + gravity-sensing liquid
    - ✅ **Notification Push**: System notifications displayed on the back screen in real time
    - ✅ **Back Screen Recording**: Floating window controls recording function
    - ✅ **URI Call**: Supports external application control (Tasker, etc.)
    - ✅ **Beautiful UI**: Four-color gradient + super elliptical rounded corner design
    - ✅ **Code Optimization**: Removed unused code and fixed garbled character issues

    ### Performance Optimizations
    - ✅ Code Obfuscation (ProGuard/R8)
    - ✅ Resource Compression
    - ✅ Includes only arm64-v8a architecture
    - ✅ APK Size Optimization

    ## 📄 License

    **V3.0.0 and later versions:** GPL-3.0 License - See the [LICENSE](LICENSE) file for details
    **Versions prior to V3.0.0**: MIT License

    ---

    ## 📝 Changelog

    ### V3.1.3 (2025)

    #### New Features
    - Full multilingual support (Simplified/Traditional/English)
    - Fully localized notification content and Toast messages

    #### Optimizations
    - Fixed UI text overflow issue in English mode
    - Optimized text display in multilingual environments

    ### V3.1.2 (2025)

    #### Feature Optimization
    - In the application selection list, selected applications are automatically displayed at the top, while unselected applications are displayed at the bottom.
    - Optimized the launcher kill logic for charging and notification animations, now only killing the launcher once before the animation starts.

    ### V3.1.1 (2025)

    #### Bug Fixes
    - Fixed an issue where ChargingService lacked a foreground service keep-alive, causing functional failure.
    - Fixed an issue where NotificationService settings conflict caused inconsistent states.
    - Unified the use of kernel service notifications for all services, avoiding notification bar accumulation.

    #### Technical Optimizations
    - ChargingService now uses a unified kernel service notification keep-alive mechanism.
    - Optimized NotificationService settings loading logic to avoid state conflicts.
    - Improved service stability, reducing the probability of being killed by the system.

    ### V3.1.0 (2025)

    #### New Features
    - Changed the charging animation UI icon from a light bulb to a lightning bolt icon.
    - Removed the 60-second limit on the automatic notification destruction time.

    #### Bug Fixes
    - Fixed the issue of persistent notification switch state on the back screen; the state is now correctly restored after a restart.
    - Fixed the issue of notification service switch state synchronization; it now works normally after being turned off and on again.
    - Fixed the issue of the notification service reloading the switch state every time a notification is received.

    ### V3.0.0 (2025)

    #### Major Updates
    - Adopted GPL-3.0 open source license
    - New charging animation: 3D lightning container + gravity-sensing liquid effect
    - Notification push function: System notifications are displayed on the back screen in real time
    - Back screen recording function: Recording is controlled by a floating window
    - URI protocol support: Supports external application control (Tasker, etc.)
    - Beautiful UI: Four-color gradient + super elliptical rounded corner design

    ## 👥 Team

    ### Author
    **AntiOblivionis**
    - 🎮 QQ: 319641317
    - 📱 Coolapk: [@AntiOblivionis](http://www.coolapk.com/u/8158212)
    - 🐙 Github: [GoldenglowSusie](https://github.com/GoldenglowSusie/)
    - 📺 Bilibili: [Rhodes Island T0 Mechanic Chengshan](https://space.bilibili.com/407059627)

    ### Chief Tester

    **Ximuze**
    - 📱 Coolapk: [@Ximuze](http://www.coolapk.com/u/4279097)
    - Provided key testing feedback and feature suggestions

    ## 🤖 AI Collaborative Development

    This project was developed by the author in collaboration with the following AI assistants:
    - Cursor
    - Claude-4.5-Sonnet
    - GPT-5
    - Gemini-2.5-Pro

    ## 🙏 Acknowledgements

    - [Shizuku](https://github.com/RikkaApps/Shizuku) - Provides privileged API support
    - Flutter Team - Excellent cross-platform framework
    - Xiaomi HyperOS Team - Xiaomi phone back screen functionality

    ---

    ## 📜 Copyright Notice

    ### Icon Ownership

    The icons and brand logos used in this application are owned as follows:

    1. **Application Icon**: This application icon directly uses icon resources from the Xiaomi HyperOS system. According to the [Xiaomi Operating System User Agreement](https://terms.miui.com/doc/eula/cn.html), the copyright of trademarks and related icons such as Xiaomi, MIUI, and Xiaomi HyperOS belongs to Xiaomi Technology Co., Ltd. This application is only a third-party developed auxiliary tool and is not related to Xiaomi officially. Please contact us to delete if there is any infringement.

    2. **Coolapk Icon**: The Coolapk icon used in the application belongs to Coolapk (Beijing Coolapk Network Technology Co., Ltd.). According to the [Coolapk User Agreement](https://m.coolapk.com/mp/user/agreement), Coolapk owns all intellectual property rights to its trademarks, icons, etc. This application uses the Coolapk icon solely for link identification and does not imply any official partnership with Coolapk.

    ### Disclaimer

    This application is an open-source project, based on Shizuku to extend backscreen functionality, and is for learning and communication purposes only. By using this application, you understand and agree that:
    - This application is not an official Xiaomi application and has no affiliation with Xiaomi Corporation.
    - Users assume all risks associated with using this application.
    - The developer is not responsible for any losses incurred due to the use of this application.
    - If there is any infringement, please contact us for removal.

    ---
</details>

- <details>
    <summary>中文</summary>

    # MiRearScreenSwitcher (MRSS)

    为小米17Pro/17Pro Max等双屏设备的背屏一键切换器

    ## 📄 开源协议

    **⚠️ 重要协议变更通知**

    - **V3.0.0及以后版本**: 采用 **GPL-3.0** 开源协议
    - **V3.0.0以前版本**: 仍受 **MIT** 协议保护

    此变更旨在保护项目的核心创新，同时保持开源精神。V3.0.0以前的所有版本将继续按照MIT协议自由使用。

    **💬 交流与支持**
    - QQ交流群：**932738927** - [加入群聊](https://tgwgroup.ltd/2025/10/21/%e5%85%b3%e4%ba%8emrss%e4%ba%a4%e6%b5%81%e7%be%a4/)
    - 打赏支持：[请作者喝杯咖啡](https://tgwgroup.ltd/2025/10/19/%e5%85%b3%e4%ba%8e%e6%89%93%e8%b5%8f/) ☕

    ---

    ## ✨ 功能特性 (V3.1.3)

    - 🎯 **快捷切换**: 通过控制中心快捷开关一键将应用切换到背屏
    - 📸 **背屏截图**: 一键截取背屏画面并保存到相册（自动收起控制中心，keycode唤醒）
    - 📹 **背屏录屏**: 悬浮窗控制，录制背屏画面并保存到Movies文件夹（持续keycode唤醒）
    - ⚡ **充电动画**: 插电时在背屏显示精美的充电动画（3D闪电+流动液体效果）
    - 📢 **通知推送**: 收到通知时自动在背屏显示通知内容（支持应用选择+隐私模式+动态重载）
    - 🔓 **后台可用**: 即使应用在后台也能正常触发切换
    - 🚀 **无需ROOT**: 基于Shizuku实现，无需ROOT权限
    - 🎨 **精致UI**: Material 3设计，四色渐变UI，精确2.84超椭圆圆角
    - 🛡️ **智能保护**: 防止系统Launcher覆盖投射的应用
    - 💡 **背屏常亮**: 可选的背屏常亮功能，防止自动息屏
    - 🔄 **智能监控**: 背屏应用退出或切换时自动清除通知
    - 📱 **DPI调整**: 支持动态调整背屏DPI，优化显示效果（推荐260-350）
    - 🤚 **背屏遮盖检测**: 可选的接近传感器检测功能
    - 🔄 **背屏旋转**: 支持背屏0°/90°/180°/270°旋转，应用自动复活
    - 🚫 **任务隐藏**: 主应用不在最近任务列表显示，防止误清理
    - 🌐 **URI调用**: 支持通过mrss://协议从外部应用控制MRSS（Tasker/MacroDroid等）
    - 🔔 **智能通知**: 支持跟随系统勿扰模式和仅在锁屏时通知
    - 🎬 **媒体库集成**: 截图和录制自动刷新到相册，方便查看

    ## 📋 使用前提

    1. **设备要求**: 支持背屏的小米手机（小米17Pro/17Pro Max等双屏设备）
    2. **Shizuku**: 需要安装并启动Shizuku
    - 下载地址: [Shizuku官网](https://shizuku.rikka.app/)
    - 启动方式: ADB或无线调试

    ## 🚀 使用方法

    ### 1. 初次设置

    1. 安装MRSS应用
    2. 确保Shizuku已启动
    3. 打开MRSS应用，授予Shizuku权限
    4. 等待状态显示"一切就绪"

    ### 2. 添加快捷开关

    1. 从屏幕顶部下拉打开**控制中心**
    2. 点击**编辑按钮**
    3. 找到以下快捷开关并添加：
    - **切换至背屏**: 将当前应用投放到背屏
    - **获取背屏截图**: 截取背屏画面并保存
    - **背屏录制**: 录制背屏画面（可选）
    4. 完成！

    ### 3. 日常使用

    **切换应用到背屏：**
    1. 打开任意想要切换到背屏的应用
    2. 下拉控制中心
    3. 点击"**切换至背屏**"快捷开关
    4. 应用立即切换到背屏，控制中心自动收起
    5. 翻转手机即可在背屏查看

    **截取背屏画面：**
    1. 下拉控制中心
    2. 点击"**获取背屏截图**"快捷开关
    3. 控制中心自动收起，截图保存到相册

    **录制背屏画面：**
    1. 下拉控制中心
    2. 点击"**背屏录制**"快捷开关
    3. 悬浮窗出现，点击红色圆形按钮开始录制
    4. 录制中按钮变为方形，再次点击停止录制
    5. 视频保存到Movies/MRSS_*.mp4

    **返回主屏：**
    - 方法1: 点击通知"点击将应用切换回主屏"
    - 方法2: 在背屏退出应用，通知自动消失

    **充电动画和通知：**
    - 充电动画: 插电时自动显示闪电图标充电动画，可在应用内关闭
    - 通知推送: 在应用内启用，选择需要推送的应用，支持隐私模式和勿扰模式跟随，自动销毁时间可自定义（无上限）

    **调整背屏显示：**
    - DPI调整: 在应用内设置，推荐260-350
    - 旋转控制: 支持0°/90°/180°/270°旋转
    - 背屏常亮: 可在应用内开关
    - 背屏遮盖检测: 可在应用内开关（基于接近传感器）

    **💡 提示**: 
    - 即使MRSS应用在后台或已关闭，快捷开关依然可以正常使用！
    - MRSS不会出现在最近任务列表，避免误清理
    - 背屏常亮功能可在应用内开关
    - 应用退出或切换时，通知会自动清除
    - 充电动画和通知推送都可独立开关
    - 支持通过URI调用（mrss://switch?current=1等）

    ## 🔧 技术实现

    - **Flutter**: 跨平台UI框架，Material 3设计，四色渐变+精确超椭圆圆角
    - **Shizuku**: 提供shell权限执行特权操作
    - **Quick Settings Tile**: Android系统级快捷开关服务（切换/截图/录屏）
    - **ActivityTaskManager**: 通过system service调用实现显示切换
    - **Foreground Service + WakeLock**: 前台服务持有唤醒锁，可选的背屏常亮
    - **NotificationListenerService**: 系统通知监听，实时推送到背屏
    - **Keycode Wakeup**: 使用input keyevent KEYCODE_WAKEUP精确唤醒背屏
    - **Media Scanner**: 自动刷新媒体库，截图和录制自动出现在相册
    - **Dynamic Animation Reload**: 通知动画动态重载机制，支持连续通知
    - **Rear Animation Manager**: 统一管理充电动画和通知动画，实现动画打断
    - **智能监控**: 每2秒检测背屏前台应用，自动清除无效通知
    - **充电监听**: BroadcastReceiver监听充电事件，触发背屏动画
    - **3D动画**: 自定义Canvas绘制，非线性动画，重力感应液体效果
    - **Screenshot & Record**: screencap截图 + screenrecord录屏
    - **URI Protocol**: 支持mrss://协议外部调用

    ## 📝 权限说明

    - `moe.shizuku.manager.permission.API_V23`: Shizuku API权限，用于执行特权操作
    - `android.permission.WAKE_LOCK`: 保持背屏常亮
    - `android.permission.FOREGROUND_SERVICE`: 前台服务权限
    - `android.permission.POST_NOTIFICATIONS`: 通知权限（Android 13+）
    - `android.permission.BIND_NOTIFICATION_LISTENER_SERVICE`: 通知监听服务（可选）
    - `android.permission.SYSTEM_ALERT_WINDOW`: 悬浮窗权限（录屏功能需要）
    - `android.permission.QUERY_ALL_PACKAGES`: 获取应用列表（通知功能需要）
    - 系统广播接收: 监听`ACTION_POWER_CONNECTED/DISCONNECTED`充电事件

    ## 🛠️ 开发构建

    ```bash
    # 安装依赖
    flutter pub get

    # 构建Debug APK
    flutter build apk --debug

    # 构建Release APK (arm64-v8a, 代码混淆+资源压缩)
    flutter build apk --release --split-per-abi --target-platform android-arm64
    ```

    生成的APK位于: `build/app/outputs/flutter-apk/app-arm64-v8a-release.apk`

    ## 🔍 技术细节

    ### V3.0 核心功能

    1. **智能应用切换** 🎯
    - 通过Quick Settings Tile快捷开关触发
    - 使用`am stack`命令获取前台应用
    - 调用`service call activity_task 50`切换显示屏
    - 自动杀死系统Launcher防止挤占
    - Toast提示显示具体应用名

    2. **充电动画** ⚡
    - 插电时自动在背屏显示充电动画
    - 3D玻璃闪电容器 + 流动绿色液体
    - 重力感应真实液体效果
    - 非线性动画，从0%填充到当前电量
    - UI图标使用闪电⚡标识，更符合充电主题
    - 可在应用内开关，支持充电动画常亮模式

    3. **通知推送** 📢
    - NotificationListenerService监听系统通知
    - 选择需要推送的应用（支持搜索、批量选择）
    - 通知内容显示在背屏（应用图标+标题+内容）
    - 隐私模式：隐藏通知具体内容
    - 精美动画：图标缩放+内容淡入
    - 自动销毁时间可自定义（无上限，支持任意时长）
    - 通知开关状态持久化，重启后正确恢复

    4. **背屏录屏** 📹
    - 悬浮窗控制，红色圆形/方形按钮
    - 使用screenrecord录制背屏画面
    - 持续唤醒背屏防止录制中断
    - 视频保存到Movies/MRSS_*.mp4
    - 可拖动悬浮窗位置

    5. **前台Service保活** 🛡️
    - 统一的"MRSS内核服务"前台通知
    - 可选的背屏常亮功能（FLAG_KEEP_SCREEN_ON）
    - 点击通知可快速返回主屏
    - 主应用隐藏于最近任务，防止误清理

    6. **智能监控与清理** 🔄
    - 每2秒检测背屏前台应用状态
    - 应用退出或切换时自动停止服务并清除通知
    - 防止多应用同时投放

    7. **DPI动态调整** 📱
    - 支持实时调整背屏显示密度
    - 使用`wm density`命令修改
    - 一键还原默认设置
    - 推荐范围: 260-350

    8. **背屏旋转控制** 🔄
    - 支持4个方向: 0°/90°/180°/270°
    - 使用`wm user-rotation -d 1`独立控制背屏
    - 旋转后应用自动复活
    - 实时显示当前旋转状态

    9. **背屏遮盖检测** 🤚
    - 可选的接近传感器检测功能
    - 应用内可开关
    
    10. **URI协议支持** 🌐
    - mrss://switch?current=1 - 切换当前应用
    - mrss://switch?packageName=xxx - 切换指定应用
    - mrss://return?current=1 - 返回主屏
    - mrss://screenshot - 截图
    - mrss://config?dpi=xxx&rotation=x - 配置，rotation传入的旋转应为0-3，与adb shell相同

    ### V3.1.3 新增特性

    - ✅ **多语言支持**: 完整支持简体中文、繁体中文和英文，自动跟随系统语言
    - ✅ **UI细节优化**: 修复多语言环境下的UI显示问题，优化文本换行和对齐
    - ✅ **通知本地化**: 通知标题和内容支持多语言显示

    ### V3.1.2 新增特性

    - ✅ **应用选择优化**: 选中的应用在列表中自动置顶显示，方便管理
    - ✅ **击杀逻辑优化**: 优化launcher击杀策略，改为仅在动画启动前击杀一次，避免频繁操作

    ### V3.1.1 新增特性

    - ✅ **服务保活优化**: ChargingService 添加前台服务保活，防止被系统杀死
    - ✅ **通知统一管理**: 所有服务使用统一的内核服务通知，避免多个通知堆积
    - ✅ **设置状态修复**: 修复 NotificationService 设置冲突问题，确保状态一致性

    ### V3.1.0 新增特性

    - ✅ **充电动画图标优化**: 充电动画UI图标从灯泡改为闪电图标，更符合充电主题
    - ✅ **通知开关状态修复**: 修复背屏通知开关状态持久化问题，重启后状态正确恢复
    - ✅ **通知服务状态同步**: 修复通知服务开关状态同步问题，关闭后再开启可正常工作
    - ✅ **自动销毁时间无上限**: 解除通知自动销毁时间60秒上限限制，支持自定义任意时长

    ### V3.0 新增特性

    - ✅ **充电动画**: 3D闪电容器 + 重力感应液体
    - ✅ **通知推送**: 系统通知实时显示到背屏
    - ✅ **背屏录屏**: 悬浮窗控制录制功能
    - ✅ **URI调用**: 支持外部应用控制（Tasker等）
    - ✅ **精美UI**: 四色渐变 + 超椭圆圆角设计
    - ✅ **代码优化**: 移除未使用代码，修复乱码问题

    ### 性能优化

    - ✅ 代码混淆（ProGuard/R8）
    - ✅ 资源压缩
    - ✅ 只包含arm64-v8a架构
    - ✅ APK体积优化

    ## 📄 许可证

    **V3.0.0及以后版本**: GPL-3.0 License - 详见 [LICENSE](LICENSE) 文件
    **V3.0.0以前版本**: MIT License

    ---

    ## 📝 更新日志

    ### V3.1.3 (2025)

    #### 新功能
    - 完整的多语言支持（简/繁/英）
    - 通知内容和Toast消息全面本地化

    #### 优化
    - 修复英文模式下UI文本溢出问题
    - 优化多语言环境下的文本显示效果

    ### V3.1.2 (2025)

    #### 功能优化
    - 应用选择列表中，选中的应用自动置顶显示，未选中的应用在下方
    - 优化充电动画和通知动画的launcher击杀逻辑，改为仅在动画启动前击杀一次

    #### 技术优化
    - 移除充电动画常亮循环中的持续击杀launcher逻辑
    - 移除通知动画循环中的持续击杀launcher逻辑
    - 应用选择列表排序逻辑优化，切换选择时自动重新排序

    ### V3.1.1 (2025)

    #### 问题修复
    - 修复 ChargingService 缺少前台服务保活导致的功能失效问题
    - 修复 NotificationService 设置冲突导致的状态不一致问题
    - 统一所有服务使用内核服务通知，避免通知栏堆积

    #### 技术优化
    - ChargingService 现在使用统一的内核服务通知保活
    - NotificationService 设置加载逻辑优化，避免状态冲突
    - 服务稳定性提升，减少被系统杀死的概率

    ### V3.1.0 (2025)

    #### 新功能
    - 充电动画UI图标从灯泡改为闪电图标
    - 解除通知自动销毁时间60秒上限限制

    #### 问题修复
    - 修复背屏通知开关状态持久化问题，重启后状态正确恢复
    - 修复通知服务开关状态同步问题，关闭后再开启可正常工作
    - 修复通知服务每次收到通知时重新加载开关状态

    ### V3.0.0 (2025)

    #### 重大更新
    - 采用GPL-3.0开源协议
    - 全新充电动画：3D闪电容器 + 重力感应液体效果
    - 通知推送功能：系统通知实时显示到背屏
    - 背屏录屏功能：悬浮窗控制录制
    - URI协议支持：支持外部应用控制（Tasker等）
    - 精美UI：四色渐变 + 超椭圆圆角设计

    ## 👥 团队

    ### 作者
    **AntiOblivionis**
    - 🎮 QQ: 319641317
    - 📱 酷安: [@AntiOblivionis](http://www.coolapk.com/u/8158212)
    - 🐙 Github: [GoldenglowSusie](https://github.com/GoldenglowSusie/)
    - 📺 Bilibili: [罗德岛T0驭械术师澄闪](https://space.bilibili.com/407059627)

    ### 首席测试官
    **汐木泽**
    - 📱 酷安: [@汐木泽](http://www.coolapk.com/u/4279097)
    - 提供关键测试反馈和功能建议

    ## 🤖 AI协作开发

    本项目由作者与以下AI助手共同开发：
    - Cursor
    - Claude-4.5-Sonnet
    - GPT-5
    - Gemini-2.5-Pro

    ## 🙏 致谢

    - [Shizuku](https://github.com/RikkaApps/Shizuku) - 提供特权API支持
    - Flutter团队 - 优秀的跨平台框架
    - Xiaomi HyperOS 小米澎湃OS团队 - 小米手机背屏功能

    ---

    ## 📜 版权声明

    ### 图标归属

    本应用使用的图标及品牌标识归属如下：

    1. **应用图标**：本应用图标直接使用了小米HyperOS系统中的图标资源。根据[小米操作系统用户协议](https://terms.miui.com/doc/eula/cn.html)，小米、MIUI、Xiaomi HyperOS等商标及相关图标的版权归小米科技有限责任公司所有。本应用仅为第三方开发的辅助工具，与小米官方无关，如有侵权请联系删除。

    2. **酷安图标**：应用内使用的酷安图标归酷安（北京酷安网络科技有限公司）所有。根据[酷安用户协议](https://m.coolapk.com/mp/user/agreement)，酷安的商标、图标等知识产权归其所有。本应用使用酷安图标仅用于跳转链接标识，不代表与酷安有任何官方合作关系。

    ### 免责声明

    本应用为开源项目，基于Shizuku实现背屏功能扩展，仅供学习交流使用。使用本应用即表示您理解并同意：
    - 本应用非小米官方应用，与小米公司无任何关联
    - 使用本应用的风险由用户自行承担
    - 开发者不对使用本应用造成的任何损失负责
    - 如有侵权，请联系删除

    ---




</details>

- <details>
    <summary>Español</summary>

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

    ## ✨ Funcionalidades (V3.1.3)

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

    ### Novedades V3.1.3
        - ✅ **Compatibilidad con varios idiomas**: Compatible con chino simplificado, chino tradicional e inglés, adaptándose automáticamente al idioma del sistema.
        - ✅ **Optimizaciones de la interfaz de usuario**: Se han corregido problemas de visualización en entornos multilingües y se ha optimizado el ajuste de texto y la alineación.
        - ✅ **Localización de notificaciones**: Los títulos y el contenido de las notificaciones ahora se muestran en varios idiomas.

    ### Novedades V3.1.2
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

    ### Novedades V3.0
        - ✅ **Animación de carga**: Contenedor de relámpagos 3D + líquido con sensor de gravedad
        - ✅ **Notificaciones push**: Notificaciones del sistema mostradas en la pantalla trasera en tiempo real
        - ✅ **Grabación de la pantalla trasera**: Función de grabación controlada por una ventana flotante
        - ✅ **Llamada URI**: Compatible con el control de aplicaciones externas (Tasker, etc.)
        - ✅ **Interfaz de usuario atractiva**: Degradado de cuatro colores + diseño de esquinas redondeadas superelípticas
        - ✅ **Optimización de código**: Se eliminó código innecesario y se corrigieron problemas de caracteres ilegibles

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

    ### V3.1.3 (2025)

    #### New Features
    - Full multilingual support (Simplified/Traditional/English)
    - Fully localized notification content and Toast messages

    #### Optimizations
    - Fixed UI text overflow issue in English mode
    - Optimized text display in multilingual environments

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


</details>