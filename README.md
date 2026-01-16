# AR Camera 📷

一款基于 Android 的 AR（增强现实）相机应用，用户可以在实时相机画面中看到经典动漫角色"孙悟空"的 3D 形象，并与角色一起拍照留念。

![AR Camera](https://img.shields.io/badge/Android-9.0+-green.svg)
![ARCore](https://img.shields.io/badge/ARCore-1.40.0-blue.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-purple.svg)

## 功能特性 ✨

### 核心功能
- ✅ **AR 相机视图**: 实时显示摄像头画面，并在其中渲染孙悟空 3D 模型
- ✅ **智能平面检测**: 使用 ARCore 进行自动平面检测，模型可放置在识别到的平面上
- ✅ **光照融合**: 自动光照估计，使 3D 模型与真实环境光照完美融合
- ✅ **手势交互**: 支持旋转、缩放、移动 3D 模型位置

### 拍照功能
- 📸 拍摄包含 AR 场景的高清照片
- 💾 一键保存到设备相册
- 🔦 支持闪光灯控制
- 🔊 拍照音效反馈

### 滤镜效果
- 🎨 提供多种滤镜选项：
  - 原图模式
  - 暖色调滤镜
  - 冷色调滤镜
  - 黑白滤镜
  - 复古滤镜

### 贴纸系统
- 🎭 预设贴纸/道具库：
  - 金箍棒
  - 筋斗云
  - 桃子
  - 战斗特效
  - 皇冠
- 🖐️ 支持多个贴纸组合
- 🔄 贴纸可缩放、旋转、删除

## 技术架构 🏗️

### 技术栈
- **AR 框架**: Google ARCore 1.40.0
- **渲染引擎**: Sceneform 1.17.1
- **开发语言**: Kotlin 1.9.20
- **最低支持**: Android 9.0 (API 28)
- **目标 SDK**: Android 14 (API 34)

### 核心依赖
```gradle
implementation 'com.google.ar:core:1.40.0'
implementation 'com.google.ar.sceneform.ux:sceneform-ux:1.17.1'
implementation 'androidx.camera:camera-core:1.3.1'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0'
```

### 项目结构
```
app/
├── src/main/
│   ├── java/com/example/arcamera/
│   │   ├── MainActivity.kt              # 主入口
│   │   ├── ar/
│   │   │   ├── ArFragment.kt           # AR 相机 Fragment
│   │   │   ├── ArManager.kt            # ARCore 管理器
│   │   │   └── ModelLoader.kt          # 3D 模型加载器
│   │   ├── models/
│   │   │   └── CharacterModel.kt       # 角色模型数据类
│   │   ├── filters/
│   │   │   ├── FilterManager.kt        # 滤镜管理器
│   │   │   └── FilterType.kt           # 滤镜类型枚举
│   │   ├── stickers/
│   │   │   ├── StickerManager.kt       # 贴纸管理器
│   │   │   └── Sticker.kt              # 贴纸数据类
│   │   ├── camera/
│   │   │   └── PhotoCaptureManager.kt  # 拍照管理器
│   │   └── utils/
│   │       ├── PermissionHelper.kt     # 权限辅助类
│   │       └── FileUtils.kt            # 文件工具
│   ├── res/
│   │   ├── layout/                     # 布局文件
│   │   ├── values/                     # 资源值
│   │   └── raw/                        # 3D 模型资源
│   └── AndroidManifest.xml
```

## 开始使用 🚀

### 环境要求
- Android Studio Arctic Fox (2020.3.1) 或更高版本
- JDK 8 或更高版本
- Android SDK API 28 或更高
- 支持 ARCore 的 Android 设备

### 安装步骤

1. **克隆项目**
```bash
git clone https://github.com/mangowong/ar-camera.git
cd ar-camera
```

2. **打开项目**
- 使用 Android Studio 打开项目目录
- 等待 Gradle 同步完成

3. **配置设备**
- 确保设备支持 ARCore
- 在设备上安装 Google Play Services for AR
- 启用开发者模式和 USB 调试

4. **运行应用**
```bash
./gradlew installDebug
```

或在 Android Studio 中点击 Run 按钮。

### 权限说明
应用需要以下权限：
- `CAMERA` - 用于 AR 相机功能
- `WRITE_EXTERNAL_STORAGE` - 用于保存照片（Android 9-10）
- `INTERNET` - 用于下载 3D 模型资源

## 使用说明 📖

### 基本操作

1. **启动应用**
   - 打开应用后，会请求相机权限
   - 应用会自动检查设备是否支持 ARCore

2. **扫描平面**
   - 移动手机，缓慢扫描周围环境
   - 当检测到平面时，屏幕会显示提示

3. **放置角色**
   - 点击屏幕上的平面位置
   - 孙悟空 3D 模型会出现在该位置

4. **调整模型**
   - 单指拖动：移动模型位置
   - 双指捏合：缩放模型大小
   - 双指旋转：旋转模型角度

5. **拍照**
   - 点击底部中央的拍照按钮
   - 照片会自动保存到相册

6. **使用滤镜**
   - 点击左侧滤镜按钮
   - 选择想要的滤镜效果

7. **添加贴纸**
   - 点击右侧贴纸按钮
   - 选择要添加的贴纸或道具

## 开发路线图 🗺️

### MVP 阶段（当前版本）
- [x] 搭建 Android 项目基础架构
- [x] 集成 ARCore 和 Sceneform
- [x] 实现基础 AR 渲染（平面检测 + 模型放置）
- [x] 实现拍照保存功能
- [x] 基础 UI 布局
- [x] 实现滤镜系统
- [x] 实现贴纸系统

### V1.0 阶段（计划中）
- [ ] 优化孙悟空 3D 模型
- [ ] 添加更多动画效果
- [ ] 实现视频录制功能
- [ ] 添加社交分享功能
- [ ] 性能优化和适配测试
- [ ] 支持更多角色模型

### 未来规划
- [ ] 多角色选择系统
- [ ] 自定义角色上传
- [ ] AR 滤镜商城
- [ ] 社交功能（AR 合照分享）
- [ ] AI 智能场景识别

## 常见问题 ❓

### Q: 应用无法启动或闪退？
A: 请确保：
1. 设备支持 ARCore
2. 已安装 Google Play Services for AR
3. 已授予相机权限

### Q: 无法检测到平面？
A: 尝试：
1. 在光照充足的环境下使用
2. 缓慢移动手机扫描平面
3. 选择有明显纹理的表面（如地板、桌面）

### Q: 3D 模型显示异常？
A: 可能原因：
1. 模型文件下载不完整，请重新启动应用
2. 设备性能不足，尝试关闭其他应用

### Q: 照片保存失败？
A: 检查：
1. 是否授予存储权限
2. 设备存储空间是否充足

## 贡献指南 🤝

欢迎贡献代码、报告问题或提出建议！

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 开源协议 📄

本项目采用 MIT 协议 - 详见 [LICENSE](LICENSE) 文件

## 致谢 🙏

- [Google ARCore](https://developers.google.com/ar) - AR 技术支持
- [Sceneform](https://github.com/google-ar/sceneform-android-sdk) - 3D 渲染引擎
- [Sketchfab](https://sketchfab.com) - 3D 模型资源

## 联系方式 📧

- 项目主页: [https://github.com/mangowong/ar-camera](https://github.com/mangowong/ar-camera)
- 问题反馈: [GitHub Issues](https://github.com/mangowong/ar-camera/issues)

---

**注意**: 本项目仅供学习交流使用，请勿用于商业用途。
