
package com.example.eventspoc.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Public {

    @SerializedName("startTBD")
    @Expose
    private Boolean startTBD;
    @SerializedName("startTBA")
    @Expose
    private Boolean startTBA;

    public Boolean getStartTBD() {
        return startTBD;
    }

    public void setStartTBD(Boolean startTBD) {
        this.startTBD = startTBD;
    }

    public Boolean getStartTBA() {
        return startTBA;
    }

    public void setStartTBA(Boolean startTBA) {
        this.startTBA = startTBA;
    }

}
