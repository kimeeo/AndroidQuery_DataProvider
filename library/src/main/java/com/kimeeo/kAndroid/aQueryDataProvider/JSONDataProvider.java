package com.kimeeo.kAndroid.aQueryDataProvider;

import android.content.Context;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.kimeeo.kAndroid.listViews.dataProvider.NetworkDataProvider;

import org.apache.http.cookie.Cookie;

import java.util.List;
import java.util.Map;

/**
 * Created by BhavinPadhiyar on 02/05/16.
 */
abstract public class JSONDataProvider extends BaseAQueryDataProvider
{
    protected Gson gson;
    public JSONDataProvider(Context context)
    {
        super(context);
        gson= new Gson();
    }
    public void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        gson=null;
    }
    @Override
    protected void dataHandler(String url, Object json, AjaxStatus status)
    {
        try
        {
            Class<ListParser> clazz = getListParser();
            ListParser listParser = gson.fromJson((String)json, clazz);
            List<?> list=listParser.getDataProvider();
            if(list!=null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) instanceof IParseableObject)
                        ((IParseableObject) list.get(i)).dataLoaded(listParser);
                }
                dataIn(url,listParser);
                addData(list);
            }
            else
            {
                dataIn(url,json);
                dataLoadError(status);
            }
        }
        catch (Throwable e)
        {
            dataIn(url,json);
            dataLoadError(e);
        }
    }


}
