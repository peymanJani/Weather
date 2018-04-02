
package com.example.peyman.jani.weather.Samples;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainJason {

    @SerializedName("query")
    @Expose
    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

}
