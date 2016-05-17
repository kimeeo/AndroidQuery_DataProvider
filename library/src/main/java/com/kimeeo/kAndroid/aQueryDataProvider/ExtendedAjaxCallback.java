package com.kimeeo.kAndroid.aQueryDataProvider;

import com.androidquery.callback.AjaxCallback;

import org.apache.http.cookie.Cookie;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 9/7/15.
 */
public class ExtendedAjaxCallback<T> extends AjaxCallback<T> {

    private WeakReference<Map<String, Object>> params;

    public Map<String, Object> getParams() {
        return params.get();
    }
    public void setParams(Map<String, Object> value) {
        if(value!=null)
            params = new WeakReference(value);
    }
    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    Class clazz;

    private static List<Cookie> cookies;
    public static List<Cookie> getCookies()
    {
        return cookies;
    }
    public static void setCookies(List<Cookie> cookies)
    {
        ExtendedAjaxCallback.cookies=cookies;
    }


    private static Map<String, String> headers;
    public static Map<String, String> getHeaders()
    {
        return headers;
    }
    public static void setHeaders(Map<String, String> headers)
    {
        ExtendedAjaxCallback.headers=headers;
    }


}
