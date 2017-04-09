package com.example.alex.androidclient.rest;

/**
 * Created by Jag on 03.04.2017.
 */

import com.example.alex.androidclient.models.dictionaries.persons.DictionaryPersons;
import com.example.alex.androidclient.models.dictionaries.sites.DictionarySites;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("GetSites")
    Call<DictionarySites> getDictionarySites();

    @GET("GetPersons")
    Call<DictionaryPersons> getDictionaryPersons();
}
