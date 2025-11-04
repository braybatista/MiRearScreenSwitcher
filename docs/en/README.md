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

## ✨ Features (V3.1.2)

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

### V3.0 New Features
   - ✅ **Charging Animation**: 3D lightning container + gravity-sensing liquid
   - ✅ **Notification Push**: System notifications displayed on the back screen in real time
   - ✅ **Back Screen Recording**: Floating window controls recording function
   - ✅ **URI Call**: Supports external application control (Tasker, etc.)
   - ✅ **Beautiful UI**: Four-color gradient + super elliptical rounded corner design
   - ✅ **Code Optimization**: Removed unused code and fixed garbled character issues

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
