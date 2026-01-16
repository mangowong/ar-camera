# 开发指南

## 开发环境设置

### 必要条件
- Android Studio Arctic Fox (2020.3.1) 或更高版本
- JDK 8 或更高版本
- Android SDK API 28+ (推荐 API 34)
- 支持 ARCore 的 Android 设备或模拟器

### 模拟器设置
ARCore 在模拟器上的支持有限，建议使用真实设备进行开发。如果需要在模拟器上测试：
1. 创建带有 Google Play Services 的虚拟设备
2. API Level >= 28
3. CPU/ABI: x86_64

## 项目架构说明

### AR 集成
- 使用 Google ARCore 进行平面检测和环境光照估计
- Sceneform 用于 3D 模型渲染和交互
- TransformableNode 提供内置的手势控制

### 拍照实现
- 使用 PixelCopy API 捕获 AR 场景
- 通过 MediaStore API 保存到相册
- 支持 Android 10+ 的分区存储

### 滤镜系统
- 使用 ColorMatrixColorFilter 实现滤镜效果
- 支持实时预览和照片后处理
- 可扩展的滤镜接口

### 贴纸系统
- 基于 Sceneform 节点系统
- 支持多个贴纸同时存在
- 可独立控制每个贴纸的变换

## 3D 模型准备

### 支持的格式
- .glb (推荐)
- .gltf
- .obj (Sceneform 会自动转换)

### 模型要求
- 面数: 5000-15000 多边形
- 纹理: PNG/JPG 格式，最大 2048x2048
- 文件大小: < 10MB
- 包含基础材质和骨骼动画

### 模型资源
推荐的 3D 模型网站：
- [Sketchfab](https://sketchfab.com) - 搜索 "monkey king"
- [Poly](https://poly.google.com) - Google 的 3D 模型库
- [Sketchfab Search](https://sketchfab.com/search?q=monkey+king&type=models)

注意版权问题，选择 CC0 或 CC-BY 协议的模型。

## 关键代码说明

### ARManager
管理 AR 场景的核心类：
- `placeCharacter()` - 放置角色模型
- `addSticker()` - 添加贴纸
- `loadCharacterModel()` - 异步加载 3D 模型

### PhotoCaptureManager
拍照功能管理：
- `capturePhoto()` - 捕获当前 AR 场景
- 使用 PixelCopy 获取高分辨率截图
- 自动处理权限和文件保存

### FilterManager
滤镜系统：
- `applyFilter()` - 应用滤镜效果
- `applyFilterToBitmap()` - 处理照片
- 可扩展添加自定义滤镜

### StickerManager
贴纸管理：
- `showStickerSelector()` - 显示贴纸选择器
- 预设常用贴纸资源

## 调试技巧

### 启用 ARCore 调试
在 `build.gradle` 中添加：
```gradle
android {
    buildTypes {
        debug {
            debuggable true
        }
    }
}
```

### 查看日志
```bash
adb logcat | grep "ARCamera"
```

### 性能分析
使用 Android Studio 的 Profiler 工具监控：
- CPU 使用率
- 内存占用
- GPU 渲染性能
- 网络请求

## 常见问题解决

### 模型加载失败
1. 检查模型文件路径
2. 验证模型格式是否兼容
3. 查看错误日志

### 平面检测不准确
1. 增加光照条件
2. 改善设备摄像头清洁度
3. 调整 ARCore 配置参数

### 性能问题
1. 降低模型面数
2. 优化纹理大小
3. 使用异步加载

## 扩展开发

### 添加新的滤镜
在 `FilterManager` 中创建新的滤镜方法：
```kotlin
private fun createCustomFilter(): ColorMatrixColorFilter {
    // 自定义颜色矩阵
}
```

### 添加新的贴纸
在 `StickerManager` 的 `availableStickers` 列表中添加：
```kotlin
Sticker(
    "new_sticker_id",
    "贴纸名称",
    Uri.parse("模型路径")
)
```

### 添加新的 3D 角色
1. 准备模型文件
2. 放入 `assets/models/` 目录
3. 更新 `ArManager.CHARACTER_MODEL_URL`
4. 调整模型缩放和位置

## 测试指南

### 单元测试
```bash
./gradlew test
```

### UI 测试
```bash
./gradlew connectedAndroidTest
```

### 手动测试清单
- [ ] ARCore 检测和初始化
- [ ] 平面检测功能
- [ ] 模型加载和放置
- [ ] 手势交互（缩放、旋转、移动）
- [ ] 拍照保存功能
- [ ] 滤镜切换
- [ ] 贴纸添加
- [ ] 权限请求流程
- [ ] 不同设备兼容性

## 发布准备

### 生成签名 APK
```bash
./gradlew assembleRelease
```

### 应用签名
1. 创建密钥库: `keytool -genkey -v -keystore my-key.keystore`
2. 在 `build.gradle` 中配置签名
3. 构建发布版本

### 发布前检查
- [ ] 代码混淆配置
- [ ] 资源压缩
- [ ] 权限说明完善
- [ ] 应用图标和启动画面
- [ ] 多语言支持（可选）
- [ ] 无障碍功能支持

## 参考资源

- [ARCore 官方文档](https://developers.google.com/ar)
- [Sceneform SDK](https://github.com/google-ar/sceneform-android-sdk)
- [Android 开发指南](https://developer.android.com/guide)
- [Kotlin 语言文档](https://kotlinlang.org/docs/)
