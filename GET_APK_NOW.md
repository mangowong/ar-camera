# 🚀 立即获取 APK - 3 步搞定！

## 🎯 最快的方法：使用 GitHub Actions 自动构建

---

## ⚡ 快速开始（只需 3 步）

### 步骤 1: 推送代码到 GitHub

```bash
# 如果代码还没推送到远程仓库
git remote add origin https://github.com/mangowong/ar-camera.git
git push -u origin main
```

**或者**，如果已经推送过，只需：
```bash
git push origin main
```

---

### 步骤 2: 等待自动构建（5-10 分钟）

1. **访问 Actions 页面**
   👉 https://github.com/mangowong/ar-camera/actions

2. **查看构建状态**
   - 会看到一个黄色的圆点（构建中）
   - 等待变成绿色的 ✅（成功）

3. **首次构建可能需要 15-20 分钟**（下载依赖）

---

### 步骤 3: 下载 APK

1. **点击构建任务**
   - 点击最新的 "Build Debug APK" 任务

2. **滚动到底部**
   - 找到 "Artifacts" 部分

3. **下载 APK**
   - 点击 "app-debug" 下载
   - 解压 zip 文件
   - 获取 `app-debug.apk`

---

## 📱 安装到手机

1. **启用未知来源**
   - 设置 -> 安全 -> 允许未知来源

2. **安装 APK**
   - 打开下载的 `app-debug.apk`
   - 点击安装

3. **授予权限**
   - 允许相机权限
   - 允许存储权限

4. **开始使用**
   - 扫描平面
   - 放置 3D 模型
   - 拍照留念！

---

## 🎨 手动触发构建（可选）

如果您想立即构建而不推送新代码：

1. 访问: https://github.com/mangowong/ar-camera/actions
2. 点击左侧 "Build Debug APK"
3. 点击 "Run workflow" 按钮
4. 选择 `main` 分支
5. 点击 "Run workflow"
6. 等待构建完成并下载

---

## 📊 构建时间说明

| 情况 | 预计时间 |
|------|---------|
| 首次构建 | 15-20 分钟 |
| 后续构建 | 5-10 分钟 |
| 代码变更后 | 5-10 分钟 |

---

## ⚠️ 注意事项

### 设备要求
- ✅ Android 9.0 (API 28) 或更高
- ✅ 支持 ARCore 的设备
- ✅ 已安装 Google Play Services for AR

### 检查设备支持
👉 https://developers.google.com/ar/devices

---

## 🆘 遇到问题？

### 构建失败
1. 查看 Actions 日志
2. 检查代码是否有错误
3. 查看 GITHUB_ACTIONS_GUIDE.md

### APK 无法安装
1. 确认 Android 版本 >= 9.0
2. 检查设备是否支持 ARCore
3. 卸载旧版本后重新安装

### 找不到 Actions 页面
1. 确保代码已推送到 GitHub
2. 刷新 GitHub 页面
3. 检查仓库是否为 Public

---

## 📚 更多信息

详细文档请查看：
- **GITHUB_ACTIONS_GUIDE.md** - 完整使用指南
- **QUICK_START.md** - 快速开始指南
- **README.md** - 项目说明

---

## 🎉 开始吧！

```bash
# 只需这一行命令！
git push origin main
```

然后访问: https://github.com/mangowong/ar-camera/actions

等待 5-10 分钟，APK 就准备好了！

---

**祝您使用愉快！** 🚀✨
