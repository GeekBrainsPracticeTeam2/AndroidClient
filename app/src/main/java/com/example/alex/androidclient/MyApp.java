package com.example.alex.androidclient;

import android.app.Application;

import com.example.alex.androidclient.managers.CacheManager;
import com.example.alex.androidclient.models.DictionaryPersons;
import com.example.alex.androidclient.models.DictionarySites;

import java.util.ArrayList;

/**
 * Created by alex on 17.03.17.
 */

public class MyApp extends Application {
    private CacheManager cacheManager;
    private ArrayList<DictionarySites> dictionarySites;
    private ArrayList<DictionaryPersons> dictionaryPersons;


    public CacheManager getCacheManager() {
        return cacheManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initDictionarySites();

        initDictionaryPersons();

        initCacheManager();
    }

    private void initCacheManager(){
        if (cacheManager == null){
            cacheManager = new CacheManager(this);
        }
    }

    private void initDictionarySites(){
        if (dictionarySites == null){
            dictionarySites = new ArrayList<>();
        }
    }

    private void initDictionaryPersons(){
        if (dictionaryPersons == null){
            dictionaryPersons = new ArrayList<>();
        }
    }
}
