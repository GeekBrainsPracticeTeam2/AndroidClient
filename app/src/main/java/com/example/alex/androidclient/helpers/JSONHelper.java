package com.example.alex.androidclient.helpers;

import android.util.Log;

import com.afollestad.ason.Ason;
import com.afollestad.ason.AsonArray;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.alex.androidclient.models.PersonStats;
import com.example.alex.androidclient.models.TotalStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jag on 09.03.2017.
 */

public class JSONHelper {

    public static final String LOG_TAG = "JSONHelper";
    private static final String SAMPLE_JSON = "{\"siteID\":0,\"statistics\":[{\"person\":0,\"count\":5},{\"person\":1,\"count\":7},{\"person\":2,\"count\":14},{\"person\":3,\"count\":8},{\"person\":4,\"count\":27}]}";
    public static final String NAMES_STATISTICS = "statistics";
    public static final String NAMES_SITE_ID = "siteID";
    private RequestQueue requestQueue;
    private Ason ason;
    private TotalStatistics totalStats;

    public JSONHelper() {
        ason = new Ason(SAMPLE_JSON);
        fetchData();
    }

    private void fetchData() {
        AsonArray personsStatistics = ason.getJsonArray(NAMES_STATISTICS);
        AsonArray array = ason.getJsonArray(NAMES_STATISTICS);
        /*
        List<PersonStats> stats = new ArrayList<>();

        for (int i = 0; i < personsStatistics; i++) {
            stats.add(new PersonStats(personsStatistics.get))
        }
        */
        List<PersonStats> statsList = Ason.deserializeList(array, PersonStats.class);
        totalStats = new TotalStatistics(ason.getInt(NAMES_SITE_ID),
                statsList);
    }

    public void fetchData(String url) {
        // let's start to fetch data from Json
        StringRequest request = new StringRequest(Request.Method.GET, url,
                onPostsLoaded, onPostsError);

        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(LOG_TAG, error.toString());
        }
    };
}
