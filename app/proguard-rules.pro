# Keep Sceneform classes
-keep class com.google.ar.sceneform.ux.** { *; }
-keep class com.google.ar.sceneform.rendering.** { *; }
-keep class com.google.ar.core.** { *; }

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}
