package com.kimeeo.kAndroid.aQueryDataProvider;

import android.content.Context;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.kimeeo.kAndroid.listViews.dataProvider.NetworkDataProvider;

import org.apache.http.cookie.Cookie;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by BhavinPadhiyar on 02/05/16.
 */
abstract public class BaseAQueryDataProvider extends NetworkDataProvider
{

    protected AQuery androidQuery;
    private List<Cookie> cookies;
    public List<Cookie> getCookies()
    {
        return cookies;
    }
    public void setCookies(List<Cookie> cookies)
    {
        this.cookies=cookies;
    }


    private Map<String,String> headers;
    public Map<String,String> getHeaders()
    {
        return headers;
    }
    public void setHeaders(Map<String,String> headers)
    {
        this.headers=headers;
    }


    //private long cachingTime=1 * 60 * 1000;
    private long cachingTime=-1;
    protected long getCachingTime()
    {
        return cachingTime;
    }
    protected void setCachingTime(long value)
    {
        cachingTime = value;
    }


    public BaseAQueryDataProvider(Context context)
    {
        super();
        androidQuery= new AQuery(context);
    }

    public AQuery getaQuery() {
        return androidQuery;
    }
    public void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        ajaxCancel();
        androidQuery=null;
        headers=null;
        cookies = null;
    }
    protected ExtendedAjaxCallback getAjaxCallback() {
        ExtendedAjaxCallback<Object> ajaxCallback = new ExtendedAjaxCallback<Object>() {
            public void callback(String url, Object json, AjaxStatus status) {
                dataHandler(url, json, status);
            }
        };

        List<Cookie> cookies = getCookies();
        if(cookies!=null && cookies.size()!=0) {
            for (Cookie cookie : cookies) {
                ajaxCallback.cookie(cookie.getName(), cookie.getValue());
            }
        }
        else {
            cookies = ExtendedAjaxCallback.getCookies();
            if (cookies != null && cookies.size() != 0) {
                for (Cookie cookie : cookies) {
                    ajaxCallback.cookie(cookie.getName(), cookie.getValue());
                }
            }
        }



        Map<String,String> headers = getHeaders();
        if(headers!=null && headers.entrySet().size()!=0)
            ajaxCallback.headers(headers);
        else {
            headers = ExtendedAjaxCallback.getHeaders();
            if (headers != null && headers.entrySet().size() != 0)
                ajaxCallback.headers(headers);
        }

        return ajaxCallback;
    }
    abstract protected void dataHandler(String url, Object json, AjaxStatus status);

    protected void dataIn(String url, Object json)
    {

    }

    public void ajaxCancel()
    {
        if(androidQuery!=null)
            androidQuery.ajaxCancel();
    }
    private void invokePostService(String url, Map<String, Object> params)
    {
        ExtendedAjaxCallback<String> ajaxCallback =getAjaxCallback();
        ajaxCallback.setParams(params);
        ajaxCallback.setClazz(String.class);
        ajaxCallback.expire(getCachingTime());
        getaQuery().ajax(url, params, String.class, ajaxCallback);
    }

    private void invokeGetService(String url)
    {
        ExtendedAjaxCallback<String> ajaxCallback =getAjaxCallback();
        ajaxCallback.setClazz(String.class);
        getaQuery().ajax(url, String.class,getCachingTime(), ajaxCallback);
    }


    protected void invokeLoadNext()
    {
        String url = getNextURL();
        if(url!=null) {
            if (getMethod() == METHOD_GET) {
                invokeGetService(url);
            }
            else if (getMethod() == METHOD_POST) {
                invokePostService(url,getNextParam());
            }
        }
        else {
            setCanLoadNext(false);
            dataLoadError(null);
        }
    }
    protected void invokeloadRefresh()
    {
        String url = getRefreshURL();
        if(url!=null) {
            if (getMethod() == METHOD_GET)
                invokeGetService(url);
            else if (getMethod() == METHOD_POST)
                invokePostService(url,getNextParam());
        }
        else {
            setCanLoadRefresh(false);
            dataLoadError(null);
        }
    }
}
