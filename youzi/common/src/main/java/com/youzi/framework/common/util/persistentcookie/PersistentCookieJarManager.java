package com.youzi.framework.common.util.persistentcookie;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
public class PersistentCookieJarManager implements CookieJar {
    private static PersistentCookieJarManager persistentCookieJarManager;
    private final PersistentCookieStore cookieStore;

    public PersistentCookieJarManager(Context applicationContext) {
        cookieStore = PersistentCookieStore.getInstance(applicationContext);
    }

    /**
     * 请使用ApplicationContext
     *
     * @param applicationContext Application上下文
     * @return
     */
    public synchronized static PersistentCookieJarManager getInstance(Context applicationContext) {
        if (persistentCookieJarManager == null) {
            persistentCookieJarManager = new PersistentCookieJarManager(applicationContext);
        }
        return persistentCookieJarManager;
    }

    public PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }
}