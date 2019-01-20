package com.youzi.framework.common.util.systembar;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.youzi.framework.common.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by LuoHaifeng on 2018/4/27 0027.
 * Email:496349136@qq.com
 */

//TODO 需要完善多窗口模式的状态栏支持(当处于上下分屏模式的下部时 需要隐藏状态栏)
public class BarCompat {
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";
    private static final String NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width";
    private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";

    private Builder builder;
    private View statusBarView, navigationBarView;
    private final Activity activity;
    private NoPaddingLinearLayout proxyContentView;

    public BarCompat(Builder builder, Activity activity) {
        this.builder = builder;
        this.activity = activity;
        installSupport();
    }

    public BarCompat setBuilder(Builder builder) {
        this.builder = builder;
        return this;
    }

    private void installSupport() {
        if (isSupportCurrentVersion()) {
            synchronized (activity) {
                statusBarView = activity.findViewById(R.id.common_id_proxy_content);
                if (statusBarView == null) {
                    View contentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
                    ViewGroup.LayoutParams contentViewLayoutParams = contentView.getLayoutParams();
                    ViewGroup contentViewParent = (ViewGroup) contentView.getParent();
                    int count = contentViewParent.getChildCount();
                    int contentViewIndex = 0;
                    for (int i = 0; i < count; i++) {
                        if (contentView == contentViewParent.getChildAt(i)) {
                            contentViewIndex = i;
                        }
                    }
                    contentViewParent.removeView(contentView);

                    proxyContentView = new NoPaddingLinearLayout(activity);
                    proxyContentView.setOrientation(LinearLayout.VERTICAL);
                    statusBarView = new View(activity);
                    statusBarView.setId(R.id.common_id_proxy_content);
                    LinearLayout.LayoutParams statusBarLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    statusBarLayoutParams.height = getInternalDimensionSize(activity.getResources(), STATUS_BAR_HEIGHT_RES_NAME);
                    proxyContentView.addView(statusBarView, statusBarLayoutParams);
                    LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    proxyContentView.setFitsSystemWindows(true);
                    proxyContentView.addView(contentView, contentParams);
                    contentViewParent.addView(proxyContentView, contentViewIndex, contentViewLayoutParams);
                }
                //默认关闭statusBar显示
                statusBarView.setVisibility(View.GONE);
            }
        }
    }

    public static BarCompat.Builder newBuilder() {
        return new Builder();
    }

    public static BarCompat.Builder newBuilder(Builder builder) {
        return new Builder(builder);
    }

    public BarCompat.Builder newBuilderWith() {
        return new Builder(builder);
    }

    public BarCompat update() {
        if (isSupportCurrentVersion()) {
            synchronized (this.activity) {
                Window window = this.activity.getWindow();
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    this.activity.isInMultiWindowMode();
                }

                if (isFullScreen(this.activity) || !builder.statusBarPadding) {
                    statusBarView.setVisibility(View.GONE);
                } else if (builder.statusBarColor != null) {
                    statusBarView.setVisibility(View.VISIBLE);
                    statusBarView.getLayoutParams().height = getInternalDimensionSize(activity.getResources(), STATUS_BAR_HEIGHT_RES_NAME);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.TRANSPARENT);
                    } else {
                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                    statusBarView.setBackgroundColor(builder.statusBarColor);
                }

                if (builder.statusBarTextColorDarkMode != null) {
                    setStatusBarTextColor(window, builder.statusBarTextColorDarkMode);
                }
            }
        }

        return this;
    }

    public Builder getBuilder() {
        return builder;
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getStatusBarHeight(Resources res) {
        return getInternalDimensionSize(res, STATUS_BAR_HEIGHT_RES_NAME);
    }

    public static void setStatusBarTextColor(Window window, boolean isDarkMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setMStatusBarLightMode(window, isDarkMode);
        }

        setMIUIStatusBarLightMode(window, isDarkMode);
        setFlymeStatusBarLightMode(window, isDarkMode);
    }

    public static boolean isSupportCurrentVersion(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    /**
     * 设置魅族手机状态栏颜色值
     *
     * @param window
     * @param dark   是否为深色模式
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    private static boolean setFlymeStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception ignored) {

            }
        }
        return result;
    }

    /**
     * 设置小米手机状态栏颜色值
     *
     * @param window
     * @param dark   是否为深色模式
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    private static boolean setMIUIStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception ignored) {

            }
        }
        return result;
    }

    /**
     * 设置6.0手机 系统自带的设置状态栏颜色
     *
     * @param window
     * @param dark   是否为深色模式
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setMStatusBarLightMode(final Window window, boolean dark) {
        int flags = window.getDecorView().getSystemUiVisibility();
        window.getDecorView().setSystemUiVisibility(dark ? (flags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (flags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    /**
     * 页面是否处于全屏模式
     *
     * @param activity
     * @return
     */
    public static boolean isFullScreen(Activity activity) {
        int flag = activity.getWindow().getAttributes().flags;
        return (flag & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }


    public static class Builder implements Cloneable {
        private Boolean statusBarPadding = null;
        @ColorInt
        private Integer statusBarColor = null;
        private Boolean navigationBarPadding = null;
        @ColorInt
        private Integer navigationBarColor = null;
        private Boolean statusBarTextColorDarkMode = null;

        public Builder() {
        }

        public Builder(Builder builder) {
            this.statusBarPadding = builder.statusBarPadding;
            this.statusBarColor = builder.statusBarColor;
            this.navigationBarPadding = builder.navigationBarPadding;
            this.navigationBarColor = builder.navigationBarColor;
            this.statusBarTextColorDarkMode = builder.statusBarTextColorDarkMode;
        }

        public Boolean getStatusBarPadding() {
            return statusBarPadding;
        }

        public Builder setStatusBarPadding(Boolean statusBarPadding) {
            this.statusBarPadding = statusBarPadding;
            return this;
        }

        public Integer getStatusBarColor() {
            return statusBarColor;
        }

        public Builder setStatusBarColor(Integer statusBarColor) {
            this.statusBarColor = statusBarColor;
            return this;
        }

        public Boolean getNavigationBarPadding() {
            return navigationBarPadding;
        }

        public Builder setNavigationBarPadding(Boolean navigationBarPadding) {
            this.navigationBarPadding = navigationBarPadding;
            return this;
        }

        public Integer getNavigationBarColor() {
            return navigationBarColor;
        }

        public Builder setNavigationBarColor(Integer navigationBarColor) {
            this.navigationBarColor = navigationBarColor;
            return this;
        }

        public Boolean getStatusBarTextColorDarkMode() {
            return statusBarTextColorDarkMode;
        }

        public Builder setStatusBarTextColorDarkMode(Boolean statusBarTextColorDarkMode) {
            this.statusBarTextColorDarkMode = statusBarTextColorDarkMode;
            return this;
        }

        public BarCompat build(Activity activity) {
//            BarCompat barCompat = (BarCompat) activity.getWindow().getDecorView().getTag(R.id.common_id_system_manager);
//            if (barCompat == null) {
//                barCompat = new BarCompat(this, activity);
//                activity.getWindow().getDecorView().setTag(R.id.common_id_system_manager, barCompat);
//            } else {
//                barCompat.setBuilder(this);
//            }
            BarCompat barCompat = new BarCompat(this, activity);
            return barCompat.update();
        }
    }
}
