package com.kimeeo.kAndroid.aQueryDataProvider;

import com.kimeeo.kAndroid.listViews.dataProvider.NetworkDataProvider;

/**
 * Created by bhavinpadhiyar on 12/25/15.
 */
public interface IParseableObject {
    void dataLoaded(NetworkDataProvider.IListParser entireData);
}
