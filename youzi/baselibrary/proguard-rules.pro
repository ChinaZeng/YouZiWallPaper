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
##混淆时不使用大小写混合类名
-dontusemixedcaseclassnames
##不跳过library中的非public的类
-dontskipnonpubliclibraryclasses
##打印混淆的详细信息
-verbose
# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
##不进行优化，建议使用此选项，理由见上
-dontoptimize
##不进行预校验，预校验是作用在Java平台上的，Android平台上不需要这项功能，去掉之后还可以加快混淆速度
-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.

##保留注解参数
-keepattributes *Annotation*
##保留Google原生服务需要的类
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http:##proguard.sourceforge.net/manual/examples.html#native
##保留native方法的类名和方法名
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http:##proguard.sourceforge.net/manual/examples.html#beans
##保留自定义View,如"属性动画"中的set/get方法
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
##保留Activity中参数是View的方法，如XML中配置android:onClick=”buttonClick”属性，Activity中调用的buttonClick(View view)方法
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http:##proguard.sourceforge.net/manual/examples.html#enumerations
##保留混淆枚举中的values()和valueOf()方法
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

##Parcelable实现类中的CREATOR字段是绝对不能改变的，包括大小写
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

##R文件中的所有记录资源id的静态字段
-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Dont warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
##忽略support包因为版本兼容产生的警告
-dontwarn android.support.**

##------------------------------commonLibrary------------------------------start--
###@AutoParam
-keepclassmembers class **{
    @com.youzi.framework.common.util.autoparam.AutoParam <fields>;
}
##------------------------------commonLibrary------------------------------end----
##------------------------------baseLibrary------------------------------start--
-keep class com.youzi.framework.base.saripaar.** { *; }
##------------------------------baseLibrary------------------------------end--
##------------------------------Dagger2------------------------------start--

##------------------------------Dagger2------------------------------end----
##------------------------------OkHttp------------------------------start--
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
##------------------------------OkHttp------------------------------end--
##------------------------------EventBus------------------------------start--
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
##------------------------------EventBus------------------------------end--
##------------------------------Glide------------------------------start--
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-result@value=GlideModule
##------------------------------Glide------------------------------end--
##------------------------------saripaar------------------------------start--
-keep class com.mobsandgeeks.saripaar.** {*;}
-keep @com.mobsandgeeks.saripaar.annotation.ValidateUsing class * {*;}
##------------------------------saripaar------------------------------end--
##------------------------------retrofit------------------------------start--
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
##------------------------------retrofit------------------------------end--
##Rxjava Dagger
-dontwarn rx.**
-keep class rx.** { *; }
-dontwarn com.trello.rxlifecycle.**
-keep class com.trello.rxlifecycle.** { *; }
-dontwarn com.google.errorprone.annotations.**
-keep class com.google.errorprone.annotations.** { *; }
##-------------------------Umeng------------------------------
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

##------------BaseRecyclerViewAdapterHelper------------------
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

