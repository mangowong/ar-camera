# 构建指南 / Build Guide

## 📋 环境要求 / Requirements

### 必需软件 / Required Software

1. **Android Studio** (推荐 Arctic Fox 或更高版本)
   - 下载地址: https://developer.android.com/studio
   - 包含 Android SDK、Gradle、模拟器等所有必需工具

2. **JDK 8 或更高版本**
   - Android Studio 自带 JDK

3. **支持 ARCore 的 Android 设备**
   - Android 9.0 (API 28) 或更高
   - 参考: https://developers.google.com/ar/devices

## 🚀 构建步骤 / Build Steps

### 方式 1: 使用 Android Studio（推荐）

#### 1. 打开项目
```bash
# 克隆项目
git clone https://github.com/mangowong/ar-camera.git
cd ar-camera

# 使用 Android Studio 打开项目目录
# File -> Open -> 选择项目目录
```

#### 2. 同步 Gradle
- 打开项目后，Android Studio 会自动同步 Gradle
- 等待同步完成（首次需要下载依赖，可能需要几分钟）

#### 3. 连接设备
- 启用开发者模式和 USB 调试
- 连接 Android 设备
- 在 Android Studio 顶部工具栏选择设备

#### 4. 运行应用
- 点击绿色运行按钮 ▶️
- 或按快捷键 `Shift + F10`

#### 5. 构建 APK
- **Debug APK**:
  - 菜单: `Build -> Build Bundle(s) / APK(s) -> Build APK(s)`
  - 输出位置: `app/build/outputs/apk/debug/app-debug.apk`

- **Release APK**:
  - 菜单: `Build -> Generate Signed Bundle / APK`
  - 选择 APK
  - 创建或选择密钥库
  - 输出位置: `app/build/outputs/apk/release/app-release.apk`

### 方式 2: 使用命令行

#### 前提条件
- 已安装 Android SDK
- 配置了 `ANDROID_HOME` 环境变量
- 已添加 Android SDK 工具到 PATH

#### 构建 Debug APK
```bash
# 清理旧的构建
./gradlew clean

# 构建 Debug APK
./gradlew assembleDebug

# APK 位置
# app/build/outputs/apk/debug/app-debug.apk
```

#### 构建 Release APK
```bash
# 构建 Release APK
./gradlew assembleRelease

# 需要配置签名信息
# 编辑 app/build.gradle.kts
```

#### 安装到设备
```bash
# 安装 Debug APK
./gradlew installDebug

# 或使用 adb
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 📦 已包含的资源

### 3D 模型
项目已包含以下开源 3D 模型：

1. **CesiumMan** (cesium_man.glb - 480KB)
   - 来源: Khronos glTF Sample Models
   - 许可: CC BY 4.0
   - 作为主角色使用

2. **Animated Box** (animated_box.glb - 12KB)
   - 来源: Khronos glTF Sample Models
   - 许可: CC BY 4.0
   - 作为贴纸使用

所有模型位于: `app/src/main/assets/models/`

## ⚙️ 配置说明

### 最低 SDK 版本
```kotlin
minSdk = 28  // Android 9.0
targetSdk = 34  // Android 14
```

### ARCore 要求
- 应用会自动检查设备是否支持 ARCore
- 如果未安装，会引导用户安装
- 参考: https://developers.google.com/ar

## 🔧 常见问题

### Q1: Gradle 同步失败
**A**:
1. 检查网络连接
2. 在 `build.gradle.kts` 中配置镜像源（如阿里云镜像）
3. 删除 `.gradle` 目录后重新同步

### Q2: ARCore 依赖下载失败
**A**:
1. 检查 Google Maven 仓库是否可访问
2. 配置代理或镜像
3. 手动下载 ARCore AAR 并放入本地仓库

### Q3: 构建时内存不足
**A**:
在 `gradle.properties` 中增加：
```properties
org.gradle.jvmargs=-Xmx4096m
```

### Q4: 设备不支持 ARCore
**A**:
- 检查设备列表: https://developers.google.com/ar/devices
- 确保设备 Android 版本 >= 9.0
- 安装 Google Play Services for AR

### Q5: 应用安装后闪退
**A**:
- 检查 logcat: `adb logcat | grep "ARCamera"`
- 确认设备支持 ARCore
- 检查相机权限是否授予

## 📱 测试建议

### 功能测试清单
- [ ] 应用正常启动
- [ ] 相机权限请求正常
- [ ] ARCore 初始化成功
- [ ] 平面检测功能正常
- [ ] 3D 模型加载和显示
- [ ] 手势交互（缩放、旋转、移动）
- [ ] 拍照保存功能
- [ ] 滤镜切换
- [ ] 贴纸添加

### 性能测试
- 使用 Android Studio Profiler 监控：
  - CPU 使用率
  - 内存占用
  - GPU 渲染
  - 电池消耗

### 兼容性测试
建议测试设备：
- Pixel 3 或更高（最佳支持）
- Samsung Galaxy S10 或更高
- 其他支持 ARCore 的设备

## 📝 发布准备

### 发布前检查
1. **代码混淆**
   - 已配置 `proguard-rules.pro`
   - Release 构建会自动启用

2. **签名**
   - 创建密钥库: `keytool -genkey -v -keystore my-key.keystore`
   - 在 `app/build.gradle.kts` 中配置签名信息

3. **版本信息**
   - 更新 `versionCode` 和 `versionName`

4. **资源优化**
   - 压缩图片资源
   - 优化 3D 模型面数
   - 启用代码混淆和资源压缩

### 上传到 Google Play
参考: https://developer.android.com/studio/publish

## 🔗 相关资源

- [Android 开发文档](https://developer.android.com/guide)
- [ARCore 开发指南](https://developers.google.com/ar)
- [Sceneform 文档](https://github.com/google-ar/sceneform-android-sdk)
- [3D 模型资源](https://github.com/KhronosGroup/glTF-Sample-Models)

## 💡 提示

1. **首次构建**: 首次构建需要下载依赖，可能需要 10-30 分钟
2. **网络问题**: 如果网络不稳定，建议使用 VPN 或镜像源
3. **设备测试**: AR 应用必须在真实设备上测试，模拟器支持有限
4. **模型优化**: 如果需要替换模型，确保格式为 .glb 或 .gltf

---

**注意**: 本项目仅供学习交流使用，遵循模型原始许可协议（CC BY 4.0）。
