
package com.example.eventspoc.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Seatmap {

    @SerializedName("staticUrl")
    @Expose
    private String staticUrl;

    public String getStaticUrl() {
        return staticUrl;
    }

    public void setStaticUrl(String staticUrl) {
        this.staticUrl = staticUrl;
    }


}
