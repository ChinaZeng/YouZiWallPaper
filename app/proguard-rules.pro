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

-dontwarn org.junit.**
-dontwarn android.test.**



#TODO 过滤实体类混淆
-keep class com.youzi.service.api.resp.** { *; } #实体类不参与混淆
-keep class com.youzi.service.api.req.** { *; } #实体类不参与混淆
-keep class com.youzi.service.api.intercepters.**{*;}

##------------------------------commonLibrary------------------------------start--
###@AutoParam
-keepclassmembers class **{
    @com.youzi.framework.common.util.autoparam.AutoParam <fields>;
}
##------------------------------commonLibrary------------------------------end----


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



#X5
# --------------------------------------------------------------------------
# Addidional for x5.sdk classes for apps

#-optimizationpasses 7
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-dontskipnonpubliclibraryclassmembers
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
#-overloadaggressively

#@proguard_debug_start
# ------------------ Keep LineNumbers and properties ---------------- #
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
#-renamesourcefileattribute TbsSdkJava
-keepattributes SourceFile,LineNumberTable
-keep class com.tencent.smtt.export.external.**{
    *;
}

-keep class com.tencent.tbs.video.interfaces.IUserStateChangedListener {
	*;
}

-keep class com.tencent.smtt.sdk.CacheManager {
	public *;
}

-keep class com.tencent.smtt.sdk.CookieManager {
	public *;
}

-keep class com.tencent.smtt.sdk.WebHistoryItem {
	public *;
}

-keep class com.tencent.smtt.sdk.WebViewDatabase {
	public *;
}

-keep class com.tencent.smtt.sdk.WebBackForwardList {
	public *;
}

-keep public class com.tencent.smtt.sdk.WebView {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebView$HitTestResult {
	public static final <fields>;
	public java.lang.String getExtra();
	public int getType();
}

-keep public class com.tencent.smtt.sdk.WebView$WebViewTransport {
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebView$PictureListener {
	public <fields>;
	public <methods>;
}


-keepattributes InnerClasses

-keep public enum com.tencent.smtt.sdk.WebSettings$** {
    *;
}

-keep public enum com.tencent.smtt.sdk.QbSdk$** {
    *;
}

-keep public class com.tencent.smtt.sdk.WebSettings {
    public *;
}


-keepattributes Signature
-keep public class com.tencent.smtt.sdk.ValueCallback {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebViewClient {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.DownloadListener {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebChromeClient {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebChromeClient$FileChooserParams {
	public <fields>;
	public <methods>;
}

-keep class com.tencent.smtt.sdk.SystemWebChromeClient{
	public *;
}
# 1. extension interfaces should be apparent
-keep public class com.tencent.smtt.export.external.extension.interfaces.* {
	public protected *;
}

# 2. interfaces should be apparent
-keep public class com.tencent.smtt.export.external.interfaces.* {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.WebViewCallbackClient {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.WebStorage$QuotaUpdater {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebIconDatabase {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebStorage {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.DownloadListener {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.QbSdk {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.QbSdk$PreInitCallback {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.CookieSyncManager {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.Tbs* {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.LogFileUtils {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.TbsLog {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.TbsLogClient {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.CookieSyncManager {
	public <fields>;
	public <methods>;
}

# Added for game demos
-keep public class com.tencent.smtt.sdk.TBSGamePlayer {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerClient* {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerClientExtension {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerService* {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.Apn {
	public <fields>;
	public <methods>;
}
-keep class com.tencent.smtt.** {
	*;
}
# end


-keep public class com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension {
	public <fields>;
	public <methods>;
}

-keep class MTT.ThirdAppInfoNew {
	*;
}

-keep class com.tencent.mtt.MttTraceEvent {
	*;
}

# Game related
-keep public class com.tencent.smtt.gamesdk.* {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.TBSGameBooter {
        public <fields>;
        public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGameBaseActivity {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.TBSGameBaseActivityProxy {
	public protected *;
}

-keep public class com.tencent.smtt.gamesdk.internal.TBSGameServiceClient {
	public *;
}
# --------------------------------------------------------------------------
#zbar
-keep class net.sourceforge.zbar.** { *; }
-keep interface net.sourceforge.zbar.** { *; }
-dontwarn net.sourceforge.zbar.**
