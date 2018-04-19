# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# wiz
-keepclasseswithmembers class cn.wiz.** { *; }
-dontwarn cn.wiz.**

#joda time
-keep class org.joda.time.** { *; }
-dontwarn org.joda.time.**

# bottom sheet
-keep class com.cocosw.query.**
-keepclassmembers class com.cocosw.query.** { *; }
# take photo
-keep class com.jph.takephoto.** { *; }
-dontwarn com.jph.takephoto.**

-keep class com.darsh.multipleimageselect.** { *; }
-dontwarn com.darsh.multipleimageselect.**

-keep class com.soundcloud.android.crop.** { *; }
-dontwarn com.soundcloud.android.crop.**
# image loader
-keep class com.nostra13.universalimageloader.** { *; }
-dontwarn com.nostra13.universalimageloader.**

#okhttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

#okio
-keep class okio.** { *; }
-dontwarn okio.**

-keep class sun.misc.** { *; }
-dontwarn sun.misc.**