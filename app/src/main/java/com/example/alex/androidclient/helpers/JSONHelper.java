package com.example.alex.androidclient.helpers;

import android.util.Log;

import com.afollestad.ason.Ason;
import com.afollestad.ason.AsonArray;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.alex.androidclient.models.DictionaryUpdates;
import com.example.alex.androidclient.models.PersonStats;
import com.example.alex.androidclient.models.TotalStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jag on 09.03.2017.
 */

public class JSONHelper {

    public static final String LOG_TAG = "JSONHelper";
    private static final String SAMPLE_JSON_TOTAL_STATISTICS = "{\"siteID\":0,\"statistics\":[{\"person\":0,\"count\":5},{\"person\":1,\"count\":7},{\"person\":2,\"count\":14},{\"person\":3,\"count\":8},{\"person\":4,\"count\":27}]}";
    private static final String SAMPLE_JSON_UPDATE_STATUS = "{\"tables\":[{\"ID\":0,\"lu_date\":\"2017-03-13 15:56:26\"},{\"ID\":1,\"lu_date\":\"2017-03-13 15:56:26\"}]}";
    private static final String SAMPLE_JSON_SITES_DIR_UPDATE = "{\"data\":[{\"ID\":0,\"url\":\"lenta.ru\"},{\"ID\":1,\"url\":\"vesti.ru\"},{\"ID\":2,\"url\":\"kp.ru\"}]}";
    private static final String SAMPLE_JSON_NAMES_DIR_UPDATE = "{\"data\":[{\"ID\":0,\"name\":\"Путин В.В.\"},{\"ID\":1,\"name\":\"Медведев Д.А.\"},{\"ID\":2,\"name\":\"Навальный ?.?.\"}]}";

    public static final String NAMES_STATISTICS = "statistics";
    public static final String NAMES_SITE_ID = "siteID";
    public static final String TABLES_UPDATES = "tables";
    private RequestQueue requestQueue;
    private Ason ason;
    private TotalStatistics totalStats;
    private List<DictionaryUpdates> dictionaryUpdatesList = new ArrayList<>();

    private int mode = -1;

    public JSONHelper(int mode) {
        this.mode = mode;
        switch (mode){
            case 0:
                ason = new Ason(SAMPLE_JSON_TOTAL_STATISTICS);
                break;
            case 1:
                ason = new Ason(SAMPLE_JSON_UPDATE_STATUS);
                break;
            case 2:
                ason = new Ason(SAMPLE_JSON_SITES_DIR_UPDATE);
                break;
            case 3:
                ason = new Ason(SAMPLE_JSON_NAMES_DIR_UPDATE);
                break;
        }
        fetchData();
    }

    private void fetchData() {
        switch (mode){
            case 0:
                /*
                AsonArray personsStatistics = ason.getJsonArray(NAMES_STATISTICS);
                 */
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
                break;
            case 1:
                AsonArray dictionaryUpdates = ason.getJsonArray(TABLES_UPDATES);
                dictionaryUpdatesList = Ason.deserializeList(dictionaryUpdates, DictionaryUpdates.class);
                break;
            case 2:
                break;
            case 3:
                break;
        }
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

    public List<DictionaryUpdates> getDictionaryUpdatesList() {
        return dictionaryUpdatesList;
    }
}
