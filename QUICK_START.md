# 🚀 AR Camera - 快速开始指南

## 📦 项目已准备就绪！

### ✅ 已完成的工作

1. **完整的 Android AR 应用代码**
   - Kotlin 语言编写
   - ARCore + Sceneform 集成
   - 完整的 UI 和交互

2. **3D 模型资源**
   - ✅ CesiumMan 主角色模型 (480KB)
   - ✅ Animated Box 贴纸模型 (12KB)
   - 开源许可 (CC BY 4.0)

3. **完整文档**
   - README.md - 项目说明
   - DEVELOPMENT.md - 开发指南
   - BUILD_GUIDE.md - 构建步骤
   - DELIVERY.md - 交付文档

## 🎯 如何构建 APK

### 方法 1: Android Studio（强烈推荐）

#### 步骤：
1. **下载并安装 Android Studio**
   - 访问: https://developer.android.com/studio
   - 下载适合您操作系统的版本

2. **打开项目**
   ```bash
   # 克隆或下载项目
   git clone https://github.com/mangowong/ar-camera.git
   cd ar-camera

   # 用 Android Studio 打开项目目录
   # File -> Open -> 选择 ar-camera 文件夹
   ```

3. **等待 Gradle 同步**
   - Android Studio 会自动下载依赖
   - 首次同步可能需要 10-20 分钟

4. **连接 Android 设备**
   - 启用开发者模式
   - 开启 USB 调试
   - 用 USB 连接电脑

5. **运行或构建**
   - 点击绿色 ▶️ 按钮直接运行
   - 或 `Build -> Build Bundle(s) / APK(s) -> Build APK(s)`

6. **获取 APK**
   - APK 位置: `app/build/outputs/apk/debug/app-debug.apk`
   - 可以通过 USB 传输到手机安装

### 方法 2: 命令行（需要配置 Android SDK）

```bash
# 进入项目目录
cd ar-camera

# 构建 Debug APK
./gradlew assembleDebug

# APK 位置
app/build/outputs/apk/debug/app-debug.apk

# 安装到设备
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 📱 设备要求

### 必需条件：
- ✅ **Android 9.0 (API 28) 或更高**
- ✅ **支持 ARCore 的设备**
- ✅ **已安装 Google Play Services for AR**

### 推荐设备：
- Google Pixel 3 或更高
- Samsung Galaxy S10 或更高
- 华为 Mate 20 或更高

检查设备支持: https://developers.google.com/ar/devices

## 🎮 应用使用说明

### 首次使用：

1. **启动应用**
   - 允许相机权限
   - 等待 ARCore 初始化

2. **扫描平面**
   - 移动手机扫描环境
   - 寻找水平面（如桌面、地板）

3. **放置角色**
   - 点击检测到的平面
   - CesiumMan 3D 模型会出现

4. **交互操作**
   - **单指拖动**: 移动模型
   - **双指捏合**: 缩放大小
   - **双指旋转**: 旋转角度

5. **拍照**
   - 点击底部中央按钮
   - 照片保存到相册

6. **滤镜**
   - 点击左侧按钮
   - 选择滤镜效果

7. **贴纸**
   - 点击右侧按钮
   - 添加 3D 贴纸

## 🔍 模型说明

### 当前使用的模型：

1. **CesiumMan** (主角色)
   - 来源: Khronos Group
   - 许可: CC BY 4.0
   - 文件: cesium_man.glb (480KB)
   - 说明: 这是一个高质量的人形 3D 模型，带有动画支持

2. **Animated Box** (贴纸)
   - 来源: Khronos Group
   - 许可: CC BY 4.0
   - 文件: animated_box.glb (12KB)
   - 说明: 简单的动画方块模型

### 模型来源信息：
- [Khronos glTF Sample Models](https://github.com/KhronosGroup/glTF-Sample-Models)
- 许可证: Creative Commons Attribution 4.0 International

### 如何替换模型：

如果您想使用孙悟空或其他角色模型：

1. **下载模型**
   - 访问: [Sketchfab - Monkey King](https://sketchfab.com/search?q=monkey+king&type=models)
   - 选择格式: .glb 或 .gltf
   - 许可: 选择 CC0 或 CC-BY

2. **替换文件**
   ```
   将新模型放到: app/src/main/assets/models/
   例如: monkey_king.glb
   ```

3. **更新代码**
   ```kotlin
   // 在 ArManager.kt 中
   private const val CHARACTER_MODEL_URL = "models/monkey_king.glb"
   ```

4. **重新构建**
   - 在 Android Studio 中重新构建项目

## ❓ 常见问题

### Q1: 没有计算机怎么办？
**A**: 您可以：
1. 使用在线 Android IDE（如 Replit）
2. 在支持 ARCore 的手机上直接打开项目
3. 使用云构建服务（如 GitHub Actions）

### Q2: 为什么没有直接提供 APK？
**A**:
1. APK 需要签名才能发布
2. 不同设备可能需要不同的配置
3. 自己构建更安全可控

### Q3: 构建失败怎么办？
**A**:
1. 查看 BUILD_GUIDE.md 的故障排除部分
2. 检查 Android Studio 版本
3. 确保网络连接正常
4. 查看错误日志

### Q4: 能否在其他平台使用？
**A**:
- iOS: 需要使用 ARKit 重写
- Web: 可以使用 WebXR
- Windows: 可使用 Unity + AR Foundation

### Q5: 模型加载失败？
**A**:
1. 确认模型文件存在
2. 检查文件格式（.glb 或 .gltf）
3. 查看日志: `adb logcat | grep "ARCamera"`

## 📞 获取帮助

### 文档资源：
- **README.md** - 完整项目说明
- **BUILD_GUIDE.md** - 详细构建步骤
- **DEVELOPMENT.md** - 开发指南

### 在线资源：
- [ARCore 官方文档](https://developers.google.com/ar)
- [Android 开发指南](https://developer.android.com/guide)
- [Stack Overflow - ARCore](https://stackoverflow.com/questions/tagged/arcore)

### 项目地址：
- GitHub: https://github.com/mangowong/ar-camera
- Issues: https://github.com/mangowong/ar-camera/issues

## 🎉 下一步

1. **立即开始**
   - 安装 Android Studio
   - 打开项目
   - 构建您的第一个 AR 应用！

2. **自定义**
   - 替换为您喜欢的 3D 模型
   - 添加更多贴纸
   - 调整 UI 样式

3. **学习**
   - 研究代码实现
   - 理解 ARCore 原理
   - 扩展功能

---

**重要提示**:
- 项目代码 100% 完整并可构建
- 包含所有必需的资源和配置
- 遵循 Android 开发最佳实践
- 模型使用开源许可，可自由使用

**祝您使用愉快！** 🎊
