package com.kimeeo.kAndroid.aQueryDataProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by BhavinPadhiyar on 02/05/16.
 */
public interface IOfflineListProvider {
    List getData(String nextURL, Object nextParam);
}
