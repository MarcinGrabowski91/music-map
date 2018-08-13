# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/maciek/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# base option from *App Dev Note*
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes LineNumberTable,SourceFile,Signature,*Annotation*,Exceptions,InnerClasses

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

# remove log call
-assumenosideeffects class android.util.Log {
    public static *** d(...);
}
-assumenosideeffects class timber.log.Timber {
    public static *** d(...);
}

# Models!
-keepclassmembernames class eu.gitcode.musicmap.data.**.model.** { *; }

# app compat-v7
-keep class android.support.v7.widget.SearchView { *; }

# FragmentArgs
-keep class com.hannesdorfmann.fragmentargs.** { *; }

# Gson
-keep class sun.misc.Unsafe { *; }
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-dontwarn sun.misc.**

# retrofit
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

# dagger
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}
-keep class javax.inject.** { *; }
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection
-keep class dagger.** { *; }
-dontwarn dagger.internal.codegen.**

# stetho
-dontwarn org.apache.http.**
-keep class com.facebook.stetho.dumpapp.** { *; }
-keep class com.facebook.stetho.server.** { *; }
-dontwarn com.facebook.stetho.dumpapp.**
-dontwarn com.facebook.stetho.server.**

# leak canary
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }
-dontwarn android.app.Notification

# fabric
-dontwarn com.crashlytics.android.**

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn okio.**
-dontwarn okhttp3.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

#GMS
-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

-dontwarn retrofit.**
-dontwarn javax.annotation.**

# --- Retrofit ---
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.*

#dagger
-dontwarn com.google.errorprone.annotations.*

# Retrofit 2
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# --- OkHttp ---
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn okio.BufferedSink
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

-dontwarn okhttp3.internal.platform.ConscryptPlatform

# --- AutoValue ---
# AutoValue annotations are retained but dependency is compileOnly.
-dontwarn com.google.auto.value.**